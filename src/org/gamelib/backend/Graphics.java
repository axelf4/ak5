/**
 * 
 */
package org.gamelib.backend;

/**
 * A graphics context that allows rendering to the canvas or off-screen images.
 * @author pwnedary
 */
public interface Graphics {
	/**
	 * Sets the color to use when rendering to this context.
	 * @param color the color to use when rendering to this context
	 */
	public void setColor(Color color);

	/**
	 * Returns the color in use by this graphics context.
	 * @return the color in use by this graphics context
	 */
	public Color getColor();

	/** Begins drawing with this graphics context. */
	public void begin();

	/** Ends drawing with this graphics context. */
	public void end();

	/** Releases any system resources that are used. */
	public void dispose();

	/** Translates the origin to <i>x</i>,&nbsp;<i>y</i>,&nbsp;<i>z</i>. */
	public void translate(float x, float y, float z);

	/**
	 * Applies a scaling factor to everything drawn.
	 * @param sx The scaling factor on the x-axis
	 * @param sy The scaling factor on the y-axis
	 * @param sz The scaling factor on the z-axis
	 */
	public void scale(float sx, float sy, float sz);

	/**
	 * Applies a rotation to everything draw on the graphics context
	 * @param theta The angle (in degrees) to rotate by
	 */
	public void rotate(double theta);

	/**
	 * Draws an image to the canvas.
	 * @param img the image to draw
	 * @param dx1 the x coordinate of the start of the destination
	 * @param dy1 the y coordinate of the start of the destination
	 * @param dx2 the x coordinate of the end of the destination
	 * @param dy2 the y coordinate of the end of the destination
	 * @param sx1 the x coordinate of the start of the source
	 * @param sy1 the y coordinate of the start of the source
	 * @param sx2 the x coordinate of the end of the source
	 * @param sy2 the y coordinate of the end of the source
	 * @throws IllegalArgumentException if source coordinates are out of bounds
	 */
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2);

	/**
	 * Draws a line on the canvas in the current color.
	 * @param x1 The x coordinate of the start point
	 * @param y1 The y coordinate of the start point
	 * @param x2 The x coordinate of the end point
	 * @param y2 The y coordinate of the end point
	 */
	public void drawLine(int x1, int y1, int x2, int y2);

	/**
	 * Draws a rectangle to the canvas in the current color.
	 * @param x1 The x coordinate of the top left corner
	 * @param y1 The y coordinate of the top left corner
	 * @param width The width of the rectangle to draw
	 * @param height The height of the rectangle to draw
	 */
	public void drawRect(int x, int y, int width, int height);

	/**
	 * Fills a rectangle on the canvas in the current color.
	 * @param x1 The x coordinate of the top left corner
	 * @param y1 The y coordinate of the top left corner
	 * @param width The width of the rectangle to fill
	 * @param height The height of the rectangle to fill
	 */
	public void fillRect(int x, int y, int width, int height);

	public void drawCube(int x, int y, int z, int width, int height, int depth);

	/** Clears the screen in the desired color. */
	public void clear();

	/** Pushes the current coordinate transforms to be available from {@link #pop()}. */
	public void push();

	/** Retrieves and sets the coordinate transform to the last {@link #push()} call. */
	public void pop();

}
