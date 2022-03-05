var App = getApp()
var util = require('../../utils/util');

Page({
  data: {
    userId:"",
    carList:null
  },
  getText: function(e) {
    this.data.name = e.detail.value
  },
  getCarList(){
    const that = this
    wx.request({
      data:{
        userId:that.data.userId
      },
      url:"http://localhost:8080/car-repair/car/my",
      method:'GET',
      success:function(res){
        that.setData({
          carList : res.data.data
        })
      }
    })
  },
  manageCars(){
    wx.navigateTo({
      url:"../car_manage/car_manage"
    })
  },
  /**
   * 入口
   */
  onLoad() {
    wx.setNavigationBarTitle({
      title: '我的车库' 
    })
    this.setData({
      userId:App.globalData.openid
    })
		this.getCarList()
  },
  onShow(){
    this.getCarList()
  }
});
