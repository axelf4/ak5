/**
 * 
 */
package org.gamelib.backend.gwt;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gamelib.graphics.GL20;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.array.Int16Array;
import com.googlecode.gwtgl.array.Int32Array;
import com.googlecode.gwtgl.array.Uint8Array;
import com.googlecode.gwtgl.binding.WebGLActiveInfo;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLFramebuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderbuffer;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLTexture;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

/** @author pwnedary */
public class GwtGL20 implements GL20 {
	private final WebGLRenderingContext gl;
	final Map<Integer, WebGLProgram> programs = new HashMap<Integer, WebGLProgram>();
	int nextProgramId = 1;
	final Map<Integer, WebGLShader> shaders = new HashMap<Integer, WebGLShader>();
	int nextShaderId = 1;
	final Map<Integer, WebGLBuffer> buffers = new HashMap<Integer, WebGLBuffer>();
	int nextBufferId = 1;
	final Map<Integer, WebGLFramebuffer> frameBuffers = new HashMap<Integer, WebGLFramebuffer>();
	int nextFrameBufferId = 1;
	final Map<Integer, WebGLRenderbuffer> renderBuffers = new HashMap<Integer, WebGLRenderbuffer>();
	int nextRenderBufferId = 1;
	final Map<Integer, WebGLTexture> textures = new HashMap<Integer, WebGLTexture>();
	int nextTextureId = 1;
	final Map<Integer, Map<Integer, WebGLUniformLocation>> uniforms = new HashMap<Integer, Map<Integer, WebGLUniformLocation>>();
	int nextUniformId = 1;
	int currProgram = 0;

	Float32Array floatBuffer = Float32Array.create(2000 * 20);
	Int32Array intBuffer = Int32Array.create(2000 * 6);
	Int16Array shortBuffer = Int16Array.create(2000 * 6);
	float[] floatArray = new float[16000];

	public GwtGL20(WebGLRenderingContext gl) {
		this.gl = gl;
		gl.pixelStorei(WebGLRenderingContext.UNPACK_PREMULTIPLY_ALPHA_WEBGL, 0);
	}

	public WebGLRenderingContext getWebGLRenderingContext() {
		return gl;
	}

	private void ensureCapacity(FloatBuffer buffer) {
		if (buffer.remaining() > floatBuffer.getLength()) {
			floatBuffer = Float32Array.create(buffer.remaining());
		}
	}

	private void ensureCapacity(ShortBuffer buffer) {
		if (buffer.remaining() > shortBuffer.getLength()) {
			shortBuffer = Int16Array.create(buffer.remaining());
		}
	}

	private void ensureCapacity(IntBuffer buffer) {
		if (buffer.remaining() > intBuffer.getLength()) {
			intBuffer = Int32Array.create(buffer.remaining());
		}
	}

	public Float32Array copy(FloatBuffer buffer) {
		//		if (GWT.isProdMode()) {
		//			return ((Float32Array) ((HasArrayBufferView) buffer).getTypedArray()).subarray(buffer.position(), buffer.remaining());
		//		} else {
		ensureCapacity(buffer);
		for (int i = buffer.position(), j = 0; i < buffer.limit(); i++, j++) {
			floatBuffer.set(j, buffer.get(i));
		}
		return floatBuffer.subarray(0, buffer.remaining());
		//		}
	}

	public Int16Array copy(ShortBuffer buffer) {
		//		if (GWT.isProdMode()) {
		//			return ((Int16Array) ((HasArrayBufferView) buffer).getTypedArray()).subarray(buffer.position(), buffer.remaining());
		//		} else {
		ensureCapacity(buffer);
		for (int i = buffer.position(), j = 0; i < buffer.limit(); i++, j++) {
			shortBuffer.set(j, buffer.get(i));
		}
		return shortBuffer.subarray(0, buffer.remaining());
		//		}
	}

	public Int32Array copy(IntBuffer buffer) {
		//		if (GWT.isProdMode()) {
		//			return ((Int32Array) ((HasArrayBufferView) buffer).getTypedArray()).subarray(buffer.position(), buffer.remaining());
		//		} else {
		ensureCapacity(buffer);
		for (int i = buffer.position(), j = 0; i < buffer.limit(); i++, j++) {
			intBuffer.set(j, buffer.get(i));
		}
		return intBuffer.subarray(0, buffer.remaining());
		//		}
	}

