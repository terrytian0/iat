<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>云测</title>
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
    <form role="form" class="form-horizontal m-t well-g" id="userForm">
        <div class="row">
            <div class="col-sm-3">
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>用户信息</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">

                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-2 control-label">邮箱：</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="userEmail" name="userEmail"
                                       aria-required="true">
                            </div>
                            <span class="col-sm-4 help-block m-b-none"><i class="fa fa-info-circle"></i> </span>

                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-2 control-label">电话：</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="userPhone" name="userPhone"
                                       aria-required="true">
                            </div>
                            <span class="col-sm-4 help-block m-b-none"><i class="fa fa-info-circle"></i> </span>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-2 control-label">登录密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="password" name="password"
                                       aria-required="true">
                            </div>
                            <span class="col-sm-4 help-block m-b-none"><i class="fa fa-info-circle"></i> </span>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-2 control-label">确认密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-2 control-label"></label>
                            <div class="col-sm-9">
                                <button class="btn btn-primary btn-sm" type="submit">提交</button>
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

    function register() {
        var data = getUserInfo();
        $.ajax({
            type: "post",
            url: "/user/register",
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
                        text: msg.message,
                        type: "success"
                    }, function () {
                        window.location.href = "/login.jsp";
                    });

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

    function getUserInfo() {
        var userEmail = $("#userEmail").val();
        var userPhone = $("#userPhone").val();
        var password = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();
        var data = "{";
        data = data + "\"name\":\"" + userEmail + "\",";
        data = data + "\"phone\":\"" + userPhone + "\",";
        data = data + "\"password\":\"" + password + "\"";
        data = data + "}";
        return data;
    }

    function validate() {
        var password = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();
        if (password == confirmPassword) {
            return true;
        } else {
            return false;
        }
    }

    $(document).ready(function () {
        jQuery.validator.addMethod("eq", function (value, param) {
            return validate();
        }, $.validator.format("确认密码和用户密码不一致！"));
        $("#userForm").validate({
            rules: {
                userEmail: {
                    required: true,
                    rangelength: [5, 32],
                    checkEmail: true
                },
                userPhone: {
                    required: true,
                    rangelength: [1, 16],
                    checkPhone: true
                },
                password: {
                    required: true,
                    rangelength: [6, 20]
                },
                confirmPassword: {
                    required: true,
                    eq: true
                }
            },
            messages: {},
            submitHandler: function (form) {
                register();
            }
        });
    });

</script>
</body>
</html>
