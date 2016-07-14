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
import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/7/12.
 */
public class ExtractDBDDL {


    @Test
    public void test() throws IOException {
        String ddl = "ddl.txt";
        //        extractFields(ddl);
//        System.out.println(getFieldNames(ddl).stream().collect(Collectors.joining(",\n")));
//        System.out.println(getJavaFieldNames(ddl).stream().map(s-> String.format("#entity.%s#" , s)).collect(Collectors.joining(",\n")));
        System.out.println(getFieldNamesAs(ddl).stream().collect(Collectors.joining(",\n")));

    }


    public List<String> getFieldNamesAs(String file) throws IOException {
        return getFieldNames(file).stream().map(s -> s + " as " + camelCase(s)).collect(Collectors.toList());
    }
    public List<String> getFieldNames(String file) throws IOException {
        return extractFieldLines(file).stream().map(s -> trim(s.split("\\s+")[0])).collect(Collectors.toList());
    }
    public List<String> getJavaFieldNames(String file) throws IOException {
        return extractFieldLines(file).stream().map(s -> camelCase(trim(s.split("\\s+")[0]))).collect(Collectors.toList());
    }

    public void extractFields(String file) throws IOException {
        for (String group : extractFieldLines(file)) {
            String[] split = group.split("\\s+");
            String field = trim(split[0]);
            field = camelCase(field);
            String type = convertType(split[1]);
            String comment = String.format("/**%s*/", trim(split[split.length - 1]));
            System.out.println(Joiner.on(" ").join(Lists.newArrayList(comment, "\n", "private", type, field, ";")));
        }
    }

    public String camelCase(String field) {
        field = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
        return field;
    }

    public List<String> extractFieldLines(String file) throws IOException {
        List<String> re = Lists.newLinkedList();
        for (Matcher matcher = Pattern.compile("\\s*([^\n]+)\\s*,").matcher(readString(file)); matcher.find(); )
            re.add(matcher.group(1));
        return re;
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
