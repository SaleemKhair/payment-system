
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.PayReq {
	color: #fff;
	background-color: #5bc0de;
	border-color: #46b8da;
}

.uploadFile {
	display: none;
}

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
.areaDanger {
	color: #fff;
	font-weight: 700;
	text-transform: uppercase;
	animation: blur 1.75s ease-out infinite;
	text-shadow: 0px 0px 5px red, 0px 0px 7px red;
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
<script>
	function myFunction() {
		$('.uploadFile').toggle();
	}
	var _validFileExtensions = [ ".csv" ];
	function ValidateSingleInput(oInput) {
		if (oInput.type == "file") {
			var sFileName = oInput.value;
			if (sFileName.length > 0) {
				var blnValid = false;
				for (var j = 0; j < _validFileExtensions.length; j++) {
					var sCurExtension = _validFileExtensions[j];
					if (sFileName.substr(
							sFileName.length - sCurExtension.length,
							sCurExtension.length).toLowerCase() == sCurExtension
							.toLowerCase()) {
						blnValid = true;
						break;
					}
				}
				if (!blnValid) {
					alert("Sorry, "
							+ " is invalid extensions , allowed extensions are:  "
							+ _validFileExtensions.join(", "));
					oInput.value = "";
					return false;
				}
			}
		}
		return true;
	}
</script>
<form class="well form-horizontal" action="./paymentRequest"
	method="post" enctype="application/x-www-form-urlencoded">
	<fieldset>
		<!-- Form Name -->
		<legend>Payment Requests</legend>

		<!-- Select Basic -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="iban">Ordering
				Account IBAN</label>
			<div class="col-md-4 selectContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-list"></i>
					</span> <select class="form-control selectpicker" name="accountIban"
						onchange="location = this.value;">
						<option hidden="hidden">Please Select IBAN</option>
						<c:forEach items="${accounts}" var="account">
							<option ${account.iban eq PaymentIban ?'selected' :'' }
								value="./paymentRequest?iban=${account.iban}">${account.iban}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<!-- Submit Button -->
		<div class="form-group">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<a
					href='<c:url value="./submitpaymentrequest"/>'
					type="submit" class="btn btn-success">Submit payment <span
					class="glyphicon glyphicon-send"></span></a>
			</div>
		</div>

	</fieldset>

</form>


<form method="post" action="./paymentRequest"
	enctype="multipart/form-data">
	<fieldset>
		<!-- Success message-->
		<div class="alert alert-info" role="alert" id="success_message">
			<strong class="area"> Note <i
				class="glyphicon glyphicon-edit"></i>
			</strong> Do You wan't upload Payment Request , as CSV file please <a
				id="UploadFile" href="#" onclick="myFunction()"> <i> Click
					Here </i></a> .
		</div>

		<!-- Upload File Browse-->

		<div class="form-group uploadFile">
			<label class="col-md-4 control-label" for="fileUpload">Upload
				CSV File</label>
			<div class="col-md-4 inputGroupContainer">
				<div class="input-group">
					<span class="input-group-addon"> <i
						class="glyphicon glyphicon-upload"></i>
					</span> <input onchange="ValidateSingleInput(this);" class="form-control"
						type="file" id="fileUpload" name="fileUpload" value="" required>
				</div>
			</div>
		</div>

		<!-- Button For submit file uploaded-->

		<div class="form-group uploadFile">
			<label class="col-md-4 control-label"></label>
			<div class="col-md-4">
				<button type="submit" class="btn btn-success">
					Upload File <span class="glyphicon glyphicon-upload"></span>
				</button>
			</div>
		</div>

	</fieldset>
</form>

<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
			<c:choose>
				<c:when test="${not empty paymentRequests}">
					<h2>Payment Request Informations</h2>
					<div class="table-responsive">
						<table class="table table-striped">

							<thead>

								<tr class="PayReq">
									<th>ID</th>
									<th>Beneficiary Name</th>
									<th>Beneficiary IBAN</th>
									<th>Date</th>
									<th>Amount</th>
									<th>Currency</th>
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
					</div>
					<div>
						<form action="./paymentRequest" method="post"
							enctype="multipart/form-data">
							<div class="btn-group" style="float: right; margin: 35px">
								<button type="button" class="btn btn-info dropdown-toggle "
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									Download Report <span class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<li>
										<button name="action" value="csv" style="border: none;"
											type="submit">CSV Report</button>
									</li>
									<li>
										<button name="action" value="xml" style="border: none;"
											type="submit">XML Report</button>
									</li>
								</ul>
							</div>
						</form>
					</div>
				</c:when>
				<c:when test="${empty paymentRequests && empty selectedIban}"></c:when>
				<c:otherwise>
					<div class="alert alert-danger" role="alert">
						<b class="areaDanger">Warning <i
							class="glyphicon glyphicon-warning-sign"></i></b> Account does not
						have payment requests.
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>