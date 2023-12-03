package org.example;

public class Server {

    public static void main(String[] args) {
        TeamMessageReceiver messageReceiver = new TeamMessageReceiver();
        messageReceiver.startListening();
    }
}
