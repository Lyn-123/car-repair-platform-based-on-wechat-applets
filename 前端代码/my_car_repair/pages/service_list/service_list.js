// pages/service_list/service_list.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    serviceList:[],
    type:["汽车维修","汽车改装","轮胎服务","洗车美容","车辆保养"]
  },
  getServiceList(opt){
    const that = this
    wx.request({
      data:{
        serviceType:opt
      },
      url: 'http://localhost:8080/car-repair/service/type',
      method:'GET',
      success:function(res){
        that.setData({
          serviceList:res.data.data
        })
        console.log(that.data.serviceList)
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var serviceType = options.serviceType
    wx.setNavigationBarTitle({
      title: this.data.type[serviceType-1] 
    })
    this.getServiceList(serviceType)
  },
})