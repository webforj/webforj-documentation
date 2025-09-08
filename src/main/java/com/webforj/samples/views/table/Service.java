package com.webforj.samples.views.table;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.utilities.Assets;

public final class Service {

  private Service() {}

  public static CollectionRepository<MusicRecord> getMusicRecords() {
    List<MusicRecord> data =
        new Gson().fromJson(Assets.contentOf(Assets.resolveContextUrl("context://data/CDStore.json")),
            new TypeToken<List<MusicRecord>>() {});

    return new CollectionRepository<>(data);
  }
}
