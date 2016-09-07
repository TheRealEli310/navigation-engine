<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/d3.v3.min.js"></script>
<script src="js/d3.layout.cloud.js"></script>
<style>
.page-head {
	background-repeat: repeat-x;
	background-size: auto;
	background-position: 0 0;
	background-color: #3a5795;
	border-bottom: 1px solid #103480;
	box-shadow: 0 2px 2px -2px rgba(0, 0, 0, .82);
}

.header-title {
	text-align: center;
}

.back-btn {
	display: block;
	background: url('img/arrow-back.png') center no-repeat;
	background-size: 45px 45px;
	height: 50px;
	width: 100px;
	cursor: pointer;
}

.hTitle {
	float: none;
	line-height: 50px;
}

.prof-info {
	width: 100%;
	height: 350px;
	background: url('img/stockimage.jpg') center;
}

.user-name {
	color: white;
}

.user-image {
	margin-top: 25px;
}
</style>
<title>User Profile</title>
</head>
<body onload=changeCall()>
	<!-- banner -->
	<nav class="navbar navbar-default navbar-fixed-top page-head">
		<div class="row">
			<div class="col-sm-2">
				<a href="#" class="back-btn pull-left"> </a>
			</div>
		</div>
	</nav>
	<section class="jumbotron prof-info">
		<div class="container">
			<div class="row">
				<div class="col-sm-8">
					<h1 class="user-name"><%=request.getAttribute("userName")%></h1>
					<p class="user-email">
						Reputation :
						<%=request.getAttribute("userReputation")%></p>
				</div>
				<div class="col-sm-2 pull-right">
					<div class="thumbnail user-image">
						<img src="img/user-dummy.jpg" alt="...">
						<div class="caption">
							<p>User ID</p>
						</div>
					</div>
					<div class="progress">
						<div class="progress-bar progress-bar-striped active"
							role="progressbar" aria-valuenow="50" aria-valuemin="0"
							aria-valuemax="100" style="width: <%=request.getAttribute("percentile")%>%">
							<%=request.getAttribute("percentile")%> %
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="d3-content">
		<div class="container">
			<div id="wordCloud" class="renderContent"></div>
		</div>
	</section>
	<script>
	var fill = d3.scale.category20();
	function changeCall(){
		var selectedValue = <%=request.getAttribute("wordCloud")%>;
		d3.layout.cloud().size([600, 600])
			.words(selectedValue)
			.padding(2)
			.rotate(function() {
				return 0;
			})
			.font("Impact")
			.fontSize(function(d) {
				return d.size;
			})
			.on("end", draw)
			.start();
	}
	function draw(words) {
		d3.select("#wordCloud").append("svg")
			.attr("width", 700)
			.attr("height", 500)
			.append("g")
			.attr("transform", "translate(350,250)")
			.selectAll("text")
			.data(words)
			.enter().append("text")
			.style("font-size", function(d) {
				return d.size + "px";
			})
			.style("font-family", "Impact")
			.style("fill", function(d, i) {
				return fill(i);
			})
			.attr("text-anchor", "middle")
			.attr("transform", function(d) {
				return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
			})
			.text(function(d) {
				return d.text;
			});
	}
	</script>
</body>
</html>