---
title: Events and updates
sidebar_position: 5
_i18n_hash: 0f7a5576086e2922c0549eae23e66061
---
<!-- vale off -->
# Tapahtumat ja päivitykset <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-tapahtumat antavat sinun reagoida tietojen muutoksiin. Automaattisten UI-päivitysten lisäksi voit kuunnella muutoksia laukaistaksesi mukautettua logiikkaa.

## `Repository`-tapahtuman elinkaari {#repository-event-lifecycle}

Jokainen `commit()`-kutsu laukaisee <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>:n. Tämä tapahtuma sisältää tietoja siitä, mitä muutettiin:

```java
repository.onCommit(event -> {
  // Hanki kaikki sitoutuneet entiteetit
  List<Customer> commits = event.getCommits();
  
  // Tarkista, oliko kyseessä yksittäisen entiteetin päivitys
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Päivitetty: " + updated.getName());
  }
});
```

Tapahtuma kertoo sinulle:
- `getCommits()` - Lista entiteeteistä, jotka on sitoutettu
- `isSingleCommit()` - Olisiko tämä ollut kohdennettu yksittäisten entiteettien päivitys
- `getFirstCommit()` - Kätevä menetelmä ensimmäisen (tai ainoan) entiteetin saamiseksi

Jos `commit()`-kutsussa ei ole parametreja, tapahtuma sisältää kaikki entiteetit, jotka ovat tällä hetkellä varastossa suodattamisen jälkeen.

## Päivitysstrategiat {#update-strategies}

Kaksi sitoutumisen allekirjoitusta palvelevat eri tarkoituksia:

```java
// Yksittäisen entiteetin päivitys - tehokas yksittäisille muutoksille
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Massapäivitys - tehokas useille muutoksille
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Yksittäiset sitoutumiset ovat kirurgisia - ne kertovat yhteydessä oleville komponenteille tarkalleen, mikä rivi muuttui. [`Table`](../../components/table/overview) voi päivittää vain sen rivin solut koskematta muihin asioihin.

Massasitoutumiset päivittävät kaiken. Käytä niitä, kun:
- Useita entiteettejä on muuttunut
- Olet lisännyt tai poistanut kohteita
- Et ole varma, mitä on muuttunut

## Reaktiiviset UI-mallit {#reactive-ui-patterns}

`Repository`-tapahtumat antavat sinun pitää yhteenveto-näytöt synkronoituina tietosi kanssa:

```java
// Automaattisesti päivittävät etiketit
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Yhteensä: $%.2f", total));
  countLabel.setText("Myynnit: " + sales.size());
});

// Live-tuloslukemat
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " tuotetta löydetty");
});
```

Nämä kuuntelijat laukaisevat jokaisella sitoutumisella, riippumatta siitä, tulevatko ne käyttäjätoiminnoista, tietojen tuonnista vai ohjelmallisista päivityksistä. Tapahtuma antaa sinulle pääsyn sitoutettuihin entiteetteihin, mutta usein lasket uudelleen alkuperäisestä kokoelmasta sisällyttääksesi kaikki nykyiset tiedot.

## Muistinhallinta {#memory-management}

Tapahtumakuuntelijat pitävät viittauksia komponentteihisi. Jos et poista niitä, `Repository` pitää komponenttisi muistissa, vaikka niitä ei enää näytettäisi:

```java
// Pidä viittaus, jotta voit poistaa myöhemmin
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Siivoa kuuntelija, kun komponentti tuhotaan
if (registration != null) {
  registration.remove();
}
```

`onCommit()`-menetelmä palauttaa `ListenerRegistration`-olion. Tallenna tämä viittaus ja kutsu `remove()` kun komponenttisi tuhotaan tai ei enää tarvitse päivityksiä. Tämä estää muistivuotoja pitkäkestoisissa sovelluksissa.
