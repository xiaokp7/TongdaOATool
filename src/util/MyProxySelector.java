package util;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.List;

public class MyProxySelector extends ProxySelector {
    private String proxyHost;
    private String proxyPort;

    private String proxyType;

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public MyProxySelector(String proxyHost, String proxyPort, String proxyType) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyType = proxyType;
    }

    @Override
    public List<Proxy> select(URI uri) {
        //创建代理服务器地址对象
        InetSocketAddress proxyAddr = new InetSocketAddress(this.proxyHost, Integer.parseInt(this.proxyPort));
        //创建代理对象代理对象
        Proxy proxy = null;
        if (proxyType.equals("HTTP")){
             proxy = new Proxy(Proxy.Type.HTTP,proxyAddr);
        } else if (proxyType.equals("SOCKS")) {
            proxy = new Proxy(Proxy.Type.SOCKS,proxyAddr);
        }
        //返回包含一个代理服务器的列表
        return Collections.singletonList(proxy);
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        System.err.println("Failed to connect proxy"+sa);
        ioe.printStackTrace();
    }
}
