package x.app.service.account.interceptor

import org.axonframework.modelling.command.Repository
import x.app.service.account.Account
import x.app.utils.extension.IExtensionExecutor
import x.app.utils.extension.interceptor.AbstractExtensionInterceptor

/**
 *   @Project: service-account
 *   @Package: x.app.service.account.interceptor
 *   @Author:  Iamee
 *   @Date:    2019-05-01 0:09
 */
class AccountInterceptor(
        repository: Repository<Account>,
        executor: IExtensionExecutor
) : AbstractExtensionInterceptor<Account>(repository, executor)