const CONFIG = require("../../wxapi/config.js");
const WXAPI = require('../../wxapi/main');
const Util = require("../../utils/util.js");
const { $Message } = require('../../components/iView/base/index');
const { $Toast } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        modal:{
            baseUrl:'',
            value:"",
            title: "",
            content: "",
            mFunction: "",
            visible:false,
            actions: [
                {
                    name: '取消'
                },
                {
                    name: '确认',
                    color: '#19be6b',
                    loading: false
                }
            ]
        },
        

        uid:"",
        doUser:{},
        givePointType: [-3, -2, -1, 1, 2, 3],
        taskId:0,
        nowTime:0,
        endTime: 0,
        task:{},

        showActionSheet: false,
        actions: [
            {
                name: '分享任务',
                icon: 'share',
                openType: 'share'
            }
        ]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            taskId: options.taskId
        })
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        // 获取用户uid
        let uid = 0
        try {
            uid = wx.getStorageSync("bdmerInfo").uid;
            if (Util.isNull(uid)) {
                app.goLoginPageTimeOut();
            }
        } catch (e) {
            app.goLoginPageTimeOut();
        }

        let that = this;
        this.setData({
            baseUrl: CONFIG.baseUrl,
            uid: uid
        })

        // WXAPI获取任务详情
        let data = {};
        data.taskId = this.data.taskId;
        WXAPI.getTaskDeatil(data).then(
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
                    //doSomething
                    let task = res.data;
                    switch (task.type) {
                        case "NOTICE": task.typeName = "通知公告"; break;
                        case "WAIKUAI": task.typeName = "外卖快递"; break;
                        case "LOSTF": task.typeName = "失物招领"; break;
                        case "OTHER": task.typeName = "其他"; break;
                    }
                    switch (task.status) {
                        case "FINDING": task.statusName = "发布中"; break;
                        case "DOING": task.statusName = "执行中"; break;
                        case "CANCEL": task.statusName = "已取消"; break;
                        case "FINISH": task.statusName = "已完成"; break;
                    }
                    if (task.reward == null) {
                        task.reward = 0;
                    }
                    // 设置时间
                    let nowTime = new Date().getTime();
                    let endTime = new Date(task.endTime).getTime()
                    that.setData({
                        task: task,
                        nowTime: nowTime,
                        endTime: endTime
                    })
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
        ).then(function () {
            if (Util.isNull(that.data.task.doUid)) {
                return;
            }
            // 查询doUserDTO
            let data = {};
            data.userId = that.data.task.doUid;
            WXAPI.getDoUser(data).then(
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
                        let doUser = res.data;
                        that.setData({
                            doUser: doUser
                        })
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
        });
    },

    openGivePointModal(e){
        console.log(e);
        let modal = this.data.modal;
        modal.title = "确认评分";
        modal.content = "评分后无法修改哦";
        modal.mFunction = "updateGivePoint";
        modal.visible = true;
        modal.value = this.data.givePointType[e.detail.value];

        this.setData({
            modal:modal
        });
    },
    openCancelTaskModal(e) {
        let modal = this.data.modal;
        modal.title = "确认取消";
        modal.content = "取消后无法恢复哦";
        modal.mFunction = "cancelTask";
        modal.visible = true;

        this.setData({
            modal: modal
        });
    },
    openReceiveTaskModal(e) {
        let bdmerInfo = wx.getStorageSync("bdmerInfo")
        if (!bdmerInfo){
            app.goLoginPageTimeOut();
            return;
        }

        if (!bdmerInfo.telNumber) {
            wx.showModal({
                title: '请完善个人信息',
                content: '请先绑定手机号码',
                success(res) {
                    wx.navigateTo({
                        url: '../mine/mine-info',
                    })
                }
            })
            return;
        }

        if (bdmerInfo.authStatus != 1) {
            wx.showModal({
                title: '请完成认证',
                content: '请先完成身份认证',
                success(res) {
                    wx.navigateTo({
                        url: '../mine/mine-cert',
                    })
                }
            })
            return;
        }

        let modal = this.data.modal;
        modal.title = "确认领取";
        modal.content = "领取后就一定要完成哦";
        modal.mFunction = "receiveTask";
        modal.visible = true;

        this.setData({
            modal: modal
        });
    },
    openFinishTaskModal(e) {
        let modal = this.data.modal;
        modal.title = "确认完成";
        modal.content = "完成后任务发布人要做评价";
        modal.mFunction = "finishTask";
        modal.visible = true;

        this.setData({
            modal: modal
        });
    },
    previewImage: function (e) {
        let that = this;
        let pictrues = this.data.task.pictrues.map(function (o) {
            return that.data.baseUrl + o;
        });
        console.log(pictrues);
        wx.previewImage({
            current: e.currentTarget.id, // 当前显示图片的http链接
            urls: pictrues // 需要预览的图片http链接列表
        })
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {
        let that = this;
        return {
            title: "BDMER帮点儿忙",
            imageUrl: 'https://bdmer.cn/nginx/image/logo.jpg',
            path: 'pages/task/task-detail?taskId=' + that.data.taskId
        };
    },
    openActionSheet(e) {
        this.setData({
            showActionSheet: true
        });
    },
    cancelActionSheet() {
        this.setData({
            showActionSheet: false
        });
    },
    clickActionSheet() {
        const action = [...this.data.actions];
        action[0].loading = true;

        this.setData({
            actions: action
        });

        setTimeout(() => {
            action[0].loading = false;
            this.setData({
                showActionSheet: false,
                actions: action
            });
        }, 2000);
    },


    /**
     * wxapi相关
     */
    receiveTask(e){
        let that = this;
        let modal = this.data.modal;
        modal.visible = false;
        this.setData({
            modal: modal
        })
        if (e.detail.index != 1) {
            return;
        }

        //WXAPI领取任务
        let data = {};
        data.taskId = this.data.taskId;
        WXAPI.updateTaskDoUid(data).then(
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
                    $Message({
                        content: '领取成功',
                        type: 'success',
                        duration: 3
                    });
                    that.onShow();
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
    cancelTask(e) {
        let that = this;
        let modal = this.data.modal;
        modal.visible = false;
        this.setData({
            modal: modal
        })
        if (e.detail.index != 1) {
            return;
        }

        //WXAPI取消任务
        let data = {};
        data.taskId = this.data.taskId;
        data.taskStatus = "CANCEL";
        WXAPI.updateTaskStatus(data).then(
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
                    $Message({
                        content: '取消成功',
                        type: 'success',
                        duration: 3
                    });
                    that.onShow();
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
    finishTask(e) {
        let that =this;
        let modal = this.data.modal;
        modal.visible = false;
        this.setData({
            modal: modal
        })
        if (e.detail.index != 1) {
            return;
        }
       
        //WXAPI完成任务
        let data = {};
        data.taskId = this.data.taskId;
        data.taskStatus = "FINISH";
        WXAPI.updateTaskStatus(data).then(
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
                    that.onShow();
                } else {
                    // doSomething
                    $Message({
                        content: '执行成功',
                        type: 'success',
                        duration: 3
                    });
                    that.onShow();
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
    updateGivePoint(e){
        let  that = this;
        let modal = this.data.modal;
        modal.visible = false;
        if(e.detail.index != 1){
            this.setData({
                modal: modal
            })
            return;
        }

        this.setData({
            modal:modal
        })

        //WXAPI评分
        //WXAPI完成任务
        let data = {};
        data.taskId = this.data.taskId;
        data.givePoint = modal.value;
        WXAPI.updateGivePoint(data).then(
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
                    let task = that.data.task;
                    task.givePoint = modal.value;
                    that.setData({
                        task: task
                    })
                    $Message({
                        content: '评分成功',
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

    }

})