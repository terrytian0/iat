<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/static/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
    <link href="/static/css/plugins/json-viewer/jquery.json-viewer.css" rel="stylesheet">

</head>

<style type="text/css">
</style>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <form role="form" class="form-horizontal m-t well-g" id="jacocoForm">
        <div class="row">
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>用例</h5>
                    </div>
                    <div class="ibox-content">
                        <div id="treeview5" class="test"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>详情</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">路径：</label>
                            <div class="col-sm-11">
                                <label id="apiUrl" class="form-control">1</label>
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">方法：</label>
                            <div class="col-sm-11">
                                <label id="apiMethod" class="form-control">Post</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>Request</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="tabs-container">
                            <ul class="nav nav-tabs" id="tabs">
                                <li class="active">
                                    <a data-toggle="tab" href="#request-header" aria-expanded="true">Header</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#request-formdata" aria-expanded="false">Form Data</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#request-body" aria-expanded="false">Body</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#request-parameter" aria-expanded="false">Parameter</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div id="request-header" class="tab-pane active">
                                    <div class="panel-body">
                                        <a id="requestHeader"></a>
                                    </div>
                                </div>
                                <div id="request-formdata" class="tab-pane">
                                    <div class="panel-body">
                                        <a id="requestFormdata"></a>
                                    </div>
                                </div>
                                <div id="request-body" class="tab-pane">
                                    <div class="panel-body">
                                        <a id="requestBody"></a>
                                    </div>
                                </div>
                                <div id="request-parameter" class="tab-pane">
                                    <div class="panel-body">
                                        <a id="requestParameter"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>Response</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="tabs-container">
                            <ul class="nav nav-tabs" id="response-tabs">
                                <li class="">
                                    <a data-toggle="tab" href="#response-header" aria-expanded="false">Header</a>
                                </li>
                                <li class="active">
                                    <a data-toggle="tab" href="#response-body" aria-expanded="true">Body</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#response-extractor" aria-expanded="false">Extractor</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#response-assert" aria-expanded="false">Assert</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#response-message" aria-expanded="false">Message</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div id="response-header" class="tab-pane">
                                    <div class="panel-body">
                                        <a id="responseHeader"></a>
                                    </div>
                                </div>
                                <div id="response-body" class="tab-pane active">
                                    <div class="panel-body">
                                        <a id="responseBody"></a>
                                    </div>
                                </div>
                                <div id="response-extractor" class="tab-pane">
                                    <div class="panel-body">
                                        <table id="responseExtractorTable" class="table table-hover"></table>

                                    </div>
                                </div>
                                <div id="response-assert" class="tab-pane">
                                    <div class="panel-body">
                                        <table id="responseAssertTable" class="table table-hover"></table>
                                    </div>
                                </div>
                                <div id="response-message" class="tab-pane">
                                    <div class="panel-body">
                                        <a id="responseMessage"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </form>
</div>

<!-- 全局js -->
<script src="/static/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
<!-- 自定义js -->
<script src="/static/js/content.js?v=1.0.0"></script>
<script src="/static/js/iat.js"></script>
<script src="/static/js/plugins/cookie/jquery.cookie.js"></script>
<script src="/static/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/static/js/plugins/layer/layer.min.js"></script>
<script src="/static/js/plugins/treeview/bootstrap-treeview.js"></script>
<script src="/static/js/plugins/json-viewer/jquery.json-viewer.js"></script>

