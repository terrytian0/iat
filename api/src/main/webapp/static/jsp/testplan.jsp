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
                    <h5>测试计划</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="testplanTable" class="table table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="testplanToolbar" class="btn-group">
    <select class="btn btn-default" id="testplanService" style="height: 30px" onchange="refreshTestplan()">
    </select>
    <button id="createTestplan" type="button" class="btn btn-primary" onclick="createTestplan();"
            style="margin-left:5px">
        创建
    </button>
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

    function refreshTestplan() {
        var serviceId = $("#testplanService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        $('#testplanTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/testplan/search?serviceId=" + serviceId,
        });
    }

    function createTestplan() {
        window.location.href = "/static/jsp/testplan-create.jsp";
    }


    function testplanOptFormatter(value, row, index) {
        return "<a class=\"fa fa-plus-circle\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"addTestcase(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-edit\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"updateTestplan(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-remove\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"deleteTestplan(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-toggle-right\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"runTestplan(this)\"></a>"
    }


    function updateTestplan(obj) {
        window.location.href = "/static/jsp/testplan-detail.jsp?testplanId=" + obj.id;
    }

    function addTestcase(obj) {
        window.location.href = "/static/jsp/testplan-addtestcase.jsp?testplanId=" + obj.id;
    }


    function runTestplan(obj) {
        $.ajax({
            type: "post",
            url: "/task/create?testplanId=" + obj.id,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    swal({
                        title: "提示！",
                        text: "执行测试计划成功",
                        type: "info"
                    });
                    refreshTestplan();
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

    function deleteTestplan(obj) {
        $.ajax({
            type: "delete",
            url: "/testplan/delete?testplanId=" + obj.id,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    swal({
                        title: "提示！",
                        text: "删除成功",
                        type: "info"
                    });
                    refreshTestplan();
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

    function rankTestcase(obj, opt) {
        var data = "{";
        data = data + "\"index\":" + "\"" + opt + "\",";
        data = data + "\"testplanTestcaseId\":" + obj.id + ",";
        data = data + "\"testplanId\":" + obj.getAttribute("name");
        data = data + "}";
        $.ajax({
            type: "PUT",
            url: "/testplan/idx",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testplanSubTable-" + obj.getAttribute("name")).bootstrapTable('refresh');
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


    function testplanTestcaseOptFormatter(value, row, index) {
        return "<a class=\"fa fa-arrow-up\" style=\"width: 14px\"  name=\"" + row.testplanId + "\"   id=\"" + row.id + "\"  onclick=\"testcaseUp(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-arrow-down\" style=\"width: 14px\"  name=\"" + row.testplanId + "\"   id=\"" + row.id + "\"  onclick=\"testcaseDown(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-remove\" style=\"width: 14px\"  name=\"" + row.testplanId + "\"  id=\"" + row.id + "\"  onclick=\"removeTestcase(this)\"></a>"
    }


    function testcaseUp(obj) {
        rankTestcase(obj, "UP")
    }

    function testcaseDown(obj) {
        rankTestcase(obj, "DOWN")
    }

    function removeTestcase(obj) {
        var data = "{"
        data = data + "\"testplanId\":" + obj.getAttribute("name");
        var ids = "[";
        ids = ids + obj.id + "";
        ids = ids + "]";
        data = data + ",\"testcaseIds\":" + ids;
        data = data + "}";
        $.ajax({
            type: "delete",
            url: "/testplan/testcase/remove",
            dataType: 'json',
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testplanSubTable-" + obj.getAttribute("name")).bootstrapTable('refresh');
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


    function initTestplanTable() {
        getTestplanService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId");
        if (serviceId != "undefined") {
            $("#testplanService").find("option[key='" + serviceId + "']").attr("selected", true);
        }
        serviceId = $("#testplanService option:selected").attr("key");
        if (serviceId != undefined) {
            localStorage.setItem("serviceId", serviceId);
        }
        $('#testplanTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/testplan/search?serviceId=" + serviceId,
            height: tableHeight(),
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            toolbar: '#testplanToolbar',
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
            detailView: true,
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
                    title: '名称',
                    field: 'name'
                },
                {
                    title: '策略',
                    field: 'strategy'
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
                    formatter: testplanOptFormatter,
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
            },
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitTestplanSubTable(index, row, $detail);
            }
        });
    }

    oTableInit.InitTestplanSubTable = function (index, row, $detail) {
        var cur_table = $detail.html('<table id=\"testplanSubTable-' + row.id + '\"></table>').find('table');
        var testplanId = row.id;
        $(cur_table).bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/testplan/info",
            height: tableHeight,
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            queryParams: {testplanId: testplanId},
            striped: true,
            idField: "id",
            dataField: "list",
            pageNumber: 1,
            pagination: false,
            queryParamsType: '',
            pageSize: 10,
            pageList: [5, 10, 20, 30],
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            paginationFirstText: "首页",
            paginationLastText: "尾页",
            showRefresh: false,
            showColumns: false,
            search: false,
            clickToSelect: false,
            columns: [
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                }, {
                    title: '排序',
                    field: 'idx'
                }, {
                    title: '名称',
                    field: 'detail.name'
                }, {
                    title: '创建人',
                    field: 'detail.createUser'
                }, {
                    title: '创建时间',
                    field: 'detail.createTime',
                    formatter: dateFormatter
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: testplanTestcaseOptFormatter,
                    width: 200
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                if (res.status) {
                    var data = res.content.testcases;
                    return data;
                } else if (res.code == "D0000104") {
                    window.location.href = "/login.jsp";
                }

            },
            onResetView: function (data) {
                var aa = $("div .fixed-table-container");
                aa.css("padding-bottom", "0px");
            }
        });
        return oTableInit;
    };

    window.onload = function () {
        initTestplanTable();
    };
</script>
</body>
</html>
