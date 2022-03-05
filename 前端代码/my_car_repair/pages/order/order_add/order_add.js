var App = getApp()
Page({
  data: {
    goodsList:[],
    sumPrice:0,
    sumNumber:0,
    storeInfo:{},
    serviceType:["","汽车维修","汽车改装","轮胎服务","洗车美容","车辆保养"],
    isFromCart:false,
  },
  getSumNumber(list){
    var sumNumber = 0
    for(var i=0;i<list.length;i++){
      sumNumber+=list[i].number
    }
    this.setData({
      sumNumber:sumNumber
    })
  },
  getStoreDetail: function(storeId) {
    const that = this;
    wx.request({
      data:{
        storeId:storeId
      },
      url:"http://localhost:8080/car-repair/store/detail",
      method:'GET',
      success:function(res){
        that.setData({
          storeInfo:res.data.data
        })
      }
    })
  },
  getSumPrice(list){
    console.log(list)
    var sumPrice = 0 
    for(var i=0;i<list.length;i++){
      if(list[i].parts!=null){
        sumPrice+=list[i].parts.price*list[i].number
      }else{
        sumPrice+=list[i].service.price*list[i].number
      }
    }
    this.setData({
      sumPrice:sumPrice
    })
  },
  addOrder:function(){
    const that = this
    var goodsList = this.data.goodsList
    var orderDetailList = new Array()
    for(var i=0;i<goodsList.length;i++){
      var detail = {
        number:goodsList[i].number,
        price:(goodsList[i].parts?goodsList[i].parts.price:goodsList[i].service.price),
        parts:goodsList[i].parts,
        service:goodsList[i].service
      }
      orderDetailList.push(detail)
    }
    wx.request({
      data:{
        storeId:goodsList[0].storeId,
        userId:App.globalData.openid,
        userName:App.globalData.userInfo.nickName,
        price:that.data.sumPrice,
        orderDetailDTOList:orderDetailList
      },
      url: 'http://localhost:8080/car-repair/orders/add',
      method:'POST',
      success:function(res){
        if(res.statusCode==200){
          wx.navigateTo({
            url: '../add_success/add_success?orderId='+res.data.data.orderId+'&status='+true,
          })
        }else{
          wx.navigateTo({
            url: '../add_success/add_success?status='+false,
          })
        }
      }
    })
  },
  deleteCartDetail(){
    var that = this
    var list = this.data.goodsList
    for(var i=0;i<list.length;i++){
      wx.request({
        data:{
          userId:App.globalData.openid,
          cartDetailId:list[i].id
        },
        url: 'http://localhost:8080/car-repair/cart/delete',
        method:'POST',
        header:{
          "Content-Type": "application/x-www-form-urlencoded"
        },
        success:function(res){
          console.log(res.data.data)
        },
      })
    }
    
  },
  comfirmOrder:function(){
    wx.showToast({
      icon: 'loading',
      duration:800
      })
    this.addOrder()
    if(this.data.isFromCart){
      this.deleteCartDetail()
    }
    
  },
  onLoad: function (options) {
    var goodsList = JSON.parse(options.goodsList)
    this.setData({
      goodsList:goodsList,
      isFromCart:JSON.parse(options.isFromCart)
    })
    this.getStoreDetail(goodsList[0].storeId)
    this.getSumPrice(goodsList)
    this.getSumNumber(goodsList)
  },
})