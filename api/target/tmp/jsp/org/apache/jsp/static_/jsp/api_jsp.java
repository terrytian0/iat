/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.12.v20180830
 * Generated at: 2019-02-14 03:29:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.static_.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class api_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("    <meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n");
      out.write("    <meta http-equiv=\"expires\" content=\"0\">\r\n");
      out.write("    <link rel=\"shortcut icon\" href=\"favicon.ico\">\r\n");
      out.write("    <link href=\"/static/css/bootstrap.min.css?v=3.3.6\" rel=\"stylesheet\">\r\n");
      out.write("    <link href=\"/static/css/font-awesome.css?v=4.4.0\" rel=\"stylesheet\">\r\n");
      out.write("    <link href=\"/static/css/plugins/bootstrap-table/bootstrap-table.min.css\" rel=\"stylesheet\">\r\n");
      out.write("    <link href=\"/static/css/animate.css\" rel=\"stylesheet\">\r\n");
      out.write("    <link href=\"/static/css/style.css?v=4.1.0\" rel=\"stylesheet\">\r\n");
      out.write("    <link href=\"/static/css/plugins/sweetalert/sweetalert.css\" rel=\"stylesheet\">\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"gray-bg\">\r\n");
      out.write("<div class=\"wrapper wrapper-content animated fadeInUp\">\r\n");
      out.write("    <div class=\"row\">\r\n");
      out.write("        <div class=\"col-sm-12\">\r\n");
      out.write("            <div class=\"ibox float-e-margins\">\r\n");
      out.write("                <div class=\"ibox-title\">\r\n");
      out.write("                    <h5>Api列表</h5>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"ibox-content\">\r\n");
      out.write("                    <div class=\"table-responsive\">\r\n");
      out.write("                        <table id=\"apiTable\" class=\"table table-hover\"></table>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("<div id=\"toolbar\" class=\"btn-group\">\r\n");
      out.write("    <div>\r\n");
      out.write("        <select class=\"form-control m-b\" id=\"service\" style=\"height: 30px\" onchange=\"refreshApiTable()\">\r\n");
      out.write("        </select>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!-- 全局js -->\r\n");
      out.write("<script src=\"/static/js/jquery.min.js?v=2.1.4\"></script>\r\n");
      out.write("<script src=\"/static/js/bootstrap.min.js?v=3.3.6\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- 自定义js -->\r\n");
      out.write("<script src=\"/static/js/content.js?v=1.0.0\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- Bootstrap table -->\r\n");
      out.write("<script src=\"/static/js/plugins/bootstrap-table/bootstrap-table.min.js\"></script>\r\n");
      out.write("<script src=\"/static/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js\"></script>\r\n");
      out.write("<script src=\"/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js\"></script>\r\n");
      out.write("<script src=\"/static/js/plugins/sweetalert/sweetalert.min.js\"></script>\r\n");
      out.write("<script src=\"/static/js/plugins/cookie/jquery.cookie.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- Peity -->\r\n");
      out.write("<script src=\"/static/js/iat.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("\r\n");
      out.write("    $(window).resize(function () {\r\n");
      out.write("        $('#apiTable').bootstrapTable('resetView', {\r\n");
      out.write("            height: tableHeight()\r\n");
      out.write("        })\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    window.onload = function () {\r\n");
      out.write("        getService($.cookie(\"Authentication\"));\r\n");
      out.write("        var serviceId = localStorage.getItem(\"serviceId\")\r\n");
      out.write("        if (serviceId != undefined) {\r\n");
      out.write("            $(\"#service\").find(\"option[key='\" + serviceId + \"']\").attr(\"selected\", true);\r\n");
      out.write("        }else{\r\n");
      out.write("            serviceId = $(\"#service option:selected\").attr(\"key\");\r\n");
      out.write("            localStorage.setItem(\"serviceId\",serviceId);\r\n");
      out.write("        }\r\n");
      out.write("        initApiTable();\r\n");
      out.write("    };\r\n");
      out.write("\r\n");
      out.write("    function refreshApiTable() {\r\n");
      out.write("        var serviceId = $(\"#service option:selected\").attr(\"key\");\r\n");
      out.write("        localStorage.setItem(\"serviceId\", serviceId);\r\n");
      out.write("        $('#apiTable').bootstrapTable('refresh', {\r\n");
      out.write("            pageNumber: 1,\r\n");
      out.write("            pageSize: 10,\r\n");
      out.write("            url: \"/api/search?serviceId=\" + serviceId,\r\n");
      out.write("        });\r\n");
      out.write("    };\r\n");
      out.write("\r\n");
      out.write("    function initApiTable() {\r\n");
      out.write("        var serviceId = $(\"#service option:selected\").attr(\"key\");\r\n");
      out.write("        $('#apiTable').bootstrapTable({\r\n");
      out.write("            method: 'get',\r\n");
      out.write("            contentType: \"application/json; charset=utf-8\",\r\n");
      out.write("            dataType: \"json\",\r\n");
      out.write("            url: \"/api/search?serviceId=\" + serviceId,\r\n");
      out.write("            height: tableHeight(),\r\n");
      out.write("            ajaxOptions: {\r\n");
      out.write("                headers: {\r\n");
      out.write("                    \"Authentication\": $.cookie(\"Authentication\")\r\n");
      out.write("                }\r\n");
      out.write("            },\r\n");
      out.write("            toolbar: '#toolbar',\r\n");
      out.write("            striped: true,\r\n");
      out.write("            idField: \"id\",\r\n");
      out.write("            dataField: \"list\",\r\n");
      out.write("            pageNumber: 1,\r\n");
      out.write("            pagination: true,\r\n");
      out.write("            queryParamsType: '',\r\n");
      out.write("            sidePagination: 'server',\r\n");
      out.write("            pageSize: 10,\r\n");
      out.write("            pageList: [5, 10, 20, 30],\r\n");
      out.write("            paginationPreText: \"上一页\",\r\n");
      out.write("            paginationNextText: \"下一页\",\r\n");
      out.write("            paginationFirstText: \"首页\",\r\n");
      out.write("            paginationLastText: \"尾页\",\r\n");
      out.write("            showRefresh: true,\r\n");
      out.write("            showColumns: true,\r\n");
      out.write("            search: true,\r\n");
      out.write("            detailView: false,\r\n");
      out.write("            clickToSelect: true,\r\n");
      out.write("            toolbarAlign: 'left',\r\n");
      out.write("            buttonsAlign: 'right',\r\n");
      out.write("            columns: [\r\n");
      out.write("                {\r\n");
      out.write("                    title: 'ID',\r\n");
      out.write("                    field: 'id',\r\n");
      out.write("                    visible: false\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    title: '方法',\r\n");
      out.write("                    field: 'method',\r\n");
      out.write("                    formatter:methodFormatter\r\n");
      out.write("                }, {\r\n");
      out.write("                    title: '路径',\r\n");
      out.write("                    field: 'path'\r\n");
      out.write("                },\r\n");
      out.write("\r\n");
      out.write("                {\r\n");
      out.write("                    title: '名称',\r\n");
      out.write("                    field: 'name'\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    title: '版本号',\r\n");
      out.write("                    field: 'version'\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    title: '创建人',\r\n");
      out.write("                    field: 'createUser'\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    title: '创建时间',\r\n");
      out.write("                    field: 'createTime',\r\n");
      out.write("                    formatter: dateFormatter\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    title: '更新人',\r\n");
      out.write("                    field: 'updateUser',\r\n");
      out.write("                    visible: false\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    title: '更新时间',\r\n");
      out.write("                    field: 'updateTime',\r\n");
      out.write("                    formatter: dateFormatter,\r\n");
      out.write("                    visible: false\r\n");
      out.write("                }, {\r\n");
      out.write("                    title: '操作',\r\n");
      out.write("                    field: 'button',\r\n");
      out.write("                    align: 'center',\r\n");
      out.write("                    formatter: apiOptFormatter,\r\n");
      out.write("                    width: 150\r\n");
      out.write("                }\r\n");
      out.write("            ],\r\n");
      out.write("            locale: 'zh-CN',\r\n");
      out.write("            responseHandler: function (res) {\r\n");
      out.write("                return res.content;\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function apiOptFormatter(value, row, index) {\r\n");
      out.write("        return \"<a class=\\\"btn btn-primary btn-sm\\\" id=\\\"\" + row.id + \"\\\" onclick=\\\"updateApi(this)\\\" >详情</a>\"\r\n");
      out.write("            + \"   <a class=\\\"btn btn-danger btn-sm\\\" id=\\\"\" + row.id + \"\\\" onclick=\\\"deleteApi(this)\\\" >删除</a>\"\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function updateApi(obj) {\r\n");
      out.write("        window.location.href = \"/static/jsp/api-detail.jsp?apiId=\" + obj.id;\r\n");
      out.write("    }\r\n");
      out.write("    function deleteApi(obj) {\r\n");
      out.write("        $.ajax({\r\n");
      out.write("            type: \"delete\",\r\n");
      out.write("            url: \"/api/delete?apiId\"+obj.id,\r\n");
      out.write("            data: data,\r\n");
      out.write("            dataType: 'json',\r\n");
      out.write("            contentType: \"application/json; charset=utf-8\",\r\n");
      out.write("            beforeSend: function (request) {\r\n");
      out.write("                request.setRequestHeader(\"Authentication\", $.cookie(\"Authentication\"));\r\n");
      out.write("            },\r\n");
      out.write("            success: function (msg) {\r\n");
      out.write("                if (msg.status) {\r\n");
      out.write("                    refreshApiTable();\r\n");
      out.write("                } else if (msg.code == \"D0000104\") {\r\n");
      out.write("                    window.location.href = \"/login.jsp\";\r\n");
      out.write("                } else {\r\n");
      out.write("                    swal({\r\n");
      out.write("                        title: \"提示！\",\r\n");
      out.write("                        text: \"删除Api失败！\\n\" + msg.errorMsg,\r\n");
      out.write("                        type: \"error\"\r\n");
      out.write("                    });\r\n");
      out.write("                }\r\n");
      out.write("\r\n");
      out.write("            }\r\n");
      out.write("\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
