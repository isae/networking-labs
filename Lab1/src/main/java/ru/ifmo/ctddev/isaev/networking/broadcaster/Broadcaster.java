package ru.ifmo.ctddev.isaev.networking.broadcaster;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static ru.ifmo.ctddev.isaev.networking.Main.PORT;
import static ru.ifmo.ctddev.isaev.networking.Main.SLEEP_TIME;

/**
 * @author Ilya Isaev
 */
public abstract class Broadcaster implements Runnable {

    public static List<InetAddress> getBroadcastAddress() throws SocketException {
        Enumeration<NetworkInterface> intefaces = NetworkInterface.getNetworkInterfaces();
        List<InetAddress> adresses = new ArrayList<>();
        while (intefaces.hasMoreElements()) {
            NetworkInterface network = intefaces.nextElement();
            for (InterfaceAddress interfaceAddress :
                    network.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast != null) {
                    adresses.add(broadcast);
                }
            }
        }
        return adresses;
    }


    public abstract byte[] getBroadcastPacket() throws SocketException, UnknownHostException;

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            while (true) {
                try {
                    byte[] bytes = getBroadcastPacket();
                    for (InetAddress broadcastAddress : getBroadcastAddress()) {
                        DatagramPacket packet = new DatagramPacket(bytes,
                                bytes.length, broadcastAddress, PORT);
                        socket.send(packet);
                    }
                    Thread.sleep(SLEEP_TIME);
                } catch (IOException | RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException | SocketException e) {
            e.printStackTrace();
        }
    }


}
