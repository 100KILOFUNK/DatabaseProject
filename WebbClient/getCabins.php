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

		$q = strval($_GET['q']);
		
			$sql = mssql_query("exec GetCabins @bookingdate ='$q'");
			


			while($row = mssql_fetch_assoc($sql)){

				
				echo '<p class="cabinlist">';
				echo 'Stugnummer: ';
				echo $row["c"];
				echo '</p>';
			

				echo '<p class="cabinlist">';
				echo 'Adress: ';
				echo $row["adress"];
				echo '</p>';
				
				echo '<p class="cabinlist">';
				echo 'Antalet bäddar: ';
				echo $row["beds"];
				echo '</br>';
				echo '</p>';


				//here we can check id the date is high ow low season and get high/low price


				echo '_________________';
				
				
			}

			




?>