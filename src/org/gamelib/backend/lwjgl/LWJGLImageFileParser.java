/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.gamelib.resource.FileLoader;
import org.gamelib.resource.FileParser;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

/**
 * @author pwnedary
 * 
 */
public class LWJGLImageFileParser implements FileParser {

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.resource.FileParser#parse(java.io.File) */
	@Override
	public Object parse(File file) throws IOException {
		LWJGLImage img = new LWJGLImage(GL_TEXTURE_2D, loadTexture(ImageIO.read(FileLoader.getResourceStream(file.getPath()))));
		return img;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.resource.FileParser#getExtensions() */
	@Override
	public String[] getExtensions() {
		return new String[] { "png" };
	}

	private static final int BYTES_PER_PIXEL = 4;

	public static int loadTexture(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		// 4 for RGBA, 3 for RGB
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				// Red component
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				// Green component
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				// Blue component
				buffer.put((byte) (pixel & 0xFF));
				// Alpha component. Only for RGBA
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		// FOR THE LOVE OF GOD DO NOT FORGET THIS
		buffer.flip();

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
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		// Return the texture ID so we can bind it later again
		return textureID;
	}

}
