/**
 * 
 */
package org.gamelib.ui;

/**
 * @author Axel
 *
 */
public interface Layout {
	
	public void layout();
	
	/** If invalid, calls layout. */
	public void validate();
	
	public void invalidate();
	
	public int getPrefferedWidth();
	
	public int getPrefferedHeight();

}
