/**
 * 
 */
package org.gamelib.backend.lwjgl;

import static org.lwjgl.opengl.ARBTransformFeedback2.*;

import java.nio.IntBuffer;

import org.gamelib.util.Emitter;
import org.gamelib.util.geom.Vector2;
import org.lwjgl.opengl.GLContext;

/**
 * this is just a draft, needs cleaning afterwards
 * @author pwnedary
 */
public class TFBEmmiter implements Emitter {

	int tfID;

	Particle[] particles;
	static final int PARTICLE_TYPE_LAUNCHER = 1;
	IntBuffer transformFeedback;

	/**
	 * 
	 */
	public TFBEmmiter(Vector2 pos) {
		if (!GLContext.getCapabilities().GL_ARB_transform_feedback2) throw new Error("TFBs not supported");
		tfID = glGenTransformFeedbacks();
		glBindTransformFeedback(GL_TRANSFORM_FEEDBACK, tfID);

		particles = new Particle[MAX_PARTICLES];
		particles[0].type = PARTICLE_TYPE_LAUNCHER;
		particles[0].pos = pos;
		particles[0].vel = new Vector2(0.0f, 0.0001f);
		particles[0].lifetimeMillis = 0.0f;
		
		
		for (int i = 0; i < 2; i++) {
			
		}
	}

	public void draw() {
		// glDrawTransformFeedback(GL_TRANSFORM_FEEDBACK, tfID);
	}

	public void update() {

	}

	public void dispose() {
		// glBindTransformFeedback(GL_TRANSFORM_FEEDBACK, 0);
		// glDeleteTransformFeedbacks(tfID);
	}

	public static class Particle {
		float type;
		Vector2 pos;
		Vector2 vel;
		float lifetimeMillis;
	}

}
