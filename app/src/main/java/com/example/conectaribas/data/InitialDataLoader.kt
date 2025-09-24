package com.example.conectaribas.data

import android.content.Context
import com.example.conectaribas.data.entities.DiagnosisAnswer
import com.example.conectaribas.data.entities.DiagnosisQuestion
import com.example.conectaribas.data.entities.FirstAidGuide
import com.example.conectaribas.data.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Carregador de dados iniciais para o banco de dados
 * Popula o sistema com perguntas de diagnóstico, respostas e guias de primeiros socorros
 */
class InitialDataLoader(
    private val repository: AppRepository,
    private val context: Context
) {
    
    /**
     * Carrega todos os dados iniciais no banco de dados
     */
    fun loadInitialData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loadDiagnosisQuestions()
                loadFirstAidGuides()
            } catch (e: Exception) {
                // Log do erro (em produção, usar um sistema de logging adequado)
                e.printStackTrace()
            }
        }
    }
    
    /**
     * Carrega as perguntas e respostas do sistema de diagnóstico
     */
    private suspend fun loadDiagnosisQuestions() {
        // Primeira pergunta - Seleção de sintomas
        val question1 = DiagnosisQuestion(
            id = 1,
            questionText = "Qual é o seu sintoma principal?",
            category = "Sintomas",
            questionOrder = 1,
            previousQuestionId = null
        )
        val question1Id = repository.insertDiagnosisQuestion(question1)
        
        // Respostas para a primeira pergunta
        val answers1 = listOf(
            DiagnosisAnswer(
                questionId = question1Id,
                answerText = "Dor de cabeça",
                nextQuestionId = 2,
                severityScore = 3
            ),
            DiagnosisAnswer(
                questionId = question1Id,
                answerText = "Febre",
                nextQuestionId = 2,
                severityScore = 4
            ),
            DiagnosisAnswer(
                questionId = question1Id,
                answerText = "Dor abdominal",
                nextQuestionId = 2,
                severityScore = 5
            ),
            DiagnosisAnswer(
                questionId = question1Id,
                answerText = "Dificuldade para respirar",
                finalResult = "Alerta de Emergência (Procure Ajuda Urgente)",
                recommendations = "Procure ajuda médica imediatamente. Dificuldade para respirar pode indicar uma situação grave.",
                severityScore = 10
            ),
            DiagnosisAnswer(
                questionId = question1Id,
                answerText = "Náusea ou vômito",
                nextQuestionId = 2,
                severityScore = 4
            ),
            DiagnosisAnswer(
                questionId = question1Id,
                answerText = "Tontura",
                nextQuestionId = 2,
                severityScore = 3
            )
        )
        
        answers1.forEach { repository.insertDiagnosisAnswer(it) }
        
        // Segunda pergunta - Nível de dor
        val question2 = DiagnosisQuestion(
            id = 2,
            questionText = "Como você avalia a intensidade da dor?",
            category = "Dor",
            questionOrder = 2,
            previousQuestionId = question1Id
        )
        val question2Id = repository.insertDiagnosisQuestion(question2)
        
        // Respostas para a segunda pergunta
        val answers2 = listOf(
            DiagnosisAnswer(
                questionId = question2Id,
                answerText = "Sem dor",
                nextQuestionId = 3,
                severityScore = 1
            ),
            DiagnosisAnswer(
                questionId = question2Id,
                answerText = "Dor leve",
                nextQuestionId = 3,
                severityScore = 2
            ),
            DiagnosisAnswer(
                questionId = question2Id,
                answerText = "Dor moderada",
                nextQuestionId = 3,
                severityScore = 4
            ),
            DiagnosisAnswer(
                questionId = question2Id,
                answerText = "Dor intensa",
                nextQuestionId = 3,
                severityScore = 7
            ),
            DiagnosisAnswer(
                questionId = question2Id,
                answerText = "Dor insuportável",
                finalResult = "Alerta de Emergência (Procure Ajuda Urgente)",
                recommendations = "Procure ajuda médica imediatamente. Dor insuportável pode indicar uma condição grave.",
                severityScore = 10
            )
        )
        
        answers2.forEach { repository.insertDiagnosisAnswer(it) }
        
        // Terceira pergunta - Duração dos sintomas
        val question3 = DiagnosisQuestion(
            id = 3,
            questionText = "Há quanto tempo você está sentindo isso?",
            category = "Duração",
            questionOrder = 3,
            previousQuestionId = question2Id
        )
        val question3Id = repository.insertDiagnosisQuestion(question3)
        
        // Respostas para a terceira pergunta
        val answers3 = listOf(
            DiagnosisAnswer(
                questionId = question3Id,
                answerText = "Menos de 1 hora",
                nextQuestionId = 4,
                severityScore = 2
            ),
            DiagnosisAnswer(
                questionId = question3Id,
                answerText = "1 a 6 horas",
                nextQuestionId = 4,
                severityScore = 3
            ),
            DiagnosisAnswer(
                questionId = question3Id,
                answerText = "6 a 24 horas",
                nextQuestionId = 4,
                severityScore = 4
            ),
            DiagnosisAnswer(
                questionId = question3Id,
                answerText = "1 a 3 dias",
                nextQuestionId = 4,
                severityScore = 6
            ),
            DiagnosisAnswer(
                questionId = question3Id,
                answerText = "Mais de 3 dias",
                nextQuestionId = 4,
                severityScore = 8
            )
        )
        
        answers3.forEach { repository.insertDiagnosisAnswer(it) }
        
        // Quarta pergunta - Impacto nas atividades
        val question4 = DiagnosisQuestion(
            id = 4,
            questionText = "Como isso afeta suas atividades diárias?",
            category = "Impacto",
            questionOrder = 4,
            previousQuestionId = question3Id
        )
        val question4Id = repository.insertDiagnosisQuestion(question4)
        
        // Respostas para a quarta pergunta
        val answers4 = listOf(
            DiagnosisAnswer(
                questionId = question4Id,
                answerText = "Não interfere nas atividades",
                finalResult = "Sintomas Leves (Observe)",
                recommendations = "Monitore seus sintomas por 24-48 horas. Descanse adequadamente e procure ajuda se os sintomas piorarem.",
                severityScore = 2
            ),
            DiagnosisAnswer(
                questionId = question4Id,
                answerText = "Interfere um pouco",
                finalResult = "Sintomas Leves (Observe)",
                recommendations = "Monitore seus sintomas. Reduza atividades intensas e procure ajuda se persistir por mais de 24 horas.",
                severityScore = 3
            ),
            DiagnosisAnswer(
                questionId = question4Id,
                answerText = "Interfere significativamente",
                finalResult = "Orientações Iniciais",
                recommendations = "Consulte um médico nas próximas 24 horas. Evite atividades que piorem os sintomas.",
                severityScore = 6
            ),
            DiagnosisAnswer(
                questionId = question4Id,
                answerText = "Impossibilita atividades",
                finalResult = "Orientações Iniciais",
                recommendations = "Consulte um médico nas próximas 12 horas. Mantenha repouso e evite esforços.",
                severityScore = 8
            ),
            DiagnosisAnswer(
                questionId = question4Id,
                answerText = "Sintomas muito graves",
                finalResult = "Alerta de Emergência (Procure Ajuda Urgente)",
                recommendations = "Procure ajuda médica imediatamente. Não dirija sozinho e mantenha-se calmo.",
                severityScore = 10
            )
        )
        
        answers4.forEach { repository.insertDiagnosisAnswer(it) }
    }
    
    /**
     * Carrega os guias de primeiros socorros
     */
    private suspend fun loadFirstAidGuides() {
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
                        "6. Transporte imediatamente para hospital\n" +
                        "7. Se possível, identifique o tipo de cobra\n" +
                        "8. Monitore sinais vitais (respiração, pulso)",
                warningSigns = "• Dificuldade para respirar\n• Inchaço rápido e extenso\n• Dor intensa e progressiva\n• Alteração da consciência\n• Paralisia muscular\n• Alteração da visão\n• Náusea e vômito intensos",
                isEmergency = true,
                displayOrder = 1
            ),
            FirstAidGuide(
                id = 2,
                title = "Cortes e Ferimentos",
                category = "Ferimentos",
                description = "Como tratar cortes e ferimentos menores",
                content = "1. Lave as mãos com água e sabão\n" +
                        "2. Limpe a ferida com água limpa corrente\n" +
                        "3. Aplique pressão direta para estancar sangramento\n" +
                        "4. Aplique antisséptico se disponível\n" +
                        "5. Cubra com curativo limpo e seco\n" +
                        "6. Troque o curativo diariamente\n" +
                        "7. Monitore sinais de infecção\n" +
                        "8. Mantenha a ferida seca e limpa",
                warningSigns = "• Sangramento que não para após 10 minutos de pressão\n• Ferida muito profunda ou extensa\n• Sinais de infecção (vermelhidão, inchaço, pus)\n• Ferida causada por objeto sujo ou enferrujado\n• Ferida no rosto ou articulações",
                isEmergency = false,
                displayOrder = 2
            ),
            FirstAidGuide(
                id = 3,
                title = "Queimaduras",
                category = "Emergência",
                description = "Primeiros socorros para queimaduras",
                content = "1. Resfrie a área queimada com água fria por 10-20 minutos\n" +
                        "2. Não use gelo ou água muito gelada\n" +
                        "3. Não estoure bolhas\n" +
                        "4. Cubra com pano limpo e úmido\n" +
                        "5. Não aplique cremes, pomadas ou manteiga\n" +
                        "6. Remova anéis e objetos apertados\n" +
                        "7. Mantenha a pessoa aquecida\n" +
                        "8. Procure ajuda médica se necessário",
                warningSigns = "• Queimaduras no rosto, mãos, pés ou genitais\n• Queimaduras muito extensas (maior que a palma da mão)\n• Queimaduras de terceiro grau (brancas ou carbonizadas)\n• Queimaduras por produtos químicos\n• Queimaduras por eletricidade",
                isEmergency = true,
                displayOrder = 3
            ),
            FirstAidGuide(
                id = 4,
                title = "Crises de Asma",
                category = "Emergência",
                description = "Como ajudar em crises de asma",
                content = "1. Mantenha a pessoa calma e tranquila\n" +
                        "2. Ajude-a a sentar-se em posição confortável\n" +
                        "3. Use inalador de resgate se prescrito\n" +
                        "4. Monitore a respiração e o nível de consciência\n" +
                        "5. Mantenha o ar circulando (janelas abertas)\n" +
                        "6. Ofereça água se a pessoa conseguir beber\n" +
                        "7. Acompanhe até a crise passar\n" +
                        "8. Procure ajuda médica se piorar",
                warningSigns = "• Dificuldade extrema para respirar\n• Lábios ou unhas azulados\n• Confusão mental ou sonolência\n• Incapacidade de falar frases completas\n• Uso excessivo dos músculos do pescoço\n• Respiração muito rápida ou lenta",
                isEmergency = true,
                displayOrder = 4
            ),
            FirstAidGuide(
                id = 5,
                title = "Diarreia Intensa",
                category = "Doenças",
                description = "Como tratar diarreia e desidratação",
                content = "1. Mantenha a pessoa hidratada com água\n" +
                        "2. Ofereça soro caseiro ou soluções de reidratação\n" +
                        "3. Evite alimentos sólidos por algumas horas\n" +
                        "4. Monitore sinais de desidratação\n" +
                        "5. Mantenha repouso e evite esforços\n" +
                        "6. Ofereça alimentos leves quando tolerar\n" +
                        "7. Mantenha boa higiene pessoal\n" +
                        "8. Procure ajuda se persistir por mais de 2 dias",
                warningSigns = "• Desidratação severa (boca seca, olhos fundos)\n• Sangue nas fezes\n• Febre alta (acima de 38°C)\n• Dor abdominal intensa e persistente\n• Vômitos que impedem hidratação\n• Alteração da consciência\n• Sinais de choque",
                isEmergency = false,
                displayOrder = 5
            )
        )
        
        guides.forEach { guide ->
            repository.insertFirstAidGuide(guide)
        }
    }
}
