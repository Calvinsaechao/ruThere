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
    <% 
    if(session.getAttribute("email") != null)
    {
      response.sendRedirect("generateCode.jsp");
    }
    %>
    <!-- Navigation Bar -->
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
    <!-- Navigation Bar -->
      
    <!-- Background Image -->
    <section id="background">
    <div class="container">
      <aside id="sidebar">
        <!-- Forms Background -->
        <div class="dark">
          <h3>Instructor's Login</h3>
          <!-- Account Form -->
          <form action="login" method="post" class="quote">
            <!-- Input box container Title and Label -->
            <div>
              <label>Google Email</label><br>
              <input type="email" placeholder="Enter your Google Email" name="email">
            </div>
            <!-- Input box container Title and Label -->
            <!-- Input box container Title and Label -->
            <div>
              <label><br>
                Password</label>
              <input type="password" placeholder="Enter your password" name="password">
              <!-- Button Container -->
              <div class="buttonContainer">
                <br><button class="button_1" type="submit">Login</button>
              </div>
              <!-- Button Container -->
            </div>
            <!-- Input box container Title and Label -->
          </form>
          <!-- Account Form -->
        </div>
        <!-- Forms Background -->
      </aside>
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
