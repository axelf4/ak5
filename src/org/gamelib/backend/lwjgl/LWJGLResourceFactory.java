/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

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

import org.gamelib.backend.Image;
import org.gamelib.backend.ResourceFactory;
import org.gamelib.backend.Sound;
import org.gamelib.util.Math2D;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

/**
 * @author pwnedary
 */
public class LWJGLResourceFactory implements ResourceFactory {

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
	 * Get the closest greater power of 2 to the fold number
	 * @param fold The target number
	 * @return The power of 2
	 */
	private static int get2Fold(int fold) {
		return Math2D.npow2(fold);
	}

	/**
	 * Convert the buffered image to a texture
	 * @param bufferedImage The image to convert to a texture
	 * @param texture The texture to store the data into
	 * @return A buffer containing the data
	 */
	private ByteBuffer convertImageData(BufferedImage bufferedImage, LWJGLImage texture) {
		ByteBuffer imageBuffer;
		WritableRaster raster;
		BufferedImage texImage;

		int texWidth = get2Fold(bufferedImage.getWidth());
		int texHeight = get2Fold(bufferedImage.getHeight());

		// find the closest power of 2 for the width and height
		// of the produced texture
		/*while (texWidth < bufferedImage.getWidth()) {
		texWidth *= 2;
		}
		while (texHeight < bufferedImage.getHeight()) {
		texHeight *= 2;
		}*/

		// texture.setTextureHeight(texHeight);
		// texture.setTextureWidth(texWidth);

		// create a raster that can be used by OpenGL as a source
		// for a texture
		if (bufferedImage.getColorModel().hasAlpha()) {
		raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
		texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
		} else {
		raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
		texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
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

		if (bufferedImage.getColorModel().hasAlpha()) {
		srcPixelFormat = GL_RGBA;
		} else {
		srcPixelFormat = GL_RGB;
		}

		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, image);

		if (target == GL_TEXTURE_2D) {
		glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}

		// produce a texture from the byte buffer
		glTexImage2D(target, 0, GL_RGBA, get2Fold(bufferedImage.getWidth()), get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer);
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#createImage(int, int)
	 */
	@Override
	public Image createImage(int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getSound(java.io.File)
	 */
	@Override
	public Sound getSound(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 4 for RGBA, 3 for RGB */
	private static final int BYTES_PER_PIXEL = 4;
	
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

		if (bufferedImage.getColorModel().hasAlpha()) {
			srcPixelFormat = GL_RGBA;
		} else {
			srcPixelFormat = GL_RGB;
		}

		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, image);

		if (target == GL_TEXTURE_2D) {
			glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		}

		// produce a texture from the byte buffer
		glTexImage2D(target, 0, GL_RGBA, get2Fold(bufferedImage.getWidth()), get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer);
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.ResourceFactory#getImage(java.awt.image.BufferedImage)
	 */
	public Image getImage2(BufferedImage img) {
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * BYTES_PER_PIXEL);

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int pixel = pixels[y * img.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:
		// glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using
		// whatever OpenGL method you want, for example:

		int textureID = glGenTextures(); // Generate texture ID
		glBindTexture(GL_TEXTURE_2D, textureID); // Bind texture ID

		// Setup wrap mode
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		// Setup texture scaling filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		// Send texel data to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		// Return the texture ID so we can bind it later again
		return new LWJGLImage(GL_TEXTURE_2D, textureID);
	}

}
