package com.bdmer.wxapp.model;

public class UserBdmerEntity {
    private Long uid;

    private String unionid;

    private String openidwxweb;

    private String openidwxapp;

    private Long point;

    private Long usedpoint;

    private Long rechargepoint;

    private Integer invitationcount;

    private Integer tempinvitationsuccess;

    private Integer tempinvitationfail;

    private Long fromuid;

    private Boolean subscribe;

    private Boolean isvip;

    private String createtime;

    private Long createstamp;

    private String telNumber;

    private String locale;

    private String localeName;

    private String authImage;

    private Integer authStatus;

    public String getAuthImage() {
        return authImage;
    }

    public void setAuthImage(String authImage) {
        this.authImage = authImage;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenidwxweb() {
        return openidwxweb;
    }

    public void setOpenidwxweb(String openidwxweb) {
        this.openidwxweb = openidwxweb;
    }

    public String getOpenidwxapp() {
        return openidwxapp;
    }

    public void setOpenidwxapp(String openidwxapp) {
        this.openidwxapp = openidwxapp;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getUsedpoint() {
        return usedpoint;
    }

    public void setUsedpoint(Long usedpoint) {
        this.usedpoint = usedpoint;
    }

    public Long getRechargepoint() {
        return rechargepoint;
    }

    public void setRechargepoint(Long rechargepoint) {
        this.rechargepoint = rechargepoint;
    }

    public Integer getInvitationcount() {
        return invitationcount;
    }

    public void setInvitationcount(Integer invitationcount) {
        this.invitationcount = invitationcount;
    }

    public Integer getTempinvitationsuccess() {
        return tempinvitationsuccess;
    }

    public void setTempinvitationsuccess(Integer tempinvitationsuccess) {
        this.tempinvitationsuccess = tempinvitationsuccess;
    }

    public Integer getTempinvitationfail() {
        return tempinvitationfail;
    }

    public void setTempinvitationfail(Integer tempinvitationfail) {
        this.tempinvitationfail = tempinvitationfail;
    }

    public Long getFromuid() {
        return fromuid;
    }

    public void setFromuid(Long fromuid) {
        this.fromuid = fromuid;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    public Boolean getIsvip() {
        return isvip;
    }

    public void setIsvip(Boolean isvip) {
        this.isvip = isvip;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Long getCreatestamp() {
        return createstamp;
    }

    public void setCreatestamp(Long createstamp) {
        this.createstamp = createstamp;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocaleName() {
        return localeName;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }
}