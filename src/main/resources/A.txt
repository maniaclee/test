package maniac.lee.test;

import java.io.Serializable;

/**
 * Created by peng on 16/2/22.
 */
public class A  implements Serializable{

    private String s ;
    private int a;

    public A(String s, int a) {
        this.s = s;
        this.a = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "A{" +
                "s='" + s + '\'' +
                ", a=" + a +
                '}';
    }
}
