package cf.shuhan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getDate(String str){
        String date = str.replaceAll("日期", "").replaceAll("：",":");
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
        return null;
    }
}
