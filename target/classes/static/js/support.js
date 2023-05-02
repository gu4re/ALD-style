function format(cmd, value) {
    document.execCommand(cmd, false, value);
}

function saveContent() {
    const inputContent = document.querySelector('#content');
    const editor = document.querySelector('#editor');
    inputContent.value = editor.innerHTML;
}

var sendButton = document.querySelector("#send-button");
sendButton.addEventListener("click", (event) => {
  Swal.fire({
    title: "Send success!",
    text: "Thanks a lot! We will review your support submit",
    icon: "success",
    confirmButtonColor: "#0E5FA7",
  });
  window.location.href = "#home";
});