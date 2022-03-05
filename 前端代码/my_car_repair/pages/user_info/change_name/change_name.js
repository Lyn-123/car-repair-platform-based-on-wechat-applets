// pages/user_info/change_name/change_name.js
const App = getApp()
Page({
  data: {
    new_name:null,
    old_name:null
  },
  myTrim(str){
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
  },
  getText(view){
    var text = this.myTrim(view.detail.value)
    this.setData({
      new_name:text
    })
  },
  update_name(){
    console.log(this.data.new_name)
    if(!this.data.new_name){
      wx.showToast({
        title: '昵称不能为空！',
        icon:'error'
      })
    }else{
      wx.request({
        url:"http://localhost:8080/car-repair/user/update/userName",
        data:{
          id:App.globalData.openid,
          userName:this.data.new_name
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
      old_name:options.userName
    })
  },
  

})