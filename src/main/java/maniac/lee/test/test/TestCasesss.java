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

        System.out.println(date.getTime() - start.getMillis());
    }

    @Test
    public void time() {
        Date date = new Date(1429159885l * 1000);
        System.out.println(date);
        System.out.println(new Date().getTime());
    }

    @Test
    public void mapping() {
        mappingEach(150301);
        mappingEach(160101);
        mappingEach(160111);
        mappingEach(160211);
        mappingEach(160301);
        mappingEach(161231);
        mappingEach(170101);
        for (int i = 0; i < 36; i++) {
            System.out.println(i + " " + i % 3 + " " + i / 3 % 12);
        }
    }

    @Test
    public void map() {
        for (int i = 1; i <= 12; i++) {
            for (int j = 0; j <= 3; j++) {
                int date = 16 * 10000 + i * 100 + (j + 1) * 10;
                System.out.println(date + "\t" + TimeMappingHash.mapTable(date) + "\t\t" + TimeMappingHash.mapDb(date));
            }
        }
    }

    public void mappingEach(long date) {
        //        System.out.println(TimeMapping.mapTable(date) + "\t\t" + TimeMapping.mapDb(date));
        //        System.out.println(TimeMapping.mapTableMod(date) + "\t\t" + TimeMapping.mapDb(date));
        System.out.println(TimeMappingHash.mapTable(date) + "\t\t" + TimeMappingHash.mapDb(date));

    }


    /***
     * 10天一张表
     * 一年36表
     */
    static class TimeMapping {
        /***
         * 库：0-9
         * 表：0-17 （每个库里18张表）
         * 20160302
         * 2016前大概7000万，
         */
        public static long mapTable(long date) {
            return date / 10000 < 16 ? 0 : ((date / 10000 - 16) * 36 + (date / 100 % 100 - 1) * 3 + date % 100 / 11 + 1/**历史数据的起始index*/);
        }

        public static long mapTableMod(long date) {
            return date / 10000 < 16 ? 0 : ((date / 100 % 100 - 1) * 3 + date % 100 / 11);
        }

        public static long mapDb(long date) {
            return date / 10000 < 16 ? 0 : ((date / 10000 - 16) * 2 + date / 100 % 100 / 7 + 1);
        }


    }

    static class TimeMappingHash {
        /***
         * 库：0:历史数据 ， 1-3一年的数据
         * 表：12 （每个库里12张表）
         * 20160302
         */

        public static long mapTable(long date) {
            return (date / 100 % 100 * 3 + date % 100 / 11) / 3 % 12;
        }

        public static long mapDb(long date) {
            return (date / 10000 - 15) * 6 /**一年6个库*/
                    + date / 10000 / 6 * 3 /**半年3个库*/
                    + date % 100 / 11 /**每十天在一个库里*/
                    ;
        }


    }

}
