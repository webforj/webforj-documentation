---
sidebar_position: 10
title: Arquitectura de componentes
description: >-
  See how webforJ components are built on the DwcComponent base class, why
  component classes are final, and why composition replaces inheritance.
_i18n_hash: 13c29b780cb00a918fe6e0a98c5e783a
---
webforJ te ofrece una API de Java para una biblioteca de componentes web y administra cada uno desde el servidor. Esta página explica cómo se estructuran los componentes y cómo ampliarlos.

## Cómo se estructuran los componentes {#how-components-are-structured}
Cada componente de webforJ incluido en el producto es una clase de Java emparejada uno a uno con un componente web del lado del cliente de la biblioteca de componentes DWC. La clase de Java contiene el estado y la API del componente en el servidor, y el framework mantiene sincronizado con ella el componente web del navegador.

Solo trabajas con la clase de Java. El framework se encarga de las propiedades, los eventos y la representación en el navegador, así que puedes crear aplicaciones web usando únicamente Java.

## La clase base `DwcComponent` {#the-dwccomponent-base-class}
En el núcleo de la arquitectura de componentes de webforJ se encuentra la clase base DWCComponent. Todos los componentes de webforJ heredan de esta clase. Esta administra el vínculo entre el componente de Java y su contraparte del lado del cliente, incluidos la creación, las actualizaciones de propiedades y los eventos.

Sin embargo, es importante tener en cuenta que los desarrolladores no pueden seguir extendiendo la clase DWCComponent. Si se intenta hacerlo, se producirá una excepción en tiempo de ejecución que impedirá dicha extensión. Esta restricción permite mantener la integridad del vínculo y garantizar que los desarrolladores no lo manipulen inadvertidamente de formas que puedan provocar consecuencias imprevistas.

### Clases finales y restricciones de extensión {#final-classes-and-extension-restrictions}
En webforJ, la mayoría de las clases de componentes, con excepción de los elementos HTML integrados y las clases que los extienden, se declaran como `final`. Esto significa que no se pueden extender ni usar como superclases. Esta decisión de diseño es deliberada y tiene varios propósitos:

1. **Control del vínculo con el lado del cliente**: Como se mencionó anteriormente, extender las clases de componentes de webforJ expondría el vínculo interno entre el componente de Java y su contraparte del lado del cliente. Para mantener la coherencia y la previsibilidad del comportamiento de los componentes, se restringe este nivel de control.

2. **Prevención de modificaciones imprevistas**: Declarar las clases de componentes como `final` evita modificaciones involuntarias en los componentes principales y reduce el riesgo de introducir comportamientos inesperados o vulnerabilidades.

3. **Fomento del uso de componentes compuestos**: Para ampliar la funcionalidad de los componentes, el framework webforJ recomienda usar un enfoque basado en la composición. Los componentes compuestos son clases de Java que contienen otros componentes de webforJ o elementos HTML estándar. Aunque no se recomienda la herencia tradicional, los componentes compuestos permiten crear componentes nuevos y personalizados que encapsulan los existentes.

<!-- ## Añadir componentes al DOM
TODO: Explicar cómo webforJ resuelve la contraparte del lado del cliente de un componente -->

## Componentes compuestos: ampliar mediante composición {#composite-components-extending-through-composition}
En el framework webforJ, el concepto de componentes compuestos cumple un papel fundamental en la ampliación de la funcionalidad de los componentes. Los componentes compuestos son clases de Java que no están sujetas a la palabra clave final, lo que permite a los desarrolladores crear componentes nuevos que amplían el comportamiento de un solo componente o combinan varios en uno mediante la composición de componentes existentes. Se han creado clases que facilitan este comportamiento para que las usen los desarrolladores. Consulta las secciones `Composite` y `ElementComposite` para aprender a crear correctamente componentes compuestos.

Este enfoque fomenta un estilo de desarrollo más modular y flexible, que permite a los desarrolladores crear componentes a medida para satisfacer requisitos específicos.

<!--
## Jerarquía de componentes {#component-hierarchy}
TODO: Crear y mostrar un gráfico -->
