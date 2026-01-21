# Sistema de Rotas – Dijkstra (Java Swing)

Aplicação desktop em Java para leitura de arquivos de rota, cálculo do caminho mínimo com Dijkstra e geração automática de relatório e imagem do grafo. O sistema também organiza os arquivos processados em pastas de sucesso/erro e permite configurar os diretórios por interface.

## Principais funcionalidades

- Leitura de arquivos no formato `rotaNN.txt`.
- Construção de grafo não direcionado com pesos positivos.
- Cálculo de caminho mínimo (Dijkstra).
- Geração de relatório de processamento.
- Exportação automática do grafo em PNG com o caminho destacado.
- Interface gráfica para seleção e processamento em lote.
- Tela de configuração para pastas de trabalho.

## Formato do arquivo de rota

Cada arquivo de rota é composto por linhas de controle. Exemplo:

```
00NN
02A;B=5
02B;C=3
```

- **00NN**: define a quantidade de vértices (NN com 2 dígitos).
- **02A;B=5**: define uma aresta entre os vértices `A` e `B` com peso `5`.

Os vértices são rotulados de `A` até o último vértice, de acordo com `NN`.

## Como usar

1. Abra o projeto na IDE.
2. Execute a classe `Main.VisivelFrame`.
3. Escolha entre:
   - Processar um único arquivo de rota.
   - Processar todos os arquivos `rotaNN.txt` na pasta raiz.
4. Verifique o log na interface e os arquivos gerados nas pastas configuradas.

> Também é possível abrir a tela de configuração executando `Main.ConfiguracaoFrame`.

## Saídas geradas

- **Relatórios**: `*_relatorio.txt` em `C:\Teste\Processado\Relatorios`.
- **Imagem do grafo**: `*_grafo.png` em `C:\Teste\Processado`.
- **Arquivos processados**: movidos para a pasta `Processado`.
- **Arquivos com erro**: movidos para a pasta `NaoProcessado`.

## Configurações

O sistema cria automaticamente um arquivo de configuração:

```
C:\Teste\Configuracao\config.txt
```

Chaves disponíveis:

- `Processado` – pasta de sucesso.
- `NaoProcessado` – pasta de erro.
- `RotaAutomatica` – booleano para execução automática.

## Estrutura do projeto

- `Grafo`: matriz de adjacência e Dijkstra.
- `FileProcessor`: leitura do arquivo, montagem do grafo e execução.
- `RelatorioGenerator`: geração do relatório de processamento.
- `VisualGrafoFrame`: renderização do grafo e exportação de PNG.
- `VisivelFrame`: interface principal.
- `ConfiguracaoFrame`: interface de configuração.

## Requisitos

- Java 8+ (Swing).

## Observações

- Os caminhos padrão estão definidos para Windows (`C:\Teste`).
- Para outro sistema operacional, ajuste as constantes no código ou a configuração inicial.

---

Se quiser, posso adicionar instruções de execução por terminal, exemplos completos de arquivos de entrada ou adaptar os caminhos para Linux.