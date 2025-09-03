package com.webforj.samples;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.Routify;

@Routify(packages = {
  "com.webforj.samples.views",
  "com.webforj.samples.blogs"
})
@AppTitle("webforJ Samples")
public class Application extends App{
}
