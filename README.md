# 🔐 Secure File Transfer System in Java (TCP + AES + SHA-256)

This project implements a **secure file transfer system** over TCP
using:

-   AES symmetric encryption
-   SHA-256 hashing for integrity verification
-   A custom 3-phase communication protocol
-   A multithreaded server (one thread per client)
-   Java Sockets

The goal is to create a complete client--server architecture where a
client can authenticate, encrypt a file, send it to the server, and the
server decrypts and verifies integrity.

Designed for academic evaluation.

------------------------------------------------------------------------

# 📁 Project Architecture

    secure-file-transfer/
    │
    ├── src/
    │   └── main/
    │       └── java/
    │           ├── crypto/
    │           │   ├── AESUtil.java               # AES encryption/decryption
    │           │   └── HashUtil.java              # SHA-256 hashing
    │           │
    │           ├── server/
    │           │   ├── SecureFileServer.java      # Main server entry point
    │           │   ├── ClientTransferHandler.java # Worker thread for one client
    │           │   ├── SessionProtocol.java       # Protocol message constants
    │           │   └── UserDB.java                # Simple user database
    │           │
    │           └── client/
    │               ├── SecureFileClient.java      # Main client entry point
    │               └── FilePreProcessor.java      # Reads, hashes, encrypts the file
    │
    ├── test_files/
    │   └── test.txt                                # Sample file sent by client
    │
    ├── .gitignore
    └── README.md

------------------------------------------------------------------------

# 🚀 How it Works (Quick Overview)

### 🔸 Technologies Used

  Feature        Technology
  -------------- ----------------------
  Transport      TCP Sockets
  Encryption     AES/ECB/PKCS5Padding
  Integrity      SHA-256 hash
  Architecture   Multithreaded Server
  Language       Java 17+ / 21

------------------------------------------------------------------------

# 🔗 Communication Protocol (3 Phases)

The client and server communicate with a custom protocol divided into
**three phases**:

------------------------------------------------------------------------

## 🧩 Phase 1 --- Authentication

### Client → Server

1.  login\
2.  password

### Server → Client

-   `AUTH_OK`\
-   or `AUTH_FAIL` (connection closed)

User credentials are stored in `server/UserDB.java`:

``` java
USERS.put("user1", "pass1");
USERS.put("admin", "1234");
```

------------------------------------------------------------------------

## 🧾 Phase 2 --- Metadata Negotiation

If authentication succeeds:

### Client → Server

1.  File name\
2.  Encrypted file size (long)\
3.  Original SHA-256 hash (hex string)

### Server → Client

-   `READY_FOR_TRANSFER`

------------------------------------------------------------------------

## 🔐 Phase 3 --- Encrypted File Transfer

### Client → Server

-   Raw bytes of encrypted file (AES)

### Server Actions:

1.  Decrypt using the shared AES key\
2.  Save the file as `received_<filename>`\
3.  Recompute SHA-256\
4.  Compare with client hash

### Server → Client

-   `TRANSFER_SUCCESS`\
-   or `TRANSFER_FAIL`

------------------------------------------------------------------------

# 🔐 Cryptography Details

### AES Configuration

``` java
private static final String AES_MODE = "AES/ECB/PKCS5Padding";
```

### Shared Secret Key (Demo Purpose Only)

``` java
private static final byte[] AES_KEY = "1234567890123456".getBytes();
```

⚠️ Note:\
Hard-coded keys are used ONLY for educational purposes.

------------------------------------------------------------------------

# 🌐 Network Configuration

### The server listens on:

    PORT = 5000;

### By default the client connects to:

``` java
String host = "127.0.0.1";  // localhost
```

To test on two different computers:

1.  On the server PC, run:

        ipconfig

2.  Locate your IPv4 address (example):

        192.168.1.45

3.  In `SecureFileClient.java`, replace:

    ``` java
    String host = "127.0.0.1";
    ```

    with:

    ``` java
    String host = "192.168.1.45";
    ```

⚠️ Both computers must be on the same Wi-Fi / LAN network.

------------------------------------------------------------------------

# ▶️ How to Run the Project

## 🖥️ Option A: Using IntelliJ / Eclipse / NetBeans

1.  Clone this repository

2.  Open the project as a Java project

3.  Create a directory:

        test_files/test.txt

    (already included)

4.  Run the **server**:

    -   Class: `server.SecureFileServer`

    -   Expected output:

            Serveur lancé sur le port 5000

5.  Run the **client**:

    -   Class: `client.SecureFileClient`
    -   Expected client output: `Serveur : TRANSFER_SUCCESS`

6.  In the server folder a new file appears:

        received_test.txt

------------------------------------------------------------------------

## 🧪 Option B: Run from Terminal

From project root:

### Compile

``` bash
javac -d out src/main/java/crypto/*.java src/main/java/server/*.java src/main/java/client/*.java
```

### Run server (terminal 1)

``` bash
cd out
java server.SecureFileServer
```

### Run client (terminal 2)

``` bash
cd out
java client.SecureFileClient
```

------------------------------------------------------------------------

# 📦 Test File

The folder:

    test_files/test.txt

is included so your teacher can run the client instantly.

------------------------------------------------------------------------

# 🧹 .gitignore

    # IntelliJ
    .idea/
    *.iml
    out/

    # Eclipse
    .project
    .classpath
    .settings/

    # Build
    target/
    build/

    # OS
    .DS_Store
    Thumbs.db

------------------------------------------------------------------------

# 📚 Possible Improvements (optional for extra points)

-   Replace AES/ECB with AES/CBC or AES/GCM\
-   Add Diffie-Hellman secure key exchange\
-   Add GUI (JavaFX or Swing)\
-   Display transfer progress bar\
-   Add RSA signatures for authenticity\
-   Support multiple file transfers at once

------------------------------------------------------------------------

# 🏁 Conclusion

This project demonstrates:

✔ TCP networking\
✔ AES encryption\
✔ SHA-256 integrity checking\
✔ A complete 3-phase protocol\
✔ Multithreaded server handling multiple clients

Everything needed for academic evaluation is included.
