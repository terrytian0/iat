<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>IAT</title>

    <meta name="keywords" content="">
    <meta name="description" content="">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="favicon.ico">
    <link href="static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="static/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="static/css/animate.css" rel="stylesheet">
    <link href="static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="static/js/plugins/layui/css/layui.css" rel="stylesheet" media="all">
    <link href="static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <%--<div class="nav-close"><i class="fa fa-times-circle"></i>--%>
        <%--</div>--%>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <a data-toggle="dropdown" class="dropdown-toggle navbar-minimalize" href="#">
                                <span class="clear">
                                    <span class="block m-t-xs">
                                        <div style="float: left"></div>
                                        <div style="float: left;margin-left: 5px;"><strong class="font-bold"
                                                                                           style="font-size: 18px;">API Test</strong></div>
                                    </span>
                                </span>
                        </a>
                    </div>
                    <div class="logo-element">AT
                    </div>
                </li>
                <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                    <span class="ng-scope"></span>
                </li>
                <li>
                    <a class="J_menuItem" href="static/jsp/iat.jsp">
                        <i class="fa fa-balance-scale" style="width: 14px"></i>
                        <span class="nav-label">接口测试</span>
                    </a>

                </li>
                <li>
                    <a class="J_menuItem" href="static/jsp/api.jsp">
                        <i class="fa fa-database" style="width: 14px"></i>
                        <span class="nav-label">Api管理</span>
                    </a>
                </li>
                <li class="line dk"></li>
                <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                    <span class="ng-scope"></span>
                </li>
                <li>
                    <a class="J_menuItem" href="static/jsp/service.jsp">
                        <i class="fa fa-bars" style="width: 14px"></i>
                        <span class="nav-label">服务管理</span>
                    </a>
                </li>
                <li>
                    <a class="J_menuItem" href="static/jsp/client.jsp">
                        <i class="fa fa-bars" style="width: 14px"></i>
                        <span class="nav-label">客户端管理</span>
                    </a>
                </li>
                <li>
                    <a class="J_menuItem" href="static/jsp/user.jsp">
                        <i class="fa fa-users" style="width: 14px"></i>
                        <span class="nav-label">用户管理</span>
                    </a>
                </li>
                <li>
                    <a class="J_menuItem">
                        <i class="fa fa-cogs" style="width: 14px"></i>
                        <span class="nav-label">系统设置</span>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashboard-chart">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-info " href="#"><i
                        class="fa fa-bars"></i> </a>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#" id="userName">

                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="#">
                                    <div data-toggle="modal" data-target="#passwordUpdateModal">
                                        <i class="fa fa-lock fa-fw"></i> 修改密码
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div data-toggle="modal" data-target="#userInfoUpdateModal">
                                        <i class="fa fa-edit fa-fw"></i> 个人信息
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="#">
                                    <div onclick="logout()">
                                        <i class="fa fa-sign-out fa-fw"></i> 退出登录
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe id="J_iframe" width="100%" height="100%" frameborder="0" src="/static/jsp/iat.jsp" seamless>
            </iframe>
        </div>
    </div>
    <!--右侧部分结束-->
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="passwordUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">个人密码修改</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <label class="col-md-3 text-right">新密码：</label>
                    <div class="col-md-9  pull-left">
                        <input type="password" class="form-control" id="newPassword" aria-required="true">
                    </div>
                </div>
                <br>
                <div class="row">
                    <label class="col-md-3 text-right">确认密码：</label>
                    <div class="col-md-9  pull-left">
                        <input type="password" class="form-control" id="confirmPassword" aria-required="true">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="updatePassword()">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="userInfoUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">个人信息修改</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                        <label class="col-md-2 text-right">用户名：</label>
                        <div class="col-md-10  pull-left">
                            <input type="text" class="form-control" id="name" aria-required="true">
                        </div>
                </div>
                <br>
                <div class="row">
                    <label class="col-md-2 text-right">电话：</label>
                    <div class="col-md-10  pull-left">
                        <input type="text" class="form-control" id="phone" aria-required="true">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="updateUserInfo()">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="/static/js/jquery.min.js?v=2.1.4"></script>
<script src="static/js/bootstrap.min.js?v=3.3.6"></script>
<script src="static/js/plugins/layui/layui.js" charset="utf-8"></script>
<script src="static/js/iat.js"></script>

<script src="static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- 自定义js -->
<script src="static/js/hAdmin.js?v=4.1.0"></script>
<script type="text/javascript" src="static/js/index.js"></script>
<script src="/static/js/plugins/cookie/jquery.cookie.js"></script>

<script>
    $(window).ready(function () {
        $("#userName").html(localStorage.getItem("name"));
    });

    function logout() {
        $.ajax({
            type: "post",
            url: "/user/logout",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    window.location.href = "/login.jsp";
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "删除Api失败！\n" + msg.errorMsg,
                        type: "error"
                    });
                }

            }

        });
    }
</script>
</body>
</html>
