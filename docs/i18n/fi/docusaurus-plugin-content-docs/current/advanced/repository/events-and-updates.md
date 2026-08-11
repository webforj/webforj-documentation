---
title: Events and updates
sidebar_position: 5
description: >-
  React to repository commits with RepositoryCommitEvent listeners, choose
  single-entity or bulk updates, and manage listener registrations.
_i18n_hash: 9ce2e7d25b38cd0d04acd30c80edffb3
---
<!-- vale off -->
# Tapahtumat ja päivitykset <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-tapahtumat antavat sinun reagoida tietomuutoksiin. Automaattisten käyttöliittymäpäivitysten lisäksi voit kuunnella muutoksia ja suorittaa mukautettua logiikkaa.

## `Repository`-tapahtuman elinkaari {#repository-event-lifecycle}

Jokainen `commit()`-kutsu laukaisee <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>-tapahtuman. Tämä tapahtuma sisältää tietoa siitä, mitä on muuttunut:

```java
repository.onCommit(event -> {
  // Hanki kaikki vahvistetut entiteetit
  List<Customer> commits = event.getCommits();

  // Tarkista, onko kyseessä yksittäisen entiteetin päivitys
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Päivitetty: " + updated.getName());
  }
});
```

Tapahtuma kertoo sinulle:
- `getCommits()` - Luettelo vahvistetuista entiteeteistä
- `isSingleCommit()` - Onko kyseessä kohdennettu yhden entiteetin päivitys
- `getFirstCommit()` - Kätevähkö metodi ensimmäisen (tai ainoan) entiteetin saamiseksi

Kun käytetään `commit()`-metodia ilman parametreja, tapahtuma sisältää kaikki entiteetit, jotka ovat tällä hetkellä käytettävissä tietovarastossa suodattamisen jälkeen.

## Päivitysstrategiat {#update-strategies}

Kaksi commit-allekirjoitusta palvelee eri tarkoituksia:

```java
// Yhden entiteetin päivitys - tehokas yksittäisille muutoksille
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Suuri päivitys - tehokas useille muutoksille
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Yhden entiteetin vahvistukset ovat kirurgisia - ne kertovat kytketyille komponenteille tarkalleen, mikä rivi muuttui. [`Table`](../../components/table/overview) voi päivittää vain kyseisen rivin solut koskematta mihinkään muuhun.

Suuret vahvistukset päivittävät kaiken. Käytä niitä, kun:
- Useita entiteettejä on muuttunut
- Olet lisännyt tai poistanut kohteita
- Et ole varma, mikä muuttui

## Reaktiiviset käyttöliittymämallit {#reactive-ui-patterns}

`Repository`-tapahtumat antavat sinun pitää yhteenveto-näytöt synkronoituna tietojesi kanssa:

```java
// Automaattisesti päivittyvät etiketit
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Yhteensä: $%.2f", total));
  countLabel.setText("Myynnit: " + sales.size());
});

// Reaalimaalitulosten laskenta
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " tuotetta löytyi");
});
```

Nämä kuuntelijat laukeavat jokaisella vahvistuksella, olipa kyseessä käyttäjän toimenpiteet, tietojen tuonnit tai ohjelmalliset päivitykset. Tapahtuma antaa sinulle pääsyn vahvistettuihin entiteetteihin, mutta usein lasket uusiksi alkuperäisestä kokoelmasta, jotta kaikki nykyiset tiedot sisällytetään.

## Muistin hallinta {#memory-management}

Tapahtumakuuntelijat pitävät viittauksia komponentteihisi. Jos et poista niitä, `Repository` pitää komponenttisi muistissa, vaikka niitä ei enää näytettäisikään:

```java
// Pidä viittaus myöhempää poistamista varten
ListenerRegistration<RepositoryCommitEvent<Data>> registration =
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Siivoa kuuntelija, kun komponentti tuhotaan
if (registration != null) {
  registration.remove();
}
```

`onCommit()`-metodi palauttaa `ListenerRegistration`:n. Tallenna tämä viittaus ja kutsu `remove()` silloin, kun komponenttisi tuhotaan tai ei enää tarvitse päivityksiä. Tämä estää muistivuotoja pitkään toimivissa sovelluksissa.
