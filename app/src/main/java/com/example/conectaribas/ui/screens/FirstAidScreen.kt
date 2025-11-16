package com.example.conectaribas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Tela de primeiros socorros
 * Lista os guias disponíveis e permite visualizar detalhes
 */
@Composable
fun FirstAidScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    var selectedGuide by remember { mutableStateOf<FirstAidGuide?>(null) }
    
    AmazonBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AmazonHeader(
                title = "Primeiros Socorros",
                subtitle = "Guias para emergências e cuidados"
            )
            
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
            
            if (selectedGuide == null) {
                FirstAidListScreen(
                    onGuideSelected = { guide -> selectedGuide = guide },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            } else {
                FirstAidDetailScreen(
                    guide = selectedGuide!!,
                    onBackToList = { selectedGuide = null },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FirstAidListScreen(
    onGuideSelected: (FirstAidGuide) -> Unit,
    modifier: Modifier = Modifier
) {
    val guides = listOf(
        FirstAidGuide(
            id = 1,
            title = "Picadas de Cobra",
            category = "Emergência",
            description = "Primeiros socorros para picadas de cobra",
            content = "1. Mantenha a vítima calma e imóvel\n" +
                    "2. Remova anéis, relógios e roupas apertadas\n" +
                    "3. Mantenha a área da picada abaixo do nível do coração\n" +
                    "4. Não aplique torniquete\n" +
                    "5. Não corte ou chupe a ferida\n" +
                    "6. Transporte imediatamente para hospital",
            warningSigns = "• Dificuldade para respirar\n• Inchaço rápido\n• Dor intensa\n• Alteração da consciência",
            isEmergency = true,
            displayOrder = 1
        ),
        FirstAidGuide(
            id = 2,
            title = "Cortes e Ferimentos",
            category = "Ferimentos",
            description = "Como tratar cortes e ferimentos menores",
            content = "1. Lave as mãos com água e sabão\n" +
                    "2. Limpe a ferida com água limpa\n" +
                    "3. Aplique pressão para estancar sangramento\n" +
                    "4. Aplique antisséptico se disponível\n" +
                    "5. Cubra com curativo limpo\n" +
                    "6. Troque o curativo diariamente",
            warningSigns = "• Sangramento que não para\n• Ferida muito profunda\n• Sinais de infecção",
            isEmergency = false,
            displayOrder = 2
        ),
        FirstAidGuide(
            id = 3,
            title = "Queimaduras",
            category = "Emergência",
            description = "Primeiros socorros para queimaduras",
            content = "1. Resfrie a área queimada com água fria por 10-20 minutos\n" +
                    "2. Não use gelo\n" +
                    "3. Não estoure bolhas\n" +
                    "4. Cubra com pano limpo e úmido\n" +
                    "5. Não aplique cremes ou pomadas\n" +
                    "6. Procure ajuda médica se necessário",
            warningSigns = "• Queimaduras no rosto, mãos ou genitais\n• Queimaduras muito extensas\n• Queimaduras de terceiro grau",
            isEmergency = true,
            displayOrder = 3
        ),
        FirstAidGuide(
            id = 4,
            title = "Crises de Asma",
            category = "Emergência",
            description = "Como ajudar em crises de asma",
            content = "1. Mantenha a pessoa calma\n" +
                    "2. Ajude-a a sentar-se em posição confortável\n" +
                    "3. Use inalador se prescrito\n" +
                    "4. Monitore a respiração\n" +
                    "5. Mantenha o ar circulando\n" +
                    "6. Procure ajuda médica se piorar",
            warningSigns = "• Dificuldade extrema para respirar\n• Lábios ou unhas azulados\n• Confusão mental",
            isEmergency = true,
            displayOrder = 4
        ),
        FirstAidGuide(
            id = 5,
            title = "Diarreia Intensa",
            category = "Doenças",
            description = "Como tratar diarreia e desidratação",
            content = "1. Mantenha a pessoa hidratada\n" +
                    "2. Ofereça soro caseiro ou água\n" +
                    "3. Evite alimentos sólidos por algumas horas\n" +
                    "4. Monitore sinais de desidratação\n" +
                    "5. Mantenha repouso\n" +
                    "6. Procure ajuda se persistir",
            warningSigns = "• Desidratação severa\n• Sangue nas fezes\n• Febre alta\n• Dor abdominal intensa",
            isEmergency = false,
            displayOrder = 5
        )
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AmazonCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(38.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Guias de Primeiros Socorros",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Instruções para emergências médicas",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    //color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Lista de guias
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(guides) { guide ->
                FirstAidGuideCard(
                    guide = guide,
                    onClick = { onGuideSelected(guide) }
                )
            }
        }
    }
}

@Composable
fun FirstAidGuideCard(
    guide: FirstAidGuide,
    onClick: () -> Unit
) {
    AmazonCard(
        modifier = Modifier
            .fillMaxWidth()
            .let { base ->
                if (guide.isEmergency) {
                    base.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else base
            }
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (guide.isEmergency) {
                    Icons.Default.Warning
                } else {
                    Icons.Default.Info
                },
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = if (guide.isEmergency) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = guide.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = guide.description,
                    style = MaterialTheme.typography.bodyMedium,
                    //color = MaterialTheme.colorScheme.onSurface
                )
                
                if (guide.isEmergency) {
                    Spacer(modifier = Modifier.height(6.dp))
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
                }
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ver detalhes",
                //tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun FirstAidDetailScreen(
    guide: FirstAidGuide,
    onBackToList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Botão voltar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBackToList) {
                Icon(Icons.Default.ArrowBack, "Voltar à lista")
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = "Voltar à lista",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
    // Título do guia
    AmazonCard(
        modifier = Modifier
            .fillMaxWidth()
            .let { base ->
                if (guide.isEmergency) {
                    base.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else base
            }
    ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (guide.isEmergency) {
                        Icons.Default.Warning
                    } else {
                        Icons.Default.Info
                    },
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = if (guide.isEmergency) {
                        MaterialTheme.colorScheme.error
                    } else {
                    MaterialTheme.colorScheme.primary
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = guide.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
            if (guide.isEmergency) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.error,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "EMERGÊNCIA MÉDICA",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            }
    }
        
        Spacer(modifier = Modifier.height(24.dp))
        
    // Conteúdo do guia
    AmazonCard(
        modifier = Modifier.fillMaxWidth()
    ) {
            Column(
            modifier = Modifier
            ) {
                Text(
                    text = "Instruções Passo a Passo:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = guide.content,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
                )
            }
    }
        
        Spacer(modifier = Modifier.height(24.dp))
        
    // Sinais de alerta
    AmazonCard(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
            Column(
            modifier = Modifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Sinais de alerta",
                        tint = MaterialTheme.colorScheme.error
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "Sinais de Alerta - Procure Ajuda Médica:",
                        style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = guide.warningSigns,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2
                )
            }
    }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Botão de ação
        Button(
            onClick = onBackToList,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text("Voltar à Lista de Guias")
        }
    }
}

// Classe de dados para os guias (simulando a entidade do banco)
data class FirstAidGuide(
    val id: Long,
    val title: String,
    val category: String,
    val description: String,
    val content: String,
    val warningSigns: String,
    val isEmergency: Boolean,
    val displayOrder: Int
)
