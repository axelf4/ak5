/**
 * 
 */
package org.gamelib.graphics;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.gamelib.util.Disposable;
import org.gamelib.util.io.BufferUtil;
import org.gamelib.util.math.Matrix4;
import org.gamelib.util.math.Vector2;
import org.gamelib.util.math.Vector3;

/** @author pwnedary */
public class ShaderProgram implements Disposable {
	/** default name for position attributes **/
	public static final String POSITION_ATTRIBUTE = "a_position";
	/** default name for normal attributes **/
	public static final String NORMAL_ATTRIBUTE = "a_normal";
	/** default name for color attributes **/
	public static final String COLOR_ATTRIBUTE = "a_color";
	/** default name for texcoords attributes, append texture unit number **/
	public static final String TEXCOORD_ATTRIBUTE = "a_texCoord";
	/** default name for tangent attribute **/
	public static final String TANGENT_ATTRIBUTE = "a_tangent";
	/** default name for binormal attribute **/
	public static final String BINORMAL_ATTRIBUTE = "a_binormal";

	private GL20 gl;
	/** The OpenGL handle for this shader program object. */
	private int program;
	private int vert;
	private int frag;
	// private boolean compiled;

	/** attribute lookup **/
	private final Map<CharSequence, Integer> attributes2 = new HashMap<CharSequence, Integer>();
	/** attribute types **/
	private final Map<String, Integer> attributeTypes = new HashMap<String, Integer>();
	/** attribute sizes **/
	private final Map<String, Integer> attributeSizes = new HashMap<String, Integer>();
	/** attribute names **/
	private String[] attributeNames;

	/** uniform lookup **/
	private final Map<CharSequence, Integer> uniforms = new HashMap<CharSequence, Integer>();
	/** uniform types **/
	private final Map<String, Integer> uniformTypes = new HashMap<String, Integer>();
	/** uniform sizes **/
	private final Map<String, Integer> uniformSizes = new HashMap<String, Integer>();
	/** uniform names **/
	private String[] uniformNames;

	private ByteBuffer buffer = BufferUtil.newByteBuffer(4 * 4 * 4);
	private IntBuffer intBuffer = buffer.asIntBuffer();
	private FloatBuffer floatBuffer = buffer.asFloatBuffer();

	public ShaderProgram(GL20 gl, String vertexShader, String fragmentShader) {
		this.gl = gl;
		if (vertexShader == null) throw new IllegalArgumentException("vertexShader can't be null");
		if (fragmentShader == null) throw new IllegalArgumentException("fragmentShader can't be null");
		vert = compileShader(GL20.GL_VERTEX_SHADER, vertexShader);
		frag = compileShader(GL20.GL_FRAGMENT_SHADER, fragmentShader);

		program = gl.glCreateProgram();
		if (program == 0) throw new RuntimeException();
		gl.glAttachShader(program, vert);
		gl.glAttachShader(program, frag);
		gl.glLinkProgram(program);

		gl.glGetProgramiv(program, GL20.GL_LINK_STATUS, intBuffer);
		if (intBuffer.get(0) == 0) throw new RuntimeException();

		//		gl.glValidateProgram(program);
		//		gl.glGetProgramiv(program, GL20.GL_VALIDATE_STATUS, intBuffer);
		//		if (intBuffer.get(0) == 0) throw new RuntimeException();

		boolean negative = false;
		if (negative) {
			// Fetch attributes and uniforms
			IntBuffer size = BufferUtil.newIntBuffer(1);
			IntBuffer type = BufferUtil.newIntBuffer(1);
			//		gl.glGetProgramiv(program, GL20.GL_ACTIVE_ATTRIBUTE_MAX_LENGTH, intBuffer);
			//		int strLen = intBuffer.get(0);
			//		gl.glGetProgramiv(program, GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH, intBuffer);
			//		strLen = Math.max(strLen, intBuffer.get(0));
			int strLen = 50;
			byte[] nameArray = new byte[strLen];
			ByteBuffer nameBuffer = BufferUtil.newByteBuffer(strLen);

			gl.glGetProgramiv(program, GL20.GL_ACTIVE_ATTRIBUTES, intBuffer); // Fetch attributes
			int numAttributes = intBuffer.get(0);
			attributeNames = new String[numAttributes];
			for (int i = 0; i < numAttributes; i++) {
				gl.glGetActiveAttrib(program, i, strLen, null, size, type, nameBuffer);
				((ByteBuffer) nameBuffer.position(0)).get(nameArray);
				String name = new String(nameArray).trim();
				int location = gl.glGetAttribLocation(program, name);
				attributes2.put(name, location);
				attributeTypes.put(name, type.get(0));
				attributeSizes.put(name, size.get(0));
				attributeNames[i] = name;
			}
			gl.glGetProgramiv(program, GL20.GL_ACTIVE_UNIFORMS, (IntBuffer) intBuffer.clear()); // Fetch uniforms
			int numUniforms = intBuffer.get(0);
			uniformNames = new String[numUniforms];
			for (int i = 0; i < numUniforms; i++) {
				gl.glGetActiveUniform(program, i, strLen, null, size, type, nameBuffer);
				((ByteBuffer) nameBuffer.position(0)).get(nameArray);
				String name = new String(nameArray).trim();
				int location = gl.glGetUniformLocation(program, name);
				uniforms.put(name, location);
				uniformTypes.put(name, type.get(0));
				uniformSizes.put(name, size.get(0));
				uniformNames[i] = name;
			}
		}
	}

