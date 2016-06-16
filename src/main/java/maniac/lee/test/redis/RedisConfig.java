package maniac.lee.test.redis;

/**
 * Created by peng on 16/6/14.
 */
public class RedisConfig {
    public static int maxactive = 10;
    public static int maxidle = 1000;
    public static long maxwait = 1000;
    public static int timeout = 1000;
    public static int retryNum = 3;


    public static int port =6379;
}
