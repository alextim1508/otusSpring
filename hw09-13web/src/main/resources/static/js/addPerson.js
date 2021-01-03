function addPerson(){
    var formData = {
        name :  $("#inputName").val()
    };
    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/person/insert",
        data : JSON.stringify(formData),
        dataType : "json",
        success : function(result) {
            $("#inputName").innerHTML = "";

            addToLog("Персон добавлен!");
            addPersonToTable(result);
        },
        error : function(e) {
            addToLog(e.responseText);
        }
    });
}