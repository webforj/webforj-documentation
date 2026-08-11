---
sidebar_position: 35
title: Filtering
slug: filtering
description: >-
  Narrow Table rows by applying a Predicate through the bound Repository,
  refreshing results on commit.
_i18n_hash: 4e0709a55b763f553eeb8ddb8a3abb32
---
De `Table`-component stelt je in staat om filtering toe te passen om weergegeven gegevens te verkleinen op basis van specifieke criteria. Filtering kan worden bereikt door een filtercriterium te definiëren met behulp van de `setFilter(Predicate<T> filter)`-methode die wordt aangeboden door de `Repository` die aan de tabel is gekoppeld.

Het volgende voorbeeld gebruikt een door de gebruiker gedefinieerd criterium uit het zoekveld en de `setBaseFilter()`-methode om een filter toe te passen op de `CollectionRepository` op basis van de titels van `MusicRecord`. Wanneer de `commit()`-methode wordt geactiveerd, wordt de tabel verfrist met de gefilterde gegevens.

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
De `setBaseFilter()`-methode behoort tot de `CollectionRepository`-klasse, niet de `Table`-component.
:::
