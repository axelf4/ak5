/**
 * 
 */
package org.gamelib.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** Constants and commands from OpenGL 1.x, specifically OpenGL 1.5 and OpenGL ES 1.1.
 * 
 * @author pwnedary */
public interface GL11 extends GL10 {
	/* ClearBufferMask */
	int GL_DEPTH_BUFFER_BIT = 0x00000100;
	int GL_STENCIL_BUFFER_BIT = 0x00000400;
	int GL_COLOR_BUFFER_BIT = 0x00004000;
	/* Boolean */
	int GL_FALSE = 0;
	int GL_TRUE = 1;
	/* BeginMode */
	int GL_POINTS = 0x0000;
	int GL_LINES = 0x0001;
	int GL_LINE_LOOP = 0x0002;
	int GL_LINE_STRIP = 0x0003;
	int GL_TRIANGLES = 0x0004;
	int GL_TRIANGLE_STRIP = 0x0005;
	int GL_TRIANGLE_FAN = 0x0006;
	/* AlphaFunction */
	int GL_NEVER = 0x0200;
	int GL_LESS = 0x0201;
	int GL_EQUAL = 0x0202;
	int GL_LEQUAL = 0x0203;
	int GL_GREATER = 0x0204;
	int GL_NOTEQUAL = 0x0205;
	int GL_GEQUAL = 0x0206;
	int GL_ALWAYS = 0x0207;
	/* BlendingFactorDest */
	int GL_ZERO = 0;
	int GL_ONE = 1;
	int GL_SRC_COLOR = 0x0300;
	int GL_ONE_MINUS_SRC_COLOR = 0x0301;
	int GL_SRC_ALPHA = 0x0302;
	int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
	int GL_DST_ALPHA = 0x0304;
	int GL_ONE_MINUS_DST_ALPHA = 0x0305;
	/* BlendingFactorSrc */
	/* GL_ZERO */
	/* GL_ONE */
	int GL_DST_COLOR = 0x0306;
	int GL_ONE_MINUS_DST_COLOR = 0x0307;
	int GL_SRC_ALPHA_SATURATE = 0x0308;
	/* GL_SRC_ALPHA */
	/* GL_ONE_MINUS_SRC_ALPHA */
	/* GL_DST_ALPHA */
	/* GL_ONE_MINUS_DST_ALPHA */
	/* ClipPlaneName */
	int GL_CLIP_PLANE0 = 0x3000;
	int GL_CLIP_PLANE1 = 0x3001;
	int GL_CLIP_PLANE2 = 0x3002;
	int GL_CLIP_PLANE3 = 0x3003;
	int GL_CLIP_PLANE4 = 0x3004;
	int GL_CLIP_PLANE5 = 0x3005;
	/* ColorMaterialFace */
	/* GL_FRONT_AND_BACK */
	/* ColorMaterialParameter */
	/* GL_AMBIENT_AND_DIFFUSE */
	/* ColorPointerType */
	/* GL_UNSIGNED_BYTE */
	/* GL_FloatBuffer / /* GL_FIXED */
	/* CullFaceMode */
	int GL_FRONT = 0x0404;
	int GL_BACK = 0x0405;
	int GL_FRONT_AND_BACK = 0x0408;
	/* DepthFunction */
	/* GL_NEVER */
	/* GL_LESS */
	/* GL_EQUAL */
	/* GL_LEQUAL */
	/* GL_GREATER */
	/* GL_NOTEQUAL */
	/* GL_GEQUAL */
	/* GL_ALWAYS */
	/* EnableCap */
	int GL_FOG = 0x0B60;
	int GL_LIGHTING = 0x0B50;
	int GL_TEXTURE_2D = 0x0DE1;
	int GL_CULL_FACE = 0x0B44;
	int GL_ALPHA_TEST = 0x0BC0;
	int GL_BLEND = 0x0BE2;
	int GL_COLOR_LOGIC_OP = 0x0BF2;
	int GL_DITHER = 0x0BD0;
	int GL_STENCIL_TEST = 0x0B90;
	int GL_DEPTH_TEST = 0x0B71;
	/* GL_LIGHT0 */
	/* GL_LIGHT1 */
	/* GL_LIGHT2 */
	/* GL_LIGHT3 */
	/* GL_LIGHT4 */
	/* GL_LIGHT5 */
	/* GL_LIGHT6 */
	/* GL_LIGHT7 */
	int GL_POINT_SMOOTH = 0x0B10;
	int GL_LINE_SMOOTH = 0x0B20;
	int GL_SCISSOR_TEST = 0x0C11;
	int GL_COLOR_MATERIAL = 0x0B57;
	int GL_NORMALIZE = 0x0BA1;
	int GL_RESCALE_NORMAL = 0x803A;
	int GL_POLYGON_OFFSET_FILL = 0x8037;
	int GL_VERTEX_ARRAY = 0x8074;
	int GL_NORMAL_ARRAY = 0x8075;
	int GL_COLOR_ARRAY = 0x8076;
	int GL_TEXTURE_COORD_ARRAY = 0x8078;
	int GL_MULTISAMPLE = 0x809D;
	int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
	int GL_SAMPLE_ALPHA_TO_ONE = 0x809F;
	int GL_SAMPLE_COVERAGE = 0x80A0;
	/* ErrorCode */
	int GL_NO_ERROR = 0;
	int GL_INVALID_ENUM = 0x0500;
	int GL_INVALID_VALUE = 0x0501;
	int GL_INVALID_OPERATION = 0x0502;
	int GL_STACK_OVERFLOW = 0x0503;
	int GL_STACK_UNDERFLOW = 0x0504;
	int GL_OUT_OF_MEMORY = 0x0505;
	/* FogMode */
	/* GL_LINEAR */
	int GL_EXP = 0x0800;
	int GL_EXP2 = 0x0801;
	/* FogParameter */
	int GL_FOG_DENSITY = 0x0B62;
	int GL_FOG_START = 0x0B63;
	int GL_FOG_END = 0x0B64;
	int GL_FOG_MODE = 0x0B65;
	int GL_FOG_COLOR = 0x0B66;
	/* FrontFaceDirection */
	int GL_CW = 0x0900;
	int GL_CCW = 0x0901;
	/* GetPName */
	int GL_CURRENT_COLOR = 0x0B00;
	int GL_CURRENT_NORMAL = 0x0B02;
	int GL_CURRENT_TEXTURE_COORDS = 0x0B03;
	int GL_POINT_SIZE = 0x0B11;
	int GL_POINT_SIZE_MIN = 0x8126;
	int GL_POINT_SIZE_MAX = 0x8127;
	int GL_POINT_FADE_THRESHOLD_SIZE = 0x8128;
	int GL_POINT_DISTANCE_ATTENUATION = 0x8129;
	int GL_SMOOTH_POINT_SIZE_RANGE = 0x0B12;
	int GL_LINE_WIDTH = 0x0B21;
	int GL_SMOOTH_LINE_WIDTH_RANGE = 0x0B22;
	int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
	int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
	int GL_CULL_FACE_MODE = 0x0B45;
	int GL_FRONT_FACE = 0x0B46;
	int GL_SHADE_MODEL = 0x0B54;
	int GL_DEPTH_RANGE = 0x0B70;
	int GL_DEPTH_WRITEMASK = 0x0B72;
	int GL_DEPTH_CLEAR_VALUE = 0x0B73;
	int GL_DEPTH_FUNC = 0x0B74;
	int GL_STENCIL_CLEAR_VALUE = 0x0B91;
	int GL_STENCIL_FUNC = 0x0B92;
	int GL_STENCIL_VALUE_MASK = 0x0B93;
	int GL_STENCIL_FAIL = 0x0B94;
	int GL_STENCIL_PASS_DEPTH_FAIL = 0x0B95;
	int GL_STENCIL_PASS_DEPTH_PASS = 0x0B96;
	int GL_STENCIL_REF = 0x0B97;
	int GL_STENCIL_WRITEMASK = 0x0B98;
	int GL_MATRIX_MODE = 0x0BA0;
	int GL_VIEWPORT = 0x0BA2;
	int GL_MODELVIEW_STACK_DEPTH = 0x0BA3;
	int GL_PROJECTION_STACK_DEPTH = 0x0BA4;
	int GL_TEXTURE_STACK_DEPTH = 0x0BA5;
	int GL_MODELVIEW_MATRIX = 0x0BA6;
	int GL_PROJECTION_MATRIX = 0x0BA7;
	int GL_TEXTURE_MATRIX = 0x0BA8;
	int GL_ALPHA_TEST_FUNC = 0x0BC1;
	int GL_ALPHA_TEST_REF = 0x0BC2;
	int GL_BLEND_DST = 0x0BE0;
	int GL_BLEND_SRC = 0x0BE1;
	int GL_LOGIC_OP_MODE = 0x0BF0;
	int GL_SCISSOR_BOX = 0x0C10;
	/* int GL_SCISSOR_TEST = 0x0C11; */
	int GL_COLOR_CLEAR_VALUE = 0x0C22;
	int GL_COLOR_WRITEMASK = 0x0C23;
	int GL_UNPACK_ALIGNMENT = 0x0CF5;
	int GL_PACK_ALIGNMENT = 0x0D05;
	int GL_MAX_LIGHTS = 0x0D31;
	int GL_MAX_CLIP_PLANES = 0x0D32;
	int GL_MAX_TEXTURE_SIZE = 0x0D33;
	int GL_MAX_MODELVIEW_STACK_DEPTH = 0x0D36;
	int GL_MAX_PROJECTION_STACK_DEPTH = 0x0D38;
	int GL_MAX_TEXTURE_STACK_DEPTH = 0x0D39;
	int GL_MAX_VIEWPORT_DIMS = 0x0D3A;
	int GL_MAX_TEXTURE_UNITS = 0x84E2;
	int GL_SUBPIXEL_BITS = 0x0D50;
	int GL_RED_BITS = 0x0D52;
	int GL_GREEN_BITS = 0x0D53;
	int GL_BLUE_BITS = 0x0D54;
	int GL_ALPHA_BITS = 0x0D55;
	int GL_DEPTH_BITS = 0x0D56;
	int GL_STENCIL_BITS = 0x0D57;
	int GL_POLYGON_OFFSET_UNITS = 0x2A00;
	/* int GL_POLYGON_OFFSET_FILL = 0x8037; */
	int GL_POLYGON_OFFSET_FACTOR = 0x8038;
	int GL_TEXTURE_BINDING_2D = 0x8069;
	int GL_VERTEX_ARRAY_SIZE = 0x807A;
	int GL_VERTEX_ARRAY_TYPE = 0x807B;
	int GL_VERTEX_ARRAY_STRIDE = 0x807C;
	int GL_NORMAL_ARRAY_TYPE = 0x807E;
	int GL_NORMAL_ARRAY_STRIDE = 0x807F;
	int GL_COLOR_ARRAY_SIZE = 0x8081;
	int GL_COLOR_ARRAY_TYPE = 0x8082;
	int GL_COLOR_ARRAY_STRIDE = 0x8083;
	int GL_TEXTURE_COORD_ARRAY_SIZE = 0x8088;
	int GL_TEXTURE_COORD_ARRAY_TYPE = 0x8089;
	int GL_TEXTURE_COORD_ARRAY_STRIDE = 0x808A;
	int GL_VERTEX_ARRAY_POINTER = 0x808E;
	int GL_NORMAL_ARRAY_POINTER = 0x808F;
	int GL_COLOR_ARRAY_POINTER = 0x8090;
	int GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092;
	int GL_SAMPLE_BUFFERS = 0x80A8;
	int GL_SAMPLES = 0x80A9;
	int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
	int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
	/* GetTextureParameter */
	/* GL_TEXTURE_MAG_FILTER */
	/* GL_TEXTURE_MIN_FILTER */
	/* GL_TEXTURE_WRAP_S */
	/* GL_TEXTURE_WRAP_T */
	int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
	int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
	/* HintMode */
	int GL_DONT_CARE = 0x1100;
	int GL_FASTEST = 0x1101;
	int GL_NICEST = 0x1102;
	/* HintTarget */
	int GL_PERSPECTIVE_CORRECTION_HINT = 0x0C50;
	int GL_POINT_SMOOTH_HINT = 0x0C51;
	int GL_LINE_SMOOTH_HINT = 0x0C52;
	int GL_FOG_HINT = 0x0C54;
	int GL_GENERATE_MIPMAP_HINT = 0x8192;
	/* LightModelParameter */
	int GL_LIGHT_MODEL_AMBIENT = 0x0B53;
	int GL_LIGHT_MODEL_TWO_SIDE = 0x0B52;
	/* LightParameter */
	int GL_AMBIENT = 0x1200;
	int GL_DIFFUSE = 0x1201;
	int GL_SPECULAR = 0x1202;
	int GL_POSITION = 0x1203;
	int GL_SPOT_DIRECTION = 0x1204;
	int GL_SPOT_EXPONENT = 0x1205;
	int GL_SPOT_CUTOFF = 0x1206;
	int GL_CONSTANT_ATTENUATION = 0x1207;
	int GL_LINEAR_ATTENUATION = 0x1208;
	int GL_QUADRATIC_ATTENUATION = 0x1209;
	/* DataType */
	int GL_BYTE = 0x1400;
	int GL_UNSIGNED_BYTE = 0x1401;
	int GL_SHORT = 0x1402;
	int GL_UNSIGNED_SHORT = 0x1403;
	int GL_FLOAT = 0x1406;
	int GL_FIXED = 0x140C;
	/* LogicOp */
	int GL_CLEAR = 0x1500;
	int GL_AND = 0x1501;
	int GL_AND_REVERSE = 0x1502;
	int GL_COPY = 0x1503;
	int GL_AND_INVERTED = 0x1504;
	int GL_NOOP = 0x1505;
	int GL_XOR = 0x1506;
	int GL_OR = 0x1507;
	int GL_NOR = 0x1508;
	int GL_EQUIV = 0x1509;
	int GL_INVERT = 0x150A;
	int GL_OR_REVERSE = 0x150B;
	int GL_COPY_INVERTED = 0x150C;
	int GL_OR_INVERTED = 0x150D;
	int GL_NAND = 0x150E;
	int GL_SET = 0x150F;
	/* MaterialFace */
	/* GL_FRONT_AND_BACK */
	/* MaterialParameter */
	int GL_EMISSION = 0x1600;
	int GL_SHININESS = 0x1601;
	int GL_AMBIENT_AND_DIFFUSE = 0x1602;
	/* GL_AMBIENT */
	/* GL_DIFFUSE */
	/* GL_SPECULAR */
	/* MatrixMode */
	int GL_MODELVIEW = 0x1700;
	int GL_PROJECTION = 0x1701;
	int GL_TEXTURE = 0x1702;
	/* NormalPointerType */
	/* GL_BYTE */
	/* GL_SHORT */
	/* GL_FloatBuffer / /* GL_FIXED */
	/* PixelFormat */
	int GL_ALPHA = 0x1906;
	int GL_RGB = 0x1907;
	int GL_RGBA = 0x1908;
	int GL_LUMINANCE = 0x1909;
	int GL_LUMINANCE_ALPHA = 0x190A;
	/* PixelStoreParameter */
	/* int GL_UNPACK_ALIGNMENT =0x0CF5; */
	/* int GL_PACK_ALIGNMENT =0x0D05 */
	/* PixelType */
	/* GL_UNSIGNED_BYTE */
	int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
	int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
	int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
	/* ShadingModel */
	int GL_FLAT = 0x1D00;
	int GL_SMOOTH = 0x1D01;
	/* StencilFunction */
	/* GL_NEVER */
	/* GL_LESS */
	/* GL_EQUAL */
	/* GL_LEQUAL */
	/* GL_GREATER */
	/* GL_NOTEQUAL */
	/* GL_GEQUAL */
	/* GL_ALWAYS */
	/* StencilOp */
	/* GL_ZERO */
	int GL_KEEP = 0x1E00;
	int GL_REPLACE = 0x1E01;
	int GL_INCR = 0x1E02;
	int GL_DECR = 0x1E03;
	/* GL_INVERT */
	/* StringName */
	int GL_VENDOR = 0x1F00;
	int GL_RENDERER = 0x1F01;
	int GL_VERSION = 0x1F02;
	int GL_EXTENSIONS = 0x1F03;
	/* TexCoordPointerType */
	/* GL_SHORT */
	/* GL_FloatBuffer / /* GL_FIXED */
	/* GL_BYTE */
	/* TextureEnvMode */
	int GL_MODULATE = 0x2100;
	int GL_DECAL = 0x2101;
	/* GL_BLEND */
	int GL_ADD = 0x0104;
	/* GL_REPLACE */
	/* TextureEnvParameter */
	int GL_TEXTURE_ENV_MODE = 0x2200;
	int GL_TEXTURE_ENV_COLOR = 0x2201;
	/* TextureEnvTarget */
	int GL_TEXTURE_ENV = 0x2300;
	/* TextureMagFilter */
	int GL_NEAREST = 0x2600;
	int GL_LINEAR = 0x2601;
	/* TextureMinFilter */
	/* GL_NEAREST */
	/* GL_LINEAR */
	int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
	int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
	int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
	int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
	/* TextureParameterName */
	int GL_TEXTURE_MAG_FILTER = 0x2800;
	int GL_TEXTURE_MIN_FILTER = 0x2801;
	int GL_TEXTURE_WRAP_S = 0x2802;
	int GL_TEXTURE_WRAP_T = 0x2803;
	int GL_GENERATE_MIPMAP = 0x8191;
	/* TextureTarget */
	/* GL_TEXTURE_2D */
	/* TextureUnit */
	int GL_TEXTURE0 = 0x84C0;
	int GL_TEXTURE1 = 0x84C1;
	int GL_TEXTURE2 = 0x84C2;
	int GL_TEXTURE3 = 0x84C3;
	int GL_TEXTURE4 = 0x84C4;
	int GL_TEXTURE5 = 0x84C5;
	int GL_TEXTURE6 = 0x84C6;
	int GL_TEXTURE7 = 0x84C7;
	int GL_TEXTURE8 = 0x84C8;
	int GL_TEXTURE9 = 0x84C9;
	int GL_TEXTURE10 = 0x84CA;
	int GL_TEXTURE11 = 0x84CB;
	int GL_TEXTURE12 = 0x84CC;
	int GL_TEXTURE13 = 0x84CD;
	int GL_TEXTURE14 = 0x84CE;
	int GL_TEXTURE15 = 0x84CF;
	int GL_TEXTURE16 = 0x84D0;
	int GL_TEXTURE17 = 0x84D1;
	int GL_TEXTURE18 = 0x84D2;
	int GL_TEXTURE19 = 0x84D3;
	int GL_TEXTURE20 = 0x84D4;
	int GL_TEXTURE21 = 0x84D5;
	int GL_TEXTURE22 = 0x84D6;
	int GL_TEXTURE23 = 0x84D7;
	int GL_TEXTURE24 = 0x84D8;
	int GL_TEXTURE25 = 0x84D9;
	int GL_TEXTURE26 = 0x84DA;
	int GL_TEXTURE27 = 0x84DB;
	int GL_TEXTURE28 = 0x84DC;
	int GL_TEXTURE29 = 0x84DD;
	int GL_TEXTURE30 = 0x84DE;
	int GL_TEXTURE31 = 0x84DF;
	int GL_ACTIVE_TEXTURE = 0x84E0;
	int GL_CLIENT_ACTIVE_TEXTURE = 0x84E1;
	/* TextureWrapMode */
	int GL_REPEAT = 0x2901;
	int GL_CLAMP_TO_EDGE = 0x812F;
	/* VertexPointerType */
	/* GL_SHORT */
	/* GL_FloatBuffer / /* GL_FIXED */
	/* GL_BYTE */
	/* LightName */
	int GL_LIGHT0 = 0x4000;
	int GL_LIGHT1 = 0x4001;
	int GL_LIGHT2 = 0x4002;
	int GL_LIGHT3 = 0x4003;
	int GL_LIGHT4 = 0x4004;
	int GL_LIGHT5 = 0x4005;
	int GL_LIGHT6 = 0x4006;
	int GL_LIGHT7 = 0x4007;
	/* Buffer Objects */
	int GL_ARRAY_BUFFER = 0x8892;
	int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
	int GL_ARRAY_BUFFER_BINDING = 0x8894;
	int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
	int GL_VERTEX_ARRAY_BUFFER_BINDING = 0x8896;
	int GL_NORMAL_ARRAY_BUFFER_BINDING = 0x8897;
	int GL_COLOR_ARRAY_BUFFER_BINDING = 0x8898;
	int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 0x889A;
	int GL_STATIC_DRAW = 0x88E4;
	int GL_DYNAMIC_DRAW = 0x88E8;
	int GL_BUFFER_SIZE = 0x8764;
	int GL_BUFFER_USAGE = 0x8765;
	/* Texture combine + dot3 */
	int GL_SUBTRACT = 0x84E7;
	int GL_COMBINE = 0x8570;
	int GL_COMBINE_RGB = 0x8571;
	int GL_COMBINE_ALPHA = 0x8572;
	int GL_RGB_SCALE = 0x8573;
	int GL_ADD_SIGNED = 0x8574;
	int GL_INTERPOLATE = 0x8575;
	int GL_CONSTANT = 0x8576;
	int GL_PRIMARY_COLOR = 0x8577;
	int GL_PREVIOUS = 0x8578;
	int GL_OPERAND0_RGB = 0x8590;
	int GL_OPERAND1_RGB = 0x8591;
	int GL_OPERAND2_RGB = 0x8592;
	int GL_OPERAND0_ALPHA = 0x8598;
	int GL_OPERAND1_ALPHA = 0x8599;
	int GL_OPERAND2_ALPHA = 0x859A;
	int GL_ALPHA_SCALE = 0x0D1C;
	int GL_SRC0_RGB = 0x8580;
	int GL_SRC1_RGB = 0x8581;
	int GL_SRC2_RGB = 0x8582;
	int GL_SRC0_ALPHA = 0x8588;
	int GL_SRC1_ALPHA = 0x8589;
	int GL_SRC2_ALPHA = 0x858A;
	int GL_DOT3_RGB = 0x86AE;
	int GL_DOT3_RGBA = 0x86AF;

