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
    <form role="form" class="form-horizontal m-t well-g" id="jacocoForm">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>基础信息</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" onclick="updateKeyword()">保存</a>
                            <a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#debugModal">调试</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">名称：</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="keywordName"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">描述：</label>
                            <div class="col-sm-11">
                                <textarea type="text" rows="5" class="form-control" id="keywordDescription"></textarea>
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
                        <h5>Api</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="table-responsive">
                            <table id="apiTable" class="table table-hover"></table>
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
                <button type="button" class="btn btn-primary" onclick="keywordDebug()">确定</button>
            </div>
        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="addExtractorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">提取器</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <h4>类型</h4>
                    <select class="form-control m-b" style="height: 30px" id="extractorType">
                        <option value="JSON">JSON-PATH</option>
                        <option value="REGEXP">正则表达式</option>
                    </select>
                    <label></label>
                    <h4>提取规则</h4>
                    <input type="text" class="form-control" id="extractorRule" aria-required="true">
                    <label></label>
                    <h4>参数名称</h4>
                    <input type="text" class="form-control" id="extractorName" aria-required="true">
                    <label></label>
                    <h4>描述</h4>
                    <textarea type="text" rows="5" class="form-control" id="extractorDescription"
                              aria-required="true"></textarea>
                    <input type="text" class="form-control" id="extractorKeywordApiId" aria-required="true"
                           style="visibility:hidden">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addExtractor()">确定</button>
            </div>
        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="addAssertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">断言</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <h4>位置</h4>
                    <select class="form-control m-b" style="height: 30px" id="assertLocale">
                        <option value="HTTPCODE">Http Code</option>
                        <option value="HEADER">Header</option>
                        <option value="BODY">Body</option>
                    </select>
                    <label></label>
                    <h4>规则类型</h4>
                    <select class="form-control m-b" style="height: 30px" id="assertType">
                        <option value="JSON">JSON-PATH</option>
                        <option value="REGEXP">正则表达式</option>
                    </select>
                    <label></label>
                    <h4>提取规则</h4>
                    <input type="text" class="form-control" id="assertRule" aria-required="true">
                    <label></label>
                    <h4>比较方法</h4>
                    <select class="form-control m-b" style="height: 30px" id="assertMethod">
                        <option value="EQUALS">等于</option>
                        <option value="GREATER">大于</option>
                        <option value="LESS">小于</option>
                        <option value="CONTAINS">包含</option>
                    </select>
                    <label></label>
                    <h4>预期结果</h4>
                    <input type="text" class="form-control" id="assertValue" aria-required="true">
                    <label></label>
                    <h4>描述</h4>
                    <textarea type="text" rows="5" class="form-control" id="assertDescription"
                              aria-required="true"></textarea>
                    <input type="text" class="form-control" id="assertKeywordApiId" aria-required="true"
                           style="visibility:hidden">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="addAssert()">确定</button>
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


