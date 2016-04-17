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


if($_SERVER["REQUEST_METHOD"] == "POST"){
	

	$pnr = $_POST['pnr'];
	$name = $_POST['name'];
	$adress = $_POST['adress'];
	$zip = $_POST['zip'];
	$province = $_POST['province'];
	$groupSize = $_POST['groupSize'];
	$cabinNr = $_POST['cabinNr'];
	$date = $_POST['date'];

	$tmp = strval($date);
	$time = strtotime($date);
	

	$test = date('D', $time);
	

	if(date('D', $time)=== 'Sat'){
		//create new SP
		$stmnt = mssql_init('MAKERESERVATION', $link);

		//BIND the values

		mssql_bind($stmnt, '@civicNr', $pnr, SQLFLT8);
		mssql_bind($stmnt, '@name', $name, SQLVARCHAR);
		mssql_bind($stmnt, '@adress', $adress, SQLVARCHAR);
		mssql_bind($stmnt, '@zipCode', $zip, SQLINT1);
		mssql_bind($stmnt, '@province', $province, SQLVARCHAR);
		mssql_bind($stmnt, '@groupsize', $groupSize, SQLINT1);
		mssql_bind($stmnt, '@cabinNr', $cabinNr, SQLINT1);
		mssql_bind($stmnt, '@startDate', $date, SQLVARCHAR);

	//execute
		mssql_execute($stmnt);


	// Free statement
		mssql_free_statement($stmnt);
		
		
		header("Location: measurements.php?grpSize=$groupSize&date=$date&cbnNr=$cabinNr&counter=0");
	}else{
		header("Location: http://www.student.bth.se/~nigb14");
	}
}

	//$sql = mssql_query('exec @civicNr = $pnr, @name = $name, @adress = $adress, @zipCode = $zip, @province = $province, @groupsize = $groupSize, @cabinNr = $cabinNr, @startDate = $date');





?>