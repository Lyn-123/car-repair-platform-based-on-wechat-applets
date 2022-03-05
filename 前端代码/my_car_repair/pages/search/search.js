var app = getApp()
var util = require('../../utils/util');

Page({
  data: {
    name: "",
    partList:[],
    serviceList:[],
    storeList:[]
  },
  
  search: function () {
    var name = this.data.name
    this.searchService(name)
    this.searchParts(name)
    this.searchStore(name)
  },
  searchService:function(name){
    const that = this
    wx.request({
      data: {
        serviceName:name
      },
      url:"http://localhost:8080/car-repair/service/find",
      method:'GET',
      success: function(res){
        that.setData({
          serviceList: res.data.data
        })
      },
    })
  },
  searchParts:function(name){
    const that = this
    wx.request({
      data: {
        partsName:name
      },
      url:"http://localhost:8080/car-repair/parts/find",
      method:'GET',
      success: function(res){
        that.setData({
          partList: res.data.data
        })
      },
    })
  },
  searchStore:function(name){
    const that = this
    wx.request({
      data: {
        storeName:name
      },
      url:"http://localhost:8080/car-repair/store/find",
      method:'GET',
      success: function(res){
        that.setData({
          storeList: res.data.data
        })
      },
    })
  },

  getText: function(e) {
    this.data.name = e.detail.value
  },

  onReachBottom: function() {
  	if (this.isNextPage) {
		this.searchWelfare(false)
  	};
  },

  onShareAppMessage: function() {
     return {
       title:'搜索',
       path:'pages/search/search'
      }
 	},
  /**
   * 入口
   */
  onLoad: function (option) {
    
  }
});
