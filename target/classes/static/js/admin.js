var form_users = document.querySelector("#admin-users");
var form_models = document.querySelector("#admin-models");
// Add users from admin
var confirm_add_user = document.querySelector("#admin-button-adduser");
var email = "";
var password = "";
var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
confirm_add_user.addEventListener("click", (event) => {
  event.preventDefault();
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
  password = document.getElementById("addpassword").value;
  if (password.length < 8) {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Password must have at least 8 characters",
      confirmButtonColor: "#0E5FA7",
    });
    return;
  }
  confirmPassword = document.getElementById("addpasswordcheck").value;
  if (password !== confirmPassword) {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Password fields must be the same",
      confirmButtonColor: "#0E5FA7",
    });
    return;
  }
  fetch("/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: email,
      password: password,
      username: document.getElementById("username").value,
    }),
  }).then((response) => {
    console.log("response", response);
    if (response.ok) {
      fetch("/auth/validate", {
        method: "POST",
      })
        .then((response) => {
          console.log("response", response);
          if (response.ok) {
            Swal.fire({
              title: "Register success!",
              text: `The account with email ${email} has been created`,
              icon: "success",
              confirmButtonColor: "#0E5FA7",
            });
          } else {
            Swal.fire({
              icon: "error",
              title: "Internal server error!",
              text: "Status code: " + response.status,
              footer:
                '<a href="#404">Contact support for more information.</a>',
            });
          }
        })
        .catch((error) => {
          // if an unexpected error occurs
          Swal.fire({
            icon: "error",
            title: "Internal server error!",
            text: "Message: " + error.message,
            footer: '<a href="#404">Contact support for more information.</a>',
          });
        });
    } else if (response.status === 400) {
      // If the response indicates a credential error, display an alert message
      Swal.fire({
        icon: "warning",
        title: "Oops...",
        text: "Unable to create the user. This user is already registered",
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
  });
});
// Delete users from admin
var confirm_delete_user = document.querySelector("#admin-button-deleteuser");
confirm_delete_user.addEventListener("click", (event) => {
  event.preventDefault();
  var email = form_users.querySelector("#deletemail").value;
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
      Swal.fire({
        title: "Delete success!",
        text: `The account with email ${email} has been deleted`,
        icon: "success",
        confirmButtonColor: "#0E5FA7",
      });
    } else if (response.status === 400) {
      Swal.fire({
        title: "Oops...",
        text: `The account with email ${email} has not been created`,
        icon: "warning",
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
  });
});
