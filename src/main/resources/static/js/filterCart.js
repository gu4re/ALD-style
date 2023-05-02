var filterButton = document.querySelector("#filter");
var priceASCButton = filterButton.querySelector("#lower");
var priceDESCButton = filterButton.querySelector("#higher");
priceASCButton.addEventListener("click", (event) => {
  var script = document.createElement("script");
  script.src = "js/showCart.js?" + Date.now();
  document.head.appendChild(script);
});
priceDESCButton.addEventListener("click", (event) => {
  var script = document.createElement("script");
  script.src = "js/showCart.js?" + Date.now();
  document.head.appendChild(script);
});
