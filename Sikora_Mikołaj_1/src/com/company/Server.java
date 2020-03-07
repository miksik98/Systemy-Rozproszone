package com.company;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    public static int maxNumberOfClients = 10;
    public static List<ChatTcpClient> tcpClients = new ArrayList<>();
    public static List<Pair<String,Pair<InetAddress,Integer>>> udpClients = new ArrayList<>();
    public static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxNumberOfClients);

    public static void main(String[] args) throws IOException {
        System.out.println("CHAT SERVER");
        int portNumber = 12345;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            //UDP connections handling thread
            Thread udpThread = new Thread(udpConnectionThread(portNumber));
            udpThread.start();

            //TCP connections handling thread
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ChatTcpClient newClient = new ChatTcpClient(clientSocket, new PrintWriter(clientSocket.getOutputStream(), true),
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
                tcpClients.add(newClient);
                executor.execute(newClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for(ChatTcpClient chatTcpClient : tcpClients){
                chatTcpClient.getClientSocket().close();
            }
            executor.shutdown();
        }
    }

    //UDP connections with clients handler
    private static Runnable udpConnectionThread(int portNumber) {
        return ()->{
            try (DatagramSocket udpSocket = new DatagramSocket(portNumber)) {
                byte[] receiveBuffer = new byte[1024];

                while (true) {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    udpSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    //CONFIGURATION when first char is 0
                    if (msg.startsWith("0")) {
                        msg = msg.trim();
                        msg = msg.substring(1);
                        udpClients.add(new Pair<>(msg, new Pair<>(receivePacket.getAddress(), receivePacket.getPort())));
                        System.out.println("Client with nick \"" + msg + "\" connected with UDP");
                    }
                    //CONFIGURATION
                    else{
                    //SENDING MSG TO OTHERS when first char is 1
                        if (msg.startsWith("1")) {
                            msg = msg.substring(1);
                            Pair<String, Pair<InetAddress, Integer>> sendingClient = null;

                            //FIND SENDING CLIENT
                            for (Pair<String, Pair<InetAddress, Integer>> client : udpClients) {
                                if (client.getValue().getKey().equals(receivePacket.getAddress()) && client.getValue().getValue() == receivePacket.getPort()) {
                                    sendingClient = client;
                                }
                            }

                            //SEND TO EVERYONE EXCEPT SENDING_CLIENT
                            if (sendingClient != null) {
                                System.out.println(sendingClient.getKey() + ": " + msg);
                                byte[] data = (sendingClient.getKey() + ": " + msg).getBytes();
                                DatagramPacket sendPacket;
                                for (Pair<String, Pair<InetAddress, Integer>> cli : udpClients) {
                                    if (!cli.equals(sendingClient)) {
                                        sendPacket = new DatagramPacket(data, data.length, cli.getValue().getKey(), cli.getValue().getValue());
                                        udpSocket.send(sendPacket);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
