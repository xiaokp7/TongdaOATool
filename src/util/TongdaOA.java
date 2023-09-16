package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class TongdaOA {
    private String url; //通达OA地址
    private String path; //通达OA path
    private String version;//通达OA版本

    //设置url
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        //设置通达oa path
        path="/inc/expired.php";
        try {
            Document document = Jsoup.connect(url + path).ignoreHttpErrors(true).get();
             version = document.title();
             return version;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
