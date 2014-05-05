/**
 * 
 */
package ak5.backend.lwjgl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import ak5.graphics.GL10;

/** @author pwnedary */
public class LWJGLGL20 extends LWJGLGL11 implements ak5.graphics.GL20 {
	@Override
	public void glAttachShader(int program, int shader) {
		GL20.glAttachShader(program, shader);
	}

	@Override
	public void glBindAttribLocation(int program, int index, CharSequence name) {
		GL20.glBindAttribLocation(program, index, name);
	}

	@Override
	public void glBindFramebuffer(int target, int framebuffer) {
		EXTFramebufferObject.glBindFramebufferEXT(target, framebuffer);
	}

	@Override
	public void glBindRenderbuffer(int target, int renderbuffer) {
		EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
	}

	@Override
	public void glBlendColor(float red, float green, float blue, float alpha) {
		GL14.glBlendColor(red, green, blue, alpha);
	}

	@Override
	public void glBlendEquation(int mode) {
		GL14.glBlendEquation(mode);
	}

	@Override
	public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
		GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
	}

	@Override
	public void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha) {
		GL14.glBlendFuncSeparate(sfactorRGB, dfactorRGB, sfactorAlpha, dfactorAlpha);
	}

	@Override
	public int glCheckFramebufferStatus(int target) {
		return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
	}

	@Override
	public void glCompileShader(int shader) {
		GL20.glCompileShader(shader);
	}

	@Override
	public int glCreateProgram() {
		return GL20.glCreateProgram();
	}

	@Override
	public int glCreateShader(int type) {
		return GL20.glCreateShader(type);
	}

	@Override
	public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
		EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffers);
	}

	@Override
	public void glDeleteProgram(int program) {
		GL20.glDeleteProgram(program);
	}

	@Override
	public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
		EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffers);
	}

	@Override
	public void glDeleteShader(int shader) {
		GL20.glDeleteShader(shader);
	}

	@Override
	public void glDetachShader(int program, int shader) {
		GL20.glDetachShader(program, shader);
	}

	@Override
	public void glDisableVertexAttribArray(int index) {
		GL20.glDisableVertexAttribArray(index);
	}

	@Override
	public void glEnableVertexAttribArray(int index) {
		GL20.glEnableVertexAttribArray(index);
	}

	@Override
	public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
		EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderbuffertarget, renderbuffer);
	}

	@Override
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
	}

	@Override
	public void glGenerateMipmap(int target) {
		EXTFramebufferObject.glGenerateMipmapEXT(target);
	}

	@Override
	public void glGenFramebuffers(int n, IntBuffer framebuffers) {
		EXTFramebufferObject.glGenFramebuffersEXT(framebuffers);
	}

	@Override
	public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
		EXTFramebufferObject.glGenRenderbuffersEXT(renderbuffers);
	}

	@Override
	public void glGetActiveAttrib(int program, int index, int bufSize, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
		GL20.glGetActiveAttrib(program, index, length, size, type, name);
	}

	@Override
	public void glGetActiveUniform(int program, int index, int bufSize, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name) {
		GL20.glGetActiveUniform(program, index, length, size, type, name);
	}

	@Override
	public void glGetAttachedShaders(int program, int maxCount, IntBuffer count, IntBuffer shaders) {
		GL20.glGetAttachedShaders(program, count, shaders);
	}

	@Override
	public int glGetAttribLocation(int program, CharSequence name) {
		return GL20.glGetAttribLocation(program, name);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		GL15.glGetBufferParameter(target, pname, params);
	}

	@Override
	public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
		EXTFramebufferObject.glGetFramebufferAttachmentParameterEXT(target, attachment, pname, params);
	}

	@Override
	public void glGetProgramiv(int program, int pname, IntBuffer params) {
		GL20.glGetProgram(program, pname, params);
	}

	@Override
	public void glGetProgramInfoLog(int program, int bufSize, IntBuffer length, ByteBuffer infoLog) {
		GL20.glGetProgramInfoLog(program, length, infoLog);
	}

	@Override
	public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
		EXTFramebufferObject.glGetRenderbufferParameterEXT(target, pname, params);
	}

	@Override
	public void glGetShaderiv(int shader, int pname, IntBuffer params) {
		GL20.glGetShader(shader, pname, params);
	}

	@Override
	public void glGetShaderInfoLog(int shader, int bufSize, IntBuffer length, ByteBuffer infoLog) {
		GL20.glGetShaderInfoLog(shader, length, infoLog);
	}

	@Override
	public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetShaderSource(int shader, int bufSize, IntBuffer length, ByteBuffer source) {
		GL20.glGetShaderSource(shader, length, source);
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		org.lwjgl.opengl.GL11.glGetTexParameter(target, pname, params);
	}

	@Override
	public void glGetUniformfv(int program, int location, FloatBuffer params) {
		GL20.glGetUniform(program, location, params);
	}

	@Override
	public void glGetUniformiv(int program, int location, IntBuffer params) {
		GL20.glGetUniform(program, location, params);
	}

	@Override
	public int glGetUniformLocation(int program, CharSequence name) {
		return GL20.glGetUniformLocation(program, name);
	}

	@Override
	public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
		GL20.glGetVertexAttrib(index, pname, params);
	}

	@Override
	public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
		GL20.glGetVertexAttrib(index, pname, params);
	}

	@Override
	public ByteBuffer glGetVertexAttribPointerv(int index, int pname, long result_size) {
		return GL20.glGetVertexAttribPointer(index, pname, result_size);
	}

	@Override
	public boolean glIsFramebuffer(int framebuffer) {
		return EXTFramebufferObject.glIsFramebufferEXT(framebuffer);
	}

	@Override
	public boolean glIsProgram(int program) {
		return GL20.glIsProgram(program);
	}

	@Override
	public boolean glIsRenderbuffer(int renderbuffer) {
		return EXTFramebufferObject.glIsRenderbufferEXT(renderbuffer);
	}

	@Override
	public boolean glIsShader(int shader) {
		return GL20.glIsShader(shader);
	}

	@Override
	public void glLinkProgram(int program) {
		GL20.glLinkProgram(program);
	}

	@Override
	public void glReleaseShaderCompiler() {/* nothing to do */}

	@Override
	public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
		EXTFramebufferObject.glRenderbufferStorageEXT(target, internalformat, width, height);
	}

	@Override
	public void glShaderBinary(int count, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void glShaderSource(int shader, int count, String string, IntBuffer length) {
		GL20.glShaderSource(shader, string);
	}

	@Override
	public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
		GL20.glStencilFuncSeparate(face, func, ref, mask);
	}

	@Override
	public void glStencilMaskSeparate(int face, int mask) {
		GL20.glStencilMaskSeparate(face, mask);
	}

	@Override
	public void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass) {
		GL20.glStencilOpSeparate(face, sfail, dpfail, dppass);
	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		GL11.glTexParameteri(target, pname, param);
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		GL11.glTexParameter(target, pname, params);
	}

	@Override
	public void glUniform1f(int location, float v0) {
		GL20.glUniform1f(location, v0);
	}

	@Override
	public void glUniform1fv(int location, int count, FloatBuffer value) {
		GL20.glUniform1(location, value);
	}

	@Override
	public void glUniform1i(int location, int v0) {
		GL20.glUniform1i(location, v0);
	}

	@Override
	public void glUniform1iv(int location, int count, IntBuffer value) {
		GL20.glUniform1(location, value);
	}

	@Override
	public void glUniform2f(int location, float v0, float v1) {
		GL20.glUniform2f(location, v0, v1);
	}

	@Override
	public void glUniform2fv(int location, int count, FloatBuffer value) {
		GL20.glUniform2(location, value);
	}

	@Override
	public void glUniform2i(int location, int v0, int v1) {
		GL20.glUniform2i(location, v0, v1);
	}

	@Override
	public void glUniform2iv(int location, int count, IntBuffer value) {
		GL20.glUniform2(location, value);
	}

	@Override
	public void glUniform3f(int location, float v0, float v1, float v2) {
		GL20.glUniform3f(location, v0, v1, v2);
	}

	@Override
	public void glUniform3fv(int location, int count, FloatBuffer value) {
		GL20.glUniform3(location, value);
	}

	@Override
	public void glUniform3i(int location, int v0, int v1, int v2) {
		GL20.glUniform3i(location, v0, v1, v2);
	}

	@Override
	public void glUniform3iv(int location, int count, IntBuffer value) {
		GL20.glUniform3(location, value);
	}

	@Override
	public void glUniform4f(int location, float v0, float v1, float v2, float v3) {
		GL20.glUniform4f(location, v0, v1, v2, v3);
	}

	@Override
	public void glUniform4fv(int location, int count, FloatBuffer value) {
		GL20.glUniform4(location, value);
	}

	@Override
	public void glUniform4i(int location, int v0, int v1, int v2, int v3) {
		GL20.glUniform4i(location, v0, v1, v2, v3);
	}

	@Override
	public void glUniform4iv(int location, int count, IntBuffer value) {
		GL20.glUniform4(location, value);
	}

	@Override
	public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
		GL20.glUniformMatrix2(location, transpose, value);
	}

	@Override
	public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
		GL20.glUniformMatrix3(location, transpose, value);
	}

	@Override
	public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
		GL20.glUniformMatrix4(location, transpose, value);
	}

	@Override
	public void glUseProgram(int program) {
		GL20.glUseProgram(program);
	}

	@Override
	public void glValidateProgram(int program) {
		GL20.glValidateProgram(program);
	}

	@Override
	public void glVertexAttrib1f(int index, float x) {
		GL20.glVertexAttrib1f(index, x);
	}

	@Override
	public void glVertexAttrib1fv(int index, FloatBuffer v) {
		GL20.glVertexAttrib1f(index, v.get());
	}

	@Override
	public void glVertexAttrib2f(int index, float x, float y) {
		GL20.glVertexAttrib2f(index, x, y);
	}

	@Override
	public void glVertexAttrib2fv(int index, FloatBuffer v) {
		GL20.glVertexAttrib2f(index, v.get(), v.get());
	}

	@Override
	public void glVertexAttrib3f(int index, float x, float y, float z) {
		GL20.glVertexAttrib3f(index, x, y, z);
	}

	@Override
	public void glVertexAttrib3fv(int index, FloatBuffer v) {
		GL20.glVertexAttrib3f(index, v.get(), v.get(), v.get());
	}

	@Override
	public void glVertexAttrib4f(int index, float x, float y, float z, float w) {
		GL20.glVertexAttrib4f(index, x, y, z, w);
	}

	@Override
	public void glVertexAttrib4fv(int index, FloatBuffer v) {
		GL20.glVertexAttrib4f(index, v.get(), v.get(), v.get(), v.get());
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, Buffer pointer) {
		if (pointer instanceof ByteBuffer) {
			if (type == GL10.GL_BYTE) GL20.glVertexAttribPointer(index, size, false, normalized, stride, (ByteBuffer) pointer);
			else if (type == GL10.GL_UNSIGNED_BYTE) GL20.glVertexAttribPointer(index, size, true, normalized, stride, (ByteBuffer) pointer);
			else if (type == GL10.GL_SHORT) GL20.glVertexAttribPointer(index, size, false, normalized, stride, ((ByteBuffer) pointer).asShortBuffer());
			else if (type == GL10.GL_UNSIGNED_SHORT) GL20.glVertexAttribPointer(index, size, true, normalized, stride, ((ByteBuffer) pointer).asShortBuffer());
			else if (type == GL10.GL_FLOAT) GL20.glVertexAttribPointer(index, size, normalized, stride, ((ByteBuffer) pointer).asFloatBuffer());
		} else if (pointer instanceof FloatBuffer) GL20.glVertexAttribPointer(index, size, normalized, stride, (FloatBuffer) pointer);
		else if (pointer instanceof DoubleBuffer) GL20.glVertexAttribPointer(index, size, normalized, stride, (DoubleBuffer) pointer);
		else throw new RuntimeException("Bad type " + pointer.getClass().getName());
	}

	@Override
	public void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int pointer) {
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
	}
}
