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

import money.rbk.data.CreditCardType
import money.rbk.presentation.screen.base.BasePresenter
import money.rbk.presentation.utils.extensions.isDataValid
import money.rbk.presentation.utils.extensions.isEmailValid

class BankCardPresenter : BasePresenter<BankCardView>() {


    fun onBuyClick(cardNumber: String, cardDate: String, cardCcv: String, cardName: String, cardEmail: String) {
        //TODO on buy click
    }


    fun onEmail(email: String) =
        view?.showEmailValid(email.isEmailValid())


    fun onName(name: String) =
        view?.showNameValid(name.isNotEmpty())

    fun onDate(date: String) =
        view?.showDateValid(date.isDataValid())

    fun onCcv(name: String) =
        view?.showCcvValid(name.length == 3)

    fun onNumber(number: String) =
        view?.showNumberValid(number.isNotBlank() and (number.length == 19), defineCardType(number))

    private fun defineCardType(number: String): CreditCardType =
        CreditCardType.detect(number.replace("\\s".toRegex(), "").trim())
}