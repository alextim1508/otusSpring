function addBook(){
    var formData = {
        title :  $("#createdTitleBookId").val(),
        authorId :  $("#createdAuthorBookId").val(),
        genreId :  $("#createdGenreBookId").val()
    };
    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/book",
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(book) {
            addToLog("Книга добавлена!");
            addRowToTable(book, book["authorId"], book["genreId"]);
        },
        error : function(e) {
            addToLog("Книга не добавлена :" + e.responseText);
        }
    });
}