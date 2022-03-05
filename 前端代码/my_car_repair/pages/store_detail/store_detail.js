//获取应用实例
var app = getApp()
var util = require('../../utils/util');

Page({
  data: {
    storeId:0,
    storeInfo:{},
    services:{}
  },
  getStoreService:function(){
    const that = this;
    wx.request({
      data:{
        storeId:that.data.storeId
      },
      url:"http://localhost:8080/car-repair/service/store",
      method:'GET',
      success:function(res){
        that.setData({
          services:res.data.data
        })
      }
    })
  },
  getStoreDetail: function() {
    const that = this;
    wx.request({
      data:{
        storeId:that.data.storeId
      },
      url:"http://localhost:8080/car-repair/store/detail",
      method:'GET',
      success:function(res){
        that.setData({
          storeInfo:res.data.data
        })
      }
    })
  },
  callService: function() {
  	wx.makePhoneCall({
      phoneNumber: '12345678',
    })
  },
  callStore: function(view) {
  	var tel = view.currentTarget.dataset.value;
  	wx.makePhoneCall({
      phoneNumber: tel,
    })
  },
  onLoad: function (option) {
    this.setData({
      storeId:option.id
    })
    this.getStoreDetail();
    this.getStoreService();
  }
});