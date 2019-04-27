package x.app.service.account.handler

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.Repository
import x.app.common.CommonService
import x.app.common.account.command.CreateAccountCommand
import x.app.service.account.aggregate.Account

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
    fun handle(command: CreateAccountCommand) {
        repository.newInstance {
            Account(command.accountId, command.accountType, command.password, time = commonService.currentTimeMillis())
        }
    }

}