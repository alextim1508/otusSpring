function login1() {
    var formData = {
        username : $("#loginUsernameId").val(),
        password : $("#loginPassId").val()
    };
    $.ajax({
        type : "post",
        url : "/login",
        data : formData,
        success : function(result) {
            addToLog("login 1: " + result["message"]);
        },
        error : function(err) {
            addToLog( JSON.parse(err.responseText)["message"]);
        }
    });
}

function login2() {
    var formData = {
        username : $("#loginUsernameId").val(),
        sms : $("#smsId").val()
    };
    $.ajax({
        type : "post",
        url : "/login",
        data : formData,
        success : function(result) {
            addToLog("login 2: " + result["message"]);
        },
        error : function(err) {
            addToLog(JSON.parse(err.responseText)["message"]);
        }
    });
}