<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>

</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<br/><br/><br/>
<table align="center" border="1px" cellspacing="0px">
	<tr>
		<td colspan="3" align="center">用户成绩管理</td>
	</tr>
	<tr align="center">
		<td width="80px">ID</td>
		<td width="80px">姓名</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${list }" var="map">
	<tr align="center"> 
		<td>${map.value.userId}</td>
		<td>${map.value.userName}</td>
		<td>
		<shiro:hasPermission name="plug:find_code:createUser">
			<a href="createUser.do">添加</a>&nbsp;&nbsp;&nbsp;
		</shiro:hasPermission>
		<shiro:hasPermission name="plug:find_code:delete">
			<a href="delete.do?userId=${map.value.userId}">删除</a>&nbsp;&nbsp;&nbsp;  
		</shiro:hasPermission>
		<shiro:hasPermission name="plug:find_code:updateUser">
			<a href="updateUser.do?userId=${map.value.userId}">修改</a>
		</shiro:hasPermission>
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>