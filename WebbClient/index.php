<html>
<title>Dalarna Stugby</title>
<pre>
<?php
		DEFINE("_SQL_SERVER_", "194.47.129.139");
		DEFINE("_SQL_USER_", "dv1454_ht15_33");
		DEFINE("_SQL_PASSWORD_", "U2TYTRc&");
		DEFINE("_SQL_DATABASE_", _SQL_USER_);
		// Connect to MSSQL
		$link = mssql_connect(_SQL_SERVER_, _SQL_USER_, _SQL_PASSWORD_);
		if (!$link || !mssql_select_db(_SQL_DATABASE_, $link)) {
  			die('Unable to connect or select database!');
		}

?>


</pre>

<head>


	<link rel="stylesheet" type="text/css" href="stylesheet.css">


	<script>

		function availableCabins() {
    		//alert(document.getElementById("birthday").value);

    		//kolla om datumet är kördag
    		//http://stackoverflow.com/questions/1181219/determine-if-a-date-is-a-saturday-or-a-sunday-using-javascript

    		var date = new Date(document.getElementById("birthday").value);
    		var day = date.getDay();
    		
    		if(day != 6){
    			alert("Du måste välja Lördag!")

    		}else{

    			var xhttp;
    			var str = document.getElementById("birthday").value;

    			if (window.XMLHttpRequest) {
    				// code for modern browsers
    				xhttp = new XMLHttpRequest();
    			} else {
   					 // code for IE6, IE5
    				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
  				}

  				xhttp.onreadystatechange = function(){
  					if (xhttp.readyState == 4 && xhttp.status == 200) { //response codes, 4 & 200 means the response was ok!
   						 document.getElementById("innerList").innerHTML = xhttp.responseText;
   				 			//om vi ska ha fler än en funktion som använder ajax så kan vi använda den v í har för att kolla den till en funktion test(xhttp);
  					}
  				}


  				xhttp.open("GET", "getCabins.php?q=" +str, true);
  				xhttp.send();
  			}



		}
	</script>


</head>

<Body>

<!---- Här kan vi börja skriva lite smått med html. saker som ska finnas med antar jag ska skrivas här-->

<h1 id="mainheader"> Dalarna stugby - Bokning </h1>

<div id="centerDiv">

	
	<div id="list">
		<h3 id="listHeader"> Tillgängliga stugor </h3>
		<div id="innerList">
			<p class="desc"> Ange ett datum för att se tillgängliga stugor</p>
			<!--- JS DOM WILL BE ADDED HERE -->
		</div>

	</div>


	<form  action="functions.php" method="post">
		<label>
			<p class="desc">Datum: </p>      <input type="date" id="birthday" name="date" size="20" onchange="availableCabins()" />
		</label>

		<label>
			<p class="desc">Personnummer:  </p>   <input type="text" name="pnr">
		</label>
		<label>
			<p class="desc">Full name: </p>       <input type="text" name="name">
		</label>
		<label>
			<p class="desc">Adress:  </p>         <input type="text" name="adress">
		</label>
		<label>
			<p class="desc">Postnummer: </p>      <input type="text" name="zip">
		</label>
		<label>
			<p class="desc">Postort: </p>         <input type="text" name="province">
		</label>
		<label>
			<p class="desc">Antal i sällskap: </p> <input type="text" name="groupSize">
		</label>
		<label>
			<p class="desc">Stug nummer: </p>      <input type="text" name="cabinNr">
		</label>
  		<input type="submit" value="submit" id="submit">
	</form>



</div>




</body>


</html>