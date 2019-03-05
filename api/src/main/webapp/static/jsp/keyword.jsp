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
                    <h5>关键字</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="keywordTable" class="table text-nowrap"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="keywordToolbar" class="btn-group">
    <select class="btn btn-default" id="keywordService" style="height: 30px" onchange="refreshKeword()">
    </select>
    <button id="createKeyword" type="button" class="btn btn-primary" onclick="createKeyword();" style="margin-left:5px">
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

    $(window).resize(function () {
        $('#keywordTable').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });

    var oTableInit = new Object();


    $('#keyword-tab').click(function () {
        initKeywordTable();

    });


    function refreshKeword() {
        var serviceId = $("#keywordService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        $('#keywordTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/keyword/search?serviceId=" + serviceId,
        });
    }


    function createKeyword() {
        window.location.href = "/static/jsp/keyword-create.jsp";
    }


    function initKeywordTable() {
        getKeywordService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId")
        if (serviceId != undefined) {
            $("#keywordService").find("option[key='" + serviceId + "']").attr("selected", true);
        }
        serviceId = $("#keywordService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        serviceId = $("#keywordService option:selected").attr("key");
        $('#keywordTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/keyword/search?serviceId=" + serviceId,
            height: tableHeight(),
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            toolbar: '#keywordToolbar',
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
                    title: '关键字ID',
                    field: 'keywordId',
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
                    formatter: keywordOptFormatter,
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
            },
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitKeywordSubTable(index, row, $detail);
            }
        });
    }

    function keywordOptFormatter(value, row, index) {
        return "<a class=\"fa fa-plus-circle\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"addApi(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-edit\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"debugKeyword(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-remove\" style=\"width: 14px\"  id=\"" + row.id + "\"  onclick=\"deleteKeyword(this)\"></a>"
    }

    function addApi(obj) {
        window.location.href = "/static/jsp/keyword-addapi.jsp?keywordId=" + obj.id;
    }

    function debugKeyword(obj) {
        window.location.href = "/static/jsp/keyword-detail.jsp?keywordId=" + obj.id;
    }

    function deleteKeyword(obj) {
        var data = "[";
        data = data + obj.id + "";
        data = data + "]";
        $.ajax({
            type: "delete",
            url: "/keyword/delete",
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
                    refreshKeword();
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


    function addKeyword(obj) {
        window.location.href = "/static/jsp/testcase-addkeyword.jsp?testcaseId=" + obj.id;
    }


    function rankKeyword(obj, opt) {
        var data = "{";
        data = data + "\"index\":" + "\"" + opt + "\",";
        data = data + "\"testcaseKeywordId\":" + obj.id + ",";
        data = data + "\"testcaseId\":" + obj.name;
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
                    $("#testcaseSubTable-" + obj.name).bootstrapTable('refresh');
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


    function removeApi(obj) {
        var data = "{"
        data = data + "\"keywordId\":" + obj.getAttribute("name");
        var ids = "[";
        ids = ids + obj.id + "";
        ids = ids + "]";
        data = data + ",\"ids\":" + ids;
        data = data + "}";
        $.ajax({
            type: "delete",
            url: "/keyword/api/remove",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#keywordSubTable-" + obj.getAttribute("name")).bootstrapTable('refresh');
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


    function rankApi(obj, opt) {
        var data = "{";
        data = data + "\"index\":" + "\"" + opt + "\",";
        data = data + "\"keywordApiId\":" + obj.id + ",";
        data = data + "\"keywordId\":" + obj.getAttribute("name");
        data = data + "}";
        $.ajax({
            type: "PUT",
            url: "/keyword/idx",
            data: data,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#keywordSubTable-" + obj.getAttribute("name")).bootstrapTable('refresh');
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


    oTableInit.InitKeywordSubTable = function (index, row, $detail) {
        var cur_table = $detail.html('<table id=\"keywordSubTable-' + row.id + '\"></table>').find('table');
        var keywordId = row.id;
        $(cur_table).bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/keyword/info",
            height: tableHeight,
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            queryParams: {id: keywordId},
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
                    title: '路径',
                    field: 'detail.path'
                }, {
                    title: '方法',
                    field: 'detail.method',
                    formatter: methodFormatter
                }, {
                    title: '名称',
                    field: 'detail.name'
                }, {
                    title: 'ApiID',
                    field: 'apiId',
                    visible: false
                }, {
                    title: '排序',
                    field: 'idx',
                    visible: false
                }, {
                    title: '操作',
                    field: 'button',
                    align: 'center',
                    formatter: keywordApiOptFormatter,
                    width: 260
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                var data = res.content.apis;
                return data;
            },
            onResetView: function (data) {
                var aa = $("div .fixed-table-container");
                aa.css("padding-bottom", "0px");
            }
        });
        return oTableInit;
    };

    function keywordApiOptFormatter(value, row, index) {
        return "<a class=\"fa fa-arrow-up\" style=\"width: 14px\"  name=\"" + row.keywordId + "\"   id=\"" + row.id + "\"  onclick=\"apiUp(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-arrow-down\" style=\"width: 14px\"  name=\"" + row.keywordId + "\"   id=\"" + row.id + "\"  onclick=\"apiDown(this)\"></a>"
            + "&nbsp;&nbsp;<a class=\"fa fa-remove\" style=\"width: 14px\"  name=\"" + row.keywordId + "\"  id=\"" + row.id + "\"  onclick=\"removeApi(this)\"></a>"
    }

    function apiUp(obj) {
        rankApi(obj,"UP");
    }
    function apiDown(obj) {
        rankApi(obj,"DOWN");
    }

    window.onload = function () {
        initKeywordTable();
    };
</script>
</body>
</html>
