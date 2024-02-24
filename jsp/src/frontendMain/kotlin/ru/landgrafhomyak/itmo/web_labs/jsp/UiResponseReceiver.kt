package ru.landgrafhomyak.itmo.web_labs.jsp

interface UiResponseReceiver {
    sealed class Result {
        object Canceled: Result(),
    }
}