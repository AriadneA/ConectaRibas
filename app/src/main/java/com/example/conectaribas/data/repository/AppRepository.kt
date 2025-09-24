package com.example.conectaribas.data.repository

import com.example.conectaribas.data.dao.DiagnosisDao
import com.example.conectaribas.data.dao.FirstAidGuideDao
import com.example.conectaribas.data.dao.SymptomRecordDao
import com.example.conectaribas.data.entities.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Repositório principal da aplicação Conecta Ribas
 * Centraliza todas as operações de dados e fornece uma interface única para acesso aos dados
 */
class AppRepository(
    private val symptomRecordDao: SymptomRecordDao,
    private val diagnosisDao: DiagnosisDao,
    private val firstAidGuideDao: FirstAidGuideDao
) {
    
    // ===== OPERAÇÕES COM REGISTROS DE SINTOMAS =====
    
    /**
     * Insere um novo registro de sintomas
     */
    suspend fun insertSymptomRecord(symptomRecord: SymptomRecord): Long {
        return symptomRecordDao.insertSymptomRecord(symptomRecord)
    }
    
    /**
     * Remove um registro de sintomas
     */
    suspend fun deleteSymptomRecord(symptomRecord: SymptomRecord) {
        symptomRecordDao.deleteSymptomRecord(symptomRecord)
    }
    
    /**
     * Retorna todos os registros de sintomas
     */
    fun getAllSymptomRecords(): Flow<List<SymptomRecord>> {
        return symptomRecordDao.getAllSymptomRecords()
    }
    
    /**
     * Busca registros por período
     */
    fun getSymptomRecordsByDateRange(startDate: Date, endDate: Date): Flow<List<SymptomRecord>> {
        return symptomRecordDao.getSymptomRecordsByDateRange(startDate, endDate)
    }
    
    /**
     * Busca registros por nome do paciente
     */
    fun getSymptomRecordsByPatientName(name: String): Flow<List<SymptomRecord>> {
        return symptomRecordDao.getSymptomRecordsByPatientName(name)
    }
    
    /**
     * Busca registros por tipo de diagnóstico
     */
    fun getSymptomRecordsByDiagnosis(diagnosis: String): Flow<List<SymptomRecord>> {
        return symptomRecordDao.getSymptomRecordsByDiagnosis(diagnosis)
    }
    
    // ===== OPERAÇÕES COM SISTEMA DE DIAGNÓSTICO =====
    
    /**
     * Retorna a primeira pergunta do sistema de diagnóstico
     */
    suspend fun getFirstQuestion(): DiagnosisQuestion? {
        return diagnosisDao.getFirstQuestion()
    }
    
    /**
     * Retorna uma pergunta específica por ID
     */
    suspend fun getQuestionById(questionId: Long): DiagnosisQuestion? {
        return diagnosisDao.getQuestionById(questionId)
    }
    
    /**
     * Retorna a próxima pergunta baseada no ID
     */
    suspend fun getNextQuestion(nextQuestionId: Long): DiagnosisQuestion? {
        return diagnosisDao.getNextQuestion(nextQuestionId)
    }
    
    /**
     * Retorna todas as respostas para uma pergunta específica
     */
    suspend fun getAnswersForQuestion(questionId: Long): List<DiagnosisAnswer> {
        return diagnosisDao.getAnswersForQuestion(questionId)
    }
    
    /**
     * Retorna uma resposta específica por ID
     */
    suspend fun getAnswerById(answerId: Long): DiagnosisAnswer? {
        return diagnosisDao.getAnswerById(answerId)
    }
    
    /**
     * Insere uma nova pergunta de diagnóstico
     */
    suspend fun insertDiagnosisQuestion(question: DiagnosisQuestion): Long {
        return diagnosisDao.insertQuestion(question)
    }
    
    /**
     * Insere uma nova resposta de diagnóstico
     */
    suspend fun insertDiagnosisAnswer(answer: DiagnosisAnswer): Long {
        return diagnosisDao.insertAnswer(answer)
    }
    
    // ===== OPERAÇÕES COM GUIAS DE PRIMEIROS SOCORROS =====
    
    /**
     * Retorna todos os guias de primeiros socorros
     */
    fun getAllFirstAidGuides(): Flow<List<FirstAidGuide>> {
        return firstAidGuideDao.getAllGuides()
    }
    
    /**
     * Retorna um guia específico por ID
     */
    suspend fun getFirstAidGuideById(guideId: Long): FirstAidGuide? {
        return firstAidGuideDao.getGuideById(guideId)
    }
    
    /**
     * Busca guias por categoria
     */
    fun getFirstAidGuidesByCategory(category: String): Flow<List<FirstAidGuide>> {
        return firstAidGuideDao.getGuidesByCategory(category)
    }
    
    /**
     * Busca guias por título
     */
    fun searchFirstAidGuidesByTitle(searchTerm: String): Flow<List<FirstAidGuide>> {
        return firstAidGuideDao.searchGuidesByTitle(searchTerm)
    }
    
    /**
     * Retorna apenas guias de emergência
     */
    fun getEmergencyFirstAidGuides(): Flow<List<FirstAidGuide>> {
        return firstAidGuideDao.getEmergencyGuides()
    }
    
    /**
     * Insere um novo guia de primeiros socorros
     */
    suspend fun insertFirstAidGuide(guide: FirstAidGuide): Long {
        return firstAidGuideDao.insertGuide(guide)
    }
    
    // ===== OPERAÇÕES DE MANUTENÇÃO =====
    
    /**
     * Remove todos os registros de sintomas
     */
    suspend fun clearAllSymptomRecords() {
        symptomRecordDao.deleteAllSymptomRecords()
    }
    
    /**
     * Remove todas as perguntas e respostas de diagnóstico
     */
    suspend fun clearAllDiagnosisData() {
        diagnosisDao.deleteAllQuestions()
        diagnosisDao.deleteAllAnswers()
    }
    
    /**
     * Remove todos os guias de primeiros socorros
     */
    suspend fun clearAllFirstAidGuides() {
        firstAidGuideDao.deleteAllGuides()
    }
    
    /**
     * Retorna estatísticas do banco de dados
     */
    suspend fun getDatabaseStats(): DatabaseStats {
        return DatabaseStats(
            totalSymptomRecords = symptomRecordDao.getTotalRecordsCount(),
            totalFirstAidGuides = firstAidGuideDao.getTotalGuidesCount()
        )
    }
}

/**
 * Classe para armazenar estatísticas do banco de dados
 */
data class DatabaseStats(
    val totalSymptomRecords: Int,
    val totalFirstAidGuides: Int
)
