---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 929625ea8b8335de7326ecb067dca773
---
El marco webforJ está diseñado para proporcionar una API Java alrededor del DWC del lenguaje BBj, ofrece una arquitectura robusta para construir y gestionar componentes.

## Mapeo de controles BBj a componentes webforJ {#mapping-bbj-controls-to-webforj-components}
Uno de los principios fundamentales de webforJ es la vinculación de controles BBj con componentes webforJ. En esta arquitectura, cada componente webforJ que se envía con el producto tiene un mapeo uno a uno con un control BBj subyacente. Este mapeo asegura que los componentes Java reflejen el comportamiento y las propiedades de sus contrapartes BBj sin problemas.

Esta estrecha correspondencia entre componentes webforJ y controles BBj simplifica el desarrollo y permite a los desarrolladores Java trabajar con conceptos familiares al construir aplicaciones basadas en la web sin necesidad de escribir código BBj.

## La clase base `DwcComponent` {#the-dwccomponent-base-class}
En el corazón de la arquitectura de componentes de webforJ se encuentra la clase base DWCComponent. Todos los componentes webforJ heredan de esta clase. Esta herencia otorga a cada componente webforJ acceso a su control BBj subyacente, proporcionando un enlace directo entre el componente Java y el control BBj que representa.

Sin embargo, es importante señalar que se restringe a los desarrolladores de extender la clase DWCComponent. Intentar hacerlo resultará en una excepción en tiempo de ejecución que impide tales extensiones. Esta restricción está establecida para mantener la integridad del control BBj subyacente y asegurar que los desarrolladores no lo manipulen inadvertidamente de maneras que puedan llevar a consecuencias no deseadas.

### Clases finales y restricciones de extensión {#final-classes-and-extension-restrictions}
En webforJ, la mayoría de las clases de componentes, con la excepción de los elementos HTML incorporados y cualquier clase que extienda estos, se declaran como `final`. Esto significa que no están disponibles para extensión o creación de subclases. Esta elección de diseño es deliberada y sirve a múltiples propósitos:

1. **Control sobre el control BBj subyacente**: Como se mencionó anteriormente, extender las clases de componentes webforJ otorgaría a los desarrolladores control sobre el control BBj subyacente. Para mantener la consistencia y la previsibilidad del comportamiento del componente, este nivel de control está restringido.

2. **Prevención de modificaciones no intencionadas**: Hacer que las clases de componentes sean `final` previene modificaciones involuntarias a los componentes centrales, reduciendo el riesgo de introducir comportamientos inesperados o vulnerabilidades.

3. **Promover el uso de compuestos**: Para extender la funcionalidad de los componentes, el marco webforJ alienta a los desarrolladores a utilizar un enfoque compuesto. Los componentes compuestos son clases Java que contienen otros componentes webforJ o elementos HTML estándar. Mientras que se desaconseja la herencia tradicional, los componentes compuestos ofrecen una manera de crear nuevos componentes personalizados que encapsulan los existentes.

## Componentes compuestos: extendiendo a través de la composición {#composite-components-extending-through-composition}
En el marco webforJ, el concepto de componentes compuestos desempeña un papel pivotal en la extensión de la funcionalidad de los componentes. Los componentes compuestos son clases Java que no están restringidas por la palabra clave final, lo que permite a los desarrolladores crear nuevos componentes que extienden el comportamiento de un solo componente, o combinan múltiples componentes en uno, mediante la composición de componentes existentes. Se han creado clases que facilitan este comportamiento para uso del desarrollador. Consulte las secciones `Composite` y `ElementComposite` para ver cómo crear componentes compuestos adecuadamente.

Este enfoque fomenta un estilo de desarrollo más modular y flexible, permitiendo a los desarrolladores construir componentes personalizados que cumplan con requisitos específicos.
