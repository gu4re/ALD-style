console.log("Script loaded successfully");
var buttons = document.querySelectorAll('[id^="atc_"]');
var size = "";
var loginButton = document.querySelector("#home-login-button");
buttons.forEach((button) => {
  button.addEventListener("click", () => {
    console.log("login text", loginButton.textContent.trim());
    if (loginButton.textContent.trim() === "Log in") {
      Swal.fire({
        icon: "warning",
        title: "Oops...",
        confirmButtonColor: "#0E5FA7",
        text: "Please log in to use this functionality",
      });
      window.location.href = "#login";
      return;
    }
    var container = button.closest('[id^="adm_"]');
    size = container.querySelector('[id^="ss_"]').value;
    if (size === "Select size") {
      Swal.fire({
        icon: "warning",
        title: "Oops...",
        confirmButtonColor: "#0E5FA7",
        text: "You need to select a size to add it to the cart",
      });
      return;
    }
    fetch("/cart/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: container.querySelector('[id^="name_"]').textContent,
        prize: container
          .querySelector('[id^="p_"]')
          .textContent.replace("$", ""),
        size: size,
      }),
    })
      .then((response) => {
        console.log("response", response);
        if (response.ok) {
          Swal.fire({
            title: "Added to cart!",
            text: "Close and keep buying or visit your cart",
            icon: "success",
            confirmButtonColor: "#0E5FA7",
          });
          var form = document.querySelector("#cart-button-form");
          var counter = parseInt(
            form.querySelector("#cart-counter").textContent
          );
          var newValue = counter + 1;
          form.querySelector("#cart-counter").textContent = newValue;
          console.log(
            "newCartCounter",
            form.querySelector("#cart-counter").textContent
          );
        } else if (response.status === 400) {
          // If the response indicates a credential error, display an alert message
          Swal.fire({
            icon: "warning",
            title: "Oops...",
            confirmButtonColor: "#0E5FA7",
            text: "Out of stock. Maximum of 3 units exceeded",
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
});
