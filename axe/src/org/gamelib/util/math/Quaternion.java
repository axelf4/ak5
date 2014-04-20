/**
 * 
 */
package org.gamelib.util.math;

/** @author pwnedary */
public class Quaternion extends Vector4 {
	public Quaternion mult(Quaternion q) {
		float x = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
		float y = this.w * q.y - this.x * q.z + this.y * q.w + this.z * q.x;
		float z = this.w * q.z + this.x * q.y - this.y * q.x + this.z * q.w;
		float w = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public Quaternion inverse() {
		x *= -1;
		z *= -1;
		y *= -1;
		return this;
	}

	public Quaternion setEulerAngles(float yaw, float pitch, float roll) {
		float sY = (float) Math.sin(pitch * 0.5);
		float cY = (float) Math.cos(pitch * 0.5);
		float sZ = (float) Math.sin(roll * 0.5);
		float cZ = (float) Math.cos(roll * 0.5);
		float sX = (float) Math.sin(yaw * 0.5);
		float cX = (float) Math.cos(yaw * 0.5);

		w = cY * cZ * cX - sY * sZ * sX;
		x = sY * sZ * cX + cY * cZ * sX;
		y = sY * cZ * cX + cY * sZ * sX;
		z = cY * sZ * cX - sY * cZ * sX;
		return this;
	}

	/** Sets the quaternion to the given euler angles in radians.
	 * 
	 * @param yaw the rotation around the y axis in radians
	 * @param pitch the rotation around the x axis in radians
	 * @param roll the rotation around the z axis in radians
	 * @return this quaternion */
	public Quaternion setEulerAnglesRad(float yaw, float pitch, float roll) {
		final float hr = roll * 0.5f;
		final float shr = (float) Math.sin(hr);
		final float chr = (float) Math.cos(hr);
		final float hp = pitch * 0.5f;
		final float shp = (float) Math.sin(hp);
		final float chp = (float) Math.cos(hp);
		final float hy = yaw * 0.5f;
		final float shy = (float) Math.sin(hy);
		final float chy = (float) Math.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
		y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
		z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
		w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
		return this;
	}

	/** Set from rotation around axis. */
	public Quaternion set(Vector3 axis, float radians) {
		float sin = (float) Math.sin(radians * 0.5);
		float cos = (float) Math.cos(radians * 0.5);

		w = cos;
		x = axis.x * sin;
		y = axis.y * sin;
		z = axis.z * sin;
		return this;
	}
}
