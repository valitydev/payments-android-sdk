package money.rbk.presentation.model

import money.rbk.domain.entity.Currency

internal class CheckoutInfoModel(
    val formattedPriceAndCurrency: String,
    val price: String,
    val currency: Currency,
    val checkoutState: CheckoutStateModel
) : BaseIUModel()
