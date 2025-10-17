package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracaoFrame extends JFrame {

    private JTextField pastaPrincipalField;
    private JTextField sucessoField;
    private JTextField erroField;
    private JCheckBox rotaAutomaticaBox;

    public ConfiguracaoFrame() {
        setTitle("Configuração do Sistema");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Map<String, String> config = ConfigManager.loadConfig();

        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));

        painel.add(new JLabel("📁 Pasta Principal:"));
        pastaPrincipalField = new JTextField(ConfigManager.getRootPath());
        painel.add(pastaPrincipalField);

        painel.add(new JLabel("✅ Pasta de Sucesso:"));
        sucessoField = new JTextField(config.getOrDefault("Processado", ConfigManager.getRootPath() + "\\Processado"));
        painel.add(sucessoField);

        painel.add(new JLabel("❌ Pasta de Erro:"));
        erroField = new JTextField(config.getOrDefault("NaoProcessado", ConfigManager.getRootPath() + "\\NaoProcessado"));
        painel.add(erroField);

        painel.add(new JLabel("⚙️ Rota Automática:"));
        rotaAutomaticaBox = new JCheckBox("Habilitar execução automática");
        rotaAutomaticaBox.setSelected(Boolean.parseBoolean(config.getOrDefault("RotaAutomatica", "false")));
        painel.add(rotaAutomaticaBox);

        JButton salvar = new JButton("💾 Salvar Configurações");
        salvar.addActionListener(this::salvarConfiguracoes);

        add(painel, BorderLayout.CENTER);
        add(salvar, BorderLayout.SOUTH);
    }

    private void salvarConfiguracoes(ActionEvent e) {
        Map<String, String> novaConfig = new HashMap<>();
        novaConfig.put("Processado", sucessoField.getText().trim());
        novaConfig.put("NaoProcessado", erroField.getText().trim());
        novaConfig.put("RotaAutomatica", String.valueOf(rotaAutomaticaBox.isSelected()));

        ConfigManager.saveConfig(novaConfig);

        JOptionPane.showMessageDialog(this, "Configurações salvas com sucesso!");
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfiguracaoFrame().setVisible(true));
    }
}
