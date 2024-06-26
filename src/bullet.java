// Gilbert Wang
// pistol
// This class is for bullet object
// January 28th, 2023
import java.awt.Rectangle;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class bullet {
	// private variables
	private Rectangle rect;
	private boolean towardsleft;
	private double speed = 20;
	private BufferedImage Bullet;
	
	// Constructor:
	// boolean towardsleft: if the bullet is shooting towards left
	// int x: born x position
	// int y: born y position
	public bullet(boolean towardsleft, int x, int y) {
		this.towardsleft = towardsleft;
		this.rect = new Rectangle(x, y, 5, 3);
		if(towardsleft) {
			try 
			{
				this.Bullet = ImageIO.read(new File("images/leftBullet.png"));
			}
			catch(Exception e)
			{
			}
		}
		else {
			try 
			{
				this.Bullet = ImageIO.read(new File("images/rightBullet.png"));
			}
			catch(Exception e)
			{
			}
		}
	}
	
	// move()
	// this method modifies the poistion of the bullet everytime frame
	public void move() {
		if(towardsleft) {
			rect.x-=speed;
		}
		else {
			rect.x+=speed;
		}
	}
	
	// checkShot(monkey player)
	// this method checks if the player is shot
	// monkey player: the player we are checking
	// boolean: if the monkey is shot
	public boolean checkShot(monkey player) {
		return rect.intersects(player.getRec());
	}
	
	// Getter
	// the bullet image
	public BufferedImage getImage() {
		return Bullet;
	}
	// get the x position
	public int getXpos() {
		return rect.x;
	}
	// get the y position
	public int getYpos() {
		return rect.y;
	}
	
	// checkCollision(Rectangle[] walls)
	// this method checks if the bullet hit walls
	// monkey player: the player we are checking
	// boolean: if the bullet shot the wall
	public boolean checkCollision(Rectangle[] walls) {
		if(rect.x < 0) {
			return false;
		}
		else if(rect.x > 900 - rect.width) {
			return false;
		}
		for(int i = 0; i<walls.length; i++) {
			if(this.rect.intersects(walls[i])) {
				return false;
			}
		}
		return true;
	}
	
}
