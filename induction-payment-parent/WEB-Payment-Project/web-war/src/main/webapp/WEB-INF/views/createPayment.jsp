<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<form class="well form-horizontal" action="./submitpaymentrequest"
	method="post" enctype="application/x-www-form-urlencoded">
	<fieldset>
		<!-- Form Name -->
		<legend>Create Payment Request</legend>
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="orderingAccountIBAN">
				User Accounts </label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <select class="form-control selectpicker" id="orderingAccountIban"
						name="orderingAccountIban">
						<c:forEach items="${accounts}" var="account">
							<option ${account.iban eq orderingAccountIban ?'selected' :'' }
								value="${account.iban}">${account.iban}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="beneficiaryAccountIBAN">beneficiary
				Account </label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-user"></i>
					</span> <input class="form-control" type="text"
						placeholder="Beneficiary Account Iban" value=""
						id="beneficiaryAccountIban" name="beneficiaryAccountIban" required>
				</div>
			</div>
		</div>


		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="beneficiaryName">beneficiary
				Name</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-envelope"></i>
					</span> <input placeholder="Beneficiary Name" class="form-control"
						type="text" id="beneficiaryName" name="beneficiaryName" value=""
						required>
				</div>
			</div>
		</div>
		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="paymentAmount">
				Payment Amount </label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-envelope"></i>
					</span> <input placeholder="Payment Amount" class="form-control"
						type="text" id="paymentAmount" name="paymentAmount" value=""
						required>
				</div>
			</div>
		</div>
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="checkLang"> Check
				Writing Language </label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <select class="form-control selectpicker" id="checkLang"
						name="checkLang">
						<c:forEach items="${checkLang}" var="lang">
							<option value="${lang}">${lang}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="currencyCode">
				Currency Code </label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <select class="form-control selectpicker" id="currencyCode"
						name="currencyCode">
						<c:forEach items="${currencies}" var="currency">
							<option value="${currency.code}">${currency.code}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-4 control-label" for="purposeCode">
				Purpose Code </label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <select class="form-control selectpicker" id="purposeCode"
						name="purposeCode">
						<c:forEach items="${paymentPurposes}" var="purpose">
							<option value="${purpose.code}">${purpose.code}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="paymentDate">
				Payment Date </label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <input class="form-control" type="date" id="paymentDate"
						name="paymentDate" value="" required>
				</div>
			</div>
		</div>
		<!-- Button -->
		<div class="form-group">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<input type="hidden" name="go_back_and_edit" id="go_back_and_edit"
					value="0" />
				<button type="submit" class="btn btn-warning">
					Submit <span class="glyphicon glyphicon-send"></span>
				</button>
			</div>
		</div>

		<!-- Success message -->
		<div class="alert alert-success" role="alert" id="success_message">
			Success <i class="glyphicon glyphicon-thumbs-up"></i> Thanks for
			contacting us, we will get back to you shortly.
		</div>
	</fieldset>
</form>
