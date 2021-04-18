var authorsById = new Map();
var genresById = new Map();

$(document).ready(function () {

    document.getElementById("getDataBtn").onclick = function() {

        getBooks(function(books) {
            createBooksTable(books);
        });
    };

    getAuthors(function(authors) {
    authors.forEach(function (author) {
            authorsById.set(author["id"], author);
        });
    });

    getGenres(function(genres) {
        genres.forEach(function (genre) {
            genresById.set(genre["id"], genre);
        });
    });

    createAddBookForm();



    createTabs();
});

function createTabs() {
    var registrationTabsDiv = document.getElementById("registrationTabsDiv");

    var regBtn = document.createElement("button");
    regBtn.setAttribute("class", "tablinks");
    regBtn.setAttribute("onclick", "openTab(event, 'reg')");
    regBtn.innerHTML = "Регистрация";

    var loginBtn = document.createElement("button");
    loginBtn.setAttribute("class", "tablinks");
    loginBtn.setAttribute("onclick", "openTab(event, 'login')");
    loginBtn.innerHTML = "Вход";

    var tabs = document.createElement("div");
    tabs.appendChild(loginBtn);
    tabs.appendChild(regBtn);
    registrationTabsDiv.appendChild(tabs);


    var headerTab = document.createElement("h3");
    headerTab.innerHTML = "Регистрация";

    var regTab = document.createElement("div");
    regTab.setAttribute("id", "reg");
    regTab.setAttribute("class", "tabcontent");
    regTab.appendChild(headerTab);
    regTab.appendChild(createRegistrationForm());
    registrationTabsDiv.appendChild(regTab);

    headerTab = document.createElement("h3");
    headerTab.innerHTML = "Вход";
    var loginTab = document.createElement("div");
    loginTab.setAttribute("id", "login");
    loginTab.setAttribute("class", "tabcontent");
    loginTab.appendChild(headerTab);
    loginTab.appendChild(createLoginForm());
    registrationTabsDiv.appendChild(loginTab);

    openTab(event, 'login');
}

function openTab(evt, tabId) {
    var i, tabcontent, tablinks;

    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    document.getElementById(tabId).style.display = "block";
    evt.currentTarget.className += " active";
}

function createRegistrationForm() {
    var form = document.createElement("div");

    var title = document.createElement("b");
    title.innerHTML = "Username:";
    form.appendChild(title);
    var inUsername = document.createElement("input");
    inUsername.setAttribute("id", "usernameId");
    form.appendChild(inUsername);
    form.appendChild(document.createElement("br"));

    title = document.createElement("b");
    title.innerHTML = "Name:";
    form.appendChild(title);
    var inName = document.createElement("input");
    inName.setAttribute("id", "nameId");
    form.appendChild(inName);
    form.appendChild(document.createElement("br"));

    title = document.createElement("b");
    title.innerHTML = "Surname:";
    form.appendChild(title);
    var inSurname = document.createElement("input");
    inSurname.setAttribute("id", "surnameId");
    form.appendChild(inSurname);
    form.appendChild(document.createElement("br"));

    title = document.createElement("b");
    title.innerHTML = "Email:";
    form.appendChild(title);
    var inEmail = document.createElement("input");
    inEmail.setAttribute("id", "emailId");
    form.appendChild(inEmail);
    form.appendChild(document.createElement("br"));

    title = document.createElement("b");
    title.innerHTML = "Phone:";
    form.appendChild(title);
    var inPhone = document.createElement("input");
    inPhone.setAttribute("id", "phoneId");
    form.appendChild(inPhone);
    form.appendChild(document.createElement("br"));

    title = document.createElement("b");
    title.innerHTML = "Password:";
    form.appendChild(title);
    var inPass = document.createElement("input");
    inPass.setAttribute("id", "passId");
    form.appendChild(inPass);
    form.appendChild(document.createElement("br"));

     var button = document.createElement("button");
     button.innerHTML = "Регистрировать";
     button.onclick = function() {
        addUser();
     };
     form.appendChild(button);

    return form;
}

function createLoginForm() {
    var form = document.createElement("div");

    var title = document.createElement("b");
    title.innerHTML = "Username:";
    form.appendChild(title);
    var inUsername = document.createElement("input");
    inUsername.setAttribute("id", "loginUsernameId");
    form.appendChild(inUsername);
    form.appendChild(document.createElement("br"));

    title = document.createElement("b");
    title.innerHTML = "Password:";
    form.appendChild(title);
    var inPass = document.createElement("input");
    inPass.setAttribute("id", "loginPassId");
    form.appendChild(inPass);
    form.appendChild(document.createElement("br"));

    var button = document.createElement("button");
    button.innerHTML = "Логин1";
    button.onclick = function() {
      login1();
    };
    form.appendChild(button);
    form.appendChild(document.createElement("br"));

    title = document.createElement("b");
    title.innerHTML = "Sms:";
    form.appendChild(title);
    var inSms = document.createElement("input");
    inSms.setAttribute("id", "smsId");
    form.appendChild(inSms);
    form.appendChild(document.createElement("br"));

    var button2 = document.createElement("button");
    button2.innerHTML = "Логин2";
    button2.onclick = function() {
        login2();
    };
    form.appendChild(button2);
    form.appendChild(document.createElement("br"));

    var button3 = document.createElement("button");
    button3.innerHTML = "Логаут";
    button3.onclick = function() {
        logout();

        var table = document.getElementById("booksTableDiv");
        while (table.lastElementChild) {
            table.removeChild(table.lastElementChild);
        }
    };
    form.appendChild(button3);
    form.appendChild(document.createElement("br"));

    return form;
}

