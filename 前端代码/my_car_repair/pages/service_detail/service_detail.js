const App = getApp()
Page({
  data: {
    type:["","汽车维修","汽车改装","轮胎服务","洗车美容","车辆保养"],
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
  },
  shoucang() {
    this.setData({
      isshoucang:!this.data.isshoucang
    })
    console.log(this.data.isshoucang);
  },
  addOrder(){
    console.log(this.data.service)
    var serviceType = this.data.service.serviceType
    var type = this.data.type
    var index = 0
    for(var i=0;i<type.length;i++){
      if(type[i]==serviceType){
        index = i
        break
      }
    }
    var goodsList =[{
      number: 1,
      parts: null,
      service:{
        price: this.data.service.price,
        rating: this.data.service.rating,
        serviceId: this.data.service.serviceId,
        serviceName: this.data.service.serviceName,
        serviceType: index,
        storeId: this.data.service.store.storeId
      },
      storeId: this.data.service.store.storeId,
      userId: App.globalData.openid,
    }]
    let jsonList = JSON.stringify(goodsList);
        wx.navigateTo({
          url: '../order/order_add/order_add?isFromCart=false&goodsList='+jsonList,
        })
  },
  addCart(){
    wx.request({
      data:{
        number:1,
        storeId:this.data.service.store.storeId,
        userId:App.globalData.openid,
        service:{
          serviceId:this.data.service.serviceId,
          serviceName:this.data.service.serviceName,
          price:this.data.service.price,
          storeId:this.data.service.store.storeId,
          rating:this.data.service.rating
        }
      },
      url: 'http://localhost:8080/car-repair/cart/add',
      method:'POST',
      success:function(res){
        console.log(res.data)
        wx.showToast({
          title: '成功添加购物车',
          icon:'success',
          duration:1000
        })
      }
    })
  },
  getAddress() {
    let latitude=wx.getStorageSync('latitude')
    let longitude=wx.getStorageSync('longitude')
    console.log(latitude,longitude);
    if(latitude == '' || longitude == '') {
      // wx.getLocation({
      //   type: 'wgs84',
      //   success(res) {
      //     const latitude = res.latitude
      //     const longitude = res.longitude
      //     const speed = res.speed
      //     const accuracy = res.accuracy
      //     wx.setStorageSync('latitude', latitude)
      //     wx.setStorageSync('longitude', longitude)
      //   }
      // })
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
      }
    })
  },
  goToCustomerCare(){

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (option) {
    this.getServiceDetail(option.id);
  }
})
