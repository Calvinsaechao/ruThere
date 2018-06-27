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
			<!-- Select Class Menu -->
			<aside id="sidebar">
				<div class="dark">
					<h3>Get Attendance</h3>
					<form action="studentsSuccess.jsp" method="post" class="quoteQuiz">
						<div>
							<label>Select a class to send the key to.</label><br>
						</div>
						<div>
							<p>
							  <label>Choose a class</label>
							  <select id="sheetId">
							    <option value="default">default</option>
							    <option>test1</option>
							    <option>test2</option>
						      </select>
					      </p>
							<p>
							  <label>Quiz Answer (Optional)</label>
							  <br>
								<textarea name="answer" type="text" placeholder="Enter your answer to today's quiz. (up to 140 characters)"></textarea>
							</p>
						</div>
						<button class="button_1" type="submit">Submit</button>
					</form>
				</div>
			</aside>
			<!-- Select Class Menu -->
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