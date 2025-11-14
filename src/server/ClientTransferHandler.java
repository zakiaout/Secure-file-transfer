package server;

import crypto.AESUtil;
import crypto.HashUtil;

import java.io.*;
import java.net.Socket;

public class ClientTransferHandler extends Thread {

    private final Socket clientSocket;
    private final byte[] aesKey;

    public ClientTransferHandler(Socket socket, byte[] aesKey) {
        this.clientSocket = socket;
        this.aesKey = aesKey;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            // PHASE 1 — AUTH
            String login = in.readUTF();
            String password = in.readUTF();

            if (!UserDB.authenticate(login, password)) {
                out.writeUTF(SessionProtocol.AUTH_FAIL);
                clientSocket.close();
                return;
            }

            out.writeUTF(SessionProtocol.AUTH_OK);

            // PHASE 2 — RÉCEPTION MÉTA
            String fileName = in.readUTF();
            long fileSize = in.readLong();
            String originalHash = in.readUTF();

            out.writeUTF(SessionProtocol.READY_FOR_TRANSFER);

            // PHASE 3 — TRANSFERT
            byte[] encrypted = in.readNBytes((int) fileSize);

            byte[] decrypted = AESUtil.decrypt(encrypted, aesKey);

            // Sauvegarde du fichier
            FileOutputStream fos = new FileOutputStream("received_" + fileName);
            fos.write(decrypted);
            fos.close();

            // Vérification du hash
            String serverHash = HashUtil.sha256(decrypted);

            if (serverHash.equals(originalHash))
                out.writeUTF(SessionProtocol.TRANSFER_SUCCESS);
            else
                out.writeUTF(SessionProtocol.TRANSFER_FAIL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
