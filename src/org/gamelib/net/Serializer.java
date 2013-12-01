/**
 * 
 */
package org.gamelib.net;


/**
 * @author pwnedary
 */
public interface Serializer<T> {
	public void write(Output output, T object);

	public T read(Input input);
}
