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
- **Material Design 3** - Design system moderno

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

### Exportação/Importação
- **JSON**: Formato padrão para backup
- **CSV**: Compatível com planilhas
- **QR Code**: Compartilhamento rápido de dados
- **Bluetooth**: Transferência entre dispositivos

## 🎨 Interface do Usuário

### Design Principles
- **Simplicidade**: Interface limpa e intuitiva
- **Acessibilidade**: Botões grandes e texto legível
- **Contraste Alto**: Visibilidade em diferentes condições de luz
- **Navegação Clara**: Fluxo lógico entre funcionalidades

### Componentes UI
- **Cards**: Organização visual clara
- **Botões Grandes**: Fácil toque em telas pequenas
- **Ícones Intuitivos**: Reconhecimento visual rápido
- **Cores Semânticas**: Verde (seguro), Amarelo (atenção), Vermelho (emergência)

## 📱 Como Executar o Projeto

### Pré-requisitos
- Android Studio Hedgehog ou superior
- Android SDK 24+ (API Level 24)
- Kotlin 2.0.21+
- Gradle 8.12.2+

### Passos para Execução

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/conectaribas.git
   cd conectaRibas
   ```

2. **Abra no Android Studio**
   - Abra o Android Studio
   - Selecione "Open an existing project"
   - Navegue até a pasta do projeto e selecione

3. **Sincronize o projeto**
   - Aguarde a sincronização do Gradle
   - Resolva quaisquer dependências pendentes

4. **Execute no dispositivo/emulador**
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em "Run" (▶️) no Android Studio

### Configurações de Build

O projeto utiliza:
- **Compile SDK**: 36 (Android 14)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)

## 🧪 Testes

### Estrutura de Testes
```
app/src/
├── test/                          # Testes unitários
│   └── java/com/example/conectaribas/
│       └── ExampleUnitTest.kt
└── androidTest/                   # Testes de instrumentação
    └── java/com/example/conectaribas/
        └── ExampleInstrumentedTest.kt
```

### Executando Testes
- **Testes Unitários**: `./gradlew test`
- **Testes de Instrumentação**: `./gradlew connectedAndroidTest`

## 📊 Dependências Principais

### Core Android
- `androidx.core:core-ktx` - Extensões Kotlin para Android
- `androidx.lifecycle:lifecycle-runtime-ktx` - Ciclo de vida
- `androidx.activity:activity-compose` - Compose Activity

### UI e Compose
- `androidx.compose.ui:ui` - Componentes UI básicos
- `androidx.compose.material3:material3` - Material Design 3
- `androidx.compose.ui:ui-tooling` - Ferramentas de desenvolvimento

### Banco de Dados
- `androidx.room:room-runtime` - Runtime do Room
- `androidx.room:room-ktx` - Extensões Kotlin para Room
- `androidx.room:room-compiler` - Processador de anotações

### Navegação e Estado
- `androidx.navigation:navigation-compose` - Navegação Compose
- `androidx.lifecycle:lifecycle-viewmodel-compose` - ViewModel Compose
- `org.jetbrains.kotlinx:kotlinx-coroutines-android` - Coroutines

### Utilitários
- `com.google.zxing:android-core` - Geração de QR Codes

## 🔧 Configurações de Desenvolvimento

### Kotlin
- **JVM Target**: 11
- **Kotlin Version**: 2.0.21

### Compose
- **Compose BOM**: 2024.09.00
- **Build Features**: Compose habilitado

### Room
- **Version**: 2.6.1
- **Kapt**: Habilitado para processamento de anotações

## 📝 Comentários no Código

O projeto segue padrões rigorosos de documentação:

- **Documentação de Classes**: Propósito e responsabilidades
- **Documentação de Métodos**: Parâmetros, retorno e comportamento
- **Documentação de Entidades**: Estrutura do banco de dados
- **Comentários Inline**: Explicação de lógica complexa

## 🚨 Considerações de Segurança

### Dados Sensíveis
- **Armazenamento Local**: Todos os dados ficam no dispositivo
- **Sem Transmissão**: Nenhum dado é enviado para servidores externos
- **Privacidade**: Controle total sobre os dados do usuário

### Avisos Médicos
- **Orientação Inicial**: O app fornece apenas orientações preliminares
- **Não Substitui**: Consulta médica profissional
- **Emergências**: Sempre procure ajuda médica em situações graves

## 🤝 Contribuição

### Como Contribuir
1. **Fork** o projeto
2. **Crie** uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. **Push** para a branch (`git push origin feature/AmazingFeature`)
5. **Abra** um Pull Request

### Padrões de Código
- **Kotlin Style Guide**: Siga as convenções do Kotlin
- **Compose Guidelines**: Use as melhores práticas do Compose
- **Room Patterns**: Siga os padrões recomendados do Room
- **Testes**: Mantenha cobertura de testes adequada

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 👥 Autores

- **Equipe Conecta Ribas** - Desenvolvimento inicial
- **Contribuidores** - Melhorias e correções

## 🙏 Agradecimentos

- Comunidades ribeirinhas que inspiraram o projeto
- Profissionais de saúde que validaram o conteúdo
- Comunidade Android e Kotlin pelo suporte
- Contribuidores open source que tornaram o projeto possível

## 📞 Suporte

Para dúvidas, sugestões ou problemas:

- **Issues**: Abra uma issue no GitHub
- **Discussions**: Participe das discussões do projeto
- **Email**: [seu-email@exemplo.com]

---

**⚠️ Aviso Importante**: Este aplicativo é apenas para orientação inicial. Em caso de emergência médica, procure ajuda profissional imediatamente.
