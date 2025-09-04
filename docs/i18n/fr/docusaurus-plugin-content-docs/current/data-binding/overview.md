---
sidebar_position: 1
title: Data Binding
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

 webforJ inclut une fonctionnalité de liaison de données qui intègre sans effort les composants UI avec les modèles de données backend dans les applications Java. Cette fonctionnalité comble le fossé entre l'interface utilisateur et la couche de données, garantissant que les changements dans l'interface utilisateur se reflètent dans le modèle de données et vice versa. En conséquence, elle améliore l'expérience utilisateur et réduit la complexité du traitement des événements et de la synchronisation des données.

## Concept {#concept}

La démonstration suivante présente une simple application webforJ pour l'enregistrement de super-héros utilisant la liaison de données webforJ. L'application se compose de deux parties principales : `HeroRegistration.java` et `Hero.java`.

Dans `HeroRegistration.java`, le code configure l'interface utilisateur avec un `TextField` pour entrer le nom du héros, un `ComboBox` pour sélectionner un super-pouvoir et un `Button` pour soumettre l'enregistrement.

La classe `Hero` définit le modèle de données avec des contraintes de validation sur le nom et le pouvoir du héros, garantissant que les entrées soient valides et respectent des critères spécifiés tels que la longueur et le motif.

L'application utilise le `BindingContext` pour lier les composants UI aux propriétés de l'objet `Hero`. Lorsque l'utilisateur clique sur le bouton de soumission, l'application écrit les données saisies dans le formulaire dans le bean `Hero` si elles sont valides.

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

## Caractéristiques clés {#key-features}

- **Liaison bidirectionnelle :** Prend en charge la liaison de données bidirectionnelle, permettant aux modifications du modèle de données de mettre à jour l'interface utilisateur, et aux interactions de l'utilisateur dans l'interface utilisateur de mettre à jour le modèle de données.

- **Support de validation :** Intègre des mécanismes de validation complets que vous pouvez personnaliser et étendre. Les développeurs peuvent mettre en œuvre leurs propres règles de validation ou utiliser des frameworks de validation existants comme Jakarta Validation pour assurer l'intégrité des données avant de mettre à jour le modèle.

- **Extensibilité :** Peut être facilement étendu pour prendre en charge différents types de composants UI, transformations de données et scénarios de validation complexes.

- **Configuration par annotation :** Utilise des annotations pour minimiser le code standard, rendant les liaisons entre les composants UI et les modèles de données déclaratives et faciles à gérer.

# Sujets

<DocCardList className="topics-section" />
