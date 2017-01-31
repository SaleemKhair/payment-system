<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<form action="./generateReport" method="post"
		enctype="application/x-www-form-urlencoded">
		<div class="optionscontainer headline">
			<div id="header-text">
				<div class="header-text-container">
					<h1>Payment Requests</h1>
				</div>
				<div style="float: right; margin-top: 35px">
					<a href='<c:url value="./submitpaymentrequest"/>' type="submit"
						class="btn btn-success">Submit payment</a>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="form-group" style="padding-top: 1cm;">
			<div class="subitem col-lg-3 rowleft col-md-3 col-sm-3">
				<label
					class="class123-label class123-fieldname class123-labelAligned   requiredfield "
					for="iban">Ordering Account IBAN</label>
			</div>
			<div tabindex="0" class="onclick-menu">
				<select class="onclick-menu-content" name="accountIban"
					onchange="location = this.value;">
					<option hidden="hidden">Please Select IBAN</option>
					<c:forEach items="${accounts}" var="account">
						<option value="./paymentRequest?iban=${account.iban}">${account.iban}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</form>

	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<c:choose>
					<c:when test="${not empty paymentRequests}">
						<h2>Payment Request Informations</h2>
						<table class="table table-striped">
							<thead>
								<tr>
									<td>ID</td>
									<td>Beneficiary Name</td>
									<td>Beneficiary IBAN</td>
									<td>Date</td>
									<td>Amount</td>
									<td>Currency</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${paymentRequests}" var="request">
									<tr>
										<td>${request.id}</td>
										<td>${request.beneficiaryName}</td>
										<td>${request.beneficiaryAccountIban}</td>
										<td>${request.paymentDate}</td>
										<td>${request.paymentAmount}</td>
										<td>${request.currencyCode}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div>
							<form action="./paymentRequest" method="post">
								<div class=" btn-group" style="float: right; margin: 35px">
									<button type="button" class="btn btn-info dropdown-toggle "
										data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false">
										Download Report <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><button name="action" value="csv"
												style="border: none;" type="submit">CSV Report</button></li>
										<li><button name="action" value="xml"
												style="border: none;" type="submit">XML Report</button></li>
									</ul>
								</div>
							</form>
						</div>
					</c:when>
					<c:when test="${empty paymentRequests && empty selectedIban}"></c:when>
					<c:otherwise>
						<div class="alert alert-warning" role="alert">
							<strong>Alert:</strong> Account does not have payment requests.
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>




