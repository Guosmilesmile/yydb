<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="http://static.hdslb.com/images/favicon.ico">
<title>商品管理</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/prettify.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/AddCss.css" />
<script type="text/javascript" src="<%=basePath%>resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/prettify.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/multiselect/jquery.multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/multiselect/jquery.multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/multiselect/jquery.multiselect.filter.js"></script>


<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/easyUI/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/easyUI/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>resources/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/easyUI/locale/easyui-lang-zh_CN.js"></script>


<!-- 引用控制层插件样式 -->
<link rel="stylesheet" href="<%=basePath%>resources/zyUpload/control/css/zyUpload.css" type="text/css">
		
<!-- 引用核心层插件 -->
<script type="text/javascript" src="<%=basePath%>resources/zyUpload/core/zyFile.js"></script>
<!-- 引用控制层插件 -->
<script type="text/javascript" src="<%=basePath%>resources/zyUpload/control/js/zyUpload.js"></script>

<style type="text/css">
	body{background: white;}
</style>


<script>
//获取指定名称的cookie的值 
function getCookie(objName){
	var arrStr = document.cookie.split("; "); 
	for(var i = 0;i < arrStr.length;i ++){ 
	var temp = arrStr[i].split("="); 
	if(temp[0] == objName) return unescape(temp[1]); 
	} 
};
function validate(number){ 
	var reg = /^\d+$/;
    if (!number.match(reg)){
        return false;
    }else{    
    	return true;
    }    
} ;
function submitForm(){//提交表单
	
	var dbplanid = $("#dbplanlist").val();
	if(dbplanid == null || dbplanid ==""){
		$.messager.alert('警告','请选择夺宝计划','error');
		return ;
	}
	
	var customerid = $("#customerlist").val();
	if(customerid == null || customerid ==""){
		$.messager.alert('警告','请选择客户','error');
		return ;
	}
	
	
	$('#ff').form('submit',{
		success:function(data){  
			if("0"==data){
				$.message.alert('警告','添加失败','error');
			}else {
				location.href="<%=basePath%>system/db/db-manage-dbattend.jsp";
			}
		}
	});
}
//更改datebox的日期格式
function myformatter(value) {
	if(value != null && value != ""){
		var date = new Date(value);
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
}
//------------------------获取客户列表-------------------
function getCustomerlist(customerid){//获取所有的客户
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>customer/getallcustomer",
		success:function(data){
			//var list = eval("("+data+")");
			var list = data;
			if(list.length>0){
				var str1 = "";
				for(var i =0;i<list.length;i++){
					if(customerid==list[i].customerid){
						str1+="<option value='"+list[i].customerid+"' selected='selected'>"+list[i].name+"  微信id："+list[i].wechatid+"</option>";
					}else{
						str1+="<option value='"+list[i].customerid+"'>"+list[i].name+"  微信id："+list[i].wechatid+"</option>";
					}
				}
				$('#customerlist').html(str1);
			}
			$("#customerlist").multiselect({
				noneSelectedText: "==请选择==",
				multiple: false,
		        checkAllText: "全选",
		        uncheckAllText: '全不选',
		        selectedText:'#项被选中',
			}).multiselectfilter();
		},error:function(){
			console.log("fail");
		}
	});
}
//------------------------获取夺宝计划列表-------------------
function getDBPlanlist(dbplanid){//获取所有的夺宝计划
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>dbplan/getalldbplan",
		success:function(data){
			//var list = eval("("+data+")");
			var list = data;
			if(list.length>0){
				var str1 = "";
				for(var i =0;i<list.length;i++){
					if(dbplanid==list[i].id){
						str1+="<option value='"+list[i].id+"' selected='selected' >"+list[i].goodsName+"  商家名称："+list[i].shopName+" 单次竞价:"+list[i].split+" 夺宝编号:"+list[i].id+"</option>";
					}else{
						str1+="<option value='"+list[i].id+"'>"+list[i].goodsName+"  商家名称："+list[i].shopName+" 单次竞价:"+list[i].split+" 夺宝编号:"+list[i].id+"</option>";
					}
				}
				$('#dbplanlist').html(str1);
			}
			$("#dbplanlist").multiselect({
				noneSelectedText: "==请选择==",
				multiple: false,
		        checkAllText: "全选",
		        uncheckAllText: '全不选',
		        selectedText:'#项被选中',
			}).multiselectfilter();
		},error:function(){
			console.log("fail");
		}
	});
}
//------------------------获取夺宝参与情况详细信息-------------------
function getdbattenddetail(){//获取夺宝参与情况详细信息
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>dbattend/getDBAttendwithid",
		data:{'id':dbattendid},
		success:function(data){
			console.log(data);
			var customerid = data.customerid;
			var dbplanid = data.dbplanid;
			var isplay = data.isplay;
			$('#isplay').val(isplay);
			getDBPlanlist(dbplanid);
			getCustomerlist(customerid);
		},error:function(){
			console.log("fail");
		}
	});
}
//获取链接数据
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
};
var dbattendid;
$(document).ready(function(){
	dbattendid = getUrlParam('id');
	$('#dbattendid').val(dbattendid);
	getdbattenddetail();
	
});
</script>

<style type="text/css">
</style>
</head>

<body bgcolor="#DDF3FF" class = "h2" >
	<form action="<%=basePath%>dbattend/updatedbattend" id="ff" method="post" style="height: 98%;margin-left: 2%;margin-top: 2%;">
		<input type="hidden" name="userid" id="userid">
		<fieldset class="simpborder" style="width: 48%; float: left; margin-right: 3%;">
			<label class="titlelabel">夺宝计划</label>
			<input id="dbattendid" name="dbattendid" style="display: none">
			<select  multiple="multiple" name="dbplanid" size="5" id="dbplanlist" style="width:40%;display: none;"> </select>
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left; ">
			<label class="titlelabel">顾客</label> 
			<select  multiple="multiple" name="customerid" size="5" id="customerlist" style="width:40%;display: none;"> </select>
		</fieldset>
		<fieldset class="simpborder"  style="width: 48%; float: left;margin-right: 3%;">
			<label>是否付款</label>
			<select  name="isplay"  id="isplay" style="width:40%;"> 
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</fieldset>
		<br/>
		<fieldset style="width:99%;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="formsubmit">Submit</a>
		</fieldset>
	
	</form>
	
	
	
</body>
</html>