function createBooksTable(books) {
    var booksTableDiv = document.getElementById("booksTableDiv");

    var table = document.createElement("table");
    table.appendChild(createHeadTable());

    var tbody = document.createElement("tbody");
    tbody.setAttribute("id", "bodyTable");
    books.forEach(function (book) {
        tbody.appendChild(createBookRow(book, book["authorId"], book["genreId"]));
    });
    table.appendChild(tbody);

    booksTableDiv.appendChild(table);
}

function createHeadTable() {
    var thead = document.createElement("thead");

    var tr = document.createElement("tr");

    var thBook = document.createElement("th");
    thBook.innerHTML = "Book";
    tr.appendChild(thBook);

    var thAuthor = document.createElement("th");
    thAuthor.innerHTML = "Author";
    tr.appendChild(thAuthor);

    var thGenre = document.createElement("th");
    thGenre.innerHTML = "Genre";
    tr.appendChild(thGenre);

    thead.appendChild(tr);
    return thead;
}

function createBookRow(book, authorId, genreId) {
    var tr = document.createElement("tr");

    var inBook = document.createElement("input");
    inBook.setAttribute("type", "text");
    inBook.setAttribute("value", book["title"]);
    var tdBook = document.createElement("td");
    tdBook.appendChild(inBook);
    tr.appendChild(tdBook);

    var author = authorsById.get(authorId);
    var inAuthor = document.createElement("input");
    inAuthor.setAttribute("type", "text");
    inAuthor.setAttribute("value", author["firstname"] + " " + author["lastname"]);

    var tdAuthor = document.createElement("td");
    tdAuthor.appendChild(inAuthor);
    tr.appendChild(tdAuthor);

    var genre = genresById.get(genreId);
    var inGenre = document.createElement("input");
    inGenre.setAttribute("type", "text");
    inGenre.setAttribute("value", genre["title"]);

    var tdGenre = document.createElement("td");
    tdGenre.appendChild(inGenre);
    tr.appendChild(tdGenre);

    return tr;
}

function addBookToTable(book) {
    var tbody = document.getElementsByTagName("tbody");
    var tr = createBookRow(book, book["authorId"], book["genreId"]);
    tbody.item(0).appendChild(tr);
}

function addRowToTable(book, author, genre) {
    var tbody = document.getElementById("bodyTable");
    tbody.appendChild(createBookRow(book, author, genre));
}


function createAddBookForm() {
    createTitleBook();
    createAuthorComboBox();
    createGenreComboBox();
    createAddBookBtn();
}

function createTitleBook() {
    var addBookForm = document.getElementById("addBookForm");

    var label = document.createElement("label");
    label.innerHTML = "Название";
    addBookForm.appendChild(label);



    var inTitle = document.createElement("input");
    inTitle.setAttribute("id", "createdTitleBookId");
    addBookForm.appendChild(inTitle);
}

function createAuthorComboBox() {
    var addBookForm = document.getElementById("addBookForm");

    var label = document.createElement("label");
    label.innerHTML = "Автор";
    addBookForm.appendChild(label);

    var span = document.createElement("span");

    var selectTag = document.createElement("select");
    selectTag.setAttribute("id", "createdAuthorBookId");

    authorsById.forEach(function (author) {
        var option = document.createElement("option");
        option.value = author["id"];
        option.innerHTML = author["firstname"]  + " " + author["lastname"];
        selectTag.appendChild(option);
    });
    span.appendChild(selectTag);

    addBookForm.appendChild(span);
}

function createGenreComboBox() {
    var addBookForm = document.getElementById("addBookForm");

    var label = document.createElement("label");
    label.innerHTML = "Жанр";
    addBookForm.appendChild(label);

    var span = document.createElement("span");

    var selectTag = document.createElement("select");
    selectTag.setAttribute("id", "createdGenreBookId");

    genresById.forEach(function (genre) {
        var option = document.createElement("option");
        option.value = genre["id"];
        option.innerHTML = genre["title"];
        selectTag.appendChild(option);
    });
    span.appendChild(selectTag);

    addBookForm.appendChild(span);
}

function createAddBookBtn() {
    var inputAuthorId = document.getElementById("addBookForm");

    var button = document.createElement("button");
    button.innerHTML = "Добавить";
    button.onclick = function() {
        addBook();
    };
    inputAuthorId.appendChild(button);
}