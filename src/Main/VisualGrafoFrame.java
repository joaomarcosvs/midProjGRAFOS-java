package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VisualGrafoFrame extends JFrame {

    private final Grafo grafo;
    private final List<Integer> caminho;
    private final String nomeArquivo;

    public VisualGrafoFrame(Grafo grafo, List<Integer> caminho, String nomeArquivo) {
        this.grafo = grafo;
        this.caminho = caminho;
        this.nomeArquivo = nomeArquivo;

        setTitle("Visualização do Grafo - Caminho Mínimo");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GraphPanel panel = new GraphPanel();
        add(panel);

        // Gera o PNG automaticamente
        salvarImagem(panel);
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int numVertices = grafo.getNumVertices();
            int radius = 200;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            Point[] posicoes = new Point[numVertices];
            for (int i = 0; i < numVertices; i++) {
                double angle = 2 * Math.PI * i / numVertices;
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));
                posicoes[i] = new Point(x, y);
            }

            // Desenha arestas
            g2.setColor(Color.GRAY);
            for (int i = 0; i < numVertices; i++) {
                for (Map.Entry<Integer, Integer> entry : grafo.getAdjList().get(i).entrySet()) {
                    int destino = entry.getKey();
                    int peso = entry.getValue();
                    Point p1 = posicoes[i];
                    Point p2 = posicoes[destino];
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    int midX = (p1.x + p2.x) / 2;
                    int midY = (p1.y + p2.y) / 2;
                    g2.drawString(String.valueOf(peso), midX, midY);
                }
            }

            // Destaca caminho mínimo
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.RED);
            for (int i = 0; i < caminho.size() - 1; i++) {
                Point p1 = posicoes[caminho.get(i)];
                Point p2 = posicoes[caminho.get(i + 1)];
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }

            // Desenha vértices
            for (int i = 0; i < numVertices; i++) {
                Point p = posicoes[i];
                char label = (char) ('A' + i);
                g2.setColor(Color.WHITE);
                g2.fillOval(p.x - 20, p.y - 20, 40, 40);
                g2.setColor(Color.BLACK);
                g2.drawOval(p.x - 20, p.y - 20, 40, 40);
                g2.drawString(String.valueOf(label), p.x - 5, p.y + 5);
            }
        }
    }

    private void salvarImagem(JPanel panel) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Espera o Swing renderizar o painel (garante tamanho correto)
                if (panel.getWidth() <= 0 || panel.getHeight() <= 0) {
                    panel.setSize(800, 600); // tamanho padrão
                }

                // Cria imagem com as dimensões do painel
                BufferedImage imagem = new BufferedImage(
                        panel.getWidth(),
                        panel.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );

                Graphics2D g2 = imagem.createGraphics();
                panel.paint(g2);
                g2.dispose();

                // Caminho de destino
                String destino = "C:\\Teste\\Processado";
                File pasta = new File(destino);
                if (!pasta.exists()) pasta.mkdirs();

                // Nome de saída baseado no arquivo de rota
                String nomeSaida = nomeArquivo.replace(".txt", "_grafo.png");
                File arquivoSaida = new File(pasta, nomeSaida);

                // Salva o PNG
                ImageIO.write(imagem, "png", arquivoSaida);
                System.out.println("🖼️ Imagem salva em: " + arquivoSaida.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Erro ao salvar imagem: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro inesperado ao exportar imagem: " + e.getMessage());
            }
        });
    }


    public static void mostrar(Grafo grafo, List<Integer> caminho, String nomeArquivo) {
        SwingUtilities.invokeLater(() -> new VisualGrafoFrame(grafo, caminho, nomeArquivo).setVisible(true));
    }
}
