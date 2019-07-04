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

package money.rbk.domain.entity

import money.rbk.data.serialization.SealedDistributor
import money.rbk.data.serialization.SealedDistributorValue
import money.rbk.data.serialization.Serializable
import money.rbk.data.serialization.json
import org.json.JSONObject
import kotlin.reflect.KClass

sealed class PaymentTool(protected val paymentToolType: PaymentToolType) : Serializable {

    companion object {
        val DISTRIBUTOR = SealedDistributor("paymentToolType", PaymentToolType.values())
    }

    //TODO: Make it secured way
    data class CardData(
        val cardNumber: String,
        val expDate: String,
        val cvv: String,
        val cardHolder: String
    ) : PaymentTool(PaymentToolType.CardData) {

        override fun toJson(): JSONObject =
            JSONObject().apply {
                put("paymentToolType", paymentToolType)
                put("cardNumber", cardNumber)
                put("expDate", expDate)
                put("cvv", cvv)
                put("cardHolder", cardHolder)
            }
    }

    data class TokenizedCardData(
        val gatewayMerchantID: String,
        val paymentToken: PaymentToken,
        val provider: String

    ) : PaymentTool(PaymentToolType.TokenizedCardData) {

        override fun toJson() = json {
            "paymentToolType" set paymentToolType.name
            "provider" set provider
            "gatewayMerchantID" set gatewayMerchantID
            "paymentToken" set paymentToken
        }
    }

    enum class PaymentToolType(override val kClass: KClass<out PaymentTool>) :
        SealedDistributorValue<PaymentTool> {
        CardData(PaymentTool.CardData::class),
        TokenizedCardData(PaymentTool.TokenizedCardData::class)
    }

}
