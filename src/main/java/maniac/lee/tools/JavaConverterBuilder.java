package maniac.lee.tools;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/7/14.
 */
public class JavaConverterBuilder {

    public static String convert(String fromClassContent) {
        return extractFields(fromClassContent).stream().map(s -> String.format("re.%s(from.%s())", setter(s), getter(s))).collect(Collectors.joining(";\n"));
    }

    public static String getter(String a) {
        return camelMerge("get", a);
    }

    public static String setter(String a) {
        return camelMerge("set", a);
    }

    public static String camelMerge(String a, String b) {

        return a + StringUtils.capitalize(b);
    }

    public static List<String> extractFields(String s) {
        List<String> re = Lists.newArrayList();
        for (Matcher matcher = Pattern.compile("private\\s+\\S+\\s+(\\S+)\\s*;").matcher(s); matcher.find(); ) {
            re.add(matcher.group(1).trim());
        }
        return re;
    }

    @Test
    public void tst() throws Exception {
        String s = IOUtils.toString(new FileInputStream("/Users/psyco/workspace/dp/ssp-es-search-service/ssp-es-search-api/src/main/java/com/dianping/ssp/es/search/api/dto/EsArticleRequest.java"));
        //        System.out.println(extractFields(s));
        System.out.println(convert(s));
    }

}
