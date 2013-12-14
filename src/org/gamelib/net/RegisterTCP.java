/**
 * 
 */
package org.gamelib.net;

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
	public void write(Output output, RegisterTCP object) {
		output.writeInt(connectionId);
	}

	@Override
	public RegisterTCP read(Input input) {
		return new RegisterTCP(input.readInt());
	}
}
