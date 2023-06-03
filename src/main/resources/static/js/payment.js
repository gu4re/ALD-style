console.log("Script loaded correctly.");
var regex_numberCard = /^\d{4}\s\d{4}\s\d{4}\s\d{4}$/;
var regex_cvv = /^\d{3}$/;
var form = document.querySelector("#creditCard-form");
console.log("form", form);
var btn = form.querySelector("#credit-button");
var card_number = "";
var card_name = "";
var cvv = "";
var day = "";
var month = "";
var year = "";
btn.addEventListener("click", (event) => {
  card_number = document.getElementById("number").value;
  card_name = document.getElementById("name").value;
  cvv = document.getElementById("cvv").value;
  day = document.getElementById("day").value;
  month = document.getElementById("month").value;
  year = document.getElementById("year").value;
  if (!regex_numberCard.test(card_number)) {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Invalid card number format or maybe\nshould have exactly 16 digits",
      confirmButtonColor: "#0E5FA7",
    });
    return;
  }
  if (card_name.length > 25) {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Card Name should have less than 25 characters",
      confirmButtonColor: "#0E5FA7",
    });
    return;
  }
  if (day === "Day" || month === "Month" || year === "Year") {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Select a valid expiration date",
      confirmButtonColor: "#0E5FA7",
    });
    return;
  }
  if (!regex_cvv.test(cvv)) {
    Swal.fire({
      icon: "warning",
      title: "Oops...",
      text: "Invalid CVV field format or maybe CVV field should have exactly 3 digit",
      confirmButtonColor: "#0E5FA7",
    });
    return;
  }
  console.log("click");
  event.preventDefault();
  Swal.fire({
    title: "Purchase success!",
    text: "Thanks a lot! Close this window to keep visiting our page.",
    icon: "success",
    confirmButtonColor: "#0E5FA7",
  });
  fetch("/cart/clear", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      user: document.getElementById("email-user").innerText
    }),
  }).then((response) => {
    if (response.ok) {
      window.location.href = "#home";
      var form = document.querySelector("#cart-button-form");
      form.querySelector("#cart-counter").textContent = "0";
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
  window.location.href = "#home";
});
