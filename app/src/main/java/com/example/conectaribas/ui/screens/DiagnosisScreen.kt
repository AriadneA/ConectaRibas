package com.example.conectaribas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.conectaribas.ui.components.*
import com.example.conectaribas.ui.theme.*

/**
 * Tela de autodiagnóstico guiado
 * Apresenta perguntas sequenciais para orientar o usuário
 */
@Composable
fun DiagnosisScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onSaveDiagnosis: (String, String, String, String) -> Unit = { _, _, _, _ -> }
) {
    var currentStep by remember { mutableStateOf(0) }
    var selectedAnswers by remember { mutableStateOf(mutableListOf<String>()) }
    var patientName by remember { mutableStateOf("") }
    
    AmazonBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header personalizado com navegação
            AmazonHeader(
                title = "Autodiagnóstico Guiado",
                subtitle = "Responda as perguntas para receber orientações"
            )
            
            // Botões de navegação
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NavigationIcon(
                    icon = { Icon(Icons.Default.ArrowBack, "Voltar", tint = Branco) },
                    onClick = onNavigateBack
                )
                
                NavigationIcon(
                    icon = { Icon(Icons.Default.Home, "Início", tint = Branco) },
                    onClick = onNavigateToHome
                )
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Indicador de progresso personalizado
                AmazonCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Passo ${currentStep + 1} de 6",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CinzaEscuro
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    LinearProgressIndicator(
                        progress = (currentStep + 1) / 6f,
                        modifier = Modifier.fillMaxWidth(),
                        color = VerdeAgua,
                        trackColor = VerdeAgua.copy(alpha = 0.3f)
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                when (currentStep) {
                    0 -> PatientNameStep(
                        patientName = patientName,
                        onNameChange = { patientName = it },
                        onNext = { currentStep++ }
                    )
                    1 -> SymptomSelectionStep(
                        onNext = { symptoms ->
                            selectedAnswers.add(symptoms)
                            currentStep++
                        }
                    )
                    2 -> PainLevelStep(
                        onNext = { painLevel ->
                            selectedAnswers.add(painLevel)
                            currentStep++
                        }
                    )
                    3 -> DurationStep(
                        onNext = { duration ->
                            selectedAnswers.add(duration)
                            currentStep++
                        }
                    )
                    4 -> SeverityStep(
                        onNext = { severity ->
                            selectedAnswers.add(severity)
                            currentStep++
                        }
                    )
                    5 -> ResultStep(
                        answers = selectedAnswers,
                        patientName = patientName,
                        onRestart = {
                            currentStep = 0
                            selectedAnswers.clear()
                            patientName = ""
                        },
                        onFinish = {
                            // Salva o diagnóstico antes de finalizar
                            val diagnosis = analyzeSymptoms(selectedAnswers)
                            val symptomsText = selectedAnswers.joinToString(", ")
                            onSaveDiagnosis(patientName, symptomsText, diagnosis.title, diagnosis.recommendations.joinToString("\n"))
                            onNavigateToHome()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PatientNameStep(
    patientName: String,
    onNameChange: (String) -> Unit,
    onNext: () -> Unit
) {
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "👤 Informações do Paciente",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = CinzaEscuro
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Por favor, informe o nome do paciente para o diagnóstico:",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = CinzaEscuro.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = patientName,
            onValueChange = onNameChange,
            label = { Text("Nome do Paciente") },
            placeholder = { Text("Digite o nome completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = VerdeAgua,
                unfocusedBorderColor = VerdeAgua.copy(alpha = 0.5f)
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        AmazonButton(
            text = "Continuar",
            onClick = onNext,
            enabled = patientName.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Primary
        )
    }
}

@Composable
fun SymptomSelectionStep(onNext: (String) -> Unit) {
    val symptoms = listOf(
        "Dor de cabeça",
        "Febre",
        "Dor abdominal", 
        "Dificuldade para respirar",
        "Náusea ou vômito",
        "Tontura"
    )
    
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "🔍 Qual é o seu sintoma principal?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = CinzaEscuro
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        symptoms.forEach { symptomName ->
            AmazonButton(
                text = symptomName,
                onClick = { onNext(symptomName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                variant = ButtonVariant.Secondary
            )
            
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun PainLevelStep(onNext: (String) -> Unit) {
    val painLevels = listOf(
        "Sem dor",
        "Dor leve", 
        "Dor moderada",
        "Dor intensa",
        "Dor insuportável"
    )
    
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "😣 Como você avalia a intensidade da dor?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = CinzaEscuro
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        painLevels.forEach { levelName ->
            AmazonButton(
                text = levelName,
                onClick = { onNext(levelName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                variant = ButtonVariant.Secondary
            )
            
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun DurationStep(onNext: (String) -> Unit) {
    val durations = listOf(
        "Menos de 1 hora",
        "1 a 6 horas",
        "6 a 24 horas",
        "1 a 3 dias",
        "Mais de 3 dias"
    )
    
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "⏰ Há quanto tempo você está sentindo isso?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = CinzaEscuro
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        durations.forEach { durationName ->
            AmazonButton(
                text = durationName,
                onClick = { onNext(durationName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                variant = ButtonVariant.Accent
            )
            
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SeverityStep(onNext: (String) -> Unit) {
    val severityOptions = listOf(
        "Não interfere nas atividades",
        "Interfere um pouco",
        "Interfere significativamente",
        "Impossibilita atividades",
        "Sintomas muito graves"
    )
    
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "📊 Como isso afeta suas atividades diárias?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = CinzaEscuro
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        severityOptions.forEach { optionName ->
            AmazonButton(
                text = optionName,
                onClick = { onNext(optionName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                variant = ButtonVariant.Primary
            )
            
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ResultStep(
    answers: List<String>,
    patientName: String,
    onRestart: () -> Unit,
    onFinish: () -> Unit
) {
    val diagnosis = analyzeSymptoms(answers)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Status indicator com cores amazônicas
        val status = when (diagnosis.severity) {
            "emergency" -> DiagnosisStatus.Emergency
            "urgent" -> DiagnosisStatus.Moderate
            else -> DiagnosisStatus.Light
        }
        
        StatusIndicator(
            status = status,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        AmazonCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = diagnosis.title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = CinzaEscuro
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = diagnosis.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = CinzaEscuro.copy(alpha = 0.8f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Informações do paciente
        if (patientName.isNotBlank()) {
            AmazonCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Paciente",
                        tint = VerdeAgua,
                        modifier = Modifier.size(24.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = "Paciente: $patientName",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CinzaEscuro
                        )
                        
                        Text(
                            text = "✅ Diagnóstico salvo no histórico",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VerdeSucesso
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        AmazonCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "📋 Recomendações:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CinzaEscuro
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            diagnosis.recommendations.forEach { recommendation ->
                Row(
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = VerdeSucesso
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = recommendation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CinzaEscuro.copy(alpha = 0.8f)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AmazonButton(
                text = "🔄 Novo Diagnóstico",
                onClick = onRestart,
                modifier = Modifier.weight(1f),
                variant = ButtonVariant.Secondary
            )
            
            AmazonButton(
                text = "✅ Finalizar",
                onClick = onFinish,
                modifier = Modifier.weight(1f),
                variant = ButtonVariant.Primary
            )
        }
    }
}

data class DiagnosisResult(
    val severity: String,
    val title: String,
    val description: String,
    val recommendations: List<String>
)

fun analyzeSymptoms(answers: List<String>): DiagnosisResult {
    // Lógica simplificada de análise de sintomas baseada em textos em português
    val hasSeverePain = answers.contains("Dor intensa") || answers.contains("Dor insuportável")
    val hasBreathingDifficulty = answers.contains("Dificuldade para respirar")
    val hasLongDuration = answers.contains("Mais de 3 dias")
    val hasSevereInterference = answers.contains("Impossibilita atividades") || answers.contains("Sintomas muito graves")
    
    return when {
        hasBreathingDifficulty || hasSeverePain -> {
            DiagnosisResult(
                severity = "emergency",
                title = "🚨 Alerta de Emergência!",
                description = "Seus sintomas indicam uma situação que pode ser grave e requer atenção médica imediata.",
                recommendations = listOf(
                    "🚑 Procure ajuda médica imediatamente",
                    "🚗 Não dirija sozinho",
                    "😌 Mantenha-se calmo e em repouso",
                    "📞 Ligue para emergência se necessário"
                )
            )
        }
        hasSevereInterference || hasLongDuration -> {
            DiagnosisResult(
                severity = "urgent",
                title = "⚠️ Orientações Iniciais",
                description = "Seus sintomas requerem atenção médica em breve para uma avaliação adequada.",
                recommendations = listOf(
                    "👨‍⚕️ Consulte um médico nas próximas 24 horas",
                    "👀 Monitore seus sintomas constantemente",
                    "🚫 Evite atividades que piorem os sintomas",
                    "💊 Tome medicamentos apenas com orientação médica"
                )
            )
        }
        else -> {
            DiagnosisResult(
                severity = "mild",
                title = "✅ Sintomas Leves (Observe)",
                description = "Seus sintomas são leves e podem ser monitorados em casa.",
                recommendations = listOf(
                    "👀 Monitore seus sintomas por 24-48 horas",
                    "😴 Descanse adequadamente",
                    "💧 Mantenha-se hidratado",
                    "🏥 Procure ajuda se os sintomas piorarem"
                )
            )
        }
    }
}
