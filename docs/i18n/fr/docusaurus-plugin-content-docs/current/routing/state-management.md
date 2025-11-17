---
sidebar_position: 7
title: Gestion de l'état
_i18n_hash: e10d155e02722ea38419a79813a2f5af
---
Créer des expériences utilisateur dynamiques et fluides nécessite souvent que l'état de votre application web soit reflété dans l'URL et conservé lors des événements de navigation dans le navigateur. Vous pouvez y parvenir sans recharger la page en utilisant les mises à jour des paramètres d'URL et la gestion d'état de l'historique du navigateur. Cela garantit que les utilisateurs peuvent partager, ajouter aux favoris ou revenir à des vues spécifiques, l'application étant pleinement consciente de leurs interactions antérieures.

## Mise à jour de l'URL {#updating-the-url}

Lorsque l'état d'une page web change, comme lors du filtrage d'une liste de produits ou de la navigation à travers différentes vues, vous devez souvent que l'URL reflète ces changements. Vous pouvez utiliser les méthodes `replaceState` ou `pushState` fournies par la classe `BrowserHistory` pour manipuler l'URL sans recharger la page :

- **`pushState`** : Ajoute une nouvelle entrée à la pile d'historique du navigateur sans recharger la page. Cela est utile pour naviguer entre différentes vues ou contenus dynamiques.
- **`replaceState`** : Met à jour l'entrée actuelle dans l'historique du navigateur sans ajouter une nouvelle entrée. Cela est idéal pour mettre à jour l'état dans la même vue.

### Exemple : Mise à jour de l'URL avec des paramètres de requête {#example-updating-the-url-with-query-parameters}

Dans cet exemple, lorsque le bouton "Mettre à jour l'URL" est cliqué, l'interface utilisateur est mise à jour pour montrer la catégorie sélectionnée et le tri, et l'URL est mise à jour avec de nouveaux paramètres de requête pour `category` et `sort` :

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    Div div = getBoundComponent();
    div.add(update);
    div.add(paragraph);
  }

  public void filter(String category, String sort) {
    // mettre à jour l'UI
    updateUI(category, sort);

    // mettre à jour l'URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Affichage de la catégorie : " + category + " et tri par : " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Mettre à jour l'URL sans recharger la page
        .replaceState(null, newLocation);
  }
}
```

### Explication : {#explanation}

- **Méthode `filter`** : La méthode gère la mise à jour à la fois de l'UI et de l'URL en fonction de la `category` et du `sort` sélectionnés.
- **Méthode `updateUrl`** : Cette méthode crée un nouveau `ParametersBag` pour les paramètres de requête, construit une nouvelle URL, puis utilise `replaceState` pour mettre à jour l'URL du navigateur sans recharger la page.
- **`replaceState`** : Cette méthode change l'URL vers la nouvelle localisation tout en maintenant l'état actuel, sans provoquer un rechargement de la page.

## Sauvegarde et restauration d'état dans l'historique du navigateur {#saving-and-restoring-state-in-browser-history}

En plus de mettre à jour l'URL, il est possible de sauvegarder des objets d'état arbitraires dans l'historique du navigateur. Cela signifie que vous pouvez stocker des données supplémentaires relatives à la vue actuelle (par exemple : saisies de formulaires, filtres, etc.) sans les intégrer directement dans l'URL.

### Exemple : Sauvegarde de l'état de sélection {#example-saving-selection-state}

Dans l'exemple suivant, un `ProfileView` se compose de plusieurs onglets (Profil, Commandes et Paramètres). Lorsque l'utilisateur change d'onglet, l'état de l'onglet sélectionné est sauvegardé dans l'historique du navigateur à l'aide de `replaceState`. Cela permet à l'application de se souvenir du dernier onglet actif si l'utilisateur navigue de nouveau vers cette vue ou actualise la page.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profile");
    sections.addTab("Orders");
    sections.addTab("Settings");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Sauvegarder l'état en utilisant replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Essayer de récupérer la dernière section sauvegardée depuis l'état de l'historique du navigateur
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Si une section a été sauvegardée, restaurer la sélection de l'onglet
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Mettre à jour l'état actuel avec la section sélectionnée
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Explication : {#explanation-1}

1. **Composant TabbedPane** : La vue se compose d'un composant `TabbedPane`, qui a trois onglets : Profil, Commandes et Paramètres.
2. **Sauvegarde d'état lors du changement d'onglet** : Chaque fois qu'un onglet est sélectionné, l'index de la section actuelle est sauvegardé dans l'historique du navigateur à l'aide de la méthode `replaceState`.
3. **Restauration de l'état lors de la navigation** : Lorsque l'utilisateur revient au `ProfileView`, l'application récupère la section sauvegardée depuis l'historique à l'aide de `event.getState()` et restaure la sélection correcte de l'onglet.
