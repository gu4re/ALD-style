'use strict';

function Router(routes) {
    try {
        if (!routes) {
            throw 'error: routes param is mandatory';
        }
        this.constructor(routes);
        this.init();
    } catch (e) {
        console.error(e);   
    }
}

Router.prototype = {
    routes: undefined,
    rootElem: undefined,
    constructor: function (routes) {
        this.routes = routes;
        this.rootElem = document.getElementById('main');
    },
    init: function () {
        var r = this.routes;
        (function(scope, r) { 
            window.addEventListener('hashchange', function (e) {
                scope.hasChanged(scope, r);
            });
        })(this, r);
        this.hasChanged(this, r);
    },
    hasChanged: function(scope, r){
        var routeFound2 = false;
        var routeFound3 = false;
        if (window.location.hash.length > 0) {
            for (var i = 0, length = r.length; i < length; i++) {
                var route = r[i];
                if(route.isActiveRoute(window.location.hash.substr(1))) {
                    if (route.htmlName === 'login.html') {
                      var script = document.createElement('script');
                      script.src = 'js/login.js?' + Date.now();
                      document.head.appendChild(script);
                    }
                    else if (route.htmlName === 'register.html') {
                      var script = document.createElement('script');
                      script.src = 'js/register.js?' + Date.now();
                      document.head.appendChild(script);
                    }
                    else if (route.htmlName === 'forgotPasswd.html'){
                      var script = document.createElement('script');
                      script.src = 'js/forgot.js?' + Date.now();
                      document.head.appendChild(script);
                    }
                    else if (route.htmlName === 'resetPasswd.html'){
                      var script = document.createElement('script');
                      script.src = 'js/reset.js?' + Date.now();
                      document.head.appendChild(script);
                    }
                    else if (route.htmlName === 'creditCard.html'){
                      var script = document.createElement('script');
                      script.src = 'js/payment.js?' + Date.now();
                      document.head.appendChild(script);
                    }
                    else if (route.name === 'validate') {
                        var script = document.createElement('script');
                        script.src = 'js/validate.js?' + Date.now();
                        document.head.appendChild(script);
                    }
                    else if ((route.htmlName === 'home.html') || (route.htmlName === 'popularItems.html')
                    || (route.htmlName === 'newArrivals.html') || (route.htmlName === 'allProducts.html')
                    || (route.htmlName === 'sales.html')){
                        setTimeout(function() {
                          var script = document.createElement('script');
                          script.src = 'js/addToCart.js?' + Date.now();
                          document.head.appendChild(script);
                        }, 1500);
                    }
                    else if (route.htmlName === 'cart.html') {
                        var script = document.createElement('script');
                        script.src = 'js/showCart.js?' + Date.now();
                        document.head.appendChild(script);
                    }
                    routeFound2 = true;
                    routeFound3 = true;
                    scope.goToRoute(route.htmlName, false);
                    break;
                }
            }
            routeFound3 = true;
            scope.goToRoute('404.html', routeFound2);
        } else {
            for (var i = 0, length = r.length; i < length; i++) {
                var route = r[i];
                if(route.default) {
                    setTimeout(function() {
                      var script = document.createElement('script');
                      script.src = 'js/addToCart.js?' + Date.now();
                      document.head.appendChild(script);
                    }, 1500);
                    scope.goToRoute(route.htmlName, routeFound3);
                }
            }
        }
    },
    goToRoute: function (htmlName, isRequestSent) {
        if (isRequestSent) {
            return;
        }
        (function(scope) {
            var url = 'html/' + htmlName,
                xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    scope.rootElem.innerHTML = this.responseText;
                }
            };
            xhttp.open('GET', url, true);
            xhttp.send();
        })(this);
    }
};