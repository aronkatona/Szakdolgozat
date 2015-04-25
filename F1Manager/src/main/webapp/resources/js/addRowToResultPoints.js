$(document).ready(
    function() {
      $('#addRow').click( 
          function() {
        	  var lineCount = $('#myTable tr').length;
        	  $('#myTable tr:last').after( 
        			  "<tr>" +
	        			  "<td style='display:none;'><input type='text' name='ids' value='0'></input></td>" +
	        			  "<td><input class='form-control' type='text' name='results' value=" + lineCount + "></input></td>" +
	        			  "<td><input class='form-control' type='text' name='driverRacePoints' value='0'></input></td>" +
	        			  "<td><input class='form-control' type='text' name='driverQualificationPoints' value='0'></input></td>"+
	        			  "<td><input class='form-control' type='text' name='teamRacePoints' value='0'></input></td>"	+
	        			  "<td><input class='form-control' type='text' name='teamQualificationPoints' value='0'></input></td>" +
	        			  "<td><input class='form-control' type='text' name='rates' value=0></input></td>" +	
            		   "</tr>");
           });
    }
);


var resultPointId;

function setResultPointId(id){
	resultPointId = id;
}

function deleteResultPoint(){

	$.ajax({
		type: "GET",
		url : "deleteResultPoint&id=" + resultPointId,
		contentType: 'application/json',
	    mimeType: 'application/json',
	    
		success : function(data) {
			$('.row' + resultPointId).remove();
		},
		error: function(data){
				
		}
	});
}