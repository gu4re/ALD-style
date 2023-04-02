console.log('Script loaded correctly.');
var form = document.querySelector('#login-form');
console.log('form', form);
var btn = form.querySelector('#login-button');
var email = '';
btn.addEventListener('click', (event) => {
    email = document.getElementById('email').value;
    console.log('click');
    event.preventDefault();
    fetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        })
    })
    .then((response) => {
        console.log('response', response);
        if(response.ok) {
            // Si la respuesta es exitosa, redirigir al usuario al home
            window.location.href = '#home';
            var loginButton = document.querySelector('[href="#login"]');
            loginButton.innerHTML = 
                    `<div class="dropdown">
                      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-fill"></i> <span class="text-about">${email}</span>
                      </a>
                      <ul class="dropdown-menu my-2" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#allProducts">Profile</a></li>
                        <hr class="dropdown-divider">
                        <li><a class="dropdown-item" href="#allProducts">Sign Up</a></li>
                      </ul>
                    </div>`;
            loginButton.removeAttribute('href');           
        } else {
            // Si la respuesta indica un error de credenciales, mostrar un mensaje de alerta
            alert('Wrong credentials.');
        }
    })
    .catch((error) => {
        // Si ocurre un error durante la petición, mostrar un mensaje de alerta
        alert('Unexpected error during authentication.');
    });
});