	private int compileShader(int type, String source) {
		int shader = gl.glCreateShader(type);

		gl.glShaderSource(shader, source.length(), source, null);
		gl.glCompileShader(shader);

		gl.glGetShaderiv(shader, GL20.GL_COMPILE_STATUS, intBuffer);
		if (intBuffer.get(0) == 0) throw new RuntimeException(getLog());

		return shader;
	}

	public String getLog() { // return gl.glGetProgramInfoLog(program, 0, null, null);
		// gl.glGetProgramiv(program, GL20.GL_INFO_LOG_LENGTH, intBuffer);
		// int infoLogLen = intBuffer.get(0);
		int infoLogLen = 1024;
		ByteBuffer logBuffer = BufferUtil.newByteBuffer(infoLogLen);
		gl.glGetProgramInfoLog(program, infoLogLen, null, logBuffer);
		byte[] logArray = new byte[infoLogLen];
		((ByteBuffer) logBuffer.clear()).get(logArray);
		return new String(logArray).trim();
	}

	public void begin() {
		gl.glUseProgram(program);
	}

	public void end() {
		gl.glUseProgram(0);
	}

	@Override
	public void dispose() {
		gl.glUseProgram(0);
		gl.glDeleteShader(vert);
		gl.glDeleteShader(frag);
		gl.glDeleteProgram(program);
	}

	/** Returns the location of an attribute variable or {@code -1}.
	 * 
	 * @param name The name of the attribute variable whose location is to be queried
	 * @return the location of a specific attribute */
	public int getAttribLocation(CharSequence name) { // return gl.glGetAttribLocation(program, name);
		Integer location = attributes2.get(name);
		if ((location = attributes2.get(name)) == null) attributes2.put(name, location = gl.glGetAttribLocation(program, name));
		return location;
	}

	/** Returns the location of a uniform variable or {@code -1}.
	 * 
	 * @param name The name of the uniform variable whose location is to be queried
	 * @return the location of a specific attribute */
	public int getUniformLocation(CharSequence name) { // return gl.glGetUniformLocation(program, name);
		Integer location;
		if ((location = uniforms.get(name)) == null) uniforms.put(name, location = gl.glGetUniformLocation(program, name));
		return location;
	}

	/* Overloads */

