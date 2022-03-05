Page({
  data: {
    status: false,
    orderId: 0
  },
  onLoad: function (options) {
    console.log(options)
    this.setData({
      orderId: options.orderId,
      status: JSON.parse(options.status)
    })
    console.log(this.data)
  },
})