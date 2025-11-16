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
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Brush
import com.example.conectaribas.R

/**
 * Tela de início da aplicação com design amazônico
 * Apresenta as funcionalidades principais com visual moderno e responsivo
 */
@Composable
fun HomeScreen(
    onNavigateToDiagnosis: () -> Unit,
    onNavigateToFirstAid: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    AmazonBackground {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header personalizado com gradiente amazônico e ícone
            AmazonHeaderWithIcon(
                title = "Conecta Ribas",
                subtitle = "Sua saúde conectada à natureza amazônica"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AmazonCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Primeiros Socorros e Autodiagnóstico",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = CinzaEscuro
                    )
/*
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Cuidando das comunidades ribeirinhas",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = CinzaEscuro.copy(alpha = 0.7f)
                    )

 */
                }

                Spacer(modifier = Modifier.height(24.dp))


                AmazonButton(
                    text = "🔍 Autodiagnóstico",
                    onClick = onNavigateToDiagnosis,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.Primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                AmazonButton(
                    text = "🚑 Guia de Primeiros Socorros",
                    onClick = onNavigateToFirstAid,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.Secondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                AmazonButton(
                    text = "📋 Histórico de Consultas",
                    onClick = onNavigateToHistory,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.Accent
                )

                Spacer(modifier = Modifier.height(16.dp))

                AmazonButton(
                    text = "⚙️ Configurações",
                    onClick = onNavigateToSettings,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.Secondary
                )

                Spacer(modifier = Modifier.height(32.dp))


                AmazonCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Aviso",
                            tint = LaranjaSuave,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Importante",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CinzaEscuro
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Este aplicativo não substitui o atendimento médico profissional. Em casos de emergência, procure ajuda médica imediatamente.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CinzaEscuro.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                AmazonCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Natureza",
                            tint = VerdeFolha,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Conectado à Amazônia",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CinzaEscuro
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Desenvolvido especialmente para comunidades ribeirinhas, respeitando a sabedoria tradicional e conectando com a natureza amazônica.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CinzaEscuro.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
fun AmazonHeaderWithIcon(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        VerdeFolha,
                        VerdeFolha.copy(alpha = 0.8f)
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                /*Icon(
                    painter = painterResource(id = R.drawable.icone_app),
                    contentDescription = "Ícone do app",
                    modifier = Modifier.size(32.dp)

                )
*/
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}