"use strict";

(function () {
  function init() {
    var router = new Router([
      new Route("home", "home.html", true),
      new Route("about", "about.html"),
      new Route("popularItems", "popularItems.html"),
      new Route("newArrivals", "newArrivals.html"),
      new Route("allProducts", "allProducts.html"),
      new Route("sales", "sales.html"),
      new Route("login", "login.html"),
      new Route("cart", "cart.html"),
      new Route("register", "register.html"),
      new Route("404", "404.html"),
      new Route("creditCard", "creditCard.html"),
      new Route("forgotPasswd", "forgotPasswd.html"),
      new Route("resetPasswd", "resetPasswd.html"),
      new Route("validate", "login.html"),
      new Route("returns", "returns.html"),
      new Route("shipping", "shipping.html"),
      new Route("admin", "admin.html"),
      new Route("orderStatus", "orderStatus.html"),
      new Route("contact", "contact.html"),
      new Route("orders", "orders.html"),
    ]);
  }
  init();
})();
