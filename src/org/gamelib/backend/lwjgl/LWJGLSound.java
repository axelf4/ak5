/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.openal.AL10.*;

import java.nio.IntBuffer;

import org.gamelib.backend.Sound;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

/**
 * TODO: move static fields to {@link LWJGLResourceFactory}
 * 
 * @author pwnedary
 */
public class LWJGLSound implements Sound {
	/** Maximum data buffers we will need. */
	public static final int NUM_BUFFERS = 3;
	/** Maximum emissions we will need. */
	public static final int NUM_SOURCES = 3;

	/** We support at most 256 buffers */
	static int[] buffers = new int[256];
	/** Number of sources is limited the user (and hardware) */
	static int[] sources;
	/** Current index in our buffers */
	static int bufferIndex;
	/** Current index in our source list */
	static int sourceIndex;
	/** Buffers hold sound data. */
	static IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
	/** Sources are points emitting sound. */
	static IntBuffer source = BufferUtils.createIntBuffer(NUM_BUFFERS);

	/** The {@link source} buffer's index. */
	int index;

	public LWJGLSound(int index) {
		this.index = index;
	}

	@Override
	public void play() {
		// int channel = source.get(index);
		alSourcei(source.get(index), AL_BUFFER, buffer.get(index)); // link buffer and source
		alSourcei(source.get(index), AL_LOOPING, AL_FALSE);
		alSourcePlay(source.get(index));
	}

	@Override
	public void pause() {
		AL10.alSourcePause(source.get(index));
	}

	@Override
	public void stop() {
		AL10.alSourceStop(source.get(index));
	}

	@Override
	public void loop(int count) {
		alSourcei(source.get(index), AL_LOOPING, count <= -1 ? AL_TRUE : AL_FALSE);
		if (count <= -1) alSourcei(source.get(index), AL_BUFFER, buffer.get(index)); // link buffer and source
		else for (int i = 0; i < count; i++)
			AL10.alSourceQueueBuffers(source.get(index), buffer.get(index));
		AL10.alSourcePlay(source.get(index));
	}

	@Override
	public boolean playing() {
		return alGetSourcei(source.get(index), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

}
