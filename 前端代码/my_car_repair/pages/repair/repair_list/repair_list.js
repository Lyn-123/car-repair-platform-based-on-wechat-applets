
var App = getApp();

Page({
  data: {
    repairList:[],
    serviceType:["","汽车维修","汽车改装","轮胎服务","洗车美容","车辆保养"],
    states:["待维修","维修中","待提车","已结束","取消订单"],
    sumPrice:[]
  },
  getOrderList(){
    const that = this
    wx.request({
      data:{
        userId:App.globalData.openid
      },
      url: 'http://localhost:8080/car-repair/repair/list',
      method:'GET',
      success:function(res){
        that.setData({
          repairList:res.data.data
        })
        that.getSumPrice(res.data.data)
      }
    })
  },
  getSumPrice(list){ 
    for(var i=0;i<list.length;i++){
      var detail_list = list[i].repairDetailDTOList
      var sum_price = 0
      for(var j=0;j<detail_list.length;j++){
        sum_price = parseFloat(sum_price)+detail_list[j].number*detail_list[j].price
        sum_price=parseFloat(sum_price).toFixed(2)
      }
      this.data.sumPrice.push(sum_price)
    }
    this.setData({
      sumPrice:this.data.sumPrice
    })
  },
  onShow: function (options) {
    this.getOrderList()
  },
  onLoad(){
    
  }
})