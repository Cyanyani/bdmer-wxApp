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
        header: {},
        baseUrl: "",
        types: ["通知公告", "外卖快递", "失物招领", "其他"],
        pictruesList: ["http://www.cjlu.edu.cn/upload/ad/1550934473809.jpg","http://www.cjlu.edu.cn/upload/ad/1550934473809.jpg"],
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
    
    // 用户自定义函数
    publishTask(){
        // WXAPI创建任务
    },

    checkTelNumber(e){
        let telNumber = e.detail.value;
        if (!(/^1[34578]\d{9}$/.test(telNumber))) {
            $Message({
                content: '手机号码有误!',
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
                    content: "未授权",
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
                    url: baseUrl + '/wxApp/wx/task/uploadPictrue',
                    header:header,
                    filePath: tempFilePaths[0],
                    name: 'file',
                    success(res) {
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