function createPersonsTable(personsData) {
    var personsTableDiv = document.getElementById("personsTableDiv");

    var table = document.createElement("table");
    table.setAttribute("id", "personsTable");
    var thead = createHeadTable();
    table.appendChild(thead);

    var tbody = document.createElement("tbody");
    personsData.forEach(function (person) {
        var tr = createRowByPerson(person);
        tbody.appendChild(tr);
    });
    table.appendChild(tbody);

    personsTableDiv.appendChild(table);
}

function createHeadTable() {
    var thead = document.createElement("thead");

    var tr = document.createElement("tr");
    var thName = document.createElement("th");
    thName.innerHTML = "Name";
    tr.appendChild(thName);
    var thAction = document.createElement("th");
    thAction.innerHTML = "Action";
    tr.appendChild(thAction);
    thead.appendChild(tr);

    return thead;
}

function createRowByPerson(person) {
    var tr = document.createElement("tr");

    var tdName = document.createElement("td");
    var inName = document.createElement("input");
    inName.setAttribute("type", "text");
    inName.setAttribute("id", "inputName" + person["id"]);
    inName.setAttribute("value", person["name"]);
    tdName.appendChild(inName);
    tr.appendChild(tdName);

    var tdButton = document.createElement("td");
    var button = document.createElement("button");
    var textButton = document.createTextNode("Edit");
    button.appendChild(textButton);
    button.onclick = function(){
        editPerson(person["id"]);
    };
    tdButton.appendChild(button);
    tr.appendChild(tdButton);

    return tr;
}

function addPersonToTable(person) {
    var tbody = document.getElementsByTagName("tbody");
    var tr = createRowByPerson(person);
    tbody.item(0).appendChild(tr);
}