var App =getApp()
Page({
  data: {
    isshoucang:false,
    part:[],
    openAttr:false,
  },
  addOrder(){
    console.log(this.data.part)
    var goodsList =[{
      number: 1,
      parts: {
        details: this.data.part.details,
        model: this.data.part.model,
        partsId: this.data.part.partsId,
        partsName: this.data.part.partsName,
        picture: this.data.part.picture,
        price: this.data.part.price,
        stock: this.data.part.stock,
        storeId: this.data.part.store.storeId
      },
      service:null,
      storeId: this.data.part.store.storeId,
      userId: App.globalData.openid,
    }]
    let jsonList = JSON.stringify(goodsList);
    wx.navigateTo({
      url: '../../order/order_add/order_add?isFromCart=false&goodsList='+jsonList,
    })
  },
  addCart(){
    const that = this
    console.log(this.data.part)
    wx.request({
      data:{
        number:1,
        storeId:that.data.part.store.storeId,
        userId:App.globalData.openid,
        parts:{
          partsId:that.data.part.partsId,
          partsName:that.data.part.partsName,
          model:that.data.part.model,
          price:that.data.part.price,
          stock:that.data.part.stock,
          details:that.data.part.details,
          picture:that.data.part.picture,
          storeId:that.data.part.store.storeId
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
  getPartDetail(opt){
    const that = this
    wx.request({
      data:{
        partsId:opt
      },
      url: 'http://localhost:8080/car-repair/parts/detail',
      method:'GET',
      success:function(res){
        that.setData({
          part:res.data.data
        })
      }
    })

  },
  buyNow:function(){
    this.setData({
      openAttr: !this.data.openAttr
    });
  },
  onLoad: function (options) {
    this.getPartDetail(options.partsId)
  },
})