const WXAPI = require('../../wxapi/main');
const Util = require('../../utils/util.js');
const { $Message } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isRequest:false,
        fabButton: [
            {
                label: '发布任务',
                icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABkklEQVRYR+2X0VHDMBBEdyuADqADoAJCBdABoYNQAUkFQAUkHVBC6CDpgBKggmPWIzGOY0tyJOPJDPpxYsnS097p7kSM3Djy+tgDMLMJgGcAl4XhNgAeSa7r87YBaOBF4cX9dBuSVzEAcwO+AbwD+OyAkVLXrm/RGPPk/n8A0IamAE70juTOptsU8ACvJGddSpjZHEC1UHNSM/NzLEjOzWwJ4L4vQPVxIYBO2JACvQCc80qNdYsCwwIAkGfLJ9Tqv70JBgfostSfAqwcReVs/nQ4JxxEAUmuY6pjNiOpJ8xMAezFBbKJ3odOzMFO2CdQ/QMctwIuwLwBOA/Y/QvAjXfE5rgsBcxM+UDpOdYeSCrm77VcgFMAdwkKLElKibIAsW2n9GcpkLJAbEwWgJnJBLcREzQZZIqVN0kuQKoTNiF+nTIXQDFf3n0Wk7rWr3KuygMuP5RPRj1glKCOEyBYlPZU4KCiVJ4cKstTGXSKVJbrmVSWD3kx2ZLcuXF1Xc1U0ZS+HW1d5RS+mqXqWmrc6LfjH6d2SzCK7AqQAAAAAElFTkSuQmCC'
            }
        ],
        swiperHeight:0,
        baseItemHeightImg:128,
        key: 'NOTICE',
        index: 0,
        tabs: [
            {
                key: 'NOTICE',
                title: '通知公告'
            },
            {
                key: 'WAIKUAI',
                title: '外卖快递'
            },
            {
                key: 'LOSTF',
                title: '失物招领'
            },
            {
                key: 'OTHER',
                title: '其他任务'
            }
        ],
        tasks: { 
            NOTICE: [], 
            WAIKUAI: [],
            LOSTF: [],
            OTHER: []
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
        data.type =  type;
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
                    if (type == "NOTICE") {
                        res.data.map((o) => {tasks.NOTICE.push(o);});
                    } else if (type == "WAIKUAI") {
                        res.data.map((o) => { tasks.WAIKUAI.push(o); });
                    } else if (type == "LOSTF") {
                        res.data.map((o) => { tasks.LOSTF.push(o); });
                    } else {
                        res.data.map((o) => { tasks.OTHER.push(o); });
                    }
                    that.setData({
                        tasks:tasks
                    });
                    that.autoHeight(type);
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
        
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        // 清除原来的数据
        let tasks = this.data.tasks;
        tasks.NOTICE = [];
        tasks.WAIKUAI = [];
        tasks.LOSTF = [];
        tasks.OTHER = [];
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
            case "NOTICE": index = this.data.tasks.NOTICE.length; break;
            case "WAIKUAI": index = this.data.tasks.WAIKUAI.length; break;
            case "LOSTF": index = this.data.tasks.LOSTF.length; break;
            case "OTHER": index = this.data.tasks.OTHER.length; break;
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
            case "NOTICE": index1 = this.data.tasks.NOTICE.length; break;
            case "WAIKUAI": index1 = this.data.tasks.WAIKUAI.length; break;
            case "LOSTF": index1 = this.data.tasks.LOSTF.length; break;
            case "OTHER": index1 = this.data.tasks.OTHER.length; break;
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
            case "NOTICE": index1 = this.data.tasks.NOTICE.length; break;
            case "WAIKUAI": index1 = this.data.tasks.WAIKUAI.length; break;
            case "LOSTF": index1 = this.data.tasks.LOSTF.length; break;
            case "OTHER": index1 = this.data.tasks.OTHER.length; break;
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
        if (current == "NOTICE") {
            listData = tasks.NOTICE;
        } else if (current == "WAIKUAI") {
            listData = tasks.WAIKUAI;
        } else if (current == "LOSTF") {
            listData = tasks.LOSTF;
        } else {
            listData = tasks.OTHER;
        }

        let swiperHeight =0;
        for(let i=0; i<listData.length; i++){
            swiperHeight += this.data.baseItemHeightImg;
        }

        this.setData({
            swiperHeight: swiperHeight > 0 ? swiperHeight+40:200
        });

        wx.createSelectorQuery()
            .select('#load').boundingClientRect()
            .select('#'+that.data.key).boundingClientRect().exec(rect => {
                let _space = rect[0].top - rect[1].top;
                let height = swiperHeight - _space;
                this.setData({
                    swiperHeight: height>0?height+50:200
                });
        })
    },

    goPublishTask(){
        //跳转
        wx.navigateTo({
            url: './task-publish'
        })
    }
})