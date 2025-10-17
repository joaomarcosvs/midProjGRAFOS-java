package Main;

import java.util.List;

public class ResultadoCaminho {
    private List<Integer> caminho;
    private int custoTotal;

    public ResultadoCaminho(List<Integer> caminho, int custoTotal) {
        this.caminho = caminho;
        this.custoTotal = custoTotal;
    }

    public List<Integer> getCaminho() {
        return caminho;
    }

    public int getCustoTotal() {
        return custoTotal;
    }
}
