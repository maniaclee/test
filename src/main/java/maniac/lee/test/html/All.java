package maniac.lee.test.html;

import com.wuman.jreadability.Readability;
import de.jetwick.snacktory.HtmlFetcher;
import de.jetwick.snacktory.JResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

import static maniac.lee.test.html.HtmlContent.GetDocContent;

/**
 * Created by lipeng on 2016/12/14.
 */
public class All {

//    String url = "https://www.oschina.net/p/html2article";
        String url = "http://blog.csdn.net/seatomorrow/article/details/48393547";
//        String url = "https://github.com/srijiths/readabilityBUNDLE";


    @Test
    public void testf() throws Exception {
        printTitle("readability");
        Readability();
        printTitle("html");
        html();
        printTitle("snack");
        snacktory();
    }

    private void printTitle(String s) {
        System.out.println(String.format("-----------------------------> %s-----------------------", s));
    }

    public void Readability() throws IOException {
        Readability readability = new Readability(getDoc());  // URL
        readability.init();
        String cleanHtml = readability.outerHtml();
        System.out.println(cleanHtml);
    }

    private Document getDoc() throws IOException {
        return Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").get();
    }


    public void html() throws IOException {
        String content = GetDocContent(getDoc());
        System.out.println("网页正文如下：\n" + content);
    }

    public void snacktory() throws Exception {
        HtmlFetcher fetcher = new HtmlFetcher();
        // set cache. e.g. take the map implementation from google collections:
        // fetcher.setCache(new MapMaker().concurrencyLevel(20).maximumSize(count).
        //    expireAfterWrite(minutes, TimeUnit.MINUTES).makeMap();

        JResult res = fetcher.fetchAndExtract(url, 5000, true);

        String text = res.getText();
        String title = res.getTitle();
        String imageUrl = res.getImageUrl();
        System.out.println(text);
        System.out.println(title);
        System.out.println(imageUrl);
        System.out.println( res.getKeywords());
    }
}
