function editBook(id){
    var formData = {
        firstname : $("#inputBook" + id).val(),
        lastname : $("#inputAuthor" + id).val(),
        lastname : $("#inputGenre" + id).val()
    }
    $.ajax({
        type : "post",
        contentType : "application/json",
        url : "/book/" + id,
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            addToLog("Книга обновлена!");
        },
        error : function(e) {
            addToLog(e.responseText);
        }
    });
}