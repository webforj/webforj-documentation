package com.webforj.samples.views.table.renderers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.utilities.Assets;
import java.util.List;

public final class ServerService {
  private ServerService() {}

  public static CollectionRepository<Server> getServers() {
    List<Server> data =
        new Gson().fromJson(Assets.contentOf(Assets.resolveContextUrl("context://data/servers.json")),
            new TypeToken<List<Server>>() {});
    return new CollectionRepository<>(data);
  }
}