/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.gamelib.backend.lwjgl.LWJGLSound.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.gamelib.Drawable;
import org.gamelib.EventBus;
import org.gamelib.Handler.Event;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Backend.BackendImpl;
import org.gamelib.backend.Color;
import org.gamelib.backend.DisplayConfiguration;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.backend.Input;
import org.gamelib.backend.Sound;
import org.gamelib.util.Math2;
import org.gamelib.util.geom.Matrix4;
import org.gamelib.util.geom.Rectangle;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.util.WaveData;

/**
 * @author pwnedary
 */
public class LWJGLBackend extends BackendImpl implements Backend {
	/** The color model including alpha for GL images */
	private static final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);;
	/** The color model for GL images */
	private static final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);;

	LWJGLGraphics graphics;
	LWJGLInput input;
	/** The Display's parent. */
	private Canvas parent;

	public LWJGLBackend() {}

	public LWJGLBackend(Container container) {
		parent = new Canvas() {
			private static final long serialVersionUID = -1322716872632362790L;

			@Override
			public final void addNotify() {
				super.addNotify();
				// startLWJGL();
			}

			@Override
			public final void removeNotify() {
				stop();
				super.removeNotify();
			}
		};
		parent.setSize(container.getWidth(), container.getHeight());
		container.add(parent);
		parent.setFocusable(true);
		parent.requestFocus();
		parent.setIgnoreRepaint(true);
	}

	@Override
	public void start() {
		try {
			DisplayConfiguration config = (LWJGLConfiguration) configuration;
			DisplayMode targetDisplayMode = null;
			if (config.fullscreen()) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == config.getWidth()) && (current.getHeight() == config.getHeight())) {
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
			} else targetDisplayMode = new DisplayMode(config.getWidth(), config.getHeight());

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(config.fullscreen());
			if (configuration instanceof LWJGLConfiguration) Display.setVSyncEnabled(((LWJGLConfiguration) configuration).vsync());
			Display.setResizable(config.resizable());
			setTitle(configuration.getProperty(DisplayConfiguration.TITLE_KEY, ""));
			if (parent != null) parent.setSize(config.getWidth(), config.getHeight()); // parent.getParent().setSize(config.getWidth(), config.getHeight());
			Display.setParent(parent);
			Display.create();

			// glMatrixMode(GL_PROJECTION);
			// glLoadIdentity();
			// glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1); // 0,0 at bottom left
			// // glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1); // 0,0 at top left
			// glMatrixMode(GL_MODELVIEW);

			glShadeModel(GL_SMOOTH);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			// glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			glClearDepth(1.0f);
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Graphics getGraphics() {
		return graphics == null ? graphics = new LWJGLGraphics() : graphics;
	}

	@Override
	public Input getInput() {
		return input == null ? input = new LWJGLInput() : input;
	}

	@Override
	public void draw(Drawable callback, float delta) {
		Graphics g = getGraphics();
		g.setColor(Color.WHITE);
		g.clear();

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		Matrix4 projection;
		if (configuration instanceof LWJGLConfiguration && ((LWJGLConfiguration) configuration).originBottomLeft()) projection = new Matrix4().setToOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		else projection = new Matrix4().setToOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glLoadMatrix((FloatBuffer) BufferUtils.createFloatBuffer(projection.val.length).put(projection.val).flip());
		glMatrixMode(GL_MODELVIEW);

		glViewport(0, 0, Display.getWidth(), Display.getHeight());

		// Game2.getInstance().screen.drawHandlers(getGraphics(), delta);
		callback.draw(g, delta);
		Display.update();
		// Util.checkGLError();
		if (Display.wasResized()) EventBus.instance().dispatch(new Event.Resize());
	}

	@Override
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	@Override
	public boolean keepRunning() {
		return !Display.isCloseRequested() && super.keepRunning();
	}

	@Override
	public void setTitle(String s) {
		Display.setTitle(s);
	}

	@Override
	public Graphics getGraphics(Image image) {
		if (GLContext.getCapabilities().GL_EXT_framebuffer_object) return new FBOGraphics((LWJGLImage) image);
		else if ((Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) != 0) return new PbufferGraphics((LWJGLImage) image);
		else throw new Error("Your OpenGL card doesn't support offscreen buffers.");
	}

	@Override
	public void dispose() {
		Display.destroy();
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
		if (AL.isCreated()) AL.destroy();
	}

	@Override
	public Rectangle getSize() {
		return new Rectangle(Display.getX(), Display.getY(), Display.getWidth(), Display.getHeight());
	}

	@Override
	public int getWidth() {
		return Display.getWidth();
	}

	@Override
	public int getHeight() {
		return Display.getHeight();
	}

	@Override
	public Image getImage(File file) throws IOException {
		return getImage(ImageIO.read(getResourceAsStream(file.getPath())));
	}

	@Override
	public Image createImage(int width, int height) {
		return getImage(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)); // 4 bytes per pixel
	}

	public Image getImage(BufferedImage bufferedImage) {
		int textureID = glGenTextures(); // create the texture ID for this texture
		int target = GL_TEXTURE_2D;
		int srcPixelFormat = bufferedImage.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;
		LWJGLImage image = new LWJGLImage(GL_TEXTURE_2D, textureID);
		image.setWidth(bufferedImage.getWidth());
		image.setHeight(bufferedImage.getHeight());

		ByteBuffer textureBuffer = convertImageData(bufferedImage, image); // convert that image into a byte buffer of texture data

		image.setFilter(Image.Filter.NEAREST, Image.Filter.NEAREST);
		glBindTexture(target, textureID);
		glTexImage2D(target, 0, GL_RGBA, image.getTexWidth(), image.getTexHeight(), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer); // produce a texture from the byte buffer
		glBindTexture(target, 0);
		return image;
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

		BufferedImage texImage = bufferedImage.getColorModel().hasAlpha() ? new BufferedImage(glAlphaColorModel, Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null), false, new Hashtable<>()) : new BufferedImage(glColorModel, Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null), false, new Hashtable<>()); // create a raster that can be used by OpenGL as a source for a texture

		texImage.getGraphics().drawImage(bufferedImage, 0, 0, null); // copy the source image into the produced image
		// Graphics2D g = (Graphics2D) texImage.getGraphics(); g.setColor(new Color(0f, 0f, 0f, 0f).toAWT()); g.fillRect(0, 0, texWidth, texHeight); g.drawImage(bufferedImage, 0, 0, null);

		// build a byte buffer from the temporary image that be used by OpenGL to produce a texture.
		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
		return (ByteBuffer) ByteBuffer.allocateDirect(data.length).order(ByteOrder.nativeOrder()).put(data).flip();
	}

	static {
		int channels = 8;
		try {
			AL.create();
			AL10.alGenBuffers(buffer); // Load wav data into a buffers.
			AL10.alGenSources(source); // Bind buffers into audio sources.

			if (AL10.alGetError() != AL10.AL_NO_ERROR) throw new LWJGLException("Unable to allocate " + channels + " sources"); // could we allocate all channels?
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.out.println("Sound disabled");
		}
	}

	@Override
	public Sound getSound(File file) throws IOException {
		WaveData waveFile = WaveData.create(getResourceAsStream(file.getPath()));
		AL10.alBufferData(buffer.get(bufferIndex), waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return new LWJGLSound(bufferIndex++);
	}

}
