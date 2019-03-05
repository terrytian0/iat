function dateFormatter(value, row, index) {
    return getDate(value);
}

function getDate(str){
    if(str==undefined){
        return "-";
    }
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth()+1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间
    return oTime;
};

function getApiService(authentication) {
    var content = getService(authentication);
    var items = "";
    $.each(content.list, function (i, item) {
        items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
    });
    $("#apiService").html(items);
}

function getKeywordService(authentication) {
    var content = getService(authentication);
    var items = "";
    $.each(content.list, function (i, item) {
        items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
    });
    $("#keywordService").html(items);
}

function getTestcaseService(authentication) {
    var content = getService(authentication);
    var items = "";
    $.each(content.list, function (i, item) {
        items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
    });
    $("#testcaseService").html(items);
}


function getTestplanService(authentication) {
    var content = getService(authentication);
    var items = "";
    $.each(content.list, function (i, item) {
        items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
    });
    $("#testplanService").html(items);
}


function getTesttaskService(authentication) {
    var content = getService(authentication);
    var items = "";
    $.each(content.list, function (i, item) {
        items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
    });
    $("#testtaskService").html(items);
}

function getService(authentication) {
    var content;
    var url = "/service/search?pageNumber=1&pageSize=100";
    $.ajax({
        type: "get",
        dataType: "json",
        url: url,
        async:false,
        contentType: "application/json; charset=utf-8",
        beforeSend:function (request) {
            request.setRequestHeader("Authentication", authentication);
        },
        success: function (msg) {
            if (msg.status) {
                content = msg.content;
            }else if(msg.code=="D0000104"){
                window.location.href = "/login.jsp";
            }else{
                swal({
                    title: "提示！",
                    text: msg.message,
                    type: "error"
                });
            }
        }
    });
    return content;
}




//补0操作
function getzf(num){
    if(parseInt(num) < 10){
        num = '0'+num;
    }
    return num;
}


function tips(message,obj) {
    layer.tips(message, obj, {
        tips: [1, '#3595CC'],
        time: 4000
    });
}


function tableHeight() {
    return $(window).height() - 200;
}

/**
 * 从Url中取参数
 * @param name
 * @returns {*}
 */
String.prototype.getQuery = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = this.substr(this.indexOf("\?") + 1).match(reg);
    if (r != null)
        return decodeURIComponent(r[2],"UTF-8");
    return null;
}

function error(xhr,exception) {
    if( xhr.status === 0)
        swal({
            title: "错误！",
            text: "Error : ' + xhr.status + 'You are not connected.",
            type: "error"
        });
    else if( xhr.status == "201")
        swal({
            title: "错误！",
            text: "Error : ' + xhr.status + '\nServer error.",
            type: "error"
        });
    else if( xhr.status == "404")
        swal({
            title: "错误！",
            text: "Error : ' + xhr.status + '\nPage note found",
            type: "error"
        });
    else if( xhr.status == "500")
        swal({
            title: "错误！",
            text: "Internal Server Error [500].",
            type: "error"
        });
    else if (exception === 'parsererror')
        swal({
            title: "错误！",
            text: "Error : ' + xhr.status + '\nImpossible to parse result.",
            type: "error"
        });
    else if (exception === 'timeout')
        swal({
            title: "错误！",
            text: "Error : ' + xhr.status + '\nRequest timeout.",
            type: "error"
        });
    else
        swal({
            title: "错误！",
            text: "Error .\n" + xhr.responseText,
            type: "error"
        });
}


function methodFormatter(value, row, index) {
    value = value.toUpperCase();
    switch (value) {
        case "POST":
            return "<font color='#23ad44'> "+value+"</font>";
        case "PUT":
            return "<font color='#f8ac59'> "+value+"</font>";
        case "DELETE":
            return "<font color='#ed5565'> "+value+"</font>";
        case "GET":
            return "<font color='#23c6c8'> "+value+"</font>";
        default:
            return "<font color='#262626'> "+value+"</font>";
    }
}






function statusFormatter(value, row, index) {
    if (row.status == undefined) {
        return;
    }
    if (row.status) {
        return "<a class=\" btn btn-w-m btn-success\">成功</a>";
    } else {
        return "<a class=\" btn btn-w-m btn-danger\" id = \"errormsg\">失败</a>";
    }
}

function testStatusFormatter(value, row, index) {
    if (row.status == undefined) {
        return;
    }
    if (row.status=="SUCCEED") {
        return "<a class=\" btn btn-w-m btn-success\">成功</a>";
    } else if(row.status=="FAILED"){
        return "<a class=\" btn btn-w-m btn-danger\" id = \"errormsg\">失败</a>";
    }else if(row.status == "RUNNING"){
        return "<a class=\" btn btn-w-m btn-default\">执行中</a>";
    }else if(row.status == "TIMEOUT"){
        return "<a class=\" btn btn-w-m btn-warning\">超时</a>";
    }else if(row.status == "INTERRUPT"){
        return "<a class=\" btn btn-w-m btn-warning\">中断</a>";
    }else if(row.status == "CREATE"){
        return "<a class=\" btn btn-w-m btn-default\">创建</a>";
    }else{
        return value;
    }
}

/**
 * 获取提起器
 * @param keywordApiId
 * @returns {*}
 */
function getExtractor(keywordApiId) {
    var content;
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/extractor/get?keywordApiId=" + keywordApiId,
        async: false,
        contentType: "application/json; charset=utf-8",
        beforeSend: function (request) {
            request.setRequestHeader("Authentication", $.cookie("Authentication"));
        },
        success: function (msg) {
            if (msg.status) {
                content = msg.content;
            } else if (msg.code == "D0000104") {
                window.location.href = "/login.jsp";
            } else {
                swal({
                    title: "提示！",
                    text: msg.message,
                    type: "error"
                });
            }
        }
    });
    return content;
}

/**
 * 获取断言
 * @param keywordApiId
 * @returns {*}
 */
function getAssert(keywordApiId) {
    var content;
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/assert/get?keywordApiId=" + keywordApiId,
        async: false,
        contentType: "application/json; charset=utf-8",
        beforeSend: function (request) {
            request.setRequestHeader("Authentication", $.cookie("Authentication"));
        },
        success: function (msg) {
            if (msg.status) {
                content = msg.content;
            } else if (msg.code == "D0000104") {
                window.location.href = "/login.jsp";
            } else {
                swal({
                    title: "提示！",
                    text: msg.message,
                    type: "error"
                });
            }
        }
    });
    return content;
}


