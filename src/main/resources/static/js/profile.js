var userId = "7d045dd1-0088-4282-86e5-38f336ef05f2";

$(document).ready(function () {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/profiles/" + userId,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            $('#name').text(data['nickname']);
            $('#email').text(data['email']);
            $('#gender').text(data['gender']);
            $('#attraction').text(data['attraction']);
        },
        error: function() { alert('Failed!'); },

    });
});

