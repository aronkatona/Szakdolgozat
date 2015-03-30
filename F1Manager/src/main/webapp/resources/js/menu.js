$(document).ready(
    function() {
    	$("#menu-toggle").click(function(e) {
    	        e.preventDefault();
    	        $("#wrapper").toggleClass("toggled");
    			$("#menuList").css("display", "block");
    	});
    }
);

