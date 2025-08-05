---
title: Project Setup
sidebar_position: 1
_i18n_hash: b1ac0a58b11558f40824c8caedeb95b3
---
En este tutorial, la aplicación se estructurará en **cuatro pasos**, cada uno introduciendo nuevas características a medida que avanza el proyecto. Al seguirlo, obtendrás una comprensión clara de cómo evoluciona la aplicación y cómo se implementa cada característica.

Para empezar, puedes descargar todo el proyecto o clonarlo desde GitHub:
<!-- vale off -->
- Descargar ZIP: [webforj-demo-application.zip](https://github.com/webforj/webforj-demo-application/archive/refs/heads/main.zip)
- Repositorio de GitHub: Clona el proyecto [directamente desde GitHub](https://github.com/webforj/webforj-demo-application)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-demo-application.git
```

Tanto el archivo ZIP como el repositorio de GitHub contienen la estructura completa del proyecto con los cuatro pasos, por lo que puedes comenzar en cualquier punto o seguir paso a paso.

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/project-setup.mp4" type="video/mp4"/>
  </video>
</div>

## Estructura del proyecto {#project-structure}

El proyecto se divide en cuatro directorios discretos, cada uno representando una etapa específica del desarrollo de la aplicación. Estos pasos te permiten ver cómo la aplicación evoluciona desde una configuración básica hasta un sistema de gestión de clientes totalmente funcional.

Dentro de la carpeta del proyecto, encontrarás cuatro subdirectorios, cada uno correspondiente a un paso en el tutorial:

```
webforj-demo-application
│   .gitignore
│   LICENSE
│   README.md
│   tree.txt
│
├───1-creating-a-basic-app  
├───2-working-with-data
├───3-scaling-with-routing-and-composites
└───4-validating-and-binding-data
```

### Ejecutando la aplicación {#running-the-app}

Para ver la aplicación en acción en cualquier etapa:

1) Navega al directorio del paso deseado. Este debe ser el directorio de nivel superior para ese paso, que contiene el `pom.xml`

2) Usa el plugin Maven Jetty para desplegar la aplicación localmente ejecutando:

```bash
mvn jetty:run
```

3) Abre tu navegador y navega a http://localhost:8080 para ver la aplicación.

Repite este proceso para cada paso mientras sigues el tutorial, permitiéndote explorar las características de la aplicación a medida que se agregan.
