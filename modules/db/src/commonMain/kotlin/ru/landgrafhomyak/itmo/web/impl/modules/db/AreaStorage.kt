package ru.landgrafhomyak.itmo.web.impl.modules.db

interface AreaStorage {
    /**
     * Saves request to area check into storage.
     *
     * @param token Session token identifying user.
     * If storage doesn't separate requests by users should be null and will be ignored.
     * Otherwise, null token is incorrect and [UnauthorizedException] will be thrown.
     * @param data Data to be stored. For more info see [PointData].
     */
    @Throws(UnauthorizedException::class)
    fun saveRequest(token: ByteArray?, data: PointData)

    /**
     * Returns requests history for user.
     *
     * @param token Session token identifying user.
     * If storage doesn't separate requests by users should be null and will be ignored.
     * Otherwise, null token is incorrect and [UnauthorizedException] will be thrown.
     * @return List of requests.
     */
    @Throws(UnauthorizedException::class)
    fun getNewerToOlderHistory(token: ByteArray?): List<PointData>

    /**
     * Clears requests history for user.
     *
     * @param token Session token identifying user.
     * If storage doesn't separate requests by users should be null and will be ignored.
     * Otherwise, null token is incorrect and [UnauthorizedException] will be thrown.
     * @return List of requests.
     */
    @Throws(UnauthorizedException::class)
    fun clearHistory(token: ByteArray?)
}