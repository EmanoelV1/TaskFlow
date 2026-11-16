# TaskFlow

Aplicativo móvel nativo para Android desenvolvido com **Kotlin**, **Jetpack Compose** e **Firebase**, projetado para o gerenciamento eficiente de tarefas pessoais com sincronização em nuvem em tempo real.

---

## Tecnologias Utilizadas

O projeto foi construído utilizando as tecnologias modernas recomendadas para o desenvolvimento Android:

* **Kotlin**: Linguagem de programação principal.
* **Jetpack Compose**: Construção moderna das telas e interface de usuário declarativa.
* **Material 3**: Componentes visuais e sistema de design atualizado.
* **Firebase Authentication**: Gerenciamento seguro de autenticação (Login e Cadastro por E-mail/Senha).
* **Cloud Firestore**: Banco de dados NoSQL orientado a documentos para armazenamento sincronizado de tarefas em tempo real.
* **Gradle / Kotlin Coroutines**: Gerenciamento de dependências e processamento assíncrono.

---

## Funcionalidades

* Autenticação de Usuários: Tela de login e cadastro integrada ao Firebase Auth com validação de credenciais e sessão persistente.
* Criação e Edição: Diálogos interativos para adicionar e atualizar dados das tarefas e prazos.
* Listagem Dinâmica: Utilização de LazyColumn para performance otimizada na exibição dos cards.
* Níveis de Urgência: Classificação visual por cores (Baixa em Verde, Média em Amarelo/Âmbar e Urgente em Vermelho).
* Conclusão com Checkbox: Marcar ou desmarcar tarefas aplica dinamicamente um efeito de texto riscado (LineThrough).
* Exclusão Segura: Confirmação de exclusão por meio de um diálogo modal (AlertDialog).
* Sincronização em Tempo Real: Ouvintes em tempo real do Cloud Firestore.
* Sistema de Notificações: Alertas locais integrados para lembretes de prazos próximos