<script>
    var keywordId;
    window.onload = function () {
        keywordId = window.location.href.getQuery("keywordId");
        getKeyword(keywordId);
        getEnv();
    }
    var oTableInit = new Object();
    var apis;
    var debugResult = new Map();

    $('#addExtractorModal').on('show.bs.modal', function (event) {
        var btnThis = $(event.relatedTarget); //触发事件的按钮
        // var modal = $(this);  //当前模态框
        $("#extractorKeywordApiId").val(btnThis.data('id'));
    });

    $('#addAssertModal').on('show.bs.modal', function (event) {
        var btnThis = $(event.relatedTarget); //触发事件的按钮
        // var modal = $(this);  //当前模态框
        $("#assertKeywordApiId").val(btnThis.data('id'));
    });

    function updateKeyword() {
        var keywordName = $("#keywordName").val();
        var keywordDescription = $("#keywordDescription").val();
        var data = "{";
        data = data + "\"id\":" + keywordId + ",";
        data = data + "\"name\":\"" + keywordName + "\",";
        data = data + "\"description\":\"" + keywordDescription + "\"";
        data = data + "}";
        $.ajax({
            type: "put",
            url: "/keyword/update",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
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

    function getKeyword(keywordId) {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/keyword/info?id=" + keywordId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#keywordName").val(msg.content.name);
                    $("#keywordDescription").val(msg.content.description);
                    getParamters(msg.content.id);
                    apis = msg.content.apis;
                    initApiTable();
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

    function getParamters(keywordId) {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/keyword/parameters?keywordId=" + keywordId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    initParameterTable(msg.content);
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

    function addExtractor() {
        var type = $("#extractorType option:selected").attr("value");
        var keywordApiId = $("#extractorKeywordApiId").val();
        var rule = $("#extractorRule").val();
        var name = $("#extractorName").val();
        var description = $("#extractorDescription").val();
        var data = "{";
        data = data + "\"keywordApiId\":" + keywordApiId + ",";
        data = data + "\"type\":\"" + type + "\",";
        data = data + "\"rule\":\"" + rule + "\",";
        data = data + "\"name\":\"" + name + "\",";
        data = data + "\"description\":\"" + description + "\"";
        data = data + "}";
        $.ajax({
            type: "post",
            dataType: "json",
            data: data,
            url: "/extractor/create",
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var extractorData = getExtractor(keywordApiId);
                    $("#extractor-assert-table-" + keywordApiId).bootstrapTable('destroy');
                    initExtractor(keywordApiId, extractorData);
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示",
                        text: msg.message,
                        type: "error"
                    });
                }
            }
        });
        $('#addExtractorModal').modal('hide');
    }


    function addAssert() {
        var keywordApiId = $("#assertKeywordApiId").val();
        var type = $("#assertType option:selected").attr("value");
        var locale = $("#assertLocale option:selected").attr("value");
        var method = $("#assertMethod option:selected").attr("value");
        var rule = $("#assertRule").val();
        var value = $("#assertValue").val();
        var description = $("#assertDescription").val();
        var data = "{";
        data = data + "\"keywordApiId\":" + keywordApiId + ",";
        data = data + "\"type\":\"" + type + "\",";
        data = data + "\"rule\":\"" + rule + "\",";
        data = data + "\"locale\":\"" + locale + "\",";
        data = data + "\"method\":\"" + method + "\",";
        data = data + "\"value\":\"" + value + "\",";
        data = data + "\"description\":\"" + description + "\"";
        data = data + "}";
        $.ajax({
            type: "post",
            dataType: "json",
            data: data,
            url: "/assert/create",
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var assertData = getAssert(keywordApiId);
                    $("#response-assert-table-" + keywordApiId).bootstrapTable('destroy');
                    initAssert(keywordApiId, assertData);
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示",
                        text: msg.message,
                        type: "error"
                    });
                }
            }
        });
        $('#addAssertModal').modal('hide');
    }

    function initParameterTable(data) {
        $('#parameterTable').bootstrapTable({
            data: data,
            dataType: "json",
            idField: "id",
            columns: [
                {
                    title: '参数名',
                    field: 'name'
                }, {
                    title: '参数值',
                    field: 'value',
                    editable: true
                }, {
                    title: '提取规则',
                    field: 'rule'
                }],
        });
    }

    function refreshApiTable() {
        $('#apiTable').bootstrapTable('destroy');
        initApiTable();
    }

    function initApiTable() {
        $('#apiTable').bootstrapTable({
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
                    title: '名称',
                    field: 'detail.name'
                }, {
                    title: '方法',
                    field: 'detail.method'
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
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: keywordApiOptFormatter,
                    width: 280
                }],
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitApiResult(index, row, $detail);
            }
        });
    }

    oTableInit.InitApiResult = function (index, row, $detail) {
        var tableId = "extractorTable-" + row.id;
        var extractorTable = $detail.html("<div class=\"ibox-content\"><div class=\"tabs-container\"><ul class=\"nav nav-tabs\" id=\"response-tabs\"><li class=\"\"><a data-toggle=\"tab\" href=\"#request-general-tab-" + row.id + "\" aria-expanded=\"true\">General</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#request-header-tab-" + row.id + "\" aria-expanded=\"true\">Request Header</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#request-formdata-tab-" + row.id + "\" aria-expanded=\"true\">Request Formdata</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#request-body-tab-" + row.id + "\" aria-expanded=\"true\">Request Body</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#response-header-tab-" + row.id + "\" aria-expanded=\"true\">Response Header</a></li><li class=\"active\"><a data-toggle=\"tab\" href=\"#response-body-tab-" + row.id + "\" aria-expanded=\"false\">Response Body</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#response-extractor-tab-" + row.id + "\" aria-expanded=\"false\">提取器</a></li><li class=\"\"><a data-toggle=\"tab\" href=\"#response-assert-tab-" + row.id + "\" aria-expanded=\"false\">断言</a></li></ul><div class=\"tab-content\"><div id=\"request-general-tab-" + row.id + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-general-" + row.id + "\"></div></div><div id=\"request-header-tab-" + row.id + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-header-" + row.id + "\"></div></div><div id=\"request-formdata-tab-" + row.id + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-formdata-" + row.id + "\"></div></div><div id=\"request-body-tab-" + row.id + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"request-body-" + row.id + "\"></div></div><div id=\"response-header-tab-" + row.id + "\" class=\"tab-pane\"><div class=\"panel-body\" id=\"response-header-" + row.id + "\"></div></div><div id=\"response-body-tab-" + row.id + "\" class=\"tab-pane active\"><div class=\"panel-body\"><a id=\"response-body-" + row.id + "\"></a></div></div><div id=\"response-extractor-tab-" + row.id + "\" class=\"tab-pane\"><div class=\"panel-body\"><div class=\"table-responsive\"><table id=\"response-extractor-table-" + row.id + "\" class=\"table table-hover\"></table></div></div></div><div id=\"response-assert-tab-" + row.id + "\" class=\"tab-pane\"><div class=\"panel-body\"><div class=\"table-responsive\"><table id=\"response-assert-table-" + row.id + "\" class=\"table table-hover\"></table></div></div></div></div></div></div>").find('#' + tableId);
        var result = debugResult.get(row.id);
        if (result != undefined) {
            var general = result.general;
            var g = "";
            for (var key in general) {
                g = g + "<li>" + key + " : " + general[key] + "</li>"
            }
            $("#request-general-" + row.id).html(g);
            var requestHeader = result.requestHeader;
            var reqh = "";
            for (var key in requestHeader) {
                reqh = reqh + "<li>" + key + " : " + requestHeader[key] + "</li>"
            }
            $("#request-header-" + row.id).html(reqh);

            var requestFormData = result.requestFormData;
            var reqf = "";
            for (var key in requestFormData) {
                reqf = reqf + "<li>" + key + " : " + requestFormData[key] + "</li>"
            }
            $("#request-formdata-" + row.id).html(reqf);

            if (result.requestBody != undefined) {
                try {
                    var requestJson = JSON.parse(result.requestBody);
                    $("#request-body-" + row.id).jsonViewer(requestJson);
                } catch (e) {
                    $("#request-body-" + row.id).html(result.requestBody.replace(/\n/g, "<br/>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\r/g, "&emsp;"));
                }
            }

            //response
            var responseHeader = result.responseHeader;
            var reph = "";
            for (var key in responseHeader) {
                reph = reph + "<li>" + key + " : " + responseHeader[key] + "</li>"
            }
            $("#response-header-" + row.id).html(reph);
            try {
                var responseJson = JSON.parse(result.responseBody);
                $("#response-body-" + row.id).jsonViewer(responseJson);
            } catch (e) {
                $("#response-body-" + row.id).html(result.responseBody.replace(/\n/g, "<br/>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\r/g, "&emsp;"));
            }
            var extractorData = result.extractors;
            initExtractor(row.id, extractorData);
            var assertData = result.asserts;
            initAssert(row.id, assertData);
        } else {
            var extractorData = getExtractor(row.id);
            initExtractor(row.id, extractorData);
            var assertData = getAssert(row.id);
            initAssert(row.id, assertData);
        }
        return oTableInit;
    }

    function initExtractor(keywordId, data) {
        var tableId = "#response-extractor-table-" + keywordId;
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
                }, {
                    title: '描述',
                    field: 'description'
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: extractorOptFormatter,
                    width: 100
                }]
        });

    }

    function initAssert(keywordId, data) {
        var tableId = "#response-assert-table-" + keywordId;
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
                }, {
                    title: '描述',
                    field: 'description'
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: assertOptFormatter,
                    width: 100
                }]
        });
    }

    function assertOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" name=\"" + row.keywordApiId + "\" id=\"" + row.id + "\" onclick=\"deleteAssert(this)\">删除</a>"
    }

    function deleteAssert(obj) {
        $.ajax({
            type: "delete",
            dataType: "json",
            url: "/assert/delete?assertId=" + obj.id,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var assertData = getAssert(obj.name);
                    $("#response-assert-table-" + obj.name).bootstrapTable('destroy');
                    initAssert(obj.name, assertData);
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

    function extractorOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" name=\"" + row.keywordApiId + "\" id=\"" + row.id + "\" onclick=\"deleteExtractor(this)\">删除</a>"
    }

    function deleteExtractor(obj) {
        $.ajax({
            type: "delete",
            dataType: "json",
            url: "/extractor/delete?extractorId=" + obj.id,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var extractorData = getExtractor(obj.name);
                    $("#response-extractor-table-" + obj.name).bootstrapTable('destroy');
                    initExtractor(obj.name, extractorData);
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


    function keywordDebug() {
        var envId = $("#env option:selected").attr("key");
        if (envId == null) {
            swal({
                title: "提示",
                text: "请选择环境！",
                type: "error"
            });
            return;
        }
        var parameterTableData = $("#parameterTable").bootstrapTable('getData');

        var parameters = "[";
        if (parameterTableData != undefined) {
            for (i = 0; i < parameterTableData.length; i++) {
                if (parameterTableData[i].value != undefined) {
                    parameters = parameters + "{";
                    parameters = parameters + "\"name\":\"" + parameterTableData[i].name + "\",";
                    parameters = parameters + "\"value\":\"" + parameterTableData[i].value + "\"";
                    parameters = parameters + "}";
                    if (i < parameterTableData.length - 1) {
                        parameters = parameters + ",";
                    }
                }
            }
        }
        if (parameters.endsWith(",")) {
            parameters = parameters.substring(0, parameters.length - 1);
        }
        parameters = parameters + "]";
        var data = "{";
        data = data + "\"keywordId\":" + keywordId + ",";
        if (envId != null) {
            data = data + "\"envId\":\"" + envId + "\",";
        }
        data = data + "\"parameters\":" + parameters + "";
        data = data + "}";

        $.ajax({
            type: "post",
            url: "/keyword/debug",
            dataType: 'json',
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var content = msg.content.httpResults;
                    for (var r in content) {
                        debugResult.set(content[r].keywordApiId, content[r]);
                    }
                    for (var i in apis) {
                        if (debugResult.get(apis[i].id) != undefined) {
                            apis[i].status = debugResult.get(apis[i].id).successful;
                            apis[i].error = debugResult.get(apis[i].id).error;

                        }
                    }
                    refreshApiTable();
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
                        text:msg.message,
                        type: "error"
                    });
                }

            }

        });
    }

    function rankApi(obj, opt) {
        var data = "{";
        data = data + "\"index\":" + "\"" + opt + "\",";
        data = data + "\"keywordApiId\":" + obj.id + ",";
        data = data + "\"keywordId\":" + obj.name;
        data = data + "}";
        $.ajax({
            type: "PUT",
            url: "/keyword/idx",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (msg) {
                if (msg.status) {
                    window.location.reload();
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


    function removeApi(obj) {
        var data = "[";
        data = data + obj.id + "";
        data = data + "]";
        $.ajax({
            type: "delete",
            url: "/keyword/api/remove",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    window.location.reload();
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

    function keywordApiOptFormatter(value, row, index) {
        return "<a class=\"btn btn-primary btn-sm\" name=\"" + row.keywordId + "\" id=\"" + row.id + "\" onclick=\"apiUp(this)\">上移</a>" +
            "   <a class=\"btn btn-primary btn-sm\" name=\"" + row.keywordId + "\" id=\"" + row.id + "\" onclick=\"apiDown(this)\">下移</a>" +
            "   <a class=\"btn btn-danger btn-sm\" name=\"" + row.keywordId + "\" id=\"" + row.id + "\" onclick=\"removeApi(this)\">删除</a>" +
            "   <a class=\"btn btn-primary btn-sm\" data-id='" + row.id + "'  data-toggle=\"modal\" data-target=\"#addExtractorModal\">提取器</a>" +
            "   <a class=\"btn btn-primary btn-sm\" data-id='" + row.id + "'  data-toggle=\"modal\" data-target=\"#addAssertModal\">断言</a>"
    }

    window.operateEvents = {
        "click #errormsg": function (e, value, row, index) {
            swal({
                title: "错误详情",
                text: row.error,
                type: "error"
            });
        }
    }
</script>
</body>
</html>
