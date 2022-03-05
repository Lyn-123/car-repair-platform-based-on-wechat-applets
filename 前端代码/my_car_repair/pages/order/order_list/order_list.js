var App = getApp();

Page({
  data: {
    orderList:[],
    serviceType:["","汽车维修","汽车改装","轮胎服务","洗车美容","车辆保养"],
    states:["待维修","维修中","待提车","已结束","取消订单"],
    sumPrice:[]
  },
  getOrderList(){
    const that = this
    wx.request({
      data:{
        userId:App.globalData.openid
      },
      url: 'http://localhost:8080/car-repair/orders/my',
      method:'GET',
      success:function(res){
        that.setData({
          orderList:res.data.data
        })
      }
    })
  },
  onShow: function (options) {
    this.getOrderList()
  },
  onLoad(){
    
  }
})