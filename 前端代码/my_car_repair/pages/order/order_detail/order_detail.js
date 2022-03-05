var App = getApp()
var time2String = require("../../../utils/time2String")
Page({
  data: {
    order:[],
    sumPrice:0,
    storeInfo:[],
    state:["待维修","维修中","待提车","已结束","取消订单"],
    serviceType:["","汽车维修","汽车改装","轮胎服务","洗车美容","车辆保养"],
  },
  getRepairDetail(data){
    var that = this
    wx.request({
      data:{
        userId:App.globalData.openid,
        orderId:data
      },
      url: 'http://localhost:8080/car-repair/orders/detail',
      method:'GET',
      success:function(res){
        var order = res.data.data
        order.establishTime = time2String.formatTime(order.establishTime)
        order.updateTime = time2String.formatTime(order.updateTime)
        that.setData({
          order:order
        })
        that.getSumPrice(order)
        that.getStoreDetail(order.storeId)
      }
    })
  },
  getSumPrice(list){ 
    var detail_list = list.orderDetailDTOList
    var sumPrice = 0
    for(var j=0;j<detail_list.length;j++){
      sumPrice = parseFloat(sumPrice)+detail_list[j].number*detail_list[j].price
      sumPrice=parseFloat(sumPrice).toFixed(2)
    }
    this.setData({
      sumPrice:sumPrice
    })
  },
  getStoreDetail: function(storeId) {
    const that = this;
    wx.request({
      data:{
        storeId:storeId
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
  goToPartDetail:function(view){
    var partsId = view.currentTarget.dataset.partsid
    wx.navigateTo({
      url:'../../parts/part_detail/part_detail?partsId='+partsId
    })
  },
  gotoServiceDetail:function(view){
    var serviceId = view.currentTarget.dataset.serviceid
    wx.navigateTo({
      url: '../../service_detail/service_detail?id='+serviceId,
    })
  },
  finishRepair:function(view){
    const that = this
    var orderId = view.currentTarget.dataset.orderid
    wx.request({
      data:{
        userId:App.globalData.openid,
        orderId:orderId
      },
      url: 'http://localhost:8080/car-repair/orders/finish',
      method:'GET',
      success:function(res){
        console.log(res.data)
        wx.showToast({
          title: '成功结束订单！',
          icon:'success',
          duration:800
        })
        that.data.order.state = 3
        that.setData(that.data)
      }
    })
  },
  cancelRepair:function(view){
    const that = this
    var state = view.currentTarget.dataset.state
    var orderId = view.currentTarget.dataset.orderid
    if(state == 0){
      wx.request({
        data:{
          userId:App.globalData.openid,
          orderId:orderId
        },
        url: 'http://localhost:8080/car-repair/orders/cancel',
        method:'GET',
        success:function(res){
          console.log(res.data)
          wx.showToast({
            title: '成功取消订单！',
            icon:'success',
            duration:800
          })
          that.data.order.state = 4
          that.setData(that.data)
        }
      })
    }else if(state==1){
      wx.showToast({
        title: '维修进行中！',
        icon:"error"
      })
    }else if(state==2){
      wx.showToast({
        title: '维修已完成！',
        icon:"error"
      })
    }
  },
  onLoad:function(options){
    this.getRepairDetail(options.orderId)
  },
})