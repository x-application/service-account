package x.app.service.account.handler

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.Repository
import x.app.common.AbstractResult
import x.app.common.CommonService
import x.app.common.account.command.CreateAccountCommand
import x.app.service.account.Account

/**
 *   @Project: service-account
 *   @Package: x.app.service.account.handler
 *   @Author:  Iamee
 *   @Date:    2019-04-27 23:55
 */
class AccountHandler(
        private val repository: Repository<Account>,
        private val commonService: CommonService
) {

    @CommandHandler
    fun handle(command: CreateAccountCommand): AbstractResult {
        repository.newInstance {
            Account(accountId = command.accountId, accountType = command.accountType, password = command.password, time = commonService.currentTimeMillis())
        }?.invoke { it }?.run { return CreateAccountCommand.Result(accountId = this.accountId) }
    }

}