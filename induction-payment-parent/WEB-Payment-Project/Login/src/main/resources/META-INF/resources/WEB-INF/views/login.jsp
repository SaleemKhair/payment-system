<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-xs-4">
	<c:if test="${loginFailure ne null}">
		<div class="alert alert-danger" role="alert">
			${loginFailure.message}</div>
	</c:if>
	<form class="form-signin" method="post">
		<h2 class="form-signin-heading">Please sign in:</h2>
		<label for="inputEmail" class="sr-only">Username</label>
		 <input
			type="text" name="username" id="inputEmail" class="form-control"
			placeholder="Username" required autofocus/> 
		<label
			for="inputPassword" class="sr-only">Password</label> 
		<input
			type="password" name="password" id="inputPassword"
			class="form-control" placeholder="Password" required/>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
			in</button>
	</form>
</div>