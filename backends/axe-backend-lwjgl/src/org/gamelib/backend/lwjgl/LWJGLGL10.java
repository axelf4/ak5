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

/** @author pwnedary */
public class LWJGLGL10 implements GL10 {
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
		GL11.glTexParameterf(target, pname, param);
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
}
