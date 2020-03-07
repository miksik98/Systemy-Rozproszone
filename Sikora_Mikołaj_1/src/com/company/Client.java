package com.company;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

//TODO closing sockets and transferring ascii art

public class Client {

    public static String asciiDirPath = "C:\\Users\\lenovo\\Desktop\\INFORMATYKA\\Systemy rozproszone\\Sikora_Mikołaj_1\\Ascii Arts\\";

    public static void main(String[] args) throws IOException {
        System.out.println("CHAT CLIENT");

        //PREPARE SOCKETS
        int portNumber = 12345;
        String hostName = "localhost";
        Socket tcpSocket = new Socket(hostName, portNumber);

        int multicastPort = 44444;
        InetAddress groupAddress = InetAddress.getByName("228.5.6.7");
        MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
        multicastSocket.joinGroup(groupAddress);

        DatagramSocket udpSocket = new DatagramSocket();

        //Entering nick and connecting with server by sending it
        System.out.println("Please, insert your cool nickname:");
        Scanner input = new Scanner(System.in);
        String nick = input.nextLine();
        final PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
        out.println(nick);
        final InetAddress address = InetAddress.getByName("localhost");
        byte[] sendBuffer1 = ("0"+nick).getBytes();
        DatagramPacket sendPacket1 = new DatagramPacket(sendBuffer1, sendBuffer1.length, address, portNumber);
        udpSocket.send(sendPacket1);

        //PREPARE THREADS
        Thread tcpReader = new Thread(tcpReaderThread(tcpSocket));
        Thread udpReader = new Thread(udpReaderThread(udpSocket));
        AtomicBoolean myMessage = new AtomicBoolean(false); //if (myMessage) then not print message on my console
        Thread multicastReader = new Thread(multicastReaderThread(groupAddress, multicastSocket, myMessage));
        Thread writer = new Thread(writerThread(groupAddress, portNumber, multicastPort, multicastSocket, udpSocket, nick, out, address, myMessage));

        //START THREADS
        writer.start();
        tcpReader.start();
        udpReader.start();
        multicastReader.start();
    }

    //Reading from tcp connection with server
    private static Runnable tcpReaderThread(Socket socket) {
        return () -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true) {
                        String response = in.readLine();
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        };
    }

    //Reading from udp with server
    private static Runnable udpReaderThread(DatagramSocket udpSocket) {
        return () -> {
            try (udpSocket) {
                byte[] receiveBuffer = new byte[1024];
                while (true) {
                    Arrays.fill(receiveBuffer, (byte) 0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    udpSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    System.out.println(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    //Reading from multicast udp with other clients
    private static Runnable multicastReaderThread(InetAddress groupAddress, MulticastSocket multicastSocket, AtomicBoolean myMessage) {
        return () -> {
            try{
                byte[] receiveBuffer = new byte[1024];

                while(true) {
                    Arrays.fill(receiveBuffer, (byte)0);
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    multicastSocket.receive(receivePacket);
                    if(!myMessage.get()) {
                        String msg = new String(receivePacket.getData());
                        System.out.println(msg);
                    }
                    else myMessage.set(false);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
                try {
                    multicastSocket.leaveGroup(groupAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    //Writing all types of messages
    private static Runnable writerThread(InetAddress groupAddress, int portNumber, int multicastPort, MulticastSocket multicastSocket, DatagramSocket udpSocket, String nick, PrintWriter out, InetAddress address, AtomicBoolean myMessage) {
        return () -> {
            try {
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    String scanned = scanner.nextLine();
                    if(scanned.startsWith("-U")){
                        if(scanned.startsWith("-UF")){
                            String fileName = scanned.substring(4).trim();
                            BufferedReader br = new BufferedReader(new FileReader(new File(asciiDirPath+fileName)));
                            String it;
                            StringBuilder sendString = new StringBuilder();
                            while((it = br.readLine())!=null){
                                sendString.append(it).append("\n");
                                for(int i = 0; i < nick.length()+2; i++){
                                    sendString.append(" ");
                                }
                            }
                            byte[] sendBuffer = ("1"+sendString.toString()).getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                            udpSocket.send(sendPacket);
                        }
                        else {
                            byte[] sendBuffer = ("1" + scanned.substring(3)).getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                            udpSocket.send(sendPacket);
                        }
                    }
                    else {
                        if(scanned.startsWith("-M")){
                            if(scanned.startsWith("-MF")){
                                String fileName = scanned.substring(4).trim();
                                BufferedReader br = new BufferedReader(new FileReader(new File(asciiDirPath+fileName)));
                                String it;
                                StringBuilder sendString = new StringBuilder();
                                while((it = br.readLine())!=null){
                                    sendString.append(it).append("\n");
                                    for(int i = 0; i < nick.length()+2; i++){
                                        sendString.append(" ");
                                    }
                                }
                                byte[] sendBuffer = (nick+": "+sendString.toString()).getBytes();
                                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, groupAddress, multicastPort);
                                myMessage.set(true);
                                multicastSocket.send(sendPacket);
                            }
                            else {
                                byte[] sendBuffer = (nick +": "+scanned.substring(3)).getBytes();
                                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, groupAddress, multicastPort);
                                myMessage.set(true);
                                multicastSocket.send(sendPacket);
                            }
                        }
                        else {
                            out.println(scanned);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
