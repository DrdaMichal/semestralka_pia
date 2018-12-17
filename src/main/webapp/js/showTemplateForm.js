function showTplForm() {
    var x = document.getElementById("istemplate");
    var y = document.getElementById("showTemplateForm");
    if (x.checked) {
        y.style.display = "block";
    }
    if (!(x.checked)) {
        y.style.display = "none"
    }
}