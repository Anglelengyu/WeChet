package cf.shuhan;

import cf.shuhan.utils.WeatherUtil;
import lombok.Data;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class WeatherTest {
    @Test
    public void test(){
        StringBuilder sb = new StringBuilder();
        try {
            //cityname = URLEncoder.encode(cityName, "UTF-8");
            String weather_url = "http://wthrcdn.etouch.cn/weather_mini?city="+"成都";


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
        System.out.println(sb.toString());
    }
    @Test
    public void test1(){
        String data = WeatherUtil.GetWeatherData("成都 ");
//        System.out.println(data);
        System.out.println(WeatherUtil.GetWeather(data));
    }
}
