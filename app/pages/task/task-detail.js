const WXAPI = require('../../wxapi/main');
const { $Message } = require('../../components/iView/base/index');
const { $Toast } = require('../../components/iView/base/index');

var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        showActionSheet: false,
        actions: [
            {
                name: '分享',
                icon: 'share',
                openType: 'share'
            }
        ],
        shareNewId: ""
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

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {
        let that = this;
        return {
            title: 'CJLU信息共享',
            imageUrl: 'https://file.iviewui.com/iview-weapp-logo.png',
            path: '../new-detail/index?newId=' + that.data.shareNewId
        };
    },

   /**
    * 打开actionSheet
    */
    openActionSheet(e) {
        this.setData({
            showActionSheet: true,
            shareNewId: e.currentTarget.dataset.newId
        });
    },

    /**
     * 点击了取消（actionSheet）
     */
    cancelActionSheet() {
        this.setData({
            showActionSheet: false
        });
    },

    /**
    * 点击了分享（actionSheet）
    */
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

    // 用户自定义函数
    publishTask(){
        // WXAPI创建任务
    }
})