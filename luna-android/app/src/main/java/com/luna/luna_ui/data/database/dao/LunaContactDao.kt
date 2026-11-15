package com.luna.luna_ui.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.luna.luna_ui.data.database.entities.LunaContactEntity

@Dao
interface LunaContactDao {

    @Query("SELECT * FROM luna_contacts ORDER BY displayName ASC")
    suspend fun getAllContacts(): List<LunaContactEntity>

    @Query("SELECT * FROM luna_contacts WHERE contactId = :id LIMIT 1")
    suspend fun getById(id: String): LunaContactEntity?

    @Query("SELECT * FROM luna_contacts WHERE phoneticKey = :code OR phoneticKeyAlt = :code")
    suspend fun getByPhoneticCode(code: String): List<LunaContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<LunaContactEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: LunaContactEntity)

    @Update
    suspend fun update(contact: LunaContactEntity)

    @Query("DELETE FROM luna_contacts WHERE contactId NOT IN (:activeIds)")
    suspend fun deleteStale(activeIds: List<String>)

    @Query("DELETE FROM luna_contacts")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM luna_contacts")
    suspend fun count(): Int
}
