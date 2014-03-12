/**
 * 
 */
package org.gamelib.util;

import org.gamelib.Drawable;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.graphics.GL10;
import org.gamelib.util.geom.Line;
import org.gamelib.util.geom.Point;

/** @author pwnedary */
public class NinePatch implements Drawable {
	/* Indices for {@link #NinePatch(TextureRegion...)} constructor, alphabetically first in javadoc. */
	public static final int TOP_LEFT = 0;
	public static final int TOP_CENTER = 1;
	public static final int TOP_RIGHT = 2;
	public static final int MIDDLE_LEFT = 3;
	public static final int MIDDLE_CENTER = 4;
	public static final int MIDDLE_RIGHT = 5;
	public static final int BOTTOM_LEFT = 6;
	public static final int BOTTOM_CENTER = 7;
	public static final int BOTTOM_RIGHT = 8;
	Image[] patches = new Image[9];

	Image rawImage;
	/* Dimensions except middle patch. */
	int minWidth, minHeight;

	public NinePatch(Image image, int left, int right, int top, int bottom) {
		this(image);
		patches[TOP_LEFT] = image.region(0, 0, left, top);
	}

	public NinePatch(Image image) {
		if (image.getWidth() < 3 || image.getHeight() < 3) throw new IllegalArgumentException("Image must be atleast 3x3p large.");
		rawImage = image.region(1, 1, image.getWidth() - 1, image.getHeight() - 1);

		Line horizontalStretch = parseInterval(image, IntervalType.HORIZONTAL_STRETCH);
		minWidth = rawImage.getWidth() - (int) horizontalStretch.length();
		Line verticalStretch = parseInterval(image, IntervalType.VERTICAL_STRETCH);
		minHeight = rawImage.getHeight() - (int) verticalStretch.length();
		System.out.println("hor: " + horizontalStretch.length() + " ver: " + verticalStretch.length());
		patches[TOP_LEFT] = rawImage.region(0, 0, horizontalStretch.getX1() - 1, verticalStretch.getY1() - 1);
		patches[TOP_CENTER] = rawImage.region(horizontalStretch.getX1(), 0, horizontalStretch.getX2() - horizontalStretch.getX1(), verticalStretch.getY1() - 1);
		patches[TOP_RIGHT] = rawImage.region(horizontalStretch.getX2() + 1, 0, rawImage.getWidth() - horizontalStretch.getX2(), verticalStretch.getY1() - 1);
	}

	Line parseInterval(Image image, IntervalType intervalType) {
		final int length = intervalType == IntervalType.HORIZONTAL_STRETCH || intervalType == IntervalType.HORIZONTAL_CONTENT ? image.getWidth() : image.getHeight();
		int[][] pixels = image.getPixels();
		int x1 = -1, y1 = -1;
		Point last = null;
		for (int i = 1; i < length; i++) {
			final Point pixel;
			switch (intervalType) {
			case HORIZONTAL_STRETCH:
				pixel = new Point(i, 0);
				break;
			case VERTICAL_STRETCH:
				pixel = new Point(0, i);
				break;
			case HORIZONTAL_CONTENT:
				pixel = new Point(i, image.getHeight() - 1);
				break;
			case VERTICAL_CONTENT:
				pixel = new Point(image.getHeight() - 1, i);
				break;
			default:
				pixel = null;
				break;
			}
			System.out.println("pixel: " + pixels[pixel.getX()][pixel.getY()] + " x: " + pixel.getX() + " y: " + pixel.getY());
			System.out.println(java.awt.Color.BLACK.getRGB());
			if (pixels[pixel.getX()][pixel.getY()] == java.awt.Color.BLACK.getRGB()) {
				if (x1 == -1 && y1 == -1) {
					x1 = pixel.getX();
					y1 = pixel.getY();
				}
			} else if ((x1 != -1 && y1 != -1) || i + 1 >= length) return new Line(x1, y1, last.getX(), last.getY());
			last = pixel;
		}
		return null;
	}

	@Override
	public void draw(GL10 gl, float delta) {}

	@Override
	public void draw(Graphics g, float delta) {}

	public void draw(Graphics g, int x, int y, int width, int height) {
		int dx = x;
		int dy = y - height;
		int w, h;
		int stretchableWidth = width - minWidth, stretchableHeight = height - minHeight;
		for (int i = 0; i < 3; i++) { // 9 instead of 3
			Image patch = patches[i];
			if (i % 3 == 0) { // new row
				dx = x;
				dy += height;
			}

			if (i == TOP_CENTER || i == MIDDLE_CENTER || i == BOTTOM_CENTER) w = stretchableWidth; // center column patches
			else w = patch.getWidth();

			if (i / 3 == 1) h = stretchableHeight; // middle row patches
			else h = patch.getHeight();

			g.drawImage(patch, dx, dy, dx + w, dy + h, 0, 0, patch.getWidth(), patch.getHeight());

			dx += w;
		}
	}

	private class Interval {

	}

	private enum IntervalType {
		HORIZONTAL_STRETCH, VERTICAL_STRETCH, HORIZONTAL_CONTENT, VERTICAL_CONTENT;
	}
}
