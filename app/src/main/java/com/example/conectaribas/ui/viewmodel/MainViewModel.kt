package com.example.conectaribas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conectaribas.data.entities.*
import com.example.conectaribas.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date


/**
 * ViewModel principal da aplicação Conecta Ribas
 * Gerencia o estado global e coordena as operações entre a UI e o repositório
 */
class MainViewModel(
    private val repository: AppRepository
) : ViewModel() {
    
    // ===== ESTADOS DA APLICAÇÃO =====
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    private val _diagnosisState = MutableStateFlow(DiagnosisState())
    val diagnosisState: StateFlow<DiagnosisState> = _diagnosisState.asStateFlow()
    
    private val _firstAidGuides = MutableStateFlow<List<FirstAidGuide>>(emptyList())
    val firstAidGuides: StateFlow<List<FirstAidGuide>> = _firstAidGuides.asStateFlow()
    
    private val _symptomHistory = MutableStateFlow<List<SymptomRecord>>(emptyList())
    val symptomHistory: StateFlow<List<SymptomRecord>> = _symptomHistory.asStateFlow()
    
    // ===== INICIALIZAÇÃO =====
    
    init {
        loadInitialData()
    }
    
    /**
     * Carrega os dados iniciais da aplicação
     */
    fun loadInitialData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                launch {
                    repository.getAllFirstAidGuides().collect { guides ->
                        _firstAidGuides.value = guides
                    }
                }
                launch {
                    repository.getAllSymptomRecords().collect { records ->
                        _symptomHistory.value = records
                    }
                }

                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar dados"
                )
            }
        }
    }
    
    // ===== OPERAÇÕES DE DIAGNÓSTICO =====
    
    /**
     * Inicia o processo de autodiagnóstico
     */
    fun startDiagnosis() {
        viewModelScope.launch {
            try {
                _diagnosisState.value = DiagnosisState(isLoading = true)
                
                val firstQuestion = repository.getFirstQuestion()
                if (firstQuestion != null) {
                    val answers = repository.getAnswersForQuestion(firstQuestion.id)
                    _diagnosisState.value = DiagnosisState(
                        currentQuestion = firstQuestion,
                        availableAnswers = answers,
                        isLoading = false
                    )
                } else {
                    _diagnosisState.value = DiagnosisState(
                        error = "Nenhuma pergunta de diagnóstico encontrada",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _diagnosisState.value = DiagnosisState(
                    error = e.message ?: "Erro ao iniciar diagnóstico",
                    isLoading = false
                )
            }
        }
    }
    
    /**
     * Processa a resposta do usuário e avança para a próxima pergunta ou resultado
     */
    fun processAnswer(answer: DiagnosisAnswer) {
        viewModelScope.launch {
            try {
                if (answer.finalResult != null) {
                    // Resposta final - salva o registro e exibe resultado
                    val currentQuestion = _diagnosisState.value.currentQuestion
                    if (currentQuestion != null) {
                        val symptomRecord = SymptomRecord(
                            timestamp = Date(),
                            symptoms = buildSymptomsDescription(currentQuestion, answer),
                            diagnosis = answer.finalResult,
                            recommendations = answer.recommendations ?: ""
                        )
                        
                        val recordId = repository.insertSymptomRecord(symptomRecord)
                        
                        // Atualiza o histórico local
                        _symptomHistory.value = _symptomHistory.value + symptomRecord.copy(id = recordId)
                        
                        _diagnosisState.value = DiagnosisState(
                            finalResult = answer.finalResult,
                            recommendations = answer.recommendations ?: "",
                            recordId = recordId
                        )
                    }
                } else if (answer.nextQuestionId != null) {
                    // Avança para a próxima pergunta
                    val nextQuestion = repository.getQuestionById(answer.nextQuestionId)
                    if (nextQuestion != null) {
                        val answers = repository.getAnswersForQuestion(nextQuestion.id)
                        _diagnosisState.value = DiagnosisState(
                            currentQuestion = nextQuestion,
                            availableAnswers = answers,
                            questionHistory = _diagnosisState.value.questionHistory + (_diagnosisState.value.currentQuestion ?: return@launch)
                        )
                    }
                }
            } catch (e: Exception) {
                _diagnosisState.value = DiagnosisState(
                    error = e.message ?: "Erro ao processar resposta",
                    currentQuestion = _diagnosisState.value.currentQuestion,
                    availableAnswers = _diagnosisState.value.availableAnswers
                )
            }
        }
    }
    
    /**
     * Reinicia o processo de diagnóstico
     */
    fun resetDiagnosis() {
        _diagnosisState.value = DiagnosisState()
    }
    
    /**
     * Volta para a pergunta anterior
     */
    fun goToPreviousQuestion() {
        val currentHistory = _diagnosisState.value.questionHistory
        if (currentHistory.isNotEmpty()) {
            val previousQuestion = currentHistory.last()
            val remainingHistory = currentHistory.dropLast(1)
            
            viewModelScope.launch {
                val answers = repository.getAnswersForQuestion(previousQuestion.id)
                _diagnosisState.value = DiagnosisState(
                    currentQuestion = previousQuestion,
                    availableAnswers = answers,
                    questionHistory = remainingHistory
                )
            }
        }
    }
    
    // ===== OPERAÇÕES DE HISTÓRICO =====
    
    /**
     * Remove um registro de sintomas
     */
    fun deleteSymptomRecord(record: SymptomRecord) {
        viewModelScope.launch {
            try {
                repository.deleteSymptomRecord(record)
                _symptomHistory.value = _symptomHistory.value.filter { it.id != record.id }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Erro ao remover registro"
                )
            }
        }
    }
    
    /**
     * Limpa todos os registros de sintomas
     */
    fun clearAllSymptomRecords() {
        viewModelScope.launch {
            try {
                repository.clearAllSymptomRecords()
                _symptomHistory.value = emptyList()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Erro ao limpar registros"
                )
            }
        }
    }
    
    // ===== OPERAÇÕES DE EXPORTAÇÃO/IMPORTAÇÃO =====
    
    /**
     * Exporta o histórico de sintomas como JSON
     */
    fun exportSymptomHistory(): String {
        return try {
            val records = _symptomHistory.value
            buildString {
                appendLine("{")
                appendLine("  \"records\": [")
                records.forEachIndexed { index, record ->
                    appendLine("    {")
                    appendLine("      \"id\": ${record.id},")
                    appendLine("      \"timestamp\": \"${record.timestamp}\",")
                    appendLine("      \"patientName\": \"${record.patientName ?: ""}\",")
                    appendLine("      \"symptoms\": \"${record.symptoms}\",")
                    appendLine("      \"diagnosis\": \"${record.diagnosis}\",")
                    appendLine("      \"recommendations\": \"${record.recommendations}\"")
                    appendLine("    }${if (index < records.size - 1) "," else ""}")
                }
                appendLine("  ]")
                appendLine("}")
            }
        } catch (e: Exception) {
            "Erro na exportação: ${e.message}"
        }
    }
    
    /**
     * Limpa mensagens de erro
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
        _diagnosisState.value = _diagnosisState.value.copy(error = null)
    }
    
    /**
     * Salva um registro de sintomas diretamente
     */
    fun saveSymptomRecord(patientName: String?, symptoms: String, diagnosis: String, recommendations: String) {
        viewModelScope.launch {
            try {
                val symptomRecord = SymptomRecord(
                    timestamp = Date(),
                    symptoms = symptoms,
                    diagnosis = diagnosis,
                    recommendations = recommendations,
                    patientName = patientName
                )
                
                val recordId = repository.insertSymptomRecord(symptomRecord)
                
                // Atualiza o histórico local
                _symptomHistory.value = _symptomHistory.value + symptomRecord.copy(id = recordId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Erro ao salvar registro"
                )
            }
        }
    }
    
    /**
     * Constrói uma descrição dos sintomas baseada na pergunta e resposta
     */
    private fun buildSymptomsDescription(question: DiagnosisQuestion, answer: DiagnosisAnswer): String {
        return "${question.questionText}: ${answer.answerText}"
    }
}

/**
 * Estado principal da UI
 */
data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Estado do processo de diagnóstico
 */
data class DiagnosisState(
    val isLoading: Boolean = false,
    val currentQuestion: DiagnosisQuestion? = null,
    val availableAnswers: List<DiagnosisAnswer> = emptyList(),
    val questionHistory: List<DiagnosisQuestion> = emptyList(),
    val finalResult: String? = null,
    val recommendations: String? = null,
    val recordId: Long? = null,
    val error: String? = null
)
