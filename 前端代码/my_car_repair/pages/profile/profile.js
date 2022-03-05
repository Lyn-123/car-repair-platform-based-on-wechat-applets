const App = getApp()

Page({
	data: {
		hasUserInfo: false,
		userInfo: null,
		items: [
			{
				icon: '../../images/iconfont-order.png',
				text: '我的爱车',
				path: '/pages/car_list/car_list'
			}, 
			{
				icon: '../../images/iconfont-kefu.png',
				text: '我的预约维修单',
				path: '../appoint/appoint_list/appoint_list',
			}, 
			{
				icon: '../../images/iconfont-help.png',
				text: '我的预约保养单',
				path: '../order/order_list/order_list',
			},
			{
				icon: '../../images/iconfont-kefu.png',
				text: '我的维修单',
				path: '../repair/repair_list/repair_list',
			}, 
		],
	},
	getUserInfo() {
		this.setData({
			hasUserInfo : App.globalData.hasUserInfo
		})
		App.getUserInfo(data_userinfo => {
			this.setData({
				userInfo: data_userinfo
			})
		})
	},
	getUserProfile(){
		const that = this
		wx.getUserProfile({
			desc: '用于完善会员资料', 
			success: (res) => {
				that.setData({
					hasUserInfo : true,
					userInfo : res.userInfo,
				})	
				const user_send = that.data.userInfo
				wx.request({
					data:{
						id:App.globalData.openid,
						userName:user_send.nickName,
						sex:user_send.gender,
						image:user_send.avatarUrl
					},
					url:"http://localhost:8080/car-repair/user/register",
					method:'POST',
					header: {
						'content-type': 'application/json'
					}
				})
			}
		})
	},
	onShow(){
		this.getUserInfo()
	},	
	onLoad() {
		this.getUserInfo()
	},
})