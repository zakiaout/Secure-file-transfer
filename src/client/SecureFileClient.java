package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class SecureFileClient {

    private static final byte[] AES_KEY = "1234567890123456".getBytes();

    public static void main(String[] args) throws Exception {

        String host = "127.0.0.1";
        int port = 5000;
        String login = "user1";
        String password = "pass1";
        String filePath = "test.txt";

        Socket socket = new Socket(host, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // PHASE 1 — AUTH
        out.writeUTF(login);
        out.writeUTF(password);

        String authResp = in.readUTF();
        if (!authResp.equals("AUTH_OK")) {
            System.out.println("Authentification échouée.");
            return;
        }

        // Lecture fichier
        File file = new File(filePath);
        byte[] data = FilePreProcessor.readFile(filePath);
        String hash = FilePreProcessor.hashFile(data);
        byte[] encrypted = FilePreProcessor.encryptFile(data, AES_KEY);

        // PHASE 2 — MÉTA
        out.writeUTF(file.getName());
        out.writeLong(encrypted.length);
        out.writeUTF(hash);

        String ready = in.readUTF();
        if (!ready.equals("READY_FOR_TRANSFER")) {
            System.out.println("Erreur négociation.");
            return;
        }

        // PHASE 3 — TRANSFERT
        out.write(encrypted);

        String result = in.readUTF();
        System.out.println("Serveur : " + result);

        socket.close();
    }
}

