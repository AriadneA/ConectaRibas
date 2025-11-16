# Conecta Ribas - Aplicativo de Primeiros Socorros e Autodiagnóstico

## 📱 Sobre o Projeto

O **Conecta Ribas** é um aplicativo Android desenvolvido especificamente para comunidades ribeirinhas com acesso limitado a serviços de saúde. Seu objetivo principal é ser uma ferramenta de primeiros socorros e autodiagnóstico preliminar, funcionando **totalmente offline**.

### 🎯 Objetivos

- Fornecer orientações de primeiros socorros para situações de emergência
- Realizar autodiagnóstico guiado através de perguntas e respostas
- Funcionar sem necessidade de conexão com a internet
- Interface simples e intuitiva para usuários com pouca familiaridade com tecnologia
- Armazenar histórico de sintomas para acompanhamento

## 🏗️ Arquitetura do Projeto

### Tecnologias Utilizadas

- **Kotlin** - Linguagem principal de desenvolvimento
- **Jetpack Compose** - Framework de UI moderna
- **Room Database** - Banco de dados local SQLite
- **Navigation Compose** - Navegação entre telas
- **ViewModel** - Gerenciamento de estado da aplicação
- **Coroutines** - Programação assíncrona

### Estrutura do Projeto

```
app/src/main/java/com/example/conectaribas/
├── data/                           # Camada de dados
│   ├── entities/                   # Entidades do banco de dados
│   ├── dao/                        # Data Access Objects
│   ├── repository/                 # Repositório de dados
│   └── util/                       # Utilitários (conversores)
├── ui/                            # Camada de interface
│   ├── screens/                    # Telas da aplicação
│   ├── theme/                      # Tema e estilos
│   └── viewmodel/                  # ViewModels
└── MainActivity.kt                 # Atividade principal
```

## 🚀 Funcionalidades Principais

### 1. Tela de Início
- **Autodiagnóstico Guiado**: Inicia o processo de diagnóstico
- **Guia de Primeiros Socorros**: Acesso aos guias de emergência
- **Histórico**: Visualização de consultas anteriores
- **Configurações**: Gerenciamento de preferências

### 2. Autodiagnóstico Guiado
O sistema utiliza uma **árvore de decisão** baseada em perguntas sequenciais:

1. **Seleção de Sintomas**: Escolha do sintoma principal
2. **Nível de Dor**: Avaliação da intensidade
3. **Duração**: Tempo desde o início dos sintomas
4. **Impacto nas Atividades**: Como afeta a rotina diária
5. **Resultado**: Diagnóstico e recomendações

#### Resultados Possíveis:
- 🟢 **Sintomas Leves (Observe)**: Monitoramento e repouso
- 🟡 **Orientações Iniciais**: Acompanhamento médico em 24h
- 🔴 **Alerta de Emergência**: Procure ajuda médica imediatamente

### 3. Guias de Primeiros Socorros
Guias detalhados para situações de emergência:

- 🐍 **Picadas de Cobra**: Procedimentos de emergência
- 🔪 **Cortes e Ferimentos**: Tratamento de feridas
- 🔥 **Queimaduras**: Primeiros socorros para queimaduras
- 😮‍💨 **Crises de Asma**: Como ajudar em crises respiratórias
- 💩 **Diarreia Intensa**: Tratamento e hidratação

Cada guia inclui:
- Instruções passo a passo
- Sinais de alerta
- Indicações de quando procurar ajuda médica

### 4. Histórico de Sintomas
- Registro de todas as consultas realizadas
- Data, sintomas, diagnóstico e recomendações
- Funcionalidade de exportação em múltiplos formatos
- Busca e filtros por período

### 5. Configurações
- **Gerenciamento de Dados**: Exportar, importar e limpar dados
- **Preferências**: Notificações, tema escuro, idioma
- **Informações**: Sobre o app, política de privacidade
- **Estatísticas**: Uso do banco de dados

## 💾 Banco de Dados

### Entidades Principais

#### SymptomRecord
Armazena o histórico de consultas de autodiagnóstico:
- ID único
- Timestamp da consulta
- Sintomas relatados
- Diagnóstico obtido
- Recomendações fornecidas
- Nome do paciente (opcional)

#### DiagnosisQuestion
Representa as perguntas do sistema de diagnóstico:
- Texto da pergunta
- Categoria (dor, febre, respiração, etc.)
- Ordem na sequência
- Relacionamento com perguntas anteriores

#### DiagnosisAnswer
Respostas possíveis para cada pergunta:
- Texto da resposta
- Próxima pergunta ou resultado final
- Recomendações específicas
- Pontuação de gravidade

#### FirstAidGuide
Guias de primeiros socorros:
- Título e categoria
- Conteúdo detalhado
- Sinais de alerta
- Indicação de emergência

## 🔄 Funcionalidades Offline

### Armazenamento Local
- **SQLite via Room**: Banco de dados local robusto
- **Dados Pré-carregados**: Guias e perguntas incluídos no app
- **Sem Dependências Externas**: Funciona completamente offline

### Componentes UI
- **Cards**: Organização visual clara
- **Botões Grandes**: Fácil toque em telas pequenas
- **Ícones Intuitivos**: Reconhecimento visual rápido
- **Cores Semânticas**: Verde (seguro), Amarelo (atenção), Vermelho (emergência)

### Avisos Médicos
- **Orientação Inicial**: O app fornece apenas orientações preliminares
- **Não Substitui**: Consulta médica profissional
- **Emergências**: Sempre procure ajuda médica em situações graves
