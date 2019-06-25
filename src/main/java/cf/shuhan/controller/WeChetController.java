package cf.shuhan.controller;

import cf.shuhan.domain.Chengyu;
import cf.shuhan.domain.UserInfo;
import cf.shuhan.domain.WeatherInfo;
import cf.shuhan.mapper.ChengyuMapper;
import cf.shuhan.utils.MyBatisUtil;
import cf.shuhan.utils.WeatherUtil;
import cn.zhengzhanpeng.itchat4j.api.WechatTools;
import cn.zhengzhanpeng.itchat4j.beans.BaseMsg;
import cn.zhengzhanpeng.itchat4j.face.IMsgHandlerFace;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChetController implements IMsgHandlerFace {
    boolean gameStatus = false;
    String text = null;
    Integer count = 5;
    public String textMsgHandle(BaseMsg baseMsg) {
        //指定群
        List<JSONObject> groupList = WechatTools.getGroupList();
        List<JSONObject> contactList = WechatTools.getContactList();
        List<UserInfo> groups = JSONArray.parseArray(JSON.toJSONString(groupList), UserInfo.class);
        List<UserInfo> userInfos = JSONArray.parseArray(JSON.toJSONString(contactList), UserInfo.class);
        Map<String,String> userMap = new HashMap<>();
        groups.forEach(e->{
            userMap.put(e.getUserName(),e.getNickName());
        });
        userInfos.forEach(e->{
            userMap.put(e.getUserName(),e.getNickName());
        });
        if (userMap.containsKey(baseMsg.getFromUserName())&&"蓬中高中同学群".equals(userMap.get(baseMsg.getFromUserName()))){
            //算时间
            if (baseMsg.getText().startsWith("日期")){
                String date = baseMsg.getText().replaceAll("日期", "").replaceAll("：",":");
                String shijian = date.substring(date.indexOf(":") + 1);
                String wenben = date.substring(0,date.indexOf(":"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dstr = shijian.replaceAll("年","-").replaceAll("月","-").replaceAll("日","");
                try {
                    Date riqi = sdf.parse(dstr);
                    int tian = (int) ((System.currentTimeMillis() - riqi.getTime()) / 1000 / 60 / 60 / 24);
                    return "恭喜你"+wenben+"已经"+tian+"天了";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //天气预报
            if (baseMsg.getText().endsWith("天气")){
                if (baseMsg.getText().equals("天气")){
                    //默认为成都天气
                    WeatherInfo weather = WeatherUtil.GetWeather(WeatherUtil.GetWeatherData("成都"));
                    return weather.getCityname()+":"+weather.getDate()+","+weather.getWeather()+","+weather.getTemperature()+","
                            +weather.getFengli();
                }
                //天气
                WeatherInfo weather = WeatherUtil.GetWeather(WeatherUtil.GetWeatherData(baseMsg.getText().substring(0,baseMsg.getText().length()-2)));
                return weather.getCityname()+":"+weather.getDate()+","+weather.getWeather()+","+weather.getTemperature()+","
                        +weather.getFengli();
            }
            SqlSession sqlession = null;
            try {
                sqlession = MyBatisUtil.getSqlession();
                ChengyuMapper chengyuMapper = sqlession.getMapper(ChengyuMapper.class);
                //成语接龙
                if (!gameStatus&&baseMsg.getText().equals("成语接龙")){
                    gameStatus = true;
                    Chengyu chengyu = chengyuMapper.getOneByrand();
                    text = chengyu.getWord();
                    if (chengyuMapper.getChengyuByName(text.substring(3,4))==null){
                        gameStatus = false;
                        text = null;
                        count = 5;
                        return "没有可接成语，请重新输入 成语接龙开始游戏";
                    }
                    return "友情提醒：要求四字成语，可以多音字。成果完成五次游戏结束。成语接龙开始：【" + chengyu.getWord()+"】:"+chengyu.getDerivation();
                }
                if (gameStatus) {
                    //判断次数还有没有
                    if (count<=1){
                        gameStatus = false;
                        text = null;
                        count = 5;
                        return "游戏结束，再次回复 成语接龙即可再次游戏";
                    }
                    //判断是不是成语
                    Chengyu oneByWord = chengyuMapper.getOneByWord(baseMsg.getText());
                    if (oneByWord == null) {
                        return "渣渣，你输入的不是成语，重新输入：【"+text+"】";
                    }
                    //判断输入对不对
                    if(text.endsWith(baseMsg.getText().substring(0,1))){
                        text = baseMsg.getText();
                        count--;
                        if (chengyuMapper.getChengyuByName(text.substring(3,4))==null){
                            gameStatus = false;
                            text = null;
                            count = 5;
                            return "没有可接成语，请重新输入 成语接龙开始游戏";
                        }
                        return "牛皮，本局还剩"+count+"次，继续来：【"+text+"】:"+oneByWord.getDerivation();
                    }
                    return "饭可以乱吃，成语不可乱说。继续：【"+text+"】";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqlession.close();
            }
        }
        return null;
    }

    public String picMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    public String voiceMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    public String viedoMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    public String nameCardMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    public void sysMsgHandle(BaseMsg baseMsg) {

    }

    public String verifyAddFriendMsgHandle(BaseMsg baseMsg) {
        return null;
    }

    public String mediaMsgHandle(BaseMsg baseMsg) {
        return null;
    }
}
