/**
 * 
 */
package org.gamelib.util.slow.xml4j;

/**
 * @author pwnedary
 */
public interface Attr {

	void setQName(String qName);

	String getQName();

	void setValue(String value);

	String getValue();
	
	/** @return whether you can use getTagById */
	boolean isId();
}
