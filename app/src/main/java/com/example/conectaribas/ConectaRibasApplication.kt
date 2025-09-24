package com.example.conectaribas

import android.app.Application
import com.example.conectaribas.data.AppDatabase
import com.example.conectaribas.data.repository.AppRepository
import com.example.conectaribas.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Application class para inicializar componentes globais da aplicação
 * Configura o banco de dados Room e fornece acesso aos repositórios
 */
class ConectaRibasApplication : Application() {
    
    // Instância do banco de dados
    val database by lazy { AppDatabase.getDatabase(this) }
    
    // Repositório principal
    val repository by lazy { 
        AppRepository(
            symptomRecordDao = database.symptomRecordDao(),
            diagnosisDao = database.diagnosisDao(),
            firstAidGuideDao = database.firstAidGuideDao()
        )
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // Inicializa dados básicos do sistema de diagnóstico
        initializeDiagnosisData()
    }
    
    /**
     * Inicializa os dados básicos do sistema de diagnóstico
     * Cria perguntas e respostas padrão se não existirem
     */
    private fun initializeDiagnosisData() {
        // Executa em uma corrotina para não bloquear a UI
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val diagnosisDao = database.diagnosisDao()
                
                // Verifica se já existem perguntas no banco
                val existingQuestions = diagnosisDao.getAllQuestionsList()
                
                // Se não há perguntas, cria dados básicos
                if (existingQuestions.isEmpty()) {
                    createBasicDiagnosisData(diagnosisDao)
                }
            } catch (e: Exception) {
                // Log do erro, mas não impede o funcionamento do app
                e.printStackTrace()
            }
        }
    }
    
    /**
     * Cria dados básicos de diagnóstico
     */
    private suspend fun createBasicDiagnosisData(diagnosisDao: com.example.conectaribas.data.dao.DiagnosisDao) {
        // Pergunta inicial sobre sintomas
        val question1 = com.example.conectaribas.data.entities.DiagnosisQuestion(
            questionText = "Qual é o seu sintoma principal?",
            category = "Sintomas",
            questionOrder = 1,
            previousQuestionId = null
        )
        val question1Id = diagnosisDao.insertQuestion(question1)
        
        // Respostas para a primeira pergunta
        val answer1_1 = com.example.conectaribas.data.entities.DiagnosisAnswer(
            questionId = question1Id,
            answerText = "Dor de cabeça",
            nextQuestionId = null,
            finalResult = "Sintomas Leves (Observe)",
            recommendations = "Descanse em um ambiente silencioso e escuro. Beba água e evite cafeína. Se a dor persistir por mais de 24 horas, procure um médico.",
            severityScore = 3
        )
        
        val answer1_2 = com.example.conectaribas.data.entities.DiagnosisAnswer(
            questionId = question1Id,
            answerText = "Febre alta",
            nextQuestionId = null,
            finalResult = "Orientações Iniciais",
            recommendations = "Mantenha-se hidratado e descanse. Use medicamentos antitérmicos conforme orientação médica. Se a febre persistir por mais de 3 dias ou ultrapassar 39°C, procure atendimento médico.",
            severityScore = 6
        )
        
        val answer1_3 = com.example.conectaribas.data.entities.DiagnosisAnswer(
            questionId = question1Id,
            answerText = "Dificuldade respiratória",
            nextQuestionId = null,
            finalResult = "Alerta de Emergência (Procure Ajuda Urgente)",
            recommendations = "PROCURE ATENDIMENTO MÉDICO IMEDIATAMENTE! Dificuldade respiratória pode indicar uma condição grave. Não espere.",
            severityScore = 10
        )
        
        val answer1_4 = com.example.conectaribas.data.entities.DiagnosisAnswer(
            questionId = question1Id,
            answerText = "Dor abdominal",
            nextQuestionId = null,
            finalResult = "Orientações Iniciais",
            recommendations = "Evite alimentos sólidos por algumas horas. Mantenha-se hidratado com líquidos claros. Se a dor for intensa ou persistir por mais de 6 horas, procure atendimento médico.",
            severityScore = 5
        )
        
        val answer1_5 = com.example.conectaribas.data.entities.DiagnosisAnswer(
            questionId = question1Id,
            answerText = "Náusea e vômito",
            nextQuestionId = null,
            finalResult = "Sintomas Leves (Observe)",
            recommendations = "Mantenha-se hidratado com pequenos goles de água ou soro caseiro. Evite alimentos sólidos por algumas horas. Se os vômitos persistirem por mais de 24 horas, procure um médico.",
            severityScore = 4
        )
        
        // Insere as respostas
        diagnosisDao.insertAnswer(answer1_1)
        diagnosisDao.insertAnswer(answer1_2)
        diagnosisDao.insertAnswer(answer1_3)
        diagnosisDao.insertAnswer(answer1_4)
        diagnosisDao.insertAnswer(answer1_5)
    }
}
