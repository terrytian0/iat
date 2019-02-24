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
            <div class="tabs-container">
                <ul class="nav nav-tabs" id="tabs">
                    <li class="active" id="keyword-tab">
                        <a data-toggle="tab" href="#keyword" aria-expanded="true">关键字</a>
                    </li>
                    <li class="" id="testcase-tab">
                        <a data-toggle="tab" href="#testcase" aria-expanded="false">测试用例</a>
                    </li>
                    <li class="" id="testplan-tab">
                        <a data-toggle="tab" href="#testplan" aria-expanded="false">测试计划</a>
                    </li>
                    <li class="" id="testtask-tab">
                        <a data-toggle="tab" href="#testtask" aria-expanded="false">测试任务</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div id="keyword" class="tab-pane active">
                        <div class="panel-body">
                            <div class="ibox float-e-margins">
                                <div class="ibox-content">
                                    <div class="table-responsive">
                                        <table id="keywordTable" class="table text-nowrap"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="testcase" class="tab-pane">
                        <div class="panel-body">
                            <div class="ibox float-e-margins">
                                <div class="ibox-content">
                                    <div class="table-responsive">
                                        <table id="testcaseTable" class="table table-hover"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="testplan" class="tab-pane">
                        <div class="panel-body">
                            <div class="ibox float-e-margins">
                                <div class="ibox-content">
                                    <div class="table-responsive">
                                        <table id="testplanTable" class="table table-hover"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="testtask" class="tab-pane">
                        <div class="panel-body">
                            <div class="ibox float-e-margins">
                                <div class="ibox-content">
                                    <div class="table-responsive">
                                        <table id="testtaskTable" class="table table-hover"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
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

<div id="testcaseToolbar" class="btn-group">
    <select class="btn btn-default" id="testcaseService" style="height: 30px" onchange="refreshTestcase()">
    </select>
    <button id="createTestcase" type="button" class="btn btn-primary" onclick="createTestcase();"
            style="margin-left:5px">
        创建
    </button>
</div>

