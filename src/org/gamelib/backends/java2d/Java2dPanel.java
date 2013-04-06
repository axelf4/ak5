/**
 * 
 */
package org.gamelib.backends.java2d;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

import org.gamelib.Game;
import org.gamelib.HandlerRegistry;
import org.gamelib.Handler.Event;

/**
 * @author pwnedary
 *
 */
public class Java2dPanel extends JPanel {
	
	private VolatileImage volatileImage;
	public Graphics2D graphics2d; // g2d

	/**
	 * 
	 */
	public Java2dPanel() {
		setIgnoreRepaint(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			do {
				if (volatileImage == null || volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) {
					volatileImage = createVolatileImage(getWidth(), getHeight());
				}
				graphics2d = volatileImage.createGraphics();
				graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				AffineTransform affineTransform = graphics2d.getTransform();
				graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

				Game.getInstance().screen.drawHandlers(Game.getBackend().getGraphics());

				graphics2d.setTransform(affineTransform);
				graphics2d.dispose();
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
