/**
 * 
 */
package org.gamelib.backend.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/** @author Axel */
public interface Resources extends ClientBundle {
	/** The instance of the Resources ClientBundle. */
	public static Resources INSTANCE = GWT.create(Resources.class);

	/** The texture to use in the example.
	 * 
	 * @return the image to use as texture. */
	@Source(value = { "texture.png" })
	ImageResource texture();

}
