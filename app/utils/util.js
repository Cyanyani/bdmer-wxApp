const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const isNull = (o) => {
    if (o === undefined || o === "" || o === {} || o === null) {
        return true;
    }
    return false;
}

const isToken = (res) => {
    if(isNull(res)){
        return false;
    }
    if(res.code === -2 || res.code === 2003 || res.code === 2004){
        console.log(res.code)
        return false;
    }
    return true;
}

const formatParamDTO = o =>{
    let data = {};
    if(isNull(o)){
        data.data = "";
    }else{
        data.data = JSON.stringify(o);
    }

    return data;
}

const formatGETUrl = (url , data) => {
    if(data === undefined){
        return url;
    }
    
    let paramNameList = Object.getOwnPropertyNames(data);
    for (let i = 0; i < paramNameList.length; ++i){
        if(i === 0 ){
            url = url + "?" + paramNameList[i] + "=" + data[paramNameList[i]];
        }else{
            url = url + "&" + paramNameList[i] + "=" + data[paramNameList[i]];
        } 
    }

    return url;
}

const formatTelNumber = (telNumber) => {
    if (isNull(telNumber)){
        return "未绑定";
    }
    return telNumber ? (telNumber.substr(0, 3) + "****" + telNumber.substr(7, 4)) : "";
}

module.exports = {
    formatTime: formatTime,
    formatParamDTO: formatParamDTO,
    formatGETUrl: formatGETUrl,
    formatTelNumber: formatTelNumber,
    isNull: isNull,
    isToken: isToken
}
