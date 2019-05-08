const WXAPI = require('../../wxapi/main');
const Util = require('../../utils/util.js');
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({
    /**
     * 页面初始数据
     */
    data: {
        isRequest:false,
        banner: [
            "https://bdmer.cn/nginx/image/banner1.jpg",
            "https://bdmer.cn/nginx/image/banner2.jpg",
            "https://bdmer.cn/nginx/image/banner3.jpg"],
        tasks: [],
        showLoadOk:false,
        showSpin:false,
    },

    getTaskData(index, size, type) {
        if (this.data.isRequest) {
            return;
        }

        let that = this;
        this.setData({
            isRequest: true,
            showSpin: true,
            showLoadOk: false
        });

        //WXAPI-请求
        let data = {};
        data.index = index;
        data.size = size;
        data.type = type;
        let locale = wx.getStorageSync("locale");
        data.lat = locale.lat;
        data.lng = locale.lng;
        WXAPI.getTaskList(Util.formatParamDTO(data)).then(
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
                    // doSomething
                    let tasks = that.data.tasks;
                    res.data.map((o) => { tasks.push(o); });
                    that.setData({
                        tasks: tasks
                    });
                }
            },
            function (err) {
                $Message({
                    content: '服务器开小差了!',
                    type: 'error',
                    duration: 3
                });
            }
        ).then(function () {
            that.setData({
                isRequest: false,
                showSpin: false,
                showLoadOk: true
            });
        });
    },

    onLoad:function(){

    },

    onShow:function(){
        // 清除原来的数据
        let tasks = this.data.tasks;
        tasks = [];
        this.setData({
            tasks: tasks
        });

        // 给当前页面获取数据
        let index = 0;
        let size = 10;
        this.getTaskData(index, size, "");
    },

    /**
    * 打开任务详情页
    */
    goTaskDetail: function (e) {
        //跳转
        wx.navigateTo({
            url: '../task/task-detail?taskId=' + e.currentTarget.dataset.taskId
        })
    },

    /**
     * 上拉加载news数据
     */
    onReachBottom:function(e){
        // 给当前界面获取数据
        let size = 10;
        this.getTaskData(this.data.tasks.length, size, "");
    },

    goPublishTask() {
        //跳转
        wx.navigateTo({
            url: '../task/task-publish'
        })
    },

    goCurrentTask() {
        //跳转
        wx.navigateTo({
            url: '../task/task-mine?myStatus=CURRENT'
        })
    },

    goHistoryTask() {
        //跳转
        wx.navigateTo({
            url: '../task/task-mine?myStatus=HISTORY'
        })
    }
})