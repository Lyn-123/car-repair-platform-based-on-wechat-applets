function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function formatTime(time) {
  var date = new Date(time);
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function getDate(time) {
  var date = new Date(time);
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  return [year, month, day].map(formatNumber).join('-')
}

function getTime(time) {
  var date = new Date(time);
  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()
  return [hour, minute, second].map(formatNumber).join(':')
}

module.exports = {
  formatTime,
  formatNumber
}