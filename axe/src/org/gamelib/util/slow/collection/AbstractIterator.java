/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An abstract Adapter that provides an Iterator interface for certain types of data which are conceptually iterable, but require single-element preloading (e.g. {@link java.io.BufferedReader}). Subclasses need to implement only the {@link #computeNext} template method.
 * <p>
 * Example:
 * 
 * <pre>
 * public static Iterator&lt;String&gt; readLines(final BufferedReader in) {
 * 	return new AbstractIterator&lt;String&gt;() {
 * 		protected String computeNext() {
 * 			try {
 * 				String result = in.readLine();
 * 				if (result == null) {
 * 					endOfData();
 * 					in.close();
 * 				}
 * 				return result;
 * 			} catch (IOException e) {
 * 				throw new RuntimeException(e);
 * 			}
 * 		}
 * 	};
 * }
 * </pre>
 * @author pwnedary
 */
public abstract class AbstractIterator<E> implements Iterator<E> {
	private State state = State.NOT_READY;
	private E next;

	@Override
	public final boolean hasNext() {
		if (state == State.NOT_READY) {
			state = State.READY;
			next = computeNext();
		}
		return state != State.DONE;
	}

	@Override
	public final E next() {
		if (!hasNext()) throw new NoSuchElementException();
		state = State.NOT_READY;
		return next;
	}

	/**
	 * Unsupported. Implementations may support.
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/** Implementors shall invoke when there's no data left. */
	protected E endOfData() {
		state = State.DONE;
		return null;
	}

	/**
	 * Returns the next item or {@link #endOfData()} if reached end.
	 * @return the next item, if exists
	 */
	protected abstract E computeNext();

	private enum State {
		/** We have computed the next element and haven't returned it yet. */
		NOT_READY, //
		/** We have computed the next element and haven't returned it yet. */
		READY, //
		/** We have reached the end of the data and are finished. */
		DONE;
	}
}
