App({
  systemInfo: null ,
  onLaunch() {
    const that = this;
    wx.getSystemInfo({
      success(res) {
        that.systemInfo = res;
      },
    });
    this.getOpenId()
  },
  getOpenId:function(cb){
    return new Promise((resolve,reject)=>{
      const that = this
      if(that.globalData.openid){
        typeof cb == "function" && cb(that.globalData.openid)
        resolve(that.globalData.openid)
      }else{
        wx.login({
          success: function (token) {
            var code = token.code
            wx.request({
              url: 'http://localhost:8080/car-repair/user/wxLogin?',
              data:{
                'code':code
              },
              method:'GET',
              success: function(result){
                that.globalData.openid = result.data.data
                resolve(result.data.data)
              },
              fail:function(errInfo){
                reject(errInfo) 
              }
            })
          }
        })
      }
    })
  },
  getUserInfo:function(cb){
    var that = this
    if(that.globalData.hasUserInfo&&that.globalData.isLastest){
      typeof cb == "function" && cb(that.globalData.userInfo)
    }else{
      this.getOpenId().then(res=>{
        wx.request({
          url: 'http://localhost:8080/car-repair/user/login?',
          data:{
            openid:res
          },
          method:'GET',
          success: function(result){
            that.globalData.isLastest = true
            var code_send = result.data.code
            if(code_send==0){
              that.globalData.hasUserInfo = true
              var user_send = result.data.data
              that.globalData.userInfo = {
                avatarUrl:user_send.image,
                nickName:user_send.userName,
                gender:user_send.sex,
                address:user_send.address,
                tel:user_send.tel,
              }
            }
            if(code_send==2){
              that.globalData.hasUserInfo = false
            }
          },
          fail: function(errInfo) { 
            console.info(errInfo)
          }
        })
      })
      
    }
  },
  updateUserInfo(){
    this.globalData.isLastest=false
    this.getUserInfo()
  },
  globalData:{
    userInfo:null,
    hasUserInfo: false,
    openid:null,
    isLastest:false
  }
});
