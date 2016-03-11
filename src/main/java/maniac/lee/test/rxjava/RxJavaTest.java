package maniac.lee.test.rxjava;

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
    }

}
