/**
 * 
 */
package org.gamelib.network;

/**
 * @author pwnedary
 */
public interface Serializer<T> {
	public void write(Output output, T object);

	public T read(Input input, Class<T> type);
}