	/* Available only in Common profile */
	void glAlphaFunc(int func, float ref);

	void glClearColor(float red, float green, float blue, float alpha);

	void glClearDepthf(float depth);

	void glClipPlanef(int plane, FloatBuffer equation);

	void glColor4f(float red, float green, float blue, float alpha);

	void glDepthRangef(float zNear, float zFar);

	void glFogf(int pname, float param);

	void glFogfv(int pname, FloatBuffer params);

	void glFrustumf(float left, float right, float bottom, float top, float zNear, float zFar);

	void glGetClipPlanef(int pname, FloatBuffer eqn);

	void glGetFloatv(int pname, FloatBuffer params);

	void glGetLightfv(int light, int pname, FloatBuffer params);

	void glGetMaterialfv(int face, int pname, FloatBuffer params);

	void glGetTexEnvfv(int env, int pname, FloatBuffer params);

	void glGetTexParameterfv(int target, int pname, FloatBuffer params);

	void glLightModelf(int pname, float param);

	void glLightModelfv(int pname, FloatBuffer params);

	void glLightf(int light, int pname, float param);

	void glLightfv(int light, int pname, FloatBuffer params);

	void glLineWidth(float width);

	void glLoadMatrixf(FloatBuffer m);

	void glMaterialf(int face, int pname, float param);

