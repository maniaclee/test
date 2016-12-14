package maniac.lee.test.html;

import de.jetwick.snacktory.HtmlFetcher;
import de.jetwick.snacktory.JResult;
import org.junit.Test;

/**
 * Created by lipeng on 2016/12/14.
 */
public class Snacktory {

    @Test
    public void sfdf() throws Exception {
        HtmlFetcher fetcher = new HtmlFetcher();
        // set cache. e.g. take the map implementation from google collections:
        // fetcher.setCache(new MapMaker().concurrencyLevel(20).maximumSize(count).
        //    expireAfterWrite(minutes, TimeUnit.MINUTES).makeMap();

        JResult res = fetcher.fetchAndExtract("http://blog.csdn.net/seatomorrow/article/details/48393547", 5000, true);
        String text = res.getText();
        String title = res.getTitle();
        String imageUrl = res.getImageUrl();
        System.out.println(text);
        System.out.println(title);
        System.out.println(imageUrl);
    }
}
