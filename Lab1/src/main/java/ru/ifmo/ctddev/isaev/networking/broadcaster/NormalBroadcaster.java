package ru.ifmo.ctddev.isaev.networking.broadcaster;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import static ru.ifmo.ctddev.isaev.networking.Main.HOSTNAME;
import static ru.ifmo.ctddev.isaev.networking.Main.PACKET_LENGTH;

/**
 * @author Ilya Isaev
 */
public class NormalBroadcaster extends Broadcaster {
    @Override
    public byte[] getBroadcastPacket() throws SocketException, UnknownHostException {
        NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        byte[] mac = network.getHardwareAddress();
        if (mac == null) {
            throw new UnknownHostException("Cannot find network interface");
        }
        assert mac.length == 6;
        byte[] host = HOSTNAME.getBytes(StandardCharsets.UTF_8);
        ByteBuffer header = ByteBuffer.allocate(7 + host.length);
        header.order(ByteOrder.BIG_ENDIAN);
        header.put(mac);
        header.put((byte) host.length);
        header.put(host);
        ByteBuffer toSend = ByteBuffer.allocate(PACKET_LENGTH);
        toSend.order(ByteOrder.BIG_ENDIAN);
        toSend.put(header.array());
        long timestamp = System.currentTimeMillis() / 1000;
        //toSend.putInt((int) (timestamp << 32 >> 32));
        //toSend.putInt((int) (timestamp >> 32));
        toSend.putInt((int) timestamp);
        return toSend.array();
    }
}