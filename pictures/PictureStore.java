package pictures;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.io.File;

import javax.imageio.ImageIO;

public class PictureStore {
	
	// enemies
	public static BufferedImage angryStrawberry;
	public static BufferedImage normalStrawberry;
	public static BufferedImage angryCarrot;
	public static BufferedImage normalCarrot;
	public static BufferedImage angryOnion;
	public static BufferedImage normalOnion;
	public static BufferedImage angryRadish;
	public static BufferedImage normalRadish;

	// shooter
	public static BufferedImage tLeftCookie; // top left
	public static BufferedImage tRightCookie; // top right
	public static BufferedImage bLeftCookie; // bottom left
	public static BufferedImage bRightCookie; // bottom right
	public static BufferedImage fullCookie; // extra shooter

	// other
	public static BufferedImage bullet;
	public static BufferedImage bomb;
	public static BufferedImage speedPower;
	public static BufferedImage bulletPower;
	public static BufferedImage shieldPower;
	public static BufferedImage shooterPower;

	// background
	public static BufferedImage background;


	public static int[] playerXY = {60, 60};
	public static int[] extraShooterXY = {30, 30};
	public static int[] enemyXY = {45, 45};
	public static int[] bulletXY = {20, 20};
	public static int[] bombXY = {10, 20};
	public static int[] powerXY = {30, 30};
	
	
	// executed before main method
	static {
		angryStrawberry = readImage("pictures/enemies/angryStrawberry.png", enemyXY[0], enemyXY[1]);
		normalStrawberry = readImage("pictures/enemies/normalStrawberry.png", enemyXY[0], enemyXY[1]);
		angryCarrot = readImage("pictures/enemies/angryCarrot.png", enemyXY[0], enemyXY[1]);
		normalCarrot = readImage("pictures/enemies/normalCarrot.png", enemyXY[0], enemyXY[1]);
		angryOnion = readImage("pictures/enemies/angryOnion.png", enemyXY[0], enemyXY[1]);
		normalOnion = readImage("pictures/enemies/normalOnion.png", enemyXY[0], enemyXY[1]);
		angryRadish = readImage("pictures/enemies/angryRadish.png", enemyXY[0], enemyXY[1]);
		normalRadish = readImage("pictures/enemies/normalRadish.png", enemyXY[0], enemyXY[1]);
		tLeftCookie = readImage("pictures/shooter/tLeftCookie.png", playerXY[0], playerXY[1]);
		tRightCookie = readImage("pictures/shooter/tRightCookie.png", playerXY[0], playerXY[1]);
		bLeftCookie = readImage("pictures/shooter/bLeftCookie.png", playerXY[0], playerXY[1]);
		bRightCookie = readImage("pictures/shooter/bRightCookie.png", playerXY[0], playerXY[1]);
		fullCookie = readImage("pictures/shooter/fullCookie.png", extraShooterXY[0], extraShooterXY[1]);
		bomb = readImage("pictures/other/bomb.png", bombXY[0], bombXY[1]);
		bullet = readImage("pictures/other/bullet.png", bulletXY[0], bulletXY[1]);
		speedPower = readImage("pictures/other/speedPower.png", powerXY[0], powerXY[1]);
		bulletPower = readImage("pictures/other/bulletPower.png", powerXY[0], powerXY[1]);
		shieldPower = readImage("pictures/other/shieldPower.png", powerXY[0], powerXY[1]);
		shooterPower = readImage("pictures/other/shooterPower.png", powerXY[0], powerXY[1]);
		background = readImage("pictures/other/background.png", 750, 500);
	}

	// reads from file and scales to width & height
	public static BufferedImage readImage(String path, int width, int height) {
		try {
			BufferedImage originalImage = ImageIO.read(new File(path));
			Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = resizedImage.createGraphics();
			g2.drawImage(tmp, 0, 0, null);
			g2.dispose();
			return resizedImage;
		} catch (Exception e) {
			System.out.println("Image file load error");
			System.out.println("e: " + e.getMessage());
		}

		return null;
	}

}
