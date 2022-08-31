'use strict';

function setMessage(elementId, message) {
    let msgBox = document.getElementById(elementId);
    msgBox.innerText = message;
    if (message === null) {
        msgBox.parentElement.style.display = "none";
        return true;
    } else {
        msgBox.parentElement.style.display = "block";
        return false
    }
}

function checkY(input) {
    let raw = input.value
    let minusCount = 0
    let dotsCount = 0
    for (let c of raw) {
        switch (c) {
            case '-':
                minusCount++;
                break;
            case ',':
            case '.':
                dotsCount++;
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                break;
            default:
                return setMessage("form-y-error", "Unexpected char '" + c + "'")
        }
    }
    if (minusCount > 1)
        return setMessage("form-y-error", "Слишком много минусов")
    if (dotsCount > 1)
        return setMessage("form-y-error", "Слишком много точек или запятых")
    if (minusCount > 0 && raw[0] !== '-')
        return setMessage("form-y-error", "Минус должен быть в начале")
    if (dotsCount > 0 && (
        (minusCount > 0 && (raw[1] === '.' || raw[1] === ',')) ||
        (raw[0] === '.' || raw[0] === ',') ||
        (raw[raw.length - 1] === '.' || raw[raw.length - 1] === ',')
    ))
        return setMessage(
            "form-y-error",
            "Лидирующие и завершающие точки и запятые недопустими, указывайте нули явно"
        )
    if (raw === "-")
        return setMessage("form-y-error", "Просто минус - это не число")
    if (raw === "-0")
        return setMessage("form-y-error", "Нулю минус не нужен")
    if (+raw <= -5 || 3 <= +raw)
        return setMessage("form-y-error", "Y должен быть в диапазоне (-3; 5)")
    return setMessage("form-y-error", null)
}