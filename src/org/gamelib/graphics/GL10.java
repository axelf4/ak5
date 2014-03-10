/**
 * 
 */
package org.gamelib.graphics;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** Constants and commands from OpenGL 1.3 and therefore OpenGL ES 1.1.
 * 
 * @author pwnedary */
public interface GL10 {
	/* ClearBufferMask */
	final int GL_DEPTH_BUFFER_BIT = 0x00000100;
	final int GL_STENCIL_BUFFER_BIT = 0x00000400;
	final int GL_COLOR_BUFFER_BIT = 0x00004000;
	/* Boolean */
	final int GL_FALSE = 0;
	final int GL_TRUE = 1;
	/* BeginMode */
	final int GL_POINTS = 0x0000;
	final int GL_LINES = 0x0001;
	final int GL_LINE_LOOP = 0x0002;
	final int GL_LINE_STRIP = 0x0003;
	final int GL_TRIANGLES = 0x0004;
	final int GL_TRIANGLE_STRIP = 0x0005;
	final int GL_TRIANGLE_FAN = 0x0006;
	/* AlphaFunction */
	final int GL_NEVER = 0x0200;
	final int GL_LESS = 0x0201;
	final int GL_EQUAL = 0x0202;
	final int GL_LEQUAL = 0x0203;
	final int GL_GREATER = 0x0204;
	final int GL_NOTEQUAL = 0x0205;
	final int GL_GEQUAL = 0x0206;
	final int GL_ALWAYS = 0x0207;
	/* BlendingFactorDest */
	final int GL_ZERO = 0;
	final int GL_ONE = 1;
	final int GL_SRC_COLOR = 0x0300;
	final int GL_ONE_MINUS_SRC_COLOR = 0x0301;
	final int GL_SRC_ALPHA = 0x0302;
	final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
	final int GL_DST_ALPHA = 0x0304;
	final int GL_ONE_MINUS_DST_ALPHA = 0x0305;
	/* BlendingFactorSrc */
	final int GL_DST_COLOR = 0x0306;
	final int GL_ONE_MINUS_DST_COLOR = 0x0307;
	final int GL_SRC_ALPHA_SATURATE = 0x0308;
	/* CullFaceMode */
	final int GL_FRONT = 0x0404;
	final int GL_BACK = 0x0405;
	final int GL_FRONT_AND_BACK = 0x0408;
	/* EnableCap */
	final int GL_FOG = 0x0B60;
	final int GL_LIGHTING = 0x0B50;
	final int GL_TEXTURE_2D = 0x0DE1;
	final int GL_CULL_FACE = 0x0B44;
	final int GL_ALPHA_TEST = 0x0BC0;
	final int GL_BLEND = 0x0BE2;
	final int GL_COLOR_LOGIC_OP = 0x0BF2;
	final int GL_DITHER = 0x0BD0;
	final int GL_STENCIL_TEST = 0x0B90;
	final int GL_DEPTH_TEST = 0x0B71;
	final int GL_POINT_SMOOTH = 0x0B10;
	final int GL_LINE_SMOOTH = 0x0B20;
	final int GL_SCISSOR_TEST = 0x0C11;
	final int GL_COLOR_MATERIAL = 0x0B57;
	final int GL_NORMALIZE = 0x0BA1;
	final int GL_RESCALE_NORMAL = 0x803A;
	final int GL_POLYGON_OFFSET_FILL = 0x8037;
	final int GL_VERTEX_ARRAY = 0x8074;
	final int GL_NORMAL_ARRAY = 0x8075;
	final int GL_COLOR_ARRAY = 0x8076;
	final int GL_TEXTURE_COORD_ARRAY = 0x8078;
	final int GL_MULTISAMPLE = 0x809D;
	final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
	final int GL_SAMPLE_ALPHA_TO_ONE = 0x809F;
	final int GL_SAMPLE_COVERAGE = 0x80A0;
	/* ErrorCode */
	final int GL_NO_ERROR = 0;
	final int GL_INVALID_ENUM = 0x0500;
	final int GL_INVALID_VALUE = 0x0501;
	final int GL_INVALID_OPERATION = 0x0502;
	final int GL_STACK_OVERFLOW = 0x0503;
	final int GL_STACK_UNDERFLOW = 0x0504;
	final int GL_OUT_OF_MEMORY = 0x0505;
	/* FogMode */
	final int GL_EXP = 0x0800;
	final int GL_EXP2 = 0x0801;
	/* FogParameter */
	final int GL_FOG_DENSITY = 0x0B62;
	final int GL_FOG_START = 0x0B63;
	final int GL_FOG_END = 0x0B64;
	final int GL_FOG_MODE = 0x0B65;
	final int GL_FOG_COLOR = 0x0B66;
	/* FrontFaceDirection */
	final int GL_CW = 0x0900;
	final int GL_CCW = 0x0901;
	/* GetPName */
	final int GL_SMOOTH_POINT_SIZE_RANGE = 0x0B12;
	final int GL_SMOOTH_LINE_WIDTH_RANGE = 0x0B22;
	final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
	final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
	final int GL_MAX_LIGHTS = 0x0D31;
	final int GL_MAX_TEXTURE_SIZE = 0x0D33;
	final int GL_MAX_MODELVIEW_STACK_DEPTH = 0x0D36;
	final int GL_MAX_PROJECTION_STACK_DEPTH = 0x0D38;
	final int GL_MAX_TEXTURE_STACK_DEPTH = 0x0D39;
	final int GL_MAX_VIEWPORT_DIMS = 0x0D3A;
	final int GL_MAX_ELEMENTS_VERTICES = 0x80E8;
	final int GL_MAX_ELEMENTS_INDICES = 0x80E9;
	final int GL_MAX_TEXTURE_UNITS = 0x84E2;
	final int GL_SUBPIXEL_BITS = 0x0D50;
	final int GL_RED_BITS = 0x0D52;
	final int GL_GREEN_BITS = 0x0D53;
	final int GL_BLUE_BITS = 0x0D54;
	final int GL_ALPHA_BITS = 0x0D55;
	final int GL_DEPTH_BITS = 0x0D56;
	final int GL_STENCIL_BITS = 0x0D57;
	/* HintMode */
	final int GL_DONT_CARE = 0x1100;
	final int GL_FASTEST = 0x1101;
	final int GL_NICEST = 0x1102;
	/* HintTarget */
	final int GL_PERSPECTIVE_CORRECTION_HINT = 0x0C50;
	final int GL_POINT_SMOOTH_HINT = 0x0C51;
	final int GL_LINE_SMOOTH_HINT = 0x0C52;
	final int GL_POLYGON_SMOOTH_HINT = 0x0C53;
	final int GL_FOG_HINT = 0x0C54;
	/* LightModelParameter */
	final int GL_LIGHT_MODEL_AMBIENT = 0x0B53;
	final int GL_LIGHT_MODEL_TWO_SIDE = 0x0B52;
	/* LightParameter */
	final int GL_AMBIENT = 0x1200;
	final int GL_DIFFUSE = 0x1201;
	final int GL_SPECULAR = 0x1202;
	final int GL_POSITION = 0x1203;
	final int GL_SPOT_DIRECTION = 0x1204;
	final int GL_SPOT_EXPONENT = 0x1205;
	final int GL_SPOT_CUTOFF = 0x1206;
	final int GL_CONSTANT_ATTENUATION = 0x1207;
	final int GL_LINEAR_ATTENUATION = 0x1208;
	final int GL_QUADRATIC_ATTENUATION = 0x1209;
	/* DataType */
	final int GL_BYTE = 0x1400;
	final int GL_UNSIGNED_BYTE = 0x1401;
	final int GL_SHORT = 0x1402;
	final int GL_UNSIGNED_SHORT = 0x1403;
	final int GL_FLOAT = 0x1406;
	/* LogicOp */
	final int GL_CLEAR = 0x1500;
	final int GL_AND = 0x1501;
	final int GL_AND_REVERSE = 0x1502;
	final int GL_COPY = 0x1503;
	final int GL_AND_INVERTED = 0x1504;
	final int GL_NOOP = 0x1505;
	final int GL_XOR = 0x1506;
	final int GL_OR = 0x1507;
	final int GL_NOR = 0x1508;
	final int GL_EQUIV = 0x1509;
	final int GL_INVERT = 0x150A;
	final int GL_OR_REVERSE = 0x150B;
	final int GL_COPY_INVERTED = 0x150C;
	final int GL_OR_INVERTED = 0x150D;
	final int GL_NAND = 0x150E;
	final int GL_SET = 0x150F;
	/* MaterialParameter */
	final int GL_EMISSION = 0x1600;
	final int GL_SHININESS = 0x1601;
	final int GL_AMBIENT_AND_DIFFUSE = 0x1602;
	/* MatrixMode */
	final int GL_MODELVIEW = 0x1700;
	final int GL_PROJECTION = 0x1701;
	final int GL_TEXTURE = 0x1702;
	/* PixelFormat */
	final int GL_ALPHA = 0x1906;
	final int GL_RGB = 0x1907;
	final int GL_RGBA = 0x1908;
	final int GL_LUMINANCE = 0x1909;
	final int GL_LUMINANCE_ALPHA = 0x190A;
	/* PixelStoreParameter */
	final int GL_UNPACK_ALIGNMENT = 0x0CF5;
	final int GL_PACK_ALIGNMENT = 0x0D05;
	/* PixelType */
	final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
	final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
	final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
	/* ShadingModel */
	final int GL_FLAT = 0x1D00;
	final int GL_SMOOTH = 0x1D01;
	/* StencilOp */
	final int GL_KEEP = 0x1E00;
	final int GL_REPLACE = 0x1E01;
	final int GL_INCR = 0x1E02;
	final int GL_DECR = 0x1E03;
	/* StringName */
	final int GL_VENDOR = 0x1F00;
	final int GL_RENDERER = 0x1F01;
	final int GL_VERSION = 0x1F02;
	final int GL_EXTENSIONS = 0x1F03;
	/* TextureEnvMode */
	final int GL_MODULATE = 0x2100;
	final int GL_DECAL = 0x2101;
	final int GL_ADD = 0x0104;
	/* TextureEnvParameter */
	final int GL_TEXTURE_ENV_MODE = 0x2200;
	final int GL_TEXTURE_ENV_COLOR = 0x2201;
	/* TextureEnvTarget */
	final int GL_TEXTURE_ENV = 0x2300;
	/* TextureMagFilter */
	final int GL_NEAREST = 0x2600;
	final int GL_LINEAR = 0x2601;
	/* TextureMinFilter */
	final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
	final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
	final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
	final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
	/* TextureParameterName */
	final int GL_TEXTURE_MAG_FILTER = 0x2800;
	final int GL_TEXTURE_MIN_FILTER = 0x2801;
	final int GL_TEXTURE_WRAP_S = 0x2802;
	final int GL_TEXTURE_WRAP_T = 0x2803;
	final int GL_GENERATE_MIPMAP = 0x8191;
	/* TextureUnit */
	final int GL_TEXTURE0 = 0x84C0;
	final int GL_TEXTURE1 = 0x84C1;
	final int GL_TEXTURE2 = 0x84C2;
	final int GL_TEXTURE3 = 0x84C3;
	final int GL_TEXTURE4 = 0x84C4;
	final int GL_TEXTURE5 = 0x84C5;
	final int GL_TEXTURE6 = 0x84C6;
	final int GL_TEXTURE7 = 0x84C7;
	final int GL_TEXTURE8 = 0x84C8;
	final int GL_TEXTURE9 = 0x84C9;
	final int GL_TEXTURE10 = 0x84CA;
	final int GL_TEXTURE11 = 0x84CB;
	final int GL_TEXTURE12 = 0x84CC;
	final int GL_TEXTURE13 = 0x84CD;
	final int GL_TEXTURE14 = 0x84CE;
	final int GL_TEXTURE15 = 0x84CF;
	final int GL_TEXTURE16 = 0x84D0;
	final int GL_TEXTURE17 = 0x84D1;
	final int GL_TEXTURE18 = 0x84D2;
	final int GL_TEXTURE19 = 0x84D3;
	final int GL_TEXTURE20 = 0x84D4;
	final int GL_TEXTURE21 = 0x84D5;
	final int GL_TEXTURE22 = 0x84D6;
	final int GL_TEXTURE23 = 0x84D7;
	final int GL_TEXTURE24 = 0x84D8;
	final int GL_TEXTURE25 = 0x84D9;
	final int GL_TEXTURE26 = 0x84DA;
	final int GL_TEXTURE27 = 0x84DB;
	final int GL_TEXTURE28 = 0x84DC;
	final int GL_TEXTURE29 = 0x84DD;
	final int GL_TEXTURE30 = 0x84DE;
	final int GL_TEXTURE31 = 0x84DF;
	/* TextureWrapMode */
	final int GL_REPEAT = 0x2901;
	final int GL_CLAMP_TO_EDGE = 0x812F;
	/* PixelInternalFormat */
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
	/* LightName */
	final int GL_LIGHT0 = 0x4000;
	final int GL_LIGHT1 = 0x4001;
	final int GL_LIGHT2 = 0x4002;
	final int GL_LIGHT3 = 0x4003;
	final int GL_LIGHT4 = 0x4004;
	final int GL_LIGHT5 = 0x4005;
	final int GL_LIGHT6 = 0x4006;
	final int GL_LIGHT7 = 0x4007;

