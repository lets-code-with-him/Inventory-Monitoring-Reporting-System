function registerUser() {

    const username = document.getElementById("regUsername").value;
    const email = document.getElementById("regEmail").value;
    const password = document.getElementById("regPassword").value;
    const role = document.getElementById("regRole").value;

    if(!username || !email || !password || !role){
        document.getElementById("regError").innerText = "All fields are required!";
        return;
    }

    fetch("/api/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            email: email,
            password: password,
            role: role
        })
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        window.location.href = "/login";
    })
    .catch(error => {
        document.getElementById("regError").innerText = "Registration failed";
    });
}