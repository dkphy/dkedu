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
		<td colspan="4" align="center">用户成绩管理</td>
	</tr>
	<tr align="center">
		<td width="80px">身份证号</td>
		<td width="80px">姓名</td>
		<td width="80px">分数</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${listCode}" var="code">
	<tr align="center"> 
		<td><input type="text" name="idCard" value="${code.idCard}"/></td>
		<td><input type="text" name="name" value="${code.name}"/></td>
		<td><input type="text" name="score" value="${code.score}"/></td>
		<td>
		<shiro:hasPermission name="plug:find_code:updateUser">
			<a href="updateUser.do?id=${code.id}">修改</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="plug:find_code:delete">
			<a href="delete.do?id=${code.id}">删除</a>&nbsp;&nbsp;&nbsp;  
		</shiro:hasPermission>
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>