	/* Available in both Common and Common-Lite profiles */

	void glActiveTexture(int texture);

	void glAlphaFunc(int func, float ref);

	void glBindTexture(int target, int texture);

	void glBlendFunc(int sfactor, int dfactor);

	void glClear(int mask);

	void glClearColor(float red, float green, float blue, float alpha);

	void glClearDepthf(float depth);

	void glClearStencil(int s);

	void glClientActiveTexture(int texture);

	void glColor4f(float red, float green, float blue, float alpha);

	void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);

	void glColorPointer(int size, int type, int stride, Buffer pointer);

	void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data);

	void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data);

	void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border);

	void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

	void glCullFace(int mode);

	void glDeleteTextures(int n, IntBuffer textures);

	void glDepthFunc(int func);

	void glDepthMask(boolean flag);

	void glDepthRangef(float zNear, float zFar);

	void glDisable(int cap);

	void glDisableClientState(int cap);

	void glDrawArrays(int mode, int first, int count);

	void glDrawElements(int mode, int count, int type, Buffer indices);

	void glEnable(int cap);

	void glEnableClientState(int cap);

	void glFinish();

	void glFlush();

	void glFog(int pname, float param);

	void glFog(int pname, FloatBuffer params);

	void glFrontFace(int mode);

	void glFrustum(double left, double right, double bottom, double top, double zNear, double zFar);

	void glGenTextures(int n, IntBuffer textures);

	int glGetError();

	void glGetIntegerv(int pname, IntBuffer params);

	String glGetString(int name);

	void glHint(int target, int mode);

	void glLightModel(int pname, float param);

	void glLightModel(int pname, FloatBuffer params);

	void glLight(int light, int pname, float param);

	void glLight(int light, int pname, FloatBuffer params);

	void glLineWidth(float width);

	void glLoadIdentity();

	void glLoadMatrix(FloatBuffer m);

	void glLogicOp(int opcode);

	void glMaterial(int face, int pname, float param);

	void glMaterial(int face, int pname, FloatBuffer params);

	void glMatrixMode(int mode);

	void glMultMatrix(FloatBuffer m);

	void glMultiTexCoord4(int target, float s, float t, float r, float q);

	void glNormal3(float nx, float ny, float nz);

	void glNormalPointer(int type, int stride, Buffer pointer);

	void glOrtho(double left, double right, double bottom, double top, double zNear, double zFar);

	void glPixelStorei(int pname, int param);

	void glPointSize(float size);

	void glPolygonOffset(float factor, float units);

	void glPopMatrix();

	void glPushMatrix();

	void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels);

	void glRotate(float angle, float x, float y, float z);

	void glSampleCoverage(float value, boolean invert);

	void glScale(float x, float y, float z);

	void glScissor(int x, int y, int width, int height);

	void glShadeModel(int mode);

	void glStencilFunc(int func, int ref, int mask);

	void glStencilMask(int mask);

	void glStencilOp(int fail, int zfail, int zpass);

	void glTexCoordPointer(int size, int type, int stride, Buffer pointer);

	void glTexEnvi(int target, int pname, int param);

	void glTexEnviv(int target, int pname, IntBuffer params);

	void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels);
	
	void glTexParameterf(int target, int pname, float param);

	void glTexParameteri(int target, int pname, int param);

	void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);

	void glTranslate(float x, float y, float z);

	void glVertexPointer(int size, int type, int stride, Buffer pointer);

	void glViewport(int x, int y, int width, int height);
}
