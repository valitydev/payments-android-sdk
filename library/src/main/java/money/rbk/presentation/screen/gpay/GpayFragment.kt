package money.rbk.presentation.screen.gpay

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fmt_google_pay.*
import money.rbk.R
import money.rbk.di.Injector
import money.rbk.domain.interactor.input.GpayLoadPaymentDataInputModel
import money.rbk.presentation.activity.web.Web3DSecureActivity
import money.rbk.presentation.model.BrowserRequestModel
import money.rbk.presentation.screen.base.BaseFragment
import money.rbk.presentation.utils.setValid

internal class GpayFragment : BaseFragment<GpayView>(), GpayView {

    override val presenter: GpayPresenter by lazy { GpayPresenter(navigator) }

    private val userEmail = Injector.email

    companion object {
        fun newInstance() = GpayFragment()

        const val LOAD_PAYMENT_DATA_REQUEST_CODE = 123

    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fmt_google_pay, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edEmail.setText(userEmail)
    }

    override fun onReadyToPay(gpayLoadPaymentDataInputModel: GpayLoadPaymentDataInputModel) {
        btnPay.setOnClickListener {
            presenter.onPerformPayment(edEmail.text.toString(), gpayLoadPaymentDataInputModel)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        btnPay.isClickable = true
        when (requestCode) {
            Web3DSecureActivity.REQUEST_CODE -> presenter.on3DsPerformed(resultCode)
            LOAD_PAYMENT_DATA_REQUEST_CODE ->
                presenter.onGpayPaymentPerformed(resultCode, data, edEmail.text.toString())
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showEmailValid(isValid: Boolean) {
        edEmail.setValid(isValid)
    }

    override fun showRedirect(request: BrowserRequestModel) {
        startActivityForResult(Web3DSecureActivity.buildIntent(activity!!, request),
            Web3DSecureActivity.REQUEST_CODE)
    }

    override fun showProgress() {
        btnPay.isClickable = false
        pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        btnPay.isClickable = true
        pbLoading.visibility = View.GONE
    }

}
