<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="./submitpaymentrequest" method="post"
	enctype="application/x-www-form-urlencoded">

	<div class="optionscontainer headline">
		<div id="header-text">
			<div class="header-text-container">
				<h1>Create Payment Request Form</h1>
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<div class="form-group" style="padding-top: 1cm;">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="orderingAccountIBAN">User Accounts</label>
		</div>
		<div>
			<select id="orderingAccountIban" name="orderingAccountIban">
				<c:forEach items="${accounts}" var="account">
					<option value="${account.iban}">${account.iban}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="beneficiaryAccountIBAN">beneficiary Account</label>
		</div>
		<div>
			<input id="beneficiaryAccountIban" name="beneficiaryAccountIban"
				type="text" value="" required />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="beneficiaryName">beneficiary Name</label>
		</div>
		<div>
			<input id="beneficiaryName" name="beneficiaryName" type="text"
				value="" required />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="paymentAmount">Payment Amount</label>
		</div>
		<div>
			<input id="paymentAmount" name="paymentAmount" type="text" value=""
				required />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="checkLang">Check Writing Language</label>
		</div>
		<div>
			<select id="checkLang" name="checkLang">
				<c:forEach items="${checkLang}" var="lang">
					<option value="${lang}">${lang}</option>
				</c:forEach>
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
					<option value="${currency.code}">${currency.code}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="purposeCode">Purpose Code</label>
		</div>
		<div>
			<select id="purposeCode" name="purposeCode">
				<c:forEach items="${paymentPurposes}" var="purpose">
					<option value="${purpose.code}">${purpose.code}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="paymentDate">Payment Date</label>
		</div>
		<div>
			<input id="paymentDate" name="paymentDate" type="text" value=""
				placeholder="yyyy-mm-dd" required />
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