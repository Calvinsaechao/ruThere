<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <meta name="description" content="Affordable and professional web design">
	  <meta name="keywords" content="web design, affordable web design, professional web design">
  	<meta name="author" content="Brad Traversy">
    <title>ruThere? | Students</title>
    <style>
    	<%@ include file="css/style.css"%>
    </style>
    <link rel="stylesheet" href="./css/style.css">
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
<!--
    <section id="newsletter">
      <div class="container">
        <h1>Subscribe To Our Newsletter</h1>
        <form>
          <input type="email" placeholder="Enter Email...">
          <button type="submit" class="button_1">Subscribe</button>
        </form>
      </div>
    </section>
-->
	  	  
    <section id="container-background3">
      <div class="container-background3">
      </div>
    </section>

<!--
    <section id="main">
-->
	<section id="showcase3">
      <div class="container">
	  <!--
        <article id="main-col">

          <h1 class="page-title">Services</h1>
          <ul id="services">
            <li>
              <h3>Website Design</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus mi augue, viverra sit amet ultricies at, vulputate id lorem. Nulla facilisi.</p>
						  <p>Pricing: $1,000 - $3,000</p>
            </li>
            <li>
              <h3>Website Maintenance</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus mi augue, viverra sit amet ultricies at, vulputate id lorem. Nulla facilisi.</p>
						  <p>Pricing: $250 per month</p>
            </li>
            <li>
              <h3>Website Hosting</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus mi augue, viverra sit amet ultricies at, vulputate id lorem. Nulla facilisi.</p>
						  <p>Pricing: $25 per month</p>
            </li>
          </ul>
        </article>
		-->
        <aside id="sidebar">
          <div class="dark">
            <h3>Get Attendance</h3>
            <form action="ruThereServlet" method="post" class="quote">
  						<div>
  							<label>SID</label><br>
  							<input type="text" placeholder="Enter your student ID">
  						</div>
  						<div>
  							<label>Key</label><br>
  							<input type="password" placeholder="Enter today's key provided by the professor">
  						</div>
  						<div>
  							<label>Quiz Answer</label><br>
  							<input type="text" placeholder="Enter your answer to today's quiz. (up to 140 characters)">
  						</div>
  						<button class="button_1" type="submit">Submit</button>
					</form>
          </div>
        </aside>

	  </div>
	</section>		
<!--
    </section>
-->
    <footer>
      <p>Web Deisgn by ruThere, Copyright &copy; 2018</p>
    </footer>
  </body>
</html>
