package pictures;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.io.File;

import javax.imageio.ImageIO;

public class PictureStore {
	
	public static BufferedImage angryStrawberry;
	public static BufferedImage normalStrawberry;
	public static BufferedImage angryCarrot;
	public static BufferedImage normalCarrot;
	public static BufferedImage angryOnion;
	public static BufferedImage normalOnion;
	public static BufferedImage tLeftCookie; // top left
	public static BufferedImage tRightCookie; // top right
	public static BufferedImage bLeftCookie; // bottom left
	public static BufferedImage bRightCookie; // bottom right


	public static int[] playerXY = {40, 40};
	public static int[] enemyXY = {40, 40};
	
	
	// executed before main method
	static {
		String cwd = System.getProperty("user.dir");
		System.out.println("cwd: " + cwd);
		angryStrawberry = readImage("pictures/enemies/angryStrawberry.png", enemyXY[0], enemyXY[1]);
		normalStrawberry = readImage("pictures/enemies/normalStrawberry.png", enemyXY[0], enemyXY[1]);
		angryCarrot = readImage("pictures/enemies/angryCarrot.png", enemyXY[0], enemyXY[1]);
		normalCarrot = readImage("pictures/enemies/normalCarrot.png", enemyXY[0], enemyXY[1]);
		angryOnion = readImage("pictures/enemies/angryOnion.png", enemyXY[0], enemyXY[1]);
		normalOnion = readImage("pictures/enemies/normalOnion.png", enemyXY[0], enemyXY[1]);
		tLeftCookie = readImage("pictures/shooter/tLeftCookie.png", playerXY[0], playerXY[1]);
		tRightCookie = readImage("pictures/shooter/tRightCookie.png", playerXY[0], playerXY[1]);
		bLeftCookie = readImage("pictures/shooter/bLeftCookie.png", playerXY[0], playerXY[1]);
		bRightCookie = readImage("pictures/shooter/bRightCookie.png", playerXY[0], playerXY[1]);
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
