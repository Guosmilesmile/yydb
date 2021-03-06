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
<title>更新商品信息</title>

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
	
	var name = $("#name").val();
	if(name == null || name ==""){
		$.messager.alert('警告','请填写商品名称','error');
		return ;
	}
	var categorylist = $("#categorylist").val();
	if(categorylist == null || categorylist ==""){
		$.messager.alert('警告','请选择商品分类','error');
		return ;
	}
	var shoplist = $("#shoplist").val();
	if(shoplist == null || shoplist ==""){
		$.messager.alert('警告','请选择商家','error');
		return ;
	}
	var summary = $("#summary").val();
	if(summary == null || summary ==""){
		$.messager.alert('警告','请填写商品概要','error');
		return ;
	}
	$('#ff').form('submit',{
		success:function(data){   
			if("-1"==data){
				$.message.alert('警告','添加失败','error');
			}else {
				$.messager.confirm("操作", "是否继续上传商品图片", function(confirm){
					if(confirm){
						location.href="<%=basePath%>system/goods/goods-upload-picture.jsp?id="+goodsid;
					}else{
						location.href="<%=basePath%>system/goods/goods-manage-goods.jsp";
					}
				});
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
//------------------------获取分类列表-------------------
function getCategorylist(categoryid){//获取所有的role
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>category/systemgetallcategory",
		success:function(data){
			//var list = eval("("+data+")");
			var list = data;
			if(list.length>0){
				var str1 = "";
				for(var i =0;i<list.length;i++){
					if(categoryid==list[i].id){
						str1+="<option value='"+list[i].id+"' selected='selected' >"+list[i].name+"</option>";
					}else{
						str1+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
					}
					
				}
				$('#categorylist').html(str1);
			}
			$("#categorylist").multiselect({
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
//------------------------获取商家列表-------------------
function getShoplist(shopid){//获取所有的shop
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>customer/getallshop",
		success:function(data){
			//var list = eval("("+data+")");
			var list = data;
			if(list.length>0){
				var str1 = "";
				for(var i =0;i<list.length;i++){
					if(shopid==list[i].customerid){
						str1+="<option value='"+list[i].customerid+"'  selected='selected' >"+list[i].name+"  微信id："+list[i].wechatid+"</option>";
					}else{
						str1+="<option value='"+list[i].customerid+"'>"+list[i].name+"  微信id："+list[i].wechatid+"</option>";
					}
				}
				$('#shoplist').html(str1);
			}
			$("#shoplist").multiselect({
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
//------------------------获取商品详细信息-------------------
function getgoodsdetail(goodsid){//获取商品详细信息
	$.ajax({
		type:'post',
		async:false,
		url:"<%=basePath%>goods/getgoodswithid",
		data:{'goodsid':goodsid},
		success:function(data){
			console.log(data);
			var name = data.name;
			var shopid = data.shopid;
			var categoryid = data.categoryid;
			var summary = data.summary;
			$('#name').val(name);
			$('#summary').val(summary);
			getCategorylist(categoryid);
			getShoplist(shopid);
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
var goodsid;
$(document).ready(function(){
	goodsid = getUrlParam('id');
	$('#goodsid').val(goodsid);
	getgoodsdetail(goodsid);
	
});
</script>

<style type="text/css">
</style>
</head>

<body bgcolor="#DDF3FF" class = "h2" >
	<form action="<%=basePath%>goods/updateGoods" id="ff" method="post" style="height: 98%;margin-left: 2%;margin-top: 2%;">
		<input type="hidden" name="userid" id="userid">
		<fieldset class="simpborder" style="width: 48%; float: left; margin-right: 3%;">
			<label>商品名称</label>
			<input type="text" name="goodsid" id="goodsid" style="display: none;">
			<input type="text" name="name" id="name">
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left; ">
			<label class="titlelabel">分类</label> 
			<!-- <select name="ishot" id="ishot" onchange="addFile(this.options[this.options.selectedIndex].value)" style="width: 92%;" >
					<option value="1">是</option>
					<option selected="selected" value="0">否</option>
			</select> -->
			<select  multiple="multiple" name="categoryid" size="5" id="categorylist" style="width:40%;display: none;"> </select>
		</fieldset>
		<fieldset class="simpborder"  style="width: 48%; float: left;margin-right: 3%;">
			<label class="titlelabel">商家</label>
			<!-- <select name="ishot" id="ishot" onchange="addFile(this.options[this.options.selectedIndex].value)" style="width: 92%;" >
					<option value="1">是</option>
					<option selected="selected" value="0">否</option>
			</select> -->
			<select  multiple="multiple" name="shopid" size="5" id="shoplist" style="width:40%;display:none;"></select>
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left;">
			<label>概要</label>
			<input type="text" name="summary" id="summary">
		</fieldset>
		<!-- <fieldset class="simpborder" style="width: 100%; height:900px; float: left; ">
			<label class="titlelabel">图片</label>
			<input  type="file" name="file" id="file">
			<div id="demo" class="demo"></div>   
		</fieldset> -->
		<br/>
		<fieldset style="width:99%;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="formsubmit">Submit</a>
		</fieldset>
	
	</form>
	
	
	
</body>
</html>


