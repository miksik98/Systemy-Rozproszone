package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ChatTcpClient implements Runnable {

    private Socket tcpSocket;
    private String nick;
    private PrintWriter out;
    private BufferedReader in;

    public ChatTcpClient(Socket clientSocket, PrintWriter out, BufferedReader in){
        this.tcpSocket = clientSocket;
        this.out = out;
        this.in = in;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatTcpClient that = (ChatTcpClient) o;
        return tcpSocket.equals(that.tcpSocket) &&
                Objects.equals(nick, that.nick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tcpSocket, nick);
    }

    public Socket getClientSocket() {
        return tcpSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Nick is null when ChatTcpClient object was just created - set nick and inform of ready connection
                if(nick == null) {
                    nick = in.readLine();
                    System.out.println("Client with nick \""+nick.trim()+"\" connected with TCP");
                }
                //Otherwise send msg to everyone except sender
                else{
                    String msg = in.readLine();
                    System.out.println(nick + ": " + msg);
                    for(ChatTcpClient chatTcpClient : Server.tcpClients) {
                        if(!chatTcpClient.equals(this)) {
                            chatTcpClient.out.println(nick + ": " + msg);
                        }
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            Server.tcpClients.remove(this);
        }
    }
}
