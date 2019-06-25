package cf.shuhan.domain;

import lombok.Data;

@Data
public class WeatherInfo {
    private String date;//时间
    private String cityname;//城市名
    private String weather;//天气
    private String temperature;//气温
    private String airquality;//pm2.5
    private String fengli;//风力
}
