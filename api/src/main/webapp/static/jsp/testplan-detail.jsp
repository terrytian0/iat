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
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/js/plugins/jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <form role="form" class="form-horizontal m-t well-g" id="">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>基础信息</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" onclick="updateTestplan()">保存</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="testplanName"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">策略：</label>
                            <div class="col-sm-8">
                                <textarea type="text" rows="10" class="form-control"
                                          id="strategy"></textarea>
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
                        <h5>环境配置</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" onclick="saveEnv()">保存</a>
                        </div>
                    </div>
                    <div class="ibox-content" id="envList">
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
<script src="/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/static/js/plugins/jquery-ui-1.12.1/jquery-ui.js"></script>
<script>

    function saveEnv() {
        var selects = $("#envList").find("select");
        var data = "["
        for (var i = 0; i < selects.length; i++) {
            if(i>0){
                data = data + ","
            }
            data = data + "{"
            data = data + "\"testplanId\":"+testplanId+",";
            var select = selects[i];
            var serviceId = select.id;
            data = data + "\"serviceId\":"+serviceId+",";
            var index = select.selectedIndex;
            var envValue = select.options[index].value
            if (envValue == "") {
                swal({
                    title: "提示！",
                    text: "请选择测试环境！",
                    type: "error"
                });
                return
            }
            data = data + "\"selectId\":"+envValue+"";
            data = data + "}"
        }
        data = data + "]"
        $.ajax({
            type: "put",
            dataType: "json",
            url: "/testplan/env",
            data:data,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    swal({
                        title: "提示！",
                        text: "测试环境保存成功！",
                        type: "info"
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

    function env() {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/testplan/env?testplanId=" + testplanId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    var envList = "";
                    var res = msg.content;
                    for (var i = 0; i < res.length; i++) {
                        var serviceName = res[i].serviceName
                        var serviceId = res[i].serviceId
                        envList = envList + "<div class=\"form-group draggable\"><label class=\"col-sm-1 control-label\">" + serviceName + "：</label><div class=\"col-sm-8\"><select class=\"form-control m-b\" style=\"height: 30px\" id=\"" + serviceId + "\">";
                        var envs = res[i].envs;
                        envList = envList + "<option value=\"\">请选择环境</option>";
                        for (var j = 0; j < envs.length; j++) {
                            var env = envs[j];
                            var name = env.env + "(" + env.host + ":" + env.port + ")";
                            var id = env.id;
                            envList = envList + "<option key=\"env-" + id + "\" value=\"" + id + "\">" + name + "</option>";
                        }
                        envList = envList + "</select></div></div>";
                    }
                    $("#envList").html(envList)
                    for (var i = 0; i < res.length; i++) {
                        var serviceId = res[i].serviceId;
                        var env = res[i].env
                        if (env != undefined) {
                            var envId = env.id;
                            $("select[id='" + serviceId + "']").find("option[key='env-" + envId + "']").attr("selected", true);
                        }
                    }
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

    var testplanId;
    window.onload = function () {
        testplanId = window.location.href.getQuery("testplanId");
        getTestplan();
        env();
    }

    function getTestplan() {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/testplan/info?testplanId=" + testplanId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testplanName").val(msg.content.name);
                    $("#strategy").val(msg.content.strategy);
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

    function updateTestplan() {
        var testplanName = $("#testplanName").val();
        var strategy = $("#strategy").val();
        var data = "{";
        data = data + "\"id\":" + testplanId + ",";
        data = data + "\"name\":\"" + testplanName + "\",";
        data = data + "\"strategy\":\"" + strategy + "\"";
        data = data + "}";
        $.ajax({
            type: "put",
            dataType: "json",
            url: "/testplan/update",
            async: false,
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    window.location.href =
                        "/static/jsp/testplan.jsp";
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
