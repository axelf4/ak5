/**
 * 
 */
package org.gamelib.backend.lwjgl;

import java.io.File;
import java.io.IOException;

import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;

/**
 * @author pwnedary
 *
 */
public class LWJGLResourceFactory implements ResourceFactory {

	/**
	 * 
	 */
	public LWJGLResourceFactory() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getImage(java.io.File)
	 */
	@Override
	public Image getImage(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#createImage(int, int)
	 */
	@Override
	public Image createImage(int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

}
