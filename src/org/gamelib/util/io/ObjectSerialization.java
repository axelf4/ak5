/**
 * 
 */
package org.gamelib.util.io;


/**
 * @author Axel
 */
public interface ObjectSerialization {
	/* BYTE */
	/** Reads a single byte. */
	byte read();

	/** Reads count bytes and writes them to the specified byte[], starting at offset. */
	public void read(byte[] dst, int off, int len);

	/** Writes a byte. */
	public void write(byte b);

	/** Writes the bytes. Note the byte[] length is not written. */
	public void write(byte[] src, int off, int len);

	/* INTEGER */
	/** Reads a 4 byte int. */
	public int readInt();

	/** Writes a 4 byte int. Uses BIG_ENDIAN byte order. */
	public void writeInt(int value);

	/* FLOAT */
	/** Reads a 4 byte float. */
	public float readFloat();

	/** Writes a 4 byte float. */
	public void writeFloat(float value);

	/* SHORT */
	/** Reads a 2 byte short. */
	public short readShort();

	/** Writes a 2 byte short. Uses BIG_ENDIAN byte order. */
	public void writeShort(short value);

	/* LONG */
	/** Reads an 8 byte long. */
	public long readLong();

	/** Writes an 8 byte long. Uses BIG_ENDIAN byte order. */
	public void writeLong(long value);

	/* BOOLEAN */
	/** Reads a 1 byte boolean. */
	public boolean readBoolean();

	/** Writes a 1 byte boolean. */
	public void writeBoolean(boolean value);

	/* CHAR */
	/** Reads a 2 byte char. */
	public char readChar();

	/** Writes a 2 byte char. Uses BIG_ENDIAN byte order. */
	public void writeChar(char value);

	/* DOUBLE */
	/** Reads an 8 bytes double. */
	public double readDouble();

	/** Writes an 8 byte double. */
	public void writeDouble(double value);

	/* STRING */
	public String readString();

	/** Writes an UTF-8 string. */
	public void writeString(String value);

	/* OBJECT */
	public <T> Object readObject();

	public void writeObject(Object value);

	/**
	 * Returns the number of bytes used to serialize an integer.
	 * 
	 * @return the number of bytes used to serialize an integer
	 */
	public int intBytes();

	public static abstract class ObjectSerializationImp implements ObjectSerialization {
		@Override
		public int intBytes() {
			return 4;
		}
	}
}
