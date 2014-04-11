/**
 * 
 */
package org.gamelib.util.io;

import java.io.IOException;
import java.io.InputStream;

/** @author pwnedary */
public class StreamByteBuf extends HeapByteBuf implements ByteBuf {
	private final InputStream is;

	public StreamByteBuf(int capacity, InputStream is) {
		super(capacity);
		this.is = is;
		try {
			is.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected int require(int required) {
		int remaining = limit - position;
		if (is != null) {
//			try {
//				int read = is.read(b, position, remaining);
//				return read;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			return 0;
		} else return remaining;
	}

}
