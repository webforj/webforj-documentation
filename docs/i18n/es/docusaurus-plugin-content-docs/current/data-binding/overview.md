---
sidebar_position: 1
title: Vinculación de Datos
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 2ce381aec06e45ed4001e7dbfdb22dc0
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

 webforJ incluye una función de enlace de datos que integra sin problemas los componentes de la interfaz de usuario con los modelos de datos del backend en aplicaciones Java. Esta función cierra la brecha entre la interfaz de usuario y la capa de datos, asegurando que los cambios en la interfaz de usuario se reflejen en el modelo de datos y viceversa. Como resultado, mejora la experiencia del usuario y reduce la complejidad del manejo de eventos y la sincronización de datos.

## Concepto {#concept}

La siguiente demostración presenta una aplicación simple de webforJ para registrar superhéroes utilizando el enlace de datos de webforJ. La aplicación consta de dos partes principales: `HeroRegistration.java` y `Hero.java`.

En `HeroRegistration.java`, el código configura la interfaz de usuario con un `TextField` para ingresar el nombre del héroe, un `ComboBox` para seleccionar un superpoder y un `Button` para enviar el registro.

La clase `Hero` define el modelo de datos con restricciones de validación sobre el nombre y el poder del héroe, asegurando que las entradas sean válidas y cumplan con los criterios especificados, como longitud y patrón.

La aplicación utiliza el `BindingContext` para vincular los componentes de la interfaz de usuario a las propiedades del objeto `Hero`. Cuando un usuario hace clic en el botón de enviar, la aplicación escribe los datos ingresados en el formulario nuevamente en el bean `Hero` si son válidos.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Campo de Texto");
  private ComboBox power = new ComboBox("Poder");
  private Button submit = new Button("Enviar Solicitud");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Volador", "Invisible", "Visión Láser", "Velocidad", "Teletransportación");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Volador");

    // reflejar los datos del bean en el formulario
    context.read(bean);

    submit.onClick(e -> {
      // escribir los datos del formulario de vuelta al bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // hacer algo con el bean
        // repository.persist(bean)
      }
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Hero" label="Hero.java">

```java showLineNumbers
public class Hero {

  @NotEmpty(message = "El nombre no puede estar vacío")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Poder no especificado")
  @Pattern(regexp = "Volador|Invisible|Visión Láser|Velocidad|Teletransportación", message = "Poder inválido")
  private String power;

  public Hero(String name, String power) {
    this.name = name;
    this.power = power;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String toString() {
    return "Nombre: " + name + ", Poder: " + power;
  }
}
```

</TabItem>
</Tabs>

## Características clave {#key-features}

- **Vinculación bidireccional:** Soporta vinculación de datos bidireccional, permitiendo que los cambios en el modelo de datos actualicen la interfaz de usuario, y las interacciones del usuario en la interfaz de usuario actualicen el modelo de datos.

- **Soporte de validación:** Integra mecanismos de validación integrales que se pueden personalizar y extender. Los desarrolladores pueden implementar sus propias reglas de validación o utilizar marcos de validación existentes como Jakarta Validation para garantizar la integridad de los datos antes de actualizar el modelo.

- **Extensibilidad:** Puede ser fácilmente extendido para soportar diferentes tipos de componentes de interfaz de usuario, transformaciones de datos y escenarios de validación complejos.

- **Configuración guiada por anotaciones:** Utiliza anotaciones para minimizar el código de boilerplate, haciendo que las vinculaciones entre componentes de interfaz de usuario y modelos de datos sean declarativas y fáciles de gestionar.

# Temas

<DocCardList className="topics-section" />
