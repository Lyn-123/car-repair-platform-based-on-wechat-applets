// pages/user_info/change_name/change_name.js
const App = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    new_address:null,
    old_address:null
  },
  myTrim(str){
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
  },
  getText(view){
    var text = this.myTrim(view.detail.value)
    this.setData({
      new_address:text
    })
  },
  update_address(){
    if(!this.data.new_address){
      wx.showToast({
        title: '地址不能为空！',
        icon:'error'
      })
    }else{
      wx.request({
        url:"http://localhost:8080/car-repair/user/update/address",
        data:{
          id:App.globalData.openid,
          address:this.data.new_address
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
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      old_address:options.address
    })
  },
  

})