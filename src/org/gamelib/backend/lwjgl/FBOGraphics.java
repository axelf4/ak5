/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.gamelib.Game;
import org.gamelib.util.geom.Rectangle;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

/**
 * TODO: rename to FrameBuffer
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

		// glBindTexture(GL_TEXTURE_2D, 0); // unlink textures because if we dont its gonna fail
		// glDisable(GL_TEXTURE_2D);

		// FBOs wont work if texture isn't just created
		// LWJGLImage tmp = (LWJGLImage) ((LWJGLBackend) Game.instance().getBackend()).getResourceFactory().createImage(image.getWidth(), image.getHeight());
		// image.textureID = tmp.textureID;
		image.bind();

		// initialize frame buffer
		frameBufferID = glGenFramebuffersEXT(); // create new framebuffer
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, image.target, image.textureID, 0); // attach texture
		check();

		// initialize renderbuffer renderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
		/*
		 * glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, renderBufferID); // bind the depth renderbuffer glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, 512, 512); // get the data space for it glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, renderBufferID); // bind it to the renderbuffer
		 */

		// Check
		if (glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT) != GL_FRAMEBUFFER_COMPLETE) throw new Error("couldn't create FBO");

		image.unbind();
		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
		glPushAttrib(GL_VIEWPORT_BIT);
		glViewport(0, 0, image.getWidth(), image.getHeight()); // An FBO has its own viewport
		// glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glDrawBuffer(GL_COLOR_ATTACHMENT0_EXT);
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
		GL11.glPopAttrib(); // restore saved information
		// glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glReadBuffer(GL_BACK);
		// glDeleteFramebuffersEXT(frameBufferID);
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

		Rectangle size = Game.instance().getBackend().getSize();
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
		Rectangle size = Game.instance().getBackend().getSize();
		GL11.glOrtho(0, size.getWidth(), 0, size.getHeight(), 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	private void check() {
		int status = glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		switch (status) {
		case GL_FRAMEBUFFER_COMPLETE_EXT:
			System.out.println("framebuffer complete");
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
		// glGenerateMipmapEXT(GL_TEXTURE_2D);  // maybe TODO
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
