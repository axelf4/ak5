/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

import org.gamelib.Drawable;
import org.gamelib.EventBus;
import org.gamelib.Handler.Event;

/**
 * @author pwnedary
 */
public class Java2DPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private VolatileImage volatileImage;
	Graphics2D g2d; // graphics2d
	float delta;
	Drawable callback;

	public Java2DPanel() {
		setIgnoreRepaint(true);
		setRequestFocusEnabled(true);
		// g2d = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY).getGraphics();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				EventBus.instance().dispatch(new Event.Resize());
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			do {
				if (volatileImage == null || volatileImage.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) volatileImage = createVolatileImage(getWidth(), getHeight());
				g2d = volatileImage.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, getWidth(), getHeight()); // clear screen

				if (callback != null) callback.draw(new Java2DGraphics(g2d, getWidth(), getHeight()), delta);

				g2d.dispose();
			} while (volatileImage.contentsLost());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			g.drawImage(volatileImage, 0, 0, getWidth(), getHeight(), this);
			volatileImage.flush();
			g.dispose();
		}

		Toolkit.getDefaultToolkit().sync(); // Tell the System to draw now, otherwise it can take a few extra ms until drawing is done which looks jerky
	}

}
