package com.yuood.wxkf.entities;

import lombok.Data;

import java.util.Map;

@Data
public class Message {
    private String touser;
    private String access_token;
    private Map<String, String> text;
    private String msgtype = "text";
}
