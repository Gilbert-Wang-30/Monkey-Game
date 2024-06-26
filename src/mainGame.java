// Gilbert Wang
// mainGame
// This class is main for monkey game
/* Which is a game that a 2d action game with 2 monkey players on different maps 
 * jumping on different platforms and shooting each other. In every round, each player has 
 * only 1 life and 1 hp. There are going to be 7(or more) rounds and the player that wins more 
 * rounds wins. One player is going to use wad, space and the other player is going to use arrows and enter. T
 * here are different weapons on the map for players to grab such as pistols or ak47.
 */
// January 28th, 2023


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

// Main
// Description: it is a dynamic game that listens to keys and mouses
public class mainGame extends JPanel implements Runnable, KeyListener, MouseListener {
	int gameState = 0;
	// gameState 0: main menu
	// gameState 1: in game
	// gameState 2: settlement interface
	// gameState 3: guide screen
	// gameState 4: challenge screen
	// gameState 5: about screen
	int roundNumber = 1;
	int Winner;
	// array for walls
	Rectangle[] walls = new Rectangle[10];
	int screenWidth = 900;
	int screenHeight = 600;
	Thread thread;
	int FPS = 60;
	// # of the rounds players win
	int p1Wins =0;
	int p2Wins =0;
	double gravity = 0.8;
	// has the game ended
	boolean gameEnd = true;
	// it is supposed to display the winner
	boolean displayWinner = false;
	// Linked lists for bullets, aks, pistols and armor
	LinkedList<bullet> bulletlist = new LinkedList<bullet>();
	LinkedList<ak> aklist = new LinkedList<ak>();
	LinkedList<pistol> pistollist = new LinkedList<pistol>();
	LinkedList<armor> armorlist = new LinkedList<armor>();
	
	// Images for maps and weapons
	BufferedImage map;
	BufferedImage map1;
	BufferedImage map2;
	BufferedImage menuScreen;
	BufferedImage challengesScreen;
	BufferedImage guide;
	BufferedImage about;
	Image akLeft;
	Image akRight;
	Image pistolLeft;
	Image pistolRight;
	Image armor;
	
