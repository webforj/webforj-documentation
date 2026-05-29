---
sidebar_position: 7
title: State Management
_i18n_hash: 0766f2c08642792af2fe62e832b4fa1a
---
Créer des expériences utilisateur fluides et dynamiques nécessite souvent que l'état de votre application web soit reflété dans l'URL et conservé lors des événements de navigation dans le navigateur. Vous pouvez y parvenir sans recharger la page en profitant des mises à jour des paramètres URL et de la gestion de l'état de l'historique du navigateur. Cela garantit que les utilisateurs peuvent partager, ajouter à leurs favoris ou revenir à des vues spécifiques avec l'application pleinement consciente de leurs interactions précédentes.

## Mise à jour de l'URL {#updating-the-url}

Lorsque l'état d'une page web change, comme le filtrage d'une liste de produits ou la navigation à travers différentes vues, vous devez souvent faire en sorte que l'URL reflète ces changements. Vous pouvez utiliser les méthodes `replaceState` ou `pushState` fournies par la classe `BrowserHistory` pour manipuler l'URL sans recharger la page :

- **`pushState`** : Ajoute une nouvelle entrée à la pile d'historique du navigateur sans recharger la page. Cela est utile pour naviguer entre différentes vues ou contenu dynamique.
- **`replaceState`** : Met à jour l'entrée actuelle dans l'historique du navigateur sans ajouter de nouvelle entrée. Cela est idéal pour mettre à jour l'état au sein de la même vue.

### Exemple : Mise à jour de l'URL avec des paramètres de requête {#example-updating-the-url-with-query-parameters}

Dans cet exemple, lorsque le bouton "Mettre à jour l'URL" est cliqué, l'interface utilisateur est mise à jour pour montrer la catégorie sélectionnée et le tri, et l'URL est mise à jour avec de nouveaux paramètres de requête pour `category` et `sort` :

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  private final Div self = getBoundComponent();
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Mettre à jour l'URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    self.add(update);
    self.add(paragraph);
  }

  public void filter(String category, String sort) {
    // mettre à jour l'UI
    updateUI(category, sort);

    // mettre à jour l'URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Catégorie affichée : " + category + " et trié par : " + sort);
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
- **`replaceState`** : Cette méthode change l'URL vers la nouvelle localisation tout en maintenant l'état actuel, sans provoquer de rechargement de la page.

## Sauvegarde et restauration de l'état dans l'historique du navigateur {#saving-and-restoring-state-in-browser-history}

En plus de mettre à jour l'URL, il est possible de sauvegarder des objets d'état arbitraires dans l'historique du navigateur. Cela signifie que vous pouvez stocker des données supplémentaires liées à la vue actuelle (par exemple : les entrées de formulaire, les filtres, etc.) sans les intégrer directement dans l'URL.

### Exemple : Sauvegarde de l'état de sélection {#example-saving-selection-state}

Dans l'exemple suivant, un `ProfileView` consiste en plusieurs onglets (Profil, Commandes et Paramètres). Lorsque l'utilisateur passe d'un onglet à l'autre, l'état de l'onglet sélectionné est sauvegardé dans l'historique du navigateur à l'aide de `replaceState`. Cela permet à l'application de se souvenir du dernier onglet actif si l'utilisateur navigue à nouveau vers cette vue ou rafraîchit la page.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profil");
    sections.addTab("Commandes");
    sections.addTab("Paramètres");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Sauvegarder l'état en utilisant replaceState
      updateState(currentSection);
    });

    self.add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Essayer de récupérer la dernière section sauvegardée à partir de l'état de l'historique du navigateur
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
2. **Sauvegarde de l'état lors du changement d'onglet** : Chaque fois qu'un onglet est sélectionné, l'indice de la section actuelle est sauvegardé dans l'historique du navigateur à l'aide de la méthode `replaceState`.
3. **Restauration de l'état lors de la navigation** : Lorsque l'utilisateur navigue à nouveau vers le `ProfileView`, l'application récupère la section sauvegardée de l'historique à l'aide de `event.getState()` et restaure la sélection correcte de l'onglet.
