var App = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    carId:"",
    manufacturer:"",
    model:"",
    capacity:"",
    year:"",
    errorInfo:"",
  },
  myTrim(str){
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
  },
  checkCarId: function(e) {
    if(e){
      this.data.carId = this.myTrim(e.detail.value)
    }
    var flag = false
    if(!this.data.carId){
      this.data.errorInfo = "车牌号不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  checkManufacturer:function(e) {
    if(e){
      this.data.manufacturer = this.myTrim(e.detail.value)
    }
    var flag = false
    if(!this.data.manufacturer){
      this.data.errorInfo = "汽车品牌不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  checkModel:function(e) {
    if(e){
      this.data.model =this.myTrim(e.detail.value)
    }
    var flag = false
    if(!this.data.model){
      this.data.errorInfo="汽车型号不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  checkCapacity:function(e) {
    if(e){
      this.data.capacity = this.myTrim(e.detail.value)
    }
    var flag = false
    if(!this.data.capacity){
      this.data.errorInfo="发动机排量不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  checkYear:function(e) {
    if(e){
      this.data.year = this.myTrim(e.detail.value)
    }
    var flag = false
    if(!this.data.year){
      this.data.errorInfo="生成年份不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  check(){
    return (this.checkCarId()&&this.checkManufacturer()&&this.checkModel()&&this.checkCapacity()&&this.checkYear())
  },
  modifyCar:function(e){
    const that = this
    if(that.check()){
      wx.request({
        url:"http://localhost:8080/car-repair/car/modify",
        data:{
          carId:that.data.carId,
          manufacturer:that.data.manufacturer,
          model:that.data.model,
          capacity:that.data.capacity,
          year:that.data.year,
          userId:App.globalData.openid
        },
        method:'POST',
        header:{
          'content-type': 'application/json'
        },
        success:function(res){
          wx.navigateBack({
            delta: 0,
          })
        }
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '我的爱车' 
    })
    var car = JSON.parse(options.car)
    this.setData({
      carId:car.carId,
      manufacturer:car.manufacturer,
      model:car.model,
      capacity:car.capacity,
      year:car.year
    })
  }
})