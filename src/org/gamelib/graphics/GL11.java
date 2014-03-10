/**
 * 
 */
package org.gamelib.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** Constants and commands from OpenGL 1.x, specifically OpenGL 1.5 OpenGL ES 1.1.
 * 
 * @author pwnedary */
public interface GL11 extends GL10 {
	final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
	final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
	/* HintTarget */
	final int GL_GENERATE_MIPMAP_HINT = 0x8192;
	/* TextureUnit */
	final int GL_ACTIVE_TEXTURE = 0x84E0;
	final int GL_CLIENT_ACTIVE_TEXTURE = 0x84E1;
	/* Buffer Objects */
	final int GL_ARRAY_BUFFER = 0x8892;
	final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;

	final int GL_ARRAY_BUFFER_BINDING = 0x8894;
	final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
	final int GL_VERTEX_ARRAY_BUFFER_BINDING = 0x8896;
	final int GL_NORMAL_ARRAY_BUFFER_BINDING = 0x8897;
	final int GL_COLOR_ARRAY_BUFFER_BINDING = 0x8898;
	final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 0x889A;

	final int GL_STATIC_DRAW = 0x88E4;
	final int GL_DYNAMIC_DRAW = 0x88E8;

	final int GL_BUFFER_SIZE = 0x8764;
	final int GL_BUFFER_USAGE = 0x8765;

	/* OES extension tokens */
	/* OES_read_format */
	final int GL_IMPLEMENTATION_COLOR_READ_TYPE_OES = 0x8B9A;
	final int GL_IMPLEMENTATION_COLOR_READ_FORMAT_OES = 0x8B9B;
	/* GL_OES_compressed_paletted_texture */
	final int GL_PALETTE4_RGB8_OES = 0x8B90;
	final int GL_PALETTE4_RGBA8_OES = 0x8B91;
	final int GL_PALETTE4_R5_G6_B5_OES = 0x8B92;
	final int GL_PALETTE4_RGBA4_OES = 0x8B93;
	final int GL_PALETTE4_RGB5_A1_OES = 0x8B94;
	final int GL_PALETTE8_RGB8_OES = 0x8B95;
	final int GL_PALETTE8_RGBA8_OES = 0x8B96;
	final int GL_PALETTE8_R5_G6_B5_OES = 0x8B97;
	final int GL_PALETTE8_RGBA4_OES = 0x8B98;
	final int GL_PALETTE8_RGB5_A1_OES = 0x8B99;
	/* OES_point_size_array */
	final int GL_POINT_SIZE_ARRAY_OES = 0x8B9C;
	final int GL_POINT_SIZE_ARRAY_TYPE_OES = 0x898A;
	final int GL_POINT_SIZE_ARRAY_STRIDE_OES = 0x898B;
	final int GL_POINT_SIZE_ARRAY_POINTER_OES = 0x898C;
	final int GL_POINT_SIZE_ARRAY_BUFFER_BINDING_OES = 0x8B9F;

	void glBindBuffer(int target, int buffer);

	void glBufferData(int target, int size, Buffer data, int usage);

	void glBufferSubData(int target, int offset, int size, Buffer data);

	void glClipPlane(int plane, DoubleBuffer equation);

	void glDeleteBuffers(int n, IntBuffer buffers);

	void glGenBuffers(int n, IntBuffer buffers);

	void glGetBooleanv(int pname, ByteBuffer params);

	int glGetBufferParameter(int target, int pname);

	void glGetClipPlane(int plane, DoubleBuffer equation);

	void glGetFloatv(int pname, FloatBuffer params);

	void glGetLight(int light, int pname, FloatBuffer params);

	void glGetMaterial(int face, int pname, FloatBuffer params);

	ByteBuffer glGetPointer(int pname, long size);
	
	void glGetTexEnviv(int env, int pname, IntBuffer params);

	void glGetTexParameteriv(int target, int pname, IntBuffer params);

	boolean glIsBuffer(int buffer);

	boolean glIsEnabled(int cap);

	boolean glIsTexture(int texture);

	void glPointParameterf(int pname, float param);

	void glPointParameterf(int pname, FloatBuffer params);

	void glTexEnvf(int target, int pname, float param);

	void glTexEnvfv(int target, int pname, FloatBuffer params);

	void glTexParameterfv(int target, int pname, FloatBuffer params);

	void glTexParameteriv(int target, int pname, IntBuffer params);
}
