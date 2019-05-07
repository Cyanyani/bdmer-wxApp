const CONFIG = require('config.js');
const Util = require("../utils/util.js");

const login = (data) => {
  let _url = CONFIG.baseUrl + CONFIG.subDomain + '/user/login';
    wx.showLoading({
        title: '登陆中'
    });

  return new Promise((resolve, reject) => {
    wx.request({
      url: _url,
      method: 'post',
      data: data,
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success(request) {
        resolve(request.data);
      },
      fail(error) {
        reject(error);
      },
      complete() {
        wx.hideLoading();
        console.log("login-执行完毕");
      }
    })
  })
};

const request = (url, method, data, isLoading=true) => {
    let _url = CONFIG.baseUrl + CONFIG.subDomain + url;
    console.log(`请求方法：${method}\n请求地址：${_url}\n请求参数:`);
    console.log(data);
    console.log("\n");

    if (method === "get" && data !== undefined){
        _url = Util.formatGETUrl(_url, data);
    }

    // 获取token
    let token = wx.getStorageSync('token');

    return new Promise((resolve, reject) => {
        if (!token || token === '') {
            console.log('没有token');
            let res = {};
            res.code = -2;
            res.msg = "本地错误，没有token"
            resolve(res);
            return;
        }
        if (isLoading){
            wx.showLoading({
                title: '加载中'
            });
        }
    
        wx.request({
        url: _url,
        method: method,
        data: data,
        header: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Cookie':'token=' + token
        },
        success(res) {
            console.log("请求结果:");
            console.log(res.data);
            console.log("\n");
            resolve(res.data);
        },
        fail(error) {
            reject(error);
        },
        complete() {
            if(isLoading){
                wx.hideLoading();
            }
            console.log(`${url}-执行完毕`);
        }
        })
    })
};

module.exports = {
    request,
    login,
    checkToken:() => {
        let data = {};
        data.token = wx.getStorageSync('token');
        return request('/user/checkToken', 'get', data);
    },
    sendUserInfo:(data) => {
        return request('/user/sendWxUserInfo', 'post', data);
    },
    getUserBdmerInfo:() => {
        return request('/user/getUserBdmerInfo', 'get', undefined, false);
    },
    updateUserLocale: (data) => {
        return request('/user/updateUserLocale', 'post', data);
    },
    updateUserTelNumber: (data) => {
        return request('/user/updateUserTelNumber', 'post', data);
    },
    publishTask:(data) => {
        return request('/task/createTask', 'post', data);
    },
    getTaskList:(data) => {
        return request('/task/getTaskList', 'post', data);
    },
    getTaskUserList: () => { 
        
    },
    getTaskDeatil: (data) => { 
        return request('/task/getTaskDetail', 'get', data);
    },
    getDoUser(data){
        return request('/task/getDoUser', 'get', data);
    },
    updateTaskStatus: (data) => {
        return request('/task/updateTaskStatus', 'get', data);
    },
    updateTaskDoUid: (data) => {
        return request('/task/updateTaskDoUid', 'get', data);
    },
    updateTaskGivePoint:() =>{
        
    }
}