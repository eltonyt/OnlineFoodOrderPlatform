
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
    return false
  } else {
    return true
  }
}

function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please Enter Username"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("Username Length must between 3-20 characters"))
  } else {
    callback()
  }
}

function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Please Enter Employee Name"))
  } else if (value.length > 12) {
    callback(new Error("Employee Name must be shorter than 12 characters"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  if (value == "") {
    callback(new Error("Please Enter Cell Phone Number"))
  } else if (!isCellPhone(value)) {//引入methods中封装的检查手机格式的方法
    callback(new Error("Please Enter Correct Cell Phone Number"))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {
  let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if(value == '') {
    callback(new Error('Please Enter ID'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('Id in wrong format'))
  }
}