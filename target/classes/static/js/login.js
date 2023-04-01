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
        console.log('email', email);
        console.log('response', response);
        if(response.ok) {
            // If the response is OK then go back to home with their session
            window.location.href = '#home';
            var loginButton = document.querySelector('[href="#login"]');
            loginButton.innerHTML = `<i class="bi bi-person-fill"></i>
                ${email}
                <span class="badge bg-dark text-white ms-1 rounded-pill"></span>
                <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <li><a class="dropdown-item" href="#404">Profile</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="/">Sign out</a></li>
                </ul>`;
            loginButton.removeAttribute('href');
        } else {
            // If the response indicates a credential error, display an alert message
            alert('Wrong credentials.');
        }
    })
    .catch((error) => {
        // If an error occurs during the request, display an alert message
        alert('Unexpected error during authentication.');
    });
});