	private int allocateUniformLocationId(int program, WebGLUniformLocation location) {
		Map<Integer, WebGLUniformLocation> progUniforms = uniforms.get(program);
		if (progUniforms == null) {
			progUniforms = new HashMap<Integer, WebGLUniformLocation>();
			uniforms.put(program, progUniforms);
		}
		// FIXME check if uniform already stored.
		int id = nextUniformId++;
		progUniforms.put(id, location);
		return id;
	}

	private WebGLUniformLocation getUniformLocation(int location) {
		return uniforms.get(currProgram).get(location);
	}

	private <T> int allocateId(Map<Integer, T> map, T value) {
		int id = map.size();
		map.put(id, value);
		return id;
	}

	private void deallocateId(Map<Integer, ?> map, int id) {
		map.remove(id);
	}

	private int allocateShaderId(WebGLShader shader) {
		int id = nextShaderId++;
		shaders.put(id, shader);
		return id;
	}

	private void deallocateShaderId(int id) {
		shaders.remove(id);
	}

	private int allocateProgramId(WebGLProgram program) {
		int id = nextProgramId++;
		programs.put(id, program);
		return id;
	}

	private void deallocateProgramId(int id) {
		uniforms.remove(id);
		programs.remove(id);
	}

	private int allocateBufferId(WebGLBuffer buffer) {
		int id = nextBufferId++;
		buffers.put(id, buffer);
		return id;
	}

	private void deallocateBufferId(int id) {
		buffers.remove(id);
	}

	private int allocateFrameBufferId(WebGLFramebuffer frameBuffer) {
		int id = nextBufferId++;
		frameBuffers.put(id, frameBuffer);
		return id;
	}

	private void deallocateFrameBufferId(int id) {
		frameBuffers.remove(id);
	}

	private int allocateRenderBufferId(WebGLRenderbuffer renderBuffer) {
		int id = nextRenderBufferId++;
		renderBuffers.put(id, renderBuffer);
		return id;
	}

	private void deallocateRenderBufferId(int id) {
		renderBuffers.remove(id);
	}

	//
	//	private int allocateTextureId(WebGLTexture texture) {
	//		int id = nextTextureId++;
	//		textures.put(id, texture);
	//		return id;
	//	}
	//
	//	private void deallocateTextureId(int id) {
	//		textures.remove(id);
	//	}

	@Override
	public void glActiveTexture(int texture) {
		gl.activeTexture(texture);
	}

	@Override
	public void glBindTexture(int target, int texture) {
		gl.bindTexture(target, textures.get(texture));
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		gl.blendFunc(sfactor, dfactor);
	}

