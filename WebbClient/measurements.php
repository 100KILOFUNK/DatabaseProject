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


		$q = $_GET['grpSize'];
		$t = $_GET['date'];
		$cabinNr = $_GET['cbnNr'];
		$counter = $_GET['counter'];
		$dummy = 0;
		$sql = mssql_query("SELECT * FROM RESERVATIONS WHERE startDate = '$t' AND cabinNr = '$cabinNr'");
		$row = mssql_fetch_assoc($sql);
	
		$time = strtotime($t);
		$month = date('n', $time);

		if($counter === $q){
			header("Location: http://www.student.bth.se/~nigb14");
		}
		

		
			
		echo '<h1> Information om resenärer (mått)</h1>';
		echo $month;

		if($month == 12 || $month < 3){

			echo "highseason";

			echo '<form  action="send.php" method="post">';
			echo "Gäst #: ";
			echo $counter+1;
			echo '</br>';
			echo '</br>';
			echo '<select name="type">';
  			echo '<option value="distanceskiis">Längdåkning</option>';
  			echo '<option value="slopeskiis">utförsskidor</option>';
			echo '</select>';
			echo '</br>';
			echo '</br>';
			echo 'längd (Skidor) <input type="text" name="skiis">';
			echo '</br>';
			echo '</br>';
			echo 'längd (Stavar) <input type="text" name="poles">';
			echo '</br>';
			echo '</br>';
			echo 'Storlek (Skor) <input type="text" name="shoes">';
			echo '</br>';
			echo '</br>';
			echo 'Omkrets (huvud) <input type="text" name="head">';
			echo '</br>';
			echo '</br>';
			echo '<input type="submit" value="submit" id="submit">';
			echo '<input type="hidden" name="grpSize" value=';
			echo $q;
			echo ">";
			echo '<input type="hidden" name="date" value=';
			echo $t;
			echo ">";
			echo '<input type="hidden" name="cbnNr" value=';
			echo $cabinNr;
			echo ">";
			echo '<input type="hidden" name="counter" value=';
			echo $counter;
			echo ">";
			echo '<input type="hidden" name="length" value=';
			echo $dummy;
			echo ">";
			echo '</form>';
			echo '</br>';
		}else{
			echo '<form  action="send.php" method="post">';
			echo "Gäst #: ";
			echo $counter+1;
			echo '</br>';
			echo '</br>';
			echo '<select name="type">';
  			echo '<option value="countrybike">Landsvägscykel</option>';
  			echo '<option value="downhill">Down Hill</option>';
 			echo '<option value="mountainbike">Mountain Bike</option>';
			echo '</select>';
			echo '</br>';
			echo '</br>';
			echo 'Benlängd (insida lår) <input type="text" name="length">';
			echo '</br>';
			echo '</br>';
			echo 'Omkrets (huvud) <input type="text" name="head">';
			echo '</br>';
			echo '</br>';
			echo '<input type="submit" value="submit" id="submit">';
			echo '<input type="hidden" name="grpSize" value=';
			echo $q;
			echo ">";
			echo '<input type="hidden" name="date" value=';
			echo $t;
			echo ">";
			echo '<input type="hidden" name="cbnNr" value=';
			echo $cabinNr;
			echo ">";
			echo '<input type="hidden" name="counter" value=';
			echo $counter;
			echo ">";
			echo '</form>';
			echo '</br>';
		}





?>


