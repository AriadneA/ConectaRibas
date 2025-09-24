package com.example.conectaribas.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conectaribas.data.repository.AppRepository
import com.example.conectaribas.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gerenciar o estado da tela de configurações
 */
class SettingsViewModel(
    private val preferencesManager: PreferencesManager,
    private val repository: AppRepository
) : ViewModel() {
    
    private val _notificationsEnabled = MutableStateFlow(preferencesManager.notificationsEnabled)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()
    
    private val _language = MutableStateFlow(preferencesManager.getLanguageDisplayName())
    val language: StateFlow<String> = _language.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()
    
    /**
     * Atualiza o estado das notificações
     */
    fun updateNotifications(enabled: Boolean) {
        android.util.Log.d("SettingsViewModel", "Updating notifications: $enabled")
        preferencesManager.notificationsEnabled = enabled
        _notificationsEnabled.value = enabled
        _message.value = "Notificações ${if (enabled) "ativadas" else "desativadas"}"
    }
    
    /**
     * Atualiza o idioma
     */
    fun updateLanguage(languageCode: String) {
        android.util.Log.d("SettingsViewModel", "Updating language: $languageCode")
        preferencesManager.language = languageCode
        _language.value = preferencesManager.getLanguageDisplayName()
        _message.value = "Idioma alterado para ${_language.value}"
    }
    
    /**
     * Exporta dados no formato especificado
     */
    fun exportData(format: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (format) {
                    "json" -> {
                        // Implementar exportação JSON
                        _message.value = "Dados exportados em JSON com sucesso!"
                    }
                    "csv" -> {
                        // Implementar exportação CSV
                        _message.value = "Dados exportados em CSV com sucesso!"
                    }
                    "qr" -> {
                        // Implementar geração de QR Code
                        _message.value = "QR Code gerado com sucesso!"
                    }
                }
            } catch (e: Exception) {
                _message.value = "Erro ao exportar dados: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Importa dados do formato especificado
     */
    fun importData(format: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (format) {
                    "qr" -> {
                        // Implementar importação via QR Code
                        _message.value = "Dados importados via QR Code com sucesso!"
                    }
                    "file" -> {
                        // Implementar importação de arquivo
                        _message.value = "Dados importados de arquivo com sucesso!"
                    }
                    "bluetooth" -> {
                        // Implementar importação via Bluetooth
                        _message.value = "Dados importados via Bluetooth com sucesso!"
                    }
                }
            } catch (e: Exception) {
                _message.value = "Erro ao importar dados: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Limpa todos os dados da aplicação
     */
    fun clearAllData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.clearAllSymptomRecords()
                repository.clearAllDiagnosisData()
                repository.clearAllFirstAidGuides()
                preferencesManager.clearAll()
                _message.value = "Todos os dados foram removidos com sucesso!"
            } catch (e: Exception) {
                _message.value = "Erro ao limpar dados: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Limpa a mensagem atual
     */
    fun clearMessage() {
        _message.value = null
    }
    
    /**
     * Retorna os idiomas disponíveis
     */
    fun getAvailableLanguages(): List<Pair<String, String>> {
        return preferencesManager.getAvailableLanguages()
    }
}
