package maniac.lee.test;

import org.junit.Test;

/**
 * Created by peng on 16/3/16.
 */
public class Trycatch {

    static int test() {
        int x = 1;
        try {
            x++;
            return x;
        } finally {
            ++x;
        }
    }

    static int plus() {
        int x = 1;
        x++;
        return x ;
    }
    static int pluss() {
        int x = 1;
        return x++ ;
    }

    @Test
    public void tests() {
        int x = 1;
        System.out.println(x++);
        System.out.println(plus());
        System.out.println(pluss());
        System.out.println(test());
    }
}
