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
      <aside id="sidebar">
        <!-- Forms Background -->
        <div class="dark">
          <h3>Student's page</h3>
          <form action="returnToStudent" method="post" class="quote">
            <div>
              <label>The information did not match. Please try again</label><br>
              <!-- Button Container -->
              <div class="buttonContainer">
                <br><button class="button_4" type="submit">Try again</button>
              </div>
              <!-- Button Container -->
            </div>
          </form>
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