package com.example.conectaribas.data.dao

import androidx.room.*
import com.example.conectaribas.data.entities.SymptomRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * DAO para acessar os registros de sintomas no banco de dados
 * Fornece métodos para inserir, consultar e gerenciar o histórico de sintomas
 */
@Dao
interface SymptomRecordDao {
    
    /**
     * Insere um novo registro de sintomas
     */
    @Insert
    suspend fun insertSymptomRecord(symptomRecord: SymptomRecord): Long
    
    /**
     * Atualiza um registro de sintomas existente
     */
    @Update
    suspend fun updateSymptomRecord(symptomRecord: SymptomRecord)
    
    /**
     * Remove um registro de sintomas
     */
    @Delete
    suspend fun deleteSymptomRecord(symptomRecord: SymptomRecord)
    
    /**
     * Busca um registro específico por ID
     */
    @Query("SELECT * FROM symptom_records WHERE id = :id")
    suspend fun getSymptomRecordById(id: Long): SymptomRecord?
    
    /**
     * Retorna todos os registros de sintomas ordenados por data (mais recente primeiro)
     */
    @Query("SELECT * FROM symptom_records ORDER BY timestamp DESC")
    fun getAllSymptomRecords(): Flow<List<SymptomRecord>>
    
    /**
     * Busca registros por período específico
     */
    @Query("SELECT * FROM symptom_records WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getSymptomRecordsByDateRange(startDate: Date, endDate: Date): Flow<List<SymptomRecord>>
    
    /**
     * Busca registros por nome do paciente
     */
    @Query("SELECT * FROM symptom_records WHERE patientName LIKE '%' || :name || '%' ORDER BY timestamp DESC")
    fun getSymptomRecordsByPatientName(name: String): Flow<List<SymptomRecord>>
    
    /**
     * Busca registros por tipo de diagnóstico
     */
    @Query("SELECT * FROM symptom_records WHERE diagnosis = :diagnosis ORDER BY timestamp DESC")
    fun getSymptomRecordsByDiagnosis(diagnosis: String): Flow<List<SymptomRecord>>
    
    /**
     * Remove todos os registros de sintomas
     */
    @Query("DELETE FROM symptom_records")
    suspend fun deleteAllSymptomRecords()
    
    /**
     * Retorna o total de registros
     */
    @Query("SELECT COUNT(*) FROM symptom_records")
    suspend fun getTotalRecordsCount(): Int
}
