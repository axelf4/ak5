/**
 * 
 */
package ak5.util.io.net;

import ak5.util.io.ByteBuf;

/**
 * @author Axel
 */
public class RegisterTCP implements FrameworkMessage<RegisterTCP> {
	public int connectionId;

	public RegisterTCP(int connectionId) {
		this.connectionId = connectionId;
	}

	public RegisterTCP() {}

	@Override
	public void write(ByteBuf output, RegisterTCP object) {
		output.putInt(connectionId);
	}

	@Override
	public RegisterTCP read(ByteBuf input) {
		return new RegisterTCP(input.getInt());
	}
}
