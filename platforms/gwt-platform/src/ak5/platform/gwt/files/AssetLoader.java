/**
 * 
 */
package ak5.platform.gwt.files;

import java.util.HashMap;
import java.util.Map;

import ak5.Event;
import ak5.Handler;
import ak5.Event.EventImpl;
import ak5.util.io.Asset;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.typedarrays.shared.Int8Array;
import com.google.gwt.typedarrays.shared.TypedArrays;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xhr.client.ReadyStateChangeHandler;
import com.google.gwt.xhr.client.XMLHttpRequest;
import com.google.gwt.xhr.client.XMLHttpRequest.ResponseType;

/** @author pwnedary */
public class AssetLoader {
	private String[] assets;
	private Map<CharSequence, Asset<?>> assetLookup = new HashMap<CharSequence, Asset<?>>();
	private int done;
	private Handler handler;

	public AssetLoader(String[] assets, final Handler handler) {
		this.assets = assets;
		this.handler = handler;

		for (final String asset : assets) {
			String[] sub = asset.split(":");
			String contentType = sub[0];
			final String path = sub[1];
			if (contentType.startsWith("image")) {
				final Image img = new Image();
				img.setVisible(false);
				RootPanel.get().add(img);
				img.addLoadHandler(new LoadHandler() {
					@Override
					public void onLoad(LoadEvent event) {
						RootPanel.get().remove(img);
						assetLookup.put(path, new GwtAsset<Image>(path, img));
						
						assetLoaded();
					}
				});
				img.setUrl(path);
			} else {
				XMLHttpRequest xhr = XMLHttpRequest.create();
				xhr.open("GET", path);
				if (contentType.startsWith("text")) {
					xhr.setResponseType(ResponseType.Default);
					xhr.setOnReadyStateChange(new ReadyStateChangeHandler() {
						@Override
						public void onReadyStateChange(XMLHttpRequest xhr) {
							if (xhr.getReadyState() == XMLHttpRequest.DONE) {
								if (xhr.getStatus() == 200) {
									assetLookup.put(path, new GwtAsset<String>(path, xhr.getResponseText()));
								} else GWT.log("Failed to load asset " + path + ".");

								assetLoaded();
							}
						}
					});
				} else {
					xhr.setResponseType(ResponseType.ArrayBuffer);
					xhr.setOnReadyStateChange(new ReadyStateChangeHandler() {
						@Override
						public void onReadyStateChange(XMLHttpRequest xhr) {
							if (xhr.getReadyState() == XMLHttpRequest.DONE) {
								if (xhr.getStatus() == 200) {
									assetLookup.put(path, new GwtAsset<Int8Array>(path, TypedArrays.createInt8Array(xhr.getResponseArrayBuffer())));
								} else GWT.log("Failed to load asset " + path + ".");

								assetLoaded();
							}
						}
					});
				}
				xhr.send();
			}
		}
	}

	public Asset<?> getAsset(CharSequence path) {
		return assetLookup.get(path);
	}

	private void assetLoaded() {
		done++;
		if (done()) handler.handle(new Done());
	}

	public boolean done() {
		return done >= assets.length;
	}

	public static class Done extends EventImpl implements Event {}
}
