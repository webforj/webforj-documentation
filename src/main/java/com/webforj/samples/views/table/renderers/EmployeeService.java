package com.webforj.samples.views.table.renderers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.utilities.Assets;
import java.util.List;

public final class EmployeeService {
  private EmployeeService() {}

  public static CollectionRepository<Employee> getEmployees() {
    List<Employee> data =
        new Gson()
            .fromJson(
                Assets.contentOf(Assets.resolveContextUrl("context://data/employees.json")),
                new TypeToken<List<Employee>>() {});
    return new CollectionRepository<>(data);
  }
}
