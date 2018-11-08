function loginFunction() {
    var login = document.getElementById("login");
	var logout = document.getElementById("logout");
	var register = document.getElementById("register");
    if (login.style.display === "none") {
        login.style.display = "block";
		logout.style.display = "none";
		//register.style.display = "block";
    } else {
        login.style.display = "none";
		logout.style.display = "block";
		//register.style.display = "none";
    }
}