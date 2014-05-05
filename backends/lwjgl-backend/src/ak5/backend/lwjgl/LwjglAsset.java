/**
 * 
 */
package ak5.backend.lwjgl;

import java.io.File;
import java.io.InputStream;

import ak5.util.io.Asset;
import ak5.util.io.Asset.AssetImpl;

/** @author pwnedary */
public class LwjglAsset extends AssetImpl<InputStream> implements Asset<InputStream> {
	private final File file;

	public LwjglAsset(File file) {
		this.file = file;
	}

	@Override
	public String getPath() {
		return file.getPath();
	}

	public String getName() {
		return file.getName();
	}

	public InputStream getData() {
		return asStream();
	}

	@Override
	public InputStream asStream() {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(getPath());
	}
}
