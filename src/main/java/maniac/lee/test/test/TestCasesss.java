package maniac.lee.test.test;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

/**
 * Created by lipeng on 16/3/24.
 */
public class TestCasesss {

    @Test
    public void date() {
        Date date = new Date();
        System.out.println(date);
        DateTime start = new DateTime(2015, 05, 01, 0, 0, 0);

        System.out.println(date.getTime()-start.getMillis());
    }

}
