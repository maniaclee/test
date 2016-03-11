package maniac.lee.test.rxjava;

import com.google.common.collect.Lists;
import org.junit.Test;
import rx.Observable;

/**
 * Created by lipeng on 16/3/11.
 */
public class RxJavaTest {

    @Test
    public void simple() {
        Observable.just("Hello, world!")
                .subscribe(s -> System.out.println(s));
        hello("fuck", "you");
    }

    public static void hello(String... names) {
        Observable.from(names).subscribe(s -> {
            System.out.println("Hello " + s + "!");
        });
        Observable.just("start").doOnNext(s -> System.out.println("next->" + s))
                .doOnNext(s1 -> System.out.println("next next->" + s1))
                .map(s3 -> Lists.newArrayList(s3))
                .subscribe(s2 -> System.out.println("end->" + s2))
        ;
    }
}
