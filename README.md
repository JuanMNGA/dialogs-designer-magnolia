![magnolia-logo](https://raw.githubusercontent.com/JuanMNGA/dialogs-designer-magnolia/master/dialogs-designer-project/dialogs-designer-magnolia/src/main/resources/img/header.png)

# DIALOGS DESIGNER

## Retos Digitales Magnolia Community 2019 [#RDMagnoliaCommunity](https://www.magnolia-cms.com/events/2019-retos-digitales-magnolia-community-es.html) :rocket:

Este repositorio recoge un módulo para la versión 6.0 de _Magnolia Community Edition (CE)_ para participar en el **Reto: Encuentra la Próxima Funcionalidad**
Se trata de una aplicación VAADIN, desarrollada a medida aprovechando las interacciones y el _look and feel_ de la plataforma.

## ¿Qué hace? :wrench:

Proporciona una Content-App para crear y diseñar diálogos en Magnolia. Ofrece un modo _Preview_ para probar el aspecto del diálogo en función del orden y propiedades de los campos.
También permite exportar el diálogo en formato Yaml, importable a través de la herramienta de importación de Magnolia.

## ¿Cómo funciona?

La aplicación se divide en tres regiones:
* Grid de campos (a la izquierda)
* Área de diálogo (centro)
* Tabla de propiedades (a la derecha)

* El usuario arrastrará (se ha implementado mediante Drag&Drop) cualquier campo del grid al área del diálogo.
* En el área del diálogo se mostrará una celda con el campo añadido y las propiedades en la tabla de la derecha, dependiendo del tipo de campo.

Las celdas se pueden ordenar con los botones de control que aparecen a la derecha de la celda, así como eliminar aquellos campos que no se deseen.
Para cambiar entre tablas de propiedades, sólo será necesario hacer click sobre la celda del campo, éste se sombreará en verde y cambiará la vista de la tabla a sus propiedades.

Arriba del área del diálogo existen dos botones, _Exportar yaml_ y _Preview_, con el botón de exportación, se nos abrirá la ventana para descargar el yaml generado. Con el botón de _Preview_ se mostrará una previsualización de cómo quedará el diálogo.

## Capturas :camera:

**Content-App**

![main-view](https://raw.githubusercontent.com/JuanMNGA/dialogs-designer-magnolia/master/dialogs-designer-project/dialogs-designer-magnolia/src/main/resources/img/captura1.png)

**Modo Preview**

![preview](https://raw.githubusercontent.com/JuanMNGA/dialogs-designer-magnolia/master/dialogs-designer-project/dialogs-designer-magnolia/src/main/resources/img/captura2.png)

**Exportación yaml**

![yaml](https://raw.githubusercontent.com/JuanMNGA/dialogs-designer-magnolia/master/dialogs-designer-project/dialogs-designer-magnolia/src/main/resources/img/captura3.png)
