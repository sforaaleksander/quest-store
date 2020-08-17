// sprawdza tylko czy oba inputy nie sa puste

const inputEmail = document.getElementById("email");
const inputPassword = document.getElementById("password");

inputEmail.addEventListener("input", ()=> {signInButtonValidation()});
inputPassword.addEventListener("input", ()=> {signInButtonValidation()})

document.getElementById("signin").addEventListener("click", ()=> {checkFormAndDisplayMessage()});
document.getElementById("signup").addEventListener("click", ()=> {checkFormAndDisplayMessage()});

function checkFormAndDisplayMessage() {
    signInButtonValidation() ? clearInputs() : document.getElementById("alert").hidden = false;
}

function signInButtonValidation() {
    if (inputEmail.value && inputPassword.value) {
        document.getElementById("alert").hidden = true;
    }
    return inputEmail.value && inputPassword.value;
}

function clearInputs() {
    inputEmail.value = "";
    inputPassword.value = "";
    document.getElementById("alert").hidden = true;
}
