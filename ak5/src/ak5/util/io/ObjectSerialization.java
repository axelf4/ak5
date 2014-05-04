/**
 * 
 */
package ak5.util.io;

/** @author Axel */
public interface ObjectSerialization {
	/* STRING */
	public String readString();

	/** Writes an UTF-8 string. */
	public void writeString(String value);

	/* OBJECT */
	public <T> Object readObject();

	public void writeObject(Object value);
}
