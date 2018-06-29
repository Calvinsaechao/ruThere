<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <meta name="description" content="A simple web design by ruThere">
    <meta name="keywords" content="web design, ruThere">
    <meta name="author" content="ruThere">
    <title>ruThere? | Welcome</title>
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
        <li class="current"><a class="active" href="index.jsp" >Home</a></li>
        <li><a href="instructors.jsp">Instructors</a></li>
        <li><a href="students.jsp">Students</a></li>
      </ul>
      </nav>
    </div>
    </header>
    <!-- Navigation Bar -->
    
    <!--  Google Map Geolocation -->
    <div id="map"></div>  
    <script>
    // Note: This example requires that you consent to location sharing when
    // prompted by your browser. If you see the error "The Geolocation service
    // failed.", it means you probably did not give permission for the browser to
    // locate you.
    var map, infoWindow;
    function initMap() 
    {
      map = new google.maps.Map(document.getElementById('map'), 
      {
      center: {lat: 38.558760, lng: -121.423006},
      zoom: 10
      });
      infoWindow = new google.maps.InfoWindow;
    
      // Try .jsp5 geolocation.
      if (navigator.geolocation) 
      {
        navigator.geolocation.getCurrentPosition(function(position) 
        {
          var pos = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
          };
      
          infoWindow.setPosition(pos);
          infoWindow.setContent('Location found.');
          infoWindow.open(map);
          map.setCenter(pos);
        }, 
        function() 
        {
          handleLocationError(true, infoWindow, map.getCenter());
        });
      } 
      else 
      {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
      }
    }
    
    function handleLocationError(browserHasGeolocation, infoWindow, pos) 
    {
      infoWindow.setPosition(pos);
      infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
      infoWindow.open(map);
    }
    </script>
    
    <script async defer
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyApqj7Gt5LVuWrJayjkyLnMke8gSeBWlkA&callback=initMap">
    </script>
    <!--  Google Map Geolocation -->
    
    <!-- Footer -->
    <footer>
      <p>Web Design by ruThere, Copyright &copy; 2018</p>
    </footer>
    <!-- Footer -->
  </body>
</html>
