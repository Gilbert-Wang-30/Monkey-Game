// Gilbert Wang
// ak
// This class is for ak object
// click to shoot, has 45 bullets
// January 28th, 2023
import java.awt.Rectangle;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// class
public class ak {
	// Private variables
	// hitbox
	private Rectangle rect;
	private boolean shooting = false;
	private int remainingBullet = 45;
	private long lastShot = 0;
	private BufferedImage leftAk;
	private BufferedImage rightAk;
	// the player who's holding
	private monkey player;
	
	// Constructor 
	// constructor for ak object
	// int x: born x position
	// int y: born y position
	public ak(int x, int y) {
		// hitbox
		this.rect = new Rectangle(x, y, 32, 11);
		try 
		{
			// grab images
			this.leftAk = ImageIO.read(new File("images/akLeft.png"));
			this.rightAk = ImageIO.read(new File("images/akRight.png"));
		}
		catch(Exception e)
		{
			
		}
	}
	
	// checkShooting()
	// This method checks if the ak is shooting and returns the bullet it shots
	public bullet checkShooting() {
		// make sure the fastest speed of shooting speed is 100 ms
		if(shooting&&System.currentTimeMillis()>=(lastShot+100)&&remainingBullet>0) {
			remainingBullet--;
			lastShot = System.currentTimeMillis();
			if(player.getFacingLeft()) {
				return new bullet(true, player.getXpos()-5, player.getYpos()+14);
			} 
			else{
				return new bullet(false, player.getXpos()+30, player.getYpos()+14);			
			}			
		}
		return null;
	}
	
	// pick()
		// this method assign the ak who is holding it
		// monkey player: the player who is holding it
	public void pick(monkey player) {
		this.player = player;
	}
	
	// Setter
	// set if it is shooting
	public void setShooting(boolean Shooting) {
		shooting = Shooting;
	}
	
	// Getter
	// get the gun's image facing left
	public BufferedImage getLeftImage() {
		return leftAk;
	}
	// get the gun's image facing right
	public BufferedImage getRightImage() {
		return rightAk;
	}
	// get the gun's x position
	public int getXpos() {
		return rect.x;
	}
	// get the gun's y position
	public int getYpos() {
		return rect.y;
	}
	// get the gun's hit box
	public Rectangle getRect() {
		return rect;
	}
	// get the gun's remaining bullet
	public int getRemainBullet() {
		return remainingBullet;
	}
}
