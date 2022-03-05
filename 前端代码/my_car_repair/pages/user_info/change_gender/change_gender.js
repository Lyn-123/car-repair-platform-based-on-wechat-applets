const App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    gender:0
  },
  change_gender(e){
    console.log(e.currentTarget.dataset.gender)
    this.setData({
      gender:e.currentTarget.dataset.gender
    })
  },
  update_gender(){
    const that = this
    wx.request({
      url: 'http://localhost:8080/car-repair/user/update/sex',
      data:{
        sex:that.data.gender,
        id:App.globalData.openid
      },
      method:'POST',
      header:{
        'content-type': 'application/x-www-form-urlencoded'
      },
      success(){
        App.updateUserInfo()
        wx.showToast({
          title: '修改成功',
          icon: 'success',
          duration: 800
        })
        setTimeout(function(){
          wx.navigateBack({
            delta: 0,
          })
        },800)
      }
    })

  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      gender:options.gender
    })
  },
})