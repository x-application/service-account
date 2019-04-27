package x.app.service.account.test

import org.axonframework.common.IdentifierFactory
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import x.app.common.CommonService
import x.app.common.account.command.CreateAccountCommand
import x.app.common.account.event.AccountCreatedEvent
import x.app.service.account.aggregate.Account
import x.app.service.account.handler.AccountHandler

/**
 *   @Project: service-account
 *   @Package: x.app.service.account.test
 *   @Author:  Iamee
 *   @Date:    2019-04-27 23:59
 */
class AccountTest {

    private lateinit var fixture: AggregateTestFixture<Account>

    private lateinit var commonService: CommonService

    @Before
    fun setup() {
        commonService = object : CommonService {
            override fun currentTimeMillis(): Long {
                return 0L
            }

            override fun generateIdentifier(): String {
                return IdentifierFactory.getInstance().generateIdentifier()
            }
        }
        fixture = AggregateTestFixture(Account::class.java)
        fixture.registerAnnotatedCommandHandler(AccountHandler(fixture.repository, commonService))
    }

    @Test
    fun createAccount() {
        val accountId = "i@iamee.me"
        val accountType = "Email"
        val password = "123456"
        fixture
                .givenNoPriorActivity()
                .`when`(CreateAccountCommand(accountId, accountType, password))
                .expectEvents(AccountCreatedEvent(accountId, accountType, password, commonService.currentTimeMillis()))
                .expectState {
                    Assert.assertEquals(it.accountId, accountId)
                    Assert.assertEquals(it.accountType, accountType)
                    Assert.assertEquals(it.password, password)
                }
    }

}