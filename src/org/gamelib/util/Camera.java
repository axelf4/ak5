/**
 * 
 */
package org.gamelib.util;

import org.gamelib.backend.Graphics;
import org.gamelib.util.geom.Vector3;

/**
 * @author pwnedary
 */
public abstract class Camera {

	protected final Vector3 location = new Vector3();
	protected final Vector3 direction = new Vector3();
	protected int width, height;

	public Camera(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public abstract void project(Graphics g);

	public abstract void unproject(Graphics g);

	public void translate(float x, float y, float z) {
		translate(new Vector3(x, y, z));
	}

	public void translate(Vector3 v) {
		location.add(v);
	}

	public static class OrthographicCamera extends Camera {

		public OrthographicCamera(int width, int height) {
			super(width, height);
		}

		@Override
		public void project(Graphics g) {
			g.translate(location.x, location.y, location.z);
		}

		@Override
		public void unproject(Graphics g) {
			g.translate(-location.x, -location.y, -location.z);
		}

	}

}
