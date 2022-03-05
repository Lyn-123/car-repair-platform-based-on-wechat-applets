var App = getApp()
var time2String = require('../../../utils/time2String');
var util = require('../../../utils/util')

Page({
  data:{
    appointList:[],
    page: 1,
    size: 10,
    loadmoreText: '正在加载更多数据',
    nomoreText: '全部加载完成',
    nomore: false,
    totalPages: 1
  },
  getAppointList(){
    const that = this
    App.getOpenId().then(
      res=>{
        wx.request({
          url: 'http://localhost:8080/car-repair/appoint/list',
          data:{
            userId:res 
          },
          method:'GET',
          success:function(res){
            if(res.data.code==0){
              var appointList=res.data.data.reverse()
              appointList.forEach(function(e){
                e.time=time2String.formatTime(e.time)
                e.appointTime=time2String.formatTime(e.appointTime)
              })
              that.setData({
                appointList:appointList
              })
              console.log(res.data.data)
            }
          }
        })
      }
    ) 
  },
  addAppoint(){
    wx.navigateTo({
      url: '../appoint_add/appoint_add',
    })
  },
  goToRepairDetail:function(view){
    var repairId = util.myTrim(view.currentTarget.dataset.repairid)
    if(!repairId){
      wx.showToast({
        title: '暂无对应维修单',
        icon:'error',
        duration:1000
      })
    }else{
      wx.navigateTo({
        url:"../../repair/repair_detail/repair_detail?repairId="+repairId,
      })
    }
  },
  onLoad:function(options){
    this.getAppointList()
  },
  onShow(){
    let pages = getCurrentPages();
    let currPage = pages[pages.length-1];
    var isRefresh = currPage.__data__.isRefresh
    if(isRefresh&&isRefresh==1){
      this.getAppointList()
    }
  }
  /**
   * 页面上拉触底事件的处理函数
   **/
  // onReachBottom: function () {
  //   this.getOrderList()
  // },

})