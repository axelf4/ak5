/**
 * 
 */
package org.gamelib.util.net;

import java.net.InetSocketAddress;

/**
 * @author Axel
 */
public class RegisterUDP implements FrameworkMessage<RegisterUDP> {
	public InetSocketAddress udpRemoteAddress;

	public RegisterUDP(InetSocketAddress udpRemote) {
		this.udpRemoteAddress = udpRemote;
	}

	public RegisterUDP() {}

	@Override
	public void write(Output output, RegisterUDP object) {
		output.writeString(udpRemoteAddress.getHostString());
		output.writeInt(udpRemoteAddress.getPort());
	}

	@Override
	public RegisterUDP read(Input input) {
		return new RegisterUDP(InetSocketAddress.createUnresolved(input.readString(), input.readInt()));
	}
}
