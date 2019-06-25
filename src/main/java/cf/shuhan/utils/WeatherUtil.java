package cf.shuhan.utils;

import cf.shuhan.domain.WeatherInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class WeatherUtil {
    /**
     * 通过城市名称获取该城市的天气信息
     *
     * @param cityName
     * @return
     */

    public  static String GetWeatherData(String cityName) {
        StringBuilder sb=new StringBuilder();;
        try {
            //cityname = URLEncoder.encode(cityName, "UTF-8");
            String weather_url = "http://wthrcdn.etouch.cn/weather_mini?city="+cityName;


            URL url = new URL(weather_url);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8"); // 设置读取流的编码格式，自定义编码
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while((line=reader.readLine())!=null)
                sb.append(line+" ");
            reader.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(sb.toString());
        return sb.toString();

    }


    /**
     * 将JSON格式数据进行解析 ，返回一个weather对象
     * @param weatherInfobyJson
     * @return
     */
    public static WeatherInfo GetWeather(String weatherInfobyJson){
        JSONObject dataOfJson = JSONObject.parseObject(weatherInfobyJson);
//        if(dataOfJson.getInt("status")!=1000)
//            return null;

        //创建WeatherInfo对象，提取所需的天气信息
        WeatherInfo weatherInfo = new WeatherInfo();

        //从json数据中提取数据
        String data = dataOfJson.getString("data");

        dataOfJson = JSONObject.parseObject(data);
        weatherInfo.setCityname(dataOfJson.getString("city"));;
        weatherInfo.setAirquality(dataOfJson.getString("aqi"));

        //获取预测的天气预报信息
        JSONArray forecast = dataOfJson.getJSONArray("forecast");
        //取得当天的
        JSONObject result=forecast.getJSONObject(0);

        weatherInfo.setDate(result.getString("date"));

        String high = result.getString("high").substring(2);
        String low  = result.getString("low").substring(2);

        weatherInfo.setTemperature(low+"~"+high);

        //风力
        String fengji = result.getString("fengli").substring(9, result.getString("fengli").length() - 3);
        String fengxiang = result.getString("fengxiang");
        weatherInfo.setFengli(fengji+fengxiang);
        weatherInfo.setWeather(result.getString("type"));



        return weatherInfo;
    }
}
