# Simulación de Línea de Producción con Concurrencia en Java

## Descripción
Este proyecto implementa una simulación de una línea de producción utilizando concurrencia en Java. Se modelan los distintos actores del sistema como Threads que interactúan con recursos compartidos representados por buzones de productos.

## Integrantes
- **Julian Mondragon**
- **Alejandro Hoyos**
- **Samuel Pena**

## Características principales
- **Productores:** Generan productos y los depositan en un buzón de revisión.
- **Equipo de Calidad:** Revisa productos y decide si aprueban o deben ser reprocesados.
- **Buzones sincronizados:** Se usan mecanismos de sincronización (`synchronized`, `wait`, `notifyAll`) para evitar condiciones de carrera.
- **Depósito:** Solo permite el almacenamiento de un producto a la vez.

## Ejecución
No se...

## Estructura del Proyecto
```
├── Producto.java         # Representa un producto en la línea de producción
├── Buzon.java            # Almacena productos con sincronización
├── Productor.java        # Genera productos y los deposita en el buzón de revisión
├── EquipoCalidad.java    # Inspecciona productos y los envía al depósito o reproceso
├── Main.java             # Punto de entrada de la aplicación
└── README.md             # Documentación del proyecto
```


