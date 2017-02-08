<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<style>
/*---------------------------------*/
* {
	box-sizing: border-box;
	font-family: sans-serif;
	margin: 0;
	padding: 0;
	transition: all 1s;
}

body {
	background: #f5f5f5;
	color: #333;
}

#welcome {
	color: #333;
	text-shadow: 0 1px 3px white, -10px 0 8px #5bc0de;
	display: flex;
	height: 100%;
	min-height: 100vh;
	min-width: 100vw;
	overflow: hidden;
	position: fixed;
	width: 100%;
}

#welcome div:hover {
	text-shadow: 0 0 1px white, 0 0 0 #5bc0de;
}

#welcome div {
	align-items: center;
	background: #f5f5f5;
	box-shadow: 0 0 100px #5bc0de;
	cursor: pointer;
	display: flex;
	font-size: 5em;
	font-weight: bold;
	width: 50%;
}

#left {
	justify-content: flex-end;
}

#right {
	justify-content: flex-start !important;
}

.Of {
	justify-content: flex-end;
	width: 0 !important;
	visibility: hidden;
}

#Of {
	width: 0;
}

.On {
	width: 100% !important;
}

/*--------------------------------*/
#lorem {
	align-items: center;
	display: flex;
	flex-flow: row wrap;
	height: 100vh;
	justify-content: center;
}

#lorem p {
	background: #fff;
	box-shadow: 0 15px 18px -10px #5bc0de;
	font-size: 1.1em;
	margin: 1em;
	padding: 1.5em;
	width: 25em;
	height: 15em;
}
</style>


<section id='welcome'>
	<div id='left'>!PS VEC</div>
	<span id='Of'></span>
	<div id='right'>TORS!</div>
</section>
<div id='lorem'>

	<p>
		<strong>Signature Verification & Recognition</strong> Recognizing the
		long-standing dominance of signatures across your financial
		institution for authenticating checks or transaction documents,
		ProgressSoft utilizes superior technology to provide you with
		high-performing systems and an intelligent approach to the conduct of
		accurate signature verification.
	</p>

	<p>
		<strong>Electronic Bill Presentment and Payment</strong>
		ProgressSoft's Electronic Bill Presentment and Payment (PS-EBPP) is an
		electronic channel for distributing bills and collecting payments
		online. It marks an evolutionary logical extension of communication
		that enhances and reinforces the biller’s relationship with the
		customer.
	</p>


	<p>
		<strong>Electronic Check Clearing</strong> Technology-driven upgrades
		of check capture, presentment and clearing routines are becoming of a
		vital importance to your banking practices to ensure a competitive
		edge, superior service and compliance with recent financial visions
		and legislation.
	</p>

	<p>
		<strong>Real-Time Gross Settlement </strong> PS-RTGS is the Real-Time
		Gross Settlement Solution that empowers central banks with the means
		to control large value payments in a guaranteed, irrevokable
		environment.
	</p>
</div>

<script>
	var divs = document.getElementById('welcome');
	divs = divs.getElementsByTagName('div');

	for (var i = 0; i < divs.length; i++) {
		divs[i].onclick = splitScreen;
	}

	function splitScreen() {
		document.getElementById('welcome').style.visibility = 'hidden';
		var split = document.getElementById('Of');
		if (true) {
			split.className = 'On';
			divs[0].className = 'Of';
			divs[1].className = 'Of';
		}
	}
</script>