<script>

    var taskId;
    var treeData;
    window.onload = function () {
        taskId = window.location.href.getQuery("testtaskId");
        initTaskTree();
    }

    function initTaskTree() {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/task/detail?taskId=" + taskId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var treeData = msg.content;
                    $('#treeview5').treeview({
                        color: "#428bca",
                        icon: "glyphicon glyphicon-stop",
                        selectedIcon: "glyphicon glyphicon-stop",
                        color: "#000000",
                        backColor: "#FFFFFF",
                        showTags: true,
                        data: treeData,
                        onNodeSelected: function (event, node) {
                            if (node.level == 4) {
                                var taskId = node.taskId;
                                var testcaseId = node.testcaseId;
                                var parameterId = node.parameterId;
                                var testcaseKeywordId = node.testcaseKeywordId;
                                var keywordId = node.keywordId;
                                var keywordApiId = node.keywordApiId;
                                var apiId = node.apiId;
                                getApiResult(taskId, testcaseId, parameterId, testcaseKeywordId, keywordId, keywordApiId, apiId);
                                getParameter(parameterId);
                            }
                        }
                    });
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

    function getApiResult(taskId, testcaseId, parameterId, testcaseKeywordId, keywordId, keywordApiId, apiId) {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/task/result/api?taskId=" + taskId + "&testcaseId=" + testcaseId + "&parameterId=" + parameterId + "&testcaseKeywordId=" + testcaseKeywordId + "&keywordId=" + keywordId + "&keywordApiId=" + keywordApiId + "&apiId=" + apiId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#apiUrl").html(msg.content.url);
                    $("#apiMethod").html(msg.content.method);
                    var reqh = "";
                    if (msg.content.requestHeaders != "") {
                        var rh = msg.content.requestHeaders;
                        var rhJson = eval("(" + rh + ")")
                        for (var key in rhJson) {
                            reqh = reqh + "<li>" + key + " : " + rhJson[key] + "</li>"
                        }
                    }
                    $("#request-header").html(reqh);
                    var rfHtml = "";
                    if (msg.content.requestFormdatas != "") {
                        var rf = msg.content.requestFormdatas;
                        var rfJson = eval("(" + rf + ")")

                        for (var key in rfJson) {
                            rfHtml = rfHtml + "<li>" + key + " : " + rfJson[key] + "</li>"
                        }
                    }
                    $("#request-formdata").html(rfHtml);

                    if (msg.content.requestBody != "") {
                        try {
                            var requestJson = JSON.parse(msg.content.requestBody);
                            $("#requestBody").jsonViewer(requestJson);
                        } catch (e) {
                            $("#requestBody").html(msg.content.requestBody.replace(/\n/g, "<br/>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\r/g, "&emsp;"));
                        }
                    } else {
                        $("#requestBody").html("");
                    }
                    var reqh = "";
                    if (msg.content.responseHeaders != "") {
                        var rh = msg.content.responseHeaders;
                        var rhJson = eval("(" + rh + ")")
                        for (var key in rhJson) {
                            reqh = reqh + "<li>" + key + " : " + rhJson[key] + "</li>"
                        }
                    }
                    $("#response-header").html(reqh);

                    if (msg.content.responseBody != "") {
                        try {
                            var requestJson = JSON.parse(msg.content.responseBody);
                            $("#responseBody").jsonViewer(requestJson);
                        } catch (e) {
                            $("#responseBody").html(msg.content.responseBody.replace(/\n/g, "<br/>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\r/g, "&emsp;"));
                        }
                    } else {
                        $("#responseBody").html("");
                    }
                    var extractorJson = ""
                    if (msg.content.extractors != "") {
                        extractorJson = JSON.parse(msg.content.extractors);
                    }
                    initExtractor(extractorJson);
                    var assertJson = "";
                    if (msg.content.asserts != "") {
                        var assertJson = JSON.parse(msg.content.asserts);
                    }
                    initAssert(assertJson);
                    $("#responseMessage").html(msg.content.message);


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


    function initExtractor(data) {
        $("#responseExtractorTable").bootstrapTable({
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
                }, {
                    title: '描述',
                    field: 'description'
                }]
        });

    }

    function initAssert(data) {
        $("#responseAssertTable").bootstrapTable({
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
                }, {
                    title: '描述',
                    field: 'description'
                }]
        });
    }

    function getParameter(parameterId) {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/task/parameter?parameterId=" + parameterId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var rpHtml = "";
                    if (msg.content.parameters != "") {
                        var rp = msg.content.parameters;
                        var rpJson = eval("(" + rp + ")")
                        for (var key in rpJson) {
                            rpHtml = rpHtml + "<li>" + key + " : " + rpJson[key] + "</li>"
                        }
                    }
                    $("#request-parameter").html(rpHtml);
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
