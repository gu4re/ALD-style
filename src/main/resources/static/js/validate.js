fetch("/auth/validate", {
  method: "POST",
})
  .then((response) => {
    console.log("response", response);
    if (response.ok) {
      Swal.fire(
        "Validation success!",
        "Now, you can log into your account",
        "success",
      );
    } else {
      Swal.fire({
        icon: "error",
        title: "Internal server error!",
        confirmButtonColor: "#0E5FA7",
        text: "Status code: " + response.status,
        footer: '<a href="#404">Contact support for more information.</a>',
      });
    }
  })
  .catch((error) => {
    // if an unexpected error occurs
    Swal.fire({
      icon: "error",
      title: "Internal server error!",
      confirmButtonColor: "#0E5FA7",
      text: "Message: " + error.message,
      footer: '<a href="#404">Contact support for more information.</a>',
    });
  });
