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
//初始化数据函数
function getData(queryParams){
	$('#grid').datagrid({
		url: '<%=basePath%>goods/getpagegoodsimgurls',
		queryParams: queryParams,
		remoteSort:false,
		nowrap: false, //换行属性
		striped: true, //奇数偶数行颜色区分
		height:600,
		singleSelect:true,
		fitColumns:true,
		collapsible : true, //可折叠
		pageSize: 50,//每页显示的记录条数，默认为10  
        pageList: [5,10,15,20,25,50,100],//可以设置每页记录条数的列表  
        pagination: true,//是否这是分页
		rownumbers:true,
		frozenColumns:[[
			{field: 'ck', checkbox: true},          
		]],
		columns: [[
			{field:'id',title:'ID',sortable:true,width:60,sortable:true,hidden:true},
			{field:'imgurls',title:'图片',sortable:true,width:150,sortable:true,
				editor: { type: 'validatebox' },
				formatter:function(value,row,index){
					var str = "";
					if(!isNull(value)){
						str+="<img src='<%=basePath%>"+row+"' width='100px' height='100px' style='margin-left:60px;'></br>";
					}
					return str;
				},
			},
		]],
		toolbar:[
			{//删除数据
				   text:"返回列表",
				   iconCls: "icon-back",
				   handler: returnBack,
			},'-',
			{//删除数据
				   text:"删除",
				   iconCls: "icon-remove",
				   handler: removeData,
			},'-',
		],
		onAfterEdit: function(rowIndex,rowData,changes){
			doedit = undefined;
		},
		onDblClickRow:function(rowIndex, rowData){    
			//$('#grid').datagrid('endEdit',doedit);
			if(doedit==undefined)   //如果存在在编辑的行，就不可以再打开第二个行进行编辑
			{					
				$('#grid').datagrid('selectRow',rowIndex);
	        	$('#grid').datagrid('beginEdit',rowIndex);
	        	doedit=rowIndex;
			}
		},
		onLoadSuccess:function(data){//数据刷新的时候，编辑的坐标设为空
			doedit = undefined;
		},
		
	});
	//分页设置
	var p = $('#grid').datagrid('getPager');
	$(p).pagination({
        beforePageText: '第',//页数文本框前显示的汉字  
        afterPageText: '页    共 {pages} 页',  
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
        BeforeRefresh:function(){
			$(this).datagrid('reload'); 
			//获取数据库全部数据
		},
	});
};
//-------------------------------返回列表页面-----------------------------------
function returnBack(){
	location.href="<%=basePath%>system/goods/goods-manage-goods.jsp";
}
//-------------------------------删除-------------------------------------------
function removeData(){
	var rows = $('#grid').datagrid('getSelections');
	if(rows.length <= 0){
		$.messager.alert('警告','您没有选择','error');
	}
	else if(rows.length >= 1){
		$.messager.confirm("操作警告", "确定删除后将不可恢复！！", function(data){
			if(data){
				//原来代码开始的位置
				var imgurl = [];
				for(var i = 0; i < rows.length; ++i){
					imgurl[i] = rows[i];
				}	
				$.ajax({
		    		type:'post',
		    		url:"<%=basePath%>goods/deletegoodsimgurl",
		    		data:{imgurl: imgurl.toString(),ids:goodsid},
		    		success:function(data){
		    			if(1==data){//成功
		    				$.messager.alert('提示','删除成功','info');
		    			}else{
		    				$.messager.alert('提示','删除失败','error');
		    			}
		    			$('#grid').datagrid('reload');
		    		},error:function(){
		    			console.log("fail");
		    		}
		    	});	
				
			}
		});
	}
}
function isNull( str ){
	if ( str == "" ) return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}
//获取指定名称的cookie的值 
function getCookie(objName){
	var arrStr = document.cookie.split("; "); 
	for(var i = 0;i < arrStr.length;i ++){ 
	var temp = arrStr[i].split("="); 
	if(temp[0] == objName) return unescape(temp[1]); 
	} 
};
//获取链接数据
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}; 
//--------------------------------------主体部分！！！-----------------------------
var doedit = undefined;//用来记录当前编辑的行，如果没有编辑的行则置为undefined
$(function(){
	//获取数据的查询参数----过滤数据
	goodsid = getUrlParam('id');
	var queryParams;
	queryParams = {'goodsid':goodsid};
	getData(queryParams);
});
//------------------------------------------------------------------------------
var goodsid = -1;
$(document).ready(function(){
	goodsid = getUrlParam('id');
	if(goodsid == -1){
		location.href="<%=basePath%>system/goods/goods-insert-goods.jsp";
	}
	// 初始化插件
	$("#demo").zyUpload({
		width            :   "60%",                 // 宽度
		height           :   "100px",                 // 高度
		itemWidth        :   "120px",                 // 文件项的宽度
		itemHeight       :   "100px",                 // 文件项的高度
		url              :   "<%=basePath%>/goods/uploadPictures?goodsid="+goodsid,  // 上传文件的路径
		multiple         :   true,                    // 是否可以多个文件上传
		dragDrop         :   true,                    // 是否可以拖动上传文件
		del              :   false,                    // 是否可以删除文件
		finishDel        :   false,  				  // 是否在上传文件完成后删除预览
		/* 外部获得的回调接口 */
		onSelect: function(files, allFiles){                    // 选择文件的回调方法
			console.info("当前选择了以下文件：");
			console.info(files);
			console.info("之前没上传的文件：");
			console.info(allFiles);
		},
		onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法
			console.info("当前删除了此文件：");
			console.info(file);
			console.info("当前剩余的文件：");
			console.info(surplusFiles);
		},
		onSuccess: function(file){                    // 文件上传成功的回调方法
			console.info("此文件上传成功：");
			console.info(file);
		},
		onFailure: function(file){                    // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file);
		},
		onComplete: function(responseInfo){           // 上传完成的回调方法
			console.info("文件上传完成");
			console.info(responseInfo);
			$.message.alert('消息','上传成功','info');
			location.href="<%=basePath%>system/goods/goods-manage-goods.jsp";
		}
	});
});
</script>

<style type="text/css">
</style>
</head>

<body bgcolor="#DDF3FF" class = "h2" >
	<table id="grid"></table>
	<!-- <input  type="file" name="file" id="file"> -->
	<div id="demo" class="demo"></div>   
		
</body>
</html>


