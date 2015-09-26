package ru.ifmo.ctddev.isaev.networking.broadcaster;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author Ilya Isaev
 */
public class MadBroadcaster extends Broadcaster {
    private static final Random RANDOM = new Random(System.nanoTime());

    @Override
    public byte[] getBroadcastPacket() throws SocketException, UnknownHostException {
        int packetSize = RANDOM.nextInt(1024);
        byte[] bytes = new byte[packetSize];
        RANDOM.nextBytes(bytes);
        return bytes;
    }
}
