// Gilbert Wang
// pistol
// This class is for pistol object
// click to shoot, has 20 bullets
// January 28th, 2023

import java.awt.Rectangle;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// class
public class pistol {
	// hitbox
	private Rectangle rect;
	private boolean shooting = false;
	private boolean hasShot = false;
	private int remainingBullet = 20;
	private long lastShot = 0;
	private BufferedImage leftPistol;
	private BufferedImage rightPistol;
	// the player who's holding
	private monkey player;
	
	// Constructor 
	// constructor for pistol object
	// int x: born x position
	// int y: born y position
	public pistol(int x, int y) {
		// hitbox
		this.rect = new Rectangle(x, y, 11, 7);
		try 
		{
			// grab images
			this.leftPistol = ImageIO.read(new File("images/pistolLeft.png"));
			this.rightPistol = ImageIO.read(new File("images/pistolRight.png"));
		}
		catch(Exception e)
		{
			
		}
	}
	// checkShooting()
	// This method checks if the pistol is shooting and returns the bullet it shots
	public bullet checkShooting() {
		if(shooting && !hasShot) {
			// make sure the fastest speed of shooting speed is 200 ms
			if(System.currentTimeMillis()>=(lastShot+200)&&remainingBullet>0) {
				remainingBullet--;
				lastShot = System.currentTimeMillis();
				if(player.getFacingLeft()) {
					shooting = false;
					hasShot = true;
					return new bullet(true, player.getXpos()-5, player.getYpos()+14);
				} 
				else{
					shooting = false;
					hasShot = true;
					return new bullet(false, player.getXpos()+30, player.getYpos()+14);			
				}
			}
		}
		return null;
	}
	
	// pick()
	// this method assign the pistol who is holding it
	// monkey player: the player who is holding it
	public void pick(monkey player) {
		this.player = player;
	}
	
	// Setter
	// set if it is shooting
	public void setShooting(boolean Shooting) {
		shooting = Shooting;
	}
	// set if it has already shot
	public void setHasShot() {
		hasShot = false;
	}
	
	// Getter
	// get the gun's image facing left
	public BufferedImage getLeftImage() {
		return leftPistol;
	}
	// get the gun's image facing right
	public BufferedImage getRightImage() {
		return rightPistol;
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
