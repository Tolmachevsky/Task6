$(function (){

        $.ajax({
            url: '/getUser/' + $('#user-id').text(),
            async: false,
            cache: false,
            success: function (result) {
                var user = JSON.parse(result);
                console.log(user.wannaBeAdmin)
                if (user.wannaBeAdmin == true) {
                    $('#admin-request').hide();
                }
            }
        })

    $('#admin-request').click(function (){
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/request/' + $('#user-id').text(),
            async: false,
            success: function () {
                console.log('+')
            },
            error: function () {
                console.log('-')
            }
        })

        $(this).hide();
    })
})