/**
 * 
 */
package ak5;

/** Callback SAM (Single Abstract Method)-interface for subscribed {@link Event}s. At registration all {@link Event}s
 * will be subscribed to, but excluded as {@link #handle(Event)} denies.
 * 
 * @author pwnedary */
public interface Handler {
	/** Handles the published {@code event}. Returns <tt>false</tt> if this {@link Handler} isn't subscribing to {@link Event}s of
	 * {@linkplain Event#getClass() <code>event</code>'s class}. ex:
	 * 
	 * <pre>
	 * public boolean handle(Event event) {
	 *  if (event instanceof Event.Tick) {
	 *   ...
	 *  } else return false;
	 *  return true;
	 * }
	 * </pre>
	 * 
	 * @param event the {@link Event} published
	 * @return <tt>true</tt> if subscribing to event */
	public boolean handle(Event event);
}
