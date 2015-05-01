function validateForgetPasswordForm() {
	var pw1 = $( "#password" ).val();
	var pw2 = $( "#passwordAgain" ).val();
	
	var okey = true;
	
	 if (pw1.length < 6) {
	        $( "#pw1Short" ).show();
	        okey = false;
	 }
	 else{
		 $( "#pw1Short" ).hide();
	 }
	 
	 if (pw2.length < 6) {
	        $( "#pw2Short" ).show();
	        okey = false;
	 }
	 else{
		 $( "#pw2Short" ).hide();
	 }
	 
	 if(pw1.length > 5 && pw2.length > 5 && pw1 != pw2){
		 $( "#pwNotSame1" ).show();
		 $( "#pwNotSame2" ).show();
		 okey = false;
	 }
	 else{
		 $( "#pwNotSame1" ).hide();
		 $( "#pwNotSame2" ).hide();
	 }
	 
	
	return okey;
	
}

function checkPw(){
	var pw1 = $( "#password" ).val();
	var pw2 = $( "#passwordAgain" ).val();
	
	if (pw1.length < 6) {
        $( "#pw1Short" ).show();
	 }
	 else{
		 $( "#pw1Short" ).hide();
	 }
	
	if (pw2.length < 6) {
        $( "#pw2Short" ).show();
	 }
	 else{
		 $( "#pw2Short" ).hide();
	 }
	
	
	 if(pw1.length > 5 && pw2.length > 5 && pw1 != pw2){
		 $( "#pwNotSame1" ).show();
		 $( "#pwNotSame2" ).show();
	 }
	 else{
		 $( "#pwNotSame1" ).hide();
		 $( "#pwNotSame2" ).hide();
	 }
	
}