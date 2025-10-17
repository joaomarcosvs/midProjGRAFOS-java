package Main;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileProcessor {

    private static Grafo ultimoGrafo;

    public static Grafo getUltimoGrafo() {
        return ultimoGrafo;
    }

    public static ResultadoCaminho processarRota(File arquivo) throws Exception {
        Map<String, String> config = ConfigManager.loadConfig();

        String pastaSucesso = config.get("Processado");
        String pastaErro = config.get("NaoProcessado");

        Grafo grafo = null;
        int numVertices = 0;
        List<String> pesos = new ArrayList<>();

        System.out.println(">> Iniciando leitura de: " + arquivo.getAbsolutePath());

        ResultadoCaminho resultado = null;

        try {
            // --- Leitura do arquivo dentro de um try-with-resources ---
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                String linha;

                while ((linha = reader.readLine()) != null) {
                    linha = linha.trim();
                    System.out.println("Linha lida: " + linha);

                    if (linha.startsWith("00")) {
                        numVertices = Integer.parseInt(linha.substring(2, 4));
                        grafo = new Grafo(numVertices);
                    } else if (linha.startsWith("02")) {
                        pesos.add(linha.substring(2)); // exemplo: A;B=5
                    }
                }
            }

            if (grafo == null)
                throw new IllegalStateException("Arquivo inválido: bloco 00 não encontrado.");

            for (String p : pesos) {
                String[] parts = p.split("[;=]");
                if (parts.length != 3) continue;

                int noOrigem = parts[0].charAt(0) - 'A';
                int noDestino = parts[1].charAt(0) - 'A';
                int peso = Integer.parseInt(parts[2]);

                grafo.criaArestar(noOrigem, noDestino, peso);
                System.out.println("Aresta criada: " + parts[0] + " -> " + parts[1] + " = " + peso);
            }

            System.out.println("Executando Dijkstra de A até " + (char) ('A' + numVertices - 1));
            resultado = grafo.caminhoMinimo(0, numVertices - 1);

            System.out.println("✅ Caminho encontrado: " + resultado.getCaminho());
            System.out.println("💰 Custo total: " + resultado.getCustoTotal());

            moverArquivo(arquivo, pastaSucesso);
            System.out.println("📦 Arquivo movido para: " + pastaSucesso);

        } catch (Exception e) {
            moverArquivo(arquivo, pastaErro);
            System.out.println("❌ Erro ao processar " + arquivo.getName() + ": " + e.getMessage());
            throw e;
        }

        ultimoGrafo = grafo;

        return resultado;
    }

    private static void moverArquivo(File arquivo, String destino) {
        try {
            // Apenas move depois que a leitura foi concluída e o arquivo fechado
            Path origem = arquivo.toPath();
            Path destinoPath = Paths.get(destino, arquivo.getName());
            Files.move(origem, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("⚠️ Erro ao mover arquivo: " + e.getMessage());
        }
    }
}
