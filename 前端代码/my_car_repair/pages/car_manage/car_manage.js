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
  setDefault(view){
    const that = this
    wx.request({
      data:{
        userId:App.globalData.openid,
        carId:view.currentTarget.dataset.carid,
      },
      url:"http://localhost:8080/car-repair/car/modifyDefault",
      method:'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded' 
      },
      success:function(res){
        that.onLoad();
      }
    })
  },
  deleteCar:function(view){
    const that = this
    wx.showModal({
      title: '温馨提示',
      content: '删除该车后，车辆数据将不再保留，您确定删除吗？',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            data:{
              carId:view.currentTarget.dataset.carid,
            },
            url:"http://localhost:8080/car-repair/car/delete",
            method:'POST',
            header: {
              'content-type': 'application/x-www-form-urlencoded' 
            },
            success:function(res){
              that.onLoad();
            }
          })
        }
      }
    })
   },
   modifyCar(view){
    var car = view.currentTarget.dataset.car
    wx.navigateTo({
      url: '../car_modify/car_modify?car='+JSON.stringify(car),
    })
  },
   addCar(){
     wx.navigateTo({
       url: '../car_add/car_add',
     })
   },
   onShow(){
     this.getCarList()
   },
  /**
   * 入口
   */
  onLoad() {
    wx.setNavigationBarTitle({
      title: '管理车库' 
    })
    this.setData({
      userId:App.globalData.openid
    })
		this.getCarList()
	},
});
