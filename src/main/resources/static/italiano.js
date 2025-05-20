
function flipCard(card) {
  const original = card.dataset.original.trim();
  const translation = card.dataset.translation.trim();
  card.textContent = (card.textContent.trim() === original) ? translation : original;
}

const questions = [
  { text: "Come si dice 'Hola' in italiano?", options: ["Grazie", "Addio", "Ciao", "Gatto"], correct: 2 },
  { text: "Come si dice 'Gracias' in italiano?", options: ["Per favore", "Grazie", "Cane", "Ciao"], correct: 1 }
];

function loadItalianTest() {
  for (let i = 0; i < 2; i++) {
    document.getElementById(`question${i+1}`).textContent = questions[i].text;
    const optionsContainer = document.getElementById(`options${i+1}`);
    optionsContainer.innerHTML = "";

    questions[i].options.forEach((option, index) => {
      const button = document.createElement("button");
      button.textContent = option;
      button.onclick = () => checkAnswer(button, index === questions[i].correct);
      optionsContainer.appendChild(button);
    });
  }
}

function checkAnswer(button, isCorrect) {
  const message = document.getElementById("result-message");
  message.textContent = isCorrect ? "¡Correcto!" : "Incorrecto, intenta de nuevo.";
  message.style.color = isCorrect ? "green" : "red";
}

function flipCard(card) {
  const original = card.dataset.original.trim();
  const translation = card.dataset.translation.trim();
  card.textContent = (card.textContent.trim() === original) ? translation : original;
}

document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll('.flashcard').forEach(card => {
    card.addEventListener('click', () => flipCard(card));
  });
  loadItalianTest();
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

document.addEventListener('DOMContentLoaded', () => {
  const btnBaja = document.getElementById('btn-baja');
  if (btnBaja) {
    btnBaja.addEventListener('click', darseDeBaja);
  }
});

function darseDeBaja() {
  if (!confirm('¿Estás seguro de que quieres darte de baja?')) return;

  fetch('/api/users/me', {
    method: 'DELETE',
    credentials: 'include'
  })
    .then(response => {
      if (response.ok) {
        window.location.href = 'login.html';
      } else {
        throw new Error('No se pudo eliminar la cuenta');
      }
    })
    .catch(error => alert('✖ ' + error.message));
}
