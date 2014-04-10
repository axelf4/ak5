/**
 * 
 */
package org.gamelib.backend.lwjgl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.gamelib.graphics.GL10;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;

/** @author pwnedary */
public class LWJGLGL11 implements org.gamelib.graphics.GL11 {
	@Override
	public void glActiveTexture(int texture) {
		GL13.glActiveTexture(texture);
	}

	@Override
	public void glAlphaFunc(int func, float ref) {
		GL11.glAlphaFunc(func, ref);
	}

	@Override
	public void glBindTexture(int target, int texture) {
		GL11.glBindTexture(target, texture);
	}

	@Override
	public void glBlendFunc(int sfactor, int dfactor) {
		GL11.glBlendFunc(sfactor, dfactor);
	}

	@Override
	public void glClear(int mask) {
		GL11.glClear(mask);
	}

	@Override
	public void glClearColor(float red, float green, float blue, float alpha) {
		GL11.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepthf(float depth) {
		GL11.glClearDepth(depth);
	}

	@Override
	public void glClearStencil(int s) {
		GL11.glClearStencil(s);
	}

	@Override
	public void glClientActiveTexture(int texture) {
		GL13.glClientActiveTexture(texture);
	}

	@Override
	public void glColor4f(float red, float green, float blue, float alpha) {
		GL11.glColor4f(red, green, blue, alpha);
	}

	@Override
	public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
		GL11.glColorMask(red, green, blue, alpha);
	}

	@Override
	public void glColorPointer(int size, int type, int stride, Buffer pointer) {
		if (pointer instanceof FloatBuffer && type == GL10.GL_FLOAT) GL11.glColorPointer(size, stride, (FloatBuffer) pointer);
		else if (pointer instanceof ByteBuffer && type == GL10.GL_FLOAT) GL11.glColorPointer(size, stride, ((ByteBuffer) pointer).asFloatBuffer()); // FIXME
		else if (pointer instanceof ByteBuffer && type == GL10.GL_UNSIGNED_BYTE) GL11.glColorPointer(size, true, stride, (ByteBuffer) pointer);
		else throw new RuntimeException("Bad type " + pointer.getClass().getName());
	}

