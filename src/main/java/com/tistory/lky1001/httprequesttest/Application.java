package com.tistory.lky1001.httprequesttest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lee on 2016. 10. 12..
 */
public class Application {

    public static void main(String[] args) throws IOException {
        Application application = new Application();

        application.boot();
    }

    private ServerSocket serverSocket;

    private void boot() throws IOException {
        serverSocket = new ServerSocket(8000);
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        int oneInt = -1;

        while(-1 != (oneInt = in.read())) {
            System.out.print((char) oneInt);
        }

        out.close();
        in.close();
        socket.close();
    }
}
