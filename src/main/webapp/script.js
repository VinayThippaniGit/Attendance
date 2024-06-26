function logout() {
	if (confirm("Do you want to logout")) {
		location.href = "index.html";
	}
}
function present(presentId){
	alert(presentId);
}
function absent(){
	alert('absent');
}