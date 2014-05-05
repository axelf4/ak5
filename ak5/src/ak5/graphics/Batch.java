/**
 * 
 */
package ak5.graphics;

/** @author pwnedary */
public interface Batch {
	void begin();

	void end();

	void draw(Texture texture, float x1, float y1, float x2, float y2);

	void draw(Texture texture, float dx1, float dy1, float dx2, float dy2, float sx1, float sy1, float sx2, float sy2);

	void flush();
}
