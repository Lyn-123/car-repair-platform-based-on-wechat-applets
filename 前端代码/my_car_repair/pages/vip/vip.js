var app = getApp();
var dialog = require("../../utils/dialog.js")
var Util = require("../../utils/util.js");
var api = require("../../utils/api.js");

Page({
    onLoad: function (options) {
        this.getList(1);
    },

    getList:function(type){
    	dialog.loading();
		var that = this;

	    api.getVIPInfo({
	      data: {
	        viplev: type
	      },
	      success: (res) => {
	      	dialog.hide();
	      	that.data.vipList = res.data.result;
	        that.setData(that.data);
	      },
	      fail: (res) => {
	      	dialog.toast("网络出错了~");
	      }
	    });
    },

    //点击某一个title条
  changeType:function(e){
    var type = e.currentTarget.dataset.value
    if(type == this.data.currentType){
      return;
    }
    this.setData({currentType:type})
    this.data.currentType = type;
    this.getList(type)
  },

  buyVIP:function(view){
	var vipId = e.currentTarget.dataset.value;
	wx.showModal({
	    title: '提示',
	    content: '小程序目前暂不支持在线支付，请到APP完成支付',
	    confirmColor: '#3aacb1',
	    success: function(res) {
	        
	    }
	})
  },

 onShareAppMessage: function() {
     return {
       title:'云车VIP',
       path:'pages/vip/vip'
     }
 },

  data: {
    vipList:[],
    types:[{
    	value:1,
    	title:"VIP(5座)"
    },
    {
    	value:2,
    	title:"VIP(7座)"
    },
    {
    	value:3,
    	title:"SVIP(5座)"
    },
    {
    	value:4,
    	title:"SVIP(7座)"
    }],
    currentType:1, 
  }
})