	/** Sets the vertex attribute with the given name. Throws an IllegalArgumentException in case it is not called in
	 * between a {@link #begin()}/{@link #end()} block.
	 *
	 * @param name The attribute name
	 * @param size The number of components, must be >= 1 and <= 4
	 * @param type The type, must be one of GL20.GL_BYTE, GL20.GL_UNSIGNED_BYTE, GL20.GL_SHORT,
	 *            GL20.GL_UNSIGNED_SHORT,GL20.GL_FIXED, or GL20.GL_FLOAT. GL_FIXED will not work on the desktop
	 * @param normalize Whether fixed point data should be normalized. Will not work on the desktop
	 * @param stride The stride in bytes between successive attributes
	 * @param buffer The buffer containing the vertex attributes. */
	public void setVertexAttribute(String name, int size, int type, boolean normalize, int stride, Buffer buffer) {
		int location = getAttribLocation(name);
		if (location == -1) return;
		gl.glVertexAttribPointer(location, size, type, normalize, stride, buffer);
	}

	/** Sets the vertex attribute with the given name. Throws an IllegalArgumentException in case it is not called in
	 * between a {@link #begin()}/{@link #end()} block.
	 * 
	 * @param name The attribute name
	 * @param size The number of components, must be >= 1 and <= 4
	 * @param type The type, must be one of GL20.GL_BYTE, GL20.GL_UNSIGNED_BYTE, GL20.GL_SHORT,
	 *            GL20.GL_UNSIGNED_SHORT,GL20.GL_FIXED, or GL20.GL_FLOAT. GL_FIXED will not work on the desktop
	 * @param normalize Whether fixed point data should be normalized. Will not work on the desktop
	 * @param stride The stride in bytes between successive attributes
	 * @param offset Byte offset into the vertex buffer object bound to GL20.GL_ARRAY_BUFFER. */
	public void setVertexAttribute(String name, int size, int type, boolean normalize, int stride, int offset) {
		int location = getAttribLocation(name);
		if (location == -1) return;
		gl.glVertexAttribPointer(location, size, type, normalize, stride, offset);
	}

	/** Disables the vertex attribute with the given name.
	 *
	 * @param name The vertex attribute name */
	public void disableVertexAttribute(String name) {
		int location = getAttribLocation(name);
		if (location == -1) return;
		gl.glDisableVertexAttribArray(location);
	}

