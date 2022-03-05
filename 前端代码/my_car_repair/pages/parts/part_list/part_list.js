Page({
  data: {
    partList:[]
  },
  getPartList(){
    const that = this
    wx.request({
      url: 'http://localhost:8080/car-repair/parts/all',
      method:'GET',
      success:function(res){
        that.setData({
          partList:res.data.data
        })
      }
    })
  },
  onLoad: function (options) {
    this.getPartList()
  },
})