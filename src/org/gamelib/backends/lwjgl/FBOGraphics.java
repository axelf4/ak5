/**
 * 
 */
package org.gamelib.backends.lwjgl;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;

import org.gamelib.Game;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

/**
 * @author pwnedary
 * 
 */
public class FBOGraphics extends LWJGLGraphics {

	private int FBO; // frameBufferID
	private int renderBufferID;

	/**
	 * 
	 */
	public FBOGraphics(LWJGLImage img) {
		super(img);

		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object)
			throw new Error("FBOs not supported.");

		/* IntBuffer buffer = BufferUtils.createIntBuffer(1);
		 * EXTFramebufferObject.glGenFramebuffersEXT(buffer); FBO =
		 * buffer.get(); */
		// initialize frame buffer
		FBO = EXTFramebufferObject.glGenFramebuffersEXT();
		renderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
		
		// FBOs wont work if textures isn't just created
		LWJGLImage tmp = (LWJGLImage) ((LWJGLBackend)Game.getBackend()).createImage(image.getWidth(), image.getHeight());

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, FBO);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, image.textureID, 0);

		// initialize depth renderbuffer
		glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, renderBufferID);                // bind the depth renderbuffer
		glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, 512, 512); // get the data space for it
		glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, renderBufferID); // bind it to the renderbuffer
		
		// Check
		if (EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT) != EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT)
			throw new Error("Could not create FBO!");

		// An fbo has its own viewport, so lets set it
		// GL11.glViewport(0, 0, img.getWidth(), img.getHeight());

		end();
	}

	/**
	 * Bind to the FBO created
	 */
	public void begin() {
		// Unbind textures
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, FBO);
		GL11.glReadBuffer(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT);

		// Save view port information
		GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		GL11.glViewport(0, 0, image.getWidth(), image.getHeight());

		// Clear the FBO to a color
		// GL11.glClearColor(0.5f, 0.3f, 0.3f, 1.0f);
		// GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Unbind from the FBO created
	 */
	public void end() {
		glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 0, 0, image.getWidth(), image.getHeight(), 0);
		
		// Finish all operations so can use texture
		GL11.glFlush();

		// Restore saved information for main rendering context
		GL11.glPopAttrib();

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		GL11.glReadBuffer(GL11.GL_BACK);
	}

}
