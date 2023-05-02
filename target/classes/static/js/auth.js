console.log("Script loaded correctly.");
var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
var form = document.querySelector("#login-form");
console.log("form", form);
var btn = form.querySelector("#login-button");
var email = "";
btn.addEventListener("click", (event) => {
  email = document.getElementById("email").value;
  if (!regex.test(email)) {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Invalid email format",
      confirmButtonColor: "#0E5FA7",
    });
    return;
  }
  console.log("click");
  event.preventDefault();
  fetch("/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: document.getElementById("email").value,
      password: document.getElementById("password").value,
    }),
  })
    .then((response) => {
      console.log("response", response);
      if (response.status === 200) {
        Swal.fire({
          title: "Login success!",
          text: "Close this window to follow your session.",
          icon: "success",
          confirmButtonColor: "#0E5FA7",
        });
        // If the response is OK then go back to home with their session
        window.location.href = "#home";
        var loginButton = document.querySelector('[href="#login"]');
        loginButton.innerHTML = `<div class="dropdown">
                      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-fill"></i> <span class="text-about">${email}</span>
                      </a>
                      <ul class="dropdown-menu my-2" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" id="orders">My orders</a></li>
                        <hr class="dropdown-divider">
                        <li><a class="dropdown-item" id="sign-out">Sign out</a></li>
                        <hr class="dropdown-divider">
                        <li><a class="dropdown-item" id="delete-account" style="color:red">Delete account</a></li>    
                      </ul>
                    </div>`;
        loginButton.removeAttribute("href");
        var signOutButton = loginButton.querySelector("#sign-out");
        var deleteButton = loginButton.querySelector("#delete-account");
        signOutButton.addEventListener("click", (event) => {
          Swal.fire({
            title: "Confirm Logout",
            text: "Are you sure you want to logout?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0E5FA7",
            cancelButtonColor: "#A5A79F",
            confirmButtonText: "Yes, Logout",
          }).then((result) => {
            if (result.isConfirmed) {
              window.location.href = "/";
            }
          });
        });
        deleteButton.addEventListener("click", (event) => {
          Swal.fire({
            title: "Confirm Delete account",
            text: "Are you sure you want to delete your account? This action cannot be undo",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0E5FA7",
            cancelButtonColor: "#A5A79F",
            confirmButtonText: "Yes, Remove account",
          }).then((result) => {
            if (result.isConfirmed) {
              fetch("/auth/delete", {
                method: "DELETE",
                headers: {
                  "Content-Type": "application/json",
                },
                body: JSON.stringify({
                  email: email,
                }),
              }).then((response) => {
                if (response.ok) {
                  window.location.href = "/";
                  Swal.fire({
                    title: "Delete success!",
                    text: "Your account has been removed",
                    icon: "success",
                    confirmButtonColor: "#0E5FA7",
                  });
                } else {
                  Swal.fire({
                    icon: "error",
                    title: "Internal server error!",
                    text: "Status code: " + response.status,
                    confirmButtonColor: "#0E5FA7",
                    footer:
                      '<a href="#404">Contact support for more information.</a>',
                  });
                }
              });
            }
          });
        });
      } else if (response.status === 400) {
        // If the response indicates a credential error, display an alert message
        Swal.fire({
          icon: "warning",
          title: "Oops...",
          text: "Wrong credentials",
          confirmButtonColor: "#0E5FA7",
        });
      } else if (response.status === 204) {
        window.location.href = "#admin";
        Swal.fire({
          icon: "success",
          title: "Welcome Admin!",
          text: "Feel free to use the panel",
          confirmButtonColor: "#0E5FA7",
        });
      } else {
        Swal.fire({
          icon: "error",
          title: "Internal server error!",
          text: "Status code: " + response.status,
          confirmButtonColor: "#0E5FA7",
          footer: '<a href="#404">Contact support for more information.</a>',
        });
      }
    })
    .catch((error) => {
      // if an unexpected error occurs
      Swal.fire({
        icon: "error",
        title: "Internal server error!",
        text: "Message: " + error.message,
        confirmButtonColor: "#0E5FA7",
        footer: '<a href="#404">Contact support for more information.</a>',
      });
    });
});
