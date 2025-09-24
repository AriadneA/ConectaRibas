package com.example.conectaribas.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Entidade que representa uma resposta possível para uma pergunta de diagnóstico
 * Cada resposta pode levar a uma próxima pergunta ou a um resultado final
 */
@Entity(
    tableName = "diagnosis_answers",
    foreignKeys = [
        ForeignKey(
            entity = DiagnosisQuestion::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DiagnosisAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * ID da pergunta à qual esta resposta pertence
     */
    val questionId: Long,
    
    /**
     * Texto da resposta a ser exibida ao usuário
     */
    val answerText: String,
    
    /**
     * ID da próxima pergunta (se houver)
     * Null se esta resposta leva a um resultado final
     */
    val nextQuestionId: Long? = null,
    
    /**
     * Resultado final se esta resposta não levar a outra pergunta:
     * - "Sintomas Leves (Observe)"
     * - "Orientações Iniciais"
     * - "Alerta de Emergência (Procure Ajuda Urgente)"
     */
    val finalResult: String? = null,
    
    /**
     * Orientações específicas para este resultado
     */
    val recommendations: String? = null,
    
    /**
     * Pontuação de gravidade (1-10) para ajudar na priorização
     */
    val severityScore: Int = 1
)
