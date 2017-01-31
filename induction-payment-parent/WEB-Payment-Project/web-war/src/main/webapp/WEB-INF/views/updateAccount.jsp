<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="optionscontainer headline">
	<div id="header-text">
		<div class="header-text-container">
			<h1>Account Profile</h1>
		</div>
		<div class="clear"></div>
	</div>
</div>
<div class="form-group" style="padding-top: 1cm;">
	<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
		<label
			class="class123-label class123-fieldname class123-labelAligned   requiredfield "
			for="iban">Account IBAN</label>
	</div>
	<div tabindex="0" class="onclick-menu">
		<select class="onclick-menu-content" name="accountIban"
			onchange="location = this.value;">
			<option hidden="hidden">Please Select IBAN</option>
			<c:forEach items="${accounts}" var="account">
				<option ${account.iban eq iban ?'selected' :''}
					value="?iban=${account.iban}">${account.iban}</option>
			</c:forEach>
		</select>
	</div>
</div>
<!--  </form>-->
<form action="./updateAccount" method="post"
	enctype="application/x-www-form-urlencoded">

	<div class="optionscontainer headline">
		<div id="header-text">
			<div class="header-text-container">
				<h1>Edit Account Form</h1>
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
			<input id="iban" name="iban" type="text" value="${iban}" readonly />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="accountType">Account Type</label>
		</div>
		<div>
			<input id="accountType" name="type" type="text"
				value="${selectedAccount.getType()}" required />
		</div>
	</div>


	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="balance">Balance</label>
		</div>
		<div>
			<input id="balance" name="balance" type="text"
				value="${selectedAccount.getBalance()}" readonly />
		</div>
	</div>

	<div class="form-group">
		<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
			<label
				class="class123-label class123-fieldname class123-labelAligned   requiredfield "
				for="status">Status</label>
		</div>
		<div>
			<input type="text" name="status"
				value="${selectedAccount.getStatus()}" readonly>
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
					<option
						${currency.code eq selectedAccount.getCurrencyCode() ?'selected' :''}
						value="${currency.code}">${currency.code}</option>
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
					<option ${r eq selectedAccount.getRule() ?'selected' :''}
						value="${r}">${r}</option>
				</c:forEach>
			</select>
		</div>
	</div>


	<div id="fieldcontainer8" class="fieldcontainer thebuttons" style="">
		<div class="row currentPage1 currentPageActive">
			<div class="subitem col-lg-9 pull-right text-left col-md-9 col-sm-9">
				<input type="hidden" name="go_back_and_edit" id="go_back_and_edit"
					value="0" />
				<button name="editAccount" value="Edit Account" type="submit"
					class="btn btn-primary">Save Changes</button>
			</div>
		</div>
		<div class="clear"></div>
	</div>

</form>