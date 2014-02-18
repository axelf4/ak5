/**
 * 
 */
package org.gamelib.util.io.net;

import org.gamelib.util.io.Buf;

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
	public void write(Buf output, RegisterTCP object) {
		output.putInt(connectionId);
	}

	@Override
	public RegisterTCP read(Buf input) {
		return new RegisterTCP(input.getInt());
	}
}
