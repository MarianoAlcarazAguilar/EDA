from heapdict import heapdict
import numpy as np

class Grafo:

    def __init__(self):
        # Nuestro grafo se implementará en un diccionario
        # Es de la siguiente forma:
        # {vertice1: {vecino11: peso, vecino12: peso}, vertice2: {vecino21: peso, vecino22:peso}}
        self.grafo = {}
        # Se tiene un diccionario auxiliar para ver los que hayamos visitado al recorrer
        self.visitados = {}

    def inserta_dirigido(self, v1, v2, peso):
        """
        Se dan dos vértices.
        v1 se conecta a v2 pero v2 no se conecta a v1
        El peso es por ejemplo la distancia entre ambos vértices
        """
        grafo = self.grafo
        # Vemos si el nodo ya existía
        if v1 in grafo:
            grafo[v1][v2] = peso
        else:
            grafo[v1] = {v2: peso}
        # IMPORTANTE: NOS FALTA ASEGURARNOS DE QUE V2 SEA UN VÉRTICE EN EL GRAFO
        if v2 not in grafo:
            grafo[v2] = {}

    def inserta(self, v1, v2, peso):
        """
        Este método hace una inserción no dirigida entre los nodos dados
        """
        self.inserta_dirigido(v1, v2, peso)
        self.inserta_dirigido(v2, v1, peso)

    def inserta_nodo(self, v1):
        """
        Este método inserta un nodo al grafo sin ninguna conexión
        """
        if v1 not in self.grafo:
            self.grafo[v1] = {}

    def dfs(self):
        """
        Depth First Search
        Este método hace un recorrido en profundidad.
        Esto es, empieza en un nodo y primero visita todos los 'hijos' de ese hijo

        Aplicaciones:
            - Encontrar nodos conectados
            - Ordenamiento topológico en grafo acíclico
            - Encontrar puentes
            - Resolver laberintos
            - Encontrar nodos fuertemente conectados
        """


        # Usaremos el diccionario de visitados para evitar entrar en loops infinitos
        # Iniciamos todos en Falso
        for vertice in self.grafo:
            self.visitados[vertice] = False

        lista = []

        for vertice in self.grafo:
            if not self.visitados[vertice]:
                self.__dfs(vertice, lista)

        return lista


    def __dfs(self, actual, lista):
        # Marcamos el actual como visitado para no regresar a él
        # y lo agregamos a la lista
        self.visitados[actual] = True
        lista.append(actual)

        # Visitamos todos los vecinos del actual
        for vecino in self.grafo[actual]:
            # Si no lo hemos visitado, entonces lo llamamos
            if not self.visitados[vecino]:
                self.__dfs(vecino, lista)


    def bfs(self):
        """
        Breath First Search
        Este método hace un recorrido en anchura.
        Esto es, primero visita el nodo actual, luego todos sus vecinos,
        y luego los vecinos de sus vecinos. Es como si fuera 'por nivel'

        Aplicaciones:
            - Encontrar el camino más corto entre dos nodos
              (medido por el número de nodos conectados)
            - Probar si un grafo de nodos es bipartito (si se puede
              (dividir en dos conjuntos)
            - Encontrar el árbol de expansión mínima
            - Sistemas de navegación GPS
        """

        for vertice in self.grafo:
            self.visitados[vertice] = False

        lista = []

        for vertice in self.grafo:
            if not self.visitados[vertice]:
                self.__bfs(vertice, lista)

        return lista

    def __bfs(self, actual, lista):
        # Esto usa una cola como cuando teníamos los árboles
        cola = []
        self.visitados[actual] = True
        cola.append(actual)

        while len(cola) != 0:
            actual = cola.pop(0)
            lista.append(actual)

            for vecino in actual:
                if not self.visitados[vecino]:
                    self.visitados[vecino] = True
                    cola.append(vecino)








