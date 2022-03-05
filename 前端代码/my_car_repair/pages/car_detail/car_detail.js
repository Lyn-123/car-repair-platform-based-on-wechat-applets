// pages/car_detail/car_detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    carId:null,
    car:[],
  },
  getCarDetail(id){
    const that = this
    wx.request({
      data:{
        carId:id
      },
      url: 'http://localhost:8080/car-repair/car/detail',
      method:'GET',
      success:function(res){
        that.setData({
          car:res.data.data
        })
        console.log(res.data.data)
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCarDetail(options.carId)
  },
})