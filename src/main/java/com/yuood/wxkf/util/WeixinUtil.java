package com.yuood.wxkf.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yuood.wxkf.entities.Message;

public class WeixinUtil {

    public final static String kf_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    public final static String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd23b2eade3bdff69&secret=5b0ef4795f80f40a930b28fb8032205b";

    public static String getToken() {
        String result = HttpUtil.get(url);
        JSONObject jsonObject = new JSONObject(result);
        return String.valueOf(jsonObject.get("access_token"));
    }

    public static String sendKfMessage(Message message) {
        String content = JSONUtil.toJsonStr(message);
        String token = getToken();
        String post = HttpUtil.post(kf_url + "?access_token=" + token, content);
        return post;
    }
}
