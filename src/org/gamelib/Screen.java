/**
 * 
 */
package org.gamelib;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;
import java.io.IOException;

import org.gamelib.Handler.Event;
import org.gamelib.backend.java2D.AWTFont;
import org.gamelib.util.Color;
import org.gamelib.util.Font;

/**
 * @author Axel
 * 
 */
public class Screen { // JPanel Canvas

	private AffineTransform affineTransform;
	public float interpolation;
	// private BufferedImage bufferedImage;
	private int width, height;

	public VolatileImage volatileImage;

	public int fps;
	private Font f;

	public Screen() {
		// setIgnoreRepaint(true);
	}
	
	public Screen(DisplayMode mode) {
		this.width = mode.getWidth();
		this.height = mode.getHeight();
		
		f = new AWTFont(new java.awt.Font(null, Font.PLAIN, 12));
	}

	/**
	 * 
	 */
	/* public Screen(GameClass game) { // TODO Auto-generated constructor stub
	 * super(); this.game = game; setIgnoreRepaint(true); // bufferedImage =
	 * (BufferedImage) createImage(getWidth(), getHeight()); //
	 * Registry.getInstance().registerHandler(this); // volatileImage =
	 * createVolatileImage(getWidth(), getHeight()); } */

	/*@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			do {
				if (volatileImage == null || volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
					volatileImage = createVolatileImage(getWidth(), getHeight());
				}
				Graphics2D graphics2d = volatileImage.createGraphics();
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				AffineTransform affineTransform = graphics2d.getTransform();
				graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

				// Draw
				graphics2d.setColor(Color.WHITE); // Color.WHITE
				graphics2d.fillRect(0, 0, getWidth(), getHeight());
				graphics2d.setColor(Color.BLACK);
				// Registry.getInstance().invokeHandlers(HandlerType.RENDER,
				// graphics2d, interpolation);
				Registry.instance().invokeHandlers(new Event.Draw(graphics2d, interpolation));
				graphics2d.setColor(Color.RED);
				graphics2d.drawString("FPS: " + fps, 500, 10); // 5 10

				graphics2d.setTransform(affineTransform);
				graphics2d.dispose();
			} while (volatileImage.contentsLost());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), this);
			g.drawImage(volatileImage, 0, 0, getWidth(), getHeight(), this);
			volatileImage.flush();
			g.dispose();
		}

		// Tell the System to do the Drawing now, otherwise it can take a few
		// extra ms until
		// Drawing is done which looks very jerky
		Toolkit.getDefaultToolkit().sync();
	}*/
	
	public void drawHandlers(org.gamelib.backend.Graphics g, float delta) {
		g.setColor(Color.WHITE); // Color.WHITE
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		// Registry.getInstance().invokeHandlers(HandlerType.RENDER,
		// graphics2d, interpolation);
		Registry.instance().dispatch(new Event.Draw(g, delta));
		g.setColor(Color.RED);
		// g.drawString("FPS: " + fps, 500, 10); // 5 10
		fps = Game.getInstance().getLoop().getFPS();
		String str = "FPS: " + fps;
		f.drawString(g, str, getWidth() - f.getWidth(str) - 20, 10);
	}

	/**
	 * Loads a <code>Image</code> from a file, taking advantage of JFrame- or
	 * JApplets functions.
	 * 
	 * @param pathname
	 * @return the loaded image
	 * @throws IOException
	 */
	/* @Deprecated public BufferedImage loadImage(String pathname) throws
	 * IOException { Container container = game.container; if (container
	 * instanceof JFrame) return ImageIO.read(new File(pathname)); else if
	 * (container instanceof JApplet) { URL url = ((JApplet)
	 * container).getCodeBase(); File f; try { f = new File(url.toURI()); }
	 * catch (URISyntaxException e) { f = new File(url.getPath()); } return
	 * (BufferedImage) ((JApplet) container).getImage(new URL("file:/" +
	 * f.getParent() + "/" + pathname)); } return null; } */

	@Deprecated
	public void reset(Graphics2D graphics2d) {
		graphics2d.setTransform(affineTransform);
	}

	/* @Override
	 * 
	 * @RegisterHandler() public void repaint() { // TODO Auto-generated method
	 * stub super.repaint(); } */
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
