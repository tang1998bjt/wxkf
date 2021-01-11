package com.yuood.wxkf.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionMessage {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
