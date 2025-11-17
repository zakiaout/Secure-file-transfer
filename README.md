#  Secure File Transfer System in Java (TCP + AES + SHA-256)

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

#  How it Works 

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

### Shared Secret Key Exemple

``` java
private static final byte[] AES_KEY = "1234567890123456".getBytes();
```

------------------------------------------------------------------------

# 🌐 Network Configuration

### The server listens on:

    PORT = 5000;

### By default the client connects to:

``` java
String host = "127.0.0.1";  // localhost
```

------------------------------------------------------------------------

# 📦 Test File

The folder:

    test_files/test.txt

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

# 🏁 Conclusion

This project demonstrates:

✔ TCP networking\
✔ AES encryption\
✔ SHA-256 integrity checking\
✔ A complete 3-phase protocol\
✔ Multithreaded server handling multiple clients
