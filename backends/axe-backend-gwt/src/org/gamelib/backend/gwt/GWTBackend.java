/**
 * 
 */
package org.gamelib.backend.gwt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.gamelib.Drawable;
import org.gamelib.Event;
import org.gamelib.Handler;
import org.gamelib.Loop;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Input;
import org.gamelib.backend.Sound;
import org.gamelib.backend.gwt.files.AssetLoader;
import org.gamelib.backend.gwt.files.AutoClientBundle;
import org.gamelib.graphics.GL10;
import org.gamelib.graphics.GL20;
import org.gamelib.graphics.Texture;
import org.gamelib.graphics.Texture.Filter;
import org.gamelib.util.Configuration;
import org.gamelib.util.io.Asset;
import org.gamelib.util.io.BufferUtil;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;

/** @author pwnedary */
public class GWTBackend implements Backend, Handler { //, EntryPoint {
	private Configuration configuration;
	private Handler handler;
	private boolean running;
	private Loop loop;
	private Canvas canvas;
	private GwtInput input;
	private GL20 gl;
	private AssetLoader assetLoader;

	//	@Override
	//	public void onModuleLoad() {
	//		start(getConfiguration(), getHandler());
	//	}

	public void start(Configuration configuration, Handler handler) {
		this.configuration = configuration;
		this.handler = handler;

		AutoClientBundle clientBundle = GWT.create(AutoClientBundle.class);
		GWT.log("Found assets " + Arrays.toString(clientBundle.getAssets()));
		assetLoader = new AssetLoader(clientBundle.getAssets(), this);

		this.running = true;
		loop = new RequestAnimationFrameLoop(new DefaultLoopListener());
		//		loop = new TimerLoop(new DefaultLoopListener());
		start();
	}

	public class DefaultLoopListener implements LoopListener {
		@Override
		public void start() {
			// GWTBackend.this.start();
			handler.handle(new Event.Create()); // Initialize game lastly
		}

		@Override
		public void stop() {
			handler.handle(new Event.Dispose());
			dispose();
		}

		@Override
		public void tick(float delta) {
			//			getInput().poll();
			//			if (getInput().keyPressed(Key.KEY_ESCAPE)) GWTBackend.this.stop();
			handler.handle(new Event.Tick(delta));
		}

		@Override
		public void draw(float delta) {
			GWTBackend.this.draw(new Drawable() {
				@Override
				public void draw(GL10 gl, float delta) {
					handler.handle(new Event.Draw(gl, delta));
				}
			}, delta);
		}

		@Override
		public boolean keepRunning() {
			return GWTBackend.this.keepRunning();
		}
	}

	protected void start() {
		System.out.println("looking for canvas element: " + "canvas-" + GWT.getModuleName());
		CanvasElement element = (CanvasElement) Document.get().getElementById("canvas-" + GWT.getModuleName());
		canvas = element != null ? Canvas.wrap(element) : Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(configuration.getProperty("width", 300));
		canvas.setCoordinateSpaceHeight(configuration.getProperty("height", 150));
		canvas.setPixelSize(configuration.getProperty("width", 300), configuration.getProperty("height", 150));
		RootPanel.get().add(canvas);

		input = new GwtInput(canvas, handler);
		WebGLRenderingContext ctx = (WebGLRenderingContext) canvas.getContext("webgl");
		if (ctx == null) ctx = (WebGLRenderingContext) canvas.getContext("experimental-webgl");
		if (ctx == null) Window.alert("Sorry, Your Browser doesn't support WebGL!");
		this.gl = new GwtGL20(ctx);
	}

	public void stop() {
		// TODO Auto-generated method stub
		//		super.stop();
	}

	public void draw(Drawable drawable, float delta) {
		drawable.draw(gl, delta);
		//		drawWebGL();
	}

	@Override
	public boolean handle(Event event) {
		if (event instanceof AssetLoader.Done) {
			loop.run();
		} else return false;
		return true;
	}

	//	public native long getTime() /*-{
	//		return new Date().getTime();
	//	}-*/;

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setTitle(String title) {
		Document.get().setTitle(title);
	}

	public int getWidth() {
		return canvas.getCoordinateSpaceWidth();
	}

	public int getHeight() {
		return canvas.getCoordinateSpaceHeight();
	}

	public Input getInput() {
		return input;
	}

	public GL10 getGL() {
		return gl;
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public boolean keepRunning() {
		return running;
	}

	@Override
	public Loop getLoop() {
		return loop;
	}

	@Override
	public InputStream getResourceAsStream(CharSequence name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Asset<?> getAsset(CharSequence path) {
		return assetLoader.getAsset(path);
	}

	@Override
	public Texture createTexture(int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Texture getTexture(CharSequence name) throws IOException {
		@SuppressWarnings("unchecked")
		Asset<Image> asset = (Asset<Image>) (name instanceof Asset<?> ? name : getAsset(name));
		Image img = asset.getData();

		IntBuffer textures = BufferUtil.newIntBuffer(1);
		gl.glGenTextures(1, textures);
		final Texture texture = new Texture.GLTexture(gl, GL10.GL_TEXTURE_2D, textures.get(0), img.getWidth(), img.getHeight());
		texture.bind();
		gl.glPixelStorei(WebGLRenderingContext.UNPACK_FLIP_Y_WEBGL, GL10.GL_TRUE);
		((GwtGL20) gl).getWebGLRenderingContext().texImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, img.getElement());
		texture.setFilter(Filter.LINEAR, Filter.LINEAR);
		texture.unbind();

		return texture;
	}

	@Override
	public Sound getSound(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
