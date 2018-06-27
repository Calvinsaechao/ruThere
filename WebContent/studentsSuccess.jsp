<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width">
		<meta name="description" content="A simple web design by ruThere">
		<meta name="keywords" content="web design, ruThere">
		<meta name="author" content="ruThere">
		<title>ruThere? | Instructors</title>
		<style>
			<%@ include file="css/style.css"%>
		</style>
		<link rel="stylesheet" href="css/style.css">
	</head>
	
	<body>
		<!-- Navigation Bar -->
		<header>
			<div class="container">
				<div id="branding">
					<h1><span class="highlight">ru</span> THERE?</h1>
				</div>
				<nav>
					<ul>
						<li><a href="index.jsp">Home</a></li>
						<li><a href="instructors.jsp">Instructors</a></li>
						<li class="current"><a href="students.jsp">Students</a></li>
					</ul>
				</nav>
			</div>
		</header>
		<!-- Navigation Bar -->
		
		<!-- Background Image -->
		<section id="background">
		<div class="container">
			<!-- Generate Key -->
			<aside id="sidebar">
				<div class="dark">
					<h3>Get Attendance</h3>
					<form action="students.jsp" method="post" class="quote">
						<div>
							<label>Key has been submitted!</label><br>
						</div>
						<button class="button_1" type="submit">Return</button>
					</form>
				</div>
			</aside>
			<!-- Generate Key -->
		</div>
		</section>
		<!-- Background Image -->
	
		<!-- Footer -->
		<footer>
			<p>Web Design by ruThere, Copyright &copy; 2018</p>
		</footer>
		<!-- Footer -->
	</body>
</html>