/**
 * 
 */
package org.gamelib.util;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.gamelib.Game;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.util.geom.Rectangle;

/**
 * @author Axel
 */
public class TrueTypeFont implements Font {

	/** Array that holds necessary information about the font characters positions */
	private Rectangle[] charArray = new Rectangle[256];
	/** Map of user defined font characters (Character <-> Rectangle) */
	private Map<Character, Rectangle> customChars = new HashMap<>();

	/** Boolean flag on whether AntiAliasing is enabled or not */
	private boolean antiAlias;

	/** Font's size */
	private int fontSize = 0;

	/** Font's height */
	private int fontHeight = 0;

	/** Texture used to cache the font 0-255 characters */
	// private int fontTextureID;
	/** Image to cache the font image */
	public Image fontImage;

	/** Default font texture width */
	private int textureWidth = 512;

	/** Default font texture height */
	private int textureHeight = 512;

	/** A reference to Java's AWT Font that we create our font texture from */
	private java.awt.Font font;

	/** The font metrics for our Java AWT font */
	private FontMetrics fontMetrics;

	private org.gamelib.backend.Color fontColor;

	private int correctL = 9, correctR = 8;
	int format = ALIGN_LEFT;

	public TrueTypeFont(java.awt.Font font, boolean antiAlias, char[] additionalChars, org.gamelib.backend.Color fontColor) {
		this.font = font;
		this.fontSize = font.getSize() + 3;
		this.antiAlias = antiAlias;
		this.fontColor = fontColor;

		createSet(additionalChars);

		fontHeight -= 1;
		if (fontHeight <= 0) fontHeight = 1;
	}

	public TrueTypeFont(java.awt.Font font) {
		this(font, true, null, org.gamelib.backend.Color.BLACK);
	}

	public TrueTypeFont() {
		// this(new java.awt.Font(null, Font.PLAIN, 15), true, null, org.gamelib.util.Color.BLACK);
		// this(getFont(new File("org/gamelib/util/arial.ttf"), PLAIN, DEFAULT_SIZE));
		this(getFont(new File("arial.ttf"), PLAIN, DEFAULT_SIZE));
	}

	public void setCorrection(boolean on) {
		if (on) {
			correctL = 2;
			correctR = 1;
		} else {
			correctL = 0;
			correctR = 0;
		}
	}

	private BufferedImage getFontImage(char ch) {
		// Create a temporary image to extract the character's size
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		if (antiAlias == true) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch) + 8;
		if (charwidth <= 0) charwidth = 7;
		int charheight = fontMetrics.getHeight() + 3;
		if (charheight <= 0) charheight = fontSize;

		// Create another image holding the character we are creating
		BufferedImage fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		if (antiAlias == true) gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gt.setFont(font);

		gt.setColor(fontColor.toAWT());
		int charx = 3;
		int chary = 1;
		gt.drawString(String.valueOf(ch), (charx), (chary) + fontMetrics.getAscent());

