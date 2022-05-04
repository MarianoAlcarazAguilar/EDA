import numpy as np
import hashlib as hl

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
    bf = BloomFilter(size=1000, k=4)

    bf.insert('mariano')
    print(bf.contains('mariano'))
