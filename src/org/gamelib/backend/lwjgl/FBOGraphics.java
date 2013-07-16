/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.EXTFramebufferObject.*;

import org.gamelib.Game;
import org.gamelib.util.geom.Rectangle;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

/**
 * @author pwnedary
 */
public class FBOGraphics extends LWJGLGraphics {

	private int frameBufferID; // FBO

	// private int renderBufferID;

	/**
	 * 
	 */
	public FBOGraphics(LWJGLImage img) {
		super(img);
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) throw new Error("FBOs not supported");

		// glBindTexture(GL_TEXTURE_2D, 0); // unlink textures because if we dont it all is gonna fail
		// glDisable(GL_TEXTURE_2D);

		// FBOs wont work if texture isn't just created
		// LWJGLImage tmp = (LWJGLImage) ((LWJGLBackend) Game.getBackend()).getResourceFactory().createImage(image.getWidth(), image.getHeight());
		// image.textureID = tmp.textureID;

		// initialize frame buffer
		/*
		 * IntBuffer buffer = ByteBuffer.allocateDirect(1 * 4).order(ByteOrder.nativeOrder()).asIntBuffer(); // allocate a 1 int byte buffer EXTFramebufferObject.glGenFramebuffersEXT(buffer); // generate frameBufferID = buffer.get();
		 */
		frameBufferID = EXTFramebufferObject.glGenFramebuffersEXT(); // create new framebuffer
		EXTFramebufferObject.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
		EXTFramebufferObject.glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, image.target, image.textureID, 0); // attach texture
		check();

		// initialize renderbuffer renderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
		/*
		 * glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, renderBufferID); // bind the depth renderbuffer glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, 512, 512); // get the data space for it glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, renderBufferID); // bind it to the renderbuffer
		 */

		// Check
		if (EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT) != EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT) throw new Error("Couldn't create FBO");

		GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		GL11.glViewport(0, 0, image.getTexWidth(), image.getTexHeight()); // An FBO has its own viewport
		// GL11.glPushAttrib(GL11.GL_TRANSFORM_BIT);
		// GL11.glTranslatef(-image.getTexWidth(), -image.getTexHeight(), 0);

		// GL11.glClearColor(1F, 0F, 0.5F, 1F);
		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.lwjgl.LWJGLGraphics#dispose()
	 */
	@Override
	public void dispose() {
		// Finish all operations so can use texture
		// GL11.glFlush();

		// Copy framebuffer to texture
		// glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 0, 0, image.getWidth(), image.getHeight(), 0);
		// glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, image.getWidth(), image.getHeight());

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		// GL11.glReadBuffer(GL11.GL_BACK);
		GL11.glPopAttrib(); // restore saved information
		// GL11.glPopAttrib();

		glDeleteFramebuffersEXT(frameBufferID);
	}

	/**
	 * Initialise the GL context
	 */
	protected void initGL() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);

		// GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// GL11.glClearDepth(1);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Rectangle size = Game.getBackend().getSize();
		GL11.glViewport(0, 0, size.getWidth(), size.getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		enterOrtho();
	}

	/**
	 * Enter the orthographic mode
	 */
	protected void enterOrtho() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		Rectangle size = Game.getBackend().getSize();
		GL11.glOrtho(0, size.getWidth(), 0, size.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	private void check() {
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		switch (status) {
		case EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT:
			break;
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT:
			throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT:
			throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT:
			throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT:
			throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT:
			throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception");
		case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT:
			throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception");
		default:
			throw new RuntimeException("Unexpected reply from glCheckFramebufferStatusEXT: " + status);
		}
	}

	/**
	 * Bind the FBO.
	 */
	public void begin() {
		// Unbind textures
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		/*
		 * GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS); GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS); GL11.glMatrixMode(GL11.GL_PROJECTION); GL11.glPushMatrix(); GL11.glMatrixMode(GL11.GL_MODELVIEW); GL11.glPushMatrix();
		 */

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, frameBufferID);
		// GL11.glReadBuffer(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT);
		GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		GL11.glViewport(0, 0, image.getWidth(), image.getHeight());
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		// Save group port information
		// GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		// GL11.glViewport(0, 0, image.getWidth(), image.getHeight());
		// initGL();

		// Clear the FBO to a color
		// GL11.glClearColor(0.5f, 0.3f, 0.3f, 1.0f);
		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Unbind from the FBO created
	 */
	public void end() {
		// glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 0, 0, image.getWidth(), image.getHeight(), 0);

		// Finish all operations so can use texture
		// GL11.glFlush();

		// Restore saved information for main rendering context
		// GL11.glPopAttrib();

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		// GL11.glReadBuffer(GL11.GL_BACK);
		GL11.glPopAttrib();

		/*
		 * GL11.glPopClientAttrib(); GL11.glPopAttrib(); GL11.glMatrixMode(GL11.GL_MODELVIEW); GL11.glPopMatrix(); GL11.glMatrixMode(GL11.GL_PROJECTION); GL11.glPopMatrix(); GL11.glMatrixMode(GL11.GL_MODELVIEW);
		 */
	}

}
