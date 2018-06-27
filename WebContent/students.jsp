<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width">
		<meta name="description" content="A simple web design by ruThere">
		<meta name="keywords" content="web design, ruThere">
		<meta name="author" content="ruThere">
		<title>ruThere? | Students</title>
		<style>
			<%@ include file="css/style.css"%>
		</style>
		<link rel="stylesheet" href="css/style.css">
	</head>
	
	<body>
		<!--  Navigation Bar -->
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

		<!-- Background -->
		<section id="background">
			<div class="container">
				<aside id="sidebar">
					<div class="dark">
						<h3>Get Attendance</h3>
						<form action="validateStudent" method="post" class="quote">
							<div>
								<label>SID</label><br>
								<input name="sid" type="text" placeholder="Enter your student ID">
							</div>
							<div>
								<label>Key</label><br>
								<input name="password" type="password" placeholder="Enter today's key provided by the professor">
							</div>
							<div>
								<label>Quiz Answer</label><br>
								<input name="answer" type="text" placeholder="Enter your answer to today's quiz. (up to 140 characters)">
							</div>
							<button class="button_1" type="submit">Submit</button>
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
