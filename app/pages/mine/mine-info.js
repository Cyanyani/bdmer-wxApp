const WXAPI = require('../../wxapi/main');
const Util = require("../../utils/util.js");
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        bdmerInfo: {},
        showTelNumber: "未绑定",
        showLocaleName: "请选择位置"
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
        let userInfo = wx.getStorageSync("userInfo");
        let bdmerInfo = wx.getStorageSync("bdmerInfo");
        
        // 获取显示电话和地址
        let showTelNumber = Util.formatTelNumber(bdmerInfo.telNumber);
        let showLocaleName = "请选择地址";
        if (!Util.isNull(bdmerInfo.localeName)){
            showLocaleName = bdmerInfo.localeName.split(";")[0];
            if (showLocaleName == ""){
                showLocaleName = bdmerInfo.localeName.split(";")[1];
            }
        }

        this.setData({
            userInfo: userInfo,
            bdmerInfo: bdmerInfo,
            showTelNumber: showTelNumber,
            showLocaleName: showLocaleName
        });
    },

    // 用户自定义函数
    /**
     * 更新位置信息
     */
    updateLocale:function(){
        let that = this;
        wx.chooseLocation({
            success: function (res) {
                let bdmerInfo = that.data.bdmerInfo;
                bdmerInfo.localeName = res.name;
                that.setData({
                    bdmerInfo: bdmerInfo
                });
                wx.setStorageSync("bdmerInfo", that.data.bdmerInfo);
                // 构建请求localeDTO
                WXAPI.updateUserLocale(Util.formatParamDTO(res)).then(
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
                        }else{
                            $Message({
                                content: "修改成功",
                                type: 'success',
                                duration: 3
                            });
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
            },
            fail: function (err) {
                console.log(err);
                $Message({
                    content: "未授权",
                    type: 'warning',
                    duration: 3
                });
            },
            complete: function () {

            }
        });
    }
})