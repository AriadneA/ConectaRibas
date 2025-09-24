package com.example.conectaribas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade que representa um guia de primeiros socorros
 * Contém informações sobre procedimentos de emergência para diferentes situações
 */
@Entity(tableName = "first_aid_guides")
data class FirstAidGuide(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * Título do guia (ex: "Picadas de Cobra", "Cortes", "Queimaduras")
     */
    val title: String,
    
    /**
     * Categoria do guia para organização
     */
    val category: String,
    
    /**
     * Descrição resumida do problema
     */
    val description: String,
    
    /**
     * Conteúdo completo do guia com instruções passo a passo
     */
    val content: String,
    
    /**
     * Sinais de alerta que indicam necessidade de ajuda médica urgente
     */
    val warningSigns: String,
    
    /**
     * Nome do arquivo de imagem associado (se houver)
     */
    val imageFileName: String? = null,
    
    /**
     * Ordem de exibição na lista de guias
     */
    val displayOrder: Int = 0,
    
    /**
     * Indica se este guia é para situações de emergência
     */
    val isEmergency: Boolean = false
)
