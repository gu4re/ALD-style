'use strict';

(function () {
    function init() {
        var router = new Router([
            new Route('home', 'home.html', true),            
            new Route('about', 'about.html'),
            new Route('popularItems','popularItems.html'),
            new Route('newArrivals','newArrivals.html'),
            new Route('allProducts','allProducts.html'),
            new Route('sales','sales.html'),
            new Route('homeSec','homeSec.html'),
            new Route('login','login.html'),
            new Route('cart','cart.html')
        ]);
    }
    init();
}());