package com.example.conectaribas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Entidade que representa um registro de sintomas do usuário
 * Armazena informações sobre consultas de autodiagnóstico realizadas
 */
@Entity(tableName = "symptom_records")
data class SymptomRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * Data e hora da consulta de autodiagnóstico
     */
    val timestamp: Date,
    
    /**
     * Sintomas relatados pelo usuário (separados por vírgula)
     */
    val symptoms: String,
    
    /**
     * Resultado do autodiagnóstico:
     * - "Sintomas Leves (Observe)"
     * - "Orientações Iniciais"
     * - "Alerta de Emergência (Procure Ajuda Urgente)"
     */
    val diagnosis: String,
    
    /**
     * Orientações específicas fornecidas pelo sistema
     */
    val recommendations: String,

    /**
     * URI da foto do paciente (opcional)
     */

    /**
     * Nome ou identificação do usuário (opcional)
     */
    val patientName: String? = null

)
