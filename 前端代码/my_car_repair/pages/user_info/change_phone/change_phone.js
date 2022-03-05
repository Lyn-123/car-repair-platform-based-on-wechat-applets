// pages/user_info/change_name/change_name.js
const App = getApp()
Page({
  data: {
    tel:null,
    Number:null,
  },
  myTrim(str){
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
  },
  getTel(view){
    var text = this.myTrim(view.detail.value)
    this.setData({
      tel:text
    })
  },
  getNumber(view){
    var text = this.myTrim(view.detail.value)
    this.setData({
      number:text
    })
  },
  checkTel(){
    var flag = true
    if(!this.data.tel){
      wx.showToast({
        title: '手机号不能为空！',
        icon:'error'
      })
      flag =  false
    }
    return flag
  },
  checkNumber(){
    var flag =true
    if(!this.data.number){
      wx.showToast({
        title: '验证码不能为空！',
        icon:'error'
      })
      flag =  false
    }else{
      if(this.data.number!='1234'){
        wx.showToast({
          title: '验证码错误！',
          icon:'error'
        })
        flag = false
      }
    }
    return flag
  },
  update_tel(){
    if(this.checkTel()&&this.checkNumber()){
      wx.request({
        url:"http://localhost:8080/car-repair/user/update/tel",
        data:{
          id:App.globalData.openid,
          tel:this.data.tel
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
    
  },
  

})