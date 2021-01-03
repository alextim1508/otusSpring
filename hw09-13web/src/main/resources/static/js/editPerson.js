function editPerson(id){
    var formData = {
        name : $("#inputName" + id).val()
    }
    $.ajax({
        type : "post",
        contentType : "application/json",
        url : "/person/save/" + id,
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            addToLog("Персон обновлен!");
        },
        error : function(e) {
            addToLog(e.responseText);
        }
    });
}