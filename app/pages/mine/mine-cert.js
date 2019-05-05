const CONFIG = require("../../wxapi/config.js");
const WXAPI = require('../../wxapi/main');
const Util = require("../../utils/util.js");
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        header:{},
        baseUrl:"",
        authInfo: {},
        progress1: 0,
        progress2: 0
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
       
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        let authInfo = {};
        let header =  {};
        let bdmerInfo = wx.getStorageSync("bdmerInfo");
        header.cookie = "token=" + wx.getStorageSync("token");
        authInfo.authImage = bdmerInfo.authImage;
        authInfo.authStatus = bdmerInfo.authStatus;
        
        this.setData({
            header: header,
            baseUrl: CONFIG.baseUrl,
            authInfo: authInfo
        });
    },

    // 身份证信息上传
    onChange1(e) {
        console.log('onChange', e)
        const { file } = e.detail
        if (file.status === 'uploading') {
            this.setData({
                progress1: 0,
            })
            wx.showLoading();
        } else if (file.status === 'done') {
            this.setData({
                imageUrl: file.url,
            })
        }
    },
    onSuccess1(e) {
        console.log('onSuccess', e);
    },
    onFail1(e) {
        console.log('onFail', e);
    },
    onComplete1(e) {
        console.log('onComplete', e);
        wx.hideLoading();
        let res = JSON.parse(e.detail.data);
        if (res.code !== 0){
            $Message({
                content: res.msg,
                type: 'error',
                duration: 3
            });
            if (!Util.isToken(res)){
                app.goLoginPageTimeOut();
            }
        }else{
            let authInfo = res.data;
            this.setData({
                authInfo: authInfo
            });

            $Message({
                content: "上传身份信息成功",
                type: 'success',
                duration: 3
            });
        }
    },
    onProgress1(e) {
        console.log('onProgress', e)
        this.setData({
            progress1: e.detail.file.progress,
        })
    }
})