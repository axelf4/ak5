/**
 * 
 */
package org.gamelib.backend.gwt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.gamelib.Drawable;
import org.gamelib.Event;
import org.gamelib.Handler;
import org.gamelib.Loop;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Backend;
import org.gamelib.backend.Input;
import org.gamelib.backend.Sound;
import org.gamelib.graphics.GL10;
import org.gamelib.graphics.GL20;
import org.gamelib.graphics.Texture;
import org.gamelib.util.Configuration;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;

/** @author pwnedary */
public class GWTBackend implements Backend { //, EntryPoint {
	private Configuration configuration;
	private Handler handler;
	private boolean running;
	private Loop loop;
	private Canvas canvas;
	private GL20 gl;

	//	@Override
	//	public void onModuleLoad() {
	//		start(getConfiguration(), getHandler());
	//	}

	public void start(Configuration configuration, Handler handler) {
		this.configuration = configuration;
		this.handler = handler;

		this.running = true;
		loop = new RequestAnimationFrameLoop(new DefaultLoopListener());
		loop.run();
	}

	public class DefaultLoopListener implements LoopListener {
		@Override
		public void start() {
			GWTBackend.this.start();
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
		// TODO Auto-generated method stub
		return null;
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
	public InputStream getResourceAsStream(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Texture createTexture(int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Texture getTexture(String name) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sound getSound(File file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
