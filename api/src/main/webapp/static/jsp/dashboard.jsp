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
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="row row-sm text-center">
            <div class="col-xs-3">
                <div class="panel padder-v item label-info">
                    <div class="h1 text-fff font-thin h1" id="apiCount">0</div>
                    <span class="text-muted text-xs">接口数</span>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="panel padder-v item bg-info">
                    <div class="h1 text-fff font-thin h1" id="testcaseCount">0</div>
                    <span class="text-muted text-xs">用例数</span>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="panel padder-v item bg-primary">
                    <div class="h1 text-fff font-thin h1" id="testplanCount">0</div>
                    <span class="text-muted text-xs">计划数</span>
                </div>
            </div>
            <div class="col-xs-3">
                <div class="panel padder-v item label-success">
                    <div class="font-thin h1" id="taskCount">0</div>
                    <span class="text-muted text-xs">任务数</span>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row row-sm text-center">
            <div class="col-sm-12">
                <div class="row">
                    <div class="col-lg-6">
                        <div id="apiChart" style="height:300px;"></div>
                    </div>
                    <div class="col-lg-6">
                        <div id="caseChart" style="height:300px;"></div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6">
                        <div id="planChart" style="height:300px;"></div>
                    </div>
                    <div class="col-lg-6">
                        <div id="taskChart" style="height:300px;"></div>
                    </div>
                </div>
            </div>
            <%--<div class="col-sm-4">--%>
                <%--<div class="ibox-title">--%>
                    <%--<h5>消息</h5>--%>
                <%--</div>--%>
                <%--<div class="ibox-content">--%>
                <%--</div>--%>
            <%--</div>--%>
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
<script src="/static/js/plugins/flot/jquery.flot.js"></script>
<script src="/static/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="/static/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="/static/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="/static/js/plugins/echarts/echarts.min.js"></script>

<!-- Peity -->
<script src="/static/js/iat.js"></script>
<script>
    window.onload = function () {
        getApiCount();
        getTestcaseCount();
        getTestplanCount();
        getTaskCount();
        getApiWeekChart("api");
        getApiWeekChart("testcase");
        getApiWeekChart("testplan");
        getApiWeekChart("task");
    }

    function drawApiChart(data) {
        var dataX = new Array(), dataY = new Array();
        for (var i = 0; i < data.length; i++) {
            dataX.push(data[i].time);
            dataY.push(data[i].count);
        }
        echarts.init(document.getElementById('apiChart')).setOption({
            title: {
                left: 'left',
                text: '接口',
            },
            grid: {
                left: '1%',
                right: '4%',
                bottom: '5%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                formatter: "日期 : {b}</br>接口: {c}"
            },
            xAxis: {
                type: 'category',
                data: dataX
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: dataY,
                type: 'line'
            }]
        });
    }

    function drawCaseChart(data) {
        var dataX = new Array(), dataY = new Array();
        for (var i = 0; i < data.length; i++) {
            dataX.push(data[i].time);
            dataY.push(data[i].count);
        }
        echarts.init(document.getElementById('caseChart')).setOption({
            title: {
                left: 'left',
                text: '测试用例',
            },
            grid: {
                left: '1%',
                right: '4%',
                bottom: '5%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                formatter: "日期 : {b}</br>用例: {c}"
            },
            xAxis: {
                type: 'category',
                data: dataX
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: dataY,
                type: 'line'
            }]
        });
    }

    function drawPlanChart(data) {
        var dataX = new Array(), dataY = new Array();
        for (var i = 0; i < data.length; i++) {
            dataX.push(data[i].time);
            dataY.push(data[i].count);
        }
        echarts.init(document.getElementById('planChart')).setOption({
            title: {
                left: 'left',
                text: '测试计划',
            },
            grid: {
                left: '1%',
                right: '4%',
                bottom: '5%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                formatter: "日期 : {b}</br>计划: {c}"
            },
            xAxis: {
                type: 'category',
                data: dataX
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: dataY,
                type: 'line'
            }]
        });
    }

    function drawTaskChart(data) {
        var dataX = new Array(), dataY = new Array();
        for (var i = 0; i < data.length; i++) {
            dataX.push(data[i].time);
            dataY.push(data[i].count);
        }
        echarts.init(document.getElementById('taskChart')).setOption({
            title: {
                left: 'left',
                text: '测试任务',
            },
            grid: {
                left: '1%',
                right: '4%',
                bottom: '5%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                formatter: "日期 : {b}</br>任务: {c}"
            },
            xAxis: {
                type: 'category',
                data: dataX
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: dataY,
                type: 'line'
            }]
        });
    }


    function getApiCount() {
        $.ajax({
            type: "get",
            url: "/api/count",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#apiCount").html(msg.content);
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

    function getTestcaseCount() {
        $.ajax({
            type: "get",
            url: "/testcase/count",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testcaseCount").html(msg.content);
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


    function getTestplanCount() {
        $.ajax({
            type: "get",
            url: "/testplan/count",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#testplanCount").html(msg.content);
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


    function getTaskCount() {
        $.ajax({
            type: "get",
            url: "/task/count",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    $("#taskCount").html(msg.content);
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


    function getApiWeekChart(type) {
        var url;
        if(type=="api"){
            url="/api/week-chart";
        }else if(type=="testcase"){
            url="/testcase/week-chart";
        }else if(type=="testplan"){
            url="/testplan/week-chart";
        }else if(type=="task"){
            url="/task/week-chart";
        }
        $.ajax({
            type: "get",
            url: url,
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (request) {
                request.setRequestHeader("Authentication", $.cookie("Authentication"));
            },
            success: function (msg) {
                if (msg.status) {
                    if(type=="api"){
                        drawApiChart(msg.content);
                    }else if(type=="testcase"){
                        drawCaseChart(msg.content)
                    }else if(type=="testplan"){
                        drawPlanChart(msg.content)
                    }else if(type=="task"){
                       drawTaskChart(msg.content)
                    }

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
