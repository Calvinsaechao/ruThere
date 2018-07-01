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
      button {text-align:center;}
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

    <!-- Background Image -->
    <section id="background">
      <div class="container">
        <aside id="sidebar">
          <div class="dark">
            <h3>Get Attendance</h3>
            <form action="validateStudent" method="post" class="quote">
              <!-- Input box container Title and Label -->
              <div>
                <label>Instructor email</label><br>
                <input name="email" type="email" placeholder="Type your instructor's email" required minLength="1">
              </div>
              <!-- Input box container Title and Label -->
              <div>
                <label>Section Name</label><br>
                <input name="sheetName" type="text" placeholder="Enter your section name" required minLength="1">
              </div>
              <!-- Input box container Title and Label -->
              <div>
                <label>Student ID</label><br>
                <input name="sid" type="text" placeholder="9 digits exactly" required minLength="9" maxLength="9">
              </div>
              <!-- Input box container Title and Label -->
              <div>
                <label>Key</label><br>
                <input name="password" type="text" placeholder="4 digits provided by professor" required minLength="4" maxLength="4">
              </div>
              <!-- Input box container Title and Label -->
              <div>
                <label>Your message</label><br>
                <input name="answer" type="text" placeholder="Up to 140 characters" required maxLength="140" minLength="1">
                <input type="hidden" name="coordLat" id ="coordLat" value="" />
                <input type="hidden" name="coordLng" id ="coordLng" value="" />
              </div>
              <!-- Button Container -->
              <div class="buttonContainer">
                <br><button class="button_5" id="btn" type="submit" style="opacity: 0.5;" disabled>N/A</button>
                <p id="showError" style="color:#F01620;"></p>
              </div>
              <!-- Button Container -->
            </form>
          </div>
        </aside>
      </div>
    </section>
    
    <script>
    // Start this function when page is loaded.
    var x = document.getElementById("showError");
    var options = {
            enableHighAccuracy: true,
            timeout: 10000,
            maximumAge: 0
    };
    window.onload = function getLocation() 
    {    
        if (navigator.geolocation) 
        {
          navigator.geolocation.getCurrentPosition(showPosition, showError,options);    
        } 
        else 
        { 
          console.log("Geolocation is not supported by this browser.");
        }
    };
    function showPosition(position, showError) 
    {
        document.getElementById('coordLat').value = position.coords.latitude + "";
        document.getElementById('coordLng').value = position.coords.longitude + "";
        document.getElementById('btn').disabled = false;
        document.getElementById("btn").style.opacity = "1.0";
        document.getElementById("btn").innerText = 'Submit';
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
      
        <footer>
          <p>Web Design by ruThere, Copyright &copy; 2018</p>
        </footer>
  </body>
</html>
