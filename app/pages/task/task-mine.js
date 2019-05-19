const WXAPI = require('../../wxapi/main');
const Util = require('../../utils/util.js');
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        myStatus:"CURRENT",
        isRequest:false,
        swiperHeight:0,
        baseItemHeightImg:128,
        key: 'PUBLISH',
        index: 0,
        tabs: [
            {
                key: 'PUBLISH',
                title: '我发布的'
            },
            {
                key: 'RECEIVE',
                title: '我领取的'
            }
        ],
        tasks: { 
            PUBLISH: [], 
            RECEIVE: []
        },
        showLoadOk: false,
        showSpin: false
    },

    /**
     * 请求-获取数据
     */
    getTaskData(index, size, type) {
        if (this.data.isRequest){
            return;
        }

        let that = this;
        this.setData({
            isRequest:true,
            showSpin: true,
            showLoadOk: false
        });
        
        //WXAPI-请求
        let data = {};
        data.index = index;
        data.size = size;
        data.myType =  type;
        data.myStatus = this.data.myStatus;
        let locale = wx.getStorageSync("locale");
        data.lat = locale.lat;
        data.lng = locale.lng;
        WXAPI.getUserTaskList(Util.formatParamDTO(data)).then(
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
                    if (type == "PUBLISH") {
                        res.data.map((o) => { tasks.PUBLISH.push(o);});
                    } else if (type == "RECEIVE") {
                        res.data.map((o) => { tasks.RECEIVE.push(o); });
                    }
                    that.setData({
                        tasks:tasks
                    });
                    that.autoHeight(that.data.key);
                }
            },
            function (err) {
                $Message({
                    content: '服务器开小差了!',
                    type: 'error',
                    duration: 3
                });
            }
        ).then(function(){
            that.setData({
                isRequest:false,
                showSpin: false,
                showLoadOk: true
            });
        });
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            myStatus: options.myStatus
        });

        if(options.myStatus == "CURRENT"){
            wx.setNavigationBarTitle({
                title: '当前任务'
            })
        }else if(options.myStatus == "HISTORY"){
            wx.setNavigationBarTitle({
                title: '历史任务'
            })
        }
        
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        // 清除原来的数据
        let tasks = this.data.tasks;
        tasks.PUBLISH = [];
        tasks.RECEIVE = [];
        this.setData({
            tasks: tasks
        });

        // 给当前页面获取数据
        let index = 0;
        let size = 10;
        this.getTaskData(index, size, this.data.key);
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        let that = this;

        // 给当前界面获取数据
        let index = 0;
        switch (this.data.key) {
            case "PUBLISH": index = this.data.tasks.PUBLISH.length; break;
            case "RECEIVE": index = this.data.tasks.RECEIVE.length; break;
        }
        let size = 10;
        this.getTaskData(index, size, this.data.key);
    },

    // 用户自定义函数
    onTabsChange(e) {
        const { key }= e.detail;
        const index = this.data.tabs.map((n) => n.key).indexOf(key);
        this.setData({
            key,
            index:index
        });
        // 给当前界面获取数据
        let index1 = 0;
        switch (this.data.key) {
            case "PUBLISH": index1 = this.data.tasks.PUBLISH.length; break;
            case "RECEIVE": index1 = this.data.tasks.RECEIVE.length; break;
        }
        let size = 10;
        this.getTaskData(index1, size, this.data.key);
    },
    onSwiperChange(e) {
        const { current: index, source } = e.detail
        const { key } = this.data.tabs[index]
        if (source) {
            this.setData({
                key,
                index
            })
        }
        // 给当前界面获取数据
        let index1 = 0;
        switch (this.data.key) {
            case "PUBLISH": index1 = this.data.tasks.PUBLISH.length; break;
            case "RECEIVE": index1 = this.data.tasks.RECEIVE.length; break;
        }
        let size = 10;
        this.getTaskData(index1, size, this.data.key);
    },

    /**
    * 打开任务详情页
    */
    goTaskDetail: function (e) {
        //跳转
        wx.navigateTo({
            url: './task-detail?taskId=' + e.currentTarget.dataset.taskId
        })
    },

    autoHeight(current) {
        let that = this;
        let { tasks, baseItemHeight } = that.data;
        let listData;
        if (current == "PUBLISH") {
            listData = tasks.PUBLISH;
        } else if (current == "RECEIVE") {
            listData = tasks.RECEIVE;
        }

        let swiperHeight = 0;
        for (let i = 0; i < listData.length; i++) {
            swiperHeight += this.data.baseItemHeightImg;
        }

        this.setData({
            swiperHeight: swiperHeight > 0 ? swiperHeight + 40 : 200
        });

        wx.createSelectorQuery()
            .select('#load').boundingClientRect()
            .select('#' + that.data.key).boundingClientRect().exec(rect => {
                let _space = rect[0].top - rect[1].top;
                let height = swiperHeight - _space;
                this.setData({
                    swiperHeight: height > 0 ? height + 50 : 200
                });
            })
    }
})