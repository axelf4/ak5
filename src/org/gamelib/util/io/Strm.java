/**
 * 
 */
package org.gamelib.util.io;

/**
 * @author pwnedary
 */
public interface Strm extends ObjectSerialization {
	public int availible();

	/**
	 * Skips over <code>n</code> bytes of data.
	 * 
	 * @param n the number of bytes to be skipped.
	 * @return the actual number of bytes skipped.
	 */
	public long skip(long n);

	/* BYTE */

	/** Reads a single byte. */
	byte read();

	/** Reads count bytes and writes them to the specified byte[], starting at offset. */
	public void read(byte[] dst, int off, int len);

	/** Writes a byte. */
	public void write(byte b);

	/** Writes the bytes. Note the byte[] length is not written. */
	public void write(byte[] src, int off, int len);
}
