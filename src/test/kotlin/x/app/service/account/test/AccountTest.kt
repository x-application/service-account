package x.app.service.account.test

import org.axonframework.commandhandling.CommandMessage
import org.axonframework.common.IdentifierFactory
import org.axonframework.messaging.InterceptorChain
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.messaging.unitofwork.UnitOfWork
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import x.app.common.CommonService
import x.app.common.account.command.CreateAccountCommand
import x.app.common.account.event.AccountCreatedEvent
import x.app.service.account.Account
import x.app.service.account.handler.AccountHandler
import x.app.service.account.interceptor.AccountInterceptor
import x.app.utils.extension.DefaultExtensionExecutor
import x.app.utils.extension.IExtensionExecutor
import x.app.utils.extension.interceptor.AbstractExtensionInterceptor

/**
 *   @Project: service-account
 *   @Package: x.app.service.account.test
 *   @Author:  Iamee
 *   @Date:    2019-04-27 23:59
 */
class AccountTest {

    private lateinit var fixture: AggregateTestFixture<Account>

    private lateinit var commonService: CommonService

    private lateinit var executor: IExtensionExecutor

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
        executor = DefaultExtensionExecutor.createExecutor("x.app.service")
        fixture = AggregateTestFixture(Account::class.java)
        fixture.registerAnnotatedCommandHandler(AccountHandler(fixture.repository, commonService))
        fixture.commandBus.registerHandlerInterceptor(AccountInterceptor(repository = fixture.repository, executor = executor))
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

    @Test
    fun createAccountWithAccountAlreadyExistsException() {
        val accountId = "i@iamee.me"
        val accountType = "Email"
        val password = "123456"
        fixture
                .given(AccountCreatedEvent(accountId, accountType, password, commonService.currentTimeMillis()))
                .`when`(CreateAccountCommand(accountId, accountType, password))
                .expectSuccessfulHandlerExecution()
    }


}