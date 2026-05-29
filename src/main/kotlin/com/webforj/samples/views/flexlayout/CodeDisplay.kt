package com.webforj.samples.views.flexlayout

import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.init
import com.webforj.samples.components.CodeDisplay

fun HasComponents.codeDisplay(block: CodeDisplay.() -> Unit) = init(CodeDisplay(), block)
