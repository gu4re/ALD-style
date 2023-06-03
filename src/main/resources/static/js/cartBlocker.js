var loginButton = document.querySelector("#home-login-button");
var cartButton = document.querySelector('[href="#cart"]');
cartButton.addEventListener("click", (event) => {
  event.preventDefault();
  console.log("login text", loginButton.textContent.trim());
  if (loginButton.textContent.trim() === "Log in") {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Please log in to use this functionality",
    });
    window.location.href = "#login";
    return;
  }
  window.location.href = "#cart";
});
