function inviteUser(leagueId,userId,inviterName,velocityCount){
			
			$( "#inviteButton" + velocityCount).hide();
			$( "#invitedDiv" + velocityCount).show();
	

	    	var json = {"leagueId": leagueId, "userId": userId, "inviterName": inviterName};
	    	$.ajax({
	    		type: "POST",
	    		dataType: "json",
	    		data: JSON.stringify(json),
	    		url : "inviteUserToLeagueWithEmail",
	    		contentType: 'application/json',
	    	    mimeType: 'application/json',
	    	    
	    		success : function(data) {
	    			if(data) {
	    			}
	    			else{
	    				
	    			}
	    		},
	    		error: function(data){
	    				
	    		}
	    	});
}