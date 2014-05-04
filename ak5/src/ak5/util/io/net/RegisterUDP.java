/**
 * 
 */
package ak5.util.io.net;

import java.net.InetSocketAddress;

import ak5.util.io.ByteBuf;

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
	public void write(ByteBuf output, RegisterUDP object) {
		output.writeString(udpRemoteAddress.getHostString());
		output.putInt(udpRemoteAddress.getPort());
	}

	@Override
	public RegisterUDP read(ByteBuf input) {
		return new RegisterUDP(InetSocketAddress.createUnresolved(input.readString(), input.getInt()));
	}
}
