/**
 * 
 */
package org.gamelib.util.io.net;

import java.net.InetSocketAddress;

import org.gamelib.util.io.Buf;

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
	public void write(Buf output, RegisterUDP object) {
		output.writeString(udpRemoteAddress.getHostString());
		output.putInt(udpRemoteAddress.getPort());
	}

	@Override
	public RegisterUDP read(Buf input) {
		return new RegisterUDP(InetSocketAddress.createUnresolved(input.readString(), input.getInt()));
	}
}
