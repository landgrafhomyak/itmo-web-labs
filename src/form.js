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

const xButtons = []
const rButtons = []
let xAccessed = false
let yAccessed = false
let rAccessed = false

function checkY() {
    if (!yAccessed) {
        setMessage("form-y-error", null)
        return false
    }
    let raw = document.getElementById("form-y").value
    if (raw === "")
        return setMessage("form-y-error", "Введите число")
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
                return setMessage("form-y-error", "Невалидный символ '" + c + "'")
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
    if (raw.length >= 2 && raw[0] === '0' && raw[1] !== '.' || raw.length >= 3 && raw[0] === '-' && raw[1] === '0' && raw[2] !== '.')
        return setMessage("form-y-error", "Лидирующие нули - это плохо")
    if (+raw <= -5 || 3 <= +raw)
        return setMessage("form-y-error", "Y должен быть в диапазоне (-5; 3)")

    return setMessage("form-y-error", null)
}

function checkX() {
    if (!xAccessed) {
        setMessage("form-x-error", null)
        return false
    }
    for (let btn of xButtons) {
        if (btn.checked) {
            return setMessage("form-x-error", null)
        }
    }
    return setMessage("form-x-error", "Выберите координату")
}

function checkR() {
    if (!rAccessed) {
        setMessage("form-r-error", null)
        return false
    }
    let selectedCount = 0
    for (let btn of rButtons) {
        if (btn.checked) {
            selectedCount++
        }
    }
    if (selectedCount === 0) {
        return setMessage("form-r-error", "Выберите размер")
    }
    if (selectedCount > 1) {
        return setMessage("form-r-error", "Слишком много размеров выбрано")
    }
    return setMessage("form-r-error", null)
}

function checkAll() {
    let x = checkX()
    let y = checkY()
    let r = checkR()
    document.getElementById("form-submit").disabled = !(x && y && r)
}
