<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
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
                    	 <shiro:hasPermission name="system:user">  
                         	<li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-user.jsp">用户管理</a></li>
                         </shiro:hasPermission> 
                         <shiro:hasPermission name="system:role"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-role.jsp">角色管理</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="system:userrole"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-user-role.jsp">用户角色管理</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="system:rolepermission">
                         <li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-permission-role.jsp">角色权限管理</a></li>
                    	 </shiro:hasPermission>
                    </ul>
                </li>
                <li><a href="#"><img src="<%=basePath%>/resources/img/icons/menu/calendar.png" alt="" />客户管理</a>
                    <ul class="subul">
                    	 <shiro:hasPermission name="customer:manage"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/customer/customer-manage-all.jsp">客户管理</a></li>
                    	 </shiro:hasPermission>
                    </ul>
                </li>
                <li><a href="#"><img src="<%=basePath%>/resources/img/icons/menu/inbox.png" alt="" />商品管理</a>
                    <ul class="subul">
                    	 <shiro:hasPermission name="goods:category"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/goods/goods-manage-category.jsp">分类管理</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="goods:manage"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/goods/goods-manage-goods.jsp">商品详情管理</a></li>
                    	 </shiro:hasPermission>
                    </ul>
                </li>
                <li><a href="#"><img src="<%=basePath%>/resources/img/icons/menu/pen.png" alt="" />夺宝管理</a>
                    <ul class="subul">
                    	 <shiro:hasPermission name="db:dbplan"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/db/db-manage-dbplan.jsp">夺宝计划管理</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="db:dbattend"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/db/db-manage-dbattend.jsp">夺宝参与情况管理</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="db:dbsituation"> 
                         <li ><a target="rightFrame" href="<%=basePath%>system/db/db-manage-dbsituation-total.jsp">夺宝情况管理</a></li>
                    	 </shiro:hasPermission>
                    </ul>
                </li>
            </ul>
        </div>
                
                        
    </body>
</html>