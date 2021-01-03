function addToLog(msg) {
    var log = document.getElementById("log");
    log.textContent = msg + "\n" + log.textContent;
}