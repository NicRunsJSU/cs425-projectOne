var ProjectOne = ( function() {
    
    
    return {
        
        init: function() {
            
            $("#version").html("jQuery Version: " + $().jquery );
            
                
            
            
        },
        
        submitSearch: function() {
            
             if ( $("#search").val() === "" ) {

                alert("INVALID! Please enter search!");
                
                return false;

            }

            $.ajax({

                url: 'registration',
                method: 'GET',
                data: $('#search').serialize(),

                success: function(response) {

                    $("#results").html(response);

                }

            });

            return false;

        },
        
        submitReg: function() {
            
            var that = this;
            
            $.ajax({
                
                url: 'registration',
                method: 'POSt',
                data: $('#registration').serialize(),
                dataType: 'json',
                
                susccess: function(response) {
                    that.registerWorked(response);
                }
                      
                
            });
            
            
        },
        
        
        registerWorked: function(result) {
            
            var lineOneOutput = "<p>You have registered as: " + result["displayName"] + "</p>";
            var lineTwoOutPut = "<p>Registration Code: " + result ["code"];
            
            
            var combine = lineOneOutput = lineTwoOutput;
            
            $("#result").html(combine);
        }
            
        };
        
        
        
        
        }());
    
    
    



