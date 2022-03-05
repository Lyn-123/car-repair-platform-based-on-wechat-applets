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
  checkCarId: function(e) {
    if(e){
      this.data.carId = e.detail.value
    }
    var flag = false
    if(this.data.carId.trim()==""){
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
      this.data.manufacturer = e.detail.value
    }
    var flag = false
    if(this.data.manufacturer.trim()==""){
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
      this.data.model = e.detail.value
    }
    var flag = false
    if(this.data.model.trim()==""){
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
      this.data.capacity = e.detail.value
    }
    var flag = false
    if(this.data.capacity.trim()==""){
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
      this.data.year = e.detail.value
    }
    var flag = false
    if(this.data.year.trim()==""){
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
  addCar:function(e){
    const that = this
    if(that.check()){
      wx.request({
        url:"http://localhost:8080/car-repair/car/add",
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
          console.log(res.data)
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
    
  }
})