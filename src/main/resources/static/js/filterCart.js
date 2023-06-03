var filterButton = document.querySelector("#filter");
filterButton.addEventListener("change", (event) => {
  var script = document.createElement("script");
  script.src = "js/showCart.js?" + Date.now();
  document.head.appendChild(script);
});

