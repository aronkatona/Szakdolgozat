function userExistByName(){
	
	var id = $( "#id" ).val();
	var name = $( "#name" ).val();
	
	var okey = false;
	
	var json = {"name": name, "id": id};
	
	if (name == null || name == "") {
        $( "#emptyName" ).show();
        okey = false;
	 }
	 else{
		 $( "#emptyName" ).hide();
	 }
	
	
		$.ajax({
			type: "POST",
			async: false,
			dataType: "json",
			data: JSON.stringify(json),
			url : "checkExistUserNameUpdateProfile",
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
			}
		});
		
	return okey;
		
}

function userExistByEmail(){
	var id = $( "#id" ).val();
	var email = $( "#email" ).val();
	var emailCheckRegExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;

	var okey = false;
	
	var json = {"email": email, "id": id};
	
    if(emailCheckRegExp.test(email)){
    	$( "#notValidEmail" ).hide();
    	$.ajax({
    		type: "POST",
    		async: false,
    		dataType: "json",
    		data: JSON.stringify(json),
    		url : "checkExistEmailUpdateProfile",
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

function validateNameForm(){
	
	var name = $( "#name" ).val();
	var email = $( "#email" ).val();
	
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
	 
	 return okey;
	
}

function validatePasswordForm() {
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