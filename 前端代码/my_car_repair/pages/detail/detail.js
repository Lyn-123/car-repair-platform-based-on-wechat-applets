// pages/detail/detail.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    service:{
      price: "",
      rating: "",
      serviceId: "",
      serviceName: "",
      serviceType: "",
      store:{
        address: "",
        storeDetail: "",
        storeId: "",
        storeName: "",
        storePicture: "",
        tel: ""
      }
    },
    isshoucang:false,
    info:{
      name:'保利中心',
      title:'房源副标题副标题副标题',
      number:'80-100m²',
      list:[
        {name:'普通1',state:0},
        {name:'普通2',state:1},
        {name:'普通3',state:2}
      ],
      Commission:'总价*0.8%',
      money:'21000/平',
      mianji:'70-90m²',
      yongtu:'普通',
      tese:'教育'
    }
  },
  shoucang() {
    this.setData({
      isshoucang:!this.data.isshoucang
    })
    console.log(this.data.isshoucang);

  },
  getAddress() {
    let latitude  =    wx.getStorageSync('latitude')
    let longitude  = wx.getStorageSync('longitude')
    console.log(latitude,longitude);
    
    if(latitude == '' || longitude == '') {
      wx.getLocation({
        type: 'wgs84',
        success(res) {
          const latitude = res.latitude
          const longitude = res.longitude
          const speed = res.speed
          const accuracy = res.accuracy
          wx.setStorageSync('latitude', latitude)
          wx.setStorageSync('longitude', longitude)
        }
      })
      wx.openSetting({
        success: function (dataAu) {
          console.log(dataAu)
          if (dataAu.authSetting["scope.userLocation"] == true) {
            wx.showToast({
              title: '授权成功',
              icon: 'success',
              duration: 1000
            })
            //再次授权，调用getLocationt的API
            // that.getLocation(that);
            wx.getLocation({
              type: "wgs84",
              success(res) {
                const latitude = res.latitude;
                const longitude = res.longitude;
                const speed = res.speed;
                const accuracy = res.accuracy;
                wx.setStorageSync("latitude", latitude);
                wx.setStorageSync("longitude", longitude);
    
              }
            });
          } else {
            wx.showToast({
              title: '授权失败',
              icon: 'success',
              duration: 1000
            })
          }
        }
      })
    } else {
      wx.openLocation({
        latitude,
        longitude,
        scale: 18
      })
    }
    // let latitude  =    parseFloat (wx.getStorageSync('latitude'))
    // let longitude  = parseFloat (wx.getStorageSync('longitude'))
  
    // wx.getLocation({//获取当前经纬度
    //   type: 'wgs84', //返回可以用于wx.openLocation的经纬度，官方提示bug: iOS 6.3.30 type 参数不生效，只会返回 wgs84 类型的坐标信息
    //   success: function (res) {
    //     wx.openLocation({//​使用微信内置地图查看位置。
    //       latitude: 22.5542080000,//要去的纬度-地址
    //       longitude: 113.8878770000,//要去的经度-地址
    //       name: "宝安中心A地铁口",
    //       address:'宝安中心A地铁口'
    //     })
    //   }
    // })
  },
  //预览轮播图
  previewImg(e){
    wx.previewImage({
      current: e.currentTarget.dataset.url, // 当前显示图片的http链接
      urls: this.data.imgUrls // 需要预览的图片http链接列表
    })
  },
  getServiceDetail(opt){
    const that = this
    wx.request({
      data:{
        serviceId:opt
      },
      url: 'http://localhost:8080/car-repair/service/detail',
      method:'GET',
      success:function(res){
        that.setData({
          service:res.data.data
        })
        console.log(that.data.service)
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.getServiceDetail(option.id);
    this.getServiceDetail("1");
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
