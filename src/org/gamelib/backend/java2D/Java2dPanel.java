/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

import org.gamelib.Game;

/**
 * @author pwnedary
 *
 */
public class Java2dPanel extends JPanel {
	
	private VolatileImage volatileImage;
	public Graphics2D g2d; // graphics2d
	public float delta;

	/**
	 * 
	 */
	public Java2dPanel() {
		setIgnoreRepaint(true);
		setRequestFocusEnabled(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			do {
				if (volatileImage == null || volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
					volatileImage = createVolatileImage(getWidth(), getHeight());
				}
				g2d = volatileImage.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				AffineTransform affineTransform = g2d.getTransform();
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

				Game.getInstance().screen.drawHandlers(new Java2DGraphics(g2d), delta);
				// Game.getInstance().screen.drawHandlers(new Java2DGraphics(g2d), g2d);

				g2d.setTransform(affineTransform);
				g2d.dispose();
			} while (volatileImage.contentsLost());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			g.drawImage(volatileImage, 0, 0, getWidth(), getHeight(), this);
			volatileImage.flush();
			g.dispose();
		}

		// Tell the System to do the Drawing now, otherwise it can take a few
		// extra ms until
		// Drawing is done which looks very jerky
		Toolkit.getDefaultToolkit().sync();
	}

}
