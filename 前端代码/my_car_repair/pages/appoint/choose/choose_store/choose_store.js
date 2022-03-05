Page({
  data: {
    name: "",
    storeList:[]
  },
  getStoreList: function() {
    const that = this;
    wx.request({
      url:"http://localhost:8080/car-repair/store/all",
      method:'GET',
      success:function(res){
        that.setData({
          storeList:res.data.data
        })
      }
    })
  },
  search: function () {
    var name = this.data.name
    this.searchStore(name)
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
        console.log("storeList",res.data.data)
        that.setData({
          storeList: res.data.data
        })
      },
    })
  },
  getText: function(e) {
    this.data.name = e.detail.value
  },
  chooseStore:function(view){
    let pages = getCurrentPages()
    let prevPage = pages[pages.length-2];
    prevPage.setData({
      mydata: { 
        isRefresh: 1 ,
        storeInfo:{
          storeName:view.currentTarget.dataset.storename,
          storeId:view.currentTarget.dataset.storeid
        }
      }
    })
    wx.navigateBack({
      delta: 1,
    })
    
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
    this.getStoreList()
  }
});
