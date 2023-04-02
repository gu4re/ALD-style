console.log('Script loaded correctly');
var form = document.querySelector('#register-form');
console.log('form', form);
var btn = form.querySelector('#create-button');
btn.addEventListener('click', (event) => {
    console.log('click');
    event.preventDefault();
    fetch('/auth/register', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify({
          email: document.getElementById('email').value,
          password: document.getElementById('password').value,
          username: document.getElementById('username').value
      })
    })
    .then((response) => {
      console.log('response', response);
      if(response.ok) {
        alert('Successfully sign up!');
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
        // Si la respuesta indica un error de credenciales, mostrar un mensaje de alerta
        alert('Invalid fields or maybe user already exists.');
      }
    })
    .catch((error) => {
      // Si ocurre un error durante la petición, mostrar un mensaje de alerta
      alert('Unexpected error signing up.');
    });
});