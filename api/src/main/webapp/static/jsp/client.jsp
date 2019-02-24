<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="shortcut icon" href="favicon.ico">
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInUp">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>客户端列表</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="clientTable" class="table table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="/static/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="/static/js/content.js?v=1.0.0"></script>


<!-- Bootstrap table -->
<script src="/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/static/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/static/js/plugins/cookie/jquery.cookie.js"></script>

<!-- Peity -->
<script src="/static/js/iat.js"></script>
<script>

    $(window).resize(function () {
        $('#clientTable').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });

    window.onload = function () {
        initClientTable();
    };

    function refreshClientTable() {
        $('#clientTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10
        });
    };

    function initClientTable() {
        $('#clientTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/client/search",
            height: tableHeight(),
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            // toolbar: '#toolbar',
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
            showRefresh: true,
            showColumns: true,
            search: true,
            detailView: false,
            clickToSelect: true,
            toolbarAlign: 'left',
            buttonsAlign: 'right',
            columns: [
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                },
                {
                    title: '客户端',
                    field: 'client'
                }, {
                    title: 'Key',
                    field: 'key'
                }, {
                    title: '状态',
                    field: 'status',
                    formatter: statusFormatter
                },
                {
                    title: '注册时间',
                    field: 'registrationTime',
                    formatter: dateFormatter
                },
                {
                    title: '更新时间',
                    field: 'lastTime',
                    formatter: dateFormatter
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: clientOptFormatter,
                    width: 150
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (msg) {
                if (msg.status) {
                    return msg.content;
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

    function statusFormatter(value, row, index) {
        if (value == "NORMAL") {
            return "正常";
        } else if (value == "DISABLE") {
            return "禁用";
        } else if (value == "DISABLE") {
            return "失联";
        } else {
            return value;
        }
    }

    function clientOptFormatter(value, row, index) {
        return "<a class=\"btn btn-success btn-sm\" id=\"" + row.id + "\" onclick=\"enable(this)\" >启用</a>" +
            "    <a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\" onclick=\"disable(this)\" >禁用</a>"
    }

    function enable(obj) {
        $.ajax({
            type: "put",
            url: "/client/enable?id=" + obj.id,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    refreshClientTable();
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

    function disable(obj) {
        $.ajax({
            type: "put",
            url: "/client/disable?id=" + obj.id,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    refreshClientTable();
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
