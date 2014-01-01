/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;

import org.gamelib.backend.Color;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

/**
 * TODO: rename to FrameBuffer TODO utilize renderbuffers if wanted
 * @author pwnedary
 */
public class FBOGraphics extends LWJGLGraphics {

	private int frameBufferID; // FBO

	// private int renderBufferID;

	public FBOGraphics(LWJGLImage img) {
		super(img);
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) throw new Error("FBOs not supported");

		// Initialize frame buffer
		frameBufferID = glGenFramebuffersEXT(); // create new frame buffer

		// LWJGLImage tmp = (LWJGLImage) ((LWJGLBackend) Game.getBackend()).createImage(image.getWidth(), image.getHeight()); // FBOs wont work if texture isn't just created
		// image.textureID = tmp.textureID;
		image.bind();

		bind();
		EXTFramebufferObject.glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, image.target, image.getTextureID(), 0); // attach texture

		// initialize renderbuffer renderBufferID = glGenRenderbuffersEXT();
		// glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, renderBufferID); // bind the depth renderbuffer glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, 512, 512); // get the data space for it glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, renderBufferID); // bind it to the renderbuffer

		check(); // check
		// if (glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT) != GL_FRAMEBUFFER_COMPLETE) throw new Error("couldn't create FBO");

		image.unbind();
		unbind();
	}

	@Override
	public void dispose() {
		// Finish all operations so can use texture
		// GL11.glFlush();

		// Copy framebuffer to texture
		// glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 0, 0, image.getWidth(), image.getHeight(), 0);
		// glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, image.getWidth(), image.getHeight());
		// GL11.glPopAttrib(); // restore saved information
		// glViewport(0, 0, Display.getWidth(), Display.getHeight());

		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		// glReadBuffer(GL_BACK);

		// glDeleteFramebuffersEXT(frameBufferID);
		// IntBuffer buffer = (IntBuffer) BufferUtils.createIntBuffer(1).put(frameBufferID).flip();
		// glDeleteFramebuffersEXT(buffer);
	}

	private void check() {

		int status = glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		switch (status) {
		case GL_FRAMEBUFFER_COMPLETE_EXT:
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

		/*
		 * int status = glCheckFramebufferStatus(frameBufferID); switch (status) { case GL_FRAMEBUFFER_COMPLETE: break; case GL_FRAMEBUFFER_UNSUPPORTED: throw new RuntimeException(); default: break; }
		 */
	}

	/**
	 * Bind the FBO.
	 */
	public void begin() {
		/*
		 * GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS); GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS); GL11.glMatrixMode(GL11.GL_PROJECTION); GL11.glPushMatrix(); GL11.glMatrixMode(GL11.GL_MODELVIEW); GL11.glPushMatrix();
		 */

		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
		// glReadBuffer(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT);

		bind();
		GL11.glPushAttrib(GL_VIEWPORT_BIT);
		// glViewport(0, 0, image.getWidth(), image.getHeight()); // An FBO has its own viewport
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	/**
	 * Unbind from the FBO created
	 */
	public void end() {
		// GL11.glFlush();

		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		// glReadBuffer(GL_BACK);

		GL11.glPopAttrib();
		unbind();
	}

	/** Bind the FBO. */
	public void bind() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, frameBufferID);
		// ARBFramebufferObject.glBindFramebuffer(ARBFramebufferObject.GL_DRAW_FRAMEBUFFER, frameBufferID);
		// GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
		// glReadBuffer(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT);
	}

	/** Unbind the FBO. */
	public void unbind() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		// ARBFramebufferObject.glBindFramebuffer(ARBFramebufferObject.GL_DRAW_FRAMEBUFFER, 0);
		// GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		// glReadBuffer(GL_BACK);
	}
}
