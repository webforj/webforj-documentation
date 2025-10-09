---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 32805132a2cf7b320864275fbbae7889
---
Ce tutoriel explique comment moderniser une application Java Swing existante en l'intégrant avec webforJ à l'aide du `WebswingConnector`. Vous apprendrez comment rendre une application de bureau traditionnelle accessible sur le web et ajouter progressivement des fonctionnalités web modernes telles que des boîtes de dialogue basées sur le web et des formulaires interactifs utilisant des composants webforJ.

:::tip Code source
Le code source complet de ce tutoriel est disponible sur GitHub : [webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

## Le scénario

Imaginez que vous ayez une application de gestion de clients construite avec Swing et qui est en production depuis des années. Elle fonctionne bien, mais les utilisateurs s'attendent maintenant à un accès web et à une interface moderne. Plutôt que de réécrire complètement l'application, vous utiliserez Webswing pour la rendre immédiatement accessible sur le web, puis ajouter progressivement des fonctionnalités web modernes telles que des boîtes de dialogue et des formulaires basés sur le web en utilisant des composants webforJ.

## Point de départ : l'application Swing

L'application Swing exemple est une table de clients avec des opérations CRUD typiques. Comme beaucoup d'applications Swing d'entreprise, elle suit des modèles standards :

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Nom", "Entreprise", "Email" };
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
        "Entreprise :", companyField,
        "Email :", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Éditer le client",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

Cette application fonctionne parfaitement en tant qu'application de bureau mais manque d'accès web. Les utilisateurs doivent installer Java et exécuter le fichier JAR localement.

## Étape 1 : la rendre consciente de Webswing

La première étape consiste à faire en sorte que l'application Swing détecte si elle fonctionne sous Webswing. Cela lui permet d'adapter son comportement sans rompre la compatibilité de bureau.

### Détection de l'environnement Webswing

Ajoutez la dépendance de l'API Webswing à votre projet Swing :

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

Ensuite, modifiez votre application pour détecter l'exécution de Webswing :

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

L'idée clé ici est que `WebswingUtil.getWebswingApi()` retourne `null` lors de l'exécution en tant qu'application de bureau classique, vous permettant de maintenir une compatibilité à double mode.

### Adapter le comportement pour le déploiement web

Avec la détection en place, vous pouvez maintenant adapter le comportement de l'application. Le changement le plus important concerne la manière dont les interactions des utilisateurs sont gérées :

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

En bifurquant le comportement selon la valeur de `isWebswing`, la base de code peut gérer les deux environnements.

## Étape 2 : créer le wrapper webforJ

Maintenant que l'application Swing peut communiquer via des événements, créez une application webforJ qui intègre l'application Swing et ajoute des fonctionnalités web modernes telles que des boîtes de dialogue et des formulaires basés sur le web.

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

### Gérer les événements depuis Swing

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

Désormais, au lieu de la boîte de dialogue Swing, les utilisateurs voient un formulaire web moderne construit avec des composants webforJ.

## Étape 3 : communication bidirectionnelle

L'intégration devient puissante lorsque la communication va dans les deux sens. L'application webforJ peut envoyer des mises à jour à l'application Swing, maintenant ainsi les deux interfaces utilisateur synchronisées.

### Envoi des mises à jour à Swing

Après que l'utilisateur ait modifié un client dans la boîte de dialogue webforJ :

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

Votre application Swing devient immédiatement accessible sur le web sans changements de code. Les utilisateurs peuvent y accéder via un navigateur pendant que vous travaillez sur des améliorations.

### Amélioration progressive

Commencez par remplacer uniquement la boîte de dialogue d'édition, puis remplacez progressivement plus de composants :

1. **Phase 1** : Intégrer l'ensemble de l'application Swing, remplacer uniquement la boîte de dialogue d'édition
2. **Phase 2** : Ajouter une navigation et des menus webforJ autour de l'application intégrée
3. **Phase 3** : Remplacer la table par une table webforJ, en gardant Swing pour les caractéristiques irremplaçables
4. **Phase 4** : En fin de compte, remplacer tous les composants Swing

### Atténuation des risques

Comme l'application Swing originale reste fonctionnelle, vous pouvez :

- Revenir à un déploiement de bureau si nécessaire
- Tester de nouvelles fonctionnalités aux côtés des existantes
- Migrer les utilisateurs progressivement
- Maintenir la même logique métier
