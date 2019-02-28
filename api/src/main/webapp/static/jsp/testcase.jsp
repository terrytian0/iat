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
                    <h5>测试用例</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="testcaseTable" class="table table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="testcaseToolbar" class="btn-group">
    <select class="btn btn-default" id="testcaseService" style="height: 30px" onchange="refreshTestcase()">
    </select>
    <button id="createTestcase" type="button" class="btn btn-primary" onclick="createTestcase();"
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


    function refreshTestcase() {
        var serviceId = $("#testcaseService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        $('#testcaseTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/testcase/search?serviceId=" + serviceId,
        });
    }


    function createTestcase() {
        window.location.href = "/static/jsp/testcase-create.jsp";
    }

    function addKeyword(obj) {
        window.location.href = "/static/jsp/testcase-addkeyword.jsp?testcaseId=" + obj.id;
    }

    function testcaseOptFormatter(value, row, index) {
        return "<a class=\"fa fa-plus-circle\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"addKeyword(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-edit\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"updateTestcase(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-remove\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"deleteTestcase(this)\"></a>"
    }


    function updateTestcase(obj) {
        window.location.href = "/static/jsp/testcase-detail.jsp?testcaseId=" + obj.id;
    }



    function deleteTestcase(obj) {
        var data = "[";
        data = data + obj.id + "";
        data = data + "]";
        $.ajax({
            type: "delete",
            url: "/testcase/delete",
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
                        text: "删除成功",
                        type: "info"
                    });
                    refreshTestcase();
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text: "删除testcase失败！errormsg=" + msg.errorMsg,
                        type: "error"
                    });
                }

            }

        });
    }

    function rankKeyword(obj, opt) {
        var data = "{";
        data = data + "\"index\":" + "\"" + opt + "\",";
        data = data + "\"testcaseKeywordId\":" + obj.id + ",";
        data = data + "\"testcaseId\":" + obj.getAttribute("name");
        data = data + "}";
        $.ajax({
            type: "PUT",
            url: "/testcase/idx",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testcaseSubTable-" +obj.getAttribute("name")).bootstrapTable('refresh');
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


    function testcaseKeywordOptFormatter(value, row, index) {
        return "<a class=\"fa fa-arrow-up\" style=\"width: 14px\"  name=\"" + row.testcaseId + "\"   id=\"" + row.id + "\"  onclick=\"keywordUp(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-arrow-down\" style=\"width: 14px\"  name=\"" + row.testcaseId + "\"   id=\"" + row.id + "\"  onclick=\"keywoedDown(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-remove\" style=\"width: 14px\"  name=\"" + row.testcaseId + "\"  id=\"" + row.id + "\"  onclick=\"removeKeyword(this)\"></a>"
    }

    function keywordUp(obj) {
        rankKeyword(obj, "UP")
    }

    function keywoedDown(obj) {
        rankKeyword(obj, "DOWN")
    }


    function removeKeyword(obj) {
        var data = "{"
        data = data + "\"testcaseId\":" + obj.getAttribute("name");
        var ids = "[";
        ids = ids + obj.id + "";
        ids = ids + "]";
        data = data + ",\"ids\":" + ids;
        data = data + "}";
        $.ajax({
            type: "delete",
            url: "/testcase/keyword/remove",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testcaseSubTable-" + obj.getAttribute("name")).bootstrapTable('refresh');
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


    function initTestcaseTable() {
        getTestcaseService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId");
        if (serviceId != "undefined") {
            $("#testcaseService").find("option[key='" + serviceId + "']").attr("selected", true);
        }
        serviceId = $("#testcaseService option:selected").attr("key");
        if (serviceId != undefined) {
            localStorage.setItem("serviceId", serviceId);
        }
        $('#testcaseTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/testcase/search?serviceId=" + serviceId,
            height: tableHeight(),
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            toolbar: '#testcaseToolbar',
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
                    formatter: testcaseOptFormatter,
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
                oTableInit.InitTestcaseSubTable(index, row, $detail);
            }
        });
    }


    oTableInit.InitTestcaseSubTable = function (index, row, $detail) {
        var cur_table = $detail.html('<table id=\"testcaseSubTable-' + row.id + '\"></table>').find('table');
        var testcaseId = row.id;
        $(cur_table).bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/testcase/info",
            height: tableHeight,
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            queryParams: {id: testcaseId},
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
                    formatter: testcaseKeywordOptFormatter,
                    width: 200
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                if (res.status) {
                    var data = res.content.keywords;
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
        initTestcaseTable();
    };

</script>
</body>
</html>
