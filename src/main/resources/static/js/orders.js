console.log('Script loaded successfully')
fetch("/order", {
    headers: {
          "Content-Type": "application/json",
    },
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
    var container = document.querySelector("#table-orders");
    let totalPrice = 0;
    // Table header
    let table = ''
    // Generating rows
    data.forEach((item) => {
      let {date, id, description} = item;
      let single_table = `
        <h5 style="text-align: left;">
          <span style="margin-left: 125px">ID: ${id}</span>
          <span style="margin-left: 865px">${date}</span>
        </h5>
        <table>
          <tr>
            <th>Product</th>
            <th>Price</th>
            <th>Size</th>
            <th>Quantity</th>
          </tr>
      `;
      let totalPrice = 0;
      // Generating rows from the 'description' array
      JSON.parse(description).forEach((item) => {
        console.log("item leido de description", item);
        let { quantity, size, price, name } = item;
        single_table += `<tr>
          <td>${name}</td>
          <td>$${price}</td>
          <td>${size}</td>
          <td>${quantity}</td>
        </tr>`;
        totalPrice += price * quantity;
      });
      single_table += `</table><br>`;
      table += single_table;
    });
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