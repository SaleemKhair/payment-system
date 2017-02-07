<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.area {
	color: #fff;
	font-weight: 700;
	text-transform: uppercase;
	animation: blur 1.75s ease-out infinite;
	text-shadow: 0px 0px 5px #31708f, 0px 0px 7px #31708f;
}

@
keyframes blur {from { text-shadow:0px0px10px#fff, 0px0px10px#31708f,
	0px0px25px#fff, 0px0px25px#fff, 0px0px25px#fff, 0px0px25px#fff,
	0px0px25px#fff, 0px0px25px#fff, 0px0px50px#fff, 0px0px50px#fff,
	0px0px50px#7B96B8, 0px0px150px#7B96B8, 0px10px100px#7B96B8,
	0px10px100px#7B96B8, 0px10px100px#7B96B8, 0px10px100px#7B96B8,
	0px-10px100px#7B96B8, 0px-10px100px#7B96B8;
	
}

}

</style>





<form class="well form-horizontal" action="./updateAccount"
	method="post" enctype="application/x-www-form-urlencoded">
	<fieldset>
		<!-- Form Name -->
		<legend>Account Profile</legend>
		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="iban"> IBAN </label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-credit-card"></i>
					</span> <select name="accountIban" onchange="location = this.value;"
						class="form-control selectpicker">
						<option hidden="hidden">Please Select IBAN</option>
						<c:forEach items="${accounts}" var="account">
							<option ${account.iban eq iban ?'selected' :'' }
								value="?iban=${account.iban}">${account.iban}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<!-- Success message -->
		<div class="alert alert-info text-center" role="alert"
			id="success_message">
			<strong class="area"> Note <i
				class="glyphicon glyphicon-edit"></i>
			</strong> Please Select you`r iban to edit account information
		</div>
	</fieldset>

	<fieldset>
		<!-- Form Name -->
		<legend>Edit Account Form</legend>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="iban">IBAN</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-credit-card"></i>
					</span> <input class="form-control" id="iban" name="iban" type="text"
						value="${iban}" readonly>
				</div>
			</div>
		</div>
		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="accountType">
				Account Type </label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-folder-open"></i>
					</span> <input class="form-control" id="accountType" name="type"
						type="text" value="${selectedAccount.getType()}" required />
				</div>
			</div>
		</div>
		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="balance">Balance</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-piggy-bank"></i>
					</span> <input class="form-control" id="balance" name="balance"
						type="text" value="${selectedAccount.getBalance()}" readonly />
				</div>
			</div>
		</div>
		<!-- Text input -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="status">Status</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <input class="form-control" type="text" name="status"
						value="${selectedAccount.getStatus()}" readonly />
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
						class="glyphicon glyphicon-usd"></i>
					</span> <select id="currencyCode" name="currencyCode" class="form-control">
						<c:forEach items="${currencies}" var="currency">
							<option
								${currency.code eq selectedAccount.getCurrencyCode() ?'selected' :''}
								value="${currency.code}">${currency.code}</option>
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
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <select id="rules" name="rule" class="form-control ">
						<c:forEach items="${rules}" var="r">
							<option ${r eq selectedAccount.getRule() ?'selected' :''}
								value="${r}">${r}</option>
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
				<button id="editAccount" name="editAccount" value="Edit Account"
					type="submit" class="btn btn-primary"
					onclick="return confirm('Are you sure you want to Edit account')">Save
					Changes</button>
			</div>
		</div>

	</fieldset>
</form>