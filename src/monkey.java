// Gilbert Wang
// monkey
// This class is monkey object for monkey game
/* Which creates the monkey and modify monkey's movement and status 
 */
// January 28th, 2023

import java.awt.Rectangle;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class monkey {
	// private variables
	// hitbox
	private Rectangle rect;
	// directions
	private boolean up; 
	private boolean left = false;
	private boolean right = false;
	// ak
	private boolean hasAK = false;
	private ak ak;
	// pistol
	private boolean hasPistol = false;
	private pistol pistol;
	// armor
	private boolean hasArmor = false;
	private int screenWidth;
	private int screenHeight;
	private double speed;
	private double gravity;
	private double jumpSpeed;		//using int for these type of variables is a bad idea
	private double xVel = 0;
	private double yVel = 0;
	private boolean airborne = true;
	private boolean alive = true;
	private boolean facingLeft = true;
	// images for different status
	private BufferedImage leftMonkey;
	private BufferedImage rightMonkey;
	private BufferedImage leftMonkeyWithAk;
	private BufferedImage rightMonkeyWithAk;
	private BufferedImage leftMonkeyWithPistol;
	private BufferedImage rightMonkeyWithPistol;
	// image for current status
	private BufferedImage MonkeyImage;
	
	// Constructor
	// constructor of monkey object
	/* Parameters
	 * xpos: x position of born place
	 * ypos: y position of born place
	 * screenWidth: screen width of JFrame
	 * screenHeight: screen height of JFrame
	 * speed: speed of the monkey
	 * gravity: the gravity acceleration
	 * jumpSpeed: the jump speed when it leaves the ground
	 * playerNumber: 1 or 2
	 */
	public monkey(int xpos, int ypos, int screenWidth, int screenHeight, double speed, double gravity, double jumpSpeed, int playerNumber) {
		this.rect = new Rectangle(xpos, ypos, 29, 30);
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.speed = speed;
		this.gravity = gravity;
		this.jumpSpeed = jumpSpeed;	
		// Get images
		try 
		{
			this.leftMonkey = ImageIO.read(new File("images/leftMonkey" + playerNumber + ".png"));
			this.rightMonkey = ImageIO.read(new File("images/rightMonkey" + playerNumber + ".png"));
			this.leftMonkeyWithAk = ImageIO.read(new File("images/leftMonkeyWithAk" + playerNumber + ".png"));
			this.rightMonkeyWithAk = ImageIO.read(new File("images/rightMonkeyWithAk" + playerNumber + ".png"));
			this.leftMonkeyWithPistol = ImageIO.read(new File("images/leftMonkeyWithPistol" + playerNumber + ".png"));
			this.rightMonkeyWithPistol = ImageIO.read(new File("images/rightMonkeyWithPistol" + playerNumber + ".png"));
		}
		catch(Exception e)
		{
		}
		if(playerNumber == 1) {
			MonkeyImage = rightMonkey;
			facingLeft = false;
		}
		else {
			MonkeyImage = leftMonkey;
		}
	}
	
	//getter
	// return current monkey status image
	public BufferedImage getMonkeyImage() {
		return MonkeyImage;
	}
	// return if the monkey is facing left
	public boolean getFacingLeft() {
		return facingLeft;
	}
	// return ak that the monkey is having
	public ak getAk() {
		return ak;
	}
	// return pistol that the monkey is having
	public pistol getPistol() {
		return pistol;
	}
	// return x position
	public int getXpos() {
		return rect.x;
	}
	// return y position
	public int getYpos() {
		return rect.y;
	}
	// return if the monkey has ak
	public boolean getHasAk() {
		return hasAK;
	}
	// return if the monkey has pistol
	public boolean getHasPistol() {
		return hasPistol;
	}
	// return hitbox
	public Rectangle getRec() {
		return rect;
	}
	// return if the monkey has armor
	public boolean getHasArmor() {
		return hasArmor;
	}
	// return if the monkey is alive
	public boolean getAlive() {
		return alive;
	}
	
	// move()
	// this method update the movement of the monkey
	public void move() {
		// left, right movements
		if(left)
			xVel = -speed;
		else if(right)
			xVel = speed;
		else
			xVel = 0;
		// in air and jump
		if(airborne) {
			yVel -= gravity;
		}else {
			if(up) {
				airborne = true;
				yVel = jumpSpeed;
			}
		}
		// update the position of hitbox
		rect.x += xVel;
		rect.y -= yVel;
		airborne = true;
		
	}
	
	// keepInBound()
	// This method keeps the monkey in bound
	public void keepInBound() {
		if(rect.x < 0) {
			rect.x = 0;
		}
		else if(rect.x > screenWidth - rect.width) {
			rect.x = screenWidth - rect.width;
		}
		
		if(rect.y < 0) {
			rect.y = 0;
			yVel = 0;
		}else if(rect.y > screenHeight - rect.height) {
			rect.y = screenHeight - rect.height;
			airborne = false;
			yVel = 0;
		}
	}
	
	// getShot()
	// this method is executed when the monkey gets shot
	// dead or alive depending on if the monkey has armor
	public void getShot() {
		if(hasArmor) {
			hasArmor = false;
		}
		// dead
		else {
			alive = false;
			speed = 0;
			jumpSpeed = 0;
			hasAK = false;
			hasPistol = false;
			try {
				// images of dead monkey
				MonkeyImage = ImageIO.read(new File("deadmonkey.png"));
				leftMonkey = MonkeyImage;
				rightMonkey = MonkeyImage;
				leftMonkeyWithAk = MonkeyImage;
				rightMonkeyWithAk = MonkeyImage;
				leftMonkeyWithPistol = MonkeyImage;
				rightMonkeyWithPistol = MonkeyImage;
			} catch (IOException e) {
			}
		}
	}
	
	// checkCollision()
	// Rectangle Wall: the wall that the method is checking
	// This method keeps the monkey from traveling through walls
	public void checkCollision(Rectangle wall) {
		//check if rect touches wall
		if(rect.intersects(wall)) {
			//stop the rect from moving
			double left1 = rect.getX();
			double right1 = rect.getX() + rect.getWidth();
			double top1 = rect.getY();
			double bottom1 = rect.getY() + rect.getHeight();
			double left2 = wall.getX();
			double right2 = wall.getX() + wall.getWidth();
			double top2 = wall.getY();
			double bottom2 = wall.getY() + wall.getHeight();
			
			if(right1 > left2 && 
			   left1 < left2 && 
			   right1 - left2 < bottom1 - top2 && 
			   right1 - left2 < bottom2 - top1)
	        {
	            //rect collides from left side of the wall
				rect.x = wall.x - rect.width;
	        }
	        else if(left1 < right2 &&
	        		right1 > right2 && 
	        		right2 - left1 < bottom1 - top2 && 
	        		right2 - left1 < bottom2 - top1)
	        {
	            //rect collides from right side of the wall
	        	rect.x = wall.x + wall.width;
	        }
	        else if(bottom1 > top2 && top1 < top2)
	        {
	            //rect collides from top side of the wall
	        	rect.y = wall.y - rect.height;
				airborne = false;
				yVel = 0;
	        }
	        else if(top1 < bottom2 && bottom1 > bottom2)
	        {
	            //rect collides from bottom side of the wall
	        	rect.y = wall.y + wall.height;
				yVel = 0;
	        }

		}
	}
	
	// checkCollision()
	// ak ak: the ak that the method is checking
	// This method checks if the monkey grabs the ak
	// return: boolean if the monkey is able to grab the ak
	public boolean checkCollision(ak ak) {
		if(!hasPistol && rect.intersects(ak.getRect())) {
			hasAK = true;
			ak.pick(this);
			this.ak = ak;
			// updates image
			leftMonkey = leftMonkeyWithAk;
			rightMonkey = rightMonkeyWithAk;
			if(left) {
				MonkeyImage = leftMonkeyWithAk;
			}
			else {
				MonkeyImage = rightMonkeyWithAk;
			}
			return true;
		}
		return false;
	}
	// checkCollision()
	// pistol pistol: the pistol that the method is checking
	// This method checks if the monkey grabs the pistol	
	// return: boolean if the monkey is able to grab the pistol
	public boolean checkCollision(pistol pistol) {
		if(!hasAK &&rect.intersects(pistol.getRect())) {
			hasPistol = true;
			pistol.pick(this);
			this.pistol = pistol;
			leftMonkey = leftMonkeyWithPistol;
			rightMonkey = rightMonkeyWithPistol;
			if(left) {
				MonkeyImage = leftMonkeyWithPistol;
			}
			else {
				MonkeyImage = rightMonkeyWithPistol;
			}
			return true;
		}
		return false;
	}
	// checkCollision()
	// armor armor: the armor that the method is checking
	// This method checks if the monkey grabs the armor	
	// return: boolean if the monkey is able to grab the armor
	public boolean checkCollision(armor armor) {
		if(rect.intersects(armor.getRect())) {
			hasArmor = true;
			return true;
		}
		return false;
	}
	
	// leftPressed()
	// This method modifies the status of the monkey when left is pressed
	public void leftPressed() {
		left = true;
		right = false;
		facingLeft = true;
		MonkeyImage = leftMonkey;
	}
	// rightPressed()
	// This method modifies the status of the monkey when right is pressed
	public void rightPressed() {
		right = true;
		left = false;
		facingLeft = false;
		MonkeyImage = rightMonkey;
	}
	// upPressed()
	// This method modifies the status of the monkey when up is pressed
	public void upPressed() {
		up = true;
	}
	// leftReleased()
	// This method modifies the status of the monkey when left is released
	public void leftReleased() {
		left = false;
	}
	// rightReleased()
	// This method modifies the status of the monkey when right is released
	public void rightReleased() {
		right = false;
	}
	// upReleased()
	// This method modifies the status of the monkey when up is released
	public void upReleased() {
		up = false;
	}

}
