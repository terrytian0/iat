<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="shortcut icon" href="favicon.ico">
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/static/css/plugins/bootstrap-editable/bootstrap-editable.css" rel="stylesheet"/>
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/js/plugins/jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet">
    <link href="/static/css/plugins/json-viewer/jquery.json-viewer.css" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <form role="form" class="form-horizontal m-t well-g">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>基础信息</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" onclick="updateTestcase()">保存</a>
                            <a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#debugModal">调试</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">名称：</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="testcaseName"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">描述：</label>
                            <div class="col-sm-11">
                                <textarea type="text" rows="5" class="form-control" id="testcaseDescription"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>Parameter</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" onclick="addParameter()">添加参数</a>
                            <a class="btn btn-primary btn-sm" onclick="saveParameter()">保存</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <table id="parameterTable" class="table table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>Keyword</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="table-responsive">
                            <table id="keywordTable" class="table table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="debugModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">环境</h4>
            </div>
            <div class="modal-body">
                <div class="form-group"><select class="form-control m-b" style="height: 30px" id="env"></select></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="testcaseDebug()">确定</button>
            </div>
        </div>
    </div>
</div>


<!-- 全局js -->
<script src="/static/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
<!-- 自定义js -->
<script src="/static/js/content.js?v=1.0.0"></script>
<script src="/static/js/iat.js"></script>
<script src="/static/js/plugins/cookie/jquery.cookie.js"></script>
<script src="/static/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/static/js/plugins/validate/messages_zh.min.js"></script>
<script src="/static/js/form-validate.js"></script>
<script src="/static/js/plugins/iCheck/icheck.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/static/js/plugins/bootstrap-editable/bootstrap-editable.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/static/js/plugins/bootstrap-editable/bootstrap-table-editable.js"></script>
<script src="/static/js/plugins/jquery-ui-1.12.1/jquery-ui.js"></script>
<script src="/static/js/plugins/json-viewer/jquery.json-viewer.js"></script>
<script src="/static/js/plugins/layer/layer.min.js"></script>

