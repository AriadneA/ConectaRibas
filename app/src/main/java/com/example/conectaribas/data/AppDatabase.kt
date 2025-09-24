package com.example.conectaribas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.conectaribas.data.dao.DiagnosisDao
import com.example.conectaribas.data.dao.FirstAidGuideDao
import com.example.conectaribas.data.dao.SymptomRecordDao
import com.example.conectaribas.data.entities.DiagnosisAnswer
import com.example.conectaribas.data.entities.DiagnosisQuestion
import com.example.conectaribas.data.entities.FirstAidGuide
import com.example.conectaribas.data.entities.SymptomRecord
import com.example.conectaribas.data.util.Converters

/**
 * Banco de dados principal da aplicação Conecta Ribas
 * Utiliza Room para persistência local de dados
 */
@Database(
    entities = [
        SymptomRecord::class,
        DiagnosisQuestion::class,
        DiagnosisAnswer::class,
        FirstAidGuide::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * DAO para acessar registros de sintomas
     */
    abstract fun symptomRecordDao(): SymptomRecordDao
    
    /**
     * DAO para acessar perguntas e respostas de diagnóstico
     */
    abstract fun diagnosisDao(): DiagnosisDao
    
    /**
     * DAO para acessar guias de primeiros socorros
     */
    abstract fun firstAidGuideDao(): FirstAidGuideDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Obtém a instância do banco de dados
         * Implementa padrão Singleton para garantir uma única instância
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "conecta_ribas_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
