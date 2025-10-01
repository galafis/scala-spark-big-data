# Diretrizes de Contribuição

Bem-vindo(a) ao projeto `scala-spark-big-data`! Agradecemos o seu interesse em contribuir. Para garantir um processo de colaboração eficiente e de alta qualidade, por favor, siga estas diretrizes.

## Como Contribuir

### 1. Reportar Bugs

Se você encontrar um bug, por favor, abra uma [issue](https://github.com/galafis/scala-spark-big-data/issues) e inclua o máximo de detalhes possível:

*   **Descrição clara e concisa** do bug.
*   **Passos para reproduzir** o comportamento inesperado.
*   **Comportamento esperado** vs. **comportamento atual**.
*   **Capturas de tela** ou logs, se aplicável.
*   **Versão do Scala, Spark e SBT** utilizada.

### 2. Sugerir Novas Funcionalidades

Para sugerir uma nova funcionalidade, abra uma [issue](https://github.com/galafis/scala-spark-big-data/issues) e descreva a sua ideia em detalhes:

*   **Explique a necessidade** ou o problema que a funcionalidade resolveria.
*   **Descreva a funcionalidade** proposta e como ela se encaixaria no projeto.
*   **Exemplos de uso** ou casos de uso, se possível.

### 3. Contribuir com Código

1.  **Faça um Fork** do repositório.
2.  **Clone** o seu fork localmente:
    ```bash
    git clone https://github.com/SEU_USUARIO/scala-spark-big-data.git
    cd scala-spark-big-data
    ```
3.  **Crie uma nova branch** para a sua funcionalidade ou correção de bug:
    ```bash
    git checkout -b feature/sua-feature-name
    # ou
    git checkout -b bugfix/seu-bugfix-name
    ```
4.  **Implemente suas mudanças** e certifique-se de seguir o estilo de código existente.
5.  **Escreva testes unitários** para suas mudanças, garantindo que o código esteja coberto e funcionando corretamente.
6.  **Execute os testes** para garantir que tudo está funcionando:
    ```bash
    sbt test
    ```
7.  **Commit suas mudanças** com uma mensagem clara e descritiva:
    ```bash
    git commit -m "feat: Adiciona nova funcionalidade X" # para novas funcionalidades
    # ou
    git commit -m "fix: Corrige bug Y" # para correções de bugs
    ```
8.  **Envie suas mudanças** para o seu fork no GitHub:
    ```bash
    git push origin feature/sua-feature-name
    ```
9.  **Abra um Pull Request (PR)** para o repositório original. Certifique-se de:
    *   Descrever claramente as mudanças e o problema que elas resolvem.
    *   Referenciar quaisquer issues relacionadas.
    *   Garantir que todos os testes passaram.

## Padrões de Código

*   Siga as convenções de nomenclatura e estilo do Scala.
*   Mantenha o código limpo, legível e bem comentado.
*   Evite código duplicado.

## Revisão de Código

Todos os Pull Requests serão revisados pela equipe do projeto. Esteja preparado(a) para feedback e discussões sobre suas mudanças. O objetivo é garantir a qualidade e a consistência do código.

Obrigado(a) por contribuir!
