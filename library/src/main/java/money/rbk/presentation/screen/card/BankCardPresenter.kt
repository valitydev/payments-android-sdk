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

package money.rbk.presentation.screen.card

import money.rbk.di.Injector
import money.rbk.domain.interactor.BaseUseCase
import money.rbk.domain.interactor.PaymentPrepareUseCase
import money.rbk.presentation.model.PaymentModel
import money.rbk.presentation.screen.base.BasePresenter

internal class BankCardPresenter(
    private val paymentPrepareUseCase: PaymentPrepareUseCase = PaymentPrepareUseCase(),
    private val paymentUseCase : BaseUseCase<PaymentModel> = Injector.resolveUseCase()

) : BasePresenter<BankCardView>() {

    override fun onViewAttached(view: BankCardView) {

    }

}