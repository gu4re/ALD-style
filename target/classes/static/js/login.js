alert('Patata');
const form = document.querySelector('#login');
form.addEventListener('#button-login', (event) => {
  alert('Patata');
  event.preventDefault();
  fetch('http://localhost:8080/login', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        user: document.getElementById('user').value,
        password: document.getElementById('password').value
    })
  })
  .then((response) => {
    if(response.ok) {
      // Si la respuesta es exitosa, redirigir al usuario al home
      window.location.href = '#home';
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