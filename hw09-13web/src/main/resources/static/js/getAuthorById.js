function getAuthorById(id, handleData) {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/author/" + id,
        dataType: "json",
        success: function (author) {
            addToLog("Автор с id " + id + " успешно загружен!");
            handleData(author);
        },
        error: function (err) {
            addToLog("Ошибка получения автора с id " + id + " : " + JSON.parse(err.responseText)["message"]);
        }
    });
}