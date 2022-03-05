const App = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    image:"../../../images/tx04.png",
    hasUserInfo:false,
    userInfo:null
  },
  imgUpload(){  //上传头像
    const that = this
    wx.chooseImage({
      success: function(res) {
      var tempFilePaths = res.tempFilePaths
      var tempFilesSize = res.tempFiles[0].size
      if (tempFilesSize >= 2097152) {
        wx.showToast({
          title: '图片不能大于2M',
          icon: 'error',
          duration: 1000
        })
      }else{
        wx.uploadFile({
          url: 'http://localhost:8080/car-repair/image/update', 
          filePath: tempFilePaths[0],
          name: 'file',
          success(res) {
            let objRes = JSON.parse(res.data);
            let url = 'http://localhost:8080/car-repair'+objRes.data;
            that.setData({
              image:url
            })
            wx.request({
              url:"http://localhost:8080/car-repair/user/update/image",
              data:{
                id:App.globalData.openid,
                image:that.data.image
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
                  duration: 1000
                })
              }
            })  
          },
        }); 
      } 
    },
  })
},
  pageTo({currentTarget: {dataset}}){
    wx.navigateTo({
      url: dataset.url
    })
  },
  onShow:function(){
    App.getUserInfo(data_userinfo => {
			this.setData({
        hasUserInfo:App.globalData.hasUserInfo,
        userInfo: data_userinfo,
        image:data_userinfo.avatarUrl
			})
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.onShow()
  },
})
