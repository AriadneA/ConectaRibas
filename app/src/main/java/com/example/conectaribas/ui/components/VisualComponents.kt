package com.example.conectaribas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.conectaribas.ui.theme.*

/**
 * Componentes visuais personalizados para o ConectaRibas
 * Inspirados na natureza amazônica com design moderno e responsivo
 */

/**
 * Botão principal com gradiente amazônico e efeitos interativos
 */
@Composable
fun AmazonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.Primary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val gradientColors = when (variant) {
        ButtonVariant.Primary -> listOf(VerdeAgua, VerdeFolha)
        ButtonVariant.Secondary -> listOf(AzulClaro, VerdeAgua)
        ButtonVariant.Accent -> listOf(LaranjaSuave, VerdeFolha)
    }
    
    val elevation = if (isPressed) 2.dp else 8.dp
    val scale = if (isPressed) 0.98f else 1f
    
    Box(
        modifier = modifier
            .scale(scale)
            .shadow(elevation, RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(gradientColors),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { if (enabled) onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Branco,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Card com gradiente de fundo amazônico
 */
@Composable
fun AmazonCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Branco, Creme),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}

/**
 * Header com gradiente amazônico
 */
@Composable
fun AmazonHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(VerdeAgua, VerdeFolha, AzulClaro)
                )
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = title,
                color = Branco,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            subtitle?.let {
                Text(
                    text = it,
                    color = Branco.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Indicador de status com cores amazônicas
 */
@Composable
fun StatusIndicator(
    status: DiagnosisStatus,
    modifier: Modifier = Modifier
) {
    val (color, text) = when (status) {
        DiagnosisStatus.Light -> VerdeSucesso to "Sintomas Leves"
        DiagnosisStatus.Moderate -> AmareloAtencao to "Orientações Iniciais"
        DiagnosisStatus.Emergency -> VermelhoEmergencia to "Alerta de Emergência"
    }
    
    Row(
        modifier = modifier
            .background(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, RoundedCornerShape(6.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = color,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Ícone de navegação com efeito hover
 */
@Composable
fun NavigationIcon(
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val backgroundColor = if (isPressed) VerdeAgua.copy(alpha = 0.1f) else Color.Transparent
    
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

/**
 * Gradiente de fundo amazônico para telas
 */
@Composable
fun AmazonBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Creme, AzulClaro.copy(alpha = 0.1f)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        content()
    }
}

/**
 * Enums para variantes de componentes
 */
enum class ButtonVariant {
    Primary, Secondary, Accent
}

enum class DiagnosisStatus {
    Light, Moderate, Emergency
}
