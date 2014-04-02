/**
 * 
 */
package org.gamelib.util;

import org.gamelib.graphics.Batch;

/** TODO add bitmap fonts
 * 
 * @author pwnedary */
public interface Font {
	/** The plain style constant. */
	static final int PLAIN = 0;
	/** The bold style constant. */
	static final int BOLD = 1;
	/** The italicized style constant. */
	static final int ITALIC = 2;
	/** The underline style constant. */
	static final int UNDERLINE = 4;
	/** The strikethrough style constant. */
	static final int STRIKETHROUGH = 8;

	/** The size to use if not specified. */
	static final int DEFAULT_SIZE = 15;

	static final int ALIGN_LEFT = 0;
	static final int ALIGN_RIGHT = 1;
	static final int ALIGN_CENTER = 2;

	void drawString(Batch batch, String str, int x, int y);

	int getWidth(String string);

	int getHeight();

	int visibleChars(String string, int width);

	static abstract class FontImpl implements Font {
		@Override
		public int visibleChars(String string, int width) {
			int i = 0;
			for (int totalWidth = 0; i < string.length() && (totalWidth += getWidth(String.valueOf(string.charAt(i)))) <= width; i++)
				;
			return i;
		}
	}

}
