package maniac.lee.test._knowlege.treemap;

import org.junit.Test;

import java.util.TreeMap;

/**
 * Created by lipeng on 16/6/23.
 */
public class TreeMapMethod {

    @Test
    /***
     * 可以用来实现一致性hash是环形查找
     */
    public void circle() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "1");
        m.put(1000, "1000");
        m.put(2, "2");
        m.put(49, "49");
        System.out.println(m.ceilingEntry(3));//大于等于3的KV
        System.out.println(m.ceilingKey(3));//大于等于3的key
        System.out.println(m.tailMap(3));//大于等于3的kVs
    }
}
