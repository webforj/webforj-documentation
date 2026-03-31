package com.webforj.samples.views.flexlayout

import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.init
import com.webforj.samples.components.Box

fun HasComponents.box(boxNumber: Int, block: Box.() -> Unit) = init(Box(boxNumber), block)
