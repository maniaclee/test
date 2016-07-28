package maniac.lee.test.note;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by peng on 16/7/14.
 */
public class Regx {

    String pStr = "0x(?<bytes>\\p{XDigit}{1,4})\\s++u\\+(?<char>\\p{XDigit}{4})(?:\\s++)?";


    @Test
    public void test() {
        CharSequence charSequence = "0x1234 u+5678";
        Matcher m = Pattern.compile(pStr).matcher(charSequence);
        if (m.matches()) {
            int bs = Integer.valueOf(m.group("bytes"), 16);
            int c = Integer.valueOf(m.group("char"), 16);
            System.out.printf("[%x] -> [%04x]", bs, c);
        }
    }

    @Test
    public void sdfsf() {
//        System.out.println("0x1234 u+5678".replaceFirst(pStr, "u+$<char> 0x$<bytes>"));
        System.out.println("0x1234 u+5678".replaceAll(pStr, "shit${char}nidaye"));
    }
}
