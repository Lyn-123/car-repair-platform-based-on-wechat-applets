var App = getApp()

Page({
  data: {
    userId:"",
    carList:null
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
  addCar(){
    wx.navigateTo({
      url:"../../../car_add/car_add"
    })
  },
  chooseCar:function(view){
    let pages = getCurrentPages(); // 当前页，
    let prevPage = pages[pages.length - 2]; // 上一页
    prevPage.setData({
      mydata: { 
        isRefresh: 1 ,
        carInfo:{
          carId:view.currentTarget.dataset.carid,
          model:view.currentTarget.dataset.model
        }
      }
    })
    wx.navigateBack({
      delta: 1
    })
  },
  /**
   * 入口
   */
  onLoad() {
    this.setData({
      userId:App.globalData.openid
    })
		this.getCarList()
  },
  onShow(){
    this.getCarList()
  }
});
