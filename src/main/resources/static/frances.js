
function flipCard(card) {
  const original = card.dataset.original.trim();
  const translation = card.dataset.translation.trim();
  card.textContent = (card.textContent.trim() === original) ? translation : original;
}

const questions = [
  { text: "Comment dit-on 'Bonjour' en espagnol?", options: ["Hola", "Buenas noches", "Adiós", "Gracias"], correct: 0 },
  { text: "Comment dit-on 'Merci' en espagnol?", options: ["Perro", "Buenos días", "Adiós", "Gracias"], correct: 3 }
];

function loadFrenchTest() {
  for (let i = 0; i < 2; i++) {
    document.getElementById(`question${i+1}`).textContent = questions[i].text;
    let optionsContainer = document.getElementById(`options${i+1}`);
    optionsContainer.innerHTML = "";

    questions[i].options.forEach((option, index) => {
      let button = document.createElement("button");
      button.textContent = option;
      button.onclick = function() { checkAnswer(this, index === questions[i].correct); };
      optionsContainer.appendChild(button);
    });
  }
}

function checkAnswer(button, isCorrect) {
  const message = document.getElementById("result-message");
  message.textContent = isCorrect ? "¡Correcto!" : "Incorrecto, intenta de nuevo.";
  message.style.color = isCorrect ? "green" : "red";
}

document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll('.flashcard').forEach(card => {
    card.addEventListener('click', () => flipCard(card));
  });
  loadFrenchTest();
});

document.getElementById('btn-logout').addEventListener('click', cerrarSesion);
function cerrarSesion() {
  fetch('/api/users/me/session', {
    method: 'DELETE',
    credentials: 'include'
  })
    .then(response => {
      if (response.ok) {
        window.location.href = 'login.html';
      } else {
        throw new Error('No se pudo cerrar sesión');
      }
    })
    .catch(error => {
      alert('Error al cerrar sesión: ' + error.message);
    });
}


