package com.tistory.lky1001.httprequesttest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
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

        while (-1 != (oneInt = in.read())) {
            byte data = (byte)oneInt;

            if (bodyFlag) {
                bodyRead++;
                bodyByteList.add(data);
                if (bodyRead >= contentLength) {
                    break;
                }
            } else {
                if (data == Application.LF && old == Application.CR) {
                    String oneLine = sb.substring(0, sb.length() - 1);
                    lineNumber++;
                    if (lineNumber == 1) {
                        int firstBlank = oneLine.indexOf(" ");
                        int secondBlank = oneLine.lastIndexOf(" ");
                        method = oneLine.substring(0, firstBlank);
                        requestUrl = oneLine.substring(firstBlank + 1, secondBlank);
                        httpVersion = oneLine.substring(secondBlank + 1);
                    } else {
                        if (oneLine.length() <= 0) {
                            bodyFlag = true;
                            // 헤더 끝, GET 요청이면 바디가 없음
                            if ("GET".equals(method)) {
                                break;
                            }
                            String contentLengthValue = headerMap.get("Content-Length");
                            if (contentLengthValue != null) {
                                contentLength = Integer.parseInt(contentLengthValue.trim());
                                bodyFlag = true;
                                bodyByteList = new ArrayList<>();
                            }
                            continue;
                        }
                        int indexOfColon = oneLine.indexOf(":");
                        String headerName = oneLine.substring(0, indexOfColon);
                        String headerValue = oneLine.substring(indexOfColon + 1);
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

        System.out.printf("METHOD : %s REQ : %s HTTP VER. %s\n", method, requestUrl, httpVersion);
        Map<String, String> paramMap = new HashMap<>();
        // url에 쿼리스트링이 있는지 검사
        int indexOfQuotation = requestUrl.indexOf("?");
        if (indexOfQuotation > 0) {
            // '?' 다음 문자열을 '&'로 자르기
            StringTokenizer st = new StringTokenizer(requestUrl.substring(indexOfQuotation + 1), "&");
            while (st.hasMoreTokens()) {
                String params = st.nextToken();
                paramMap.put(params.substring(0, params.indexOf("=")),
                    params.substring(params.indexOf("=") + 1));
            }
        }
        System.out.println("Header list");
        Set<String> keySet = headerMap.keySet();
        Iterator<String> keyIter = keySet.iterator();
        while (keyIter.hasNext()) {
            String headerName = keyIter.next();
            System.out.printf(" Key : %s Value : %s\n", headerName, headerMap.get(headerName));
        }

        if (bodyByteList != null) {
            if ("application/x-www-form-urlencoded".equals(headerMap.get("Content-Type").trim())) {
                int startIndex = 0;
                byte[] srcBytes = new byte[bodyByteList.size()];
                String currentName = null;

                for (int i = 0; i < bodyByteList.size(); i++) {
                    byte oneByte = bodyByteList.get(i);
                    srcBytes[i] = oneByte;
                    if ('=' == oneByte) {
                        byte[] one = new byte[i - startIndex];
                        System.arraycopy(srcBytes, startIndex, one, 0, i - startIndex);
                        currentName = URLDecoder.decode(new String(one), "CP949");
                        startIndex = i + 1;
                    } else if ('&' == oneByte) {
                        byte[] one = new byte[i - startIndex];
                        System.arraycopy(srcBytes, startIndex, one, 0, i - startIndex);
                        paramMap.put(currentName, URLDecoder.decode(new String(one), "CP949"));
                        startIndex = i + 1;
                    } else if (i == bodyByteList.size() - 1) {
                        byte[] one = new byte[i - startIndex + 1];
                        System.arraycopy(srcBytes, startIndex, one, 0, i - startIndex + 1);
                        paramMap.put(currentName, URLDecoder.decode(new String(one), "CP949"));
                        startIndex = i + 1;
                    }
                }
            } else {
                System.out.print("Message Body-->");
                for(byte oneByte : bodyByteList) {
                    System.out.print(oneByte);
                }
                System.out.print("<--");
            }
        }

        Set<String>paramKeySet = paramMap.keySet();
        Iterator<String> paramKeyIter = paramKeySet.iterator();

        while(paramKeyIter.hasNext()) {
            String paramName = paramKeyIter.next();
            System.out.printf("paramName: %s paramValue: %s\n", paramName, paramMap.get(paramName));
        }

        System.out.println("End of HTTP Message.");
    }
}
