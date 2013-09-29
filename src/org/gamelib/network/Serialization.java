/**
 * 
 */
package org.gamelib.network;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pwnedary
 */
@SuppressWarnings("unchecked")
public class Serialization {
	public static Map<Class<?>, Serializer<?>> serializers = new HashMap<>();
	static {
		DefaultSerializers.register();
	}
	
	public <T> void write(ByteBuffer buffer, T object) {
		try {
			Output output = new Output(new ByteBufferOutputStream(buffer));
			Class<?> type = object.getClass();
			output.writeString(type.getName());
			
			Serializer<T> serializer = (Serializer<T>) serializers.get(type);
			serializer.write(output, object);
			output.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public <T> T read(ByteBuffer buffer) {
		try {
			Input input = new Input(new ByteBufferInputStream(buffer));
			String className = input.readString();
			Class<T> type = (Class<T>) Class.forName(className);
			
			Serializer<T> serializer = (Serializer<T>) serializers.get(type);
			T object = serializer.read(input, type);
			return object;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void writeLength(ByteBuffer buffer, int length) {
		buffer.putInt(length);
	}

	public int readLength(ByteBuffer buffer) {
		return buffer.getInt();
	}

	public int getLengthLength() {
		return 4;
	}
}
