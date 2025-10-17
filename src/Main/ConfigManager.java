package Main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final String CONFIG_PATH = "C:\\Teste\\Configuracao\\config.txt";
    private static final String ROOT_PATH = "C:\\Teste";

    public static Map<String, String> loadConfig() {
        Map<String, String> config = new HashMap<>();

        File configFile = new File(CONFIG_PATH);

        // Cria pastas iniciais se não existirem
        new File(ROOT_PATH).mkdirs();
        new File(ROOT_PATH + "\\Configuracao").mkdirs();
        new File(ROOT_PATH + "\\Processado").mkdirs();
        new File(ROOT_PATH + "\\NaoProcessado").mkdirs();

        // Cria config.txt padrão se não existir
        if (!configFile.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
                writer.println("Processado=" + ROOT_PATH + "\\Processado");
                writer.println("NaoProcessado=" + ROOT_PATH + "\\NaoProcessado");
                writer.println("RotaAutomatica=false");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Lê o conteúdo do arquivo
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) config.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }

    public static void saveConfig(Map<String, String> config) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_PATH))) {
            for (String key : config.keySet()) {
                writer.println(key + "=" + config.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRootPath() {
        return ROOT_PATH;
    }

    public static String getConfigPath() {
        return CONFIG_PATH;
    }
}
