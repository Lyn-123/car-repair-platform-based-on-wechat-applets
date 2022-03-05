//获取应用实例
var App = getApp()
var util = require('../../utils/util');
Page({
  data: {
    store:[],
    hasUserInfo:false,
    userInfo:[],
    car:null,
  },
  getStoreInfo: function() {
    const that = this;
    wx.request({
      url:"http://localhost:8080/car-repair/store/all",
      method:'GET',
      success:function(res){
        that.setData({
          store:res.data.data
        })
      }
    })
  },
  search: function() {
	wx.navigateTo({
      url: "../search/search"
    });
  },
  getDefaultCar(res){
    const that = this
    wx.request({
      data:{
        userId:res
      },
      url:"http://localhost:8080/car-repair/car/myDefault",
      method:'GET',
      success:function(res){
        that.setData({
          car:res.data.data
        })
      },
    })
  },
  goToCarList(){
    wx.navigateTo({
      url: "../car_list/car_list"
    });
  },
  /**
   * 入口
   */
   onLoad: function () {
     App.getUserInfo();
     this.getStoreInfo();
     App.getOpenId().then(res=>{
       this.getDefaultCar(res)
     })
  },
  onShow(){
    App.getOpenId().then(res=>{
      this.getDefaultCar(res)
    })
  }
});