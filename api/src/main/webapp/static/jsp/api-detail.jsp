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
                            <a class="btn btn-primary btn-sm" onclick="updateApi()">保存</a>

                            <a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#debugModal">调试</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">名称：</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="apiName"
                                       aria-required="true">
                            </div>
                            <label class="col-sm-1 control-label">版本：</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="apiVersion" aria-required="true"
                                       disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">路径：</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="apiPath"
                                       aria-required="true" disabled="disabled">
                            </div>
                            <label class="col-sm-1 control-label">方法：</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="apiMethod"
                                       aria-required="true" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">说明：</label>
                            <div class="col-sm-11">
                                <textarea type="text" rows="5" class="form-control" id="apiDescription"></textarea>
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
                        <h5>Request</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">

                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="tabs-container">
                            <ul class="nav nav-tabs" id="tabs">
                                <li class="">
                                    <a data-toggle="tab" href="#header" aria-expanded="true">Header</a>
                                </li>
                                <li class="active">
                                    <a data-toggle="tab" href="#formdata" aria-expanded="false">Form Data</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#body" aria-expanded="false">Body</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#parameter" aria-expanded="false">Parameter</a>
                                </li>

                            </ul>
                            <div class="tab-content">
                                <div id="header" class="tab-pane">
                                    <div class="panel-body">
                                        <table id="headerTable" class="table text-nowrap"></table>
                                    </div>
                                </div>
                                <div id="formdata" class="tab-pane active">
                                    <div class="panel-body">
                                        <table id="formdataTable" class="table table-hover"></table>
                                    </div>
                                </div>
                                <div id="body" class="tab-pane">
                                    <div class="panel-body">
                                        <div class="col-sm-6">
                                            <h5>定义格式</h5>
                                            <textarea type="text" rows="10" class="form-control" body-id=""
                                                      id="apiBody"></textarea>

                                        </div>
                                        <div class="col-sm-6">
                                            <h5>默认值</h5>
                                            <textarea type="text" rows="10" class="form-control"
                                                      id="apiBody-default"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div id="parameter" class="tab-pane">
                                    <div class="panel-body">
                                        <table id="parameterTable" class="table table-hover"></table>
                                    </div>
                                </div>
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
                        <h5>Response</h5>
                        <div class="ibox-tools" style="margin-top: -9px;" id="response-status">
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="tabs-container">
                            <ul class="nav nav-tabs" id="response-tabs">
                                <li class="">
                                    <a data-toggle="tab" href="#response-header" aria-expanded="true">Headers</a>
                                </li>
                                <li class="active">
                                    <a data-toggle="tab" href="#response-body" aria-expanded="false">Body</a>
                                </li>
                                <li class="">
                                    <a data-toggle="tab" href="#resultcode" aria-expanded="false">Result Code</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div id="response-header" class="tab-pane">
                                    <div class="panel-body" id="response-headers">

                                    </div>
                                </div>
                                <div id="response-body" class="tab-pane active">
                                    <div class="panel-body">
                                        <a id="response-apiBody"></a>
                                    </div>
                                </div>
                                <div id="resultcode" class="tab-pane">
                                    <div class="panel-body">
                                        <table id="resultcodeTable" class="table table-hover"></table>
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
                <button type="button" class="btn btn-primary" onclick="apiDebug()">确定</button>
            </div>
        </div>
    </div>
</div>

