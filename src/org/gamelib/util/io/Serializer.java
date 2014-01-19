/**
 * 
 */
package org.gamelib.util.io;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pwnedary
 */
public interface Serializer<T> {
	public void write(Buf buffer, T object);

	public T read(Buf buffer);

	public static final class Serialization {
		public static Map<Class<?>, Serializer<?>> serializers = new HashMap<>();

		public void writeLength(ByteBuffer buffer, int length) {
			buffer.putInt(length);
		}

		public int readLength(ByteBuffer buffer) {
			return buffer.getInt();
		}

		public int getLengthLength() {
			return 4;
		}

		static {
			serializers.put(Boolean.class, new BooleanSerializer());
			serializers.put(Byte.class, new ByteSerializer());
			serializers.put(Character.class, new CharSerializer());
			serializers.put(Short.class, new ShortSerializer());
			serializers.put(Integer.class, new IntSerializer());
			serializers.put(Long.class, new LongSerializer());
			serializers.put(Float.class, new FloatSerializer());
			serializers.put(Double.class, new DoubleSerializer());
			serializers.put(String.class, new StringSerializer());
		}

		private static class BooleanSerializer implements Serializer<Boolean> {
			@Override
			public void write(Buf buffer, Boolean object) {
				buffer.writeBoolean(object);
			}

			@Override
			public Boolean read(Buf NewBuf) {
				return NewBuf.readBoolean();
			}
		}

		private static class ByteSerializer implements Serializer<Byte> {
			public void write(Buf buffer, Byte object) {
				buffer.write(object);
			}

			public Byte read(Buf NewBuf) {
				return NewBuf.read();
			}
		}

		private static class CharSerializer implements Serializer<Character> {
			public void write(Buf buffer, Character object) {
				buffer.writeChar(object);
			}

			public Character read(Buf NewBuf) {
				return NewBuf.readChar();
			}
		}

		private static class ShortSerializer implements Serializer<Short> {
			public void write(Buf buffer, Short object) {
				buffer.writeShort(object);
			}

			public Short read(Buf NewBuf) {
				return NewBuf.readShort();
			}
		}

		private static class IntSerializer implements Serializer<Integer> {
			public void write(Buf buffer, Integer object) {
				buffer.writeInt(object);
			}

			public Integer read(Buf NewBuf) {
				return NewBuf.readInt();
			}
		}

		private static class LongSerializer implements Serializer<Long> {
			public void write(Buf buffer, Long object) {
				buffer.writeLong(object);
			}

			public Long read(Buf NewBuf) {
				return NewBuf.readLong();
			}
		}

		private static class FloatSerializer implements Serializer<Float> {
			public void write(Buf buffer, Float object) {
				buffer.writeFloat(object);
			}

			public Float read(Buf NewBuf) {
				return NewBuf.readFloat();
			}
		}

		private static class DoubleSerializer implements Serializer<Double> {
			public void write(Buf buffer, Double object) {
				buffer.writeDouble(object);
			}

			public Double read(Buf NewBuf) {
				return NewBuf.readDouble();
			}
		}

		private static class StringSerializer implements Serializer<String> {
			public void write(Buf buffer, String object) {
				buffer.writeString(object);
			}

			public String read(Buf NewBuf) {
				return NewBuf.readString();
			}
		}
	}
}
