<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
 <head>
        <title>Your Admin Panel</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/easyUI/themes/bootstrap/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/easyUI/themes/icon.css">
        
        <!-- jQuery AND jQueryUI -->
        <script type="text/javascript" src="<%=basePath%>/resources/js/libs/jquery/1.6/jquery.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>/resources/js/libs/jqueryui/1.8.13/jquery-ui.min.js"></script>
        
        <script type="text/javascript">
        	$(document).ready(function(){
        		  $('.subul').find('li').find('a').click(function(){
        				//$(this).parent().siblings().removeClass('current');
        				$('.subul').find('li').removeClass('current');
        				$(this).parent().addClass('current');
        		  });
        	});
        	
        </script>
        <link rel="stylesheet" href="<%=basePath%>/resources/css/min.css" />
        <script type="text/javascript" src="<%=basePath%>/resources/js/min.js"></script>
    </head>
    <body>
        
        <div id="sidebar">
            <ul id="sidebarul">
            	<li><a href="#"><img src="<%=basePath%>/resources/img/icons/menu/brush.png" alt="" />系统管理</a>
                    <ul class="subul">
                         <li class="current"><a target="rightFrame" href="<%=basePath%>system/user/system-manage-user.jsp">用户管理</a></li>
                    </ul>
                </li>
                <li><a href="#"><img src="<%=basePath%>/resources/img/icons/menu/calendar.png" alt="" />签到管理</a>
                    <ul class="subul">
                         <li class="current"><a target="rightFrame" href="./managecourse.jsp">课程管理</a></li>
                         <li ><a target="rightFrame" href="./managesign.jsp">签到管理</a></li>
                    </ul>
                </li>
            </ul>
        </div>
                
                        
    </body>
</html>