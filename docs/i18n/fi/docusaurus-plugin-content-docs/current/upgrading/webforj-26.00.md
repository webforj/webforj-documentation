---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
draft: true
_i18n_hash: 505eca822769ef1f30adc7acca089fac
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämisessä versiosta 25.00 versioon 26.00. Tässä ovat tarvittavat muutokset olemassa oleville sovelluksille, jotta ne voivat toimia sujuvasti. Kuten aina, katso [GitHubin julkaisu-yhteenveto](https://github.com/webforj/webforj/releases) saadaksesi kattavamman listan muutoksista eri versioiden välillä.

## Suodattimet `Repository`-rajapinnassa

`RetrievalCriteria`-rajapinta on poistettu webforJ 26.00:sta, mukaan lukien `setFilter()`-menetelmä. Sen sijaan, että käyttäisit yleistä `Repository`-rajapintaa, käytä joko `CollectionRepository`-rajapintaa yksinkertaisille suodattimille tai [`QueryableRepository`](/docs/advanced/repository/querying-data) -rajapintaa edistyneille suodatus- ja lajittelutyypeille sekä sivutukselle.

### Ennen:
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```

### Jälkeen:
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
});
```
