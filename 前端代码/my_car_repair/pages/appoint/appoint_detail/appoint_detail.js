var App = getApp()
var time2String = require('../../../utils/time2String')
var util = require('../../../utils/util')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    appoint:null,
    appointTime: '',
    time:'',
    storeInfo:[],
  },
  getAppointDetail(opt){
    const that = this
    wx.request({
      url: 'http://localhost:8080/car-repair/appoint/detail',
      data:{
        appointId:opt
      },
      method:'GET',
      success:function(res){
        var appoint = res.data.data
        that.getStoreDetail(appoint.storeId)
        that.setData({
          appoint:appoint,
          appointTime:time2String.formatTime(appoint.appointTime),
          time:time2String.formatTime(appoint.time),
          carId:appoint.carId,
          model:appoint.model,
          user:appoint.carUser,
          tel:appoint.userNumber,
          storeName:appoint.storeId,
          storeId:appoint.storeId,
          
          image:appoint.picture,
          detail:appoint.details,
        })
      }
    })
  },
  goToStoreDetail:function(view){
    var storeId = view.currentTarget.dataset.storeid
    wx.navigateTo({
      url: '../../store_detail/store_detail?id='+storeId,
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
  goToRepairDetail:function(view){
    var repairId = util.myTrim(view.currentTarget.dataset.repairid)
    if(!repairId){
      wx.showToast({
        title: '暂无对应维修单',
        icon:'error',
        duration:1000
      })
    }else{
      wx.navigateTo({
        url:"../../repair/repair_detail/repair_detail?repairId="+repairId,
      })
    }
  },
  cancelAppoint:function(view){
    const that = this
    var repairId = util.myTrim(view.currentTarget.dataset.repairid)
    var appointId = view.currentTarget.dataset.appointid
    if(repairId){
      wx.showToast({
        title: '商家已接单！',
        icon:'error',
        duration:1000
      })
    }else{
      App.getOpenId().then(res=>{
        wx.request({
          url: 'http://localhost:8080/car-repair/appoint/delete',
          data:{
            appointId:appointId,
            userId:res
          },
          method:'POST',
          header:{
            'content-type':'application/x-www-form-urlencoded'
          },
          success:function(res){
            console.log(res.data)
            if(res.data.code==0){
              that.goBack("删除成功!","success")
            }
          }
        })
      })
    }
  },
  goBack(msg,icon){
    let pages = getCurrentPages();
    let prevPage = pages[pages.length-2];
    prevPage.setData({
      isRefresh:1
    })
    wx.showToast({
      title: msg,
      icon:icon,
      duration:800
    })
    setTimeout(function(){
      wx.navigateBack({
        delta: 0,
      })
    },800)
  },
  onLoad: function (options) {
    this.getAppointDetail(options.appointId)
  },
})