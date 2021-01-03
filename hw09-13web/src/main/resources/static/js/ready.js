$(document).ready(function () {
    $("#addPerson").submit(function (event) {
        event.preventDefault();
        addPerson();
    });

    console.log("ready");
    getPersons();
});