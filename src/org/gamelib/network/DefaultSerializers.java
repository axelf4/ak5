/**
 * 
 */
package org.gamelib.network;

/**
 * @author pwnedary
 */
public class DefaultSerializers {
	public static void register() {
		Serialization.serializers.put(Boolean.class, new BooleanSerializer());
		Serialization.serializers.put(Byte.class, new ByteSerializer());
		Serialization.serializers.put(Character.class, new CharSerializer());
		Serialization.serializers.put(Short.class, new ShortSerializer());
		Serialization.serializers.put(Integer.class, new IntSerializer());
		Serialization.serializers.put(Long.class, new LongSerializer());
		Serialization.serializers.put(Float.class, new FloatSerializer());
		Serialization.serializers.put(Double.class, new DoubleSerializer());
		Serialization.serializers.put(String.class, new StringSerializer());
	}

	static class BooleanSerializer implements Serializer<Boolean> {
		/** {@inheritDoc} */
		@Override
		public void write(Output output, Boolean object) {
			output.writeBoolean(object);
		}

		/** {@inheritDoc} */
		@Override
		public Boolean read(Input input, Class<Boolean> type) {
			return input.readBoolean();
		}
	}

	static public class ByteSerializer implements Serializer<Byte> {
		public void write(Output output, Byte object) {
			output.writeByte(object);
		}

		public Byte read(Input input, Class<Byte> type) {
			return input.readByte();
		}
	}

	static public class CharSerializer implements Serializer<Character> {
		public void write(Output output, Character object) {
			output.writeChar(object);
		}

		public Character read(Input input, Class<Character> type) {
			return input.readChar();
		}
	}

	static public class ShortSerializer implements Serializer<Short> {
		public void write(Output output, Short object) {
			output.writeShort(object);
		}

		public Short read(Input input, Class<Short> type) {
			return input.readShort();
		}
	}

	static public class IntSerializer implements Serializer<Integer> {
		public void write(Output output, Integer object) {
			output.writeInt(object, false);
		}

		public Integer read(Input input, Class<Integer> type) {
			return input.readInt(false);
		}
	}

	static public class LongSerializer implements Serializer<Long> {
		public void write(Output output, Long object) {
			output.writeLong(object, false);
		}

		public Long read(Input input, Class<Long> type) {
			return input.readLong(false);
		}
	}

	static public class FloatSerializer implements Serializer<Float> {
		public void write(Output output, Float object) {
			output.writeFloat(object);
		}

		public Float read(Input input, Class<Float> type) {
			return input.readFloat();
		}
	}

	static public class DoubleSerializer implements Serializer<Double> {
		public void write(Output output, Double object) {
			output.writeDouble(object);
		}

		public Double read(Input input, Class<Double> type) {
			return input.readDouble();
		}
	}

	/** @see Output#writeString(String) */
	static public class StringSerializer implements Serializer<String> {
		public void write(Output output, String object) {
			output.writeString(object);
		}

		public String read(Input input, Class<String> type) {
			return input.readString();
		}
	}

}
