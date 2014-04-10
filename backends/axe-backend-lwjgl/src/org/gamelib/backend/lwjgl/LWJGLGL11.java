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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;

/** @author pwnedary */
public class LWJGLGL11 extends LWJGLGL10 implements org.gamelib.graphics.GL11 {
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
	public void glTexParameterf(int target, int pname, float param) {
		org.lwjgl.opengl.GL11.glTexParameterf(target, pname, param);
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
