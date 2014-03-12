/**
 * 
 */
package org.gamelib.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** @author pwnedary */
public interface GL20 extends GL11 {
	final int GL_DEPTH_BUFFER_BIT = 0x00000100;
	final int GL_STENCIL_BUFFER_BIT = 0x00000400;
	final int GL_COLOR_BUFFER_BIT = 0x00004000;
	final int GL_FALSE = 0;
	final int GL_TRUE = 1;
	final int GL_POINTS = 0x0000;
	final int GL_LINES = 0x0001;
	final int GL_LINE_LOOP = 0x0002;
	final int GL_LINE_STRIP = 0x0003;
	final int GL_TRIANGLES = 0x0004;
	final int GL_TRIANGLE_STRIP = 0x0005;
	final int GL_TRIANGLE_FAN = 0x0006;
	final int GL_ZERO = 0;
	final int GL_ONE = 1;
	final int GL_SRC_COLOR = 0x0300;
	final int GL_ONE_MINUS_SRC_COLOR = 0x0301;
	final int GL_SRC_ALPHA = 0x0302;
	final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
	final int GL_DST_ALPHA = 0x0304;
	final int GL_ONE_MINUS_DST_ALPHA = 0x0305;
	final int GL_DST_COLOR = 0x0306;
	final int GL_ONE_MINUS_DST_COLOR = 0x0307;
	final int GL_SRC_ALPHA_SATURATE = 0x0308;
	final int GL_FUNC_ADD = 0x8006;
	final int GL_BLEND_EQUATION = 0x8009;
	final int GL_BLEND_EQUATION_RGB = 0x8009;
	final int GL_BLEND_EQUATION_ALPHA = 0x883D;
	final int GL_FUNC_SUBTRACT = 0x800A;
	final int GL_FUNC_REVERSE_SUBTRACT = 0x800B;
	final int GL_BLEND_DST_RGB = 0x80C8;
	final int GL_BLEND_SRC_RGB = 0x80C9;
	final int GL_BLEND_DST_ALPHA = 0x80CA;
	final int GL_BLEND_SRC_ALPHA = 0x80CB;
	final int GL_CONSTANT_COLOR = 0x8001;
	final int GL_ONE_MINUS_CONSTANT_COLOR = 0x8002;
	final int GL_CONSTANT_ALPHA = 0x8003;
	final int GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;
	final int GL_BLEND_COLOR = 0x8005;
	final int GL_ARRAY_BUFFER = 0x8892;
	final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
	final int GL_ARRAY_BUFFER_BINDING = 0x8894;
	final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
	final int GL_STREAM_DRAW = 0x88E0;
	final int GL_STATIC_DRAW = 0x88E4;
	final int GL_DYNAMIC_DRAW = 0x88E8;
	final int GL_BUFFER_SIZE = 0x8764;
	final int GL_BUFFER_USAGE = 0x8765;
	final int GL_CURRENT_VERTEX_ATTRIB = 0x8626;
	final int GL_FRONT = 0x0404;
	final int GL_BACK = 0x0405;
	final int GL_FRONT_AND_BACK = 0x0408;
	final int GL_TEXTURE_2D = 0x0DE1;
	final int GL_CULL_FACE = 0x0B44;
	final int GL_BLEND = 0x0BE2;
	final int GL_DITHER = 0x0BD0;
	final int GL_STENCIL_TEST = 0x0B90;
	final int GL_DEPTH_TEST = 0x0B71;
	final int GL_SCISSOR_TEST = 0x0C11;
	final int GL_POLYGON_OFFSET_FILL = 0x8037;
	final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
	final int GL_SAMPLE_COVERAGE = 0x80A0;
	final int GL_NO_ERROR = 0;
	final int GL_INVALID_ENUM = 0x0500;
	final int GL_INVALID_VALUE = 0x0501;
	final int GL_INVALID_OPERATION = 0x0502;
	final int GL_OUT_OF_MEMORY = 0x0505;
	final int GL_CW = 0x0900;
	final int GL_CCW = 0x0901;
	final int GL_LINE_WIDTH = 0x0B21;
	final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
	final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
	final int GL_CULL_FACE_MODE = 0x0B45;
	final int GL_FRONT_FACE = 0x0B46;
	final int GL_DEPTH_RANGE = 0x0B70;
	final int GL_DEPTH_WRITEMASK = 0x0B72;
	final int GL_DEPTH_CLEAR_VALUE = 0x0B73;
	final int GL_DEPTH_FUNC = 0x0B74;
	final int GL_STENCIL_CLEAR_VALUE = 0x0B91;
	final int GL_STENCIL_FUNC = 0x0B92;
	final int GL_STENCIL_FAIL = 0x0B94;
	final int GL_STENCIL_PASS_DEPTH_FAIL = 0x0B95;
	final int GL_STENCIL_PASS_DEPTH_PASS = 0x0B96;
	final int GL_STENCIL_REF = 0x0B97;
	final int GL_STENCIL_VALUE_MASK = 0x0B93;
	final int GL_STENCIL_WRITEMASK = 0x0B98;
	final int GL_STENCIL_BACK_FUNC = 0x8800;
	final int GL_STENCIL_BACK_FAIL = 0x8801;
	final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802;
	final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803;
	final int GL_STENCIL_BACK_REF = 0x8CA3;
	final int GL_STENCIL_BACK_VALUE_MASK = 0x8CA4;
	final int GL_STENCIL_BACK_WRITEMASK = 0x8CA5;
	final int GL_VIEWPORT = 0x0BA2;
	final int GL_SCISSOR_BOX = 0x0C10;
	final int GL_COLOR_CLEAR_VALUE = 0x0C22;
	final int GL_COLOR_WRITEMASK = 0x0C23;
	final int GL_UNPACK_ALIGNMENT = 0x0CF5;
	final int GL_PACK_ALIGNMENT = 0x0D05;
	final int GL_MAX_TEXTURE_SIZE = 0x0D33;
	final int GL_MAX_VIEWPORT_DIMS = 0x0D3A;
	final int GL_SUBPIXEL_BITS = 0x0D50;
	final int GL_RED_BITS = 0x0D52;
	final int GL_GREEN_BITS = 0x0D53;
	final int GL_BLUE_BITS = 0x0D54;
	final int GL_ALPHA_BITS = 0x0D55;
	final int GL_DEPTH_BITS = 0x0D56;
	final int GL_STENCIL_BITS = 0x0D57;
	final int GL_POLYGON_OFFSET_UNITS = 0x2A00;
	final int GL_POLYGON_OFFSET_FACTOR = 0x8038;
	final int GL_TEXTURE_BINDING_2D = 0x8069;
	final int GL_SAMPLE_BUFFERS = 0x80A8;
	final int GL_SAMPLES = 0x80A9;
	final int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
	final int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
	final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
	final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
	final int GL_DONT_CARE = 0x1100;
	final int GL_FASTEST = 0x1101;
	final int GL_NICEST = 0x1102;
	final int GL_GENERATE_MIPMAP_HINT = 0x8192;
	final int GL_BYTE = 0x1400;
	final int GL_UNSIGNED_BYTE = 0x1401;
	final int GL_SHORT = 0x1402;
	final int GL_UNSIGNED_SHORT = 0x1403;
	final int GL_INT = 0x1404;
	final int GL_UNSIGNED_INT = 0x1405;
	final int GL_FLOAT = 0x1406;
	final int GL_FIXED = 0x140C;
	final int GL_DEPTH_COMPONENT = 0x1902;
	final int GL_ALPHA = 0x1906;
	final int GL_RGB = 0x1907;
	final int GL_RGBA = 0x1908;
	final int GL_LUMINANCE = 0x1909;
	final int GL_LUMINANCE_ALPHA = 0x190A;
	final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
	final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
	final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
	final int GL_FRAGMENT_SHADER = 0x8B30;
	final int GL_VERTEX_SHADER = 0x8B31;
	final int GL_MAX_VERTEX_ATTRIBS = 0x8869;
	final int GL_MAX_VERTEX_UNIFORM_VECTORS = 0x8DFB;
	final int GL_MAX_VARYING_VECTORS = 0x8DFC;
	final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;
	final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0x8B4C;
	final int GL_MAX_TEXTURE_IMAGE_UNITS = 0x8872;
	final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 0x8DFD;
	final int GL_SHADER_TYPE = 0x8B4F;
	final int GL_DELETE_STATUS = 0x8B80;
	final int GL_LINK_STATUS = 0x8B82;
	final int GL_VALIDATE_STATUS = 0x8B83;
	final int GL_ATTACHED_SHADERS = 0x8B85;
	final int GL_ACTIVE_UNIFORMS = 0x8B86;
	final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 0x8B87;
	final int GL_ACTIVE_ATTRIBUTES = 0x8B89;
	final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;
	final int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;
	final int GL_CURRENT_PROGRAM = 0x8B8D;
	final int GL_NEVER = 0x0200;
	final int GL_LESS = 0x0201;
	final int GL_EQUAL = 0x0202;
	final int GL_LEQUAL = 0x0203;
	final int GL_GREATER = 0x0204;
	final int GL_NOTEQUAL = 0x0205;
	final int GL_GEQUAL = 0x0206;
	final int GL_ALWAYS = 0x0207;
	final int GL_KEEP = 0x1E00;
	final int GL_REPLACE = 0x1E01;
	final int GL_INCR = 0x1E02;
	final int GL_DECR = 0x1E03;
	final int GL_INVERT = 0x150A;
	final int GL_INCR_WRAP = 0x8507;
	final int GL_DECR_WRAP = 0x8508;
	final int GL_VENDOR = 0x1F00;
	final int GL_RENDERER = 0x1F01;
	final int GL_VERSION = 0x1F02;
	final int GL_EXTENSIONS = 0x1F03;
	final int GL_NEAREST = 0x2600;
	final int GL_LINEAR = 0x2601;
	final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
	final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
	final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
	final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
	final int GL_TEXTURE_MAG_FILTER = 0x2800;
	final int GL_TEXTURE_MIN_FILTER = 0x2801;
	final int GL_TEXTURE_WRAP_S = 0x2802;
	final int GL_TEXTURE_WRAP_T = 0x2803;
	final int GL_TEXTURE = 0x1702;
	final int GL_TEXTURE_CUBE_MAP = 0x8513;
	final int GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;
	final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515;
	final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516;
	final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517;
	final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518;
	final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519;
	final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;
	final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;
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
	final int GL_ACTIVE_TEXTURE = 0x84E0;
	final int GL_REPEAT = 0x2901;
	final int GL_CLAMP_TO_EDGE = 0x812F;
	final int GL_MIRRORED_REPEAT = 0x8370;
	final int GL_FLOAT_VEC2 = 0x8B50;
	final int GL_FLOAT_VEC3 = 0x8B51;
	final int GL_FLOAT_VEC4 = 0x8B52;
	final int GL_INT_VEC2 = 0x8B53;
	final int GL_INT_VEC3 = 0x8B54;
	final int GL_INT_VEC4 = 0x8B55;
	final int GL_BOOL = 0x8B56;
	final int GL_BOOL_VEC2 = 0x8B57;
	final int GL_BOOL_VEC3 = 0x8B58;
	final int GL_BOOL_VEC4 = 0x8B59;
	final int GL_FLOAT_MAT2 = 0x8B5A;
	final int GL_FLOAT_MAT3 = 0x8B5B;
	final int GL_FLOAT_MAT4 = 0x8B5C;
	final int GL_SAMPLER_2D = 0x8B5E;
	final int GL_SAMPLER_CUBE = 0x8B60;
	final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 0x8622;
	final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 0x8623;
	final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 0x8624;
	final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 0x8625;
	final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;
	final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;
	final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;
	final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 0x8B9A;
	final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 0x8B9B;
	final int GL_COMPILE_STATUS = 0x8B81;
	final int GL_INFO_LOG_LENGTH = 0x8B84;
	final int GL_SHADER_SOURCE_LENGTH = 0x8B88;
	final int GL_SHADER_COMPILER = 0x8DFA;
	final int GL_SHADER_BINARY_FORMATS = 0x8DF8;
	final int GL_NUM_SHADER_BINARY_FORMATS = 0x8DF9;
	final int GL_LOW_FLOAT = 0x8DF0;
	final int GL_MEDIUM_FLOAT = 0x8DF1;
	final int GL_HIGH_FLOAT = 0x8DF2;
	final int GL_LOW_INT = 0x8DF3;
	final int GL_MEDIUM_INT = 0x8DF4;
	final int GL_HIGH_INT = 0x8DF5;
	final int GL_FRAMEBUFFER = 0x8D40;
	final int GL_RENDERBUFFER = 0x8D41;
	final int GL_RGBA4 = 0x8056;
	final int GL_RGB5_A1 = 0x8057;
	final int GL_RGB565 = 0x8D62;
	final int GL_DEPTH_COMPONENT16 = 0x81A5;
	final int GL_STENCIL_INDEX8 = 0x8D48;
	final int GL_RENDERBUFFER_WIDTH = 0x8D42;
	final int GL_RENDERBUFFER_HEIGHT = 0x8D43;
	final int GL_RENDERBUFFER_INTERNAL_FORMAT = 0x8D44;
	final int GL_RENDERBUFFER_RED_SIZE = 0x8D50;
	final int GL_RENDERBUFFER_GREEN_SIZE = 0x8D51;
	final int GL_RENDERBUFFER_BLUE_SIZE = 0x8D52;
	final int GL_RENDERBUFFER_ALPHA_SIZE = 0x8D53;
	final int GL_RENDERBUFFER_DEPTH_SIZE = 0x8D54;
	final int GL_RENDERBUFFER_STENCIL_SIZE = 0x8D55;
	final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 0x8CD0;
	final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 0x8CD1;
	final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 0x8CD2;
	final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 0x8CD3;
	final int GL_COLOR_ATTACHMENT0 = 0x8CE0;
	final int GL_DEPTH_ATTACHMENT = 0x8D00;
	final int GL_STENCIL_ATTACHMENT = 0x8D20;
	final int GL_NONE = 0;
	final int GL_FRAMEBUFFER_COMPLETE = 0x8CD5;
	final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 0x8CD6;
	final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 0x8CD7;
	final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS = 0x8CD9;
	final int GL_FRAMEBUFFER_UNSUPPORTED = 0x8CDD;
	final int GL_FRAMEBUFFER_BINDING = 0x8CA6;
	final int GL_RENDERBUFFER_BINDING = 0x8CA7;
	final int GL_MAX_RENDERBUFFER_SIZE = 0x84E8;
	final int GL_INVALID_FRAMEBUFFER_OPERATION = 0x0506;

