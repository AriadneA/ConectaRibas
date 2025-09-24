package com.example.conectaribas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade que representa uma pergunta do sistema de autodiagnóstico
 * Cada pergunta pode ter múltiplas respostas que levam a diferentes caminhos na árvore de decisão
 */
@Entity(tableName = "diagnosis_questions")
data class DiagnosisQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * Texto da pergunta a ser exibida ao usuário
     */
    val questionText: String,
    
    /**
     * Categoria da pergunta (ex: "Dor", "Febre", "Respiração", etc.)
     */
    val category: String,
    
    /**
     * Ordem da pergunta na sequência de diagnóstico
     */
    val questionOrder: Int,
    
    /**
     * ID da pergunta anterior (para navegação na árvore de decisão)
     * Null se for a primeira pergunta
     */
    val previousQuestionId: Long? = null,
    
    /**
     * Indica se esta pergunta é obrigatória para o diagnóstico
     */
    val isRequired: Boolean = true
)
