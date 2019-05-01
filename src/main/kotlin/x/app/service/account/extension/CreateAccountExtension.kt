package x.app.service.account.extension

import org.axonframework.modelling.command.AggregateNotFoundException
import org.axonframework.modelling.command.Repository
import x.app.common.account.command.CreateAccountCommand
import x.app.service.account.Account
import x.app.utils.extension.annotation.Extension
import x.app.utils.extension.point.ICommandExtensionPoint

/**
 *   @Project: service-account
 *   @Package: x.app.service.account.extension
 *   @Author:  Iamee
 *   @Date:    2019-04-30 23:29
 */
@Extension(CreateAccountCommand::class)
class CreateAccountExtension : ICommandExtensionPoint<CreateAccountCommand, Account> {

    override fun before(repository: Repository<Account>, command: CreateAccountCommand) {
        println("==========before")
        try {
            repository.load(command.getIdentifier())
            throw Exception("already exists")
        } catch (ex: AggregateNotFoundException) {

        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun after(repository: Repository<Account>, command: CreateAccountCommand) {
        println("===========after")
        super.after(repository, command)
    }

}