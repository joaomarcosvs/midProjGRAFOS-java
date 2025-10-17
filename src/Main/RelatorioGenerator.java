package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RelatorioGenerator {

    /**
     * Gera um relatório de processamento de rota.
     *
     * @param nomeArquivo Nome do arquivo de rota processado
     * @param caminho     Lista com o caminho mínimo
     * @param custoTotal  Custo total calculado
     */
    public static void gerar(String nomeArquivo, List<Integer> caminho, int custoTotal) {
        try {
            // Diretório de destino
            String destino = "C:\\Teste\\Processado\\Relatorios";
            File pasta = new File(destino);
            if (!pasta.exists()) pasta.mkdirs();

            // Nome do relatório
            String nomeRelatorio = nomeArquivo.replace(".txt", "_relatorio.txt");
            File arquivoRelatorio = new File(pasta, nomeRelatorio);

            // Data e hora formatadas
            String dataHora = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            );

            // Criação do conteúdo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoRelatorio))) {
                writer.write("=== RELATÓRIO DE PROCESSAMENTO DE ROTA ===\n\n");
                writer.write("Arquivo de entrada: " + nomeArquivo + "\n");
                writer.write("Data de processamento: " + dataHora + "\n\n");
                writer.write("Caminho mínimo encontrado:\n");
                writer.write("  " + caminho + "\n\n");
                writer.write("Custo total:\n");
                writer.write("  " + custoTotal + "\n\n");
                writer.write("Status: PROCESSAMENTO CONCLUÍDO COM SUCESSO ✅\n");
            }

            System.out.println("📝 Relatório gerado em: " + arquivoRelatorio.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
