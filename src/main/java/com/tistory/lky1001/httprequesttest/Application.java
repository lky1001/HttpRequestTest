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

    private static final byte CR = '\r';
    private static final byte LF = '\n';
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
        byte old = (byte) -1;

        StringBuilder sb = new StringBuilder();
        int lineNumber = 0;

        while(-1 != (oneInt = in.read())) {
            byte data = (byte) oneInt;
            // CRLF 찾기, 이전 CRLF다음부터 여기까지가 1행이 된다.
            if (data == Application.LF && old == Application.CR) {
                String line = sb.substring(0, sb.length() - 1);
                lineNumber++;
                System.out.printf("%d: %s\n", lineNumber, line);

                // 행의 내용이 없음. 즉 헤더의 끝
                if (line.length() <= 0) {
                    System.out.println("헤더 끝");
                    break;
                }
                sb.setLength(0);
            } else {
                sb.append((char)data);
            }
            old = (byte) oneInt;
        }

        out.close();
        in.close();
        socket.close();
    }
}
