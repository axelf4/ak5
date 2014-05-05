/**
 * 
 */
package ak5.util.io;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/** @author pwnedary */
public interface Serializer<T> {
	public void write(ByteBuf buffer, T object);

	public T read(ByteBuf buffer);

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
			public void write(ByteBuf buffer, Boolean object) {
				buffer.putBoolean(object);
			}

			@Override
			public Boolean read(ByteBuf NewBuf) {
				return NewBuf.getBoolean();
			}
		}

		private static class ByteSerializer implements Serializer<Byte> {
			public void write(ByteBuf buffer, Byte object) {
				buffer.put(object);
			}

			public Byte read(ByteBuf NewBuf) {
				return NewBuf.get();
			}
		}

		private static class CharSerializer implements Serializer<Character> {
			public void write(ByteBuf buffer, Character object) {
				buffer.putChar(object);
			}

			public Character read(ByteBuf NewBuf) {
				return NewBuf.getChar();
			}
		}

		private static class ShortSerializer implements Serializer<Short> {
			public void write(ByteBuf buffer, Short object) {
				buffer.putShort(object);
			}

			public Short read(ByteBuf NewBuf) {
				return NewBuf.getShort();
			}
		}

		private static class IntSerializer implements Serializer<Integer> {
			public void write(ByteBuf buffer, Integer object) {
				buffer.putInt(object);
			}

			public Integer read(ByteBuf NewBuf) {
				return NewBuf.getInt();
			}
		}

		private static class LongSerializer implements Serializer<Long> {
			public void write(ByteBuf buffer, Long object) {
				buffer.putLong(object);
			}

			public Long read(ByteBuf NewBuf) {
				return NewBuf.getLong();
			}
		}

		private static class FloatSerializer implements Serializer<Float> {
			public void write(ByteBuf buffer, Float object) {
				buffer.putFloat(object);
			}

			public Float read(ByteBuf NewBuf) {
				return NewBuf.getFloat();
			}
		}

		private static class DoubleSerializer implements Serializer<Double> {
			public void write(ByteBuf buffer, Double object) {
				buffer.putDouble(object);
			}

			public Double read(ByteBuf NewBuf) {
				return NewBuf.getDouble();
			}
		}

		private static class StringSerializer implements Serializer<String> {
			public void write(ByteBuf buffer, String object) {
				buffer.writeString(object);
			}

			public String read(ByteBuf NewBuf) {
				return NewBuf.readString();
			}
		}
	}
}
