package com.example.conectaribas.data.dao

import androidx.room.*
import com.example.conectaribas.data.entities.FirstAidGuide
import kotlinx.coroutines.flow.Flow

/**
 * DAO para acessar os guias de primeiros socorros no banco de dados
 * Fornece métodos para consultar e gerenciar os guias de emergência
 */
@Dao
interface FirstAidGuideDao {
    
    @Insert
    suspend fun insertGuide(guide: FirstAidGuide): Long
    
    @Update
    suspend fun updateGuide(guide: FirstAidGuide)
    
    @Delete
    suspend fun deleteGuide(guide: FirstAidGuide)
    
    /**
     * Retorna todos os guias ordenados por ordem de exibição
     */
    @Query("SELECT * FROM first_aid_guides ORDER BY displayOrder, title")
    fun getAllGuides(): Flow<List<FirstAidGuide>>
    
    /**
     * Retorna um guia específico por ID
     */
    @Query("SELECT * FROM first_aid_guides WHERE id = :guideId")
    suspend fun getGuideById(guideId: Long): FirstAidGuide?
    
    /**
     * Busca guias por categoria
     */
    @Query("SELECT * FROM first_aid_guides WHERE category = :category ORDER BY displayOrder, title")
    fun getGuidesByCategory(category: String): Flow<List<FirstAidGuide>>
    
    /**
     * Busca guias por título (busca parcial)
     */
    @Query("SELECT * FROM first_aid_guides WHERE title LIKE '%' || :searchTerm || '%' ORDER BY displayOrder, title")
    fun searchGuidesByTitle(searchTerm: String): Flow<List<FirstAidGuide>>
    
    /**
     * Retorna apenas guias de emergência
     */
    @Query("SELECT * FROM first_aid_guides WHERE isEmergency = 1 ORDER BY displayOrder, title")
    fun getEmergencyGuides(): Flow<List<FirstAidGuide>>
    
    /**
     * Retorna o total de guias disponíveis
     */
    @Query("SELECT COUNT(*) FROM first_aid_guides")
    suspend fun getTotalGuidesCount(): Int
    
    /**
     * Remove todos os guias (para reset do sistema)
     */
    @Query("DELETE FROM first_aid_guides")
    suspend fun deleteAllGuides()
}
