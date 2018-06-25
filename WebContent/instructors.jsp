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
    <link rel="stylesheet" href="./css/style.css">
  </head>
  <body>
  	<% 
 		if(session.getAttribute("email") != null){
 			response.sendRedirect("generateCode.jsp");
 		}
  	%>
    <header>
      <div class="container">
        <div id="branding">
          <h1><span class="highlight">ru</span> THERE?</h1>
        </div>
        <nav>
          <ul>
            <li><a href="index.jsp">Home</a></li>
            <li class="current"><a href="instructors.jsp">Instructors</a></li>
            <li><a href="students.jsp">Students</a></li>
          </ul>
        </nav>
      </div>
    </header>
	  	  
    <section id="container-background2">
      <div class="container-background2">
      </div>
    </section>

      <div class="container">
        <aside id="sidebar">
          <div class="dark">
            <h3>Instructor's Login</h3>
            <form action="login" method="post" class="quote">
			  <div>
  							<label>Google Email</label><br>
				<input type="email" placeholder="Enter your Google Email" name="email">
			  </div>
			  <div>
  							<label>Password</label><br>
				<input type="password" placeholder="Enter your password" name="password">
  						</div>
<button class="button_1" type="submit">Submit</button>
			</form>
          </div>
        </aside>

	  </div>

    <footer>
      <p>Web Design by ruThere, Copyright &copy; 2018</p>
    </footer>
  </body>
</html>
