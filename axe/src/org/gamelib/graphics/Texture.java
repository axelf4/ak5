/**
 * 
 */
package org.gamelib.graphics;

import java.nio.IntBuffer;

import org.gamelib.Drawable;
import org.gamelib.util.Disposable;
import org.gamelib.util.io.BufferUtil;

/** @author pwnedary */
public interface Texture extends Drawable, Disposable {
	enum Filter {
		NEAREST(GL20.GL_NEAREST), LINEAR(GL20.GL_LINEAR), MIPMAP(
				GL20.GL_LINEAR_MIPMAP_LINEAR), LINEAR_MIPMAP_LINEAR(
				GL20.GL_LINEAR_MIPMAP_LINEAR), NEAREST_MIPMAP_NEAREST(
				GL20.GL_NEAREST_MIPMAP_NEAREST), LINEAR_MIPMAP_NEAREST(
				GL20.GL_LINEAR_MIPMAP_NEAREST), NEAREST_MIPMAP_LINEAR(
				GL20.GL_NEAREST_MIPMAP_LINEAR);

		final int glEnum;

		private Filter(int glEnum) {
			this.glEnum = glEnum;
		}

		public int getGLEnum() {
			return glEnum;
		}

		public boolean isMipMap() {
			return glEnum != GL20.GL_NEAREST && glEnum != GL20.GL_LINEAR;
		}
	}

	enum Wrap {
		CLAMP_TO_EDGE(GL20.GL_CLAMP_TO_EDGE), REPEAT(GL20.GL_REPEAT), MIRRORED_REPEAT(
				GL20.GL_MIRRORED_REPEAT);

		final int glEnum;

		Wrap(int glEnum) {
			this.glEnum = glEnum;
		}

		public int getGLEnum() {
			return glEnum;
		}
	}

	/** Returns this texture's type; the arrangement of images within.
	 * 
	 * @return this texture's type */
	int getTarget();

	/** Returns the internal OpenGL Texture Object.
	 * 
	 * @return the OpenGL Object */
	int getTexture();

	/** Binds this texture to the specified target for future use. */
	void bind();
	
	/** Unbinds this texture. */
	void unbind();

	/** Returns the width of this image.
	 * 
	 * @return the width of this image */
	int getWidth();

	/** Returns the height of this image.
	 * 
	 * @return the height of this image */
	int getHeight();

	/** Sets the {@link Filter} for minification and magnification.
	 * 
	 * @param minFilter the minification filter
	 * @param magFilter the magnification filter */
	void setFilter(Filter min, Filter mag);

	/** Sets the {@link Wrap} on the u and v axis.
	 * 
	 * @param u the u wrap
	 * @param v the v wrap */
	void setWrap(Wrap u, Wrap v);

	/** Returns the pixels in a <code>int[][]</code> of 32-bit ARGB values.
	 * 
	 * @return the pixels */
	//	int[][] getPixels();

	/** Returns a 32-bit RGBA value of the pixel at (<code>x,y</code>), (<code>0,0</code> being top-left).
	 * 
	 * @param x the x-coordinate of the pixel
	 * @param y the y-coordinate of the pixel
	 * @return the color at x,y */
	//	int getPixel(int x, int y);

	/** Returns a <code>byte[]</code> of the pixels. */
	//	byte[] getData();

	/** Returns a subimage as defined by the specified region, that shares the same content as this {@link Image}.
	 * 
	 * @param x the x-coordinate of the upper-left corner of the rectangular region
	 * @param y the y-coordinate of the upper-left corner of the rectangular region
	 * @param width the width of the rectangular region
	 * @param height the height of the rectangular region
	 * @return an Image that is a subimage of this Image */
	Texture region(int x, int y, int width, int height);

	/** Returns the U coordinate as mapped when drawing.
	 * 
	 * @return the U coordinate */
	float getU();

	/** Returns the V coordinate as mapped when drawing.
	 * 
	 * @return the V coordinate */
	float getV();

	/** Returns the U2 coordinate as mapped when drawing.
	 * 
	 * @return the U2 coordinate */
	float getU2();

	/** Returns the V2 coordinate as mapped when drawing.
	 * 
	 * @return the V2 coordinate */
	float getV2();

	class GLTexture implements Texture {
		private final GL10 gl;
		private final int target;
		private final int texture;
		private final int width, height;
		private int texWidth, texHeight;
		/* The texture coordinates. */
		private float u = 0, v = 0, u2 = 1, v2 = 1;

		public GLTexture(GL10 gl, int target, int texture, int width, int height) {
			this.gl = gl;
			this.target = target;
			this.texture = texture;
			this.width = width;
			this.height = height;
		}

		@Override
		public void draw(GL10 gl, float delta) {}

		@Override
		public void dispose() {
			IntBuffer buffer = BufferUtil.newIntBuffer(1);
			buffer.put(texture);
			gl.glDeleteTextures(1, buffer);
		}

		@Override
		public void bind() {
			gl.glBindTexture(target, texture);
		}
		
		@Override
		public void unbind() {
			gl.glBindTexture(target, 0);
		}

		@Override
		public int getTarget() {
			return target;
		}

		@Override
		public int getTexture() {
			return texture;
		}

		@Override
		public int getWidth() {
			return width;
		}

		@Override
		public int getHeight() {
			return height;
		}

		/** @return the texWidth */
		public int getTexWidth() {
			return texWidth;
		}

		/** @param texWidth the texWidth to set */
		public void setTexWidth(int texWidth) {
			this.texWidth = texWidth;
		}

		/** @return the texHeight */
		public int getTexHeight() {
			return texHeight;
		}

		/** @param texHeight the texHeight to set */
		public void setTexHeight(int texHeight) {
			this.texHeight = texHeight;
		}

		@Override
		public void setFilter(Filter min, Filter mag) {
			gl.glTexParameterf(target, GL10.GL_TEXTURE_MIN_FILTER, min.getGLEnum());
			gl.glTexParameterf(target, GL10.GL_TEXTURE_MAG_FILTER, mag.getGLEnum());
		}

		@Override
		public void setWrap(Wrap u, Wrap v) {
			gl.glTexParameterf(target, GL10.GL_TEXTURE_WRAP_S, u.getGLEnum());
			gl.glTexParameterf(target, GL10.GL_TEXTURE_WRAP_T, v.getGLEnum());
		}

		@Override
		public Texture region(int x, int y, int width, int height) {
			GLTexture texture = new GLTexture(gl, target, this.texture, width, height);
			texture.setTexWidth(getTexWidth());
			texture.setTexHeight(getTexHeight());
			texture.u = (float) x / texture.getTexWidth();
			texture.v = (float) y / texture.getTexHeight();
			texture.u2 = (float) (x + width) / texture.getTexWidth();
			texture.v2 = (float) (y + height) / texture.getTexHeight();
			return texture;
		}

		@Override
		public float getU() {
			return u;
		}

		@Override
		public float getV() {
			return v;
		}

		@Override
		public float getU2() {
			return u2;
		}

		@Override
		public float getV2() {
			return v2;
		}
	}
}
