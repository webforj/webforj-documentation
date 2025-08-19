---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: 110061605b615701c1832988833fe959
---
De `Table` component stelt je in staat om filteringfunctionaliteit te implementeren om de weergegeven gegevens te verkleinen op basis van specifieke criteria. Filtering kan worden bereikt door een filtercriteria te definiëren met de `setFilter(Predicate<T> filter)` methode die wordt aangeboden door de `Repository` die aan de tabel is gekoppeld.

<ComponentDemo
path='/webforj/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

In het bovenstaande voorbeeld wordt de `setFilter()` methode gebruikt om een filtercriteria te definiëren op basis van de titel van `MusicRecord`.

:::tip
De `setFilter()` methode behoort tot de `Repository` klasse en is geen ingebouwde functionaliteit van de `Table` zelf.
:::

De filter wordt vervolgens toegepast wanneer de gebruiker de inhoud van het zoekveld wijzigt, waardoor de searchTerm wordt bijgewerkt en de `commit()` methode wordt geactiveerd om de weergegeven gegevens te vernieuwen.
