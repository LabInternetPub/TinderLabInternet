$(document).ready(function () {
        $.ajax({
            type:"POST",
            url:'http://localhost:8080/profiles',
            dataType: 'json',
            contentType: 'application/json',
            data:JSON.stringify( {
                "email": "manu@tecnocampus.cat",
                "nickname": "manuel",
                "gender": "Man",
                "attraction": "Woman",
                "passion": "Sport"
            }),
            error : function(err) {
                console.log('Error!', err);
                console.log('error');
                alert('Error ' + err);
            },
            success: function(data) {
                alert('success');
                console.log("Data: " + data);
            },
        })
    });
