package com.bdmer.wxapp.dto.request;

/**
 * 微信用户加密手机信息 - DTO类
 *
 * @since 2019-05-05
 * @author gongdl
 */
public class AESUserTelNumberDTO {

    private String iv;

    private String encryptedData;

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
