<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link href="/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
</head>

<style type="text/css">
    body {
        background: #d4d0c8;
    }
    td {
        border: 1px solid transparent !important;
    }
</style>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <form role="form" class="form-horizontal m-t well-g" id="jacocoForm">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>测试任务详情</h5>
                    </div>
                    <div class="ibox-content">
                        <div class="table-responsive">
                            <table id="testcaseTable" class="table table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
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

    var taskId;
    var oTableInit = new Object();
    function initTestcaseTable() {
        $('#testcaseTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/task/testcase?taskId=" + taskId,
            height: tableHeight(),
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            queryParamsType: '',
            sidePagination: 'client',
            pageNumber: 1,
            pageSize: 1000,
            pageList: [1000],
            detailView: true,
            clickToSelect: true,
            dataField: "list",
            showHeader:false,
            columns: [
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                },
                {
                    title: '用例Id',
                    field: 'testcaseId',
                    visible: false
                },
                {
                    title: '名称',
                    field: 'name'
                },
                {
                    title: '描述',
                    field: 'description'
                },
                {
                    title: '状态',
                    field: 'status',
                    formatter:testStatusFormatter,
                    width: 100
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
                oTableInit.InitTestcaseParameterTable(index, row, $detail);
            }
        });
    }

    function testStatusFormatter(value, row, index) {
        if (row.status=="true") {
            return '<span style="color:#27c24c;">Pass</span>'
        } else if(row.status=="false"){
            return '<span style="color:#ed5565;">Fail</span>'
        }else{
            return '<span style="color:#23b7e5;">Not Run</span>'
        }
    }

    oTableInit.InitTestcaseParameterTable = function (index, row, $detail) {
        var cur_table = $detail.html('<table id=\"parameterTable-' + row.id + '\"></table>').find('table');
        var testcaseId = row.testcaseId;
        $(cur_table).bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/task/parameter?taskId="+taskId+"&testcaseId="+testcaseId,
            height: tableHeight,
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            queryParamsType: '',
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 1000,
            pageList: [1000],
            detailView: true,
            clickToSelect: true,
            dataField: "list",
            showHeader:false,
            columns: [
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                }, {
                    title: '参数',
                    field: 'parameters'
                }, {
                    title: '状态',
                    field: 'status',
                    formatter:testStatusFormatter,
                    width: 100
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                var data = res.content;
                return data;
            },
            onResetView: function (data) {
                var aa = $("div .fixed-table-container");
                aa.css("padding-bottom", "0px");
            },
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitTestcaseKeywordTable(index, row, $detail);
            }
        });
        return oTableInit;
    };

    oTableInit.InitTestcaseKeywordTable = function (index, row, $detail) {
        // console.log(index)
        // console.log(row)
        // console.log($detail)
        var cur_table = $detail.html('<table id=\"keywordTable-' + row.id + '\"></table>').find('table');
        var testcaseId = row.testcaseId;
        var parameterId = row.id;
        $(cur_table).bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/task/keyword?taskId="+taskId+"&testcaseId="+testcaseId+"&parameterId="+parameterId,
            height: tableHeight,
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            queryParamsType: '',
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 1000,
            pageList: [1000],
            detailView: true,
            clickToSelect: true,
            dataField: "list",
            showHeader:false,
            columns: [
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                }, {
                    title: '关键字',
                    field: 'name'
                }, {
                    title: '描述',
                    field: 'description'
                }, {
                    title: '状态',
                    field: 'status',
                    formatter:testStatusFormatter,
                    width: 100
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                var data = res.content;
                return data;
            },
            onResetView: function (data) {
                var aa = $("div .fixed-table-container");
                aa.css("padding-bottom", "0px");
            },
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitTestcaseKeywordApiTable(index, row, $detail,parameterId);
            }
        });
        return oTableInit;
    };

    oTableInit.InitTestcaseKeywordApiTable = function (index, row, $detail,parameterId) {
        var cur_table = $detail.html('<table id=\"keywordTable-' + row.id + '\"></table>').find('table');
        var keywordId = row.keywordId;
        var testcaseId = row.testcaseId;
        var testcaseKeywordId = row.testcaseKeywordId;
        $(cur_table).bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/task/api?taskId="+taskId+"&testcaseId="+testcaseId+"&keywordId="+keywordId+"&testcaseKeywordId="+testcaseKeywordId+"&parameterId="+parameterId,
            height: tableHeight,
            ajaxOptions: {
                headers: {
                    "Authentication": $.cookie("Authentication")
                }
            },
            queryParamsType: '',
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 1000,
            pageList: [1000],
            detailView: true,
            clickToSelect: true,
            dataField: "list",
            showHeader:false,
            columns: [
                {
                    title: 'ID',
                    field: 'id',
                    visible: false
                }, {
                    title: '路径',
                    field: 'path'
                }, {
                    title: '方法',
                    field: 'method'
                }, {
                    title: '状态',
                    field: 'status',
                    formatter:testStatusFormatter,
                    width: 100
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                var data = res.content;
                return data;
            },
            onResetView: function (data) {
                var aa = $("div .fixed-table-container");
                aa.css("padding-bottom", "0px");
            },
            onExpandRow: function (index, row, $detail) {
            }
        });
        return oTableInit;
    };


    window.onload = function () {
        taskId = window.location.href.getQuery("testtaskId");
        initTestcaseTable();
    }

</script>
</body>
</html>
