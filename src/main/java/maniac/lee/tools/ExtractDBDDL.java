package maniac.lee.tools;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lipeng on 16/7/12.
 */
public class ExtractDBDDL {


    @Test
    public void test() throws IOException {
        extractSql("ddl");
    }

    public void extractSql(String file) throws IOException {
        String s = readString(file);
        Pattern compile = Pattern.compile("\\s*([^\n]+)\\s*,");
        Matcher matcher = compile.matcher(s);
        while (matcher.find()) {
            String group = matcher.group(1);
            //            System.out.println("group" + group);
            String[] split = group.split("\\s+");
            String field = trim(split[0]);
            field = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
            String type = convertType(split[1]);
            String comment = String.format("/**%s*/", trim(split[split.length - 1]));
            System.out.println(Joiner.on(" ").join(Lists.newArrayList(comment, "\n", "private", type, field, ";")));
        }

    }

    private String trim(String s) {
        return s.replaceAll("`|'", "");
    }

    private String convertType(String s) {
        if (s.startsWith("big"))
            return "long";
        if (s.startsWith("var"))
            return "String";
        if (s.startsWith("int"))
            return "int";
        if (s.startsWith("date"))
            return "Date";
        if (s.startsWith("decimal"))
            return "BigDecimal";
        return s;
    }


    public static List<String> readStrings(String file) throws IOException {
        InputStream resourceAsStream = getInputStream(file);
        return IOUtils.readLines(resourceAsStream);
    }

    public static String readString(String file) throws IOException {
        InputStream resourceAsStream = getInputStream(file);
        return IOUtils.toString(resourceAsStream);
    }

    public static InputStream getInputStream(String file) {
        return ExtractDBDDL.class.getClassLoader().getResourceAsStream(file);
    }


}
