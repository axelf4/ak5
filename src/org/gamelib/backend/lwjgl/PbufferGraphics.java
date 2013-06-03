/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;

/**
 * @author Axel
 */
public class PbufferGraphics extends LWJGLGraphics {

	private Pbuffer pbuffer;

	/**
	 * @param img
	 */
	public PbufferGraphics(LWJGLImage img) {
		super(img);
		try {
			pbuffer = new Pbuffer(image.getWidth(), image.getHeight(), new PixelFormat(), null, null);

			if ((Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) == 0) {
				throw new Error("Pbuffers not supported.");
			}
			
			initPbuffer();
			
			pbuffer.makeCurrent();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.gamelib.backend.lwjgl.LWJGLGraphics#dispose()
	 */
	@Override
	public void dispose() {
		glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 0, 0, image.getWidth(), image.getHeight(), 0);
		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	/*@Override
	public void begin() {
		if (pbuffer.isBufferLost()) {
			System.out.println("Buffer contents lost - will recreate the buffer");
			pbuffer.destroy();
			try {
				pbuffer = new Pbuffer(image.getWidth(), image.getHeight(), new PixelFormat(), null, null);
				initPbuffer();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		try {
			pbuffer.makeCurrent();
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void end() {
		glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 0, 0, image.getWidth(), image.getHeight(), 0);
		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}*/
	
	private void initPbuffer() throws LWJGLException {
		pbuffer.makeCurrent();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluOrtho2D(0, image.getWidth(), 0, image.getHeight());
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, image.getWidth(), image.getHeight());
		glBindTexture(GL_TEXTURE_2D, image.textureID);
		Display.makeCurrent();
	}

}