	void glMaterialfv(int face, int pname, FloatBuffer params);

	void glMultMatrixf(FloatBuffer m);

	void glMultiTexCoord4f(int target, float s, float t, float r, float q);

	void glNormal3f(float nx, float ny, float nz);

	void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar);

	void glPointParameterf(int pname, float param);

	void glPointParameterfv(int pname, FloatBuffer params);

	void glPointSize(float size);

	void glPolygonOffset(float factor, float units);

	void glRotatef(float angle, float x, float y, float z);

	void glScalef(float x, float y, float z);

	void glTexEnvf(int target, int pname, float param);

	void glTexEnvfv(int target, int pname, FloatBuffer params);

	void glTexParameterf(int target, int pname, float param);

	void glTexParameterfv(int target, int pname, FloatBuffer params);

	void glTranslatef(float x, float y, float z);

	/* Available in both Common and Common-Lite profiles */
	void glActiveTexture(int texture);

	void glAlphaFuncx(int func, int ref);

	void glBindBuffer(int target, int buffer);

	void glBindTexture(int target, int texture);

	void glBlendFunc(int sfactor, int dfactor);

	void glBufferData(int target, int size, Buffer data, int usage);

	void glBufferSubData(int target, int offset, int size, Buffer data);

	void glClear(int mask);

	void glClearColorx(int red, int green, int blue, int alpha);

	void glClearDepthx(int depth);

	void glClearStencil(int s);

	void glClientActiveTexture(int texture);

	void glClipPlanex(int plane, IntBuffer equation);

	void glColor4ub(byte red, byte green, byte blue, byte alpha);

	void glColor4x(int red, int green, int blue, int alpha);

	void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);

	void glColorPointer(int size, int type, int stride, Buffer pointer);

	void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data);

	void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data);

	void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border);

	void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

	void glCullFace(int mode);

	void glDeleteBuffers(int n, IntBuffer buffers);

	void glDeleteTextures(int n, IntBuffer textures);

	void glDepthFunc(int func);

	void glDepthMask(boolean flag);

	void glDepthRangex(int zNear, int zFar);

	void glDisable(int cap);

	void glDisableClientState(int array);

	void glDrawArrays(int mode, int first, int count);

	void glDrawElements(int mode, int count, int type, Buffer indices);
	
	void glDrawElements(int mode, int count, int type, int offset);

	void glEnable(int cap);

	void glEnableClientState(int array);

	void glFinish();

	void glFlush();

	void glFogx(int pname, int param);

	void glFogxv(int pname, IntBuffer params);

	void glFrontFace(int mode);

	void glFrustumx(int left, int right, int bottom, int top, int zNear, int zFar);

	void glGetBooleanv(int pname, ByteBuffer params);

	void glGetBufferParameteriv(int target, int pname, IntBuffer params);

	void glGetClipPlanex(int pname, IntBuffer eqn);

	void glGenBuffers(int n, IntBuffer buffers);

	void glGenTextures(int n, IntBuffer textures);

	int glGetError();

	void glGetFixedv(int pname, IntBuffer params);

	void glGetIntegerv(int pname, IntBuffer params);

	void glGetLightxv(int light, int pname, IntBuffer params);

	void glGetMaterialxv(int face, int pname, IntBuffer params);

	void glGetPointerv(int pname, Buffer[] params);

	String glGetString(int name);

	void glGetTexEnviv(int env, int pname, IntBuffer params);

	void glGetTexEnvxv(int env, int pname, IntBuffer params);

	void glGetTexParameteriv(int target, int pname, IntBuffer params);

	void glGetTexParameterxv(int target, int pname, IntBuffer params);

	void glHint(int target, int mode);

	boolean glIsBuffer(int buffer);

	boolean glIsEnabled(int cap);

	boolean glIsTexture(int texture);

	void glLightModelx(int pname, int param);

	void glLightModelxv(int pname, IntBuffer params);

	void glLightx(int light, int pname, int param);

	void glLightxv(int light, int pname, IntBuffer params);

	void glLineWidthx(int width);

	void glLoadIdentity();

	void glLoadMatrixx(IntBuffer m);

	void glLogicOp(int opcode);

	void glMaterialx(int face, int pname, int param);

	void glMaterialxv(int face, int pname, IntBuffer params);

	void glMatrixMode(int mode);

	void glMultMatrixx(IntBuffer m);

	void glMultiTexCoord4x(int target, int s, int t, int r, int q);

	void glNormal3x(int nx, int ny, int nz);

	void glNormalPointer(int type, int stride, Buffer pointer);

	void glOrthox(int left, int right, int bottom, int top, int zNear, int zFar);

	void glPixelStorei(int pname, int param);

	void glPointParameterx(int pname, int param);

	void glPointParameterxv(int pname, IntBuffer params);

	void glPointSizex(int size);

	void glPolygonOffsetx(int factor, int units);

	void glPopMatrix();

	void glPushMatrix();

	void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels);

	void glRotatex(int angle, int x, int y, int z);

	void glSampleCoverage(float value, boolean invert);

	void glSampleCoveragex(int value, boolean invert);

	void glScalex(int x, int y, int z);

	void glScissor(int x, int y, int width, int height);

	void glShadeModel(int mode);

	void glStencilFunc(int func, int ref, int mask);

	void glStencilMask(int mask);

	void glStencilOp(int fail, int zfail, int zpass);

	void glTexCoordPointer(int size, int type, int stride, Buffer pointer);

	void glTexEnvi(int target, int pname, int param);

	void glTexEnvx(int target, int pname, int param);

	void glTexEnviv(int target, int pname, IntBuffer params);

	void glTexEnvxv(int target, int pname, IntBuffer params);

	void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels);

	void glTexParameteri(int target, int pname, int param);

	void glTexParameterx(int target, int pname, int param);

	void glTexParameteriv(int target, int pname, IntBuffer params);

	void glTexParameterxv(int target, int pname, IntBuffer params);

	void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels);

	void glTranslatex(int x, int y, int z);

	void glVertexPointer(int size, int type, int stride, Buffer pointer);

	void glViewport(int x, int y, int width, int height);
}
