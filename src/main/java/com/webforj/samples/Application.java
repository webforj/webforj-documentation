package com.webforj.samples;

import com.webforj.App;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.Routify;
import com.webforj.bundle.annotation.BundleEntry;

@Routify(packages = {"com.webforj.samples.views", "com.webforj.samples.blogs"})
@AppTitle("webforJ Samples")
@BundleEntry("css/app.css")
public class Application extends App {}
