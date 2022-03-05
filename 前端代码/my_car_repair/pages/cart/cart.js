let App = getApp()
Page({
  data: {
    userId:"",
    cartGoods:[],
    serviceType:["","汽车维修","汽车改装","轮胎服务","洗车美容","车辆保养"],
    isEditCart:false,
    checkList:[],
    checkedAllStatus:false,
    checkCount:0,
    checkAmount:0,
    show:true,
    showModal:false,
    showModelNumber:0,
    showModelId:0,
  },
  getCartInfo:function(){
    const that = this
    App.getOpenId().then(openId=>{
      that.setData({
        userId:openId
      })
      wx.request({
        data:{
          userId:openId
        },
        url: 'http://localhost:8080/car-repair/cart/my',
        method:'GET',
        success:function(res){
          that.setData({
            cartGoods:res.data.data
          })
          that.GetCheckList(that.data.cartGoods)
        }
      })
    })
  },
  GetCheckList:function(list){
    var array = new Array()
    for(var i=0;i<list.length;i++){
      array.push(new Array(list[i].cartDetailDTOList.length).fill(false))
    }
    this.setData({
      checkList:array
    })
  },
  checkedItem:function(view){
    var i = view.currentTarget.dataset.i
    var j = view.currentTarget.dataset.j
    var goods = this.data.cartGoods
    var list = this.data.checkList
    var flag = this.data.checkedAllStatus
    var count = 0
    if(list[i][j]){
      list[i][j]=false
    }else{
      list[i][j]=true
    }
    for(var x=0;x<list.length;x++){
      for(var y=0;y<list[x].length;y++){
        flag = flag&&list[x][y]
        list[x][y]?count+=goods[x].cartDetailDTOList[y].number:'';
      }
    }
    this.setData({
      checkList:list,
      checkedAllStatus:flag,
      checkCount:count,
    })
    this.getCheckAmount()
    
  },
  checkedAll:function(){
    if(this.data.checkedAllStatus){
      this.data.checkedAllStatus = false
      for(var i=0;i<this.data.checkList.length;i++){
        this.data.checkList[i].fill(false)
      }
      this.data.checkCount=0
    }else{
      this.data.checkedAllStatus = true
      for(var i=0;i<this.data.checkList.length;i++){
        this.data.checkList[i].fill(true)
      }
      var num = 0
      var list = this.data.cartGoods
      var flag = this.data.checkList
      for(var i=0;i<list.length;i++){
        var inList = list[i].cartDetailDTOList
        for(var j=0;j<inList.length;j++){
          if(flag[i][j]==true){
              num+=inList[j].number
          }
        }
      }
      this.data.checkCount=num
    }
    this.setData(this.data)
    this.getCheckAmount()
  },
  getCheckAmount(){
    var list = this.data.cartGoods
    var flag = this.data.checkList
    var amount = 0
    for(var i=0;i<list.length;i++){
      var inList = list[i].cartDetailDTOList
      for(var j=0;j<inList.length;j++){
        if(flag[i][j]==true){
          if(inList[j].parts){
            amount+=inList[j].parts.price*inList[j].number
          }else if(inList[j].service){
            amount+=inList[j].service.price*inList[j].number
          }
        }
      }
    }
    this.setData({
      checkAmount:amount
    })
    console.log("list",list)
    console.log("count",amount)
  },
  goToStore:function(view){
    var storeId = view.currentTarget.dataset.storeid
    wx.navigateTo({
      url: '../store_detail/store_detail?id='+storeId,
    })
  },
  goToGoodsDetail:function(view){
    var data = view.currentTarget.dataset
    if(data.partid){
      wx.navigateTo({
        url:'../parts/part_detail/part_detail?partsId='+data.partid
      })
    }else if(data.serviceid){
      wx.navigateTo({
        url: '../service_detail/service_detail?id='+data.serviceid,
      })
    }
  },
  editCart:function(){
    if(this.data.isEditCart){
      this.data.isEditCart=false
    }else{
      this.data.isEditCart=true
    }
    this.setData(this.data)
  },
  openChange: function(view) {
    var id = view.currentTarget.dataset.id
    var number = view.currentTarget.dataset.number
    this.setData({
      showModal: true,
      showModelNumber:number,
      showModelId:id
    })
  },
  updateNumber:function(id,number){
    const that = this
    wx.request({
      data:{
        cartDetailId:id,
        number:number
      },
      url: 'http://localhost:8080/car-repair/cart/update',
      method:'POST',
      header:{
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success:function(res){
        that.onShow()
      }
    })
  },
  hideModal: function() {
    this.setData({
       showModal: false
     });
   },
   onCancel: function() {
    this.hideModal()
   },
   onConfirm: function() {
     var id = JSON.parse(this.data.showModelId)
     var number = JSON.parse(this.data.showModelNumber)
     console.log(id,number)
     this.updateNumber(id,number)
     this.hideModal()
     this.onShow()
  },
   inputChangename: function(e) {
     this.setData({
      showModelNumber:e.detail.value
     })
   },
   //增加数量
   addNumber(view){
    var id = view.currentTarget.dataset.id
    var number = view.currentTarget.dataset.number
    this.updateNumber(id,number+1)
   },
   cutNumber(view){
    var id = view.currentTarget.dataset.id
    var number = view.currentTarget.dataset.number
    this.updateNumber(id,number-1)
   },
   getGoodsList:function(){
    var goodsList = new Array()
    var cartGoods = this.data.cartGoods
    var checkList = this.data.checkList
    for(var i=0;i<checkList.length;i++){
      for(var j=0;j<checkList[i].length;j++){
        if(checkList[i][j]==true){
         goodsList.push(cartGoods[i].cartDetailDTOList[j])
        }
      }
    }
    return goodsList
   },
   checkoutOrder:function(){
     var goodsList = this.getGoodsList()
     if(goodsList.length==0){
       wx.showToast({
         title: '请选择商品',
         icon:'error',
         duration:1000
       })
     }else{
      var storeId = goodsList[0].storeId
      for(var k=1;k<goodsList.length;k++){
       if(storeId!=goodsList[k].storeId){
         break;
       }
      }
      if(k<goodsList.length){
        wx.showToast({
          title: '订单限一家店铺',
          icon:'error',
          duration:1000
        })
      }else{
        let jsonList = JSON.stringify(goodsList);
        wx.navigateTo({
          url: '../order/order_add/order_add?isFromCart=true&goodsList='+jsonList,
        })
      }
     }
   },
   deleteCart:function(){
     const that = this
    var goodsList = this.getGoodsList()
    if(goodsList.length==0){
      wx.showToast({
        title: '请选择商品',
        icon:'error',
        duration:1000
      })
    }else{
      for(var i=0;i<goodsList.length;i++){
        wx.request({
          data:{
            userId:that.data.userId,
            cartDetailId:goodsList[i].id
          },
          url: 'http://localhost:8080/car-repair/cart/delete',
          method:'POST',
          header:{
            "Content-Type": "application/x-www-form-urlencoded"
          },
          success:function(res){
            wx.showToast({
              title: '删除成功',
              icon: 'success',
              duration: 800
            })
            that.onShow(true)
          },
          fail:function(res){
            console.log(res)
          }
        })
      }
    }
   },
  onShow: function (flag) {
    if(!flag){
      wx.showToast({
        icon:'loading',
        duration:2500
      })
    }
    this.getCartInfo();
    this.setData({
      checkCount:0,
      checkAmount:0,
    })
  },
})