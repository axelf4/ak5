/**
 * 
 */
package org.gamelib.util.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

/** @author pwnedary */
public final class BufferUtil {
	private BufferUtil() {}

	public static ByteBuffer newByteBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.nativeOrder());
	}

	public static FloatBuffer newFloatBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
	}

	public static DoubleBuffer newDoubleBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * 8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
	}

	public static ShortBuffer newShortBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
	}

	public static CharBuffer newCharBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * 2).order(ByteOrder.nativeOrder()).asCharBuffer();
	}

	public static IntBuffer newIntBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
	}

	public static LongBuffer newLongBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity * 8).order(ByteOrder.nativeOrder()).asLongBuffer();
	}
}