	@Override
	public void glClear(int mask) {
		gl.clear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		gl.clearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepthf(float depth) {
		gl.clearDepth(depth);
	}

	@Override
	public void glClearStencil(int s) {
		gl.clearStencil(s);
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		gl.colorMask(red, green, blue, alpha);
	}

	@Override
	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
		throw new RuntimeException("compressed textures not supported by GWT WebGL backend");
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
		throw new RuntimeException("compressed textures not supported by GWT WebGL backend");
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
		gl.copyTexImage2D(target, level, internalformat, x, y, width, height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		gl.copyTexSubImage2D(target, level, 0, xoffset, yoffset, x, y, width, height);
	}

	@Override
	public void glCullFace(int mode) {
		gl.cullFace(mode);
	}

	@Override
	public void glDeleteTextures(int n, IntBuffer textures) {
		for (int i = 0; i < n; i++) {
			int id = textures.get();
			WebGLTexture texture = this.textures.get(id);
			deallocateId(this.textures, id);// deallocateTextureId(id);
			gl.deleteTexture(texture);
		}
	}

	@Override
	public void glDepthFunc(int func) {
		gl.depthFunc(func);
	}

	@Override
	public void glDepthMask(boolean flag) {
		gl.depthMask(flag);
	}

	@Override
	public void glDepthRangef(float zNear, float zFar) {
		gl.depthRange(zNear, zFar);
	}

	@Override
	public void glDisable(int cap) {
		gl.disable(cap);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		gl.drawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		gl.drawElements(mode, count, type, indices.position()); // FIXME this is assuming WebGL supports client side buffers...
	}

	@Override
	public void glDrawElements(int mode, int count, int type, int offset) {
		gl.drawElements(mode, count, type, offset);
	}

	@Override
	public void glEnable(int cap) {
		gl.enable(cap);
	}

	@Override
	public void glFinish() {
		gl.finish();
	}

	@Override
	public void glFlush() {
		gl.flush();
	}

	@Override
	public void glFrontFace(int mode) {
		gl.frontFace(mode);
	}

	@Override
	public void glGenTextures(int n, IntBuffer textures) {
		WebGLTexture texture = gl.createTexture();
		int id = allocateId(this.textures, texture); // allocateTextureId(texture);
		textures.put(id);
	}

	@Override
	public int glGetError() {
		return gl.getError();
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		if (pname == GL20.GL_ACTIVE_TEXTURE || pname == GL20.GL_ALPHA_BITS || pname == GL20.GL_BLEND_DST_ALPHA || pname == GL20.GL_BLEND_DST_RGB || pname == GL20.GL_BLEND_EQUATION_ALPHA || pname == GL20.GL_BLEND_EQUATION_RGB || pname == GL20.GL_BLEND_SRC_ALPHA || pname == GL20.GL_BLEND_SRC_RGB || pname == GL20.GL_BLUE_BITS || pname == GL20.GL_CULL_FACE_MODE || pname == GL20.GL_DEPTH_BITS || pname == GL20.GL_DEPTH_FUNC || pname == GL20.GL_FRONT_FACE || pname == GL20.GL_GENERATE_MIPMAP_HINT || pname == GL20.GL_GREEN_BITS || pname == GL20.GL_IMPLEMENTATION_COLOR_READ_FORMAT || pname == GL20.GL_IMPLEMENTATION_COLOR_READ_TYPE || pname == GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS || pname == GL20.GL_MAX_CUBE_MAP_TEXTURE_SIZE || pname == GL20.GL_MAX_FRAGMENT_UNIFORM_VECTORS || pname == GL20.GL_MAX_RENDERBUFFER_SIZE || pname == GL20.GL_MAX_TEXTURE_IMAGE_UNITS || pname == GL20.GL_MAX_TEXTURE_SIZE || pname == GL20.GL_MAX_VARYING_VECTORS || pname == GL20.GL_MAX_VERTEX_ATTRIBS || pname == GL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS || pname == GL20.GL_MAX_VERTEX_UNIFORM_VECTORS || pname == GL20.GL_NUM_COMPRESSED_TEXTURE_FORMATS || pname == GL20.GL_PACK_ALIGNMENT || pname == GL20.GL_RED_BITS || pname == GL20.GL_SAMPLE_BUFFERS || pname == GL20.GL_SAMPLES || pname == GL20.GL_STENCIL_BACK_FAIL || pname == GL20.GL_STENCIL_BACK_FUNC || pname == GL20.GL_STENCIL_BACK_PASS_DEPTH_FAIL || pname == GL20.GL_STENCIL_BACK_PASS_DEPTH_PASS || pname == GL20.GL_STENCIL_BACK_REF || pname == GL20.GL_STENCIL_BACK_VALUE_MASK || pname == GL20.GL_STENCIL_BACK_WRITEMASK || pname == GL20.GL_STENCIL_BITS || pname == GL20.GL_STENCIL_CLEAR_VALUE || pname == GL20.GL_STENCIL_FAIL || pname == GL20.GL_STENCIL_FUNC || pname == GL20.GL_STENCIL_PASS_DEPTH_FAIL || pname == GL20.GL_STENCIL_PASS_DEPTH_PASS || pname == GL20.GL_STENCIL_REF || pname == GL20.GL_STENCIL_VALUE_MASK || pname == GL20.GL_STENCIL_WRITEMASK || pname == GL20.GL_SUBPIXEL_BITS || pname == GL20.GL_UNPACK_ALIGNMENT) params.put(0, gl.getParameteri(pname));
		else throw new RuntimeException("glGetFloat not supported by GWT WebGL backend");
	}

	@Override
	public String glGetString(int name) {
		throw new RuntimeException();
	}

	@Override
	public void glHint(int target, int mode) {
		throw new RuntimeException();
	}

	@Override
	public void glLineWidth(float width) {
		gl.lineWidth(width);
	}

	@Override
	public void glPixelStorei(int pname, int param) {
		gl.pixelStorei(pname, param);
	}

	@Override
	public void glPolygonOffset(float factor, float units) {
		gl.polygonOffset(factor, units);
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
		// verify request
		if ((format != WebGLRenderingContext.RGBA) || (type != WebGLRenderingContext.UNSIGNED_BYTE)) { throw new RuntimeException("Only format RGBA and type UNSIGNED_BYTE are currently supported for glReadPixels(...)."); }
		if (!(pixels instanceof ByteBuffer)) { throw new RuntimeException("Inputed pixels buffer needs to be of type ByteBuffer for glReadPixels(...)."); }

		// create new ArrayBufferView (4 bytes per pixel)
		int size = 4 * width * height;
		Uint8Array buffer = Uint8Array.create(size);

		// read bytes to ArrayBufferView
		gl.readPixels(x, y, width, height, format, type, buffer);

		// copy ArrayBufferView to our pixels array
		ByteBuffer pixelsByte = (ByteBuffer) pixels;
		for (int i = 0; i < size; i++) {
			pixelsByte.put((byte) (buffer.get(i) & 0x000000ff));
		}
	}

	@Override
	public void glScissor(int x, int y, int width, int height) {
		gl.scissor(x, y, width, height);
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask) {
		gl.stencilFunc(func, ref, mask);
	}

	@Override
	public void glStencilMask(int mask) {
		gl.stencilMask(mask);
	}

	@Override
	public void glStencilOp(int fail, int zfail, int zpass) {
		gl.stencilOp(fail, zfail, zpass);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		//		Pixmap pixmap = Pixmap.pixmaps.get(((IntBuffer) pixels).get(0));
		//		gl.texImage2D(target, level, internalformat, format, type, pixmap.getCanvasElement());
		throw new RuntimeException();
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		gl.texParameterf(target, pname, param);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
		//		Pixmap pixmap = Pixmap.pixmaps.get(((IntBuffer) pixels).get(0));
		//		gl.texSubImage2D(target, level, xoffset, yoffset, width, height, pixmap.getCanvasElement());
		throw new RuntimeException();
	}

	@Override
	public void glAttachShader(int program, int shader) {
		gl.attachShader(programs.get(program), shaders.get(shader));
	}

	@Override
	public void glBindAttribLocation(int program, int index, CharSequence name) {
		gl.bindAttribLocation(programs.get(program), index, name.toString());
	}

	@Override
	public void glBindBuffer(int target, int buffer) {
		gl.bindBuffer(target, buffers.get(buffer));
	}

	@Override
	public void glBindFramebuffer(int target, int framebuffer) {
		gl.bindFramebuffer(target, frameBuffers.get(framebuffer));
	}

	@Override
	public void glBindRenderbuffer(int target, int renderbuffer) {
		gl.bindRenderbuffer(target, renderBuffers.get(renderbuffer));
	}

	@Override
	public void glBlendColor(float red, float green, float blue, float alpha) {
		gl.blendColor(red, green, blue, alpha);
	}

	@Override
	public void glBlendEquation(int mode) {
		gl.blendEquation(mode);
	}

	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		gl.blendEquationSeparate(modeRGB, modeAlpha);
	}

