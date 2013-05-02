/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.File;
import java.io.IOException;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.backend.Sound;
import org.gamelib.util.Log;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;

/**
 * @author pwnedary
 */
public class LWJGLBackend implements Backend {

	private LWJGLGraphics graphics;

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#start(org.gamelib.Game, org.gamelib.DisplayMode)
	 */
	@Override
	public void start(Game instance, DisplayMode mode) {
		try {
			// Display.setDisplayModeAndFullscreen(convertModes(mode));
			Display.setDisplayMode(new org.lwjgl.opengl.DisplayMode(800, 600));
			Display.create();

			// glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
			glEnable(GL_TEXTURE_2D);
			// disable the OpenGL depth test since we're rendering 2D graphics
			// glDisable(GL_DEPTH_TEST);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			// GL11.glOrtho(0, mode.getWidth(), 0, mode.getHeight(), 1, -1);
			GL11.glOrtho(0, mode.getWidth(), mode.getHeight(), 0, -1, 1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			glViewport(0, 0, mode.getWidth(), mode.getHeight());
		} catch (LWJGLException e) {
			Log.error("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics()
	 */
	@Override
	public Graphics getGraphics() {
		return graphics == null ? graphics = new LWJGLGraphics() : graphics;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getInput()
	 */
	@Override
	public Input getInput() {
		return new LWJGLInput();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#screenUpdate()
	 */
	@Override
	public void screenUpdate(float delta) {
		// Clear the screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		// GL11.glEnable(GL11.GL_BLEND);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Game.getInstance().screen.drawHandlers(getGraphics(), delta);
		Display.update();
	}

	public org.lwjgl.opengl.DisplayMode convertModes(DisplayMode mode) {
		return new org.lwjgl.opengl.DisplayMode(mode.getWidth(), mode.getHeight());
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getTime()
	 */
	@Override
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#shouldClose()
	 */
	@Override
	public boolean shouldClose() {
		return Display.isCloseRequested();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		Display.destroy();
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String s) {
		Display.setTitle(s);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics(org.gamelib.graphics.Image)
	 */
	@Override
	public Graphics getGraphics(Image image) {
		if (GLContext.getCapabilities().GL_EXT_framebuffer_object)
			return new FBOGraphics((LWJGLImage) image);
		else if ((Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) != 0)
			return new PbufferGraphics((LWJGLImage) image);
		else
			throw new Error("Your OpenGL card doesn't support offscreen buffers.");
	}

	/*
	 * public Image createImage2(int width, int height) { IntBuffer buffer = ByteBuffer.allocateDirect(4 * 10000).order(ByteOrder.nativeOrder()).asIntBuffer(); glGenTextures(buffer); int textureID = buffer.get(0); glBindTexture(GL_TEXTURE_2D, textureID); glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP); glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP); glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR); BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR); byte[] tmp = ((DataBufferByte) img.getRaster().getDataBuffer()).getData(); ByteBuffer test = BufferUtils.createByteBuffer(tmp.length); test.put(tmp); test.flip(); // produce a texture from the byte buffer // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, get2Fold(bufferedImage.getWidth()), get2Fold(bufferedImage.getHeight()), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureBuffer); glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,
	 * width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer); Image image = new LWJGLImage(GL_TEXTURE_2D, textureID); image.setWidth(width); image.setHeight(height); return image; }
	 */

	public Image createImage(int width, int height) {
		int textureID = glGenTextures();
		// initialize texture
		glBindTexture(GL_TEXTURE_2D, textureID);
		// {
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); // make it linear filtered
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL11.GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null); // create the texture data
		// }
		glBindTexture(GL_TEXTURE_2D, 0);
		Image image = new LWJGLImage(GL_TEXTURE_2D, textureID);
		image.setWidth(width);
		image.setHeight(height);
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getImage(java.io.File)
	 */
	@Override
	public Image getImage(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getSound(java.io.File)
	 */
	@Override
	public Sound getSound(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backend.Backend#getResourceFactory()
	 */
	@Override
	public ResourceFactory getResourceFactory() {
		return new LWJGLResourceFactory();
	}

}
