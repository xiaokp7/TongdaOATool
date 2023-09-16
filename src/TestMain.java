import poc.*;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;

public class TestMain {
    public static void main(String[] args){
//        //设置系统代理
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                //创建代理服务器地址及端口
                InetSocketAddress proxyAddr = new InetSocketAddress("127.0.0.1",8080);
                //创建代理对象
                Proxy proxy = new Proxy(Proxy.Type.HTTP,proxyAddr);
                //返回一个包含代理服务器的列表
                return Collections.singletonList(proxy);
            }
            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                ioe.printStackTrace();
            }
        });
        TongdaOA_ApiAliUpload tongdaOAApiAliUpload = new TongdaOA_ApiAliUpload();

        tongdaOAApiAliUpload.setUrl("http://192.168.31.164");
        System.out.println( tongdaOAApiAliUpload.EXP());
    }
}
