var authorsById = new Map();
var genresById = new Map();

$(document).ready(function () {
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

    console.log("ready");
    getBooks(function(books) {
        createBooksTable(books);
    });

    createAuthorComboBox();
    createGenreComboBox();

     $("#addBook").submit(function (event) {
        event.preventDefault();
        addBook();
     });
});

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


function createAuthorComboBox() {
    var inputAuthorId = document.getElementById("inputAuthorId");
    var selectTag = document.createElement("select");
    selectTag.setAttribute("id", "createdAuthorBookId");

    authorsById.forEach(function (author) {
        var option = document.createElement("option");
        option.value = author["id"];
        option.innerHTML = author["firstname"]  + " " + author["lastname"];
        selectTag.appendChild(option);
    });

    inputAuthorId.appendChild(selectTag);
}

function createGenreComboBox() {
    var inputGenreId = document.getElementById("inputGenreId");
    var selectTag = document.createElement("select");
    selectTag.setAttribute("id", "createdGenreBookId");
    genresById.forEach(function (genre) {
        var option = document.createElement("option");
        option.value = genre["id"];
        option.innerHTML = genre["title"];
        selectTag.appendChild(option);
    });

    inputGenreId.appendChild(selectTag);
}