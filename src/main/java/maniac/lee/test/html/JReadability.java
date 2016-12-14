package maniac.lee.test.html;

import com.wuman.jreadability.Readability;
import org.junit.Test;

/**
 * Created by lipeng on 2016/12/14.
 */
public class JReadability {


    @Test
    public void test(){
//        String url = "https://www.oschina.net/p/html2article";
        String url = "http://blog.csdn.net/seatomorrow/article/details/48393547";
        Readability readability = new Readability(url);  // URL
        readability.init();
        String cleanHtml = readability.outerHtml();
        System.out.println(cleanHtml);
    }
}
