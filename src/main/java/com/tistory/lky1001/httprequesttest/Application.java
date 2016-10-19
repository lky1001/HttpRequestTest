package com.tistory.lky1001.httprequesttest;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

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

        int oneInt = -1;
        byte old = (byte) -1;
        StringBuilder sb = new StringBuilder();
        int lineNumber = 0;
        boolean bodyFlag = false;
        String method = null;
        String requestUrl = null;
        String httpVersion = null;
        int contentLength = -1;
        int bodyRead = 0;
        List<Byte> bodyByteList = null;
        Map<String, String> headerMap = new HashMap<>();

        while(-1 != (oneInt = in.read())) {
            byte data = (byte) oneInt;

            // 메시지 바디일 경우
            if (bodyFlag) {
                bodyRead++;
                bodyByteList.add(data);
                // 읽은 바디 메시지의 길이가 헤더의 content-length만큼이면 종료
                if (bodyRead >= contentLength) {
                    break;
                }
            } else {
                // 행 바꿈 문자일 경우
                if (data == Application.LF && old == Application.CR) {
                    String line = sb.substring(0, sb.length() - 1);
                    lineNumber++;

                    if (lineNumber == 1) {
                        // 요청의 첫 행일 경우, HTTP 메서드, 요청 URL, 버전을 알아낸다.
                        // GET / HTTP/1.1
                        int firstBlank = line.indexOf(" ");
                        int secondBlank = line.lastIndexOf(" ");
                        method = line.substring(0, firstBlank);
                        requestUrl = line.substring(firstBlank + 1, secondBlank);
                        httpVersion = line.substring(secondBlank + 1);
                    } else {
                        if (line.length() <= 0) {
                            // 헤더의 끝을 만남
                            bodyFlag = true;
                            if ("GET".equals(method)) {
                                // GET 방식이면 메시지 바디가 없음
                                break;
                            }

                            String contentLengthValue = headerMap.get("Content-Length");

                            // advanced rest client로 보내면 소문자로 들어와서 예외처리
                            if (contentLengthValue == null) {
                                contentLengthValue = headerMap.get("content-length");
                            }

                            if (contentLengthValue != null) {
                                contentLength = Integer.parseInt(contentLengthValue.trim());
                                bodyFlag = true;
                                bodyByteList = new ArrayList<>();
                            }
                            continue;
                        }
                        int indexOfColon = line.indexOf(":");
                        String headerName = line.substring(0, indexOfColon);
                        String headerValue = line.substring(indexOfColon + 1);
                        headerMap.put(headerName, headerValue);
                    }
                    sb.setLength(0);
                } else {
                    sb.append((char) data);
                }
            }

            old = (byte) oneInt;
        }

        in.close();
        socket.close();

        System.out.printf("METHOD: %s REQ: %s HTTP VER. %s\n", method, requestUrl, httpVersion);
        System.out.println("Header list");

        Set<String> keyset = headerMap.keySet();
        Iterator<String> keyIter = keyset.iterator();

        // 헤더 출력
        while (keyIter.hasNext()) {
            String headerName = keyIter.next();
            System.out.printf("  Key: %s Value: %s\n", headerName, headerMap.get(headerName));
        }

        // 바디 출력
        if (bodyByteList != null) {
            System.out.print("Message Body-->");
            for(byte oneByte : bodyByteList) {
                System.out.print(oneByte);
            }
            System.out.println("<--");
        }

        System.out.println("End of HTTP Message.");
    }
}
