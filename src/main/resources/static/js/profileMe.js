$(document).ready(function () {
    $.ajax({
        headers: {'Authorization': localStorage.getItem('token')},
        url: "http://localhost:8080/profiles/me",
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

