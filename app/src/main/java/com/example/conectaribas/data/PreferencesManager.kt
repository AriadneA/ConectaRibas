package com.example.conectaribas.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Gerenciador de preferências da aplicação
 * Utiliza SharedPreferences para persistir configurações do usuário
 */
class PreferencesManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "conectaribas_preferences", 
        Context.MODE_PRIVATE
    )
    
    companion object {
        // Chaves das preferências
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        
        // Valores padrão
        private const val DEFAULT_NOTIFICATIONS_ENABLED = true
        private const val DEFAULT_LANGUAGE = "pt"
        private const val DEFAULT_FIRST_LAUNCH = true
    }
    
    /**
     * Configurações de notificações
     */
    var notificationsEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, DEFAULT_NOTIFICATIONS_ENABLED)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, value).apply()
    
    /**
     * Idioma da aplicação
     */
    var language: String
        get() = prefs.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        set(value) = prefs.edit().putString(KEY_LANGUAGE, value).apply()
    
    /**
     * Primeira execução do app
     */
    var isFirstLaunch: Boolean
        get() = prefs.getBoolean(KEY_FIRST_LAUNCH, DEFAULT_FIRST_LAUNCH)
        set(value) = prefs.edit().putBoolean(KEY_FIRST_LAUNCH, value).apply()
    
    /**
     * Limpa todas as preferências
     */
    fun clearAll() {
        prefs.edit().clear().apply()
    }
    
    /**
     * Retorna o nome do idioma em português
     */
    fun getLanguageDisplayName(): String {
        return when (language) {
            "pt" -> "Português"
            "en" -> "English"
            "es" -> "Español"
            else -> "Português"
        }
    }
    
    /**
     * Lista de idiomas disponíveis
     */
    fun getAvailableLanguages(): List<Pair<String, String>> {
        return listOf(
            "pt" to "Português",
            "en" to "English", 
            "es" to "Español"
        )
    }
}

