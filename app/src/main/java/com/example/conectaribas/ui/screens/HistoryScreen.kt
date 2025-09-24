package com.example.conectaribas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.conectaribas.data.entities.SymptomRecord
import com.example.conectaribas.ui.components.*
import com.example.conectaribas.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tela de histórico de sintomas
 * Exibe registros anteriores e permite exportação de dados
 */
@Composable
fun HistoryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    symptomHistory: List<SymptomRecord> = emptyList(),
    onClearHistory: () -> Unit = {}
) {
    var showClearDialog by remember { mutableStateOf(false) }
    
    AmazonBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header personalizado
            AmazonHeader(
                title = "Histórico de Sintomas",
                subtitle = "Consultas anteriores salvas"
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
            ) {
                // Card de estatísticas
                AmazonCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = VerdeAgua
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = "📋 Histórico de Consultas",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = CinzaEscuro
                            )
                            
                            Text(
                                text = "Total de ${symptomHistory.size} registros",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CinzaEscuro.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Botões de ação (somente limpar)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AmazonButton(
                        text = "🗑️ Limpar",
                        onClick = { showClearDialog = true },
                        modifier = Modifier.weight(1f),
                        variant = ButtonVariant.Accent
                    )
                }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Lista de registros
            if (symptomHistory.isEmpty()) {
                AmazonCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Lista vazia",
                            modifier = Modifier.size(64.dp),
                            tint = VerdeAgua.copy(alpha = 0.5f)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Nenhum registro encontrado",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CinzaEscuro
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Realize um autodiagnóstico para começar a criar seu histórico",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = CinzaEscuro.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(symptomHistory) { record ->
                        AmazonCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                            val status = when (record.diagnosis) {
                                "Alerta de Emergência (Procure Ajuda Urgente)" -> DiagnosisStatus.Emergency
                                "Orientações Iniciais" -> DiagnosisStatus.Moderate
                                else -> DiagnosisStatus.Light
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = record.patientName ?: "👤 Paciente",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = CinzaEscuro
                                    )
                                    Text(
                                        text = dateFormat.format(record.timestamp),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CinzaEscuro.copy(alpha = 0.6f)
                                    )
                                }
                                StatusIndicator(status = status)
                            }
                            if (status == DiagnosisStatus.Emergency) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.08f),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.error,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "EMERGÊNCIA",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else if (status == DiagnosisStatus.Moderate) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .background(
                                            color = AmareloAtencao.copy(alpha = 0.12f),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = AmareloAtencao,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = AmareloAtencao,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "ATENÇÃO",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = AmareloAtencao,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "🔍 Sintomas:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = VerdeAgua
                            )
                            Text(
                                text = record.symptoms,
                                style = MaterialTheme.typography.bodyMedium,
                                color = CinzaEscuro.copy(alpha = 0.8f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "📋 Diagnóstico:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = VerdeAgua
                            )
                            Text(
                                text = record.diagnosis,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = CinzaEscuro
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "💡 Recomendações:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = VerdeAgua
                            )
                            Text(
                                text = record.recommendations,
                                style = MaterialTheme.typography.bodyMedium,
                                color = CinzaEscuro.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Diálogo de confirmação para limpar
    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = {
                Text("Limpar Histórico")
            },
            text = {
                Text("Tem certeza que deseja remover todos os registros de sintomas? Esta ação não pode ser desfeita.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearHistory()
                        showClearDialog = false
                    }
                ) {
                    Text("Limpar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}


@Composable
fun HistorySymptomRecordCard(record: SymptomRecord) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
    
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Status do diagnóstico (usado em múltiplas seções abaixo)
        val status = when (record.diagnosis) {
            "Alerta de Emergência (Procure Ajuda Urgente)" -> DiagnosisStatus.Emergency
            "Orientações Iniciais" -> DiagnosisStatus.Moderate
            else -> DiagnosisStatus.Light
        }
        // Cabeçalho do registro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = record.patientName ?: "👤 Paciente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CinzaEscuro
                )
                
                Text(
                    text = dateFormat.format(record.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = CinzaEscuro.copy(alpha = 0.6f)
                )
            }
            
            // Status indicator com cores amazônicas
            StatusIndicator(status = status)
        }
        
        if (status == DiagnosisStatus.Emergency) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.error.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "EMERGÊNCIA",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        } else if (status == DiagnosisStatus.Moderate) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .background(
                        color = AmareloAtencao.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = AmareloAtencao,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = AmareloAtencao,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "ATENÇÃO",
                    style = MaterialTheme.typography.labelMedium,
                    color = AmareloAtencao,
                    fontWeight = FontWeight.Bold
                )
            }
        }
            
        Spacer(modifier = Modifier.height(12.dp))
        
        // Sintomas
        Text(
            text = "🔍 Sintomas:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = VerdeAgua
        )
        
        Text(
            text = record.symptoms,
            style = MaterialTheme.typography.bodyMedium,
            color = CinzaEscuro.copy(alpha = 0.8f)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Diagnóstico
        Text(
            text = "📋 Diagnóstico:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = VerdeAgua
        )
        
        Text(
            text = record.diagnosis,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = CinzaEscuro
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Recomendações
        Text(
            text = "💡 Recomendações:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = VerdeAgua
        )
        
        Text(
            text = record.recommendations,
            style = MaterialTheme.typography.bodyMedium,
            color = CinzaEscuro.copy(alpha = 0.8f)
        )
        }
    }
}

@Composable
fun ExportDialog(
    onDismiss: () -> Unit,
    onExport: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Exportar Histórico")
        },
        text = {
            Text("Escolha o formato para exportar o histórico de sintomas:")
        },
        confirmButton = {
            Column {
                Button(
                    onClick = { onExport("json") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text("JSON")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { onExport("csv") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = null
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text("CSV")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { onExport("qr") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text("QR Code")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}


