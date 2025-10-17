package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Program {

    public static void main(String[] args) throws Exception {
        File arquivo = new File("C:\\Teste\\rota01.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println("-> " + linha);
            }
        }
    }
}