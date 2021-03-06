function userExistByName(){
	
	var name = $( "#name" ).val();
	
	var json = {"name": name};
	
	var okey = false;
	
	if (name == null || name == "") {
        $( "#emptyName" ).show();    
        okey = true;
	 }
	 else{
		 $( "#emptyName" ).hide();
	 }
	
	
		$.ajax({
			type: "POST",
			async: false,
			dataType: "json",
			data: JSON.stringify(json),
			url : "checkExistUserName",
			contentType: 'application/json',
			mimeType: 'application/json',
			
			success : function(data) {
				if(data){
					$( "#existName" ).show();
					okey = true;
				}
				else{
					$( "#existName" ).hide();
				}
			},
			error: function(data){
					console.log(data);
			}
		});
	return okey;
}

function userExistByEmail(){
	var email = $( "#email" ).val();
	var emailCheckRegExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;

	var okey = false;
	
	var json = {"email": email};
	
    if(emailCheckRegExp.test(email)){
    	$( "#notValidEmail" ).hide();
    	$.ajax({
    		type: "POST",
    		async: false,
    		dataType: "json",
    		data: JSON.stringify(json),
    		url : "checkExistEmail",
    		contentType: 'application/json',
    		mimeType: 'application/json',
    		
    		success : function(data) {
    			if(data){
    				$( "#existEmail" ).show();
    				okey = true;
    			}
    			else{
    				$( "#existEmail" ).hide();
    			}
    		},
    		error: function(data){
    		}
    	});
    }
    else{
    	okey = true;
    	$( "#notValidEmail" ).show();
    	$( "#existEmail" ).hide();
    }
    
    return okey;
}


function validateRegistrationForm() {
    var name = $( "#name" ).val();
	var email = $( "#email" ).val();
	var pw1 = $( "#password" ).val();
	var pw2 = $( "#passwordAgain" ).val();
	
	var okey = true;
	var emailCheckRegExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	
	
	 if (name == null || name == "") {
	        $( "#emptyName" ).show();
	        okey = false;
	 }
	 else{
		 $( "#emptyName" ).hide();
	 }
	 
	 if (!emailCheckRegExp.test(email)) {
	        $( "#notValidEmail" ).show();
	        okey = false;
	 }
	 else{
		 $( "#notValidEmail" ).hide();
	 }
	 
	 if(userExistByName()){
	        okey = false;
	 }
	 
	 if(userExistByEmail()){
	        okey = false;
	 }
	 
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