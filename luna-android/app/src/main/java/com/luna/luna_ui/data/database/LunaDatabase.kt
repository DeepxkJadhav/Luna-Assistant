package com.luna.luna_ui.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luna.luna_ui.data.database.dao.LunaContactDao
import com.luna.luna_ui.data.database.dao.SttCorrectionDao
import com.luna.luna_ui.data.database.entities.LunaContactEntity
import com.luna.luna_ui.data.database.entities.SttCorrectionEntity

/**
 * Central Room database for Luna.
 *
 * Bump [version] and add a [androidx.room.migration.Migration] whenever the schema changes.
 * Current tables:
 *  - luna_contacts   — synced device contacts with phonetic keys + STT aliases
 *  - stt_corrections — correction-count ledger driving the learning loop
 */
@Database(
    entities = [
        LunaContactEntity::class,
        SttCorrectionEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LunaDatabase : RoomDatabase() {

    abstract fun audioDao(): AudioDao
    abstract fun contactDao(): LunaContactDao
    abstract fun sttCorrectionDao(): SttCorrectionDao
}
