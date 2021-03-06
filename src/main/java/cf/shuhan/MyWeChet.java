package cf.shuhan;

import cf.shuhan.controller.WeChetController;
import cn.zhengzhanpeng.itchat4j.Wechat;
import cn.zhengzhanpeng.itchat4j.face.IMsgHandlerFace;

public class MyWeChet {
    public static void main(String[] args) {
        String qrPath = "D://WeChetLogin"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
        IMsgHandlerFace msgHandler = new WeChetController(); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(msgHandler, qrPath); // 【注入】
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
    }
}
