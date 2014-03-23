/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.gamelib.backend.lwjgl.LWJGLSound.*;
import static org.gamelib.graphics.GL10.*;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
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
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.gamelib.Drawable;
import org.gamelib.Event;
import org.gamelib.Handler;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Backend.BackendImpl;
import org.gamelib.backend.DisplayConfiguration;
import org.gamelib.backend.Input;
import org.gamelib.backend.Sound;
import org.gamelib.graphics.GL10;
import org.gamelib.graphics.Texture;
import org.gamelib.graphics.Texture.GLTexture;
import org.gamelib.util.Configuration;
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
import org.lwjgl.util.WaveData;

/** @author pwnedary */
public class LWJGLBackend extends BackendImpl implements Backend {
	/** The color model including alpha for GL images */
	private static final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);;
	/** The color model for GL images */
	private static final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);;

	private GL10 gl;
	private LWJGLInput input;
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
	public void start(Configuration configuration, Handler handler) {
		super.start(configuration, handler);
		gl = new LWJGLGL20();
		input = new LWJGLInput(handler);
	}

	@Override
	public void start() {
		try {
			DisplayConfiguration config = (DisplayConfiguration) configuration;
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
			setTitle(configuration.getProperty(DisplayConfiguration.TITLE, ""));
			if (parent != null) parent.setSize(config.getWidth(), config.getHeight()); // parent.getParent().setSize(config.getWidth(), config.getHeight());
			Display.setParent(parent);
			Display.create();

			//			gl.glMatrixMode(GL_PROJECTION);
			//			gl.glLoadIdentity();
			//			gl.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1); // 0,0 at bottom left
			//			// glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1); // 0,0 at top left
			//			gl.glMatrixMode(GL_MODELVIEW);

			gl.glShadeModel(GL_SMOOTH);
			gl.glEnable(GL_BLEND);
			gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			// glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			gl.glClearDepthf(1.0f);
			gl.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Input getInput() {
		return input;
	}

	@Override
	public void draw(Drawable callback, float delta) {
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		Matrix4 projection;
		if (configuration instanceof LWJGLConfiguration && ((LWJGLConfiguration) configuration).originBottomLeft()) projection = new Matrix4().setToOrtho(0, Display.getWidth(), 0, Display.getHeight(), 1, -1);
		else projection = new Matrix4().setToOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		gl.glLoadMatrix((FloatBuffer) BufferUtils.createFloatBuffer(projection.val.length).put(projection.val).flip());
		gl.glMatrixMode(GL_MODELVIEW);

		gl.glViewport(0, 0, Display.getWidth(), Display.getHeight());

		callback.draw(gl, delta);
		Display.update();
		// Util.checkGLError();
		if (Display.wasResized()) handler.handle(new Event.Resize(getWidth(), getHeight()));
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
	public Texture getTexture(String name) throws IOException {
		return getTexture(ImageIO.read(getResourceAsStream(name)));
	}

	@Override
	public Texture createTexture(int width, int height) {
		return getTexture(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)); // 4 bytes per pixel
	}

	@Override
	public Texture getTexture(BufferedImage bufferedImage) {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		gl.glGenTextures(1, buffer);
		int textureID = buffer.get(0); // create the texture ID for this texture
		int target = GL_TEXTURE_2D;
		int srcPixelFormat = bufferedImage.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;
		Texture texture = new Texture.GLTexture(gl, GL_TEXTURE_2D, textureID, bufferedImage.getWidth(), bufferedImage.getHeight());

		ByteBuffer textureBuffer = convertImageData(bufferedImage, texture); // convert that image into a byte buffer of texture data

		texture.bind();
		texture.setFilter(Texture.Filter.NEAREST, Texture.Filter.NEAREST);
		//		texture.setWrap(Texture.Wrap.CLAMP_TO_EDGE, Texture.Wrap.CLAMP_TO_EDGE);
		gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		gl.glTexImage2D(target, 0, GL_RGBA, ((GLTexture) texture).getTexWidth(), ((GLTexture) texture).getTexHeight(), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer); // produce a texture from the byte buffer
		return texture;
	}

	/** Convert the buffered image to a texture
	 * 
	 * @param bufferedImage The image to convert to a texture
	 * @param texture The texture to store the data into
	 * @return a buffer containing the data */
	private ByteBuffer convertImageData(BufferedImage bufferedImage, Texture texture) {
		// Find the closest power of 2 for the width and height of the produced texture
		int texWidth = Math2.pot(bufferedImage.getWidth());
		int texHeight = Math2.pot(bufferedImage.getHeight());
		((GLTexture) texture).setTexWidth(texWidth);
		((GLTexture) texture).setTexHeight(texHeight);

		BufferedImage image = bufferedImage.getColorModel().hasAlpha() ? new BufferedImage(glAlphaColorModel, Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null), false, new Hashtable<>()) : new BufferedImage(glColorModel, Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null), false, new Hashtable<>()); // Create a raster that can be used by OpenGL as a source for a texture

		AffineTransform xform = AffineTransform.getScaleInstance(1, -1);
		xform.translate(0, -bufferedImage.getHeight(null)); // Flip the image vertically
		((Graphics2D) image.getGraphics()).drawImage(bufferedImage, xform, parent); // Copy source into the produced image

		// Build a byte buffer from the temporary image that be used by OpenGL to produce a texture.
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
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

	@Override
	public GL10 getGL() {
		return gl;
	}

}
