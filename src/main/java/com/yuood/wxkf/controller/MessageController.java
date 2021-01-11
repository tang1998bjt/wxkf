package com.yuood.wxkf.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import com.yuood.wxkf.entities.Message;
import com.yuood.wxkf.entities.SessionMessage;
import com.yuood.wxkf.util.WeixinUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

@RestController
public class MessageController {
    private final String TOKEN = "YUOOD_KF_TOKEN";

    @RequestMapping("/kf")
    public String receive(HttpServletRequest request, SessionMessage sessionMessage) throws IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            String signature = sessionMessage.getSignature();
            String nonce = sessionMessage.getNonce();
            String echostr = sessionMessage.getEchostr();
            String timestamp = sessionMessage.getTimestamp();
            String[] tmpArr = {TOKEN, timestamp, nonce};
            Arrays.sort(tmpArr);
            StringBuilder sb = new StringBuilder();
            for (String s : tmpArr) {
                sb.append(s);
            }
            return SecureUtil.sha1(sb.toString()).equals(signature) ? echostr : "";
        } else {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder requestStr = new StringBuilder();
            String line;
            while ((line = streamReader.readLine()) != null) {
                requestStr.append(line);
            }
            JSONObject requestObject = new JSONObject(requestStr);
            String msgType = (String) requestObject.get("MsgType");
            String fromUserName = (String) requestObject.get("FromUserName");
            if (msgType != null) {
                Message message = new Message();
                message.setText(new HashMap<>());
                switch (msgType) {
                    case "event":
                        message.setTouser(fromUserName);
                        message.setMsgtype("text");
                        message.getText().put("content", "您好，欢迎来到企优定，用心陪伴企业成长，助力实体复兴。\n" +
                                "了解合伙人政策回复“1”\n" +
                                "获取招商资料回复“2”\n" +
                                "了解微商城功能回复“3” \n" +
                                "电话：13711293949（微信同号）");
                        WeixinUtil.sendKfMessage(message);
                        break;
                    case "text":
                        String content = (String) requestObject.get("Content");
                        String reply;
                        switch (("" + content).trim()) {
                            case "1":
                                reply = "";
                                break;
                            case "2":
                                reply = "";
                                break;
                            case "3":
                                reply = "";
                                break;
                            default:
                                reply = "";
                                break;
                        }
                        message.setTouser(fromUserName);
                        message.setMsgtype("text");
                        message.getText().put("content", reply);
                        WeixinUtil.sendKfMessage(message);
                        break;
                    default:
                        break;
                }
            }
            return "";
        }
    }
}
