<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>云测</title>
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/login.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <![endif]-->
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>

<body class="signin">

<div class="signinpanel">
    <div class="row">
        <div class="col-sm-12">
            <br>
            <br>
            <br>
            <br>
            <br>
            <%--<h3 style="float: none;text-align: center">云测</h3>--%>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <div class="signinbox">
                <input type="text" class="form-control uname" placeholder="用户名" required="" id="username"/>
                <input type="password" class="form-control pword m-b" placeholder="密码" required="" id="userpwd"/>
                <br>
                <button type="submit" class="btn btn-default btn-block" id="login">登录</button>
            </div>
        </div>
    </div>
    <div class="signup-footer">
        <div style="text-align: center;float: none">
           <a class="label label-info" herf="">忘記密碼</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="label label-info" herf="">用戶註冊</a>
        </div>
    </div>
</div>
<!-- 全局js -->
<script src="/static/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/static/js/plugins/cookie/jquery.cookie.js"></script>
<script src="/static/js/plugins/sweetalert/sweetalert.min.js"></script>


<script>
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            $("#login").trigger("click");
        }
    });
    var clicktag = 0;
    $("#login").click(function () {
        if (clicktag == 1) {
            swal({
                title: "提示！",
                text: "您已经提交登录请求，请稍等！",
                type: "error"
            });
            return;
        }
        var username = $("#username").val();
        if (username == "") {
            swal({
                title: "提示！",
                text: "登录用户名不能为空！",
                type: "error"
            });
            return;
        }
        var userpwd = $("#userpwd").val();
        if (userpwd == "") {
            swal({
                title: "提示！",
                text: "登录密码不能为空！",
                type: "error"
            });
            return;
        }
        if (clicktag == 0) {
            clicktag = 1;
        }
        var data = "{";
        data = data + "\"name\":\"" + username + "\",";
        data = data + "\"password\":\"" + userpwd + "\"";
        data = data + "}";
        $.ajax({
            type: "post",
            dataType: "json",
            url: "/user/login",
            data: data,
            async: true,
            contentType: "application/json; charset=utf-8",
            success: function (msg) {
                if (msg.status) {
                    $.cookie("Authentication", msg.content.token);
                    localStorage.setItem("role",msg.content.role);
                    localStorage.setItem("name",msg.content.name);
                    window.location.href = "index.jsp";
                } else {
                    clicktag = 0;
                    swal({
                        title: "提示！",
                        text: msg.message,
                        type: "error"
                    });
                }
            }
        });
    });
</script>
</body>


</html>
