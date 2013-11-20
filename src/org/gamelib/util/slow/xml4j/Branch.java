/**
 * 
 */
package org.gamelib.util.slow.xml4j;

import java.util.List;

/**
 * @author pwnedary
 *
 */
public interface Branch {
	Tag add(Tag element);
	// public Tag addElement(String qName);
	
	List<Tag> children();
	
	List<Tag> downwards();
	
	Tag getTagById(String id);

	List<Tag> getTagsByName(String qName);
}
