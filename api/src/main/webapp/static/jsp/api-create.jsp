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
    <form role="form" class="form-horizontal m-t well-g" id="apiForm">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>基础信息</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <button class="btn btn-primary btn-sm" type="submit">保存</button>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="apiName" name="apiName"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">路径：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="apiPath" name="apiPath"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">方法：</label>
                            <div class="col-sm-8">
                                <select class="btn btn-default" id="apiMethod" style="height: 30px">
                                    <option value="POST">Post</option>
                                    <option value="GET">Get</option>
                                    <option value="PUT">Put</option>
                                    <option value="DELETE">Delete</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">说明：</label>
                            <div class="col-sm-8">
                                <textarea type="text" rows="5" class="form-control" id="apiDescription"
                                          name="apiDescription"></textarea>
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
                        <h5>Header</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" id="addHeader">新增</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group">
                            <div id="header" class="tab-pane">
                                <div class="panel-body">
                                    <table id="headerTable" class="table text-nowrap"></table>
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
                        <h5>Form Data</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" id="addFormdata">新增</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group">
                            <div id="formdata" class="tab-pane active">
                                <div class="panel-body">
                                    <table id="formdataTable" class="table table-hover"></table>
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
                        <h5>Body</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group">
                            <div id="body" class="tab-pane">
                                <div class="panel-body">
                                    <div class="col-sm-6">
                                        <h5>定义格式</h5>
                                        <textarea type="text" rows="10" class="form-control" body-id=""
                                                  id="apiBody" name="apiBody"></textarea>

                                    </div>
                                    <div class="col-sm-6">
                                        <h5>默认值</h5>
                                        <textarea type="text" rows="10" class="form-control"
                                                  id="apiBodyDefault" name="apiBodyDefault"></textarea>
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
<script src="/static/js/form-validate.js"></script>

<script>
    $("#addHeader").click(function () {
        var count = $('#headerTable').bootstrapTable('getData').length;
        var uniqueId = new Date().getTime();
        var row = "{";
        row = row + "\"rowNum\":" + uniqueId;
        row = row + "}";
        $('#headerTable').bootstrapTable('insertRow', {index: count, row: eval("(" + row + ")")});
    });
    $("#addFormdata").click(function () {
        var count = $('#formdataTable').bootstrapTable('getData').length;
        var uniqueId = new Date().getTime();
        var row = "{";
        row = row + "\"rowNum\":" + uniqueId;
        row = row + "}";
        $('#formdataTable').bootstrapTable('insertRow', {index: count, row: eval("(" + row + ")")});
    });
    window.operateEvents = {
        'click #headerDelete': function (e, value, row, index) {
            $('#headerTable').bootstrapTable('remove', {field: 'rowNum', values: [row.rowNum]});
        },
        'click #formdataDelete': function (e, value, row, index) {
            $('#formdataTable').bootstrapTable('remove', {field: 'rowNum', values: [row.rowNum]});
        }
    };

    function headerOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" id=\"headerDelete\">删除</a>";
    }

    function formdataOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" id=\"formdataDelete\">删除</a>";
    }


    function initHeaderTable(data) {
        $('#headerTable').bootstrapTable({
            dataType: "json",
            idField: "id",
            toolbar: "#headerToolBar",
            columns: [
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
                    events: "operateEvents",
                    formatter: headerOptFormatter,
                    width: 150
                }],
            onEditableSave: function (field, row, oldValue, $el) {

            }
        });
    }


    function initFormdataTable() {
        $('#formdataTable').bootstrapTable({
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
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    events: "operateEvents",
                    formatter: formdataOptFormatter,
                    width: 150
                }],
            onEditableSave: function (field, row, oldValue, $el) {

            }

        });
    }

    function saveApi() {
        var data = getApiData();
        $.ajax({
            type: "post",
            url: "/api/create",
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

    function getApiData() {
        var serviceId = localStorage.getItem("serviceId");
        var apiMethod = $("#apiMethod option:selected").attr("value");

        var apiName = $("#apiName").val();
        var apiPath = $("#apiPath").val();
        var apiDescription = $("#apiDescription").val();
        var apiBody = $("#apiBody").val();
        var apiBodyDefault = $("#apiBodyDefault").val();
        apiBody = apiBody.replace(/\\/g, "\\\\").replace(/\"/g, "\\\"");
        apiBodyDefault = apiBodyDefault.replace(/\\/g, "\\\\").replace(/\"/g, "\\\"");
        var apiBodyId = $("#apiBody").attr("body-id");
        var headerTableData = $("#headerTable").bootstrapTable('getData');
        var formdataTableData = $("#formdataTable").bootstrapTable('getData');

        var headers = "[";
        for (i = 0; i < headerTableData.length; i++) {
            if (headerTableData != undefined) {
                headers = headers + "{";
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


        var data = "{";
        data = data + "\"serviceId\":\"" + serviceId + "\",";
        data = data + "\"name\":\"" + apiName + "\",";
        data = data + "\"path\":\"" + apiPath + "\",";
        data = data + "\"method\":\"" + apiMethod + "\",";
        data = data + "\"description\":\"" + apiDescription + "\",";
        if (apiBody != "") {
            data = data + "\"body\":{\"content\":\"" + apiBody + "\",\"defaultValue\":\"" + apiBodyDefault + "\"},";
        }
        data = data + "\"headers\":" + headers + ",";
        data = data + "\"formDatas\":" + formdatas;
        data = data + "}";
        return data;
    }

    window.onload = function () {
        initHeaderTable();
        initFormdataTable();
    }

    $(document).ready(function () {
        $("#apiForm").validate({
            rules: {
                apiName: {
                    required: true,
                    rangelength: [1, 32]
                },
                apiPath: {
                    required: true,
                    rangelength: [1, 128]
                },
                apiDescription: {
                    rangelength: [1, 1024]
                },
                apiBodyDefault: {
                    rangelength: [1, 10240],
                    checkJson: true
                },
                apiBody: {
                    rangelength: [1, 10240],
                    checkJson: true
                },
            },
            messages: {},
            submitHandler: function (form) {
                saveApi();
            }
        });
    });

</script>
</body>
</html>
