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

	private enum State {
		/** We have computed the next element and haven't returned it yet. */
		READY,
		/** We haven't yet computed or have already returned the element. */
		NOT_READY,
		/** We have reached the end of the data and are finished. */
		DONE,
		/** We've suffered an exception and are kaput. */
		FAILED
	}

	private State state = State.NOT_READY;
	private E next;

	/**
	 * Returns the next element. <b>Note:</b> the implementor must call {@link #endOfData} when it has reached the end of the data. Failure to do so could result in an infinite loop.
	 * <p>
	 * This class invokes {@link #computeNext} during the caller's initial invocation of {@link #hasNext} or {@link #next}, and on the first invocation of {@link #hasNext} or {@link #next} that follows each successful call to {@link #next}. Once the implementor either invokes {@link #endOfData} or throws any exception, {@link #computeNext} is guaranteed to never be called again.
	 * <p>
	 * If this method throws an exception, it will propagate outward to the {@code hasNext} or {@code next} invocation that invoked this method. Any further attempts to use the iterator will result in {@code IllegalStateException}.
	 * @return the next element if there was one. {@code null} is a valid element value. If {@link #endOfData} was called during execution, the return value will be ignored.
	 */

	protected abstract E computeNext();

	/**
	 * Implementors of {@link #computeNext} <b>must</b> invoke this method when there is no data left.
	 * @return always null
	 */
	protected final E endOfData() {
		state = State.DONE;
		return null;
	}

	/** Attempts to get the next element from the implementation class. */
	private final boolean tryToComputeNext() {
		state = State.FAILED; // temporary pessimism
		next = computeNext();
		if (state == State.FAILED) {
			state = State.READY; // ok, whew
			return true;
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public final boolean hasNext() {
		assert state != State.FAILED;
		if (state == State.DONE) return false;
		else if (state == State.READY) return true;

		return tryToComputeNext();
	}

	/** {@inheritDoc} */
	@Override
	public final E next() {
		if (!hasNext()) throw new NoSuchElementException();
		state = State.NOT_READY;
		return next;
	}

	/**
	 * Unsupported.
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}

}
