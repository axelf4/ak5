/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.Iterator;

/**
 * An {@link Iterator} that can reset its cursor to its initial position.
 * @author pwnedary
 *
 */
public abstract class ResettableIterator<E> implements Iterator<E> {
	
	/**
     * Resets the cursor of this Iterator to its initial position
     */
    public abstract void reset();

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
    	throw new UnsupportedOperationException();
    }


}