<div id="headerToolBar" class="btn-group">
    <a class="btn btn-primary btn-sm" id="addHeader">添加</a>
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
    var apiId;
    window.onload = function () {
        apiId = window.location.href.getQuery("apiId");
        getApi();
        getEnv();

    }

    $("#addHeader").click(function () {
        var count = $('#headerTable').bootstrapTable('getData').length;
        var row = "{";
        // var titleLength = Object.getOwnPropertyNames(title).length;
        // for (var key in title) {
        //     row = row + key + ":" + "'" + title[key] + "',";
        // }
        // var timestamp = (new Date()).getTime();
        // row = row + "rowNum:" + "'unique-" + timestamp + "'";
        row = row + "}";
        $('#headerTable').bootstrapTable('insertRow', {index: count, row: eval("(" + row + ")")});
    });

    function getApi() {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/api/info?id=" + apiId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#apiName").val(msg.content.name);
                    $("#apiVersion").val(msg.content.version);
                    $("#apiPath").val(msg.content.path);
                    $("#apiMethod").val(msg.content.method);

                    $("#apiDescription").val(msg.content.description);
                    if (msg.content.body != undefined) {
                        $("#apiBody").val(msg.content.body.content);
                        $("#apiBody-default").val(msg.content.body.defaultValue);
                        $("#apiBody").attr("body-id", msg.content.body.id);
                    }

                    initHeaderTable(msg.content.header);
                    initFormdataTable(msg.content.formData);
                    initResultCodeTable(msg.content.resultCode);
                    getParamters(msg.content.id);
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "创建关键字失败，errormsg=" + msg.errorMsg,
                        type: "error"
                    });
                }
            }
        });
    }

    function getParamters(apiId) {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/api/parameters?apiId=" + apiId,
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
                        text: "获取Api参数失败！" + msg.errorMsg,
                        type: "error"
                    });
                }
            }
        });
    }

    function initResultCodeTable(data) {
        $('#resultcodeTable').bootstrapTable({
            data: data,
            dataType: "json",
            idField: "id",
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false

                },
                {
                    title: '返回码',
                    field: 'code'
                }, {
                    title: '描述',
                    field: 'description'
                }]

        });
    }

    function headerOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\" onclick=\"deleteHeader(this)\" >删除</a>";
    }

    function getHeader(apiId) {
        var data;
        $.ajax({
            type: "get",
            url: "/api/header/get?apiId="+apiId,
            dataType: 'json',
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    data =msg.content;
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
    
    function deleteHeader(obj) {
        $.ajax({
            type: "delete",
            url: "/api/header/delete?headerId="+obj.id,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $('#headerTable').bootstrapTable("destroy");
                    var data = getHeader(apiId);
                    initHeaderTable(data);
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

    function initHeaderTable(data) {
        $('#headerTable').bootstrapTable({
            data: data,
            dataType: "json",
            idField: "id",
            toolbar: "#headerToolBar",
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false
                },
                {
                    title: '名称',
                    field: 'name',
                    editable: true
                }, {
                    title: '类型',
                    field: 'type',
                    editable: true
                }, {
                    title: '默认值',
                    field: 'defaultValue',
                    editable: true
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: headerOptFormatter,
                    width: 150
                }],
            onEditableSave: function (field, row, oldValue, $el) {
                var data = "{";
                if (row.id != undefined) {
                    data = data + "\"id\":" + row.id + ",";
                }
                if (row.name == undefined) {
                    return;
                }
                data = data + "\"name\":\"" + row.name + "\",";
                if (row.type != undefined) {
                    data = data + "\"type\":\"" + row.type + "\",";
                }
                if (row.defaultValue != undefined) {
                    data = data + "\"defaultValue\":\"" + row.defaultValue + "\",";
                }
                data = data + "\"apiId\":" + apiId + "";
                data = data + "}";
                $.ajax({
                    type: "put",
                    url: "/api/header/update",
                    data: data,
                    dataType: 'json',
                    contentType: "application/json; charset=utf-8",
                    beforeSend: function (request) {
                        request.setRequestHeader("Authentication", $.cookie("Authentication"));
                    },
                    success: function (msg) {
                        if (msg.status) {
                            $('#headerTable').bootstrapTable("destroy");
                            var data = getHeader(apiId);
                            initHeaderTable(data);
                        } else if (msg.code == "D0000104") {
                            window.location.href = "/login.jsp";
                        } else {
                            swal({
                                title: "提示！",
                                text: "修改header失败！errormsg=" + msg.errorMsg,
                                type: "error"
                            });
                        }

                    }

                });
            }
        });
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
                }],
        });
    }


    function initFormdataTable(data) {
        $('#formdataTable').bootstrapTable({
            data: data,
            dataType: "json",
            idField: "id",
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false
                },
                {
                    title: '名称',
                    field: 'name',
                    editable: true
                }, {
                    title: '类型',
                    field: 'type',
                    editable: true
                }, {
                    title: '默认值',
                    field: 'defaultValue',
                    editable: true
                }],
            onEditableSave: function (field, row, oldValue, $el) {
                var data = "{";
                data = data + "\"id\":" + row.id + ",";
                data = data + "\"name\":\"" + row.name + "\",";
                data = data + "\"type\":\"" + row.type + "\",";
                data = data + "\"defaultValue\":\"" + row.defaultValue + "\"";
                data = data + "}";
                $.ajax({
                    type: "put",
                    url: "/api/form-data/update",
                    data: data,
                    dataType: 'json',
                    contentType: "application/json; charset=utf-8",
                    success: function (msg) {
                        if (msg.status) {
                        } else if (msg.code == "D0000104") {
                            window.location.href = "/login.jsp";
                        } else {
                            swal({
                                title: "提示！",
                                text: "修改form data失败！errormsg=" + msg.errorMsg,
                                type: "error"
                            });
                        }

                    }

                });
            }
        });
    }

    function apiDebug() {
        var envId = $("#env option:selected").attr("key");
        if (envId == null) {
            swal({
                title: "提示！",
                text: "请选择环境！",
                type: "error"
            });
            return;
        }
        $.ajax({
            type: "post",
            url: "/api/debug",
            dataType: 'json',
            data: getApiData(envId),
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    if (msg.content.statusCode == 200) {
                        $("#response-status").html("<a class=\" btn btn-w-m btn-success \">200</a>");
                    } else {
                        $("#response-status").html("<a class=\" btn btn-w-m btn-danger \">" + msg.content.statusCode + "</a>");
                    }
                    try {
                        var json = JSON.parse(msg.content.responseBody);
                        $("#response-apiBody").jsonViewer(json);
                    } catch (e) {
                        $("#response-apiBody").html(msg.content.responseBody.replace(/\n/g, "<br/>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;").replace(/\r/g, "&emsp;"));

                    }

                    headers = msg.content.responseHeader;
                    var rh = "";
                    for (var key in headers) {
                        rh = rh + "<li>" + key + " : " + headers[key] + "</li>"
                    }
                    $("#response-headers").html(rh);
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "Api Debug错误！errormsg=" + msg.errorMsg,
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

    function getApiData(envId) {
        var apiName = $("#apiName").val();
        var apiPath = $("#apiPath").val();
        var apiMethod = $("#apiMethod").val();
        var apiBody = $("#apiBody").val();
        var apiBodyDefault = $("#apiBody-default").val();
        apiBody = apiBody.replace(/\\/g, "\\\\").replace(/\"/g, "\\\"");
        apiBodyDefault = apiBodyDefault.replace(/\\/g, "\\\\").replace(/\"/g, "\\\"");
        var apiBodyId = $("#apiBody").attr("body-id");
        var headerTableData = $("#headerTable").bootstrapTable('getData');
        var formdataTableData = $("#formdataTable").bootstrapTable('getData');
        var parameterTableData = $("#parameterTable").bootstrapTable('getData');


        var headers = "[";
        for (i = 0; i < headerTableData.length; i++) {
            if (headerTableData != undefined) {
                headers = headers + "{";
                headers = headers + "\"id\":\"" + headerTableData[i].id + "\",";
                headers = headers + "\"name\":\"" + headerTableData[i].name + "\",";
                headers = headers + "\"defaultValue\":\"" + headerTableData[i].defaultValue + "\",";
                headers = headers + "\"type\":\"" + headerTableData[i].type + "\"";
                headers = headers + "}";
                if (i != headerTableData.length - 1) {
                    headers = headers + ",";
                }
            }
        }
        headers = headers + "]";
        var formdatas = "[";
        for (i = 0; i < formdataTableData.length; i++) {
            if (formdataTableData != undefined) {
                formdatas = formdatas + "{";
                formdatas = formdatas + "\"id\":\"" + formdataTableData[i].id + "\",";
                formdatas = formdatas + "\"name\":\"" + formdataTableData[i].name + "\",";
                formdatas = formdatas + "\"defaultValue\":\"" + formdataTableData[i].defaultValue + "\",";
                formdatas = formdatas + "\"type\":\"" + formdataTableData[i].type + "\"";
                formdatas = formdatas + "}";
                if (i != formdataTableData.length - 1) {
                    formdatas = formdatas + ",";
                }
            }
        }
        formdatas = formdatas + "]";

        var parameters = "[";
        for (i = 0; i < parameterTableData.length; i++) {
            if (parameterTableData != undefined) {
                parameters = parameters + "{";
                parameters = parameters + "\"name\":\"" + parameterTableData[i].name + "\",";
                parameters = parameters + "\"value\":\"" + parameterTableData[i].value + "\"";
                parameters = parameters + "}";
                if (i != parameterTableData.length - 1) {
                    parameters = parameters + ",";
                }
            }
        }
        parameters = parameters + "]";

        var data = "{";
        data = data + "\"id\":" + apiId + ",";
        if (envId != null) {
            data = data + "\"envId\":\"" + envId + "\",";
        }
        data = data + "\"name\":\"" + apiName + "\",";
        data = data + "\"path\":\"" + apiPath + "\",";
        data = data + "\"method\":\"" + apiMethod + "\",";
        if (apiBody != "") {
            data = data + "\"body\":{\"id\":" + apiBodyId + ",\"content\":\"" + apiBody + "\",\"defaultValue\":\"" + apiBodyDefault + "\"},";
        }
        data = data + "\"headers\":" + headers + ",";
        data = data + "\"parameters\":" + parameters + ",";
        data = data + "\"formDatas\":" + formdatas;
        data = data + "}";
        return data;
    }

    function updateApi() {
        $.ajax({
            type: "put",
            url: "/api/update",
            data: getApiData(),
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
                        text: "修改Api失败！errormsg=" + msg.errorMsg,
                        type: "error"
                    });
                }

            }

        });
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
                        text: "修改Api失败！errormsg=" + msg.errorMsg,
                        type: "error"
                    });
                }
            }
        });
    }

</script>
</body>
</html>
