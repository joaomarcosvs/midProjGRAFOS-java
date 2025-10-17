package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

public class VisivelFrame extends JFrame {

    private JTextArea logArea;
    private JFileChooser fileChooser;

    public VisivelFrame() {
        setTitle("Sistema de Rotas - Dijkstra");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel superior (botões)
        JPanel topPanel = new JPanel();
        JButton selecionarArquivoBtn = new JButton("📂 Selecionar rotaNN.txt");
        JButton processarTodosBtn = new JButton("⚙️ Processar todos da pasta");
        JButton sairBtn = new JButton("❌ Sair");

        topPanel.add(selecionarArquivoBtn);
        topPanel.add(processarTodosBtn);
        topPanel.add(sairBtn);

        // Área de log
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Adiciona ao frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        fileChooser = new JFileChooser();

        // Eventos
        selecionarArquivoBtn.addActionListener(this::selecionarArquivo);
        processarTodosBtn.addActionListener(this::processarTodos);
        sairBtn.addActionListener(e -> dispose());
    }

    private void selecionarArquivo(ActionEvent e) {
        fileChooser.setDialogTitle("Selecione o arquivo de rota");
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            processarArquivo(arquivo);
        }
    }

    private void processarTodos(ActionEvent e) {
        Map<String, String> config = ConfigManager.loadConfig();
        String pastaRaiz = ConfigManager.getRootPath();

        File dir = new File(pastaRaiz);
        File[] arquivos = dir.listFiles((d, name) -> name.startsWith("rota") && name.endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum arquivo rotaNN.txt encontrado em " + pastaRaiz);
            return;
        }

        for (File arquivo : arquivos) {
            processarArquivo(arquivo);
        }
    }

    private void processarArquivo(File arquivo) {
        try {
            logArea.append("📄 Processando: " + arquivo.getName() + "\n");

            ResultadoCaminho resultado = FileProcessor.processarRota(arquivo);
            List<Integer> caminho = resultado.getCaminho();

            logArea.append("✅ Caminho encontrado: " + caminho + "\n");
            logArea.append("💰 Custo total: " + resultado.getCustoTotal() + "\n\n");
            RelatorioGenerator.gerar(arquivo.getName(), caminho, resultado.getCustoTotal());
            VisualGrafoFrame.mostrar(FileProcessor.getUltimoGrafo(), caminho, arquivo.getName());
        } catch (Exception ex) {
            logArea.append("❌ Erro ao processar " + arquivo.getName() + ": " + ex.getMessage() + "\n\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisivelFrame().setVisible(true));
    }
}
