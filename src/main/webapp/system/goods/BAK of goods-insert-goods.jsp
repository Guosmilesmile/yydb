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
<style type="text/css">
	body{background: url(css/img/bg.png);}
</style>


<script>
KindEditor.ready(function(K) {
	 window.editor =K.create('#editor_id', {
        uploadJson : '<%=basePath%>imageupload.do?method=keuploadimg',
        allowFileManager : false,
        items:[
               'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
               'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
               'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
               'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
               'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
               'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 
               'media', 'table', 'hr', 'baidumap', 'pagebreak',
               'anchor', 'link', 'unlink', '|', 'about'
       ],
	});
});
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
function submitForm(){
	editor.sync();
	var html = document.getElementById('editor_id').value;
	var token = getCookie("token");
	var index = token.indexOf("&");
	var userid = token.substring(0,index);
	if(userid == null || userid ==""){
		$.messager.alert('警告','请重新登录','error');
		return ;
	}
	$('#userid').val(userid);
	var title = $("#title").val();
	if(title == null || title ==""){
		$.messager.alert('警告','请填写标题','error');
		return ;
	}
	var author = $("#author").val();
	if(author == null || author ==""){
		$.messager.alert('警告','请填写作者','error');
		return ;
	}
	var file  = $('#file').val();
	if(file ==null || file ==""){
		$.messager.alert('警告','请选择资讯封面','error');
		return ;
	}
	var summary = $("#summary").val();
	if(summary == null || summary ==""){
		$.messager.alert('警告','请填写概要','error');
		return ;
	}
	
	var money = $('#money').val();
	if(money == null || money == ""){
		$.messager.alert('警告','费用请填写费用','error');
		return ;
	}
	if(!validate(money)){
		$.messager.alert('警告','费用请填写数字','error');
		return ;
	}
	var timestart = $('#timestart').datebox('getValue');
	var timeout = $('#timeout').datebox('getValue');
	if(timestart>timeout){
		$.messager.alert('警告','下线时间不能大于上线时间','error');
		return ;
	}
	if(timestart == null || timestart ==""){
		$.messager.alert('警告','请选择上线时间','error');
		return ;
	}
	
	if(timeout == null || timeout ==""){
		$.messager.alert('警告','请选择下线时间','error');
		return ;
	}
	$('#timestart').datebox('setValue',timestart);
	$('#timeout').datebox('setValue',timeout);
	$('#ff').form('submit',{
		success:function(data){   
			if(0==data){
				$.message.alert('警告','发布失败','error');
			}else if(1==data){
				location.href="WatchNews.jsp";
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
</script>

<style type="text/css">
</style>
</head>

<body bgcolor="#DDF3FF" class = "h2" >
	<form action="<%=basePath%>news.do?method=addnews" id="ff" method="post" style="height: 98%;margin-left: 2%;margin-top: 2%;" enctype="multipart/form-data">
		<input type="hidden" name="userid" id="userid">
		<fieldset class="simpborder" style="width: 48%; float: left; margin-right: 3%;">
			<label>是否为首页资讯</label> 
			<select name="ishot" id="ishot" onchange="addFile(this.options[this.options.selectedIndex].value)" style="width: 92%;" >
					<option value="1">是</option>
					<option selected="selected" value="0">否</option>
			</select>
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left; ">
			<label>资讯标题</label>
			<input type="text" name="title" id="title">
		</fieldset>
		<fieldset class="simpborder"  style="width: 48%; float: left;margin-right: 3%;">
			<label>作者</label>
			<input  type="text" name="author" id="author">
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left;">
			<label>封面图片</label>
			<input  type="file" name="file" id="file">
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left; margin-right: 3%;">
			<label>资讯概要</label>
			<input type="text" name="summary" id="summary">
		</fieldset>
		<fieldset class="simpborder" style="width: 48%; float: left;">
			<label>费用</label>
			<input type="text" name="money" id="money">
		</fieldset>
		<fieldset class="simpborder" style="width: 47%; float: left;margin-right: 3%;padding-left: 12px;">
			<label>上线时间</label>
			<input class="easyui-datebox" name="timestart" id="timestart" style="width:91%;margin-left: 2%;">
		</fieldset>
		<fieldset class="simpborder"  style="width: 47%; float: left;padding-left: 12px;">
			<label>下线时间</label>
			<input  class="easyui-datebox" name="timeout"id="timeout"  style="width:91%">
		</fieldset>
		
		<br/>
		<fieldset style="width:99%;">
		<textarea id="editor_id" name="content" style="width:99%;height:400px;"></textarea>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" id="formsubmit">Submit</a>
		</fieldset>
	
	</form>
	
	
	
</body>
</html>


