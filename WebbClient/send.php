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


		$q = $_POST['grpSize'];
		$t = $_POST['date'];
		$cabinNr = $_POST['cbnNr'];
		$counter = $_POST['counter'];

		$sql = mssql_query("SELECT * FROM RESERVATIONS WHERE startDate = '$t' AND cabinNr = '$cabinNr'");
		$row = mssql_fetch_assoc($sql);

		$r_id = $row['r_id'];

		
	
		$time = strtotime($t);
		$month = date('n', $time);

	
		$type = $_POST['type'];
		

		if($month == 12 || $month < 3){
			
			$skiis = $_POST['skiis'];
			$poles = $_POST['poles'];
			$shoes = $_POST['shoes'];
			$head = $_POST['head'];

			

			$counter++;

			$stmnt = mssql_init('addWinterEq', $link);

			//BIND the values



			mssql_bind($stmnt, '@r_id', $r_id, SQLINT1);
			mssql_bind($stmnt, '@type', $type, SQLVARCHAR);
			mssql_bind($stmnt, '@skiis', $skiis, SQLINT1);
			mssql_bind($stmnt, '@poles', $poles, SQLINT1);
			mssql_bind($stmnt, '@shoes', $shoes, SQLFLT8);
			mssql_bind($stmnt, '@head', $head, SQLINT1);
			mssql_bind($stmnt, '@groupmemberNr', $counter, SQLINT1);

			//execute
			mssql_execute($stmnt);


			// Free statement
			mssql_free_statement($stmnt);
			
			header("Location: measurements.php?grpSize=$q&date=$t&cbnNr=$cabinNr&counter=$counter");

		}else{

			$type = $_POST['type'];
			$length = $_POST['length'];
			$head = $_POST['head'];

			$counter++;
			$stmnt = mssql_init('addSummerEq', $link);

			//BIND the values

			mssql_bind($stmnt, '@r_id', $r_id, SQLINT1);
			mssql_bind($stmnt, '@type', $type, SQLVARCHAR);
			mssql_bind($stmnt, '@length', $length, SQLINT1);
			mssql_bind($stmnt, '@head', $head, SQLINT1);
			mssql_bind($stmnt, '@groupmemberNr', $counter, SQLINT1);

			//execute
			mssql_execute($stmnt);


			// Free statement
			mssql_free_statement($stmnt);
			
			header("Location: measurements.php?grpSize=$q&date=$t&cbnNr=$cabinNr&counter=$counter");


		}

		


?>