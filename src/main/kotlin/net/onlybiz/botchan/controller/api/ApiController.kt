package net.onlybiz.botchan.controller.api

import net.onlybiz.botchan.domain.TestRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired



@RestController
@RequestMapping(value = ["/"])
class ApiController {
    @Autowired
    lateinit var testRepository: TestRepository

    //@RequestMapping(value = ["/query"], method = [RequestMethod.POST])
    //fun queryResponse(@RequestBody query: QueryMessage): ResponseMessage {
    //    val response = dialogFlow.detectIntentTexts(query.queryMessage, query.session_id, query.eventName)
    //    return ResponseMessage(response, query.session_id)
    //}

    //@GetMapping
    //fun helloWorld(@RequestParam("query") queryMessage: String, @RequestParam("session_id") sessionId: String, @RequestParam("event_name") eventName: String?): ResponseMessage {
    //    return ResponseMessage(response, sessionId)
    //}

    @GetMapping
    fun helloWorld(): String {
        val test = testRepository.findById(1)
        return if (test.isPresent) test.get().name!! else "ないよ"
    }

}
