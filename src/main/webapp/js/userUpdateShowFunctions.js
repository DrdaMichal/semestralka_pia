function showUserInfoUpdateForm() {
    var a = document.getElementById("showUserInfoUpdateForm");
    var b = document.getElementById("showPasswordUpdateForm");

    if (a.style.display === "none") {
        a.style.display = "block";
        b.style.display = "none";
    }
    else {
        a.style.display = "none";
        b.style.display = "none";
    }
}

function showPasswordUpdateForm() {
    var a = document.getElementById("showUserInfoUpdateForm");
    var b = document.getElementById("showPasswordUpdateForm");

    if (b.style.display === "none") {
        a.style.display = "none";
        b.style.display = "block";
    }
    else {
        a.style.display = "none";
        b.style.display = "none";
    }
}