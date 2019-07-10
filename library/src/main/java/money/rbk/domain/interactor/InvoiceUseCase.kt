/*
 *
 * Copyright 2019 RBKmoney
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package money.rbk.domain.interactor

import money.rbk.di.Injector
import money.rbk.domain.converter.EntityConverter
import money.rbk.domain.converter.InvoiceChangesInvoiceStateConverter
import money.rbk.domain.entity.InvoiceEvent
import money.rbk.domain.extension.cost
import money.rbk.domain.interactor.base.UseCase
import money.rbk.domain.interactor.input.InvoiceInitializeInputModel
import money.rbk.domain.repository.CheckoutRepository
import money.rbk.domain.repository.GpayRepository
import money.rbk.presentation.model.InvoiceModel
import money.rbk.presentation.model.InvoiceStateModel

internal class InvoiceUseCase(
    private val repository: CheckoutRepository = Injector.checkoutRepository,
    private val gpayRepository : GpayRepository = Injector.gpayRepository,
    private val invoiceChangesConverter: EntityConverter<List<InvoiceEvent>, InvoiceStateModel> = InvoiceChangesInvoiceStateConverter()) :
    UseCase<InvoiceInitializeInputModel, InvoiceModel>() {

    override fun invoke(
        inputModel: InvoiceInitializeInputModel,
        onResultCallback: (InvoiceModel) -> Unit,
        onErrorCallback: (Throwable) -> Unit) {

        bgExecutor(onErrorCallback) {

            val invoice = repository.loadInvoice()
            repository.loadPaymentMethods()

            gpayRepository.init(invoice.shopID)

            val invoiceState = invoiceChangesConverter(repository.loadInvoiceEvents())

            val invoiceModel = InvoiceModel(
                invoice.id,
                repository.shopName,
                invoice.cost,
                invoice.product + invoice.description?.let { ". $it" }.orEmpty(),
                invoiceState
            )

            uiExecutor {
                onResultCallback(invoiceModel)
            }
        }
    }
}
