package maniac.lee.test.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import okhttp3.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;

/**
 * Created by lipeng on 2018/5/22.
 */
public class HttpUtils {

    public static final MediaType type_JSON = MediaType.parse("application/json; charset=utf-8");
    static OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(type_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String postPayload(String url, Object json) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), JSON.toJSONString(json));
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(json));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String postPayloadHttp(String url, Object json) throws IOException {
        String content = JSON.toJSONString(json);

        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Accept","application/json, */*");
        post.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
        post.setRequestHeader("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        post.setRequestHeader("Accept-Encoding","gzip, deflate, br");
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
//        post.setRequestHeader("Connection","keep-alive");
        post.setRequestHeader("origin","https://xflush.alipay.com");
        post.setRequestHeader("Referer","https://xflush.alipay.com/optimus/");
        System.out.println("param: "+content);
        RequestEntity entity = new StringRequestEntity(content, "application/json", "UTF-8");
        post.setRequestEntity(entity);
        httpClient.executeMethod(post);
        String html = post.getResponseBodyAsString();
        return html;
    }

    public static void main(String[] args) throws IOException {
        /***
         * [{
         "zones": null,
         "condition": {
         "plugin": "AM",
         "end": 1526995920000,
         "start": 1526995920000,
         "tag": null,
         "contentType": "EA",
         "dsId": "error.app.zcbprod",
         "dims": [
         ["zcbprod"]
         ]
         },
         "tenant": null
         }]
         */
        JSONArray list = new JSONArray();
        list.add(new JSONObject()
                .fluentPut("conditions", new JSONObject()
                        .fluentPut("plugin", "AM")
                        .fluentPut("contentType", "EA")
                        .fluentPut("dsId", "error.app.zcbprod")
                        .fluentPut("dims", new JSONArray().fluentAdd(Lists.newArrayList("zcbprod")))
                        .fluentPut("start", null)
                        .fluentPut("end", null)
                ));

//        String s = postPayload("https://xflush.alipay.com/u_39/universalQuery", list);
//        System.out.println(s);

        System.out.println(postPayloadHttp("https://xflush.alipay.com/u_39/universalQuery", list));
    }
}