		return fontImage;

	}

	private void createSet(char[] customCharsArray) {
		// If there are custom chars then I expand the font texture twice
		if (customCharsArray != null && customCharsArray.length > 0) textureWidth *= 2;

		// In any case this should be done in other way. Texture with size 512x512 can maintain only 256 characters with resolution of 32x32. The texture size should be calculated dynamically by looking at character sizes.
		try {
			BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) imgTemp.getGraphics();

			g.setColor(new Color(0, 0, 0, 1));
			g.fillRect(0, 0, textureWidth, textureHeight);

			int rowHeight = 0, positionX = 0, positionY = 0;
			int customCharsLength = (customCharsArray != null) ? customCharsArray.length : 0;

			for (int i = 0; i < 256 + customCharsLength; i++) {
				// get 0-255 characters and then custom characters
				char ch = (i < 256) ? (char) i : customCharsArray[i - 256];

				BufferedImage fontImage = getFontImage(ch);

				Rectangle newIntObject = new Rectangle();

				newIntObject.setWidth(fontImage.getWidth());
				newIntObject.setHeight(fontImage.getHeight());

				if (positionX + newIntObject.getWidth() >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
				}

				newIntObject.setX(positionX);
				newIntObject.setY(positionY);

				if (newIntObject.getHeight() > fontHeight) fontHeight = newIntObject.getHeight();
				if (newIntObject.getHeight() > rowHeight) rowHeight = newIntObject.getHeight();

				// Draw it here
				g.drawImage(fontImage, positionX, positionY, null);

				positionX += newIntObject.getWidth();

				if (i < 256) charArray[i] = newIntObject;// standard characters
				else customChars.put(new Character(ch), newIntObject); // custom characters

				fontImage = null;
			}

			fontImage = Game.getBackend().getImage(imgTemp);
		} catch (Exception e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#drawString(org.gamelib.backend.Graphics, java.lang.String, int, int)
	 */
	@Override
	public void drawString(Graphics g, String str, int x, int y) {
		Rectangle intObject = null;
		int charCurrent;

		int totalwidth = 0;
		int startIndex = 0, endIndex = str.length() - 1;
		int i = startIndex, d, c;
		float startY = 0;

		float scaleX = 1.0f, scaleY = 1.0f;

		switch (format) {
		case ALIGN_RIGHT: {
			d = -1;
			c = correctR;

			while (i < endIndex) {
				if (str.charAt(i) == '\n') startY += fontHeight;
				i++;
			}
			break;
		}
		case ALIGN_CENTER: {
			for (int l = startIndex; l <= endIndex; l++) {
				charCurrent = str.charAt(l);
				if (charCurrent == '\n') break;
				if (charCurrent < 256) intObject = charArray[charCurrent];
				else intObject = (Rectangle) customChars.get(new Character((char) charCurrent));
				totalwidth += intObject.getWidth() - correctL;
			}
			totalwidth /= -2;
		}
		case ALIGN_LEFT:
		default: {
			d = 1;
			c = correctL;
			break;
		}
		}

		while (i >= startIndex && i <= endIndex) {
			charCurrent = str.charAt(i);
			if (charCurrent < 256) {
				intObject = charArray[charCurrent];
			} else {
				intObject = (Rectangle) customChars.get(Character.valueOf((char) charCurrent));
			}

			if (intObject != null) {
				if (d < 0) totalwidth += (intObject.getWidth() - c) * d;
				if (charCurrent == '\n') {
					startY += fontHeight * d;
					totalwidth = 0;
					if (format == ALIGN_CENTER) {
						for (int l = i + 1; l <= endIndex; l++) {
							charCurrent = str.charAt(l);
							if (charCurrent == '\n') break;
							if (charCurrent < 256) intObject = charArray[charCurrent];
							else intObject = (Rectangle) customChars.get(new Character((char) charCurrent));
							totalwidth += intObject.getWidth() - correctL;
						}
						totalwidth /= -2;
					}
					// if center get next lines total width/2;
				} else {
					g.drawImage(fontImage, (int) (totalwidth * scaleX + x), (int) (startY * scaleY + y), (int) ((totalwidth + intObject.getWidth()) * scaleX + x), (int) ((startY + intObject.getHeight()) * scaleY + y), intObject.getX(), intObject.getY(), intObject.getX() + intObject.getWidth(), intObject.getY() + intObject.getHeight());
					if (d > 0) totalwidth += (intObject.getWidth() - c) * d;
				}
				i += d;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#getWidth(java.lang.String)
	 */
	@Override
	public int getWidth(String str) {
		int totalwidth = 0;
		Rectangle intObject = null;
		int currentChar = 0;
		for (int i = 0; i < str.length(); i++) {
			currentChar = str.charAt(i);
			if (currentChar < 256) intObject = charArray[currentChar];
			else intObject = (Rectangle) customChars.get(new Character((char) currentChar));

			if (intObject != null) totalwidth += intObject.getWidth() - correctL;
		}
		return totalwidth;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.util.Font#getHeight()
	 */
	@Override
	public int getHeight() {
		return fontHeight;
	}

	public static boolean isSupported(String fontname) {
		java.awt.Font font[] = getFonts();
		for (int i = font.length - 1; i >= 0; i--)
			if (font[i].getName().equalsIgnoreCase(fontname)) return true;
		return false;
	}

	public static java.awt.Font[] getFonts() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
	}

	public static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}

	public static java.awt.Font getFont(File file, int style, int size) {
		// try (InputStream stream = Game.getBackend().getResourceAsStream(file.getPath())) {
		try (InputStream stream = TrueTypeFont.class.getResourceAsStream(file.getPath())) {
			Map<TextAttribute, Object> fontAttributes = new HashMap<>();
			if ((style & BOLD) == BOLD) fontAttributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
			if ((style & ITALIC) == ITALIC) fontAttributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
			if ((style & UNDERLINE) == UNDERLINE) fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			if ((style & STRIKETHROUGH) == STRIKETHROUGH) fontAttributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
			fontAttributes.put(TextAttribute.SIZE, new Float(size)); // add size attribute
			java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, stream).deriveFont(fontAttributes);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
			return font;
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
