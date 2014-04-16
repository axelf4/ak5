/**
 * 
 */
package org.gamelib.backend.gwt.files;

import java.io.IOException;
import java.io.InputStream;

import org.gamelib.util.io.Asset;
import org.gamelib.util.io.Asset.AssetImpl;

import com.google.gwt.typedarrays.shared.Int8Array;

/** @author pwnedary */
public class GwtAsset<T> extends AssetImpl<T> implements Asset<T> {
	private final String path;
	private T data;

	public GwtAsset(String path, T data) {
		this.path = path.replace('\\', '/');
		this.data = data;
	}

	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public T getData() {
		return data;
	}

	@Override
	public InputStream asStream() {
		if (data == null || !(data instanceof Int8Array)) throw new RuntimeException("Not loaded.");
		final Int8Array data = (Int8Array) this.data;
		return new InputStream() {
			private int pos;

			@Override
			public int read() throws IOException {
				if (pos == data.length()) return -1;
				return data.get(pos++) & 0xff;
			}
			
			@Override
			public int available() {
				return data.length() - pos;
			}
			
			// no @Override as not defined in emulation class
			public long skip(long n) throws IOException {
				pos += n;
				return n;
			}
		};
	}
}
