package com.webforj.samples.views.applayout.applayout

import com.webforj.router.annotation.Route
import com.webforj.samples.views.applayout.AbstractContentView

@Route(value = "/content/:name", outlet = AppLayoutKotlinView::class)
class AppLayoutContentKotlinView : AbstractContentView()
