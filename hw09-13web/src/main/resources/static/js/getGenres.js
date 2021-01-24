function getGenres(handleData) {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/genre",
        dataType: "json",
        success: function (result) {
            addToLog("Список жанров успешно загружен!");
            handleData(result);
        },
        error: function (err) {
            addToLog("Ошибка получения списка жанров: " + err);
        },
        async: false
    });
}