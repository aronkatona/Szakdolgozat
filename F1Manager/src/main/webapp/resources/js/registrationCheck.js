function userExistByName(){
		$.ajax({
			type: "GET",
			url : "checkExistUserName&userName=" + $( "#name" ).val(),
			contentType: 'application/json',
			mimeType: 'application/json',
			
			success : function(data) {
				if(data){
					$( "#existName" ).show();
				}
				else{
					$( "#existName" ).hide();
				}
			},
			error: function(data){
					console.log(data);
			}
		});
		
}

function userExistByEmail(){
	var email = $( "#email" ).val();
	var emailCheckRegExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	
    if(emailCheckRegExp.test(email)){
    	$( "#notValidEmail" ).hide();
    	$.ajax({
    		type: "GET",
    		url : "checkExistEmail&email=" + email,
    		contentType: 'application/json',
    		mimeType: 'application/json',
    		
    		success : function(data) {
    			if(data){
    				$( "#existEmail" ).show();
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
    	$( "#notValidEmail" ).show();
    	$( "#existEmail" ).hide();
    }
	
	
}