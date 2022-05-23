package com.poketmoney.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.androidutillibrary.OtpView.AutoReadSmsUtil
import com.androidutillibrary.OtpView.OTPListener
import com.androidutillibrary.OtpView.OtpTextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.poketmoney.R
import com.poketmoney.databinding.ActivityMobileBinding
import com.poketmoney.ui.WebViewActivity
import com.poketmoney.ui.signup.SignupActivity
import com.poketmoney.utils.CommonUtils
import com.poketmoney.di.NetworkUtils
import com.poketmoney.di.SharedPreference
import com.poketmoney.utils.Validation
import java.lang.ref.WeakReference
import kotlin.math.max


/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 17/02/22.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMobileBinding
    private lateinit var otpTVBottomSheet: OtpTextView
    private var mobileNo: String = ""
    private var otpFromUser: String = ""
    private var userExist: Boolean = false
    private lateinit var myReferralCode: String

    private lateinit var loginViewModel: LoginViewModel
    private var sharedPreference: SharedPreference? = null

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        sharedPreference = SharedPreference(this)

        val progressDialog = CommonUtils().showLoadingDialog(this)
        setupListeners()

        loginViewModel.resendOtpResp.observe({ lifecycle }) {
            if (it.type == "success")
                Toast.makeText(
                    this@LoginActivity,
                    "The OTP is resend successfully.",
                    Toast.LENGTH_LONG
                ).show()
        }
        loginViewModel.loginResponse.observe({ lifecycle }) {
            userExist = it.status
            myReferralCode = it.myReferralCode
            CommonUtils().hideLoading(progressDialog)
        }


        binding.btnGoMobile.setOnClickListener {
            if (isValidate()) {
                CommonUtils().hideKeyboard(this)
                progressDialog.show()

                if (NetworkUtils().isNetworkConnected(this)) {
                    // send OTP to mobile
                    loginViewModel.sendOtp("91$mobileNo", this)
                    // check user exist
                    loginViewModel.fetchUserInfo(mobileNo)
                    val btnSheet = layoutInflater.inflate(R.layout.otp_bottom_sheet, null)
                    val otpText2BottomSheet: TextView =
                        btnSheet.findViewById(R.id.otp_text2_bottomSheet)
                    otpTVBottomSheet = btnSheet.findViewById(R.id.otpTV_bottomSheet)
                    val tvCountdownBottomSheet: TextView =
                        btnSheet.findViewById(R.id.tv_countdown_bottomSheet)
                    val materialBtnVerifyBottomSheet: MaterialButton =
                        btnSheet.findViewById(R.id.materialBtn_verify_bottomSheet)

                    val dialog = BottomSheetDialog(this)
                    dialog.setContentView(btnSheet)
                    otpText2BottomSheet.text =
                        "Enter the 4 digit code sent to your\nmobile number ${
                            mobileNo.substring(
                                0,
                                2
                            )
                        }******${mobileNo.substring(max(mobileNo.length - 2, 0))}"

                    AutoReadSmsUtil.setSmsListener(WeakReference(this))
                    otpTVBottomSheet.requestFocusOTP()
                    otpTVBottomSheet.otpListener = object : OTPListener {
                        override fun onInteractionListener() {
                            //Log.e("Afnan", "onInteractionListener")
                        }

                        override fun onOTPComplete(otp: String) {
                            otpFromUser = otp
                            verifyOTP()
                        }
                    }

                    object : CountDownTimer(60000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            tvCountdownBottomSheet.text =
                                "Resend OTP 00:" + millisUntilFinished / 1000
                            //here you can have your logic to set text to edittext
                        }

                        override fun onFinish() {
                            val text =
                                "<font color=#9e9e9e>Didn't receive OTP yet? </font> <font color=#223772><b>Resend OTP </b></font>"
                            tvCountdownBottomSheet.text =
                                HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
                            tvCountdownBottomSheet.setTextColor(
                                ContextCompat.getColor(
                                    applicationContext,
                                    R.color.grey
                                )
                            )
                            tvCountdownBottomSheet.setOnClickListener {
                                // resend OTP to mobile
                                loginViewModel.reSendOtp("91$mobileNo")
                            }
                        }
                    }.start()
                    materialBtnVerifyBottomSheet.setOnClickListener {
                        verifyOTP()
                        //dialog.dismiss()
                    }
                    dialog.show()
                    dialog.setCanceledOnTouchOutside(false)
                } else {
                    Snackbar.make(
                        binding.clBodyMobile,
                        "Please connect to the internet!",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupListeners() {
        /*binding.userName.addTextChangedListener(TextFieldValidation(binding.userName))
        binding.email.addTextChangedListener(TextFieldValidation(binding.email))
        binding.password.addTextChangedListener(TextFieldValidation(binding.password))
        binding.confirmPassword.addTextChangedListener(TextFieldValidation(binding.confirmPassword))*/
        binding.tietNumberMobile.addTextChangedListener(TextFieldValidation(binding.tietNumberMobile))
    }

    private fun isValidate(): Boolean =
        //validateUserName() && validateEmail() && validatePassword() && validateConfirmPassword()
        validateMobileNumber()

    /**
     * applying text watcher on each text field
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.tiet_number_mobile -> {
                    validateMobileNumber()
                }
            }
        }
    }

    /**
     * 1) field must not be empty
     * 2) text should matches email address format
     */
    private fun validateMobileNumber(): Boolean {
        if (binding.tietNumberMobile.text.toString().trim().isEmpty()) {
            binding.tilNumberMobile.error = "Required Field!"
            binding.tietNumberMobile.requestFocus()
            return false
        } else if (!Validation().isValidPhoneNumber(binding.tietNumberMobile.text.toString())) {
            binding.tilNumberMobile.error = "Invalid Mobile Number!"
            binding.tietNumberMobile.requestFocus()
            return false
        } else {
            mobileNo = binding.tietNumberMobile.text.toString()
            binding.tilNumberMobile.isErrorEnabled = false
        }
        return true
    }

    private fun verifyOTP() {
        if (sharedPreference!!.otp.equals(otpFromUser)) {
            if (userExist) {
                sharedPreference!!.mobile = mobileNo
                sharedPreference!!.myReferralCode = myReferralCode
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("phone", mobileNo)
                // set the new task and clear flags
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, SignupActivity::class.java)
                intent.putExtra("phone", mobileNo)
                startActivity(intent)
            }
        } else {
            Toast.makeText(
                this@LoginActivity,
                "Invalid OTP, please input the correct one!",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    /*override fun onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            smsBroadcastReceiver, IntentFilter(
                MySmsRetriever.SMSLOCALBROADCASTE
            )
        )
        super.onResume()
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsBroadcastReceiver)
        super.onPause()
    }


    private val smsBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(MySmsRetriever.SMSLOCALBROADCASTE)) {
                val data =
                    intent?.getBundleExtra("extra") // this give you all data or code related to sms
                val mess =
                    intent?.getStringExtra(MySmsRetriever.SMS_DATA) // if only want only message from sms then use this only
                otpTVBottomSheet.setOTP(mess?.getOtpCode(4)!!)
            }
        }
    }*/

}
