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
<title>夺宝计划更新</title>

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
	
	var goodsid = $("#goodslist").val();
	if(goodsid == null || goodsid ==""){
		$.messager.alert('警告','请选择商品','error');
		return ;
	}
	
	var money = $("#money").val();
	if(money == null || money ==""){
		$.messager.alert('警告','请填写价格','error');
		return ;
	}
	
	var number = $("#number").val();
	if(number == null || number ==""){
		$.messager.alert('警告','请填写数量','error');
		return ;
	}
	
	var split = $("#split").val();
	if(split == null || split ==""){
		$.messager.alert('警告','请填写单次竞标价','error');
		return ;
	}
	
	var timestart = $('#starttime').datebox('getValue');
	var timeout = $('#endtime').datebox('getValue');
	if(timestart == null || timestart ==""){
		$.messager.alert('警告','请选择上线时间','error');
		return ;
	}
	
	if(timeout == null || timeout ==""){
		$.messager.alert('警告','请选择下线时间','error');
		return ;
	}
	if(timestart>timeout){
		$.messager.alert('警告','下线时间不能大于上线时间','error');
		return ;
	}
	
	
	$('#ff').form('submit',{
		success:function(data){  
			if("0"==data){
				$.message.alert('警告','添加失败','error');
			}else {
				location.href="<%=basePath%>system/db/db-manage-dbplan.jsp";
			}
		}
	});
}
function myformatter(value) {//时间转换函数
	if(value != null && value != ""){
		var date = new Date(value*1000);
		var hour = date.getHours();
		var minute = date.getMinutes();
		var second = date.getSeconds();
		if(hour<10){
			hour = "0"+hour;
		}
		if(minute<10){
			minute = "0"+minute;
		}
		if(second<10){
			second = "0"+second;
		}
		return date.getFullYear()+"-"+(date.getMonth() + 1) +"-"+ date.getDate()+" "+ hour + ":" + minute +":"+ second;
	}
}

//------------------------获取商品列表-------------------
function getGoodlist(goodsid){//获取所有的商品
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>goods/getallGoods",
		success:function(data){
			//var list = eval("("+data+")");
			var list = data;
			if(list.length>0){
				var str1 = "";
				for(var i =0;i<list.length;i++){
					if(goodsid==list[i].id){
						str1+="<option value='"+list[i].id+"' selected='selected' >"+list[i].name+"  商家名称："+list[i].shopName+"</option>";
					}else{
						str1+="<option value='"+list[i].id+"'>"+list[i].name+"  商家名称："+list[i].shopName+"</option>";	
					}
					
				}
				$('#goodslist').html(str1);
			}
			$("#goodslist").multiselect({
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
//------------------------获取夺宝计划详细信息-------------------
function getdbplandetail(){//获取夺宝计划详细信息
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>dbplan/getdbplanwithid",
		data:{'id':dbplanid},
		success:function(data){
			console.log(data);
			var goodsid = data.goodsid;
			var number = data.number;
			$('#number').val(number);
			var money = data.money;
			$('#money').val(money);
			var split = data.split;
			$('#split').val(split);
			var timestart = data.startTime;
			var timeout = data.endTime;
			timestart = myformatter(timestart);
			timeout = myformatter(timeout);
			$('#starttime').datetimebox('setValue',timestart);
			$('#endtime').datetimebox('setValue',timeout);
			getGoodlist(goodsid);
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
var dbplanid;
$(document).ready(function(){
	dbplanid = getUrlParam('id');
	$('#dbplanid').val(dbplanid);
	getdbplandetail(dbplanid);
	//getGoodlist();
});
</script>

<style type="text/css">
</style>
</head>

<body bgcolor="#DDF3FF" class = "h2" >
	<form action="<%=basePath%>dbplan/updatedbplan" id="ff" method="post" style="height: 98%;margin-left: 2%;margin-top: 2%;">
		<input type="hidden" name="userid" id="userid">
		<fieldset class="simpborder" style="width: 48%; float: left; margin-right: 3%;">
			<label class="titlelabel">商品</label> 
			<select  multiple="multiple" name="goodsid" size="5" id="goodslist" style="width:40%;display: none;"> </select>
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left; ">
			<label>价格</label>
			<input type="text" name="money" id="money">
			<input type="text" name="dbplanid" id="dbplanid" style="display: none;">
		</fieldset>
		<fieldset class="simpborder"  style="width: 48%; float: left;margin-right: 3%;">
			<label>数量</label>
			<input type="text" name="number" id="number">
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left;">
			<label>单次竞标价</label>
			<input type="text" name="split" id="split">
		</fieldset>
		<fieldset class="simpborder" style="width: 47%; float: left;margin-right: 3%;padding-left: 12px;">
			<label>上线时间</label>
			<input class="easyui-datetimebox" name="starttime" id="starttime" style="width:91%;margin-left: 2%;">
		</fieldset>
		<fieldset class="simpborder"  style="width: 47%; float: left;padding-left: 12px;">
			<label>下线时间</label>
			<input  class="easyui-datetimebox" name="endtime"id="endtime"  style="width:91%">
		</fieldset>
		<br/>
		<fieldset style="width:99%;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="formsubmit">Submit</a>
		</fieldset>
	
	</form>
	
	
	
</body>
</html>


