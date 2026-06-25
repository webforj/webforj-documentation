---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: e35c9b340f9faa796a4dbf5635f59495
---
De `Table`-component stelt je in staat om filters toe te passen om de weergegeven gegevens te verkleinen op basis van specifieke criteria. Filtering kan worden bereikt door een filtercriterium te definiëren met de `setFilter(Predicate<T> filter)`-methode die wordt geleverd door de `Repository` die aan de tabel is gekoppeld.

In het volgende voorbeeld wordt een door de gebruiker gedefinieerd criterium uit het zoekveld gebruikt en de `setBaseFilter()`-methode om een filter toe te passen op de `CollectionRepository` op basis van de titels van `MusicRecord`. Wanneer de `commit()`-methode wordt geactiveerd, wordt de tabel vernieuwd met de gefilterde gegevens.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablefiltering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableFilteringView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::note
De `setBaseFilter()`-methode behoort tot de `CollectionRepository`-klasse, niet tot de `Table`-component.
:::
