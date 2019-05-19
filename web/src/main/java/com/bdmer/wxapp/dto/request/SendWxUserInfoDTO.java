package com.bdmer.wxapp.dto.request;

/**
 * 微信用户信息加密DTO - 入参类
 *
 * @since 2019-05-03
 * @author gongdl
 */
public class SendWxUserInfoDTO {
    private String rawData;

    private String signature;

    private String iv;

    private String encryptedData;

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
