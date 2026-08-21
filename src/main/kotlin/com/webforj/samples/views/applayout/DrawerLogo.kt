package com.webforj.samples.views.applayout

import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init

fun @WebforjDsl HasComponents.drawerLogo(block: @WebforjDsl DrawerLogo.() -> Unit = {}) =
  init(DrawerLogo(), block)