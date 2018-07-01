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
    if(session.getAttribute("email") == null){
    response.sendRedirect("instructors.jsp");
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
    <section id="background">
    <div class="container">
    <aside id="sidebar">
      <div class="dark">
        <h3>Welcome!</h3>
        
        <form action="gradeCourses" method="post" class="quote">
            <div>
              <label>Type the name of the section</label><br>
              <input type="text" placeholder="Enter section for grading" name="sheetName">
            </div>
            <button class="button_3" type="submit">Grade</button>
          </form>
          
        <form action="generate" method="post" class="quote" accept-charset="UTF-8">
          <div>
            <label>Type the name of the section</label><br>
          </div>
          <div>
          <input type="text" placeholder="Enter section for generating code" name="sheetName">
          <input type="hidden" name="coordLat" id ="coordLat" value="" />
          <input type="hidden" name="coordLng" id ="coordLng" value="" />
          </div>
          <button class="button_3" id="btn" type="submit" style="opacity: 0.5;" disabled>N/A</button>
          <p id="showError" style="color:#F01620;"></p>
          <script>
          // Start this function when page is loaded.
          var x = document.getElementById("showError");
          var options = {
                enableHighAccuracy: true,
                timeout: 10000,
                maximumAge: 0
          };
          window.onload = function getLocation() {    
              if (navigator.geolocation) {
                  navigator.geolocation.getCurrentPosition(showPosition,showError,options);    
              } else { 
                  console.log("Geolocation is not supported by this browser.");
              }
              
          };

          function showPosition(position) {
            document.getElementById('coordLat').value = position.coords.latitude + "";
            document.getElementById('coordLng').value = position.coords.longitude + "";
            document.getElementById('btn').disabled = false;
            document.getElementById("btn").style.opacity = "1.0";
            document.getElementById("btn").innerText = 'Generate Code';
            console.log("Ready to generate code.")
          }
          function showError(error) {
                switch(error.code) {
                    case error.PERMISSION_DENIED:
                        x.innerHTML = "Error: User denied the request for Geolocation."
                        break;
                    case error.POSITION_UNAVAILABLE:
                        x.innerHTML = "Error: Location information is unavailable."
                        break;
                    case error.TIMEOUT:
                        x.innerHTML = "Error: The request to get user location timed out."
                        break;
                    case error.UNKNOWN_ERROR:
                        x.innerHTML = "Error: An unknown error occurred."
                        break;
                }
            } 
          </script>
      </form>
        <form action="logout" method="post" class="quote">
        <div class="buttonContainer3">
          <button class="button_2" type="submit">Logout</button>
        </div>
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