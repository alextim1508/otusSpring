function getAuthors(handleData) {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/author",
        dataType: "json",
        success: function (result) {
            addToLog("Список авторов успешно загружен!");
            handleData(result);
        },
        error: function (err) {
            addToLog("Ошибка получения списка авторов: " + err);
        },
        async: false
    });
}