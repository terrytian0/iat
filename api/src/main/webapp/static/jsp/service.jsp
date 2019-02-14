<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico">
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInUp">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>服务列表</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="serviceTable" class="table table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="toolbar" class="btn-group">
    <div>
        <a class="btn btn-primary btn-sm" style="height: 30px" id="createService">创建
        </a>
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
<script src="/static/js/iat.js"></script>
<script src="/static/js/plugins/cookie/jquery.cookie.js"></script>



<script>

    $(window).resize(function () {
        $('#serviceTable').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });

    window.onload = function () {
        initServiceTable();
    };
    function refreshServiceTable() {
        $('#serviceTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10
        });
    };

    $('#createService').click(function () {
        window.location.href = "/static/jsp/service-create.jsp";
    });

    function initServiceTable() {

        $('#serviceTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/service/search",
            height: tableHeight(),
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
            showRefresh: true,
            showColumns: true,
            search: true,
            detailView: false,
            clickToSelect: true,
            toolbarAlign: 'left',
            buttonsAlign: 'right',
            iconSize: 'outline',
            icons: {
                refresh: 'glyphicon-repeat',
                toggle: 'glyphicon-list-alt',
                columns: 'glyphicon-list'
            },
            columns: [
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                },
                {
                    title: '名称',
                    field: 'name'
                },
                {
                    title: '唯一标识',
                    field: 'uniqueKey'
                },
                {
                    title: '创建人',
                    field: 'createUser'
                },
                {
                    title: '创建时间',
                    field: 'createTime',
                    formatter: dateFormatter
                },
                {
                    title: '更新人',
                    field: 'updateUser',
                    visible: false
                },
                {
                    title: '更新时间',
                    field: 'updateTime',
                    formatter: dateFormatter,
                    visible: false
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: serviceOptFormatter,
                    width: 150
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                if (res.status) {
                    return res.content;
                } else if (res.code == "D0000104") {
                    window.location.href = "/login.jsp";
                }
            }
        });
    }

    function serviceOptFormatter(value, row, index) {
        return "<a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\" onclick=\"updateService(this)\">修改</a>"
            + "   <a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\" onclick=\"deleteService(this)\">删除</a>"


    }

    function updateService(obj) {
        window.location.href = "/static/jsp/service-create.jsp?serviceId="+obj.id;
    }
    
    function deleteService(obj) {
        $.ajax({
            type: "delete",
            dataType: "json",
            url: "/service/delete?id="+obj.id,
            async: false,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    refreshServiceTable();
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "删除服务失败。\n" + msg.errorMsg,
                        type: "error"
                    });
                }
            },
            error:function (xhr,exception) {
                error(xhr,exception);
            }
        });
    }

</script>
</body>
</html>
