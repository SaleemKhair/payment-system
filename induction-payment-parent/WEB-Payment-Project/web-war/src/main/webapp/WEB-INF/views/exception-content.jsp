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
		<h3>${requestScope['javax.servlet.error.message']}</h3>

	</div>
</div>