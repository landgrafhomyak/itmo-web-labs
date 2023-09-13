'use strict';

function setMessage(elementId, message) {
    let msgBox = document.getElementById(elementId);
    msgBox.innerText = message;
    if (message === null) {
        msgBox.parentElement.style.display = "none";
        return undefined
    } else {
        msgBox.parentElement.style.display = "block";
        return null
    }
}

const xButtons = []
let yInput = null
const rButtons = []
let xAccessed = false
let yAccessed = false
let rAccessed = false

function getY(clear) {
    if (!yAccessed) {
        setMessage("form-y-error", null)
        return false
    }
    let raw = document.getElementById("form-y").value
    if (clear)
        document.getElementById("form-y").value = ""

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

    setMessage("form-y-error", null)
    return +raw
}

function getX(clear) {
    if (!xAccessed) {
        setMessage("form-x-error", null)
        return null
    }
    for (let btn of xButtons) {
        if (btn.checked) {
            setMessage("form-x-error", null)
            const value = btn.value
            if (clear)
                btn.checked = false
            return value
        }
    }

    return setMessage("form-x-error", "Выберите координату")
}

function getR(clear) {
    if (!rAccessed) {
        setMessage("form-r-error", null)
        return false
    }
    let selectedCount = 0
    let selected = null
    for (let btn of rButtons) {
        if (btn.checked) {
            selectedCount++
            selected = btn
            if (clear)
                btn.checked = false
        }
    }
    if (selectedCount === 0) {
        return setMessage("form-r-error", "Выберите размер")
    }
    if (selectedCount > 1) {
        return setMessage("form-r-error", "Слишком много размеров выбрано")
    }
    setMessage("form-r-error", null)
    return selected.value
}

function checkAll() {
    let x = getX(false) !== null
    let y = getY(false) !== null
    let r = getR(false) !== null
    document.getElementById("form-submit").disabled = !(x && y && r)
}


function shift() {
    const last = {
        "point": document.getElementById("dot-point-" + (historySize - 1)),
        "title": document.getElementById("dot-link-" + (historySize - 1)),
        "row": document.getElementById("request-" + (historySize - 1))
    }
    if (last.point !== null) last.point.remove()
    if (last.title !== null) last.title.remove()
    if (last.row !== null) last.row.remove()


    for (const i of Array.from(new Array(historySize - 1), (x, i) => historySize - 2 - i)) {
        const elem = {
            "point": document.getElementById("dot-point-" + i),
            "title": document.getElementById("dot-link-" + i),
            "row": document.getElementById("request-" + i)
        }

        if (elem.point !== null) elem.point.id = "dot-point-" + (i + 1)
        if (elem.title !== null) elem.title.id = "dot-link-" + (i + 1)
        if (elem.row !== null) elem.row.id = "request-" + (i + 1)
    }
}

function generateResponseUI(time, x, y, r, state) {
    if (state === "y" || state === "n") {
        x = +x
        y = +y
        r = +r

        let point = {
            "container": document.getElementById("dot-points"),
            "root": document.createElement("a"),
            "point": document.createElement("circle")
        }
        point.root.appendChild(point.point)
        point.container.insertBefore(point.root, point.container.firstChild)
        point.root.id = "dot-point-0"
        point.root.href = "#request-0"
        point.point.classList.add("point")
        point.point.id = "point-0"
        point.point.cy = 75 + y * 50 / r
        point.point.cx = 75 + x * 50 / r
        point.point.r = 2

        let link = {
            "container": document.getElementById("dot-links"),
            "root": document.createElement("a"),
            "text": document.createElement("text")
        }

        link.root.appendChild(link.text)
        link.container.insertBefore(link.root, link.container.firstChild)
        link.root.id = "dot-link-0"
        link.root.href = "#request-0"
        link.text.classList.add("point")
        link.text.y = 75 + y * 50 / r - 3
        link.text.x = 75 + x * 50 / r + 3
        link.text.innerText = "(x=" + x.toString().substring(0, 6) + "; y=" + y.toString().substring(0, 6) + "; r=" + y.toString().substring(0, 6) + ")"
    }

    x = x.toString()
    y = y.toString()
    r = r.toString()

    let history = {
        "container": document.getElementById("request-history").children[0],
        "splitter": document.createElement("tr"),
        "root": document.createElement("tr"),
        "time": document.createElement("td"),
        "x": document.createElement("td"),
        "y": document.createElement("td"),
        "r": document.createElement("td"),
        "state": document.createElement("td"),
        "link": document.createElement("a"),
    }
    history.container.insertBefore(history.root, history.container.children[1])
    history.container.insertBefore(history.splitter, history.container.children[1])
    history.splitter.classList.add("splitter")
    history.root.appendChild(history.time)
    history.time.innerText = time + " UTC+0"
    history.root.appendChild(history.x)
    history.x.innerText = x.substring(0, 6)
    history.root.appendChild(history.y)
    history.y.innerText = y.substring(0, 6)
    history.root.appendChild(history.r)
    history.r.innerText = r.substring(0, 6)

    history.root.appendChild(history.state)
    history.link.href = "#point-0"
    switch (state) {
        case "y":
            history.state.appendChild(history.link)
            history.link.style.color = "green"
            history.link.innerText = "В области"
            break
        case "n":
            history.state.appendChild(history.link)
            history.link.style.color = "#b300ff"
            history.link.innerText = "Не в области"
            break
        case "i":
        default:
            history.state.style.color = "red"
            history.state.innerText = "Невалидный запрос"
    }
}

function submit() {
    let x = getX(true)
    let y = getY(true)
    let r = getR(true)
    xAccessed = false
    yAccessed = false
    rAccessed = false
    if (x === null || y === null || r === null)
        return

    const fd = new FormData()
    fd.append("x", x.toString())
    fd.append("y", y.toString())
    fd.append("r", r.toString())

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "./submit.php", false)
    xhr.send(fd)
    const resp = JSON.parse(xhr.response);

    shift()

    generateResponseUI(resp["time"], resp["x"], resp["y"], resp["r"], resp["state"])


}