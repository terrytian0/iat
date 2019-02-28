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
                    <h5>添加关键字</h5>
                    <div class="ibox-tools" style="margin-top: -9px;">
                        <a class="btn btn-primary btn-sm" id="testcaseAddKeyword">保存</a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="keywordTable" class="table table-hover"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="keywordToolbar" class="btn-group">
    <div>
        <select class="form-control m-b" id="keywordService" style="height: 30px" onchange="refreshKeywordTable()">
        </select>
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
        $('#keywordTable').bootstrapTable('resetView', {
            height: tableHeight()
        })
    });

    window.onload = function () {
        getService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId")
        if (serviceId != null) {
            $("#service").find("option[key='" + serviceId + "']").attr("selected", true);
        }
        initKeywordTable();
    };

    function refreshKeywordTable() {
        var serviceId = $("#keywordService option:selected").attr("key");
        localStorage.setItem("serviceId",serviceId);
        $('#keywordTable').bootstrapTable('refresh', {
            pageNumber: 1,
            pageSize: 10,
            url: "/keyword/search?serviceId="+serviceId,
        });
    };

    function initKeywordTable() {
        getKeywordService($.cookie("Authentication"));
        var serviceId = localStorage.getItem("serviceId")
        if (serviceId != null) {
            $("#keywordService").find("option[key='" + serviceId + "']").attr("selected", true);
        }
        serviceId = $("#keywordService option:selected").attr("key");
        $('#keywordTable').bootstrapTable({
            method: 'get',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            url: "/keyword/search?serviceId="+serviceId,
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
                }
            ],
            locale: 'zh-CN',
            responseHandler: function (res) {
                return res.content;
            },
            onExpandRow: function (index, row, $detail) {
                oTableInit.InitKeywordSubTable(index, row, $detail);
            }
        });
    }
    var clicktag = 0;
    $("#testcaseAddKeyword").click(function () {
        if (clicktag == 1) {
            swal({
                title: "提示！",
                text: "您已经提交了请求，请稍等！",
                type: "error"
            });
            return;
        }
        var keywordIds = jQuery(".table tbody input[type=checkbox]:checked").map(function () {
            return jQuery(this).val();
        }).get().join(',');
        if (keywordIds == null || keywordIds == "" || keywordIds == undefined) {
            swal({
                title: "请选择需要添加的关键字！",
                type: "error"
            });
            return;
        }
        if (clicktag == 0) {
            clicktag = 1;
        }
        var testcaseId = window.location.href.getQuery("testcaseId");
        var data = "{";
        data = data + "\"testcaseId\":" + testcaseId + ",";
        data = data + "\"ids\":[" + keywordIds + "]";
        data = data + "}";
        $.ajax({
            type: "put",
            dataType: "json",
            url: "/testcase/keyword/add",
            async: false,
            data: data,
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    self.location = document.referrer;
                } else if (msg.code == "D0000104") {
                    window.location.href = "/login.jsp";
                } else {
                    swal({
                        title: "提示！",
                        text:  msg.message,
                        type: "error"
                    });
                    clicktag = 0;
                }

            }
        });
    });
</script>
</body>
</html>
