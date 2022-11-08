$(function () {

    $('#block-user').hide(0)

    drow();


    $('.btn-check[name = vbtn-radio]').change(function () {

        $('.btn-group-vertical label').removeClass('active');
        $("label[for='" + this.id + "']").addClass('active');

        var target = $('#block-' + $(this).val());

        $('.block-text').not(target).hide(0);
        target.show(450);
    });

    // $('.table-body button[data-toggle=modal]').click(function () {
    //     var userId = $(this).attr('id');
    //     user = userId;
    //     $.ajax({
    //         url: '/getUser/' + userId,
    //         async: false,
    //         success: function (result) {
    //             var roles = JSON.parse(result).roles;
    //             var array = roles.split(" ");
    //             $('#del5').empty();
    //             $('#del1').attr('placeholder', JSON.parse(result).id);
    //             $('#del2').attr('placeholder', JSON.parse(result).name);
    //             $('#del3').attr('placeholder', JSON.parse(result).surname);
    //             $('#del4').attr('placeholder', JSON.parse(result).email);
    //             for (var i = 0; i<array.length-1; i++) {
    //                 $('#del5').append('<option>' + array[i] + '</option>');
    //             }
    //
    //             $('#ed1').attr('value', JSON.parse(result).id);
    //             $('#ed2').attr('value', JSON.parse(result).name);
    //             $('#ed3').attr('value', JSON.parse(result).surname);
    //             $('#ed4').attr('value', JSON.parse(result).email);
    //             $('#ed6').attr('value', JSON.parse(result).dateOfBirth);
    //         }
    //     })
    // });

    $('#delete-form').submit(function (e) {
        var user = $('#del1').attr('placeholder');
        $.ajax({
            type: 'DELETE',
            url: 'http://localhost:8080/delete/' + user,
            async: false,
            success: function () {
                console.log('+')
                drow()
            },
            error: function () {
                console.log('-')
                drow()
            }
        })
        $('#delete').modal('toggle');

    })

    $('#edit-form').submit(function (e) {

        var ident = $('#ed1').val()
        var name = $('#ed2').val()
        var surname = $('#ed3').val()
        var email = $('#ed4').val()
        var role = $('#ed5').val()
        var date = $('#ed6').val()
        $.ajax({
            type: 'PUT',
            url: '/update',
            dataType: 'json',
            data: { "password" : "12345", "roles" : role.toString(), "id" : ident, "name" : name, "surname" : surname, "dateOfBirth" : date, "email" : email, "wannaBeAdmin" : false },
            success: function () {
                console.log('+')
                drow()
            },
            error: function () {
                console.log('-')
                drow()
            }
        })
        $('#edit').modal('toggle');


    });

    $('#add-form').submit(function (e) {
        var name = $('#new1').val()
        var surname = $('#new2').val()
        var date = $('#new3').val()
        var email = $('#new4').val()
        var password = $('#new5').val()
        var role = $('#new6').val()
        $.ajax({
            type: 'POST',
            url: '/add',
            dataType: 'json',
            data: { "password" : password, "roles" : role.toString(), "id" : null, "name" : name, "surname" : surname, "dateOfBirth" : date, "email" : email, "wannaBeAdmin" : false }
        })
        //отмена действия по умолчанию для кнопки submit
        e.preventDefault();
        window.location.replace('/admin')
    });

    requestsTable()

    $('#requests-body button').click(function (){
        $.ajax({
            type: 'PUT',
            url: $(this).attr('id'),
            async: false,
            cache: false,
            success: function () {
            }
        })
        requestsTable()
        window.location.replace('/admin')
    })

});

function requestsTable(){
    $('#requests-body').children().hide();
    $.ajax({
        url: '/getUsers',
        async: false,
        cache: false,
        success: function (result) {
            var users = JSON.parse(result);
            var table = $('#requests-body');
            for (var i = 0; i < users.length; i++) {
                if (users[i].wannaBeAdmin == true){
                    var role = users[i].roles;
                    console.log(role)
                    if (role == 'USER'){
                        table.append('<tr>');
                        table.append('<td>' + users[i].id + '</td>');
                        table.append('<td>' + users[i].name + '</td>');
                        table.append('<td>' + users[i].surname + '</td>');
                        table.append('<td>' + new Date(users[i].dateOfBirth) + '</td>');
                        table.append('<td>' + users[i].email + '</td>');
                        table.append('<td><button class="btn btn-success" ' +
                            ' id="/make-admin/'+ users[i].id +'">' +
                            'Make admin' +
                            '</button></td>>');
                        table.append('<td><button class="btn btn-danger" ' +
                            ' id="/reject-request/'+ users[i].id +'">' +
                            'Reject request' +
                            '</button></td>>');
                    }
                }
            }
        }
    })
}

function drow() {
    $('#table-body').children().hide()
    $.ajax({
        url: '/getUsers',
        async: false,
        cache: false,
        success: function (result) {
            var users = JSON.parse(result);
            var table = $('#table-body');
            for (var i = 0; i < users.length; i++) {
                table.append('<tr>');
                table.append('<td>'+ users[i].id +'</td>');
                table.append('<td>'+ users[i].name +'</td>');
                table.append('<td>'+ users[i].surname +'</td>');
                table.append('<td>'+ new Date(users[i].dateOfBirth) +'</td>');
                table.append('<td>'+ users[i].email +'</td>');
                table.append('<td>'+ users[i].roles +'</td>');
                table.append('<td><button class="btn btn-secondary" data-toggle="modal"' +
                    ' data-target="#edit" id="' + users[i].id + '" onclick="loadForm(' + users[i].id + ')">' +
                    'Edit' +
                    '</button></td>>');
                table.append('<td><button class="btn btn-danger" data-toggle="modal"' +
                    ' data-target="#delete" id="' + users[i].id + '" onclick="loadForm(' + users[i].id + ')">' +
                    'Delete' +
                    '</button></td>>');
            }
        }
    })
}

function loadForm (id) {
    var userId = id;
    $.ajax({
        url: '/getUser/' + userId,
        async: false,
        success: function (result) {
            var roles = JSON.parse(result).roles;
            var array = roles.split(" ");
            $('#del5').empty();
            $('#del1').attr('placeholder', JSON.parse(result).id);
            $('#del2').attr('placeholder', JSON.parse(result).name);
            $('#del3').attr('placeholder', JSON.parse(result).surname);
            $('#del4').attr('placeholder', JSON.parse(result).email);
            for (var i = 0; i<array.length-1; i++) {
                $('#del5').append('<option>' + array[i] + '</option>');
            }

            $('#ed1').attr('value', JSON.parse(result).id);
            $('#ed2').attr('value', JSON.parse(result).name);
            $('#ed3').attr('value', JSON.parse(result).surname);
            $('#ed4').attr('value', JSON.parse(result).email);
            $('#ed6').attr('value', JSON.parse(result).dateOfBirth);
        }
    })
}

