function userExistByName(){
	
	var json = {"name": $( "#name" ).val()};
	
		$.ajax({
			type: "POST",
			dataType: "json",
			data: JSON.stringify(json),
			url : "checkExistUserName",
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
	
	var json = {"email": email};
	
    if(emailCheckRegExp.test(email)){
    	$( "#notValidEmail" ).hide();
    	$.ajax({
    		type: "POST",
    		dataType: "json",
    		data: JSON.stringify(json),
    		url : "checkExistEmail",
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