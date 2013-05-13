/**
 * 
 */
package org.gamelib.backend.lwjgl;

import java.nio.IntBuffer;

import org.gamelib.backend.Sound;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

/**
 * TODO: move static fields to {@link LWJGLResourceFactory}
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
	/** Our internal scratch buffer */
	static IntBuffer scratchBuffer = BufferUtils.createIntBuffer(256);
	/** Whether we're running in no sound mode */
	static boolean soundOutput;
	/** Current index in our buffers *ny */
	static int bufferIndex;
	/** Current index in our source list */
	static int sourceIndex;
	/** Buffers hold sound data. */
	static IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
	/** Sources are points emitting sound. */
	static IntBuffer source = BufferUtils.createIntBuffer(NUM_BUFFERS);

	/** The {@link source} buffer's index. */
	int index;

	/**
	 * 
	 */
	public LWJGLSound(int index) {
		this.index = index;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#play()
	 */
	@Override
	public void play() {
		// int channel = source.get(index);
		AL10.alSourcei(source.get(index), AL10.AL_BUFFER, buffer.get(index)); // link buffer and source
		AL10.alSourcei(source.get(index), AL10.AL_LOOPING, AL10.AL_FALSE);
		AL10.alSourcePlay(source.get(index));
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#pause()
	 */
	@Override
	public void pause() {
		AL10.alSourcePause(source.get(index));
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#stop()
	 */
	@Override
	public void stop() {
		AL10.alSourceStop(source.get(index));
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#loop(int)
	 */
	@Override
	public void loop(int count) {
		AL10.alSourcei(source.get(index), AL10.AL_LOOPING, AL10.AL_TRUE);
		AL10.alSourcePlay(source.get(index));
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Sound#playing()
	 */
	@Override
	public boolean playing() {
		return AL10.alGetSourcei(source.get(index), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
		// return AL10.alGetSourcei(sources[buffer], AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

}
