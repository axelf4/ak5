/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;

/**
 * @author pwnedary
 *
 */
public class Java2DResourceFactory implements ResourceFactory {

	@Override
	public InputStream getResourceAsStream(String name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getImage(java.io.File)
	 */
	@Override
	public Image getImage(File file) throws IOException {
		// return new Java2DImage(ImageIO.read(file));
		return new Java2DImage(ImageIO.read(getResourceAsStream(file.getPath())));
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#createImage(int, int)
	 */
	@Override
	public Image createImage(int width, int height) {
		// return new Java2DImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		return new Java2DImage(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT));
	}

}
