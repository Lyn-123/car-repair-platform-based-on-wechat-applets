var App = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasCarInfo:false,
    carId:"",
    model:"",
    user:"",
    tel:"",
    storeName:"",
    storeId:"",
    date: '2021-06-01',
    time: '8:00',
    image:'../../../images/none.png',
    detail:"",
    errorInfo:"",
  },
  getData:function(data){
    if(data.carInfo){
      this.setData({
        hasCarInfo:true,
        carId:data.carInfo.carId,
        model:data.carInfo.model
      })
    }
    if(data.storeInfo){
      this.setData({
        storeName:data.storeInfo.storeName,
        storeId:data.storeInfo.storeId
      })
    }
  },
  chooseStore:function(){
    wx.navigateTo({
      url: '../choose/choose_store/choose_store'
    })
  },
  chooseCar(){
    wx.navigateTo({
      url: '../choose/choose_car/choose_car'
    })
  },
  // 日期判断
  bindDateChange: function(e) {    
    this.setData({      
      date: e.detail.value
    })
  },  
  checkDate:function(){
    var flag =true
    var chosen = new Date(this.data.date+" "+this.data.time)
    var now = new Date();
    if (Date.parse(chosen) < Date.parse(now)) {//时间戳对比
      this.setData({
        errorInfo:"不能选择过去时间！"
      })    
      flag=false
    }
    return flag
  },
  bindTimeChange: function(e) {    
    this.setData({      
      time: e.detail.value
    })
  },
  bindPickerChange: function(e) {    
    console.log('picker发送选择改变，携带值为', e.detail.value)    
    this.setData({      
      index: e.detail.value
    })
  },  
  //图像判断
  Imgupdate(){  
    const that = this
    wx.chooseImage({
      success: function(res) {
        var tempFilePaths = res.tempFilePaths
        var tempFilesSize = res.tempFiles[0].size;  //获取图片的大小，单位B
          if (tempFilesSize >= 2097152) {
            wx.showToast({
              title: '图片不能大于2M',  
              icon: 'error',
              duration: 1000
            })            
          }else{
            wx.uploadFile({
              url: 'http://localhost:8080/car-repair/image/update', 
              filePath: tempFilePaths[0],
              name: 'file',
              success(res) {
                let objRes = JSON.parse(res.data);
                let url = 'http://localhost:8080/car-repair'+objRes.data;
                that.setData({
                  image:url
                })  
              },
            });
          }
      },
    })
  },
  checkImg:function() {
    var flag = false
    if(this.data.image=="../../../images/none.png"){
      this.data.errorInfo="故障图片不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
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
  checkUser:function(e) {
    if(e){
      this.data.user = e.detail.value
    }
    var flag = false
    if(this.data.user.trim()==""){
      this.data.errorInfo="联系人不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  checkTel:function(e) {
    if(e){
      this.data.tel = e.detail.value
    }
    var flag = false
    if(this.data.tel.trim()==""){
      this.data.errorInfo="联系电话不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  getDetail:function(e){
    this.setData({
      detail:e.detail.value.trim()
    })
  },
  checkDetail:function() {
    var flag = false
    if(this.data.detail==""){
      this.data.errorInfo="故障详情不能为空！"
      flag=false
    }else{
      this.data.errorInfo = ""
      flag=true
    }
    this.setData(this.data)
    return flag
  },
  check(){
    return (this.checkCarId()&&this.checkModel()&&this.checkUser()&&this.checkTel()&&this.checkDate()&&this.checkImg()&&this.checkDetail())
  },
  addAppoint:function(e){
    const that = this
    if(that.check()){
      var appointTime = new Date(that.data.date+" "+that.data.time)
      wx.request({
        url:"http://localhost:8080/car-repair/appoint/create",
        data:{
          appointTime: appointTime,
          carId:that.data.carId,
          model:that.data.model,
          carUser:that.data.user,
          userNumber:that.data.tel,
          details:that.data.detail,
          picture:that.data.image,
          storeId:that.data.storeId,
          userId:App.globalData.openid
        },
        method:'POST',
        header:{
          "Content-Type": "application/x-www-form-urlencoded"
        },
        success:function(res){
          if(res.data.code==0){
            wx.showToast({
              title: '创建预约单成功！',
              icon:'success',
              duration:800
            })
            let pages = getCurrentPages();
            let prevPage = pages[pages.length-2];
            prevPage.setData({
              isRefresh:1
            })
            setTimeout(function(){
              wx.navigateBack({
                delta: 0,
              })
            },800)
          }else if(res.data.code==1){
            wx.showToast({
              title: res.data.data.errorMsg,
              icon:'error',
              duration:1000
            })
          }
        }
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },
  onShow:function(){
    let pages = getCurrentPages();
    let currPage = pages[pages.length - 1];
    var backData = currPage.__data__.mydata
    // 如果是提交状态返回isRefresh=1，才更新数据，从详情过来无需刷新
    if(backData&&backData.isRefresh==1){ 
      //获取数据     
      this.getData(backData)
      // 每一次需要清除，否则会参数会缓存
      currPage.__data__.mydata.isRefresh=''
    }
  }
})