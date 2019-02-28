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
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInUp">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>测试任务</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="testtaskTable" class="table table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="testtaskToolbar" class="btn-group">
    <select class="btn btn-default" id="testtaskService" style="height: 30px" onchange="refreshTesttask()">
    </select>
</div>

<!-- 全局js -->
<script src="/static/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/js/bootstrap.min.js?v=3.3.6"></script>
<!-- 自定义js -->
<script src="/static/js/content.js?v=1.0.0"></script>
<script src="/static/js/iat.js"></script>
<script src="/static/js/plugins/cookie/jquery.cookie.js"></script>
<script src="/static/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/static/js/plugins/layer/layer.min.js"></script>
<script>

    var role = $.cookie('role');

    $(document).ready(function () {
        if (localStorage.lastname) {
            $('a[href=' + localStorage.lastname + ']').tab('show');
        }
        $(document.body).on("click", "a[data-toggle]", function (event) {
            localStorage.lastname = this.getAttribute("href");

        });
    });

    var oTableInit = new Object();


    function refreshTesttask() {
        var serviceId = $("#testtaskService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        $('#testtaskTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/task/search?serviceId=" + serviceId,
        });
    }


    function testtaskDetail(obj) {
        var testtaskId = obj.id;
        window.location.href = "/static/jsp/testtask-detail.jsp?testtaskId=" + testtaskId;
    }


    function testtaskOptFormatter(value, row, index) {
        return "<a class=\"btn btn-primary btn-sm\"  id=\"" + row.id + "\" onclick=\"testtaskDetail(this)\">详情</a>" +
            "   <a class=\"btn btn-primary btn-sm\"  id=\"" + row.id + "\" onclick=\"testtaskInterrupt(this)\">中断</a>" +
            "   <a class=\"btn btn-primary btn-sm\"  id=\"" + row.id + "\" onclick=\"testtaskRetry(this)\">重试</a>"
    }

    function testtaskStatusFormatter(value, row, index) {
        if (row.status == undefined) {
            return;
        }
        if (row.status == "FINISHED") {
            return '<span style="color:#7266ba;">完成</span>'
        } else if (row.status == "FAILED") {
            return '<span style="color:#ed5565;">失败</span>'
        } else if (row.status == "RUNNING") {
            return '<span style="color:#27c24c;">运行中</span>'
        } else if (row.status == "TIMEOUT") {
            return '<span style="color:#fad733;">超时</span>'
        } else if (row.status == "INTERRUPT") {
            return '<span style="color:#fad733;">中断</span>'
        } else if (row.status == "CREATE") {
            return '<span style="color:#23b7e5;">新建</span>'
        } else {
            return value;
        }
    }


    function passRateFormatter(value, row, index) {
        if (value != 100) {
            return '<span style="color:#c12e2a;">' + value + '%</span>'
        } else {
            return '<span style="color:#3e8f3e;">' + value + '%</span>';
        }
    }

    function coverageFormatter(value, row, index) {
        return value + "%";
    }


    function initTesttaskTable() {
        getTesttaskService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId");
        if (serviceId != "undefined") {
            $("#testtaskService").find("option[key='" + serviceId + "']").attr("selected", true);
        }
        serviceId = $("#testtaskService option:selected").attr("key");
        if (serviceId != undefined) {
            localStorage.setItem("serviceId", serviceId);
        }
        $('#testtaskTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/task/search?serviceId=" + serviceId,
            height: tableHeight(),
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            toolbar: '#testtaskToolbar',
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
                    title: '测试计划',
                    field: 'testplanName'
                },
                {
                    title: '标签',
                    field: 'tag'
                }, {
                    title: '通过率',
                    field: 'passRate',
                    formatter: passRateFormatter
                }, {
                    title: '接口覆盖率',
                    field: 'coverage',
                    formatter: coverageFormatter
                }, {
                    title: '状态',
                    field: 'status',
                    formatter: testtaskStatusFormatter
                },
                {
                    title: '开始时间',
                    field: 'startTime',
                    formatter: dateFormatter
                }, {
                    title: '完成时间',
                    field: 'endTime',
                    formatter: dateFormatter
                }
                , {
                    title: '耗时(毫秒)',
                    field: 'elapsed'
                },
                {
                    title: '创建人',
                    field: 'createUser',
                    visible: false
                },
                {
                    title: '创建时间',
                    field: 'createTime',
                    formatter: dateFormatter,
                    visible: false
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
                }
                , {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: testtaskOptFormatter,
                    width: 250
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

    window.onload = function () {
        initTesttaskTable();
    };
</script>
</body>
</html>
