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
            actions: [
                {
                    name: '取消'
                },
                {
                    name: '确认',
                    color: '#19be6b',
                    loading: false
                }
            ],
            visible:false
            
        },
        header: {},
        baseUrl: "",
        types: ["通知公告", "外卖快递", "失物招领", "其他"],
        pictruesList: [],
        form:{
            title:"",
            content:"",
            type:"",
            endDate:"",
            endTime:"",
            telNumber:"",
            reward: 0,
            lat:"",
            lng:"",
            localeName:"",
            pictrues:"",
        }
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
        let header = {};
        let bdmerInfo = wx.getStorageSync("bdmerInfo");
        header.cookie = "token=" + wx.getStorageSync("token");
        this.setData({
            header: header,
            baseUrl: CONFIG.baseUrl
        });
    },

    openPublishTasktModal(e) {
        // 任务表单检查
        let form = this.data.form;
        if (Util.isNull(form)){
            $Message({
                content: '任务不能为空',
                type: 'error',
                duration: 3
            });
            return;
        }

        if (Util.isNull(form.title)){
            $Message({
                content: '任务标题不能为空',
                type: 'error',
                duration: 3
            });
            return;
        }

        if (Util.isNull(form.content)) {
            $Message({
                content: '任务内容不能为空',
                type: 'error',
                duration: 3
            });
            return;
        }

        if (Util.isNull(form.type)) {
            $Message({
                content: '任务类型不能为空',
                type: 'error',
                duration: 3
            });
            return;
        }

        if (Util.isNull(form.endDate) || Util.isNull(form.endTime)) {
            $Message({
                content: '任务时间日期不能为空',
                type: 'error',
                duration: 3
            });
            return;
        }

        if (this.data.form.type !== "通知公告" && this.data.form.type !== "失物招领") {
            if (Util.isNull(form.telNumber)){
                $Message({
                    content: '手机号不能为空',
                    type: 'error',
                    duration: 3
                });
                return;
            } else if (!(/^1[34578]\d{9}$/.test(form.telNumber))) {
                $Message({
                    content: '手机号码有误!',
                    type: 'error',
                    duration: 3
                });
                return;
            }
        }

        if (Util.isNull(form.localeName)) {
            $Message({
                content: '位置不能为空',
                type: 'error',
                duration: 3
            });
            return;
        }

        let modal = this.data.modal;
        modal.visible = true;

        this.setData({
            modal: modal
        });
    },
    
    // 用户自定义函数
    publishTask(e){
        let modal = this.data.modal;
        modal.visible = false;
        this.setData({
            modal: modal
        })
        if (e.detail.index != 1) {
            return;
        }

        // 只有通过验证的用户才可以发布可领取任务
        let type = this.data.form.type;
        if(type == "外卖快递" || type == "其他"){
            let bdmerInfo = wx.getStorageSync("bdmerInfo")
            if (!bdmerInfo) {
                app.goLoginPageTimeOut();
                return;
            }

            if (bdmerInfo.authStatus != 1) {
                wx.showModal({
                    title: '用户认证',
                    content: '请先完成用户认证',
                    success(res) {
                        wx.navigateTo({
                            url: '../mine/mine-cert',
                        })
                    }
                })
                return;
            }
        }

        // WXAPI创建任务
        WXAPI.publishTask(Util.formatParamDTO(this.data.form)).then(
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
                    let taskId = res.data;
                    // doSomething
                    $Message({
                        content: "发布成功",
                        type: 'success',
                        duration: 3
                    });
                    wx.redirectTo({
                        url:"./task-detail?taskId="+taskId
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

    checkTelNumber(e){
        let telNumber = e.detail.value;
        if (!(/^1[34578]\d{9}$/.test(telNumber))) {
            $Message({
                content: '手机号码有误',
                type: 'error',
                duration: 3
            });
        }   
    },

    updateForm(e){
        let target = e.currentTarget.id;

        let form = this.data.form;
        form[target] = e.detail.value;
        this.setData({
            form: form
        })
    },

    getType(e){
        let form = this.data.form;
        form.type = this.data.types[e.detail.value];

        this.setData({
            form: form
        })
    },

    getLocale(){
        let that = this;
        wx.chooseLocation({
            success: function (res) {
                let form = that.data.form;
                form.lat = res.latitude;
                form.lng = res.longitude;
                form.localeName = res.name;
                that.setData({
                    form: form
                })
            },
            fail: function (err) {
                console.log(err);
                $Message({
                    content: "请选择位置",
                    type: 'warning',
                    duration: 3
                });
            },
            complete: function () {

            }
        });
    },

    getPictrues(){
        let that = this;
        let baseUrl = this.data.baseUrl;
        let header = this.data.header;

        wx.chooseImage({
            success(res) {
                const tempFilePaths = res.tempFilePaths
                wx.uploadFile({
                    url: baseUrl + '/wxApp/wx/task/uploadTaskPictrue',
                    header:header,
                    filePath: tempFilePaths[0],
                    name: 'file',
                    success(res) {
                        res = JSON.parse(res.data);
                        let pictruesList = that.data.pictruesList;
                        let form = that.data.form;
                        if (form.pictrues === ""){
                            form.pictrues = res.data;
                        }else{
                            form.pictrues = form.pictrues + ";" + res.data;
                        }
                        pictruesList.push(baseUrl + res.data);

                        that.setData({
                            pictruesList: pictruesList,
                            form: form
                        });
                    },
                    fail(){
                        $Message({
                            content: '服务器开小差了!',
                            type: 'error',
                            duration: 3
                        });
                    }
                })
            }
        })
    },

    previewImage: function (e) {
        wx.previewImage({
            current: e.currentTarget.id, // 当前显示图片的http链接
            urls: this.data.pictruesList // 需要预览的图片http链接列表
        })
    }
})