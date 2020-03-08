package neo.util;

import com.google.gson.Gson;
import neo.domain.IsbnData;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IsbnUtil {
    private static final String SECREY_ID = "AKIDli78csd3lm0y965zn6d4m9mqjxbeoqhay2n0";
    private static final String SECREY_KEY = "2g0lC7gziXjbRpnjgnm8kZr51t307x55zg1itja0";

    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = new BASE64Encoder().encode(hash);

        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }

    public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }

    public static IsbnData fetch(String isbn) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        //云市场分配的密钥Id
        String secretId = SECREY_ID;
        //云市场分配的密钥Key
        String secretKey = SECREY_KEY;
        String source = "market";

        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String datetime = sdf.format(cd.getTime());
        // 签名
        String auth = calcAuthorization(source, secretId, secretKey, datetime);
        // 请求方法
        String method = "GET";
        // 请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Source", source);
        headers.put("X-Date", datetime);
        headers.put("Authorization", auth);

        // 查询参数
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("isbn", isbn);
        // body参数
        Map<String, String> bodyParams = new HashMap<String, String>();

        // url参数拼接
        String url = "https://service-995c4k11-1257101137.ap-shanghai.apigateway.myqcloud.com/release/isbn/query";
        if (!queryParams.isEmpty()) {
            url += "?" + urlencode(queryParams);
        }

//        return new Gson().fromJson("{\"status\":0,\"msg\":\"ok\",\"result\":{\"title\":\"有理想就有疼痛\",\"subtitle\":\"中国当代文化名人访谈录\",\"pic\":\"http:\\/\\/api.jisuapi.com\\/isbn\\/upload\\/1\\/1.jpg\",\"author\":\"高晓春\",\"summary\":\"《有理想就有疼痛:中国当代文化名人访谈录》是一份关于当代中国文化的最真实底稿，收录了高晓春与白先勇、冯骥才、余华、莫言、余秋雨、陈忠实等20位当代中国文化大家的对话。通过近距离的访谈，展现了这些文化大家多彩的内心世界，也阐释了他们对生命、艺术、财富及文化的理解。\",\"publisher\":\"\",\"pubplace\":\"合肥\",\"pubdate\":\"2013-1\",\"page\":256,\"price\":\"29.00\",\"binding\":\"\",\"isbn\":\"9787212058937\",\"isbn10\":\"7212058939\",\"keyword\":\"名人－访问记－中国－现代\",\"edition\":\"1版\",\"impression\":\"1\",\"language\":\"\",\"format\":\"23×18\",\"class\":\"K820.7\",\"sellerlist\":[{\"seller\":\"当当\",\"price\":\"20.80\",\"link\":\"http:\\/\\/product.dangdang.com\\/22921241.html\"},{\"seller\":\"京东\",\"price\":\"99.00\",\"link\":\"http:\\/\\/item.jd.com\\/25471096577.html\"},{\"seller\":\"douban\",\"price\":null},{\"seller\":\"bookschina\",\"price\":\"21.20\"}]}}", IsbnData.class);

        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod(method);

            // request headers
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // request body
            Map<String, Boolean> methods = new HashMap<>();
            methods.put("POST", true);
            methods.put("PUT", true);
            methods.put("PATCH", true);
            Boolean hasBody = methods.get(method);
            if (hasBody != null) {
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(urlencode(bodyParams));
                out.flush();
                out.close();
            }

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String result = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }

            return new Gson().fromJson(result, IsbnData.class);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return null;
    }

//    {
//        "status":0,
//            "msg":"ok",
//            "result":{
//        "title":"有理想就有疼痛",
//                "subtitle":"中国当代文化名人访谈录",
//                "pic":"http:\/\/api.jisuapi.com\/isbn\/upload\/1\/1.jpg",
//                "author":"高晓春",
//                "summary":"《有理想就有疼痛:中国当代文化名人访谈录》是一份关于当代中国文化的最真实底稿，收录了高晓春与白先勇、冯骥才、余华、莫言、余秋雨、陈忠实等20位当代中国文化大家的对话。通过近距离的访谈，展现了这些文化大家多彩的内心世界，也阐释了他们对生命、艺术、财富及文化的理解。",
//                "publisher":"",
//                "pubplace":"合肥",
//                "pubdate":"2013-1",
//                "page":256,
//                "price":"29.00",
//                "binding":"",
//                "isbn":"9787212058937",
//                "isbn10":"7212058939",
//                "keyword":"名人－访问记－中国－现代",
//                "edition":"1版",
//                "impression":"1",
//                "language":"",
//                "format":"23×18",
//                "class":"K820.7",
//                "sellerlist":[
//        {
//            "seller":"当当",
//                "price":"20.80",
//                "link":"http:\/\/product.dangdang.com\/22921241.html"
//        },
//        {
//            "seller":"京东",
//                "price":"99.00",
//                "link":"http:\/\/item.jd.com\/25471096577.html"
//        },
//        {
//            "seller":"douban",
//                "price":null
//        },
//        {
//            "seller":"bookschina",
//                "price":"21.20"
//        }
//      ]
//    }
//    }
}
