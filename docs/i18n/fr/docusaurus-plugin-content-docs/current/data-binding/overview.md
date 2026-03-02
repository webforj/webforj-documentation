---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: ba33283588df8722a31ad0c5fb15892a
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

 webforJ inclut une fonctionnalité de liaison de données qui intègre les composants de l'interface utilisateur avec les modèles de données backend dans les applications Java. Cette fonctionnalité comble le fossé entre l'UI et la couche de données, de sorte que les modifications de l'interface utilisateur se réfléchissent dans le modèle de données et vice versa, réduisant ainsi la complexité de la gestion des événements et de la synchronisation des données.

## Concept {#concept}

La démonstration suivante présente une application webforJ simple pour enregistrer des super-héros en utilisant la liaison de données webforJ. L'application se compose de deux parties principales : `HeroRegistration.java` et `Hero.java`. 

Dans `HeroRegistration.java`, le code configure l'interface utilisateur avec un `TextField` pour entrer le nom du héros, un `ComboBox` pour sélectionner un super pouvoir, et un `Button` pour soumettre l'enregistrement.

La classe `Hero` définit le modèle de données avec des contraintes de validation sur le nom et le pouvoir du héros. Les entrées doivent être valides et respecter des critères spécifiés tels que la longueur et le motif.

L'application utilise le `BindingContext` pour lier les composants de l'interface utilisateur aux propriétés de l'objet `Hero`. Lorsqu'un utilisateur clique sur le bouton de soumission, l'application écrit les données saisies dans le formulaire de retour au bean `Hero` si elles sont valides.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Text Field");
  private ComboBox power = new ComboBox("Power");
  private Button submit = new Button("Submit Application");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Fly", "Invisible", "LaserVision", "Speed", "Teleportation");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Fly");

    // reflect the bean data in the form
    context.read(bean);

    submit.onClick(e -> {
      // write the form data back to the bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // do something with the bean
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

  @NotEmpty(message = "Name cannot be empty")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unspecified power")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Invalid power")
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
    return "Name: " + name + ", Power: " + power;
  }
}
```

</TabItem>
</Tabs>

## Key features {#key-features}

- **Liaison Bidirectionnelle :**  Prend en charge la liaison de données bidirectionnelle, permettant aux modifications du modèle de données de mettre à jour l'interface utilisateur, et aux interactions des utilisateurs dans l'interface utilisateur de mettre à jour le modèle de données.

- **Support de Validation :** Intègre des mécanismes de validation complets que vous pouvez personnaliser et étendre. Les développeurs peuvent mettre en œuvre leurs propres règles de validation ou utiliser des frameworks de validation existants comme Jakarta Validation pour vérifier l'intégrité des données avant de mettre à jour le modèle.

- **Extensibilité :** Peut être facilement étendue pour prendre en charge différents types de composants de l'interface utilisateur, des transformations de données et des scénarios de validation complexes.

- **Configuration Basée sur Annotations :**  Utilise des annotations pour minimiser le code boilerplate, rendant les liaisons entre les composants de l'interface utilisateur et les modèles de données déclaratives et faciles à gérer.

# Topics

<DocCardList className="topics-section" />
