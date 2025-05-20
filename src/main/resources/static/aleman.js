
function flipCard(card) {
  const original = card.dataset.original.trim();
  const translation = card.dataset.translation.trim();
  card.textContent = (card.textContent.trim() === original) ? translation : original;
}

const questions = [
  { text: "Wie sagt man 'Hola' auf Deutsch?", options: ["Hallo", "Danke", "Bitte", "Hund"], correct: 0 },
  { text: "Wie sagt man 'Gracias' auf Deutsch?", options: ["Tschüss", "Hallo", "Danke", "Katze"], correct: 2 }
];

function loadGermanTest() {
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
  loadGermanTest();
});


