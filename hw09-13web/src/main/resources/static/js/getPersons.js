function getPersons() {
    $.ajax({
        type: "get",
        contentType: "application/json",
        url: "/person",
        dataType: "json",
        success: function (result) {
            addToLog("Запрашиваем список персонов");
            createPersonsTable(result);
            addToLog("Список персонов успешно загружен!");
        },
        error: function (err) {
            addToLog("Ошибка " + err);
        }
    });
}