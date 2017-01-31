<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
<c:forEach items="${requestScope}" var="item">
${item.key}:${item.value}<br />
</c:forEach>
 --%>

<div class="col-xs-8 col-xs-offset-2">
	<div class="row">
		<div class="col-xs-10 col-xs-offset-1"></div>
	</div>
	<div class="row">
		<h1>Not found: ${requestScope['javax.servlet.error.status_code']}</h1>
	</div>
</div>