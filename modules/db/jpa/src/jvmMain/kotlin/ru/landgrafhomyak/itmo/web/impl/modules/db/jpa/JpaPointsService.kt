package ru.landgrafhomyak.itmo.web.impl.modules.db.jpa

import jakarta.enterprise.inject.Model
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Query
import jakarta.transaction.Transactional
import ru.landgrafhomyak.itmo.web.impl.modules.db.AreaStorage
import ru.landgrafhomyak.itmo.web.impl.modules.db.PointData

@Model
open class JpaPointsService : AreaStorage {
    @Suppress("PrivatePropertyName")
    @field:PersistenceContext
    private lateinit var __em: EntityManager

    private val em by this::__em

    @Transactional
    override fun saveRequest(token: ByteArray?, data: PointData) {
        this.em.persist(PointEntity.fromPointData(data))
    }

    override fun getNewerToOlderHistory(token: ByteArray?): List<PointData> {
        val query: Query = this.em.createQuery("SELECT history FROM history ORDER BY time ASC")
        return query.resultList.map { e -> (e as PointEntity).toPointData() }
    }

    @Transactional
    override fun clearHistory(token: ByteArray?) {
        this.em.createQuery("DELETE FROM history").executeUpdate()
    }
}