<div id="testplanToolbar" class="btn-group">
    <select class="btn btn-default" id="testplanService" style="height: 30px" onchange="refreshTestplan()">
    </select>
    <button id="createTestplan" type="button" class="btn btn-primary" onclick="createTestplan();"
            style="margin-left:5px">
        创建
    </button>
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
    $(window).resize(function () {
        $('#keywordTable').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });
    var oTableInit = new Object();


    $('#keyword-tab').click(function () {
        initKeywordTable();

    });

    $('#testcase-tab').click(function () {
        initTestcaseTable();

    });
    $('#testplan-tab').click(function () {
        // alert("testplan-tab");
    });
    $('#testtask-tab').click(function () {
        // alert("testtask-tab");
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

    function refreshTestcase() {
        var serviceId = $("#testcaseService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        $('#testcaseTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/testcase/search?serviceId=" + serviceId,
        });
    }

    function refreshTestplan() {
        var serviceId = $("#testplanService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        $('#testplanTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/testplan/search?serviceId=" + serviceId,
        });
    }

    function refreshTesttask() {
        var serviceId = $("#testtaskService option:selected").attr("key");
        localStorage.setItem("serviceId", serviceId);
        $('#testtaskTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/task/search?serviceId=" + serviceId,
        });
    }

    function createKeyword() {
        // var serviceId = $("#keywordService option:selected").attr("key");
        window.location.href = "/static/jsp/keyword-create.jsp";
    }

    function createTestcase() {
        // var serviceId = $("#testcaseService option:selected").attr("key");
        window.location.href = "/static/jsp/testcase-create.jsp";
    }

    function createTestplan() {
        // var serviceId = $("#testplanService option:selected").attr("key");
        window.location.href = "/static/jsp/testplan-create.jsp";
    }
    
    function testtaskDetail(obj) {
        var testtaskId = obj.id;
        window.location.href = "/static/jsp/testtask-detail.jsp?testtaskId="+testtaskId;
    }


    function initKeywordTable() {
        getKeywordService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId")
        if (serviceId != undefined) {
            $("#keywordService").find("option[key='" + serviceId + "']").attr("selected", true);
        } else {
            serviceId = $("#keywordService option:selected").attr("key");
            localStorage.setItem("serviceId", serviceId);
        }
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
        return "<a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\" onclick=\"addApi(this)\" >添加Api</a>"
            + "   <a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\" onclick=\"debugKeyword(this)\" >修改</a>"
            + "   <a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\" onclick=\"deleteKeyword(this)\" >删除</a>"


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

    function testcaseOptFormatter(value, row, index) {
        return "<a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\"  onclick=\"addKeyword(this)\" >添加关键字</a>"
            + "   <a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\"  onclick=\"updateTestcase(this)\" >修改</a>"
            + "   <a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\"  onclick=\"deleteTestcase(this)\" >删除</a>"

    }

    function testplanOptFormatter(value, row, index) {
        return "<a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\"  onclick=\"addTestcase(this)\" >添加</a>"
            + "   <a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\"  onclick=\"updateTestplan(this)\" >修改</a>"
            + "   <a class=\"btn btn-primary btn-sm\" id=\"" + row.id + "\"  onclick=\"runTestplan(this)\" >执行</a>"
            + "   <a class=\"btn btn-danger btn-sm\" id=\"" + row.id + "\"  onclick=\"deleteTestplan(this)\" >删除</a>"



    }

    function addKeyword(obj) {
        window.location.href = "/static/jsp/testcase-addkeyword.jsp?testcaseId=" + obj.id;
    }

    function updateTestcase(obj) {
        window.location.href = "/static/jsp/testcase-detail.jsp?testcaseId=" + obj.id;
    }
    
    function updateTestplan(obj) {
        window.location.href = "/static/jsp/testplan-detail.jsp?testplanId=" + obj.id;
    }

    function addTestcase(obj) {
        window.location.href = "/static/jsp/testplan-addtestcase.jsp?testplanId=" + obj.id;
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
    
    function runTestplan(obj) {
        $.ajax({
            type: "post",
            url: "/task/create?testplanId="+obj.id,
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
            url: "/testplan/delete?testplanId="+obj.id,
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
        data = data + "\"testplanId\":" + obj.name;
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
                    $("#testplanSubTable-" + obj.name).bootstrapTable('refresh');
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




    function rankApi(obj, opt) {
        var data = "{";
        data = data + "\"index\":" + "\"" + opt + "\",";
        data = data + "\"keywordApiId\":" + obj.id + ",";
        data = data + "\"keywordId\":" + obj.name;
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
                    $("#keywordSubTable-" + obj.name).bootstrapTable('refresh');
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
        var data = "[";
        data = data + obj.id + "";
        data = data + "]";
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
                    $("#keywordSubTable-" + obj.name).bootstrapTable('refresh');
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
        return "<a class=\"btn btn-primary btn-sm\" name=\"" + row.testcaseId + "\"  id=\"" + row.id + "\" onclick=\"keywordUp(this)\">上移</a>" +
            "   <a class=\"btn btn-primary btn-sm\" name=\"" + row.testcaseId + "\"  id=\"" + row.id + "\" onclick=\"keywoedDown(this)\">下移</a>" +
            "   <a class=\"btn btn-danger btn-sm\" name=\"" + row.testcaseId + "\" id=\"" + row.id + "\" onclick=\"removeKeyword(this)\">删除</a>"
    }


    function testplanTestcaseOptFormatter(value, row, index) {
        return "<a class=\"btn btn-primary btn-sm\" name=\"" + row.testplanId + "\"  id=\"" + row.id + "\" onclick=\"testcaseUp(this)\">上移</a>" +
            "   <a class=\"btn btn-primary btn-sm\" name=\"" + row.testplanId + "\"  id=\"" + row.id + "\" onclick=\"testcaseDown(this)\">下移</a>" +
            "   <a class=\"btn btn-danger btn-sm\" name=\"" + row.testplanId + "\" id=\"" + row.id + "\" onclick=\"removeTestcase(this)\">删除</a>"
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
        if (row.status=="SUCCEED") {
            return "<a class=\" btn btn-w-m btn-success\">成功</a>";
        } else if(row.status=="FAILED"){
            return "<a class=\" btn btn-w-m btn-danger\" id = \"errormsg\">失败</a>";
        }else{
            return value;
        }
    }


    function passRateFormatter(value, row, index) {
        return value+"%";
    }

    function coverageFormatter(value, row, index) {
        return value+"%";
    }

    function keywordUp(obj) {
        rankKeyword(obj, "UP")
    }

    function keywoedDown(obj) {
        rankKeyword(obj, "DOWN")
    }

    function testcaseUp(obj) {
        rankTestcase(obj, "UP")
    }

    function testcaseDown(obj) {
        rankTestcase(obj, "DOWN")
    }

    function removeTestcase(obj) {
        var data = "[";
        data = data + obj.id;
        data = data + "]";
        $.ajax({
            type: "delete",
            url: "/testplan/testcase/remove",
            dataType: 'json',
            data:data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testplanSubTable-" + obj.name).bootstrapTable('refresh');
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

    function removeKeyword(obj) {
        var data = "[";
        data = data + obj.id;
        data = data + "]";
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
                    title: '名称',
                    field: 'detail.name'
                }, {
                    title: '路径',
                    field: 'detail.path'
                }, {
                    title: '方法',
                    field: 'detail.method',
                    formatter: methodFormatter
                }, {
                    title: 'ApiID',
                    field: 'apiId',
                    visible: false
                }, {
                    title: '排序',
                    field: 'idx'
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
        return "<a class=\"btn btn-primary btn-sm\" name=\"" + row.keywordId + "\" id=\"" + row.id + "\" onclick=\"apiUp(this)\">上移</a>" +
            "   <a class=\"btn btn-primary btn-sm\" name=\"" + row.keywordId + "\" id=\"" + row.id + "\" onclick=\"apiDown(this)\">下移</a>" +
            "   <a class=\"btn btn-danger btn-sm\" name=\"" + row.keywordId + "\" id=\"" + row.id + "\" onclick=\"removeApi(this)\">删除</a>";
    }

    function initTestcaseTable() {
        getTestcaseService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId");
        if (serviceId != "undefined") {
            $("#testcaseService").find("option[key='" + serviceId + "']").attr("selected", true);
        } else {
            serviceId = $("#testcaseService option:selected").attr("key");
            if (serviceId != undefined) {
                localStorage.setItem("serviceId", serviceId);
            } else {
                return;
            }
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


    function initTestplanTable() {
        getTestplanService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId");
        if (serviceId != "undefined") {
            $("#testcaseService").find("option[key='" + serviceId + "']").attr("selected", true);
        } else {
            serviceId = $("#testcaseService option:selected").attr("key");
            if (serviceId != undefined) {
                localStorage.setItem("serviceId", serviceId);
            } else {
                return;
            }
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

    function initTesttaskTable() {
        getTesttaskService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId");
        if (serviceId != "undefined") {
            $("#testcaseService").find("option[key='" + serviceId + "']").attr("selected", true);
        } else {
            serviceId = $("#testcaseService option:selected").attr("key");
            if (serviceId != undefined) {
                localStorage.setItem("serviceId", serviceId);
            } else {
                return;
            }
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
                },{
                    title: '通过率',
                    field: 'passRate',
                    formatter: passRateFormatter
                },{
                    title: '接口覆盖率',
                    field: 'coverage',
                    formatter: coverageFormatter
                },{
                    title: '状态',
                    field: 'status',
                    formatter:testtaskStatusFormatter
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
        initKeywordTable();
        initTestcaseTable();
        initTestplanTable();
        initTesttaskTable();
    };
</script>
</body>
</html>