<script>
    var testcaseId;
    var title;
    window.onload = function () {
        testcaseId = window.location.href.getQuery("testcaseId");
        getTestcase(testcaseId);
        initParameterTable(testcaseId);
        getEnv();
    }
    var oTableInit = new Object();
    //通过用例详情获取的关键字列表
    var keywords;
    //测试用例执行返回结果
    var testcaseResultMap = new Map();
    //通过用例详情获取的关键字Map
    var keywordApisMap = new Map();


    function updateTestcase() {
        var testcaseName = $("#testcaseName").val();
        var testcaseDescription = $("#testcaseDescription").val();
        var data = "{";
        data = data + "\"id\":" + testcaseId + ",";
        data = data + "\"name\":\"" + testcaseName + "\",";
        data = data + "\"description\":\"" + testcaseDescription + "\"";
        data = data + "}";
        $.ajax({
            type: "put",
            url: "/testcase/update",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (msg) {
                if (msg.status) {
                    self.location = document.referrer;
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
    }

    function getTestcase(testcaseId) {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/testcase/info?id=" + testcaseId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testcaseName").val(msg.content.name);
                    $("#testcaseDescription").val(msg.content.description);
                    keywords = msg.content.keywords;
                    for (var i in keywords) {
                        keywordApisMap.set(keywords[i].id, keywords[i].detail.apis);
                    }
                    initKeywordTable();
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
    }

    function getParamterTitle(testcaseId) {
        var ptitle;
        $.ajax({
            type: "put",
            dataType: "json",
            url: "/testcase/parameter/title?testcaseId=" + testcaseId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    ptitle = msg.content;
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
        return ptitle;
    }

    function getParamterData(testcaseId) {
        var data;
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/testcase/parameter/data?testcaseId=" + testcaseId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    data = msg.content;
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
        return data;
    }


    function initParameterTable(testcaseId) {
        title = getParamterTitle(testcaseId)
        var data = getParamterData(testcaseId)
        $('#parameterTable').bootstrapTable({
            data: data,
            dataType: "json",
            idField: "id",
            columns: getParameterColumns(title)
        });
    }

    function addParameter() {
        var count = $('#parameterTable').bootstrapTable('getData').length;
        var row = "{";
        var titleLength = Object.getOwnPropertyNames(title).length;
        for (var key in title) {
            row = row + key + ":" + "'" + title[key] + "',";
        }
        var timestamp = (new Date()).getTime();
        row = row + "rowNum:" + "'unique-" + timestamp + "'";
        row = row + "}";
        $('#parameterTable').bootstrapTable('insertRow', {index: count, row: eval("(" + row + ")")});
    }

    function getParameterColumns(title) {
        var col = "[";
        col = col + "{";
        col = col + "title: '全选',";
        col = col + "field: 'select',";
        col = col + "checkbox: true,";
        col = col + "width: 25,";
        col = col + "align: 'center',";
        col = col + "valign: 'middle'";
        col = col + "},";
        col = col + "{";
        col = col + "title: 'row',";
        col = col + "field: 'rowNum',";
        col = col + "visible: false";
        col = col + "},";
        for (var key in title) {
            col = col + "{";
            col = col + "title: '" + key + "',";
            col = col + "field: '" + key + "',";
            col = col + "editable: true";
            col = col + "},";
        }
        col = col + "{";
        col = col + "title: '操作',";
        col = col + "field: 'button',";
        col = col + "align: 'center',";
        col = col + "events: operateEvents,";
        col = col + "formatter: parameterOptFormatter,";
        col = col + "width: 200";
        col = col + "}";
        col = col + "]";
        return eval("(" + col + ")");
    }

    function parameterOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" id=\"parameterDelete\">删除</a>";
    }

    function getRowData(row) {
        var data = "{";
        data = data + "\"testcaseId\":" + testcaseId + ",";
        if (row.rowNum.startsWith("unique-") == false) {
            data = data + "\"rowNum\":" + row.rowNum + ",";
        }
        data = data + "\"parameters\":" + getRowParameter(row) + "";
        data = data + "}";
        return data;
    }

    function getRowParameter(row) {
        alert(row["password"]);
        var param = "{"
        var k = 0;
        var length = Object.getOwnPropertyNames(row).length;
        for (var key in row) {
            if (key != "rowNum") {
                k = k + 1;
                param = param + "\"" + key + "\":\"" + row[key] + "\"";
                if (k < length - 1) {
                    param = param + ",";
                }
            }
        }
        param = param + "}";
        return param;
    }

    function getDatas() {
        var parameterTableDatas = $('#parameterTable').bootstrapTable('getData')
        var data = "[";
        var k = 0;
        for (var i in parameterTableDatas) {
            k = k + 1;
            data = data + getRowData(parameterTableDatas[i]);
            if (k < parameterTableDatas.length) {
                data = data + ",";
            }
        }
        data = data + "]";
        return data;
    }


    function saveParameter(row) {
        var data;
        if (row == undefined) {
            data = getDatas();
        } else {
            data = "[";
            data = data + getRowData(row);
            data = data + "]";
        }
        $.ajax({
            type: "put",
            url: "/testcase/parameter",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    swal({
                        title: "提示！",
                        text: "保存参数成功",
                        type: "success"
                    });
                    refreshParameterTable();
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: msg.message,
                        type: "error"
                    });
                }

            },
            error: function (xhr, exception) {
                error(xhr, exception);
            }
        });
    }

    function deleteParameter(rowNum) {
        alert(rowNum);
        if (rowNum.startsWith("unique-")) {
            $('#parameterTable').bootstrapTable('remove', {field: 'rowNum', values: [rowNum]});
            return;
        }
        $.ajax({
            type: "delete",
            url: "/testcase/parameter?testcaseId=" + testcaseId + "&rowNum=" + rowNum,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $('#parameterTable').bootstrapTable('remove', {field: 'rowNum', values: [rowNum]});
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: msg.message,
                        type: "error"
                    });
                }

            },
            error: function (xhr, exception) {
                error(xhr, exception);
            }
        });
    }

    function refreshKeywordTable() {
        $('#keywordTable').bootstrapTable('destroy');
        initKeywordTable();
    }

    function refreshParameterTable() {
        $('#parameterTable').bootstrapTable('destroy');
        initParameterTable(testcaseId);
    }

    function initKeywordTable() {
        $('#keywordTable').bootstrapTable({
            data: keywords,
            dataType: "json",
            idField: "id",
            detailView: true,
            columns: [

                {
                    title: '序号',
                    field: 'id',
                    visible: false
                },
                {
                    title: '关键字id',
                    field: 'keywordId',
                    visible: false
                },
                {
                    title: '名称',
                    field: 'detail.name'
                }, {
                    title: '方法',
                    field: 'detail.description'
                }, {
                    title: '排序',
                    field: 'idx'
                }, {
                    title: '结果',
                    field: 'status',
                    formatter: statusFormatter
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: testcaseKeywordOptFormatter,
                    width: 280
                }],
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitApiTable(index, row, $detail);
            }

        });
    }


    function testcaseKeywordOptFormatter(value, row, index) {
        return "<a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\" onclick=\"keywordUp(this)\">上移</a>" +
            "   <a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\" onclick=\"keywoedDown(this)\">下移</a>" +
            "   <a class=\"btn btn-danger btn-sm\" name=\"" + row.testcaseId + "\" id=\"" + row.id + "\" onclick=\"removeKeyword(this)\">删除</a>"
    }

    oTableInit.InitApiTable = function (index, row, $detail) {
        var tableId = "api-table-" + row.id;
        var apiTable = $detail.html('<table id=\"api-table-' + row.id + '\"></table>').find('#' + tableId);
        var apis = keywordApisMap.get(row.id);
        var testcaseKeywordId = row.id;
        if (testcaseResultMap.get(testcaseKeywordId) != undefined) {
            var keywordResult = testcaseResultMap.get(testcaseKeywordId).httpResults;
            var keywordApiMaps = new Map();
            for (var i in keywordResult) {
                keywordApiMaps.set(keywordResult[i].apiId, keywordResult[i]);
            }
            for (var i in apis) {
                if (keywordApiMaps.get(apis[i].apiId) != undefined) {
                    apis[i].status = keywordApiMaps.get(apis[i].apiId).successful;
                    apis[i].error = keywordApiMaps.get(apis[i].apiId).error;
                }
            }
        }

        $(apiTable).bootstrapTable({
            data: apis,
            dataType: "json",
            idField: "id",
            detailView: true,
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false
                },
                {
                    title: 'ApiId',
                    field: 'apiId',
                    visible: false
                },
                {
                    title: '名称',
                    field: 'detail.name'
                }, {
                    title: '方法',
                    field: 'detail.method',
                    formatter: methodFormatter
                }, {
                    title: '路径',
                    field: 'detail.path'
                }, {
                    title: '参数',
                    field: 'detail.parameters'
                }, {
                    title: '排序',
                    field: 'idx'
                }, {
                    title: '结果',
                    field: 'status',
                    events: operateEvents,
                    formatter: statusFormatter
                }],
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitApiResult(index, row, $detail, testcaseKeywordId);
            }

        });
        return oTableInit;
    }

    window.operateEvents = {
        "click #parameterDelete": function (e, value, row, index) {
            deleteParameter(row.rowNum);
        },
        "click #errormsg": function (e, value, row, index) {
            swal({
                title: "错误详情",
                text: row.error,
                type: "error"
            });
        }
    }

    oTableInit.InitApiResult = function (index, row, $detail, testcaseKeywordId) {
        $detail.html("<div class=\"ibox-content\"><div class=\"tabs-container\"><ul class=\"nav nav-tabs\" id=\"response-tabs\"><li class=\"\"><a data-toggle=\"tab\" href=\"#request-general-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"true\">General</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#request-header-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"true\">Request Header</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#request-formdata-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"true\">Request Formdata</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#request-body-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"true\">Request Body</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#response-header-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"true\">Response Header</a></li><li class=\"active\"><a data-toggle=\"tab\" href=\"#response-body-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"false\">Response Body</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#response-extractor-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"false\">提取器</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#response-assert-tab-" + row.id + "-" + testcaseKeywordId + "\" aria-expanded=\"false\">断言</a></li></ul><div class=\"tab-content\"><div id=\"request-general-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-general-" + row.id + "-" + testcaseKeywordId + "\"></div></div><div id=\"request-header-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-header-" + row.id + "-" + testcaseKeywordId + "\"></div></div><div id=\"request-formdata-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-formdata-" + row.id + "-" + testcaseKeywordId + "\"></div></div><div id=\"request-body-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-body-" + row.id + "-" + testcaseKeywordId + "\"></div></div><div id=\"response-header-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"response-header-" + row.id + "-" + testcaseKeywordId + "\"></div></div><div id=\"response-body-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane active\"><div class=\"panel-body\"><a id=\"response-body-" + row.id + "-" + testcaseKeywordId + "\"></a></div></div><div id=\"response-extractor-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane\"><div class=\"panel-body\"><div class=\"table-responsive\"><table id=\"response-extractor-table-" + row.id + "-" + testcaseKeywordId + "\" class=\"table table-hover\"></table></div></div></div><div id=\"response-assert-tab-" + row.id + "-" + testcaseKeywordId + "\" class=\"tab-pane\"><div class=\"panel-body\"><div class=\"table-responsive\"><table id=\"response-assert-table-" + row.id + "-" + testcaseKeywordId + "\" class=\"table table-hover\"></table></div></div></div></div></div></div>");
        //TODO API提取器
        var keywordApiId = row.id;
        if (testcaseResultMap.get(testcaseKeywordId) == null) {
            var extractorData = getExtractor(keywordApiId);
            $("#response-extractor-table-" + keywordApiId + "-" + testcaseKeywordId).bootstrapTable('destroy');
            initExtractor(testcaseKeywordId, keywordApiId, extractorData);

            var assertData = getAssert(keywordApiId);
            $("#response-assert-table-" + keywordApiId + "-" + testcaseKeywordId).bootstrapTable('destroy');
            initAssert(testcaseKeywordId, keywordApiId, assertData);
            return oTableInit;
        }
        var httpResults = testcaseResultMap.get(testcaseKeywordId).httpResults;
        var result;
        for (var i in httpResults) {
            if (httpResults[i].apiId == row.apiId) {
                result = httpResults[i];
                break;
            }
        }
        if (result != undefined) {
            var general = result.general;
            var g = "";
            for (var key in general) {
                g = g + "<li>" + key + " : " + general[key] + "</li>"
            }
            $("#request-general-" + row.id + "-" + testcaseKeywordId).html(g);
            var requestHeader = result.requestHeader;
            var reqh = "";
            for (var key in requestHeader) {
                reqh = reqh + "<li>" + key + " : " + requestHeader[key] + "</li>"
            }
            $("#request-header-" + row.id + "-" + testcaseKeywordId).html(reqh);

            var requestFormData = result.requestFormData;
            var reqf = "";
            for (var key in requestFormData) {
                reqf = reqf + "<li>" + key + " : " + requestFormData[key] + "</li>"
            }
            $("#request-formdata-" + row.id + "-" + testcaseKeywordId).html(reqf);

            if (result.requestBody != undefined) {
                try {
                    var requestJson = JSON.parse(result.requestBody);
                    $("#request-body-" + row.id + "-" + testcaseKeywordId).jsonViewer(requestJson);
                } catch (e) {
                    $("#request-body-" + row.id + "-" + testcaseKeywordId).html(result.requestBody.replace(/\n/g, "<br/>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\r/g, "&emsp;"));
                }
            }

            var responseHeader = result.responseHeader;
            var reph = "";
            for (var key in responseHeader) {
                reph = reph + "<li>" + key + " : " + responseHeader[key] + "</li>"
            }
            $("#response-header-" + row.id + "-" + testcaseKeywordId).html(reph);
            try {
                var responseJson = JSON.parse(result.responseBody);
                $("#response-body-" + row.id + "-" + testcaseKeywordId).jsonViewer(responseJson);
            } catch (e) {
                $("#response-body-" + row.id + "-" + testcaseKeywordId).html(result.responseBody.replace(/\n/g, "<br/>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\r/g, "&emsp;"));
            }

            var extractorData = result.extractors;
            $("#response-extractor-table-" + keywordApiId + "-" + testcaseKeywordId).bootstrapTable('destroy');
            initExtractor(testcaseKeywordId, keywordApiId, extractorData);

            var assertData = result.asserts;
            $("#response-assert-table-" + keywordApiId + "-" + testcaseKeywordId).bootstrapTable('destroy');
            initAssert(testcaseKeywordId, keywordApiId, assertData);
        }
        return oTableInit;
    }

    function initAssert(testcaseKeywordId, keywordApiId, data) {
        var tableId = "#response-assert-table-" + keywordApiId + "-" + testcaseKeywordId;
        $(tableId).bootstrapTable({
            data: data,
            dataType: "json",
            idField: "id",
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false
                }, {
                    title: 'ApiId',
                    field: 'keywordApiId',
                    visible: false
                },
                {
                    title: '位置',
                    field: 'locale'
                },
                {
                    title: '类型',
                    field: 'type'
                }, {
                    title: '规则',
                    field: 'rule'
                }, {
                    title: '比较方法',
                    field: 'method'
                }, {
                    title: '预期结果',
                    field: 'value'
                }, {
                    title: '实际结果',
                    field: 'actual'
                }, {
                    title: '状态',
                    field: 'status',
                    formatter: statusFormatter
                }]
        });
    }

    function initExtractor(testcaseKeywordId, keywordApiId, data) {
        var tableId = "#response-extractor-table-" + keywordApiId + "-" + testcaseKeywordId;
        $(tableId).bootstrapTable({
            data: data,
            dataType: "json",
            idField: "id",
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false
                }, {
                    title: 'ApiId',
                    field: 'keywordApiId',
                    visible: false
                },
                {
                    title: '类型',
                    field: 'type'
                }, {
                    title: '参数名',
                    field: 'name'
                }, {
                    title: '规则',
                    field: 'rule'
                }, {
                    title: '提取值',
                    field: 'value'
                }]
        });

    }

    function testcaseDebug() {
        var envId = $("#env option:selected").attr("key");
        if (envId == null) {
            swal({
                title: "提示",
                text: "请选择环境！",
                type: "error"
            });
            return;
        }

        var parameterTableData=  $("#parameterTable").bootstrapTable('getSelections');
        if(parameterTableData.length!=1){
            swal({
                title: "提示！",
                text: "请选择一行参数进行调试！",
                type: "info"
            });
            return;
        }
        // var parameterTableData = $("#parameterTable").bootstrapTable('getData');
        // var parameters = "[";
        // if (parameterTableData != undefined) {
        //     for (var i in parameterTableData) {
        //         if (parameterTableData[i] != undefined) {
        //             parameters = parameters + getRowParameter(parameterTableData[i]);
        //             if (i != parameterTableData.length - 1) {
        //                 parameters = parameters + ",";
        //             }
        //         }
        //     }
        // }
        // parameters = parameters + "]";
        parameters = getRowParameter(parameterTableData)
        var data = "{";
        if (envId != null) {
            data = data + "\"envId\":" + envId + ",";
        }
        data = data + "\"testcaseId\":" + testcaseId + ",";
        data = data + "\"parameters\":" + parameters + "";
        data = data + "}";
        $.ajax({
            type: "post",
            url: "/testcase/debug",
            dataType: 'json',
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var keywordResults = msg.content.keywordResults;

                    for (var krs in keywordResults) {
                        testcaseResultMap.set(keywordResults[krs].testcaseKeywordId, keywordResults[krs]);
                    }
                    for (var i in keywords) {
                        if (testcaseResultMap.get(keywords[i].id) != undefined) {
                            keywords[i].status = testcaseResultMap.get(keywords[i].id).status;
                        }
                    }
                    refreshKeywordTable();
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: msg.message,
                        type: "error"
                    });
                }

            },
            error: function (xhr, exception) {
                error(xhr, exception);
            }
        });
        $('#debugModal').modal('hide');
    }

    function refreshTestcaseTable() {

    }

    function getEnv() {
        var serviceId = localStorage.getItem("serviceId")
        $.ajax({
            type: "get",
            url: "/service/env/get?serviceId=" + serviceId,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var items = "";
                    $.each(msg.content, function (i, item) {
                        items = items + "<option key='" + item.id + "'>" + item.env + "</option>";
                    });
                    $("#env").html(items);
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
    }
</script>
</body>
</html>
