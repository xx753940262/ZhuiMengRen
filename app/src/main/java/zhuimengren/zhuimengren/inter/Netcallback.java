package zhuimengren.zhuimengren.inter;

/**
 * Created by __追梦人 on 2015/3/11.
 */
public interface Netcallback {
    //res为请求网络成功后的Json串，返回的请求网络
    void preccess(Object res , boolean flag);
}
