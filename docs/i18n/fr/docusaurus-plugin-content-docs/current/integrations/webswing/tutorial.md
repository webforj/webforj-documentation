---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: d4f256ba28ac621f2280bbd31575f6f1
---
Ce tutoriel vous guide à travers la modernisation d'une application Java Swing existante en l'intégrant avec webforJ en utilisant le `WebswingConnector`. Vous apprendrez comment rendre une application de bureau traditionnelle accessible sur le web et comment ajouter progressivement des fonctionnalités modernes du web telles que des dialogues basés sur le web et des formulaires interactifs en utilisant des composants webforJ.

:::tip Code source
Le code source complet de ce tutoriel est disponible sur GitHub : [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## Le scénario

Imaginez que vous avez une application de gestion de clients construite avec Swing qui est en production depuis des années. Elle fonctionne bien, mais les utilisateurs s'attendent maintenant à un accès web et une interface moderne. Plutôt que de tout réécrire, vous utiliserez Webswing pour la rendre immédiatement accessible sur le web, puis ajouter progressivement des fonctionnalités modernes telles que des dialogues basés sur le web et des formulaires en utilisant des composants webforJ.

## Point de départ : l'application Swing

L'exemple d'application Swing est un tableau de clients avec des opérations CRUD typiques. Comme beaucoup d'applications Swing d'entreprise, elle suit des modèles standard :

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Nom", "Société", "Email" };
    model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(model);
    table.setRowHeight(30);
    table.setRowSelectionAllowed(true);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          // Gérer le double-clic pour éditer
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Nom :", nameField,
        "Société :", companyField,
        "Email :", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Éditer le client",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Cette application fonctionne parfaitement comme une application de bureau mais n'a pas d'accessibilité web. Les utilisateurs doivent installer Java et exécuter le fichier JAR localement.

## Étape 1 : rendre l'application consciente de Webswing

La première étape consiste à faire détecter à l'application Swing si elle fonctionne sous Webswing. Cela lui permet d'adapter son comportement sans compromettre la compatibilité de bureau.

### Détection de l'environnement Webswing

Ajoutez la dépendance de l'API Webswing à votre projet Swing :

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Ensuite, modifiez votre application pour détecter le runtime Webswing :

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

L'idée clé ici est que `WebswingUtil.getWebswingApi()` retourne `null` lorsqu'elle est exécutée en tant qu'application de bureau normale, vous permettant de maintenir la compatibilité en mode double.

### Adapter le comportement pour le déploiement web

Avec la détection en place, vous pouvez maintenant adapter le comportement de l'application. Le changement le plus important concerne la façon dont les interactions des utilisateurs sont gérées :

```java
private void handleDoubleClick(MouseEvent e) {
  int row = table.rowAtPoint(e.getPoint());
  if (row >= 0 && row < customers.size()) {
    Customer customer = customers.get(row);

    if (isWebswing) {
      api.sendActionEvent("select-customer", gson.toJson(customer), null);
    } else {
      showEditDialog(customer);
    }
  }
}
```

En séparant le comportement selon la valeur de `isWebswing`, la base de code peut gérer les deux environnements.

## Étape 2 : créer le wrapper webforJ

Maintenant que l'application Swing peut communiquer via des événements, créez une application webforJ qui intègre l'application Swing et ajoute des fonctionnalités modernes du web telles que des dialogues et des formulaires basés sur le web.

### Configuration du connecteur

Le composant `WebswingConnector` intègre votre application hébergée par Webswing dans une vue webforJ :

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

Le connecteur se connecte à votre serveur Webswing, établissant un canal de communication bidirectionnel.

### Gestion des événements provenant de Swing

Lorsque l'application Swing envoie des événements (comme lorsqu'un utilisateur double-clique sur une ligne), le connecteur les reçoit :

```java
connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        JsonObject customer = JsonParser.parseString(data).getAsJsonObject();
        CustomerForm dialog = new CustomerForm(customer);
        self.add(dialog);
        dialog.onSave(() -> {
          Gson gson = new Gson();
          connector.performAction("update-customer", gson.toJson(customer));
        });
      });
      break;
  }
});
```

Maintenant, au lieu du dialogue Swing, les utilisateurs voient un formulaire web moderne construit avec des composants webforJ.

## Étape 3 : communication bidirectionnelle

L'intégration devient puissante lorsque la communication se fait dans les deux sens. L'application webforJ peut envoyer des mises à jour à l'application Swing, maintenant ainsi les deux interfaces synchronisées.

### Envoi de mises à jour à Swing

Après que l'utilisateur ait édité un client dans le dialogue webforJ :

```java
dialog.onSave(() -> {
  // Envoyer le client mis à jour à Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### Traitement des mises à jour dans Swing

L'application Swing écoute ces mises à jour et rafraîchit son affichage :

```java
private void setupWebswingListeners() {
  api.addBrowserActionListener(event -> {
    if ("update-customer".equals(event.getActionName())) {
      Customer updated = gson.fromJson(event.getData(), Customer.class);
      updateCustomer(updated);
    }
  });
}
```

## Avantages de l'architecture

Cette approche présente plusieurs avantages par rapport à une réécriture complète :

### Déploiement web immédiat

Votre application Swing devient immédiatement accessible sur le web sans modifications de code. Les utilisateurs peuvent y accéder via un navigateur pendant que vous travaillez sur des améliorations.

### Amélioration progressive

Commencez par remplacer seulement le dialogue d'édition, puis remplacez progressivement plus de composants :

1. **Phase 1** : intégrer l'ensemble de l'application Swing, remplacer uniquement le dialogue d'édition
2. **Phase 2** : ajouter la navigation et les menus webforJ autour de l'application intégrée
3. **Phase 3** : remplacer le tableau par un tableau webforJ, en gardant Swing pour les fonctionnalités irremplaçables
4. **Phase 4** : remplacer finalement tous les composants Swing

### Atténuation des risques

Étant donné que l'application Swing originale reste fonctionnelle, vous pouvez :

- Revenir au déploiement de bureau si nécessaire
- Tester de nouvelles fonctionnalités aux côtés des existantes
- Migrer les utilisateurs progressivement
- Maintenir la même logique métier
