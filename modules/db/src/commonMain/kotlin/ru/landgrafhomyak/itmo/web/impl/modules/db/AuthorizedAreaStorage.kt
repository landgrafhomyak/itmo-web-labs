package ru.landgrafhomyak.itmo.web.impl.modules.db

interface AuthorizedAreaStorage : AreaStorage {
    /**
     * Returns session token for authorized user.
     * If credentials are incorrect, returns `null`.
     */
    fun logIn(name: String, password: String): ByteArray?

    /**
     * Invalidates specified token.
     */
    fun logOut(token: ByteArray)
}