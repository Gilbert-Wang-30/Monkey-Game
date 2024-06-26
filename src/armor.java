// Gilbert Wang
// armor
// This class is for armor object
// protects the player once
// January 28th, 2023
import java.awt.Rectangle;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// Class
public class armor {
	// private variables
	// hitbox
	private Rectangle rect;
	// image
	private BufferedImage armorImage;
	
	// Constructor 
		// constructor for armor object
		// int x: born x position
		// int y: born y position
	public armor(int x, int y) {
		this.rect = new Rectangle(x, y, 10, 10);
		try 
		{
			this.armorImage = ImageIO.read(new File("images/helmet.png"));
		}
		catch(Exception e)
		{
			System.out.println("catch");
		}
	}
	
	// Getters
	// get hitbox
	public Rectangle getRect() {
		return rect;
	}
	// get x position
	public int getXpos() {
		return rect.x;
	}
	// get y position
	public int getYpos() {
		return rect.y;
	}
	// get image
	public BufferedImage getImage() {
		return armorImage;
	}
}
