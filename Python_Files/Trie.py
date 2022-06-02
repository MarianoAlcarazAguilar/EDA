class NodoTrie:

    def __init__(self):

        self.letters = {}
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
            if key in aux_node.letters:
                aux_node = aux_node.letters[key]
            else:
                aux_node.letters[key] = NodoTrie()
                aux_node = aux_node.letters[key]

        aux_node.flag = True
        self.cont += 1

    def busca(self, elemento):
        # Al chile no entiendo cómo funciona esta madre.
        flag = True
        aux_node = self.root
        for letter in elemento:
            if letter in aux_node.letters:
                aux_node = aux_node.letters[letter]
            else:
                flag = False
                break
        if flag:
            flag = aux_node.flag
        return flag

    def __elimina(self, actual, cadena):
        if len(cadena) == 0:
            if actual.flag:
                self.cont -= 1
                actual.flag = False

                # Si ya no hay nada en el nodo
                if len(actual.letters) == 0:
                    return None
            return actual

        letter = cadena[0]
        if letter in actual.lettersk:
            res = self.__elimina(actual.letters[letter], cadena[1:])

        if res is None:
            actual.letters.pop(letter)
            if len(actual.letters) == 0 and not actual.flag:
                return None
        return actual


    def __recorre(self, actual, lista, cadena):
        if actual.flag:
            lista.append(cadena)
        for k in actual.letters:
            self.__recorre(actual.letters[k], lista, cadena+k)
        return cadena

    def recorre(self):
        lista = []
        self.__recorre(self.root, lista, "")
        return lista


if __name__ == '__main__':
    trie = Trie()

    trie.inserta('Mariano')
    trie.inserta('Maria')
    trie.inserta('Jose Luis')
    trie.inserta('Mariana')
    trie.inserta(['h','o','l','a'])

    print(trie.recorre())

    print(trie.busca('Mariano'))
