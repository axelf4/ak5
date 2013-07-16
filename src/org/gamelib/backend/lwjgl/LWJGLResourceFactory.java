/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.gamelib.backend.lwjgl.LWJGLSound.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Graphics;
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
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.gamelib.Destroyable;
import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.backend.Sound;
import org.gamelib.util.Math2;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.WaveData;

/**
 * @author pwnedary
 */
public class LWJGLResourceFactory implements ResourceFactory, Destroyable {

	@Override
	public InputStream getResourceAsStream(String name) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

	/** The colour model including alpha for the GL image */
	private final ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);;

	/** The colour model for the GL image */
	private final ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);;

	/** Scratch buffer for texture ID's */
	private IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);

	/**
	 * Create a new texture ID
	 * @return A new texture ID
	 */
	private int createTextureID() {
		glGenTextures(textureIDBuffer);
		return textureIDBuffer.get(0);
	}

	/**
	 * Convert the buffered image to a texture
	 * @param bufferedImage The image to convert to a texture
	 * @param image The texture to store the data into
	 * @return A buffer containing the data
	 */
	private ByteBuffer convertImageData(BufferedImage bufferedImage, LWJGLImage image) {
		ByteBuffer imageBuffer;
		WritableRaster raster;
		BufferedImage texImage;

		// find the closest power of 2 for the width and height of the produced texture
		int texWidth = Math2.pot(bufferedImage.getWidth());
		int texHeight = Math2.pot(bufferedImage.getHeight());
		image.setTexWidth(texWidth);
		image.setTexHeight(texHeight);

		// create a raster that can be used by OpenGL as a source for a texture
		if (bufferedImage.getColorModel().hasAlpha()) {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<>());
		} else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable<>());
		}

		// copy the source image into the produced image
		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(bufferedImage, 0, 0, null);

		// build a byte buffer from the temporary image
		// that be used by OpenGL to produce a texture.
		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();

		return imageBuffer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getImage(java.io.File)
	 */
	@Override
	public Image getImage(File file) throws IOException {
		int srcPixelFormat;
		// create the texture ID for this texture
		int textureID = createTextureID();
		int target = GL_TEXTURE_2D;
		LWJGLImage image = new LWJGLImage(target, textureID);

		// bind this texture
		glBindTexture(target, textureID);

		// BufferedImage bufferedImage = loadImage(resourceName);
		BufferedImage bufferedImage = ImageIO.read(getResourceAsStream(file.getPath()));
		image.setWidth(bufferedImage.getWidth());
		image.setHeight(bufferedImage.getHeight());

		srcPixelFormat = bufferedImage.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;

		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, image);

		if (target == GL_TEXTURE_2D) {
			glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_REPEAT);
		}

		// produce a texture from the byte buffer
		glTexImage2D(target, 0, GL_RGBA, Math2.pot(bufferedImage.getWidth()), Math2.pot(bufferedImage.getHeight()), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer);
		glBindTexture(GL_TEXTURE_2D, 0);
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#createImage(int, int)
	 */
	@Override
	public Image createImage(int width, int height) {
		// TODO
		// int textureID = glGenTextures();
		int textureID = createTextureID();
		LWJGLImage image = new LWJGLImage(GL_TEXTURE_2D, textureID);
		image.setWidth(width);
		image.setHeight(height);
		int texWidth = Math2.pot(width);
		int texHeight = Math2.pot(height);
		image.setTexWidth(texWidth);
		image.setTexHeight(texHeight);
		// ByteBuffer textureBuffer = convertImageData(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR), image);
		// initialize texture
		glBindTexture(GL_TEXTURE_2D, textureID);
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); // make it linear filtered
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, texWidth, texHeight, 0, GL_RGBA, GL11.GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null); // create the texture data
		}
		glBindTexture(GL_TEXTURE_2D, 0);
		return image;
	}

	// /** 4 for RGBA, 3 for RGB */
	// private static final int BYTES_PER_PIXEL = 4;

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getImage(java.awt.image.BufferedImage)
	 */
	public Image getImage(BufferedImage bufferedImage) {
		int srcPixelFormat;

		// create the texture ID for this texture
		int textureID = createTextureID();
		int target = GL_TEXTURE_2D;
		LWJGLImage image = new LWJGLImage(target, textureID);

		image.setWidth(bufferedImage.getWidth());
		image.setHeight(bufferedImage.getHeight());

		srcPixelFormat = bufferedImage.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;

		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, image);

		glBindTexture(target, textureID);
		if (target == GL_TEXTURE_2D) {
			glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		}

		// produce a texture from the byte buffer
		glTexImage2D(target, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer);
		glBindTexture(target, 0);
		return image;
	}

	static {
		int channels = 8;
		try {
			AL.create();

			// Load wav data into a buffers.
			AL10.alGenBuffers(buffer);
			// Bind buffers into audio sources.
			AL10.alGenSources(source);

			// could we allocate all channels?
			if (AL10.alGetError() != AL10.AL_NO_ERROR) {
				throw new LWJGLException("Unable to allocate " + channels + " sources");
			}
		} catch (LWJGLException le) {
			le.printStackTrace();
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

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
		if (AL.isCreated()) {
			AL.destroy();
		}
	}

}
