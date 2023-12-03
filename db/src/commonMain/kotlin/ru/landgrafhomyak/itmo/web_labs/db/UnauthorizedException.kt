package ru.landgrafhomyak.itmo.web_labs.db

@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class UnauthorizedException(val token: ByteArray) : Exception(
    "User with token '${token.joinToString { b -> (b / 16).toString(16) + (b % 16).toString(16) }}' not found"
)