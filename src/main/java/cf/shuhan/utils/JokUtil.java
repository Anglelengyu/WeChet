package cf.shuhan.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JokUtil {
    private static String url = "https://www.mxnzp.com/api/jokes/list/random";
    public static String getJok(){
        String s = HttpClientUtil.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String data = jsonObject.getString("data");
        JSONArray objects = JSONArray.parseArray(data);
        String string = objects.getString(0);
        JSONObject jsonObject1 = JSONObject.parseObject(string);
        return jsonObject1.getString("content");
    }
}
