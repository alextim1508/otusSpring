function addUser(){
    var formData = {
        username : $("#usernameId").val(),
        name : $("#nameId").val(),
        surname : $("#surnameId").val(),
        email : $("#emailId").val(),
        phone : $("#phoneId").val(),
        rawPassword : $("#passId").val()
    };
    $.ajax({
        type : "post",
        contentType : "application/json",
        url : "/account",
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            addToLog("Запрос на нового пользователя отправлен: " + result);
        },
        error : function(err) {
            addToLog("Ошибка добавления нового пользователя: " + JSON.parse(err.responseText)["message"]);
        }
    });
}