# HttpRequestTest

### http 리퀘스트 데이터 확인하기 ###

1. CRLF를 이용해 헤더의 행 구분하기
2. 헤더 값 읽기
3. GET 요청이 아니면 메시지 바디 읽기
4. GET 요청이면 url에 파라미터 읽기
5. POST 요청이면 body에 파라미터 읽기
4. 헤더 출력
5. 파라미터 출력

### 사용법 ###
- 자바 프로그램 실행
- GET 요청 시 브라우저에서 http://localhost:8000?aaa=8&bbb=9 요청
- 자바 콘솔 확인
- curl로 POST 요청 시 : curl -XPOST 'http://localhost:8000' -d 'aaa=8&bbb=9'
- curl로 GET 요청 시 : curl -XPOST 'http://localhost:8000?aaa=8&bbb=9'

```
/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:56467,suspend=y,server=n -javaagent:/Users/lee/Library/Caches/IntelliJIdea15/groovyHotSwap/gragent.jar -Dfile.encoding=UTF-8 -classpath "/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/lib/tools.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/htmlconverter.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Users/lee/Documents/workspace/HttpRequestTest/build/classes/main:/Users/lee/Documents/workspace/HttpRequestTest/build/resources/main:/Applications/IntelliJ IDEA 15.app/Contents/lib/idea_rt.jar" com.tistory.lky1001.httprequesttest.Application
objc[5874]: Class JavaLaunchHelper is implemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/bin/java and /Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/jre/lib/libinstrument.dylib. One of the two will be used. Which one is undefined.
Connected to the target VM, address: '127.0.0.1:56467', transport: 'socket'
METHOD : POST REQ : / HTTP VER. HTTP/1.1
Header list
 Key : Accept Value :  */*
 Key : User-Agent Value :  curl/7.43.0
 Key : Host Value :  localhost:8000
 Key : Content-Length Value :  11
 Key : Content-Type Value :  application/x-www-form-urlencoded
paramName: aaa paramValue: 8
paramName: bbb paramValue: 9
End of HTTP Message.
Disconnected from the target VM, address: '127.0.0.1:56467', transport: 'socket'

Process finished with exit code 0

```
