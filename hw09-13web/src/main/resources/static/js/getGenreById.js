function getGenreById(id, handleData) {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/genre/" + id,
        dataType: "json",
        success: function (genre) {
            addToLog("Жанр с id " + id + " успешно загружен!");
            handleData(genre);
        },
        error: function (err) {
            addToLog("Ошибка получения жанра с id " + id + " : " + err);
        }

    });
}