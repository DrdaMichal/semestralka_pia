function hideBtn() {
    var x = document.getElementById("showText");
    var y = document.getElementById("hideBtn")
    if (x.style.display === "none") {
        x.style.display = "block";
        y.style.display = "none";
    } else {
        x.style.display = "none";
        y.style.display = "block";
    }
}