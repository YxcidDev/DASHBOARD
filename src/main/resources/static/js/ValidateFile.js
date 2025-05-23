function validateFile() {
  const fileInput = document.getElementById("image");
  const file = fileInput.files[0];

  if (file) {
    const validImageTypes = ["image/png", "image/jpeg", "image/jpg"];
    if (!validImageTypes.includes(file.type)) {
      alert("Solo se permiten imágenes en formato PNG o JPEG.");
      fileInput.value = "";
    }
  }
}

document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("image").addEventListener("change", validateFile);
});
