<?php
require_once("db.php");

/*
100 - veritabanına veri eklendiyse 
101 - veritabanına veri eklenemediyse
200 - sayfaya post tipi ile bağlanmadı.
*/

if($_SERVER['REQUEST_METHOD']=="POST"){
	
	$adSoyad = $_POST['adsoyad'];
	$telefonno = $_POST['telefonno'];
	$sifre = $_POST['sifre'];
	$email = $_POST['email']
	
	$db = new Db("localhost","android_db","root","","utf8");
	$db->query("INSERT INTO uyeler(ad_soyad,telefon_no,sifre,email) VALUES ('".$adSoyad."','".$telefonno."','".$sifre."','".$email."')");
	if($db->insert()){
		echo "100";
	}else{
		echo "101";
	}
	
}else{
	echo "200";
}



?>