<!DOCTYPE html>
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
		
		<section id="background">
		<div class="container">
			<aside id="sidebar">
				<div class="dark">
					<h3>Student's page</h3>
					<form action="success" method="post" class="quote">
						<div>
							<label>The information did not match. Please try again</label><br>
						</div>
						<center><button class="button_1" type="submit">Try again</button></center>
					</form>
				</div>
			</aside>
		</div>
		</section>
	
		<footer>
			<p>Web Design by ruThere, Copyright &copy; 2018</p>
		</footer>
	</body>
</html>