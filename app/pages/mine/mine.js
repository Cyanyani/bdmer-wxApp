const WXAPI = require('../../wxapi/main');
const Util = require('../../utils/util.js');
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo:{},
        bdmerInfo:{},
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        let that = this;
        let userInfo = wx.getStorageSync("userInfo");
        let bdmerInfo = {};

        //WXAPI获取bdmerInfo
        WXAPI.getUserBdmerInfo().then(
            function (res) {
                if (res.code != 0) {
                    $Message({
                        content: res.msg,
                        type: 'error',
                        duration: 3
                    });
                    if (!Util.isToken(res)) {
                        app.goLoginPageTimeOut();
                    }
                } else {
                    bdmerInfo = res.data;
                    wx.setStorageSync("bdmerInfo", res.data);
                }
            },
            function (err) {
                console.log(err);
                $Message({
                    content: '服务器开小差了!',
                    type: 'error',
                    duration: 3
                });
            }
        ).then(() => {
            that.setData({
                userInfo: userInfo,
                bdmerInfo: bdmerInfo
            });
        });
    },

    //-- 用户自定义函数 --//
    
    /**
     * 跳转用户信息
     */
    goMineInfo:function(){
        wx.navigateTo({
            url: "./mine-info"
        });
    },

    /**
     * 跳转点数记录
     */
    goRecord:function(){
        wx.navigateTo({
            url: "../other/record"
        }); 
    }

})