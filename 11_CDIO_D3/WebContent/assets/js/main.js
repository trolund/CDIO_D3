// menu mobil button 
    $(document).ready(function(){
      $("#menu_but").click(function(){
        $("#sidepanel ul").toggleClass("on");
        $("#sidepanel").toggleClass("on_sidepanel");
        $("#menu_but").toggleClass("rot");
      });
    });

// user button menu 
    $(document).ready(function(){
      $("#user_but").click(function(){       
          $( "#content_box" ).load( 'adduser.html');
      });
    });

// ajax get user data 
    jQuery.ajax({
        url: "api/opr/getname",
        type: "GET",
        contentType: 'text/plain',
        success: function(resultData) {
            $('#oprName').html(resultData);
        },
        error : function(jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000,
    });