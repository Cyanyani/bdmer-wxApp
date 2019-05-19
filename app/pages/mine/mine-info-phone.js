const WXAPI = require('../../wxapi/main');
const Util = require('../../utils/util.js');
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        canIUse: wx.canIUse('button.open-type.getPhoneNumber'),
        telNumber:""
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            telNumber: options.telNumber
        })
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    // 用户自定义函数
    bindPhoneNumber:function(e){
        let that = this;
        if (!e.detail.iv || !e.detail.encryptedData){
            $Message({
                content: '无法授权，请重试',
                type: 'warning',
                duration: 3
            });
            return;
        }
        let data = {};
        data.iv = e.detail.iv;
        data.encryptedData = e.detail.encryptedData;
        //WXAPI解密+更新telNumber
        WXAPI.updateUserTelNumber(Util.formatParamDTO(data)).then(
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
                    that.setData({
                        telNumber: res.data
                    });
                    let bdmerInfo = wx.getStorageSync("bdmerInfo");
                    if (!Util.isNull(bdmerInfo)){
                        bdmerInfo.telNumber = res.data;
                        wx.setStorageSync("bdmerInfo", bdmerInfo);
                    }

                    $Message({
                        content: "绑定成功",
                        type: 'success',
                        duration: 3
                    });

                    // 关闭当前页面，返回上一层
                    setTimeout(function () {
                        wx.navigateBack();
                    }, 2000);
                   
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
        );
    }
 
})