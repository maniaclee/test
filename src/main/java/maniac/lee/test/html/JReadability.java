package maniac.lee.test.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by lipeng on 2016/12/14.
 */
public class JReadability {


    @Test
    public void test() throws IOException {
//        String url = "https://www.oschina.net/p/html2article";
        String url = "http://blog.csdn.net/seatomorrow/article/details/48393547";
        Readability readability = new Readability(getDoc(url));  // URL
        readability.init();
        String cleanHtml = readability.outerHtml();
        System.out.println(cleanHtml);
    }
    private Document getDoc(String url) throws IOException {
        return Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").get();
    }
}
