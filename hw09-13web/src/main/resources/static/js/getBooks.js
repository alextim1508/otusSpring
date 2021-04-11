function getBooks(handleData) {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/book",
        dataType: "json",
        success: function (result) {
            addToLog("Список книг успешно загружен!");
            handleData(result);
        },
        error: function (err) {
            addToLog("Ошибка получения списка книг: " + JSON.parse(err.responseText)["message"]);
        }
    });
}