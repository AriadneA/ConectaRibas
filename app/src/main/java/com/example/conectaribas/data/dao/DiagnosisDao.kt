package com.example.conectaribas.data.dao

import androidx.room.*
import com.example.conectaribas.data.entities.DiagnosisQuestion
import com.example.conectaribas.data.entities.DiagnosisAnswer
import kotlinx.coroutines.flow.Flow

/**
 * DAO para acessar as perguntas e respostas do sistema de autodiagnóstico
 * Fornece métodos para navegar pela árvore de decisão do diagnóstico
 */
@Dao
interface DiagnosisDao {
    
    // Métodos para perguntas
    @Insert
    suspend fun insertQuestion(question: DiagnosisQuestion): Long
    
    @Update
    suspend fun updateQuestion(question: DiagnosisQuestion)
    
    @Delete
    suspend fun deleteQuestion(question: DiagnosisQuestion)
    
    /**
     * Retorna a primeira pergunta do sistema de diagnóstico
     */
    @Query("SELECT * FROM diagnosis_questions WHERE previousQuestionId IS NULL ORDER BY questionOrder LIMIT 1")
    suspend fun getFirstQuestion(): DiagnosisQuestion?
    
    /**
     * Retorna uma pergunta específica por ID
     */
    @Query("SELECT * FROM diagnosis_questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Long): DiagnosisQuestion?
    
    /**
     * Retorna a próxima pergunta baseada no ID da pergunta atual
     */
    @Query("SELECT * FROM diagnosis_questions WHERE id = :nextQuestionId")
    suspend fun getNextQuestion(nextQuestionId: Long): DiagnosisQuestion?
    
    /**
     * Retorna todas as perguntas ordenadas por ordem
     */
    @Query("SELECT * FROM diagnosis_questions ORDER BY questionOrder")
    fun getAllQuestions(): Flow<List<DiagnosisQuestion>>
    
    /**
     * Retorna todas as perguntas como lista suspensa (para inicialização)
     */
    @Query("SELECT * FROM diagnosis_questions ORDER BY questionOrder")
    suspend fun getAllQuestionsList(): List<DiagnosisQuestion>
    
    // Métodos para respostas
    @Insert
    suspend fun insertAnswer(answer: DiagnosisAnswer): Long
    
    @Update
    suspend fun updateAnswer(answer: DiagnosisAnswer)
    
    @Delete
    suspend fun deleteAnswer(answer: DiagnosisAnswer)
    
    /**
     * Retorna todas as respostas para uma pergunta específica
     */
    @Query("SELECT * FROM diagnosis_answers WHERE questionId = :questionId ORDER BY severityScore DESC")
    suspend fun getAnswersForQuestion(questionId: Long): List<DiagnosisAnswer>
    
    /**
     * Retorna uma resposta específica por ID
     */
    @Query("SELECT * FROM diagnosis_answers WHERE id = :answerId")
    suspend fun getAnswerById(answerId: Long): DiagnosisAnswer?
    
    /**
     * Retorna todas as respostas ordenadas por pergunta
     */
    @Query("SELECT * FROM diagnosis_answers ORDER BY questionId, severityScore DESC")
    fun getAllAnswers(): Flow<List<DiagnosisAnswer>>
    
    /**
     * Busca perguntas por categoria
     */
    @Query("SELECT * FROM diagnosis_questions WHERE category = :category ORDER BY questionOrder")
    fun getQuestionsByCategory(category: String): Flow<List<DiagnosisQuestion>>
    
    /**
     * Remove todas as perguntas e respostas (para reset do sistema)
     */
    @Query("DELETE FROM diagnosis_questions")
    suspend fun deleteAllQuestions()
    
    @Query("DELETE FROM diagnosis_answers")
    suspend fun deleteAllAnswers()
}
