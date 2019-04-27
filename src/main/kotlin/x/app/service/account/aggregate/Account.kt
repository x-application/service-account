package x.app.service.account.aggregate

import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.commandhandling.model.AggregateMember
import org.axonframework.eventhandling.EventHandler
import org.axonframework.spring.stereotype.Aggregate
import x.app.common.account.event.AccountCreatedEvent

/**
 *   @Project: service-account
 *   @Package: x.app.service.account.aggregate
 *   @Author:  Iamee
 *   @Date:    2019-04-27 23:52
 */
@Aggregate
class Account {

    @AggregateIdentifier
    lateinit var id: String

    @AggregateMember
    lateinit var accountId: String

    @AggregateMember
    lateinit var accountType: String

    @AggregateMember
    lateinit var password: String

    constructor()

    constructor(accountId: String, accountType: String, password: String, time: Long) {
        AggregateLifecycle.apply(AccountCreatedEvent(
                accountId = accountId, accountType = accountType, password = password, time = time
        ))
    }

    @EventHandler
    fun on(event: AccountCreatedEvent) {
        this.id = event.getIdentifier()
        this.accountId = event.accountId
        this.accountType = event.accountType
        this.password = event.password
    }

}