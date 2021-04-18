function logout() {
    $.ajax({
        type : "POST",
        url : "/logout",

        success : function(res) {
            addToLog("Вы вышли");
        },
        error : function(err) {
            addToLog("Ошибка выхода");
        }
    });
}