const form = document.querySelector('form');
form.addEventListener('submit', (event) => {
  event.preventDefault();
  const formData = new FormData(form);
  fetch('/api/login', {
    method: 'POST',
    body: formData
  })
  .then((response) => {
    if(response.ok) {
      // Si la respuesta es exitosa, redirigir al usuario al home
      window.location.href = '#home';
    } else {
      // Si la respuesta indica un error de credenciales, mostrar un mensaje de alerta
      alert('Credenciales incorrectas');
    }
  })
  .catch((error) => {
    // Si ocurre un error durante la petición, mostrar un mensaje de alerta
    alert('Error durante la autenticación');
  });
});