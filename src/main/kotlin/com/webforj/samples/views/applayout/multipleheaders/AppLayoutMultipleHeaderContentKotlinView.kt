package com.webforj.samples.views.applayout.multipleheaders

import com.webforj.router.annotation.Route
import com.webforj.samples.views.applayout.AbstractContentView

@Route(value = "/content/:name", outlet = AppLayoutMultipleHeadersKotlinView::class)
class AppLayoutMultipleHeaderContentKotlinView : AbstractContentView()
