/**
 * 
 */
package org.gamelib.backend.lwjgl;

// import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.IntBuffer;

import org.gamelib.Game;
import org.gamelib.backend.Backend;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

/**
 * TODO: rename to FrameBuffer
 * 
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

		// initialize frame buffer
		// frameBufferID = glGenFramebuffersEXT(); // create new framebuffer
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		GL30.glGenFramebuffers(buffer);
		frameBufferID = buffer.get();

		// FBOs wont work if texture isn't just created
		LWJGLImage tmp = (LWJGLImage) ((LWJGLBackend) Game.getBackend()).createImage(image.getWidth(), image.getHeight());
		image.textureID = tmp.textureID;

		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
		// glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, image.target, image.textureID, 0); // attach texture
		bind();
		GL30.glFramebufferTexture2D(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, image.target, image.textureID, 0);
		// initialize renderbuffer renderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
		// glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, renderBufferID); // bind the depth renderbuffer glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, 512, 512); // get the data space for it glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, renderBufferID); // bind it to the renderbuffer

		// Check
		check();
		// if (glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT) != GL_FRAMEBUFFER_COMPLETE) throw new Error("couldn't create FBO");

		unbind();
		// glPushAttrib(GL_VIEWPORT_BIT);
		// glViewport(0, 0, image.getWidth(), image.getHeight()); // An FBO has its own viewport
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
		/*
		 * int status = glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT); switch (status) { case GL_FRAMEBUFFER_COMPLETE_EXT: break; case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT: throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT exception"); case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT: throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT exception"); case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT: throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT exception"); case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT: throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT exception"); case
		 * EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT: throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT exception"); case EXTFramebufferObject.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT: throw new RuntimeException("FrameBuffer: " + frameBufferID + ", has caused a GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT exception"); default: throw new RuntimeException("Unexpected reply from glCheckFramebufferStatusEXT: " + status); }
		 */
		int status = glCheckFramebufferStatus(frameBufferID);
		switch (status) {
		case GL_FRAMEBUFFER_COMPLETE:
			break;
		case GL_FRAMEBUFFER_UNSUPPORTED:
			throw new RuntimeException();
		default:
			break;
		}
	}

	/**
	 * Bind the FBO.
	 */
	public void begin() {
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();

		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
		// glReadBuffer(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT);
		bind();
		initGL();
		
		image.unbind();
	}

	/**
	 * Unbind from the FBO created
	 */
	public void end() {
		// GL11.glFlush();

		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		// glReadBuffer(GL_BACK);
		unbind();
		
		LWJGLBackend.init2d(Display.getWidth(), Display.getHeight());

		GL11.glPopClientAttrib();
		GL11.glPopAttrib();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/** Bind the FBO. */
	public void bind() {
		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBufferID);
		// ARBFramebufferObject.glBindFramebuffer(ARBFramebufferObject.GL_DRAW_FRAMEBUFFER, frameBufferID);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
		// glReadBuffer(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT);
	}

	/** Unbind the FBO. */
	public void unbind() {
		// glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		// ARBFramebufferObject.glBindFramebuffer(ARBFramebufferObject.GL_DRAW_FRAMEBUFFER, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		// glReadBuffer(GL_BACK);
	}

	/**
	 * Initialise the GL context
	 */
	protected void initGL() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		Backend backend = Game.getBackend();
		GL11.glViewport(0, 0, backend.getWidth(), backend.getHeight());
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
		Backend backend = Game.getBackend();
		GL11.glOrtho(0, backend.getWidth(), backend.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

}
