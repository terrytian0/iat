function dateFormatter(value, row, index) {
    return getDate(value);
}

function getDate(str){
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

function getService(authentication) {
    var url = "/service/search?pageNumber=1&pageSize=100";
    $.ajax({
        type: "get",
        dataType: "json",
        url: url,
        async:false,
        contentType: "application/json; charset=utf-8",
        beforeSend:function (request) {
            request.setRequestHeader("Authentication",authentication);
        },
        success: function (msg) {
            if (msg.status) {
                var items = "";
                $.each(msg.content.list, function (i, item) {
                    items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
                });
                $("#service").html(items);
            }else if(msg.code=="D0000104"){
                window.location.href = "/login.jsp";
            }else{
                swal({
                         title: "提示！",
                         text: msg.errorMsg,
                         type: "error"
                     });
            }
        }
    });
}

function getKeywordService(authentication) {
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
                var items = "";
                $.each(msg.content.list, function (i, item) {
                    items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
                });
                $("#keywordService").html(items);
            }else if(msg.code=="D0000104"){
                window.location.href = "/login.jsp";
            }else{
                swal({
                    title: "提示！",
                    text: msg.errorMsg,
                    type: "error"
                });
            }
        }
    });
}

function getTestcaseService(authentication) {
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
                var items = "";
                $.each(msg.content.list, function (i, item) {
                    // items = items + "<li><a onclick=\"shows($(this).text())\">"+item.name+"</a></li>";
                    items = items + "<option key='"+item.id+"'>"+item.name+"</option>";
                });
                $("#testcaseService").html(items);
            }else if(msg.code=="D0000104"){
                window.location.href = "/login.jsp";
            }else{
                swal({
                    title: "提示！",
                    text: msg.errorMsg,
                    type: "error"
                });
            }
        }
    });
}



function getDate(str){
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
            return "<a class=\"btn btn-success btn-w-m\" >"+value+"</a>";
        case "PUT":
            return "<a class=\"btn btn-warning btn-w-m\" >"+value+"</a>";
        case "DELETE":
            return "<a class=\"btn btn-danger  btn-w-m\" >"+value+"</a>";
        case "GET":
            return "<a class=\"btn btn-info  btn-w-m\" >"+value+"</a>";
        default:
            return "<a class=\"btn btn-info  btn-w-m\" >"+value+"</a>";
    }
}




function apiUp(obj) {
    rankApi(obj,"UP");
}
function apiDown(obj) {
    rankApi(obj,"DOWN");
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
                    text: "获取Api参数失败！" + msg.errorMsg,
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
                    text: "获取Api参数失败！" + msg.errorMsg,
                    type: "error"
                });
            }
        }
    });
    return content;
}


