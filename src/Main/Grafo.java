package Main;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * Classe responsável por representar um grafo não direcionado com pesos positivos.
 * Implementa o algoritmo de Dijkstra para cálculo do menor caminho.
 */
public class Grafo {

    private static final int INDEFINIDO = -1;
    private int[][] vertices;

    /**
     * Construtor que cria a matriz de adjacência do grafo.
     * @param numVertices Número total de vértices do grafo
     */
    public Grafo(final int numVertices) {
        vertices = new int[numVertices][numVertices];
    }

    /**
     * Cria uma aresta entre dois nós (bidirecional).
     * @param noOrigem índice do nó de origem
     * @param noDestino índice do nó de destino
     * @param peso peso da aresta
     */
    public void criaArestar(final int noOrigem, final int noDestino, final int peso) {
        if (peso >= 1) {
            vertices[noOrigem][noDestino] = peso;
            vertices[noDestino][noOrigem] = peso;
        } else {
            throw new InvalidParameterException(
                    "Peso negativo detectado entre " + noOrigem + " e " + noDestino + ": " + peso);
        }
    }

    /**
     * Calcula o caminho mínimo entre dois vértices usando o algoritmo de Dijkstra.
     * @param noOrigem índice do nó origem
     * @param noDestino índice do nó destino
     * @return ResultadoCaminho contendo a rota e o custo total
     */
    public ResultadoCaminho caminhoMinimo(final int noOrigem, final int noDestino) {

        int[] custo = new int[vertices.length];
        int[] antecessor = new int[vertices.length];
        Set<Integer> naoVisitados = new HashSet<>();

        // Inicializa os custos
        for (int v = 0; v < vertices.length; v++) {
            custo[v] = (v == noOrigem) ? 0 : Integer.MAX_VALUE;
            antecessor[v] = INDEFINIDO;
            naoVisitados.add(v);
        }

        while (!naoVisitados.isEmpty()) {
            int noMaisProximo = getMaisProximo(custo, naoVisitados);
            naoVisitados.remove(noMaisProximo);

            for (Integer vizinho : getVizinhos(noMaisProximo)) {
                int custoTotal = custo[noMaisProximo] + getCusto(noMaisProximo, vizinho);
                if (custoTotal < custo[vizinho]) {
                    custo[vizinho] = custoTotal;
                    antecessor[vizinho] = noMaisProximo;
                }
            }

            if (noMaisProximo == noDestino) {
                List<Integer> caminho = reconstruirCaminho(antecessor, noMaisProximo);
                return new ResultadoCaminho(caminho, custo[noDestino]);
            }
        }

        return new ResultadoCaminho(Collections.emptyList(), 0);
    }

    /**
     * Retorna o nó mais próximo (menor custo ainda não visitado)
     */
    private int getMaisProximo(final int[] listaCusto, final Set<Integer> naoVisitados) {
        double minDistancia = Integer.MAX_VALUE;
        int noProximo = 0;
        for (Integer i : naoVisitados) {
            if (listaCusto[i] < minDistancia) {
                minDistancia = listaCusto[i];
                noProximo = i;
            }
        }
        return noProximo;
    }

    /**
     * Retorna os vizinhos conectados a um determinado nó
     */
    private List<Integer> getVizinhos(final int no) {
        List<Integer> vizinhos = new ArrayList<>();
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[no][i] > 0) {
                vizinhos.add(i);
            }
        }
        return vizinhos;
    }

    /**
     * Retorna o custo da aresta entre dois nós
     */
    private int getCusto(final int noOrigem, final int noDestino) {
        return vertices[noOrigem][noDestino];
    }

    /**
     * Reconstrói o caminho mínimo percorrido a partir do vetor de antecessores
     */
    private List<Integer> reconstruirCaminho(final int[] antecessor, int atual) {
        List<Integer> caminho = new ArrayList<>();
        caminho.add(atual);
        while (antecessor[atual] != INDEFINIDO) {
            caminho.add(antecessor[atual]);
            atual = antecessor[atual];
        }
        Collections.reverse(caminho);
        return caminho;
    }

    /**
     * Retorna o número total de vértices do grafo.
     */
    public int getNumVertices() {
        return vertices.length;
    }

    /**
     * Constrói e retorna uma lista de adjacência baseada na matriz.
     * Útil para visualização gráfica.
     */
    public Map<Integer, Map<Integer, Integer>> getAdjList() {
        Map<Integer, Map<Integer, Integer>> adjList = new HashMap<>();
        for (int i = 0; i < vertices.length; i++) {
            Map<Integer, Integer> vizinhos = new HashMap<>();
            for (int j = 0; j < vertices.length; j++) {
                if (vertices[i][j] > 0) {
                    vizinhos.put(j, vertices[i][j]);
                }
            }
            adjList.put(i, vizinhos);
        }
        return adjList;
    }
}