	/** Enables the vertex attribute with the given name.
	 *
	 * @param name The vertex attribute name */
	public void enableVertexAttribute(String name) {
		int location = getAttribLocation(name);
		if (location == -1) return;
		gl.glEnableVertexAttribArray(location);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value the value */
	public void setUniformi(String name, int value) {
		int location = getUniformLocation(name);
		gl.glUniform1i(location, value);
	}

	public void setUniformi(int location, int value) {
		gl.glUniform1i(location, value);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value1 the first value
	 * @param value2 the second value */
	public void setUniformi(String name, int value1, int value2) {
		int location = getUniformLocation(name);
		gl.glUniform2i(location, value1, value2);
	}

	public void setUniformi(int location, int value1, int value2) {
		gl.glUniform2i(location, value1, value2);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value1 the first value
	 * @param value2 the second value
	 * @param value3 the third value */
	public void setUniformi(String name, int value1, int value2, int value3) {
		int location = getUniformLocation(name);
		gl.glUniform3i(location, value1, value2, value3);
	}

	public void setUniformi(int location, int value1, int value2, int value3) {
		gl.glUniform3i(location, value1, value2, value3);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value1 the first value
	 * @param value2 the second value
	 * @param value3 the third value
	 * @param value4 the fourth value */
	public void setUniformi(String name, int value1, int value2, int value3, int value4) {
		int location = getUniformLocation(name);
		gl.glUniform4i(location, value1, value2, value3, value4);
	}

	public void setUniformi(int location, int value1, int value2, int value3, int value4) {
		gl.glUniform4i(location, value1, value2, value3, value4);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value the value */
	public void setUniformf(String name, float value) {
		int location = getUniformLocation(name);
		gl.glUniform1f(location, value);
	}

	public void setUniformf(int location, float value) {
		gl.glUniform1f(location, value);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value1 the first value
	 * @param value2 the second value */
	public void setUniformf(String name, float value1, float value2) {
		int location = getUniformLocation(name);
		gl.glUniform2f(location, value1, value2);
	}

	public void setUniformf(int location, float value1, float value2) {
		gl.glUniform2f(location, value1, value2);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value1 the first value
	 * @param value2 the second value
	 * @param value3 the third value */
	public void setUniformf(String name, float value1, float value2, float value3) {
		int location = getUniformLocation(name);
		gl.glUniform3f(location, value1, value2, value3);
	}

	public void setUniformf(int location, float value1, float value2, float value3) {
		gl.glUniform3f(location, value1, value2, value3);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param value1 the first value
	 * @param value2 the second value
	 * @param value3 the third value
	 * @param value4 the fourth value */
	public void setUniformf(String name, float value1, float value2, float value3, float value4) {
		int location = getUniformLocation(name);
		gl.glUniform4f(location, value1, value2, value3, value4);
	}

	public void setUniformf(int location, float value1, float value2, float value3, float value4) {
		gl.glUniform4f(location, value1, value2, value3, value4);
	}

	//	public void setUniform1fv(String name, float[] values, int offset, int length) {
	//		int location = fetchUniformLocation(name);
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform1fv(location, length, floatBuffer);
	//	}
	//
	//	public void setUniform1fv(int location, float[] values, int offset, int length) {
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform1fv(location, length, floatBuffer);
	//	}
	//
	//	public void setUniform2fv(String name, float[] values, int offset, int length) {
	//		int location = fetchUniformLocation(name);
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform2fv(location, length / 2, floatBuffer);
	//	}
	//
	//	public void setUniform2fv(int location, float[] values, int offset, int length) {
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform2fv(location, length / 2, floatBuffer);
	//	}
	//
	//	public void setUniform3fv(String name, float[] values, int offset, int length) {
	//		int location = fetchUniformLocation(name);
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform3fv(location, length / 3, floatBuffer);
	//	}
	//
	//	public void setUniform3fv(int location, float[] values, int offset, int length) {
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform3fv(location, length / 3, floatBuffer);
	//	}
	//
	//	public void setUniform4fv(String name, float[] values, int offset, int length) {
	//		int location = fetchUniformLocation(name);
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform4fv(location, length / 4, floatBuffer);
	//	}
	//
	//	public void setUniform4fv(int location, float[] values, int offset, int length) {
	//		ensureBufferCapacity(length << 2);
	//		floatBuffer.clear();
	//		BufferUtils.copy(values, floatBuffer, length, offset);
	//		gl.glUniform4fv(location, length / 4, floatBuffer);
	//	}

	/** Sets the uniform matrix with the given name. Throws an IllegalArgumentException in case it is not called in
	 * between a {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param matrix the matrix */
	public void setUniformMatrix(String name, Matrix4 matrix) {
		setUniformMatrix(name, matrix, false);
	}

	/** Sets the uniform matrix with the given name. Throws an IllegalArgumentException in case it is not called in
	 * between a {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param matrix the matrix
	 * @param transpose whether the matrix should be transposed */
	public void setUniformMatrix(String name, Matrix4 matrix, boolean transpose) {
		setUniformMatrix(getUniformLocation(name), matrix, transpose);
	}

	public void setUniformMatrix(int location, Matrix4 matrix) {
		setUniformMatrix(location, matrix, false);
	}

	public void setUniformMatrix(int location, Matrix4 matrix, boolean transpose) {
		((FloatBuffer) floatBuffer.clear()).put(matrix.data).flip();
		gl.glUniformMatrix4fv(location, 1, transpose, floatBuffer);
	}

	//	/** Sets the uniform matrix with the given name. Throws an IllegalArgumentException in case it is not called in
	//	 * between a {@link #begin()}/{@link #end()} block.
	//	 *
	//	 * @param name the name of the uniform
	//	 * @param matrix the matrix */
	//	public void setUniformMatrix(String name, Matrix3 matrix) {
	//		setUniformMatrix(name, matrix, false);
	//	}
	//
	//	/** Sets the uniform matrix with the given name. Throws an IllegalArgumentException in case it is not called in
	//	 * between a {@link #begin()}/{@link #end()} block.
	//	 *
	//	 * @param name the name of the uniform
	//	 * @param matrix the matrix
	//	 * @param transpose whether the uniform matrix should be transposed */
	//	public void setUniformMatrix(String name, Matrix3 matrix, boolean transpose) {
	//		int location = fetchUniformLocation(name);
	//		float[] vals = matrix.getValues();
	//		this.matrix.clear();
	//		BufferUtils.copy(vals, this.matrix, vals.length, 0);
	//		gl.glUniformMatrix3fv(location, 1, transpose, this.matrix);
	//	}
	//
	//	public void setUniformMatrix(int location, Matrix3 matrix) {
	//		setUniformMatrix(location, matrix, false);
	//	}
	//
	//	public void setUniformMatrix(int location, Matrix3 matrix, boolean transpose) {
	//		float[] vals = matrix.getValues();
	//		this.matrix.clear();
	//		BufferUtils.copy(vals, this.matrix, vals.length, 0);
	//		gl.glUniformMatrix3fv(location, 1, transpose, this.matrix);
	//	}
	//
	//	/** Sets an array of uniform matrices with the given name. Throws an IllegalArgumentException in case it is not
	//	 * called in between a {@link #begin()}/{@link #end()} block.
	//	 *
	//	 * @param name the name of the uniform
	//	 * @param buffer buffer containing the matrix data
	//	 * @param transpose whether the uniform matrix should be transposed */
	//	public void setUniformMatrix3fv(String name, FloatBuffer buffer, int count, boolean transpose) {
	//		buffer.position(0);
	//		int location = fetchUniformLocation(name);
	//		gl.glUniformMatrix3fv(location, count, transpose, buffer);
	//	}

	/** Sets an array of uniform matrices with the given name. Throws an IllegalArgumentException in case it is not
	 * called in between a {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param buffer buffer containing the matrix data
	 * @param transpose whether the uniform matrix should be transposed */
	public void setUniformMatrix4fv(String name, FloatBuffer buffer, int count, boolean transpose) {
		buffer.position(0);
		int location = getUniformLocation(name);
		gl.glUniformMatrix4fv(location, count, transpose, buffer);
	}

	public void setUniformMatrix4fv(int location, float[] values, int offset, int length) {
		// ensureBufferCapacity(length << 2);
		((FloatBuffer) floatBuffer.clear()).put(values, offset, length);
		gl.glUniformMatrix4fv(location, length / 16, false, floatBuffer);
	}

	public void setUniformMatrix4fv(String name, float[] values, int offset, int length) {
		// ensureBufferCapacity(length << 2);
		((FloatBuffer) floatBuffer.clear()).put(values, offset, length);
		int location = getUniformLocation(name);
		gl.glUniformMatrix4fv(location, length / 16, false, floatBuffer);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param values x and y as the first and second values respectively */
	public void setUniformf(String name, Vector2 values) {
		setUniformf(name, values.x, values.y);
	}

	public void setUniformf(int location, Vector2 values) {
		setUniformf(location, values.x, values.y);
	}

	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	 * {@link #begin()}/{@link #end()} block.
	 *
	 * @param name the name of the uniform
	 * @param values x, y and z as the first, second and third values respectively */
	public void setUniformf(String name, Vector3 values) {
		setUniformf(name, values.x, values.y, values.z);
	}

	public void setUniformf(int location, Vector3 values) {
		setUniformf(location, values.x, values.y, values.z);
	}

	//	/** Sets the uniform with the given name. Throws an IllegalArgumentException in case it is not called in between a
	//	 * {@link #begin()}/{@link #end()} block.
	//	 *
	//	 * @param name the name of the uniform
	//	 * @param values r, g, b and a as the first through fourth values respectively */
	//	public void setUniformf(String name, Color values) {
	//		setUniformf(name, values.r, values.g, values.b, values.a);
	//	}
	//
	//	public void setUniformf(int location, Color values) {
	//		setUniformf(location, values.r, values.g, values.b, values.a);
	//	}
}
