package lesson5;

import kotlin.NotImplementedError;
import java.util.*;
import lesson5.impl.GraphBuilder;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        //Трудоемкость O(n*n)
        //Ресурсоёмкость O(m+n)
        //m - количество дуг
        //n - количество вершин
        List<Graph.Edge> result = new LinkedList<>();
        List<Graph.Vertex> loop = new LinkedList<>();
        Set<Graph.Edge> edgeSet = graph.getEdges();
        Set<Graph.Vertex> verticesSet = graph.getVertices();
        boolean EulerLoop = graph.getVertices().stream().anyMatch(vertex ->
                (graph.getNeighbors(vertex).size() % 2) != 0);
        if ((EulerLoop) || (edgeSet.isEmpty()) || (verticesSet.isEmpty())) {
            return result;
        }
        loopSearch(verticesSet.iterator().next(), graph, edgeSet, verticesSet, loop);
        int i = 0;
        while (i < (loop.size() - 1)) {
            result.add(graph.getConnection(loop.get(i), loop.get(i + 1)));
            i++;
        }
        return result;
    }

    private static void loopSearch(Graph.Vertex vertex, Graph graph,
                                 Set<Graph.Edge> edgeSet, Set<Graph.Vertex> verticesSet, List<Graph.Vertex> loop) {
        for (Graph.Vertex vertices : verticesSet) {
            Graph.Edge edge = graph.getConnection(vertex, vertices);
            if (edgeSet.contains(edge)) {
                edgeSet.remove(edge);
                loopSearch(vertices, graph, edgeSet, verticesSet, loop);
            }
        }
        loop.add(vertex);
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        //Трудоемкость О(n*m)
        //Ресурсоемкость О(n)
        //m - количество дуг
        //n - количество вершин
        GraphBuilder result = new GraphBuilder();
        Map<Graph.Vertex, Integer> vertices = new LinkedHashMap<>();
        Set<Graph.Edge> edgeSet = graph.getEdges();
        Set<Graph.Vertex> verticesSet = graph.getVertices();
        int count = 0;
        for (Graph.Vertex vertex : verticesSet) {
            vertices.put(result.addVertex(vertex.getName()), count);
            count++;
        }
        for (Graph.Edge edge : edgeSet) {
            Integer start = vertices.get(edge.getBegin());
            Integer end = vertices.get(edge.getEnd());
            if (!((start).equals(end))) {
                result.addConnection(edge.getBegin(), edge.getEnd(), 0);
                for (Map.Entry<Graph.Vertex, Integer> vertex: vertices.entrySet()) {
                    if (vertex.getValue().equals(start)) {
                        vertex.setValue(end);
                    }
                }
            }
        }
        return result.build();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        throw new NotImplementedError();
    }
}
