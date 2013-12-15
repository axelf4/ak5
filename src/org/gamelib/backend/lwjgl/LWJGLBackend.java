/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.gamelib.backend.lwjgl.LWJGLSound.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.gamelib.Drawable;
import org.gamelib.Game;
import org.gamelib.backend.Backend;
import org.gamelib.backend.BackendImpl;
import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.Input;
import org.gamelib.backend.Sound;
import org.gamelib.backend.VideoMode;
import org.gamelib.util.Math2;
import org.gamelib.util.geom.Rectangle;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.glu.GLU;

/**
 * @author pwnedary
 */
public class LWJGLBackend extends BackendImpl implements Backend {

	/** The colour model including alpha for the GL image */
	private final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);;
	/** The colour model for the GL image */
	private final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);;
	/** Scratch buffer for texture ID's */
	private IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);

	LWJGLGraphics graphics;
	LWJGLInput input;

	static void init2d_old(int width, int height) {
		glMatrixMode(GL_PROJECTION); // resets any previous projection matrices
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1); // 0,0 : top-left
		// glOrtho(0, width, 0, height, 1, -1); // 0,0 : bottom-left
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glDisable(GL_DEPTH_TEST);
		glViewport(0, 0, width, height);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	static void init2d(int width, int height) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1); // 0,0 : top-left