	@Override
	public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
		if (!(data instanceof ByteBuffer)) throw new RuntimeException("Bad type " + data.getClass().getName());
		GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, (ByteBuffer) data);
	}

	@Override
	public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
		if (!(data instanceof ByteBuffer)) throw new RuntimeException("Bad type " + data.getClass().getName());
		GL13.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, (ByteBuffer) data);
	}

	@Override
	public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) {
		GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
	}

	@Override
	public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
		GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
	}

	@Override
	public void glCullFace(int mode) {
		GL11.glCullFace(mode);
	}

	@Override
	public void glDeleteTextures(int n, IntBuffer textures) {
		GL11.glDeleteTextures(textures);
	}

	@Override
	public void glDepthFunc(int func) {
		GL11.glDepthFunc(func);
	}

	@Override
	public void glDepthMask(boolean flag) {
		GL11.glDepthMask(flag);
	}

	@Override
	public void glDepthRangef(float zNear, float zFar) {
		GL11.glDepthRange(zNear, zFar);
	}

	@Override
	public void glDisable(int cap) {
		GL11.glDisable(cap);
	}

	@Override
	public void glDisableClientState(int cap) {
		GL11.glDisableClientState(cap);
	}

	@Override
	public void glDrawArrays(int mode, int first, int count) {
		GL11.glDrawArrays(mode, first, count);
	}

	@Override
	public void glDrawElements(int mode, int count, int type, Buffer indices) {
		if (indices instanceof ShortBuffer && type == GL10.GL_UNSIGNED_SHORT) GL11.glDrawElements(mode, (ShortBuffer) indices);
		else if (indices instanceof ByteBuffer && type == GL10.GL_UNSIGNED_SHORT) GL11.glDrawElements(mode, ((ByteBuffer) indices).asShortBuffer()); // FIXME
		else if (indices instanceof ByteBuffer && type == GL10.GL_UNSIGNED_BYTE) GL11.glDrawElements(mode, (ByteBuffer) indices);
		else throw new RuntimeException("Bad type " + indices.getClass().getName());
	}

	@Override
	public void glEnable(int cap) {
		GL11.glEnable(cap);
	}

	@Override
	public void glEnableClientState(int cap) {
		GL11.glEnableClientState(cap);
	}

	@Override
	public void glFinish() {
		GL11.glFinish();
	}

	@Override
	public void glFlush() {
		GL11.glFlush();
	}

	@Override
	public void glFogf(int pname, float param) {
		GL11.glFogf(pname, param);
	}

	@Override
	public void glFogfv(int pname, FloatBuffer params) {
		GL11.glFog(pname, params);
	}

	@Override
	public void glFrontFace(int mode) {
		GL11.glFrontFace(mode);
	}

	@Override
	public void glFrustumf(float left, float right, float bottom, float top, float zNear, float zFar) {
		GL11.glFrustum(left, right, bottom, top, zNear, zFar);
	}

	@Override
	public void glGenTextures(int n, IntBuffer textures) {
		GL11.glGenTextures(textures);
	}

	@Override
	public int glGetError() {
		return GL11.glGetError();
	}

	@Override
	public void glGetIntegerv(int pname, IntBuffer params) {
		GL11.glGetInteger(pname, params);
	}

	@Override
	public String glGetString(int name) {
		return GL11.glGetString(name);
	}

	@Override
	public void glHint(int target, int mode) {
		GL11.glHint(target, mode);
	}

	@Override
	public void glLightModelf(int pname, float param) {
		GL11.glLightModelf(pname, param);
	}

	@Override
	public void glLightModelfv(int pname, FloatBuffer params) {
		GL11.glLightModel(pname, params);
	}

	@Override
	public void glLightf(int light, int pname, float param) {
		GL11.glLightf(light, pname, param);
	}

	@Override
	public void glLightfv(int light, int pname, FloatBuffer params) {
		GL11.glLight(light, pname, params);
	}

	@Override
	public void glLineWidth(float width) {
		GL11.glLineWidth(width);
	}

	@Override
	public void glLoadIdentity() {
		GL11.glLoadIdentity();
	}

	@Override
	public void glLoadMatrixf(FloatBuffer m) {
		GL11.glLoadMatrix(m);
	}

	@Override
	public void glLogicOp(int opcode) {
		GL11.glLogicOp(opcode);
	}

	@Override
	public void glMaterialf(int face, int pname, float param) {
		GL11.glMaterialf(face, pname, param);
	}

	@Override
	public void glMaterialfv(int face, int pname, FloatBuffer params) {
		GL11.glMaterial(face, pname, params);
	}

	@Override
	public void glMatrixMode(int mode) {
		GL11.glMatrixMode(mode);
	}

	@Override
	public void glMultMatrixf(FloatBuffer m) {
		GL11.glMultMatrix(m);
	}

	@Override
	public void glMultiTexCoord4f(int target, float s, float t, float r, float q) {
		GL13.glMultiTexCoord4f(target, s, t, r, q);
	}

	@Override
	public void glNormal3f(float nx, float ny, float nz) {
		GL11.glNormal3f(nx, ny, nz);
	}

	@Override
	public void glNormalPointer(int type, int stride, Buffer pointer) {
		if (pointer instanceof FloatBuffer && type == GL11.GL_FLOAT) GL11.glNormalPointer(stride, (FloatBuffer) pointer);
		else if (pointer instanceof ByteBuffer && type == GL11.GL_FLOAT) GL11.glNormalPointer(stride, ((ByteBuffer) pointer).asFloatBuffer());
		else if (pointer instanceof ByteBuffer && type == GL11.GL_BYTE) GL11.glNormalPointer(stride, (ByteBuffer) pointer);
		else throw new RuntimeException("Bad type " + pointer.getClass().getName());
	}

	@Override
	public void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar) {
		GL11.glOrtho(left, right, bottom, top, zNear, zFar);
	}

	@Override
	public void glPixelStorei(int pname, int param) {
		GL11.glPixelStorei(pname, param);
	}

	@Override
	public void glPointSize(float size) {
		GL11.glPointSize(size);
	}

	@Override
	public void glPolygonOffset(float factor, float units) {
		GL11.glPolygonOffset(factor, units);
	}

	@Override
	public void glPopMatrix() {
		GL11.glPopMatrix();
	}

	@Override
	public void glPushMatrix() {
		GL11.glPushMatrix();
	}

	@Override
	public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
		if (pixels instanceof ByteBuffer) GL11.glReadPixels(x, y, width, height, format, type, (ByteBuffer) pixels);
		else if (pixels instanceof ShortBuffer) GL11.glReadPixels(x, y, width, height, format, type, (ShortBuffer) pixels);
		else if (pixels instanceof IntBuffer) GL11.glReadPixels(x, y, width, height, format, type, (IntBuffer) pixels);
		else if (pixels instanceof FloatBuffer) GL11.glReadPixels(x, y, width, height, format, type, (FloatBuffer) pixels);
		else throw new RuntimeException("Bad type " + pixels.getClass().getName());

	}

	@Override
	public void glRotatef(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		GL13.glSampleCoverage(value, invert);
	}

	@Override
	public void glScalef(float x, float y, float z) {
		GL11.glScalef(x, y, z);
	}

	@Override
	public void glScissor(int x, int y, int width, int height) {
		GL11.glScissor(x, y, width, height);
	}

	@Override
	public void glShadeModel(int mode) {
		GL11.glShadeModel(mode);
	}

	@Override
	public void glStencilFunc(int func, int ref, int mask) {
		GL11.glStencilFunc(func, ref, mask);
	}

	@Override
	public void glStencilMask(int mask) {
		GL11.glStencilMask(mask);
	}

	@Override
	public void glStencilOp(int fail, int zfail, int zpass) {
		GL11.glStencilOp(fail, zfail, zpass);
	}

	@Override
	public void glTexCoordPointer(int size, int type, int stride, Buffer pointer) {
		if (pointer instanceof ShortBuffer && type == GL10.GL_SHORT) GL11.glTexCoordPointer(size, stride, (ShortBuffer) pointer);
		else if (pointer instanceof ByteBuffer && type == GL10.GL_SHORT) GL11.glTexCoordPointer(size, stride, ((ByteBuffer) pointer).asShortBuffer());
		else if (pointer instanceof FloatBuffer && type == GL10.GL_FLOAT) GL11.glTexCoordPointer(size, stride, (FloatBuffer) pointer);
		else if (pointer instanceof ByteBuffer && type == GL10.GL_FLOAT) GL11.glTexCoordPointer(size, stride, ((ByteBuffer) pointer).asFloatBuffer());
		else throw new RuntimeException("Bad type " + pointer.getClass().getName());

	}

	@Override
	public void glTexEnvf(int target, int pname, float param) {
		GL11.glTexEnvf(target, pname, param);
	}

	@Override
	public void glTexEnvfv(int target, int pname, FloatBuffer params) {
		GL11.glTexEnv(target, pname, params);
	}

	@Override
	public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
		if (pixels == null) GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (ByteBuffer) null);
		else if (pixels instanceof ByteBuffer) GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (ByteBuffer) pixels);
		else if (pixels instanceof ShortBuffer) GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (ShortBuffer) pixels);
		else if (pixels instanceof IntBuffer) GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (IntBuffer) pixels);
		else if (pixels instanceof FloatBuffer) GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (FloatBuffer) pixels);
		else if (pixels instanceof DoubleBuffer) GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (DoubleBuffer) pixels);
		else throw new RuntimeException("Bad type " + pixels.getClass().getName());
	}

	@Override
	public void glTexParameterf(int target, int pname, float param) {
		org.lwjgl.opengl.GL11.glTexParameterf(target, pname, param);
	}

	@Override
	public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
		if (pixels instanceof ByteBuffer) GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (ByteBuffer) pixels);
		else if (pixels instanceof ShortBuffer) GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (ShortBuffer) pixels);
		else if (pixels instanceof IntBuffer) GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (IntBuffer) pixels);
		else if (pixels instanceof FloatBuffer) GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (FloatBuffer) pixels);
		else if (pixels instanceof DoubleBuffer) GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (DoubleBuffer) pixels);
		else throw new RuntimeException("Bad type " + pixels.getClass().getName());
	}

	@Override
	public void glTranslatef(float x, float y, float z) {
		GL11.glTranslatef(x, y, z);
	}

	@Override
	public void glVertexPointer(int size, int type, int stride, Buffer pointer) {
		if (pointer instanceof FloatBuffer && type == GL10.GL_FLOAT) GL11.glVertexPointer(size, stride, ((FloatBuffer) pointer));
		else if (pointer instanceof ByteBuffer && type == GL10.GL_FLOAT) GL11.glVertexPointer(size, stride, ((ByteBuffer) pointer).asFloatBuffer());
		else throw new RuntimeException("Bad type " + pointer.getClass().getName());
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
		GL11.glViewport(x, y, width, height);
	}

	@Override
	public void glBindBuffer(int target, int buffer) {
		GL15.glBindBuffer(target, buffer);
	}

	@Override
	public void glBufferData(int target, int size, Buffer data, int usage) {
		if (data instanceof ByteBuffer) GL15.glBufferData(target, (ByteBuffer) data, usage);
		else if (data instanceof ShortBuffer) GL15.glBufferData(target, (ShortBuffer) data, usage);
		else if (data instanceof IntBuffer) GL15.glBufferData(target, (IntBuffer) data, usage);
		else if (data instanceof FloatBuffer) GL15.glBufferData(target, (FloatBuffer) data, usage);
		else if (data instanceof DoubleBuffer) GL15.glBufferData(target, (DoubleBuffer) data, usage);
		else throw new RuntimeException("Bad type " + data.getClass().getName());
	}

	@Override
	public void glBufferSubData(int target, int offset, int size, Buffer data) {
		if (data instanceof ByteBuffer) GL15.glBufferSubData(target, offset, (ByteBuffer) data);
		else if (data instanceof ShortBuffer) GL15.glBufferSubData(target, offset, (ShortBuffer) data);
		else if (data instanceof IntBuffer) GL15.glBufferSubData(target, offset, (IntBuffer) data);
		else if (data instanceof FloatBuffer) GL15.glBufferSubData(target, offset, (FloatBuffer) data);
		else if (data instanceof DoubleBuffer) GL15.glBufferSubData(target, offset, (DoubleBuffer) data);
		else throw new RuntimeException("Bad type " + data.getClass().getName());
	}

	@Override
	public void glClipPlanef(int plane, FloatBuffer equation) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void glClipPlanex(int plane, IntBuffer equation) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void glDeleteBuffers(int n, IntBuffer buffers) {
		GL15.glDeleteBuffers(buffers);
	}

	@Override
	public void glGenBuffers(int n, IntBuffer buffers) {
		GL15.glGenBuffers(buffers);
	}

	@Override
	public void glGetBooleanv(int pname, ByteBuffer params) {
		org.lwjgl.opengl.GL11.glGetBoolean(pname, params);
	}

	@Override
	public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
		GL15.glGetBufferParameter(target, pname, params);
	}

	@Override
	public void glGetClipPlanef(int pname, FloatBuffer eqn) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetClipPlanex(int pname, IntBuffer eqn) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void glGetFloatv(int pname, FloatBuffer params) {
		org.lwjgl.opengl.GL11.glGetFloat(pname, params);
	}

	@Override
	public void glGetLightfv(int light, int pname, FloatBuffer params) {
		GL11.glGetLight(light, pname, params);
	}

	@Override
	public void glGetLightxv(int light, int pname, IntBuffer params) {
		GL11.glGetLight(light, pname, params);
	}

	@Override
	public void glGetMaterialfv(int face, int pname, FloatBuffer params) {
		GL11.glGetMaterial(face, pname, params);
	}

	@Override
	public void glGetMaterialxv(int face, int pname, IntBuffer params) {
		GL11.glGetMaterial(face, pname, params);
	}

	@Override
	public void glGetPointerv(int pname, Buffer[] params) {
		throw new UnsupportedOperationException();
	}

	//	@Override
	//	public void glGetTexEnviv(int env, int pname, IntBuffer params) {
	//		GL11.glGetTexEnv(env, pname, params);
	//	}

	@Override
	public void glGetTexEnviv(int env, int pname, IntBuffer params) {
		org.lwjgl.opengl.GL11.glGetTexEnv(env, pname, params);
	}

	//	@Override
	//	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
	//		GL11.glGetTexParameter(target, pname, params);
	//	}

	@Override
	public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
		org.lwjgl.opengl.GL11.glGetTexParameter(target, pname, params);
	}

	@Override
	public boolean glIsBuffer(int buffer) {
		return GL15.glIsBuffer(buffer);
	}

	@Override
	public boolean glIsEnabled(int cap) {
		return org.lwjgl.opengl.GL11.glIsEnabled(cap);
	}

	@Override
	public boolean glIsTexture(int texture) {
		return org.lwjgl.opengl.GL11.glIsTexture(texture);
	}

	@Override
	public void glLightx(int light, int pname, int param) {
		GL11.glLighti(light, pname, param);
	}

	@Override
	public void glLightxv(int light, int pname, IntBuffer params) {
		GL11.glLight(light, pname, params);
	}

	@Override
	public void glPointParameterf(int pname, float param) {
		GL14.glPointParameterf(pname, param);
	}

	@Override
	public void glPointParameterfv(int pname, FloatBuffer params) {
		GL14.glPointParameter(pname, params);
	}

	@Override
	public void glPointParameterx(int pname, int param) {
		GL14.glPointParameteri(pname, param);
	}

	@Override
	public void glPointParameterxv(int pname, IntBuffer params) {
		GL14.glPointParameter(pname, params);
	}

	@Override
	public void glTexParameterfv(int target, int pname, FloatBuffer params) {
		org.lwjgl.opengl.GL11.glTexParameter(target, pname, params);
	}

	@Override
	public void glTexParameteriv(int target, int pname, IntBuffer params) {
		org.lwjgl.opengl.GL11.glTexParameter(target, pname, params);
	}

	@Override
	public void glGetTexEnvfv(int env, int pname, FloatBuffer params) {
		GL11.glGetTexEnv(env, pname, params);
	}

	@Override
	public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
		GL11.glGetTexParameter(target, pname, params);
	}

	@Override
	public void glAlphaFuncx(int func, int ref) {
		GL11.glAlphaFunc(func, ref);
	}

	@Override
	public void glClearColorx(int red, int green, int blue, int alpha) {
		GL11.glClearColor(red, green, blue, alpha);
	}

	@Override
	public void glClearDepthx(int depth) {
		GL11.glClearDepth(depth);
	}

	@Override
	public void glColor4ub(byte red, byte green, byte blue, byte alpha) {
		GL11.glColor4ub(red, green, blue, alpha);
	}

	@Override
	public void glColor4x(int red, int green, int blue, int alpha) {
		GL11.glColor4ub((byte) red, (byte) green, (byte) blue, (byte) alpha);
	}

	@Override
	public void glDepthRangex(int zNear, int zFar) {
		GL11.glDepthRange(zNear, zFar);
	}

	@Override
	public void glFogx(int pname, int param) {
		GL11.glFogi(pname, param);
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
	public void glGetFixedv(int pname, IntBuffer params) {
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
	public void glLineWidthx(int width) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glLoadMatrixx(IntBuffer m) {
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
	public void glOrthox(int left, int right, int bottom, int top, int zNear, int zFar) {
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
	public void glTexEnvi(int target, int pname, int param) {
		GL11.glTexEnvi(target, pname, param);
	}

	@Override
	public void glTexEnvx(int target, int pname, int param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexEnviv(int target, int pname, IntBuffer params) {
		GL11.glTexEnv(target, pname, params);
	}

	@Override
	public void glTexEnvxv(int target, int pname, IntBuffer params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void glTexParameteri(int target, int pname, int param) {
		GL11.glTexParameteri(target, pname, param);
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

	public void glColorPointer(int size, int type, int stride, int pointer) {
		GL11.glColorPointer(size, type, stride, pointer);
	}

	public void glNormalPointer(int type, int stride, int pointer) {
		GL11.glNormalPointer(type, stride, pointer);
	}

	public void glTexCoordPointer(int size, int type, int stride, int pointer) {
		GL11.glTexCoordPointer(size, type, stride, pointer);
	}

	public void glVertexPointer(int size, int type, int stride, int pointer) {
		GL11.glVertexPointer(size, type, stride, pointer);
	}
}
