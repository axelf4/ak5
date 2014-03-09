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
		glBlendFunc(sfactor, dfactor);
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
	public void glClearDepth(double depth) {
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
	public void glDeleteTextures(IntBuffer textures) {
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
	public void glDepthRange(double zNear, double zFar) {
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
	public void glFog(int pname, float param) {
		GL11.glFogf(pname, param);
	}

	@Override
	public void glFog(int pname, FloatBuffer params) {
		GL11.glFog(pname, params);
	}

	@Override
	public void glFrontFace(int mode) {
		GL11.glFrontFace(mode);
	}

	@Override
	public void glFrustum(double left, double right, double bottom, double top, double zNear, double zFar) {
		GL11.glFrustum(left, right, bottom, top, zNear, zFar);
	}

	@Override
	public void glGenTextures(IntBuffer textures) {
		GL11.glGenTextures(textures);
	}

	@Override
	public int glGetError() {
		return GL11.glGetError();
	}

	@Override
	public int glGetInteger(int pname) {
		return GL11.glGetInteger(pname);
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
	public void glLightModel(int pname, float param) {
		GL11.glLightModelf(pname, param);
	}

	@Override
	public void glLightModel(int pname, FloatBuffer params) {
		GL11.glLightModel(pname, params);
	}

	@Override
	public void glLight(int light, int pname, float param) {
		GL11.glLightf(light, pname, param);
	}

	@Override
	public void glLight(int light, int pname, FloatBuffer params) {
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
	public void glLoadMatrix(FloatBuffer m) {
		GL11.glLoadMatrix(m);
	}

	@Override
	public void glLogicOp(int opcode) {
		GL11.glLogicOp(opcode);
	}

	@Override
	public void glMaterial(int face, int pname, float param) {
		GL11.glMaterialf(face, pname, param);
	}

	@Override
	public void glMaterial(int face, int pname, FloatBuffer params) {
		GL11.glMaterial(face, pname, params);
	}

	@Override
	public void glMatrixMode(int mode) {
		GL11.glMatrixMode(mode);
	}

	@Override
	public void glMultMatrix(FloatBuffer m) {
		GL11.glMultMatrix(m);
	}

	@Override
	public void glMultiTexCoord4(int target, float s, float t, float r, float q) {
		GL13.glMultiTexCoord4f(target, s, t, r, q);
	}

	@Override
	public void glNormal3(float nx, float ny, float nz) {
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
	public void glOrtho(double left, double right, double bottom, double top, double zNear, double zFar) {
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
	public void glRotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}

	@Override
	public void glSampleCoverage(float value, boolean invert) {
		GL13.glSampleCoverage(value, invert);
	}

	@Override
	public void glScale(float x, float y, float z) {
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
	public void glTexEnv(int target, int pname, float param) {
		GL11.glTexEnvf(target, pname, param);
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
	public void glTexParameter(int target, int pname, float param) {
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
	public void glTranslate(float x, float y, float z) {
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
