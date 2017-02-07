<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="<c:url value="/static/bootstrap/css/style.css"/>"
	rel="stylesheet">


<!-- particles.js container -->
<div id="particles-js">
	<div style="z-index: 999999999999; position: relative; top: 67px;">
		<c:if test="${loginFailure ne null}">
			<div class="alert alert-danger" role="alert">
				${loginFailure.message}</div>
		</c:if>

		<form class="well form-horizontal" id="formLogin" method="post">
			<fieldset>

				<!-- Form Name -->
				<legend>Payment System</legend>
				<p class="text-muted">Account Login</p>
				<!-- Text input-->

				<div class="form-group">
					<label class="col-md-4 control-label">First Name</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input name="username"
								id="inputEmail" maxlength="15" pattern="^[0-9a-zA-Z_\-]+$"
								placeholder="User Name" class="form-control" type="text"
								autofocus required>
						</div>
					</div>
				</div>

				<!-- Text input-->

				<div class="form-group">
					<label class="col-md-4 control-label" for="inputPassword">Password</label>
					<div class="col-md-4 inputGroupContainer">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock
						"></i></span> <input
								name="password" id="inputPassword" placeholder="Password"
								required="required" maxlength="255" type="password"
								class="form-control">
						</div>
					</div>
				</div>



				<!-- Button -->
				<div class="form-group">
					<label class="col-md-4 control-label"></label>
					<div class="col-md-4">
						<button id="loginButton" type="submit"
							class="btn btn-lg btn-primary btn-block">
							Login <span class="glyphicon glyphicon-log-in"></span>
						</button>
					</div>
				</div>

			</fieldset>
		</form>


	</div>

</div>
<!-- scripts -->
<script src='<c:url value="/static/bootstrap/js/particles.js"/>'></script>
<script src='<c:url value="/static/bootstrap/js/app.js"/>'></script>
<!-- stats.js -->
<script src='<c:url value="/static/bootstrap/js/stats.js"/>'></script>
<script src="js/lib/stats.js"></script>
<script>
	var count_particles, stats, update;
	stats = new Stats;
	stats.setMode(0);
	stats.domElement.style.position = 'absolute';
	stats.domElement.style.left = '0px';
	stats.domElement.style.top = '0px';
	document.body.appendChild(stats.domElement);
	count_particles = document.querySelector('.js-count-particles');
	update = function() {
		stats.begin();
		stats.end();
		if (window.pJSDom[0].pJS.particles
				&& window.pJSDom[0].pJS.particles.array) {
			count_particles.innerText = window.pJSDom[0].pJS.particles.array.length;
		}
		requestAnimationFrame(update);
	};
	requestAnimationFrame(update);
</script>