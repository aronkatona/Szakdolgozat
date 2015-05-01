function sendEmail(){
	
		$( "#activateFailDiv" ).hide();
		$( "#emailSentOut" ).hide();
		$( "#emailFail" ).hide();
		$( "#emailProcessing" ).show();
		
	
		var json = {"id": $( "#userId" ).val()};
	
		$.ajax({
		type: "POST",
		dataType: "json",
		data: JSON.stringify(json),
		url : "sendNewActivationCode",
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