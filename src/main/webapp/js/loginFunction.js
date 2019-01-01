function loginFunction() {
    var login = document.getElementById("login");
	var logout = document.getElementById("logout");
    if (login.style.display === "none") {
        login.style.display = "block";
		logout.style.display = "none";
    } else {
        login.style.display = "none";
		logout.style.display = "block";
    }
}