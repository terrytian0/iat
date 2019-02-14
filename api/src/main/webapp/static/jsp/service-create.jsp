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
    <form role="form" class="form-horizontal m-t well-g">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>服务</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" onclick="createOrUpdateService()">保存</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="serviceName"
                                       aria-required="true">
                            </div>
                        </div>
                        <div class="form-group draggable">
                            <label class="col-sm-1 control-label">描述：</label>
                            <div class="col-sm-8">
                                <textarea type="text" rows="10" class="form-control" id="serviceDescription"></textarea>
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
                        <h5>用户</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#userListModal">添加</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="table-responsive">
                            <table id="userTable" class="table table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>环境</h5>
                        <div class="ibox-tools" style="margin-top: -9px;">
                            <a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#envModal">新增</a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="table-responsive">
                            <table id="envTable" class="table table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="envModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">环境</h4>
            </div>
            <div class="modal-body">
                <div class="form-group"><label>环境</label><input id="env" placeholder="请输入环境"
                                                                class="form-control"><label>域名</label> <input
                        id="host" placeholder="请输入域名" class="form-control"><label>端口</label> <input
                        id="port" placeholder="请输入端口" class="form-control"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="saveEnv()">保存</button>
            </div>
        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="userListModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">环境</h4>
            </div>
            <div class="modal-body">
                <table id="userListTable" class="table table-hover"></table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="adduser()">保存</button>
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
<script src="/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/static/js/plugins/jquery-ui-1.12.1/jquery-ui.js"></script>
<script>
    var serviceId = window.location.href.getQuery("serviceId");
    window.onload = function () {
        if (serviceId != null) {
            getService(serviceId);
        }
    }

    function getService(serviceId) {
        $.ajax({
            type: "get",
            dataType: "json",
            url: "/service/info?id=" + serviceId,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#serviceName").val(msg.content.name);
                    $("#serviceDescription").val(msg.content.description);
                    initEnvTabble(serviceId);
                    initUserTabble(serviceId);
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "获取服务失败。\n" + msg.errorMsg,
                        type: "error"
                    });
                }
            },
            error: function (xhr, exception) {
                error(xhr, exception);
            }
        });
    }

    function refreshEnvTable() {
        $('#envTable').bootstrapTable('refresh');
    };

    function initEnvTabble(serviceId) {
        $('#envTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/service/env/get?serviceId=" + serviceId,
            dataType: "json",
            dataField: "list",
            idField: "id",
            locale: 'zh-CN',
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            responseHandler: function (msg) {
                if (msg.status) {
                    return msg.content;
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                }else{
                    swal({
                        title: "提示！",
                        text: msg.message,
                        type: "error"
                    });
                }
            },
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false
                },
                {
                    title: '环境',
                    field: 'env'
                }, {
                    title: '域名',
                    field: 'host'
                }, {
                    title: '端口',
                    field: 'port'
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: envOptFormatter,
                    width: 150
                }]

        });
    }


    $('#userListModal').on('show.bs.modal', function () {
        $('#userListTable').bootstrapTable("destroy");
        $('#userListTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/service/user/unadded/get?serviceId="+serviceId,
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            toolbar: '#toolbar',
            striped: true,
            idField: "id",
            dataField: "list",
            pageNumber: 1,
            pagination: true,
            queryParamsType: '',
            sidePagination: 'server',
            pageSize: 10,
            pageList: [5, 10, 20, 30],
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            paginationFirstText: "首页",
            paginationLastText: "尾页",
            detailView: false,
            clickToSelect: true,
            toolbarAlign: 'left',
            buttonsAlign: 'right',
            columns: [
                {
                    title: '全选',
                    field: 'select',
                    checkbox: true,
                    width: 25,
                    align: 'center',
                    valign: 'middle'
                },
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                },
                {
                    title: '用户名',
                    field: 'name'
                }, {
                    title: '电话',
                    field: 'phone'
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (msg) {
                if (msg.status) {
                    return msg.content;
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                }else{
                    swal({
                        title: "提示！",
                        text: msg.message,
                        type: "error"
                    });
                }
            }
        });

    })

    function adduser() {
        var users= $("#userListTable").bootstrapTable('getSelections');
        if(users.length==0){
            swal({
                title: "提示",
                text: "请选择……",
                type: "info"
            });
        }
        var data = "{";
        data = data+ "\"serviceId\":" + serviceId+",";
        var userIds = "[";
        for(var i in users){
            userIds = userIds+users[i].id;
            if(i<users.length-1){
                userIds = userIds+",";
            }
        }
        userIds = userIds+"]";
        data = data+ "\"userIds\":" + userIds+"";
        data = data +  "}";
        $.ajax({
            type: "put",
            dataType: "json",
            url: "/service/user/add",
            async: false,
            data:data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    refreshUserTable();
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
        $('#userListModal').modal('hide');
    }


    function initUserTabble(serviceId) {
        $('#userTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/service/user/get?serviceId=" + serviceId,
            dataType: "json",
            dataField: "list",
            idField: "id",
            locale: 'zh-CN',
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            responseHandler: function (msg) {
                if (msg.status) {
                    return msg.content;
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                }else{
                    swal({
                        title: "提示！",
                        text: msg.message,
                        type: "error"
                    });
                }
            },
            columns: [
                {
                    title: '序号',
                    field: 'id',
                    visible: false
                }
                ,
                {
                    title: '用户名',
                    field: 'detail.name'
                }, {
                    title: '电话',
                    field: 'detail.phone'
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: userOptFormatter,
                    width: 150
                }]

        });
    }

    function userOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\" onclick=\"removeUser(this)\" >移除</a>";
    }

    function removeUser(obj) {
        $.ajax({
            type: "delete",
            dataType: "json",
            url: "/service/user/remove?id=" + obj.id,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    refreshUserTable();
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

    function refreshUserTable() {
        $('#userTable').bootstrapTable('refresh');
    };

    function envOptFormatter(value, row, index) {
        return "<a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\" onclick=\"deleteEnv(this)\" >删除</a>";
    }

    function saveEnv() {
        var env = $("#env").val();
        var host = $("#host").val();
        var port = $("#port").val();

        var data = "{";
        data = data + "\"serviceId\":" + serviceId + ",";
        data = data + "\"env\":\"" + env + "\",";
        data = data + "\"port\":" + port + ",";
        data = data + "\"host\":\"" + host + "\"";
        data = data + "}";
        $.ajax({
            type: "post",
            dataType: "json",
            url: "/service/env/create",
            async: false,
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    refreshEnvTable();
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "删除环境失败。\n" + msg.errorMsg,
                        type: "error"
                    });
                }
            },
            error: function (xhr, exception) {
                error(xhr, exception);
            }
        });
        $('#envModal').modal('hide');
    }

    function deleteEnv(obj) {
        $.ajax({
            type: "delete",
            dataType: "json",
            url: "/service/env/delete?id=" + obj.id,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    refreshEnvTable();
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "删除环境失败。\n" + msg.errorMsg,
                        type: "error"
                    });
                }
            },
            error: function (xhr, exception) {
                error(xhr, exception);
            }
        });
    }

    function createOrUpdateService() {
        if (serviceId == null) {
            createService();
        } else {
            updateService();
        }
    }

    function updateService() {
        var serviceName = $("#serviceName").val();
        var serviceDescription = $("#serviceDescription").val();
        var data = "{";
        data = data + "\"id\":" + serviceId + ",";
        data = data + "\"name\":\"" + serviceName + "\",";
        data = data + "\"description\":\"" + serviceDescription + "\"";
        data = data + "}";
        $.ajax({
            type: "put",
            dataType: "json",
            url: "/service/update",
            async: false,
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    window.location.href =
                        "/static/jsp/service.jsp";
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "创建服务失败。\n" + msg.errorMsg,
                        type: "error"
                    });
                }
            }
        });
    }

    function createService() {
        var serviceName = $("#serviceName").val();
        var serviceDescription = $("#serviceDescription").val();
        var data = "{";
        data = data + "\"name\":\"" + serviceName + "\",";
        data = data + "\"description\":\"" + serviceDescription + "\"";
        data = data + "}";
        $.ajax({
            type: "post",
            dataType: "json",
            url: "/service/create",
            async: false,
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    window.location.href =
                        "/static/jsp/service.jsp";
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "创建服务失败。\n" + msg.errorMsg,
                        type: "error"
                    });
                }
            }
        });
    }
</script>
</body>
</html>
