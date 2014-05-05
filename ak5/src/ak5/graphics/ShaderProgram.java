/**
 * 
 */
package ak5.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import ak5.util.Disposable;
import ak5.util.io.BufferUtil;

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

		// gl.glValidateProgram(program);
		// gl.glGetProgramiv(program, GL20.GL_VALIDATE_STATUS, intBuffer);
		// if (intBuffer.get(0) == 0) throw new RuntimeException();

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

	public ShaderProgram(GL20 gl, InputStream vertexShader, InputStream fragmentShader) {
		this(gl, streamToString(vertexShader), streamToString(fragmentShader));
	}

	private static String streamToString(InputStream in) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		try {
			String line;
			while ((line = reader.readLine()) != null)
				stringBuilder.append(line).append(ls);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

	private int compileShader(int type, String source) {
		int shader = gl.glCreateShader(type);

		gl.glShaderSource(shader, source.length(), source, null);
		gl.glCompileShader(shader);

		gl.glGetShaderiv(shader, GL20.GL_COMPILE_STATUS, intBuffer);
		if (intBuffer.get(0) == GL20.GL_FALSE) throw new RuntimeException(getShaderInfoLog(shader));

		return shader;
	}

	public String getProgramInfoLog() { // return gl.glGetProgramInfoLog(program, 0, null, null);
		/* gl.glGetProgramiv(program, GL20.GL_INFO_LOG_LENGTH, intBuffer); */int infoLogLen = 1024 /* intBuffer.get(0) */;
		ByteBuffer logBuffer = BufferUtil.newByteBuffer(infoLogLen);
		gl.glGetProgramInfoLog(program, infoLogLen, null, logBuffer);
		byte[] logArray = new byte[infoLogLen];
		logBuffer.get(logArray);
		return new String(logArray).trim();
	}

	private String getShaderInfoLog(int shader) { // return gl.glGetShaderInfoLog(shader, 0, null, null);
		/* gl.glGetShaderiv(shader, GL20.GL_INFO_LOG_LENGTH, intBuffer); */int infoLogLen = 1024 /* intBuffer.get(0) */;
		ByteBuffer logBuffer = BufferUtil.newByteBuffer(infoLogLen);
		gl.glGetShaderInfoLog(shader, infoLogLen, null, logBuffer);
		byte[] logArray = new byte[infoLogLen];
		logBuffer.get(logArray);
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

	/** Returns the location of an attribute variable, or {@code -1} if the named attribute variable is not an active
	 * attribute in this program object or if {@code name} starts with the reserved prefix "gl_".
	 * 
	 * @param name The name of the attribute variable whose location is to be queried
	 * @return the location of the specified attribute
	 * @see <a href="https://www.opengl.org/sdk/docs/man/docbook4/xhtml/glGetAttribLocation.xml">OpenGL
	 *      specification</a> */
	public int getAttribLocation(CharSequence name) { // return gl.glGetAttribLocation(program, name);
		Integer location = attributes2.get(name);
		if ((location = attributes2.get(name)) == null) attributes2.put(name, location = gl.glGetAttribLocation(program, name));
		return location;
	}

	/** Returns the location of a uniform variable, or {@code -1} if the named uniform variable is not an active uniform
	 * in this program object or if {@code name} starts with the reserved prefix "gl_".
	 * 
	 * @param name The name of the uniform variable whose location is to be queried
	 * @return the location of the specified attribute
	 * @see <a href="https://www.opengl.org/sdk/docs/man/docbook4/xhtml/glGetUniformLocation.xml">OpenGL
	 *      specification</a> */
	public int getUniformLocation(CharSequence name) { // return gl.glGetUniformLocation(program, name);
		Integer location;
		if ((location = uniforms.get(name)) == null) uniforms.put(name, location = gl.glGetUniformLocation(program, name));
		return location;
	}
}
