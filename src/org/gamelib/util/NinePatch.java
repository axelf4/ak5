/**
 * 
 */
package org.gamelib.util;

import java.awt.geom.Line2D;

import org.gamelib.Drawable;
import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * @author pwnedary
 */
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

	public NinePatch(Image image, int left, int right, int top, int bottom) {
		this(image);
		patches[TOP_LEFT] = image.region(0, 0, left, top);
	}

	public NinePatch(Image image) {
		if (image.getWidth() < 3 || image.getHeight() < 3) throw new IllegalArgumentException("Image must be atleast 3x3p large.");
		rawImage = image.region(1, 1, image.getWidth() - 1, image.getHeight() - 1);
	}

	void parseInterval(Image image, IntervalType intervalType) {
		final int length = intervalType == IntervalType.HORIZONTAL_STRETCH || intervalType == IntervalType.HORIZONTAL_CONTENT ? image.getWidth() : image.getHeight();
		int[][] pixels = image.getPixels();
		Line2D.Float interval = new Line2D.Float();
		float lastX, lastY;
		for (int i = 1; i < length; i++) {
			final int argb;
			switch (intervalType) {
			case HORIZONTAL_STRETCH:
				argb = pixels[i][0];
				break;
			case VERTICAL_STRETCH:
				argb = pixels[0][i];
				break;
			case HORIZONTAL_CONTENT:
				argb = pixels[i][image.getHeight() - 1];
				break;
			case VERTICAL_CONTENT:
				argb = pixels[image.getHeight() - 1][i];
				break;
			default:
				argb = 0;
				break;
			}
			boolean black = argb == java.awt.Color.BLACK.getRGB();
			if (black) {
				switch (intervalType) {
				case HORIZONTAL_STRETCH:
					break;
				case VERTICAL_STRETCH:
					break;
				case HORIZONTAL_CONTENT:
					break;
				case VERTICAL_CONTENT:
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void draw(Graphics g, float delta) {}

	private class Interval {

	}

	private enum IntervalType {
		HORIZONTAL_STRETCH, VERTICAL_STRETCH, HORIZONTAL_CONTENT, VERTICAL_CONTENT;
	}
}
