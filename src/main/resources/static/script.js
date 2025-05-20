document.addEventListener("DOMContentLoaded", function () {
    console.log("Script cargado correctamente");

    const languageButton = document.getElementById("language-toggle");

    languageButton.addEventListener("click", function () {
        window.location.href = "suscripcion.html"; // Redirige a otra página
    });
});


    languageButton.addEventListener("click", function () {
        currentLanguage = currentLanguage === "es" ? "en" : "es";

        document.getElementById("main-title").innerText = translations[currentLanguage]["title"];
        document.getElementById("main-text").innerText = translations[currentLanguage]["text"];
        languageButton.innerText = translations[currentLanguage]["button"];
    });

