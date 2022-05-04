# Guía EDA Parcial 2
Temas incluidos:  
- Heaps
- Hash tables
- Bloom Filters
- Skip Lists
- Maybe árboles b+

## Heaps
### Características
- Altura: log(n)
- Son árboles binarios
- Todos sus niveles están llenos menos posiblemente el último. 
- Todas las hojas se insertan cargadas a la izquierda
- No existen nodos con hijos derechos sin tener hijos izquierdos

### Operaciones disponibles

#### Insertar

Para ello tenemos los siguientes pasos:
1. Lo agregamos en la posición primera desocupada del arreglo.
2. Comparamos con su padre para ver si es mayor.  
  2.1. Si es mayor; terminamos.  
  2.2. Si es menor, los intercambiamos y repetimos.
3. El proceso termina cuando su padre es menor, o estamos en la raíz. 

#### Borrar (solo el mínimo)

Para ello tenemos los siguientes pasos:
1. Intercambiamos la última posición del arreglo con la primera.


- Buscar el mínimo

### Implementación
Una de las maneras más eficientes de lograr implementar esta estructura es mediante  
arreglos, ya que su forma permite manejar los índices de forma binaria, por lo que  
para acceder a ellos solo es necesario recorrer la posición hacia la izquierda.

#### Operandos binarios:
- `&` = AND
- `|` = OR
- `^` = XOR
- `~` = NOT
- `>>` = Shift right
  - Nos da la operación de dividir entre 2 y redondear hacia arriba.
- `<<` = Shift left
  - Es lo mismo que multiplicar por 2.

#### Localizar padres e hijos
Es importante destacar que el arreglo deberá dejar la posición 0 vacía para que esto funcione  
Dado un nodo i, tenemos lo siguiente:
- Hijo izquierdo: **2i**
- Hijo derecho: **2i + 1**
- Padre: **i >> 2**

## Hash Tables

Es la mejor estructura de todas. Tiene un comportamiento asintótico constante.  

Cuando el arreglo se vaya llenando, las colisiones serán cada vez más frecuentes,  
cuando eso suceda será necesario agrandar el tamaño del arreglo, para lo cual  
necesitaremos reorganizar TODOS los datos contenidos.

### Operaciones disponibles
- Inserción
- Borrado
- Búsqueda

Es un **arreglo** más una **función**.

### Función de Hash
Características deseables:
- Suprayectiva: Que los datos se mapeen a toda la tabla (aplicar módulo)
- Que se distribuya relativamente uniforme en todo el arreglo. 
- Determinista: Que siempre regrese el mismo output ante la misma entrada.
- Que tarde O(1)

### Manejo de colisiones
- Reubicación lineal: buscamos siguiente posición disponible.
- Reubicación cuadrática: siguiente posición en múltiplos de 2.
- Rehasheo
- Encadenamiento (esta es nuestra implementación)


## Bloom Filters

Básicamente es una hash table con booleanos.  
Es una estructura de datos probabilística.

#### Ventajas
- Rapidez
- Eficiencia en espacio

#### Desventajas
- No permite borrado
- Puede arrojar falsos positivos

### Operaciones Disponibles

#### Inserción
1. Recibimos el dato y lo hasheamos en tantas funciones como sean especificadas.
2. Convertimos cada hash a posiciones en el arreglo.
3. Convertimos esas posiciones a True.

#### Búsqueda
1. Recibimos el dato y lo hasheamos en tantas funciones como se había hecho.
2. Convertimos cada hash a posiciones en el arreglo.
3. Verificamos que todas esas posiciones sean positivas.

Llegará un momento en el que tengamos bastantes falsos positivos; será ahí cuando  
debamos agrandar el tamaño del arreglo.


## Skip Lists

Es un tipo de lista enlazada probabilística en la que tenemos varios niveles de  
listas enlazadas. La capa más baja está doblemente enlazada y contiene todos los  
elementos insertados, mientras que los niveles superiores solo contienen un  
subconjunto de estos elegidos aleatoriamente. 

En general tendremos log(n) listas.

### Operaciones Disponibles

#### Inserción
1. Buscamos la posición correcta
2. Lo insertamos en la lista de hasta abajo.
3. Decidimos si será insertado en una lista superior con probabilidad p.
   1. Si necesita estar arriba buscamos el siguiente elemento con alguno arriba 
más próximo y los conectamos.
      1. Si no hay ninguno que tenga arriba, creamos un nuevo nivel, con la
condición de que no puede haber más de log(n) listas.
   2. Si no necesita estar arriba hemos **terminado**.
4. Terminamos cuando por probabilidad ya no se necesite insertar más arriba o hayamos llegado al máximo de listas.

#### Búsqueda
1. Empezamos en la cabeza.
2. Comparamos con el elemento a la derecha.
   1. Si el elemento es mayor, bajamos y repetimos.
   2. Si el elemento es menor, nos movemos a la derecha y repetimos.
   3. Si el elemento es el buscado, bajamos hasta la última lista y regresamos el elemento en el que nos encontramos.

#### Eliminado
1. Buscamos el elemento.
2. Eliminamos las conexiones de sus nodos izquierdos y derechos.
3. Eliminamos las conexiones de sus nodos de arriba y sus respectivos vecinos.





