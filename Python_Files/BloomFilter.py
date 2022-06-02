import numpy as np
import hashlib as hl
import os

# import random as rnd
# import matplotlib.pyplot as plt

'''
Operaciones disponibles:
    -Insertar
    -Buscar
    
Ventajas:
    -Rapidez
    -Eficiencia en espacio

Desventajas:
    -Puede mandar falsos positivos
    
Básicamente es una HashTable de booleanos
'''
class BloomFilter:

    def __init__(self, size, k):
        self.k = k
        self.bloom = np.array([False for _ in range(size)], dtype=bool)
        self.size = size
        self.cont = 0

    def __regresa_hash(self, dato):
        bits = int(np.ceil(np.log(self.size) / np.log(2)))
        hexa = int(np.ceil(bits / 4))
        nMd5 = int(np.ceil(self.k * hexa / 32))
        hash = hl.md5(dato.encode('utf-8')).hexdigest()
        for i in range(nMd5 - 1):
            hash += hl.md5(hash.encode('utf-8')).hexdigest()
        res = []
        for i in range(0, hexa * self.k, hexa):
            res.append(int(hash[i:i + hexa], 16) % self.size)
        return res

    def insert(self, dato):
        posiciones = self.__regresa_hash(dato)
        for pos in posiciones:
            self.bloom[pos] = True
        self.cont += 1

    def contains(self, dato):
        posiciones = self.__regresa_hash(dato)
        i = 0
        band = True
        while band and i < len(posiciones):
            if not self.bloom[posiciones[i]]:
                band = False
            i += 1
        return band


if __name__ == '__main__':

    # Pruebas con la k fija, para varias k's
    # Para cada k aumentas el tamaño del bf
    # Insertamos siempre la misma cantidad de datos
    # Buscamos siempre de datos que NO estén en el bf
    # Contamos cuantos falsos positivos hay
    # Dividimos entre la cantidad de datos insertados
    # Graficamos en excel

    '''
    universe = [i for i in range(300)]

    np.random.seed(100)
    insertados = np.random.choice(universe, 200)


    file = open("./Datasets/results.csv", "w")
    file.write("k,n_datos,bf_size,total_fp,fp" + os.linesep)

    # Para cada k
    for k in np.arange(1,11):

        # Para cada tamaño
        for size in np.arange(100, 5001, 100):
            file.write(str(k) + ',' + str(len(insertados)) + ',' + str(size) + ',')
            bf = BloomFilter(size=size, k=k)

            # Insertamos los datos
            for dato in insertados:
                bf.insert(str(dato))

            # Contamos falsos positivos
            fp = 0
            for dato in universe:
                resp = bf.contains(str(dato))
                if resp and dato not in list(insertados):
                    fp += 1

            file.write(str(fp) + ',' + str(fp/len(universe)) + os.linesep)

    file.close()
    '''

    # Otro intento

    # Ahora vamos a mantener constante la cantidad de datos insertados y vamos a aumentar las funciones de hash

    universe = [i for i in range(1000)]

    np.random.seed(1000)

    # Por ahora vamos a mantener el bf size constante

    file = open('./Datasets/results_2.csv', 'w')
    file.write('datos_insertados,hash_functions,total_fp,fp'+os.linesep)

    for to_insert in np.arange(50,501,50):
        insertados = np.random.choice(universe,to_insert)


        for k in np.arange(1,11):
            bf = BloomFilter(size=1000, k=k)
            file.write(str(to_insert) + ',' + str(k)  + ',')

            for dato in insertados:
                bf.insert(str(dato))

            fp = 0
            for dato in universe:
                if bf.contains(str(dato)) and dato not in insertados:
                    fp += 1

            file.write(str(fp) + ',' + str(fp/len(universe)) + os.linesep)

    file.close()