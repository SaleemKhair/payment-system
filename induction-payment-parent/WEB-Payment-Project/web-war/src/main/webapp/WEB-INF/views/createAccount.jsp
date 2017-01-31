<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="./createAccount" method="post"
	enctype="application/x-www-form-urlencoded">

	<div class="optionscontainer headline">
		<div id="header-text">
			<div class="header-text-container">
				<h1>Create Account Form</h1>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="form-group" style="padding-top: 1cm;">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="iban">IBAN</label>
		</div>
		<div>
			<input id="iban" name="iban" type="text" value="" required />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="accountType">Account Type</label>
		</div>
		<div>
			<input id="accountType" name="type" type="text" value="" required />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="balance">Balance</label>
		</div>
		<div>
			<input id="balance" name="balance" type="text" value="" required />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="status">Status</label>
		</div>
		<div>
			<select id="status" name="status">
				<option value="ACTIVE">ACTIVE</option>
				<option value="INACTIVE">INACTIVE</option>
			</select>
		</div>
	</div>
	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="currencyCode">Currency Code</label>
		</div>
		<div>
			<select id="currencyCode" name="currencyCode">
				<c:forEach items="${currencies}" var="currency">
					<option value="${currency.getCode()}">${currency.getCode()}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="rules">Rule</label>
		</div>
		<div>
			<select id="rules" name="rule">
				<c:forEach items="${rules}" var="r">
					<option value="${r}">${r}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div id="fieldcontainer8" class="fieldcontainer thebuttons" style="">
		<div class="row currentPage1 currentPageActive">
			<div class="subitem col-lg-9 pull-right text-left col-md-9 col-sm-9">
				<input type="hidden" name="go_back_and_edit" id="go_back_and_edit"
					value="0" />
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
		<div class="clear"></div>
	</div>

</form>