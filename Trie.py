class NodoTrie:

    def __init__(self):

        self.keys = {}
        self.flag = False

class Trie:

    def __init__(self):

        # Esta madre siempre va a contener la cadena vacía.
        self.root = NodoTrie()
        self.root.flag = True
        self.cont = 0


    def inserta(self, elemento):
        """
        Aquí estamos considerando que siempre elemento será un iterable
        """
        aux_node = self.root

        for key in elemento:
            if key in aux_node.keys:
                aux_node = aux_node.keys[key]
            else:
                aux_node.keys[key] = NodoTrie()
                aux_node = aux_node.keys[key]

        aux_node.flag = True
        self.cont += 1

    def busca(self, elemento):
        flag = True
        aux_node = self.root
        for letter in elemento:
            if letter in aux_node.keys:
                aux_node = aux_node.keys[letter]
            else:
                flag = False
                break
        if flag:
            flag = aux_node.flag
        return flag

    def busca_recursivo(self, elemento, i=0):
        letter = elemento[i]
        print(letter)

    def elimina(self, elemento):
        # Vemos si el elemento está en la estructura
        flag = self.busca(elemento)

        if flag:
            aux_node = self.root
            for letter in elemento:
                aux_node = aux_node.keys[letter]
            aux_node.flag = False




if __name__ == '__main__':
    trie = Trie()

    trie.inserta('Mariano')

    trie.inserta('Maria')
    trie.elimina('Mariano')

    print(trie.busca('Mariano'))



