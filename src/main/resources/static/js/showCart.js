var mode = document.getElementById("filter").value;
fetch("/cart/show", {
    method: "POST",
    headers: {
          "Content-Type": "application/json",
    },
    body: JSON.stringify({
          mode: mode,
    }),
})
  .then((response) => {
    if (response.ok) {
      return response.json();
    } else {
      Swal.fire({
        icon: "error",
        title: "Internal server error!",
        text: "Message: " + error.message,
        footer: '<a href="#support">Contact support for more information.</a>',
      });
    }
  })
  .then((data) => {
    console.log("data", data);
    if (!Array.isArray(data)) {
      // In case the response was ok but the body of the response was not a JSONArray
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: "A problem has occurred during reading your cart. Try it later!",
      });
      return;
    }
    // Reading the 'data' variable that contains the database and inject HTML
    console.log("JSONArray read");
    var container = document.querySelector("#carrito");
    let totalPrice = 0;
    // Table header
    let table = `
            <table>
              <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Size</th>
                <th>Quantity</th>
              </tr>
        `;
    // Generating rows
    data.forEach((item) => {
      let { quantity, size, price, name } = item;
      totalPrice += quantity * price;
      table += `<tr>
                  <td>${name}</td>
                  <td>$${price}</td>
                  <td>${size}</td>
                  <td>${quantity}</td>
                  </tr>`;
    });
    // Table footer
    table += `
            </table>
            <div class="total">
                <p>Total: $${totalPrice}</p>
              </div>
            <button id="check_shopping" type="submit" class="comprar">
                <a href="#creditCard" style="color: white; text-decoration: none">Confirm Purchase</a>
            </button>`;
    container.innerHTML = table;
    console.log("created table", table);
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
