package net.onlybiz.botchan.model.api.request

import net.onlybiz.botchan.model.api.request.BasicParameter

class LinkTokenParameter: BasicParameter() {
    lateinit var linkToken: String
}
