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

import org.gamelib.graphics.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;

/** @author Axel */
public class LWJGLGL11 extends LWJGLGL10 implements GL11 {
	@Override
	public void glBindBuffer(int target, int buffer) {
		GL15.glBindBuffer(target, buffer);
	}

	@Override
	public void glBufferData(int target, Buffer data, int usage) {
		if (data instanceof ByteBuffer) GL15.glBufferData(target, (ByteBuffer) data, usage);
		else if (data instanceof ShortBuffer) GL15.glBufferData(target, (ShortBuffer) data, usage);
		else if (data instanceof IntBuffer) GL15.glBufferData(target, (IntBuffer) data, usage);
		else if (data instanceof FloatBuffer) GL15.glBufferData(target, (FloatBuffer) data, usage);
		else if (data instanceof DoubleBuffer) GL15.glBufferData(target, (DoubleBuffer) data, usage);
		else throw new RuntimeException("Bad type " + data.getClass().getName());
	}

	@Override
	public void glBufferSubData(int target, long offset, Buffer data) {
		if (data instanceof ByteBuffer) GL15.glBufferSubData(target, offset, (ByteBuffer) data);
		else if (data instanceof ShortBuffer) GL15.glBufferSubData(target, offset, (ShortBuffer) data);
		else if (data instanceof IntBuffer) GL15.glBufferSubData(target, offset, (IntBuffer) data);
		else if (data instanceof FloatBuffer) GL15.glBufferSubData(target, offset, (FloatBuffer) data);
		else if (data instanceof DoubleBuffer) GL15.glBufferSubData(target, offset, (DoubleBuffer) data);
		else throw new RuntimeException("Bad type " + data.getClass().getName());
	}

	@Override
	public void glClipPlane(int plane, DoubleBuffer equation) {
		org.lwjgl.opengl.GL11.glClipPlane(plane, equation);
	}

	@Override
	public void glDeleteBuffers(IntBuffer buffers) {
		GL15.glDeleteBuffers(buffers);
	}

	@Override
	public void glGenBuffers(IntBuffer buffers) {
		GL15.glGenBuffers(buffers);
	}

	@Override
	public boolean glGetBoolean(int pname) {
		return org.lwjgl.opengl.GL11.glGetBoolean(pname);
	}

	@Override
	public int glGetBufferParameter(int target, int pname) {
		return GL15.glGetBufferParameteri(target, pname);
	}

	@Override
	public void glGetClipPlane(int plane, DoubleBuffer equation) {
		org.lwjgl.opengl.GL11.glGetClipPlane(plane, equation);
	}

	@Override
	public float glGetFloat(int pname) {
		return org.lwjgl.opengl.GL11.glGetFloat(pname);
	}

	@Override
	public void glGetLight(int light, int pname, FloatBuffer params) {
		org.lwjgl.opengl.GL11.glGetLight(light, pname, params);
	}

	@Override
	public void glGetMaterial(int face, int pname, FloatBuffer params) {
		org.lwjgl.opengl.GL11.glGetMaterial(face, pname, params);
	}

	@Override
	public ByteBuffer glGetPointer(int pname, long size) {
		return org.lwjgl.opengl.GL11.glGetPointer(pname, size);
	}

	@Override
	public int glGetTexEnvi(int coord, int pname) {
		return org.lwjgl.opengl.GL11.glGetTexEnvi(coord, pname);
	}

	@Override
	public float glGetTexEnvf(int coord, int pname) {
		return org.lwjgl.opengl.GL11.glGetTexEnvf(coord, pname);
	}

	@Override
	public int glGetTexParameteri(int target, int pname) {
		return org.lwjgl.opengl.GL11.glGetTexParameteri(target, pname);
	}

	@Override
	public float glGetTexParameterf(int target, int pname) {
		return org.lwjgl.opengl.GL11.glGetTexParameterf(target, pname);
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
	public void glPointParameterf(int pname, float param) {
		GL14.glPointParameterf(pname, param);
	}

	@Override
	public void glPointParameterf(int pname, FloatBuffer params) {
		GL14.glPointParameter(pname, params);
	}

	@Override
	public void glTexEnv(int target, int pname, int param) {
		org.lwjgl.opengl.GL11.glTexEnvi(target, pname, param);
	}
}
