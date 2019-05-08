const WXAPI = require('../../wxapi/main');
const Util = require('../../utils/util.js');
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        records:[{
                createTime:"2019-06-07 10:25:00",
                getPoint:-10,
                event:"拆红包"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 10,
                event:"充值"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 5,
                event:"外卖快递"
            },{
                createTime: "2019-06-07 10:25:00",
                getPoint: -10,
                event: "拆红包"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 10,
                event: "充值"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 5,
                event: "外卖快递"
            },{
                createTime: "2019-06-07 10:25:00",
                getPoint: -10,
                event: "拆红包"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 10,
                event: "充值"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 5,
                event: "外卖快递"
            },{
                createTime: "2019-06-07 10:25:00",
                getPoint: -10,
                event: "拆红包"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 10,
                event: "充值"
            },
            {
                createTime: "2019-06-07 10:25:00",
                getPoint: 5,
                event: "外卖快递"
            }],
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
        //WXAPI获取record数据
        WXAPI.getRecord().then(
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
                    let records = res.data;
                    that.setData({
                        records:records
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

    }
})