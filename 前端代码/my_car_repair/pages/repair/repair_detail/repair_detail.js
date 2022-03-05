// pages/order/order_detail/order_detail.js
var App = getApp()
var time2String = require("../../../utils/time2String")
Page({
  data: {
    repair:[],
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
        repairId:data
      },
      url: 'http://localhost:8080/car-repair/repair/detail?',
      method:'GET',
      success:function(res){
        var repair = res.data.data
        repair.establishTime = time2String.formatTime(repair.establishTime)
        that.setData({
          repair:repair
        })
        that.getSumPrice(repair)
        that.getStoreDetail(repair.storeId)
      }
    })
  },
  getSumPrice(list){ 
    var detail_list = list.repairDetailDTOList
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
  finishRepair:function(view){
    const that = this
    var repairId = view.currentTarget.dataset.repairid
    wx.request({
      data:{
        userId:App.globalData.openid,
        repairId:repairId
      },
      url: 'http://localhost:8080/car-repair/repair/finish',
      method:'GET',
      success:function(res){
        console.log(res.data)
        wx.showToast({
          title: '成功结束订单！',
          icon:'success',
          duration:800
        })
        that.data.repair.state = 3
        that.setData(that.data)
      }
    })
  },
  cancelRepair:function(view){
    const that = this
    var state = view.currentTarget.dataset.state
    var repairId = view.currentTarget.dataset.repairid
    if(state == 0){
      wx.request({
        data:{
          userId:App.globalData.openid,
          repairId:repairId
        },
        url: 'http://localhost:8080/car-repair/repair/cancel',
        method:'GET',
        success:function(res){
          console.log(res.data)
          wx.showToast({
            title: '成功取消订单！',
            icon:'success',
            duration:800
          })
          that.data.repair.state = 4
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
  onLoad:function(options){
    this.getRepairDetail(options.repairId)
  },
})