<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.createAccount {
	display: none;
}
</style>
<script>
	function myFunction() {
		$('.createAccount').toggle();
	}
</script>

<form class="well form-horizontal" action="./createAccount"
	method="post" enctype="application/x-www-form-urlencoded">
	<fieldset>
		<!-- Form Name -->
		<legend>Create Account</legend>

		<!-- Text input-->

		<div class="form-group">
			<label class="col-md-4 control-label" for="iban">IBAN</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-credit-card"></i></span> <input
						placeholder="IBAN" class="form-control" type="text" id="iban"
						name="iban" value="" required>
				</div>
			</div>
		</div>

		<!-- Text input-->

		<div class="form-group">
			<label class="col-md-4 control-label" for="accountType">
				Account Type</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-folder-open"></i></span> <input
						placeholder="Account Type" class="form-control" type="text"
						value="" name="type" id="accountType" required>
				</div>
			</div>
		</div>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="balance">Balance</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-piggy-bank"></i></span> <input
						placeholder="Balance" class="form-control" type="text"
						id="balance" name="balance" value="" required>
				</div>
			</div>
		</div>

		<!-- Select Basic -->

		<div class="form-group">
			<label class="col-md-4 control-label" for="status">Status</label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-list"></i></span> <select id="status"
						name="status" class="form-control selectpicker">
						<option value=" ">Please select your Status</option>
						<option value="ACTIVE">ACTIVE</option>
						<option value="INACTIVE">INACTIVE</option>
					</select>
				</div>
			</div>
		</div>


		<!-- Select Basic -->

		<div class="form-group">
			<label class="col-md-4 control-label" for="currencyCode">Currency
				Code</label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-list"></i></span> <select id="currencyCode"
						name="currencyCode" class="form-control selectpicker">
						<option value=" ">Please select your Currency Code</option>
						<c:forEach items="${currencies}" var="currency">
							<option value="${currency.code}">${currency.code}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<!-- Select Basic -->

		<div class="form-group">
			<label class="col-md-4 control-label" for="rules">Rule</label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-list"></i></span> <select id="rules"
						name="rule" class="form-control selectpicker">
						<option value=" ">Please select your Rule</option>
						<c:forEach items="${rules}" var="r">
							<option value="${r}">${r}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<!-- Button -->
		<div class="form-group">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<input type="hidden" name="go_back_and_edit" id="go_back_and_edit"
					value="0" />
				<button type="submit" id="createAccount" class="btn btn-warning"
					onclick="myFunction()">
					Submit <span class="glyphicon glyphicon-send"></span>
				</button>
			</div>
		</div>


		<!-- Success message -->
		<div class="alert alert-success createAccount" role="alert"
			id="success_message">
			Success <i class="glyphicon glyphicon-thumbs-up"></i> Thanks for
			creating account .
		</div>

	</fieldset>
</form>

