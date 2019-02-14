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
    <form role="form" class="form-horizontal m-t well-g" id="jacocoForm">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>创建测试用例</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" onclick="saveTestcase()">保存</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="testcaseName"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">描述：</label>
                            <div class="col-sm-8">
                                <textarea type="text" rows="10" class="form-control"
                                          id="testcaseDescription"></textarea>
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
<script src="/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/static/js/plugins/jquery-ui-1.12.1/jquery-ui.js"></script>
<script>
    var serviceId = localStorage.getItem("serviceId");

    function saveTestcase() {
        var testcaseName = $("#testcaseName").val();
        var testcaseDescription = $("#testcaseDescription").val();
        var data = "{";
        data = data + "\"serviceId\":" + serviceId + ",";
        data = data + "\"name\":\"" + testcaseName + "\",";
        data = data + "\"description\":\"" + testcaseDescription + "\"";
        data = data + "}";
        $.ajax({
            type: "post",
            dataType: "json",
            url: "/testcase/create",
            async: false,
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    window.location.href =
                        "/static/jsp/iat.jsp";
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "创建测试用例失败，errormsg=" + msg.errorMsg,
                        type: "error"
                    });
                }
            }
        });
    }
</script>
</body>
</html>