//		 glOrtho(0, width, 0, height, 1, -1); // 0,0 : bottom-left
		glMatrixMode(GL_MODELVIEW);

		// GL11.glTranslatef((width - xsize) / 2, (height - ysize) / 2, 0);
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClearDepth(1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, width, height);
		// glMatrixMode(GL_MODELVIEW);
	}

	static void init3d(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION); // resets any previous projection matrices
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, (float) width / (float) height, 0.1f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	/** {@inheritDoc} */
	@Override
	public void start(Game game) {
		try {
			VideoMode videoMode = game.getResolution();
			DisplayMode targetDisplayMode = null;
			if (videoMode.fullscreen()) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == videoMode.getWidth()) && (current.getHeight() == videoMode.getHeight())) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}
						// if we've found a match for bpp and frequency against the original display mode then it's probably best to go for this one since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else targetDisplayMode = new DisplayMode(videoMode.getWidth(), videoMode.getHeight());

			// Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(videoMode.fullscreen());
			Display.setVSyncEnabled(videoMode.vsync);
			Display.create();

			super.start(game);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Graphics getGraphics() {
		return graphics == null ? graphics = new LWJGLGraphics() : graphics;
	}

	/** {@inheritDoc} */
	@Override
	public Input getInput() {
		return input == null ? input = new LWJGLInput() : input;
	}

	/** {@inheritDoc} */
	@Override
	public void draw(Drawable callback, float delta) {
		Graphics g = getGraphics();
		g.setColor(Color.WHITE);
		g.clear();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		// Game2.getInstance().screen.drawHandlers(getGraphics(), delta);
		callback.draw(g, delta);
		Display.update();
		// Util.checkGLError();
	}

	public org.lwjgl.opengl.DisplayMode convertModes(DisplayMode mode) {
		return new org.lwjgl.opengl.DisplayMode(mode.getWidth(), mode.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/** {@inheritDoc} */
	@Override
	public boolean shouldClose() {
		return Display.isCloseRequested() || super.shouldClose();
	}

	/** {@inheritDoc} */
	@Override
	public void setTitle(String s) {
		Display.setTitle(s);
	}

	/** {@inheritDoc} */
	@Override
	public Graphics getGraphics(Image image) {
		if (GLContext.getCapabilities().GL_EXT_framebuffer_object) return new FBOGraphics((LWJGLImage) image);
		else if ((Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) != 0) return new PbufferGraphics((LWJGLImage) image);
		else throw new Error("Your OpenGL card doesn't support offscreen buffers.");
	}

	/** {@inheritDoc} */
	@Override
	public void destroy() {
		Display.destroy();
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
		if (AL.isCreated()) AL.destroy();
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle getSize() {
		return new Rectangle(Display.getX(), Display.getY(), Display.getWidth(), Display.getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		return Display.getWidth();
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		return Display.getHeight();
	}

	/**
	 * Create a new texture ID
	 * @return a new texture ID
	 */
	private int createTextureID() {
		// glGenTextures(textureIDBuffer); return textureIDBuffer.get(0);
		IntBuffer tmp = createIntBuffer(1);
		glGenTextures(tmp);
		return tmp.get(0);
	}

	/**
	 * Creates an integer buffer to hold specified ints - strictly a utility method
	 * @param size how many int to contain
	 * @return created IntBuffer
	 */
	private static IntBuffer createIntBuffer(int size) {
		return ByteBuffer.allocateDirect(4 * size).order(ByteOrder.nativeOrder()).asIntBuffer();
	}

	/**
	 * Convert the buffered image to a texture
	 * @param bufferedImage The image to convert to a texture
	 * @param image The texture to store the data into
	 * @return a buffer containing the data
	 */
	private ByteBuffer convertImageData(BufferedImage bufferedImage, LWJGLImage image) {
		// find the closest power of 2 for the width and height of the produced texture
		int texWidth = Math2.pot(bufferedImage.getWidth());
		int texHeight = Math2.pot(bufferedImage.getHeight());
		image.setTexWidth(texWidth);
		image.setTexHeight(texHeight);

		// create a raster that can be used by OpenGL as a source for a texture
		WritableRaster raster;
		BufferedImage texImage;
		if (bufferedImage.getColorModel().hasAlpha()) {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<>());
		} else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable<>());
		}

		// copy the source image into the produced image
		Graphics2D g = (Graphics2D) texImage.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f).toAWT());
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(bufferedImage, 0, 0, null);

		// build a byte buffer from the temporary image that be used by OpenGL to produce a texture.
		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

		ByteBuffer imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();

		return imageBuffer;
	}

	/** {@inheritDoc} */
	@Override
	public Image getImage(File file) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(getResourceAsStream(file.getPath()));
		return getImage(bufferedImage);
	}

	public Image createImage2(int width, int height) {
		// TODO
		int textureID = createTextureID();
		LWJGLImage image = new LWJGLImage(GL_TEXTURE_2D, textureID);
		image.setWidth(width);
		image.setHeight(height);
		// image.setTexWidth(Math2.pot(width));
		// image.setTexHeight(Math2.pot(height));
		// ByteBuffer textureBuffer = convertImageData(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR), image);
		BufferedImage image1 = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = (Graphics2D) image1.getGraphics();
		g2d.setColor(org.gamelib.backend.Color.CYAN.toAWT());
		g2d.fillRect(10, 10, 50, 50);
		ByteBuffer textureBuffer = convertImageData(image1, image);
		// initialize texture
		image.bind();
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); // make it linear filtered
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getTexWidth(), image.getTexHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null); // create the texture data
		}
		image.unbind();
		return image;
	}

	@Override
	public Image createImage(int width, int height) {
		return getImage(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR));
	}

	// /** 4 for RGBA, 3 for RGB */
	// private static final int BYTES_PER_PIXEL = 4;

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getImage(java.awt.image.BufferedImage)
	 */
	public Image getImage(BufferedImage bufferedImage) {
		int textureID = createTextureID(); // create the texture ID for this texture
		int target = GL_TEXTURE_2D;
		LWJGLImage image = new LWJGLImage(target, textureID);

		image.setWidth(bufferedImage.getWidth());
		image.setHeight(bufferedImage.getHeight());

		int srcPixelFormat = bufferedImage.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;

		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, image);

		glBindTexture(target, textureID);
		if (target == GL_TEXTURE_2D) {
			/*
			 * glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_REPEAT); glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_REPEAT); glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR); glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			 */
			glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		}

		glTexImage2D(target, 0, GL_RGBA, image.getTexWidth(), image.getTexHeight(), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer); // produce a texture from the byte buffer
		glBindTexture(target, 0);
		return image;
	}

	static {
		int channels = 8;
		try {
			AL.create();

			AL10.alGenBuffers(buffer); // Load wav data into a buffers.
			AL10.alGenSources(source); // Bind buffers into audio sources.

			// could we allocate all channels?
			if (AL10.alGetError() != AL10.AL_NO_ERROR) throw new LWJGLException("Unable to allocate " + channels + " sources");
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.out.println("Sound disabled");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getSound(java.io.File)
	 */
	@Override
	public Sound getSound(File file) throws IOException {
		WaveData waveFile = WaveData.create(getResourceAsStream(file.getPath()));
		AL10.alBufferData(buffer.get(bufferIndex), waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();

		return new LWJGLSound(bufferIndex++);
	}

}
