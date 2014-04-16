/**
 * 
 */
package org.gamelib.util.io;

import java.io.InputStream;

import org.gamelib.Handler;

/** @author pwnedary */
public interface Asset<T> extends CharSequence {
	String getPath();

	String getName();

	T getData();

	/** Will return null if this asset hasn't completely loaded yet. */
	InputStream asStream();

	public abstract class AssetImpl<T> implements Asset<T> {
		protected Handler handler;

		@Override
		public String getName() {
			return getPath().substring(getPath().lastIndexOf('/'));
		}

		@Override
		public int length() {
			return getPath().length();
		}

		@Override
		public char charAt(int index) {
			return getPath().charAt(index);
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return getPath().subSequence(start, end);
		}

		@Override
		public String toString() {
			return getPath();
		}
	}
}
