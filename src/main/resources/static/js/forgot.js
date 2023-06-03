console.log("Script loaded correctly.");
var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
var form = document.querySelector("#forgotPasswd-form");
console.log("form", form);
var btn = form.querySelector("#forgotPasswd-button");
var email = "";
btn.addEventListener("click", (event) => {
  email = document.getElementById("email").value;
  if (!regex.test(email)) {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      confirmButtonColor: "#0E5FA7",
      text: "Invalid email format",
    });
    return;
  }
  console.log("click");
  event.preventDefault();
  fetch("/auth/forgot", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: document.getElementById("email").value,
    }),
  })
    .then((response) => {
      console.log("response", response);
      if (response.ok) {
        Swal.fire(
          "Send success!",
          confirmButtonColor: "#0E5FA7",
          "Check your inbox and spam box of your mail and follow the steps to recover your password.",
          "success"
        );
        // If the response is OK then go back to home with their session
        window.location.href = "#login";
      } else if (response.status === 400) {
        // If the response indicates a credential error, display an alert message
        Swal.fire({
          icon: "warning",
          title: "Oops...",
          confirmButtonColor: "#0E5FA7",
          text: "A problem has occurred during sending the mail. Try again later!",
        });
      } else {
        Swal.fire({
          icon: "error",
          title: "Internal server error!",
          text: "Status code: " + response.status,
          confirmButtonColor: "#0E5FA7",
          footer: '<a href="#support">Contact support for more information.</a>',
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
        footer: '<a href="#support">Contact support for more information.</a>',
      });
    });
});
