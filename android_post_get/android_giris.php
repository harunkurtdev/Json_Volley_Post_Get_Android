<?php
require_once("db.php");

/*
RESPONSE CODES

100 - kullanıcı adı ve şifre doğru ise
101 - kullanıcı adı yada şifre hatalı ise
200 - sayfaya post tipinde istekte bulunulmadıysa

*/

if($_SERVER['REQUEST_METHOD']=="POST"){
	
	$email = $_POST['email'];
	$sifre = $_POST['sifre'];
	
	$db = new Db("localhost","android_db","root","","utf8");
	$db->query("SELECT * FROM uyeler WHERE email='".$email."' and sifre='".$sifre."'");
	$sonuc = $db->select();
	
	if(count($sonuc)==0){
		// Kullanıcı adı veya şifre yanlış ise
		echo "101";
	}else if(count($sonuc)==1){
		// Kullanıcı adı ve şifre doğru ise json döndür.
		print json_encode($sonuc);
	}
	
}else{
	//Sayfaya post tipinde erişilmediyse
	echo "200";
}



?>