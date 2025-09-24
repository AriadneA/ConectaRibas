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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.conectaribas.data.repository.AppRepository
import com.example.conectaribas.data.AppDatabase
import com.example.conectaribas.data.PreferencesManager
import com.example.conectaribas.ui.viewmodels.SettingsViewModel
import androidx.compose.foundation.clickable
import com.example.conectaribas.ui.components.AmazonCard


/**
 * Tela de configurações da aplicação
 * Permite gerenciar preferências, dados e funcionalidades avançadas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { 
        AppRepository(
            database.symptomRecordDao(),
            database.diagnosisDao(),
            database.firstAidGuideDao()
        )
    }
    val viewModel: SettingsViewModel = viewModel { 
        SettingsViewModel(preferencesManager, repository) 
    }
    
    var showImportDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    
    // Observar estados do ViewModel
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val language by viewModel.language.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()
    
    
    
    // Mostrar loading se necessário
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Mostrar mensagens via Snackbar
    LaunchedEffect(message) {
        message?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearMessage()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToHome) {
                        Icon(Icons.Default.Home, "Início")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Cabeçalho
            AmazonCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Configurações do Conecta Ribas",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Gerencie suas preferências e dados",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Seção de Dados (sem exportação)
            SettingsSection(
                title = "Gerenciamento de Dados",
                icon = Icons.Default.Info
            ) {
                SettingsItem(
                    title = "Importar Dados",
                    subtitle = "Restaura dados de backup ou QR Code",
                    icon = Icons.Default.Check,
                    onClick = { showImportDialog = true }
                )
                
                SettingsItem(
                    title = "Limpar Dados",
                    subtitle = "Remove todos os registros e configurações",
                    icon = Icons.Default.Delete,
                    onClick = { showResetDialog = true },
                    isDestructive = true
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Seção de Preferências
            SettingsSection(
                title = "Preferências",
                icon = Icons.Default.Settings
            ) {
                SettingsItem(
                    title = "Notificações",
                    subtitle = "Receber lembretes de consultas",
                    icon = Icons.Default.Notifications,
                    trailing = {
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { 
                                android.util.Log.d("SettingsScreen", "Switch clicked: $it")
                                viewModel.updateNotifications(it) 
                            }
                        )
                    }
                )
                
                SettingsItem(
                    title = "Idioma",
                    subtitle = "Idioma da interface",
                    icon = Icons.Default.Settings,
                    onClick = { 
                        android.util.Log.d("SettingsScreen", "Language clicked")
                        showLanguageDialog = true 
                    },
                    trailing = {
                        Text(
                            text = language,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Seção de Informações
            SettingsSection(
                title = "Informações",
                icon = Icons.Default.Info
            ) {
                SettingsItem(
                    title = "Sobre o App",
                    subtitle = "Versão, desenvolvedores e licenças",
                    icon = Icons.Default.Info,
                    onClick = { showAboutDialog = true }
                )

                SettingsItem(
                    title = "Política de Privacidade",
                    subtitle = "Como seus dados são protegidos",
                    icon = Icons.Default.Info,
                    onClick = { 
                        // Implementar navegação para política de privacidade
                        // Por enquanto, mostrar um diálogo informativo
                    }
                )

                SettingsItem(
                    title = "Termos de Uso",
                    subtitle = "Condições para uso do aplicativo",
                    icon = Icons.Default.Info,
                    onClick = { 
                        // Implementar navegação para termos de uso
                        // Por enquanto, mostrar um diálogo informativo
                    }
                )
            }
            

        }
    }
    
    // Diálogos
    if (showImportDialog) {
        ImportDialog(
            onDismiss = { showImportDialog = false },
            onImport = { format ->
                viewModel.importData(format)
                showImportDialog = false
            }
        )
    }
    
    
    
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text("Limpar Todos os Dados")
            },
            text = {
                Text("Tem certeza que deseja remover todos os dados? Esta ação não pode ser desfeita e removerá:\n\n• Histórico de sintomas\n• Configurações personalizadas\n• Dados de backup")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearAllData()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Limpar Tudo")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
    
    if (showLanguageDialog) {
        LanguageSelectionDialog(
            onDismiss = { showLanguageDialog = false },
            onLanguageSelected = { languageCode ->
                viewModel.updateLanguage(languageCode)
            },
            availableLanguages = viewModel.getAvailableLanguages(),
            currentLanguage = preferencesManager.language
        )
    }
    
    if (showAboutDialog) {
        AboutDialog(
            onDismiss = { showAboutDialog = false }
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            content()
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    isDestructive: Boolean = false
) {
    val textColor = if (isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val iconColor = if (isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .let { modifier ->

                    if (onClick != null) {
                        modifier.clickable {
                            android.util.Log.d("SettingsItem", "Item clicked: $title")
                            onClick.invoke()
                        }
                    } else {
                        modifier
                    }
                }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (trailing != null) {
                trailing()
            } else if (onClick != null) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Navegar",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (onClick != null) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 40.dp, top = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}


@Composable
fun ImportDialog(
    onDismiss: () -> Unit,
    onImport: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Importar Dados")
        },
        text = {
            Text("Escolha como deseja importar seus dados:")
        },
        confirmButton = {
            Column {
                Button(
                    onClick = { onImport("qr") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text("QR Code")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { onImport("file") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text("Arquivo")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = { onImport("bluetooth") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text("Bluetooth")
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

@Composable
fun ExportSettingsDialog(
    onDismiss: () -> Unit,
    onExport: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Exportar Dados")
        },
        text = {
            Text("Escolha o formato para exportar seus dados:")
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
                        imageVector = Icons.Default.Check,
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
                        imageVector = Icons.Default.Check,
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

@Composable
fun LanguageSelectionDialog(
    onDismiss: () -> Unit,
    onLanguageSelected: (String) -> Unit,
    availableLanguages: List<Pair<String, String>>,
    currentLanguage: String = "pt"
) {
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Selecionar Idioma")
        },
        text = {
            Column {
                Text(
                    text = "Escolha o idioma da interface:",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                availableLanguages.forEach { (code, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedLanguage == code,
                            onClick = { selectedLanguage = code }
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    onLanguageSelected(selectedLanguage)
                    onDismiss()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun AboutDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Sobre o Conecta Ribas")
        },
        text = {
            Column {
                Text(
                    text = "Versão 1.0.0",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Aplicativo de primeiros socorros e autodiagnóstico para comunidades ribeirinhas.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Desenvolvido com foco em funcionalidade offline e interface intuitiva para usuários com pouca familiaridade com tecnologia.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "© 2025 Conecta Ribas. Todos os direitos reservados.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}