	@Override
	public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
		gl.blendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
	}

	@Override
	public void glBufferData(int target, int size, Buffer data, int usage) {
		if (data instanceof FloatBuffer) {
			gl.bufferData(target, copy((FloatBuffer) data), usage);
		} else if (data instanceof ShortBuffer) {
			gl.bufferData(target, copy((ShortBuffer) data), usage);
		} else {
			throw new RuntimeException("Can only copy FloatBuffer and ShortBuffer at the moment");
		}
	}

	@Override
	public void glBufferSubData(int target, int offset, int size, Buffer data) {
		if (data instanceof FloatBuffer) {
			gl.bufferSubData(target, offset, copy((FloatBuffer) data));
		} else if (data instanceof ShortBuffer) {
			gl.bufferSubData(target, offset, copy((ShortBuffer) data));
		} else {
			throw new RuntimeException("Can only copy FloatBuffer and ShortBuffer at the moment");
		}
	}

	@Override
	public int glCheckFramebufferStatus(int target) {
		return gl.checkFramebufferStatus(target);
	}

	@Override
	public void glCompileShader(int shader) {
		WebGLShader glShader = shaders.get(shader);
		gl.compileShader(glShader);
	}

	@Override
	public int glCreateProgram() {
		WebGLProgram program = gl.createProgram();
		return allocateProgramId(program);
	}

	@Override
	public int glCreateShader(int type) {
		WebGLShader shader = gl.createShader(type);
		return allocateShaderId(shader);
	}

	@Override
	public void glDeleteBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) {
			int id = buffers.get();
			WebGLBuffer buffer = this.buffers.get(id);
			deallocateBufferId(id);
			gl.deleteBuffer(buffer);
		}
	}

	@Override
	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) {
			int id = framebuffers.get();
			WebGLFramebuffer fb = this.frameBuffers.get(id);
			deallocateFrameBufferId(id);
			gl.deleteFramebuffer(fb);
		}
	}

	@Override
	public void glDeleteProgram(int program) {
		WebGLProgram prog = programs.get(program);
		deallocateProgramId(program);
		gl.deleteProgram(prog);
	}

	@Override
	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) {
			int id = renderbuffers.get();
			WebGLRenderbuffer rb = this.renderBuffers.get(id);
			deallocateRenderBufferId(id);
			gl.deleteRenderbuffer(rb);
		}
	}

	@Override
	public void glDeleteShader(int shader) {
		WebGLShader sh = shaders.get(shader);
		deallocateShaderId(shader);
		gl.deleteShader(sh);
	}

	@Override
	public void glDetachShader(int program, int shader) {
		gl.detachShader(programs.get(program), shaders.get(shader));
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		gl.disableVertexAttribArray(index);
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		gl.enableVertexAttribArray(index);
	}

	@Override
	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		gl.framebufferRenderbuffer(target, attachment, renderbuffertarget, renderBuffers.get(renderbuffer));
	}

	@Override
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		gl.framebufferTexture2D(target, attachment, textarget, textures.get(texture), level);
	}

	@Override
	public void glGenBuffers(int n, IntBuffer buffers) {
		for (int i = 0; i < n; i++) {
			WebGLBuffer buffer = gl.createBuffer();
			int id = allocateBufferId(buffer);
			buffers.put(i, id);
		}
	}

	@Override
	public void glGenerateMipmap(int target) {
		gl.generateMipmap(target);
	}

	@Override
	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		for (int i = 0; i < n; i++) {
			WebGLFramebuffer fb = gl.createFramebuffer();
			int id = allocateFrameBufferId(fb);
			framebuffers.put(id);
		}
	}

	@Override
	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		for (int i = 0; i < n; i++) {
			WebGLRenderbuffer rb = gl.createRenderbuffer();
			int id = allocateRenderBufferId(rb);
			renderbuffers.put(id);
		}
	}

	@Override
	public void glGetActiveAttrib(int program, int index, int bufSize, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
		System.out.println(index);
		WebGLActiveInfo activeAttrib = gl.getActiveAttrib(programs.get(program), index);
		size.put(activeAttrib.getSize());
		((IntBuffer) type).put(activeAttrib.getType());
		// name.putInt(activeAttrib.getName());
	}

	@Override
	public void glGetActiveUniform(int program, int index, int bufSize, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
		WebGLActiveInfo activeUniform = gl.getActiveUniform(programs.get(program), index);
		size.put(activeUniform.getSize());
		((IntBuffer) type).put(activeUniform.getType());
		// name.putInt(activeAttrib.getName());
	}

	@Override
	public void glGetAttachedShaders(int program, int maxCount, IntBuffer count, IntBuffer shaders) {
		WebGLShader[] webGLShaders = gl.getAttachedShaders(programs.get(program));
		count.put(webGLShaders.length);
		Set<Entry<Integer, WebGLShader>> entrySet = this.shaders.entrySet();
		for (int i = 0; i < Math.min(webGLShaders.length, maxCount); i++)
			for (Iterator<Entry<Integer, WebGLShader>> iterator = entrySet.iterator(); iterator.hasNext();) {
				Entry<Integer, WebGLShader> entry = iterator.next();
				if (entry.getValue() == webGLShaders[i]) {
					shaders.put(entry.getKey());
					break;
				}
			}
	}

	@Override
	public int glGetAttribLocation(int program, CharSequence name) {
		WebGLProgram prog = programs.get(program);
		return gl.getAttribLocation(prog, name.toString());
	}

	@Override
	public void glGetBooleanv(int pname, ByteBuffer params) {
		throw new UnsupportedOperationException("glGetBoolean not supported by GWT WebGL backend");
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(gl.getBufferParameteri(target, pname));
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
		if (pname == GL20.GL_DEPTH_CLEAR_VALUE || pname == GL20.GL_LINE_WIDTH || pname == GL20.GL_POLYGON_OFFSET_FACTOR || pname == GL20.GL_POLYGON_OFFSET_UNITS || pname == GL20.GL_SAMPLE_COVERAGE_VALUE) params.put(0, gl.getParameterf(pname));
		else throw new RuntimeException("glGetFloat not supported by GWT WebGL backend");
	}

	@Override
	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		params.put(gl.getFramebufferAttachmentParameteri(target, attachment, pname));
	}

	@Override
	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		if (pname == GL20.GL_DELETE_STATUS || pname == GL20.GL_LINK_STATUS || pname == GL20.GL_VALIDATE_STATUS) {
			boolean result = gl.getProgramParameterb(programs.get(program), pname);
			params.put(result ? GL20.GL_TRUE : GL20.GL_FALSE);
		} else {
			params.put(gl.getProgramParameteri(programs.get(program), pname));
		}
	}

	@Override
	public void glGetProgramInfoLog(int program, int bufSize, IntBuffer length, ByteBuffer infoLog) {
		String log = gl.getProgramInfoLog(programs.get(program));
		if (length != null) length.put(log.length());
		infoLog.asCharBuffer().append(log);
	}

	@Override
	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		params.put(gl.getRenderbufferParameteri(target, pname));
	}

	@Override
	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		if (pname == GL20.GL_COMPILE_STATUS || pname == GL20.GL_DELETE_STATUS) {
			boolean result = gl.getShaderParameterb(shaders.get(shader), pname);
			params.put(result ? GL20.GL_TRUE : GL20.GL_FALSE);
		} else {
			int result = gl.getShaderParameteri(shaders.get(shader), pname);
			params.put(result);
		}
	}

	@Override
	public void glGetShaderInfoLog(int shader, int bufSize, IntBuffer length, ByteBuffer infoLog) {
		String log = gl.getShaderInfoLog(shaders.get(shader));
		length.put(log.length());
		infoLog.asCharBuffer().append(log);
	}

	@Override
	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		throw new RuntimeException("glGetShaderPrecisionFormat not supported by GWT WebGL backend");
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		throw new RuntimeException("glGetTexParameter not supported by GWT WebGL backend");
	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		throw new RuntimeException("glGetTexParameter not supported by GWT WebGL backend");
	}

	@Override
	public void glGetUniformfv(int program, int location, FloatBuffer params) {
		params.put(gl.getUniformf(programs.get(program), getUniformLocation(location)));
	}

	@Override
	public void glGetUniformiv(int program, int location, IntBuffer params) {
		params.put(gl.getUniformi(programs.get(program), getUniformLocation(location)));
	}

	@Override
	public int glGetUniformLocation(int program, CharSequence name) {
		WebGLUniformLocation location = gl.getUniformLocation(programs.get(program), name.toString());
		return allocateUniformLocationId(program, location);
	}

	@Override
	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		throw new UnsupportedOperationException("not implemented"); // FIXME
	}

	@Override
	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		params.put(gl.getVertexAttribi(index, pname));
	}

	@Override
	public ByteBuffer glGetVertexAttribPointerv(int index, int pname, long result_size) {
		throw new UnsupportedOperationException("glGetVertexAttribPointer not supported by GwtGL");
	}

	@Override
	public boolean glIsBuffer(int buffer) {
		return gl.isBuffer(buffers.get(buffer));
	}

	@Override
	public boolean glIsEnabled(int cap) {
		// return gl.isEnabled(cap);
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean glIsFramebuffer(int framebuffer) {
		return gl.isFramebuffer(frameBuffers.get(framebuffer));
	}

	@Override
	public boolean glIsProgram(int program) {
		return gl.isProgram(programs.get(program));
	}

	@Override
	public boolean glIsRenderbuffer(int renderbuffer) {
		return gl.isRenderbuffer(renderBuffers.get(renderbuffer));
	}

	@Override
	public boolean glIsShader(int shader) {
		return gl.isShader(shaders.get(shader));
	}

	@Override
	public boolean glIsTexture(int texture) {
		return gl.isTexture(textures.get(texture));
	}

	@Override
	public void glLinkProgram(int program) {
		gl.linkProgram(programs.get(program));
	}

	@Override
	public void glReleaseShaderCompiler() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
		gl.renderbufferStorage(target, internalformat, width, height);
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		gl.sampleCoverage(value, invert);
	}

	@Override
	public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		throw new RuntimeException("glShaderBinary not supported by GWT WebGL backend");
	}

	@Override
	public void glShaderSource(int shader, int count, String string, IntBuffer length) {
		gl.shaderSource(shaders.get(shader), string);
	}

	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		gl.stencilFuncSeparate(face, func, ref, mask);
	}

	@Override
	public void glStencilMaskSeparate(int face, int mask) {
		gl.stencilMaskSeparate(face, mask);
	}

	@Override
	public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
		gl.stencilOpSeparate(face, fail, zfail, zpass);
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		gl.texParameterf(target, pname, params.get());
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		gl.texParameterf(target, pname, param);
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		gl.texParameterf(target, pname, params.get());
	}

	@Override
	public void glUniform1f(int location, float x) {
		gl.uniform1f(getUniformLocation(location), x);
	}

	@Override
	public void glUniform1fv(int location, int count, FloatBuffer v) {
		gl.uniform1fvProd(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniform1i(int location, int x) {
		gl.uniform1i(getUniformLocation(location), x);
	}

	@Override
	public void glUniform1iv(int location, int count, IntBuffer v) {
		gl.uniform1iv(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniform2f(int location, float x, float y) {
		gl.uniform2f(getUniformLocation(location), x, y);
	}

	@Override
	public void glUniform2fv(int location, int count, FloatBuffer v) {
		gl.uniform2fv(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniform2i(int location, int x, int y) {
		gl.uniform2i(getUniformLocation(location), x, y);
	}

	@Override
	public void glUniform2iv(int location, int count, IntBuffer v) {
		gl.uniform2iv(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniform3f(int location, float x, float y, float z) {
		gl.uniform3f(getUniformLocation(location), x, y, z);
	}

	@Override
	public void glUniform3fv(int location, int count, FloatBuffer v) {
		gl.uniform3fv(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniform3i(int location, int x, int y, int z) {
		gl.uniform3i(getUniformLocation(location), x, y, z);
	}

	@Override
	public void glUniform3iv(int location, int count, IntBuffer v) {
		gl.uniform3ivProd(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniform4f(int location, float x, float y, float z, float w) {
		gl.uniform4f(getUniformLocation(location), x, y, z, w);
	}

	@Override
	public void glUniform4fv(int location, int count, FloatBuffer v) {
		gl.uniform4fv(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniform4i(int location, int x, int y, int z, int w) {
		gl.uniform4i(getUniformLocation(location), x, y, z, w);
	}

	@Override
	public void glUniform4iv(int location, int count, IntBuffer v) {
		gl.uniform4iv(getUniformLocation(location), copy(v));
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
		gl.uniformMatrix2fv(getUniformLocation(location), transpose, copy(value));
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
		gl.uniformMatrix3fv(getUniformLocation(location), transpose, copy(value));
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
		gl.uniformMatrix4fv(getUniformLocation(location), transpose, copy(value));
	}

	@Override
	public void glUseProgram(int program) {
		currProgram = program;
		gl.useProgram(programs.get(program));
	}

	@Override
	public void glValidateProgram(int program) {
		gl.validateProgram(programs.get(program));
	}

	@Override
	public void glVertexAttrib1f(int indx, float x) {
		gl.vertexAttrib1f(indx, x);
	}

	@Override
	public void glVertexAttrib1fv(int indx, FloatBuffer values) {
		gl.vertexAttrib1fv(indx, copy(values));
	}

	@Override
	public void glVertexAttrib2f(int indx, float x, float y) {
		gl.vertexAttrib2f(indx, x, y);
	}

	@Override
	public void glVertexAttrib2fv(int indx, FloatBuffer values) {
		gl.vertexAttrib2fv(indx, copy(values));
	}

	@Override
	public void glVertexAttrib3f(int indx, float x, float y, float z) {
		gl.vertexAttrib3f(indx, x, y, z);
	}

	@Override
	public void glVertexAttrib3fv(int indx, FloatBuffer values) {
		gl.vertexAttrib3fv(indx, copy(values));
	}

	@Override
	public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
		gl.vertexAttrib4f(indx, x, y, z, w);
	}

	@Override
	public void glVertexAttrib4fv(int indx, FloatBuffer values) {
		gl.vertexAttrib4fv(indx, copy(values));
	}

	@Override
	public void glVertexPointer(int size, int type, int stride, Buffer pointer) {
		throw new RuntimeException("not implemented, vertex arrays aren't support in WebGL it seems");
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		gl.viewport(x, y, width, height);
	}

	@Override
	public void glGetShaderSource(int shader, int bufSize, IntBuffer length, ByteBuffer source) {
		String string = gl.getShaderSource(shaders.get(shader));
		length.put(string.length());
		source.asCharBuffer().append(string);
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, Buffer pointer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void glAlphaFunc(int func, float ref) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glClipPlanef(int plane, FloatBuffer equation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glColor4f(float red, float green, float blue, float alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glFogf(int pname, float param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glFogfv(int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glFrustumf(float left, float right, float bottom, float top, float zNear, float zFar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetClipPlanef(int pname, FloatBuffer eqn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetLightfv(int light, int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetMaterialfv(int face, int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetTexEnvfv(int env, int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightModelf(int pname, float param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightModelfv(int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightf(int light, int pname, float param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightfv(int light, int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLoadMatrixf(FloatBuffer m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMaterialf(int face, int pname, float param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMaterialfv(int face, int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMultMatrixf(FloatBuffer m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMultiTexCoord4f(int target, float s, float t, float r, float q) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glNormal3f(float nx, float ny, float nz) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPointParameterf(int pname, float param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPointParameterfv(int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPointSize(float size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glRotatef(float angle, float x, float y, float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glScalef(float x, float y, float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexEnvf(int target, int pname, float param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexEnvfv(int target, int pname, FloatBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTranslatef(float x, float y, float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glAlphaFuncx(int func, int ref) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glClearColorx(int red, int green, int blue, int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glClearDepthx(int depth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glClientActiveTexture(int texture) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glClipPlanex(int plane, IntBuffer equation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glColor4ub(byte red, byte green, byte blue, byte alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glColor4x(int red, int green, int blue, int alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glColorPointer(int size, int type, int stride, Buffer pointer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glDepthRangex(int zNear, int zFar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glDisableClientState(int array) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glEnableClientState(int array) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glFogx(int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glFogxv(int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glFrustumx(int left, int right, int bottom, int top, int zNear, int zFar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetClipPlanex(int pname, IntBuffer eqn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetFixedv(int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetLightxv(int light, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetMaterialxv(int face, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetPointerv(int pname, Buffer[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetTexEnviv(int env, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetTexEnvxv(int env, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glGetTexParameterxv(int target, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightModelx(int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightModelxv(int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightx(int light, int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLightxv(int light, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLineWidthx(int width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLoadIdentity() {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLoadMatrixx(IntBuffer m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLogicOp(int opcode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMaterialx(int face, int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMaterialxv(int face, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMatrixMode(int mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMultMatrixx(IntBuffer m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glMultiTexCoord4x(int target, int s, int t, int r, int q) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glNormal3x(int nx, int ny, int nz) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glNormalPointer(int type, int stride, Buffer pointer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glOrthox(int left, int right, int bottom, int top, int zNear, int zFar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPointParameterx(int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPointParameterxv(int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPointSizex(int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPolygonOffsetx(int factor, int units) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPopMatrix() {
		// TODO Auto-generated method stub

	}

	@Override
	public void glPushMatrix() {
		// TODO Auto-generated method stub

	}

	@Override
	public void glRotatex(int angle, int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glSampleCoveragex(int value, boolean invert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glScalex(int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glShadeModel(int mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexCoordPointer(int size, int type, int stride, Buffer pointer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexEnvi(int target, int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexEnvx(int target, int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexEnviv(int target, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexEnvxv(int target, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexParameterx(int target, int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexParameterxv(int target, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTranslatex(int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int pointer) {
		gl.vertexAttribPointer(index, size, type, normalized, stride, pointer);
	}

}