	// Monkey player object
	monkey p1 = new monkey(150, 200, screenWidth, screenHeight, 4, gravity, 15, 1);
	monkey p2 = new monkey(735, 200, screenWidth, screenHeight, 4, gravity, 15, 2);
	// offScreenBuffer to aviod lag
	Graphics offScreenBuffer;
	Image offScreenImage;
	// HashMap to record challenges
	static HashMap<String, challenges> killsCount = new HashMap<String, challenges>();
	// array list that allows the programes to sort challenges
	static ArrayList<challenges> challengesList;
	
	
	public mainGame() {
		// JFrame
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);
		thread = new Thread(this);
		thread.start();
		// read images
		try 
		{
			map1 = ImageIO.read(new File("images/map1.png"));
			map = map1;
			map2 = ImageIO.read(new File("images/map2.png"));
			menuScreen = ImageIO.read(new File("images/menuScreen.png"));
			challengesScreen = ImageIO.read(new File("images/challenges.png"));
			guide = ImageIO.read(new File("images/guide.png"));
			about = ImageIO.read(new File("images/about.png"));
			akLeft = Toolkit.getDefaultToolkit().getImage("images/akLeft.png");
			akRight = Toolkit.getDefaultToolkit().getImage("images/akRight.png");
			pistolLeft = Toolkit.getDefaultToolkit().getImage("images/pistolLeft.png");
			pistolRight = Toolkit.getDefaultToolkit().getImage("images/pistolRight.png");
			armor = Toolkit.getDefaultToolkit().getImage("images/armor.png");
		}
		catch(Exception e)
		{
		}
	}
	
	// Run method
	// void
	// a method for the game to run
	public void run() {
		while(true) {
			//main game loop
			
			// code to initialize maps everytime a round ends
			if(gameEnd) {
				if(p1Wins>=4||p2Wins>=4&&gameState ==1) {
					gameState = 2;
				}
				else {
					displayWinner = false;
					if(roundNumber%2==1) {
						initializeMap1();
					}
					else {
						initializeMap2();
					}
					gameEnd = false;
				}
			}
			// update and thread
			update();
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// initializeMap1
	// This method initialize the position of all the walls, weapons and players in map1
	public void initializeMap1() {
		//setups before the game starts running
		map = map1;
		walls[0] = new Rectangle(225, 550, 450, 30);
		walls[1] = new Rectangle(225, 450, 20, 100);
		walls[2] = new Rectangle(655, 450, 20, 100);
		walls[3] = new Rectangle(100, 450, 125, 30);
		walls[4] = new Rectangle(675, 450, 125, 30);
		walls[5] = new Rectangle(100, 150, 20, 300);
		walls[6] = new Rectangle(780, 150, 20, 300);
		walls[7] = new Rectangle(440, 250, 20, 230);
		walls[8] = new Rectangle(320, 360, 260, 30);
		walls[9] = new Rectangle(400, 230, 100, 30);
		bulletlist = new LinkedList<bullet>();
		aklist = new LinkedList<ak>();
		pistollist = new LinkedList<pistol>();
		armorlist = new LinkedList<armor>();
		p1 = new monkey(150, 200, screenWidth, screenHeight, 4, gravity, 15, 1);
		p2 = new monkey(735, 200, screenWidth, screenHeight, 4, gravity, 15, 2);
		aklist.add(new ak(432,539));
		pistollist.add(new pistol(445,223));
		armorlist.add(new armor(445,213));
	}
	
	// initializeMap2
		// This method initialize the position of all the walls, weapons and players in map2
	public void initializeMap2() {
		//setups before the game starts running
		map = map2;
		walls[0] = new Rectangle(230, 450, 440, 30);
		walls[1] = new Rectangle(225, 450, 20, 130);
		walls[2] = new Rectangle(655, 450, 20, 130);
		walls[3] = new Rectangle(100, 550, 125, 30);
		walls[4] = new Rectangle(675, 550, 125, 30);
		walls[5] = new Rectangle(100, 150, 20, 400);
		walls[6] = new Rectangle(780, 150, 20, 400);
		walls[7] = new Rectangle(300, 360, 300, 30);
		walls[8] = new Rectangle(320, 260, 260, 30);
		walls[9] = new Rectangle(400, 190, 100, 30);
		bulletlist = new LinkedList<bullet>();
		aklist = new LinkedList<ak>();
		pistollist = new LinkedList<pistol>();
		armorlist = new LinkedList<armor>();
		p1 = new monkey(150, 200, screenWidth, screenHeight, 4, gravity, 15, 1);
		p2 = new monkey(735, 200, screenWidth, screenHeight, 4, gravity, 15, 2);
		aklist.add(new ak(432,339));
		pistollist.add(new pistol(445,245));
		armorlist.add(new armor(445,180));
	}
	
	// update
	// This method updates the position of the players, weapons, bulltes, armor, and checks for collections between player and other objects
	// such as weapons and bullets
	public void update() {
		// player movement
		p1.move();
		p2.move();
		p1.keepInBound();
		p2.keepInBound();
		for(int i = 0; i < walls.length; i++) {
			p1.checkCollision(walls[i]);
			p2.checkCollision(walls[i]);
		}
		// ak grabbed or on the floor
		for(int i = 0; i < aklist.size(); i++) {
			if(p1.checkCollision(aklist.get(i))||p2.checkCollision(aklist.get(i))) {
				aklist.remove(i);
			}
		}
		// pistol grabbed or on the floor
		for(int i = 0; i < pistollist.size(); i++) {
			if(p1.checkCollision(pistollist.get(i))||p2.checkCollision(pistollist.get(i))) {
				pistollist.remove(i);
			}
		}
		// armor grabbed or on the floor
		for(int i = 0; i < armorlist.size(); i++) {
			if(p1.checkCollision(armorlist.get(i))||p2.checkCollision(armorlist.get(i))) {
				armorlist.remove(i);
			}
		}
		// is players shooting ak
		if(p1.getHasAk()) {
			bullet b = p1.getAk().checkShooting();
			if(b!=null&&b.checkCollision(walls)) {
				bulletlist.add(b);
			}
		}
		if(p2.getHasAk()) {
			bullet b = p2.getAk().checkShooting();
			if(b!=null&&b.checkCollision(walls)) {
				bulletlist.add(b);
			}
		}
		// is players shooting pistol
		if(p1.getHasPistol()) {
			bullet b = p1.getPistol().checkShooting();
			if(b!=null&&b.checkCollision(walls)) {
				bulletlist.add(b);
			}
		}
		if(p2.getHasPistol()) {
			bullet b = p2.getPistol().checkShooting();
			if(b!=null&&b.checkCollision(walls)) {
				bulletlist.add(b);
			}
		}
		// does the bullet hits the player or wall?
		for(int i = 0; i< bulletlist.size(); i++) {
			bullet b = bulletlist.get(i);
			b.move();
			if(!b.checkCollision(walls)) {
				bulletlist.remove(i);
				i--;
			}
			else if(b.checkShot(p1)) {
				bulletlist.remove(i);
				i--;
				p1.getShot();
			}
			else if(b.checkShot(p2)) {
				bulletlist.remove(i);
				i--;
				p2.getShot();
			}
		}
	}
	
	// paintComponent
	// paints everything of the game
	public void paintComponent(Graphics g) {
		// menu
		if(gameState == 0) {
			if (offScreenBuffer == null)  {
				// offScreen Settings
				offScreenImage =createImage (this.getWidth(), this.getHeight ());
				offScreenBuffer = offScreenImage.getGraphics ();
			}
			offScreenBuffer.clearRect (0,0,this.getWidth(), this.getHeight ());
			offScreenBuffer.drawImage(menuScreen, 0, 0, null);
			g.drawImage (offScreenImage, 0,0,this);
		}
		// in game
		else if(gameState == 1) {
			// offScreen Settings
			if (offScreenBuffer == null)  {
				offScreenImage =createImage (this.getWidth(), this.getHeight ());
				offScreenBuffer = offScreenImage.getGraphics ();
			}
			offScreenBuffer.clearRect (0,0,this.getWidth(), this.getHeight ());
			offScreenBuffer.setColor(new Color(0,0,255));
			//Background
			offScreenBuffer.drawImage(map, 0, 0, null);
			// draw bullets
			for(int i = 0; i<bulletlist.size(); i++) {
				bullet b = bulletlist.get(i);
				offScreenBuffer.drawImage(b.getImage(),b.getXpos(),b.getYpos(), null);
			}
			// draw monkeys
			offScreenBuffer.drawImage(p1.getMonkeyImage(), p1.getXpos(), p1.getYpos(), null);
			offScreenBuffer.drawImage(p2.getMonkeyImage(), p2.getXpos(), p2.getYpos(), null);
			// draw aks
			for(int i = 0; i<aklist.size(); i++) {
				ak AK = aklist.get(i);
				offScreenBuffer.drawImage(AK.getLeftImage(),AK.getXpos(),AK.getYpos(), null);
			}
			// draw pistols
			for(int i = 0; i<pistollist.size(); i++) {
				pistol Pistol = pistollist.get(i);
				offScreenBuffer.drawImage(Pistol.getLeftImage(),Pistol.getXpos(),Pistol.getYpos(), null);
			}
			// draw armors
			for(int i = 0; i<armorlist.size(); i++) {
				armor armor= armorlist.get(i);
				offScreenBuffer.drawImage(armor.getImage(),armor.getXpos(),armor.getYpos(), null);
			}
			// draw UI for rounds
			offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 30));
			offScreenBuffer.drawString(p1Wins + "/4 to Win",164, 42);
			offScreenBuffer.drawString(p2Wins + "/4 to Win",620, 42);
			offScreenBuffer.setColor(new Color(0,0,0));
			
			// draw UI for remaining bullets 
			if(p1.getHasAk()) {
				offScreenBuffer.drawImage(akRight,10,60,64,22,this);
				offScreenBuffer.drawString(p1.getAk().getRemainBullet() + "/45",64, 42);
			}
			else if(p1.getHasPistol()) {
				offScreenBuffer.drawImage(pistolRight,10,60,22,14,this);
				offScreenBuffer.drawString(p1.getPistol().getRemainBullet() + "/20",64, 42);
			}
			
			if(p2.getHasAk()) {
				offScreenBuffer.drawImage(akLeft,826,60,64,22,this);
				offScreenBuffer.drawString(p2.getAk().getRemainBullet() + "/45",770, 42);
			}
			else if(p2.getHasPistol()) {
				offScreenBuffer.drawImage(pistolLeft,870,60,22,14,this);
				offScreenBuffer.drawString(p2.getPistol().getRemainBullet() + "/20",770, 42);
			}
			// draw UI for armor
			if(p1.getHasArmor()) {
				offScreenBuffer.drawImage(armor,10,78,20,20,this);
			}
			else if(p2.getHasArmor()) {
				offScreenBuffer.drawImage(armor,870,78,20,20,this);
			}
			// display the winner or draw
			if(!p1.getAlive()&&p2.getAlive()) {
				displayWinner = true;
				Winner = 2;
			}
			else if(!p2.getAlive()&&p1.getAlive()) {
				displayWinner = true;
				Winner = 1;
			}
			// When no bullets, draw
			else if(p1.getHasAk()&&p1.getAk().getRemainBullet()==0&&p2.getHasPistol()&&p2.getPistol().getRemainBullet()==0) {
				displayWinner = true;
				Winner =0;
			}
			else if(p2.getHasAk()&&p2.getAk().getRemainBullet()==0&&p1.getHasPistol()&&p1.getPistol().getRemainBullet()==0) {
				displayWinner = true;
				Winner =0;
			}
			// Draw the display of winner
			if(displayWinner && Winner ==2) {
				offScreenBuffer.setColor(new Color(200, 160, 255));
				offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 90));
				offScreenBuffer.drawString("PLAYER TWO WINS",140, 150);
				offScreenBuffer.setColor(new Color(0,0,0));
				offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 20));
				offScreenBuffer.drawString("Click mouse to continue",349, 200);
			}
			else if(displayWinner && Winner==1) {
				offScreenBuffer.setColor(new Color(200, 160, 255));
				offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 90));
				offScreenBuffer.drawString("PLAYER ONE WINS",140, 150);
				offScreenBuffer.setColor(new Color(0,0,0));
				offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 20));
				offScreenBuffer.drawString("Click mouse to continue",349, 200);
			}
			else if(displayWinner && Winner==0) {
				offScreenBuffer.setColor(new Color(200, 160, 255));
				offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 90));
				offScreenBuffer.drawString("DRAW",345, 150);
				offScreenBuffer.setColor(new Color(0,0,0));
				offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 20));
				offScreenBuffer.drawString("Click mouse to continue",349, 200);
			}
			
			g.drawImage (offScreenImage, 0,0,this);
		}
		// settlement interface
		else if(gameState ==2) {
			// player 1 win
			if(p1Wins>=4) {
				try 
				{
					Image player1Win = Toolkit.getDefaultToolkit().getImage("images/player1Win.png");
					g.drawImage (player1Win, 0,0,this);
				}
				catch(Exception e){
				}
			}
			// player 2 win
			else {
				try 
				{
					Image player2Win = Toolkit.getDefaultToolkit().getImage("images/player2Win.png");
					g.drawImage (player2Win, 0,0,this);
				}
				catch(Exception e){
				}
			}
		}
		// draw guide 
		else if(gameState ==3) {
			offScreenBuffer.drawImage(guide, 0,0,this);
			g.drawImage (offScreenImage, 0,0,this);
		}
		// draw challenges
		else if(gameState ==4) {
			// background
			offScreenBuffer.drawImage(challengesScreen, 0,0,this);
			Collections.sort(challengesList);
			offScreenBuffer.setColor(new Color(255,255,255));
			offScreenBuffer.setFont(new Font("Impact", Font.ITALIC, 20));
			// draw each challenge
			for(int i =0; i<challengesList.size();i++) {
				offScreenBuffer.drawString(challengesList.get(i).toString(),510, 140+i*80);
			}
			Collections.sort(challengesList, new SortByName());
			for(int i =0; i<challengesList.size();i++) {
				offScreenBuffer.drawString(challengesList.get(i).toString(),20, 140+i*80);
			}
			g.drawImage (offScreenImage, 0,0,this);
		}
		// draw about screen
		else if(gameState ==5) {
			offScreenBuffer.drawImage(about, 0,0,this);
			g.drawImage (offScreenImage, 0,0,this);
		}
	}
	
	// Key methods
	public void keyTyped(KeyEvent e) {
	}
	// keyPressed
	// This method allows players to control monkey by record the key pressed
	// Parameters: KeyEvent e, record key event
	public void keyPressed(KeyEvent e) {
		if(gameState ==1) {
			int key = e.getKeyCode();
			// shooting
			if(key == KeyEvent.VK_SPACE){
				if(p1.getHasAk()) {
					p1.getAk().setShooting(true);
				}
				else if(p1.getHasPistol()) {
					p1.getPistol().setShooting(true);
				}
			}if(key == KeyEvent.VK_ENTER) {
				if(p2.getHasAk()) {
					p2.getAk().setShooting(true);
				}
				else if(p2.getHasPistol()) {
					p2.getPistol().setShooting(true);
				}
			}
			// jump
			if(key == KeyEvent.VK_W) {
				p1.upPressed();
			}if(key == KeyEvent.VK_UP) {
				p2.upPressed();
			}
			// move
			if(key == KeyEvent.VK_A) {
				p1.leftPressed();
			}if(key == KeyEvent.VK_D) {
				p1.rightPressed();
			}if(key == KeyEvent.VK_LEFT) {
				p2.leftPressed();
			}if(key == KeyEvent.VK_RIGHT) {
				p2.rightPressed();
			}
		}
	}
	// keyReleased
	// This method allows players to control monkey by record the key released
	// Parameters: KeyEvent e, record key event
	public void keyReleased(KeyEvent e) {
		if(gameState ==1) {
			int key = e.getKeyCode();
			// shoot
			if(key == KeyEvent.VK_SPACE) {
				if(p1.getHasAk()) {
					p1.getAk().setShooting(false);
				}
				else if(p1.getHasPistol()) {
					p1.getPistol().setShooting(false);
					p1.getPistol().setHasShot();
				}
			}if(key == KeyEvent.VK_ENTER) {
				if(p2.getHasAk()) {
					p2.getAk().setShooting(false);
				}
				else if(p2.getHasPistol()) {
					p2.getPistol().setShooting(false);
					p2.getPistol().setHasShot();
				}
			}
			// Jump
			if(key == KeyEvent.VK_W) {
				p1.upReleased();
			}if(key == KeyEvent.VK_UP) {
				p2.upReleased();
			}
			// Move
			if(key == KeyEvent.VK_A) {
				p1.leftReleased();
			}if(key == KeyEvent.VK_D) {
				p1.rightReleased();
			}if(key == KeyEvent.VK_LEFT) {
				p2.leftReleased();
			}if(key == KeyEvent.VK_RIGHT) {
				p2.rightReleased();
			}
		}
	}
	
	// Main 
	// Parameters: String[] args
	// This is for JFrame and reading files for challenges
	public static void main(String[] args) throws IOException {
		// JFrame
		JFrame frame = new JFrame ("Monkey Game");
		mainGame myPanel = new mainGame ();
		frame.add(myPanel);
		frame.addKeyListener(myPanel);
		frame.addMouseListener(myPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		// Read file
		try {
			BufferedReader br = new BufferedReader (new FileReader("killsCount.txt"));
			String line = br.readLine();
			while (line != null){
				killsCount.put(line.substring(0, line.lastIndexOf(" ")),new challenges(line.substring(0, line.lastIndexOf(" ")),Integer.parseInt(line.substring(line.lastIndexOf(" ")+1))));
				line = br.readLine();
			}
			challengesList = new ArrayList<challenges>(killsCount.values());
		} catch (FileNotFoundException e) {
		}
		

	}

	// MouseMethods
	
	public void mouseClicked(MouseEvent e) {
	}
	

	// mousePressed
	// This method allows players to switch between different screens
	// Parameters: mouseEvent e, record Mouse event
	public void mousePressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		// mainScreen
		if(gameState ==0) {
			// start button
			if(mouseX>= 81 && mouseX <= 350 && mouseY >= 150 && mouseY <= 250) {
				gameState =1;
				gameEnd = false;
				paintComponent(this.getGraphics());
			}
			// guide button
			else if(mouseX>= 81 && mouseX <= 350 && mouseY >= 250 && mouseY <= 350) {
				gameState = 3;
				paintComponent(this.getGraphics());
			}
			// challenge button
			else if(mouseX>= 81 && mouseX <= 350 && mouseY >= 350 && mouseY <= 450) {
				gameState = 4;
				paintComponent(this.getGraphics());
			}
			// about button
			else if(mouseX>= 81 && mouseX <= 350 && mouseY >= 450 && mouseY <= 550) {
				gameState = 5;
				paintComponent(this.getGraphics());
			}
			
		}
		// end display winner, modify challenges and go to next round
		else if(gameState == 1&&displayWinner) {
			gameEnd = true;
			roundNumber++;
			// modify challenges
			if(Winner ==0) {
				killsCount.get("Draws     ").change();
			}
			if(Winner ==1) {
				p1Wins++;
				killsCount.get("P1 Kills  ").change();
			}
			else if(Winner ==2){
				p2Wins++;
				killsCount.get("P2 Kills  ").change();
			}
			displayWinner = false;
		}
		else if(gameState ==2) {
			// modify challenges
			if(p1Wins>=4) {
				killsCount.get("P1 Wins   ").change();
			}
			else if(p2Wins>=4) {
				killsCount.get("P2 Wins   ").change();
			}
			p1Wins =0;
			p2Wins =0;
			gameState = 0;
			// modify challenges file
			try 
			{
				PrintWriter outFile = new PrintWriter (new FileWriter ("killsCount.txt", false));
				challengesList = new ArrayList<challenges>(killsCount.values());
				for(int i = 0; i<challengesList.size(); i++) {
					outFile.println(challengesList.get(i));
				}
				outFile.close ();
			}
			catch (IOException a) {
				System.out.println("File error");
			}
		}
		// about screen exit
		else if(gameState ==3) {
			gameState = 0;
		}
		// challenge screen exit
		else if(gameState ==4) {
			gameState = 0;
		}
		// challenge screen exit
		else if(gameState ==5) {
			gameState = 0;
		}
	// useless mouse methods
	}public void mouseReleased(MouseEvent e) {
	}public void mouseEntered(MouseEvent e) {
	}public void mouseExited(MouseEvent e) {
	}
}
