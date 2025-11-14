package server;

import java.net.ServerSocket;
import java.net.Socket;

public class SecureFileServer {

    private static final int PORT = 5000;

    // Clé AES PARTAGÉE (exemple, normalement via échange sécurisé)
    private static final byte[] AES_KEY = "1234567890123456".getBytes();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Serveur lancé sur le port " + PORT);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("Nouveau client connecté.");

            new ClientTransferHandler(client, AES_KEY).start();
        }
    }
}
