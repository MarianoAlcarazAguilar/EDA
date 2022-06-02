"""
Método para ver si es conexo.
Método para ver si el arbol es rojo azul o una madre así.
Método para ver si tiene un camino hamiltoniano.
Árbol de expansión mínima.
"""
from heapdict import heapdict
import numpy as np


class Grafo:

    def __init__(self):
        self.grafo = {}
        self.visitados = {}

    def inserta_dirigido(self, v1, v2, peso=None):
        # Primero tenemos que ver si está v1
        if v1 not in self.grafo:
            self.grafo[v1] = {v2: peso}
        else:
            self.grafo[v1][v2] = peso
        # Metemos v2 en caso de que no esté
        if v2 not in self.grafo:
            self.grafo[v2] = {}

    def inserta(self, v1, v2, peso=None):
        self.inserta_dirigido(v1, v2, peso)
        self.inserta_dirigido(v2, v1, peso)

    def inserta_nodo(self, v1):
        if v1 not in self.grafo:
            self.grafo[v1] = {}

    def dfs(self):
        """
        Este método recorre primero el nodo y luego sus hijos
        """
        for v in self.grafo:
            self.visitados[v] = False

        lista = []

        for v in self.grafo:
            if not self.visitados[v]:
                self.__dfs(v, lista)

        return lista


    def __dfs(self, actual, lista):
        self.visitados[actual] = True
        lista.append(actual)

        for v in self.grafo[actual]:
            if not self.visitados[v]:
                self.__dfs(v, lista)

    def bfs(self):
        """
        Este método va viendo de dentro hacia afuera, como si fuera por nivel
        """
        for v in self.grafo:
            self.visitados[v] = False
        lista = []
        for v in self.grafo:
            if not self.visitados[v]:
                self.__bfs(v, lista)
        return lista

    def __bfs(self, actual, lista):
        cola = []
        self.visitados[actual] = True
        cola.append(actual)

        while len(cola) != 0:
            actual = cola.pop(0)
            lista.append(actual)

            for vecino in self.grafo[actual].keys():
                if not self.visitados[vecino]:
                    self.visitados[vecino] = True
                    cola.append(vecino)


    def prim(self, v_ini):
        """
        Método para encontrar el árbol de mínima expansión
        """
        key = heapdict()
        papa = {}

        for v in self.grafo:
            key[v] = np.inf
            papa[v] = None

        key[v_ini] = 0
        while len(key) > 0:
            actual, costo = key.popitem()
            for vecino in self.grafo[actual]:
                # Este método puede fallar porque no estamos agregando los pesos de las conexiones
                if vecino in key and self.grafo[actual][vecino] < key[vecino]:
                    key[vecino] = self.grafo[actual][vecino]
                    papa[vecino] = actual

        return papa

    def dijkstra(self, v_ini):
        # Esta madre no jala
        key = heapdict()
        papa = {}

        for v in self.grafo:
            key[v] = np.inf
            papa[v] = None

        key[v_ini] = 0
        while len(key) > 0:
            actual, costo = key.popitem()
            for vecino in self.grafo[actual]:
                if vecino in key and costo+self.grafo[actual][vecino] < key[vecino]:
                    key[vecino] = costo+self.grafo[actual][vecino]
                    papa[vecino] = actual

        return papa


if __name__ == '__main__':

    grafo = Grafo()
    grafo.inserta('mariano', 'rodrigo', peso=10)
    grafo.inserta('ximena', 'cuco', peso=10)
    grafo.inserta('mariana', 'amor', peso=10)



    print(grafo.dfs())
    print(grafo.dfs())
    print(grafo.dfs())
    print(grafo.bfs())
    print(grafo.grafo)
    print(grafo.prim('mariano'))


    # Otro grafo
    g = Grafo()
    g.inserta_dirigido('a','b',2)
    g.inserta_dirigido('a', 'd', 1)
    g.inserta_dirigido('a', 'f', 4)
    g.inserta_dirigido('b', 'c', 7)
    g.inserta_dirigido('b', 'f', 3)
    g.inserta_dirigido('d', 'c', 4)
    g.inserta_dirigido('d', 'e', 1)
    g.inserta_dirigido('e', 'c', 4)
    g.inserta_dirigido('e', 'f', 1)

    print(g.dijkstra('e'))



