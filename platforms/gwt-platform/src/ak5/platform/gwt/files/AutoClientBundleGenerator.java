/**
 * 
 */
package ak5.platform.gwt.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLConnection;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/** @author pwnedary */
public class AutoClientBundleGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		String packageName = "org.gamelib.backend.gwt.files";
		String className = "AutoClientBundleImpl";
		PrintWriter printWriter = context.tryCreate(logger, packageName, className);
		if (printWriter == null) return packageName + '.' + className; // Source already created
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, className);
		composer.addImplementedInterface(packageName + ".AutoClientBundle");
		SourceWriter sourceWriter = composer.createSourceWriter(context, printWriter);
		sourceWriter.println("public String[] getAssets() { return new String[] { ");

		File assetPath = new File("../", getAssetPath(context));
		File assetOutputPath = new File("./" +getAssetOutputPath(context));
		try {
			copy(assetPath, assetOutputPath, sourceWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sourceWriter.println("}; }");
		sourceWriter.commit(logger);
		return packageName + '.' + className;
	}

	private String getAssetPath(GeneratorContext context) {
		ConfigurationProperty assetPathProperty = null;
		try {
			assetPathProperty = context.getPropertyOracle().getConfigurationProperty("assetpath");
			if (assetPathProperty.getValues().size() != 0) {
				String path = assetPathProperty.getValues().get(0);
				if (path != null && new File("../" + path).exists()) return path;
			}
		} catch (BadPropertyValueException e) {}
		throw new RuntimeException("No assetpath defined. Add <set-configuration-property name=\"assetpath\" value=\"relative/path/to/assets/\"/> to your GWT projects gwt.xml file (from GWT project POV)");
	}

	private String getAssetOutputPath(GeneratorContext context) {
		ConfigurationProperty assetOutputPathProperty = null;
		try {
			assetOutputPathProperty = context.getPropertyOracle().getConfigurationProperty("assetoutputpath");
			if (assetOutputPathProperty.getValues().size() != 0) {
				String path = assetOutputPathProperty.getValues().get(0);
				if (path != null && new File(path).exists()) return path;
			}
			throw new RuntimeException("No assetoutputpath defined. Add <set-configuration-property name=\"assetoutputpath\" value=\"relative/path/to/war/\"/> to your GWT projects gwt.xml file");
		} catch (BadPropertyValueException e) {
			return "assets/";
		}
	}

	private void copy(File src, File dest, SourceWriter sourceWriter)
			throws IOException, UnableToCompleteException {
		if (src.isDirectory()) {
			dest.mkdirs(); // If target directory doesn't exists, create it
			// List all the directory contents
			for (String file : src.list())
				copy(new File(src, file), new File(dest, file), sourceWriter); // Recursive folder look-up
		} else { // If file, then copy it
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			// Copy the file content in bytes
			while ((length = in.read(buffer)) > 0)
				out.write(buffer, 0, length);
			in.close();
			out.close();

			sourceWriter.println("\"" + URLConnection.guessContentTypeFromName(dest.getPath()) + ":" + dest.getPath().replace('\\', '/').replace(getWarDirectory(null).getPath() + '/', "") + "\", ");
		}
	}

	/** When invoking the GWT compiler from GPE, the working directory is the Eclipse project directory. However, when
	 * launching a GPE project, the working directory is the project 'war' directory. This methods returns the war
	 * directory in either case in a fairly naive and non-robust manner. */
	private File getWarDirectory(TreeLogger logger)
			throws UnableToCompleteException {
		File currentDirectory = new File(".");
		try {
			if (currentDirectory.getCanonicalPath().endsWith("war")) return currentDirectory;
			else return new File("war");
		} catch (IOException e) {
			logger.log(TreeLogger.ERROR, "Failed to get canonical path", e);
			throw new UnableToCompleteException();
		}
	}
}
