function showForgetPasswordDiv(){
		$( "#forgetPasswordDiv" ).show();
		$( "#forgetPwOpenerDiv" ).hide();
	}
	
function forgottenPassword(){
	
		
		$( "#emailSentOut" ).hide();
		$( "#emailFail" ).hide();
		
		var email = $( "#email" ).val();
		var emailCheckRegExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		
	    if(emailCheckRegExp.test(email)){
	    	$( "#notValidEmail" ).hide();	
	    	$( "#emailProcessing" ).show();
	    	var json = {"email": email};
	    	$.ajax({
	    		type: "POST",
	    		dataType: "json",
	    		data: JSON.stringify(json),
	    		url : "sendNewPasswordToken",
	    		contentType: 'application/json',
	    	    mimeType: 'application/json',
	    	    
	    		success : function(data) {
	    			if(data) {
	    				$( "#emailProcessing" ).hide();
	    				$( "#emailSentOut" ).show();
	    			}
	    			else{
	    				$( "#emailProcessing" ).hide();
	    				$( "#emailFail" ).show();
	    			}
	    		},
	    		error: function(data){
	    				$( "#emailProcessing" ).hide();
	    				$( "#emailFail" ).show();
	    		}
	    	});
	    }
	    else{
	    	$( "#notValidEmail" ).show();	    	
	    }
		
		
		
		
}