	void glActiveTexture(int texture);

	void glAttachShader(int program, int shader);

	void glBindAttribLocation(int program, int index, CharSequence name);

	void glBindBuffer(int target, int buffer);

	void glBindFramebuffer(int target, int framebuffer);

	void glBindRenderbuffer(int target, int renderbuffer);

	void glBindTexture(int target, int texture);

	void glBlendColor(float red, float green, float blue, float alpha);

	void glBlendEquation(int mode);

	void glBlendEquationSeparate(int modeRGB, int modeAlpha);

	void glBlendFunc(int sfactor, int dfactor);

	void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha);

	void glBufferData(int target, int size, Buffer data, int usage);

	void glBufferSubData(int target, int offset, int size, Buffer data);

	int glCheckFramebufferStatus(int target);

	void glClear(int mask);

	void glClearColor(float red, float green, float blue, float alpha);

	void glClearDepthf(float depth);

	void glClearStencil(int s);

	void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);

	void glCompileShader(int shader);

	void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data);

	void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data);

	void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border);

	void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

	int glCreateProgram();

	int glCreateShader(int type);

	void glCullFace(int mode);

	void glDeleteBuffers(int n, IntBuffer buffers);

	void glDeleteFramebuffers(int n, IntBuffer framebuffers);

	void glDeleteProgram(int program);

	void glDeleteRenderbuffers(int n, IntBuffer renderbuffers);

	void glDeleteShader(int shader);

	void glDeleteTextures(int n, IntBuffer textures);

	void glDepthFunc(int func);

	void glDepthMask(boolean flag);

	void glDepthRangef(float n, float f);

	void glDetachShader(int program, int shader);

	void glDisable(int cap);

	void glDisableVertexAttribArray(int index);

	void glDrawArrays(int mode, int first, int count);

	void glDrawElements(int mode, int count, int type, Buffer indices);

	void glEnable(int cap);

	void glEnableVertexAttribArray(int index);

	void glFinish();

	void glFlush();

	void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer);

	void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level);

	void glFrontFace(int mode);

	void glGenBuffers(int n, IntBuffer buffers);

	void glGenerateMipmap(int target);

	void glGenFramebuffers(int n, IntBuffer framebuffers);

	void glGenRenderbuffers(int n, IntBuffer renderbuffers);

	void glGenTextures(int n, IntBuffer textures);

	void glGetActiveAttrib(int program, int index, int bufSize, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name);

	void glGetActiveUniform(int program, int index, int bufSize, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name);

	void glGetAttachedShaders(int program, int maxCount, IntBuffer count, IntBuffer shaders);

	int glGetAttribLocation(int program, CharSequence name);

	void glGetBooleanv(int pname, ByteBuffer data);

	void glGetBufferParameteriv(int target, int pname, IntBuffer params);

	int glGetError();

	void glGetFloatv(int pname, FloatBuffer data);

	void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params);

	void glGetIntegerv(int pname, IntBuffer data);

	void glGetProgramiv(int program, int pname, IntBuffer params);

	void glGetProgramInfoLog(int program, int bufSize, IntBuffer length, ByteBuffer infoLog);

	void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params);

	void glGetShaderiv(int shader, int pname, IntBuffer params);

	void glGetShaderInfoLog(int shader, int bufSize, IntBuffer length, ByteBuffer infoLog);

	void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision);

	void glGetShaderSource(int shader, int bufSize, IntBuffer length, ByteBuffer source);

	String glGetString(int name);

	void glGetTexParameterfv(int target, int pname, FloatBuffer params);

	void glGetTexParameteriv(int target, int pname, IntBuffer params);

	void glGetUniformfv(int program, int location, FloatBuffer params);

	void glGetUniformiv(int program, int location, IntBuffer params);

	int glGetUniformLocation(int program, CharSequence name);

	void glGetVertexAttribfv(int index, int pname, FloatBuffer params);

	void glGetVertexAttribiv(int index, int pname, IntBuffer params);

	ByteBuffer glGetVertexAttribPointerv(int index, int pname, long result_size);

	void glHint(int target, int mode);

	boolean glIsBuffer(int buffer);

	boolean glIsEnabled(int cap);

	boolean glIsFramebuffer(int framebuffer);

	boolean glIsProgram(int program);

	boolean glIsRenderbuffer(int renderbuffer);

	boolean glIsShader(int shader);

	boolean glIsTexture(int texture);

	void glLineWidth(float width);

	void glLinkProgram(int program);

	void glPixelStorei(int pname, int param);

	void glPolygonOffset(float factor, float units);

	void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels);

	void glReleaseShaderCompiler();

	void glRenderbufferStorage(int target, int internalformat, int width, int height);

	void glSampleCoverage(float value, boolean invert);

	void glScissor(int x, int y, int width, int height);

	void glShaderBinary(int count, IntBuffer shaders, int binaryformat, Buffer binary, int length);

	void glShaderSource(int shader, int count, String string, IntBuffer length);

	void glStencilFunc(int func, int ref, int mask);

	void glStencilFuncSeparate(int face, int func, int ref, int mask);

	void glStencilMask(int mask);

	void glStencilMaskSeparate(int face, int mask);

	void glStencilOp(int fail, int zfail, int zpass);

	void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass);

	void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels);

	void glTexParameterf(int target, int pname, float param);

	void glTexParameterfv(int target, int pname, FloatBuffer params);

	void glTexParameteri(int target, int pname, int param);

	void glTexParameteriv(int target, int pname, IntBuffer params);

	void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);

	void glUniform1f(int location, float v0);

	void glUniform1fv(int location, int count, FloatBuffer value);

	void glUniform1i(int location, int v0);

	void glUniform1iv(int location, int count, IntBuffer value);

	void glUniform2f(int location, float v0, float v1);

	void glUniform2fv(int location, int count, FloatBuffer value);

	void glUniform2i(int location, int v0, int v1);

	void glUniform2iv(int location, int count, IntBuffer value);

	void glUniform3f(int location, float v0, float v1, float v2);

	void glUniform3fv(int location, int count, FloatBuffer value);

	void glUniform3i(int location, int v0, int v1, int v2);

	void glUniform3iv(int location, int count, IntBuffer value);

	void glUniform4f(int location, float v0, float v1, float v2, float v3);

	void glUniform4fv(int location, int count, FloatBuffer value);

	void glUniform4i(int location, int v0, int v1, int v2, int v3);

	void glUniform4iv(int location, int count, IntBuffer value);

	void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value);

	void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value);

	void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value);

	void glUseProgram(int program);

	void glValidateProgram(int program);

	void glVertexAttrib1f(int index, float x);

	void glVertexAttrib1fv(int index, FloatBuffer v);

	void glVertexAttrib2f(int index, float x, float y);

	void glVertexAttrib2fv(int index, FloatBuffer v);

	void glVertexAttrib3f(int index, float x, float y, float z);

	void glVertexAttrib3fv(int index, FloatBuffer v);

	void glVertexAttrib4f(int index, float x, float y, float z, float w);

	void glVertexAttrib4fv(int index, FloatBuffer v);

	void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, Buffer pointer);

	void glViewport(int x, int y, int width, int height);
}
