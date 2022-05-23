package com.poketmoney.ui.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.poketmoney.R
import com.poketmoney.api.models.entities.User
import com.poketmoney.api.models.responses.Offer18SignupResponse
import com.poketmoney.databinding.ActivitySignupBinding
import com.poketmoney.di.NetworkUtils
import com.poketmoney.di.SharedPreference
import com.poketmoney.ui.WebViewActivity
import com.poketmoney.utils.CommonUtils
import com.poketmoney.utils.Validation
import java.io.InputStream

/**
 * Bismillah
 * Created by Md. Afnan Haseeb on 22/02/22.
 */
class SignupActivity : AppCompatActivity(), SignupResponse {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel
    private var sharedPreference: SharedPreference? = null
    private lateinit var progressDialog: Dialog

    private var backPressed: Long = 0
    private var btnClicked: Int = 1
    private var mobileNo: String = ""
    private var firstName: String = ""
    private var lastName: String = ""
    private var email: String = ""
    private var referralCode: String = ""
    private var address: String = ""
    private var pincode: String = ""

    private var mProfileUri: Uri? = null
    private var imgProfile: AppCompatImageView? = null
    private var imageName: String = ""
    private lateinit var newUserReferralCode: String


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        signupViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        sharedPreference = SharedPreference(this)
        progressDialog = CommonUtils().showLoadingDialog(this)

        mobileNo = intent.getStringExtra("phone")!!
        binding.tvPhoneSignup.text = "+91 $mobileNo"
        imgProfile = binding.civSignup



        setupListeners()
        signupViewModel.referralCodeResponse.observe({ lifecycle }) {
            CommonUtils().hideLoading(progressDialog)
            if (it.status) {
                binding.acivDashed2Signup.setImageResource(R.drawable.ic_dashed_blue)
                binding.acivLocationSignup.setImageResource(R.drawable.ic_location_color)
                binding.rlMultiUserSignup.visibility = View.GONE
                binding.rlLocationSignup.visibility = View.VISIBLE
                btnClicked = 3
                binding.btnNextSignup.text = "Submit"
            } else {
                Snackbar.make(
                    binding.rlBodySignup,
                    "Referral Code Not Exists, please contact to 033-4804 8284.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        /*signupViewModel.offer18SignupResponse.observe({ lifecycle }) { offer18SignupResponse ->
        }*/
        signupViewModel.signupResponse.observe({ lifecycle }) {
            if (it.status) {
                newUserReferralCode = it.my_referral_code
                binding.rlProgressStatusSignup.visibility = View.GONE
                binding.cvFormSignup.visibility = View.GONE
                binding.btnNextSignup.visibility = View.GONE
                binding.rlSuccessSignup.visibility = View.VISIBLE
                binding.civSignup.isEnabled = false
                binding.ivCameraSignup.isEnabled = false

                CommonUtils().hideLoading(progressDialog)
            }
        }

        binding.civSignup.setOnClickListener {
            imagePicker()
        }
        binding.ivCameraSignup.setOnClickListener {
            imagePicker()
        }

        binding.btnNextSignup.setOnClickListener {
            when (btnClicked) {
                1 -> if (isValidateName()) {
                    binding.acivDashed1Signup.setImageResource(R.drawable.ic_dashed_blue)
                    binding.acivMultipleUserSignup.setImageResource(R.drawable.ic_multiple_user_color)
                    binding.rlSingleUserSignup.visibility = View.GONE
                    binding.rlMultiUserSignup.visibility = View.VISIBLE
                    btnClicked = 2
                }
                2 -> if (isValidate()) {
                    CommonUtils().hideKeyboard(this)
                    if (NetworkUtils().isNetworkConnected(this)) {
                        progressDialog.show()
                        // validate referral code
                        signupViewModel.checkReferralCode(referralCode)
                    } else {
                        Snackbar.make(
                            binding.rlBodySignup,
                            "Please connect to the internet!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                }
                3 -> if (isValidateAddress()) {
                    getEditTextValue()
                    CommonUtils().hideKeyboard(this)
                    progressDialog.show()
                    if (NetworkUtils().isNetworkConnected(this)) {
                        // send data to Offer18
                        signupViewModel.offer18Signup(
                            firstName,
                            lastName,
                            email,
                            mobileNo,
                            pincode,
                            this
                        )
                    } else {
                        Snackbar.make(
                            binding.rlBodySignup,
                            "Please connect to the internet!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        binding.btnEarnSignup.setOnClickListener {
            sharedPreference!!.mobile = mobileNo
            sharedPreference!!.myReferralCode = newUserReferralCode
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("phone", mobileNo)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        binding.btnShareSignup.setOnClickListener {
            val text =
                "<html>\n" +
                        "<body>\n" +
                        "<p>Download the Poket Money app to earn income for your good future.</p>\n" +
                        "<p>Use my referral code: $newUserReferralCode</p>\n" +
                        "<p>Check the below link to download the app from Google Play</p>\n" +
                        "<a herf=\"#\" >https://tinyurl.com/2ncavxmt</a>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>"
            val body: String =
                HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            CommonUtils().shareText("Poket Money", body, this)
        }
    }


    private fun setupListeners() {
        binding.tietFirstNameSignup.addTextChangedListener(TextFieldValidation(binding.tietFirstNameSignup))
        binding.tietLastNameSignup.addTextChangedListener(TextFieldValidation(binding.tietLastNameSignup))

        binding.tietEmailSignup.addTextChangedListener(TextFieldValidation(binding.tietEmailSignup))
        binding.tietReferralCodeSignup.addTextChangedListener(TextFieldValidation(binding.tietReferralCodeSignup))

        binding.tietAddressSignup.addTextChangedListener(TextFieldValidation(binding.tietAddressSignup))
        binding.tietPincodeSignup.addTextChangedListener(TextFieldValidation(binding.tietPincodeSignup))
    }

    private fun isValidateName(): Boolean =
        validateFirstName() && validateLastName()

    private fun isValidate(): Boolean =
        validateEmail() && validateReferralCode()

    private fun isValidateAddress(): Boolean =
        validateAddress() && validatePincode()

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        @SuppressLint("SetTextI18n")
        override fun afterTextChanged(s: Editable?) {
            when (view.id) {
                R.id.tiet_firstName_signup -> {
                    textChangedInMyTV()
                }
                R.id.tiet_lastName_signup -> {
                    textChangedInMyTV()
                }
                R.id.tiet_email_signup -> {
                    email = binding.tietEmailSignup.text.toString()
                    if (email == "") {
                        binding.tvEmailSignup.text = "xxxxxxxxx@xxxxx.xxx"
                    } else {
                        binding.tvEmailSignup.text = email
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        @SuppressLint("SetTextI18n")
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.tiet_firstName_signup -> {
                    validateFirstName()
                }
                R.id.tiet_lastName_signup -> {
                    validateLastName()
                }
                R.id.tiet_email_signup -> {
                    validateEmail()
                }
                R.id.tiet_referralCode_signup -> {
                    validateReferralCode()
                }
                R.id.tiet_address_signup -> {
                    validateAddress()
                }
                R.id.tiet_pincode_signup -> {
                    validatePincode()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun textChangedInMyTV() {
        firstName = binding.tietFirstNameSignup.text.toString()
        if (firstName == "" && lastName == "") {
            binding.tvNameSignup.text = "Your Full Name"
        } else {
            binding.tvNameSignup.text = "$firstName $lastName"
        }
    }

    private fun validateFirstName(): Boolean {
        if (binding.tietFirstNameSignup.text.toString().trim().isEmpty()) {
            binding.tilFirstNameSignup.error = "Required Field!"
            binding.tietFirstNameSignup.requestFocus()
            return false
        } else {
            binding.tilFirstNameSignup.isErrorEnabled = false
        }
        return true
    }

    private fun validateLastName(): Boolean {
        if (binding.tietLastNameSignup.text.toString().trim().isEmpty()) {
            binding.tilLastNameSignup.error = "Required Field!"
            binding.tietLastNameSignup.requestFocus()
            return false
        } else {
            lastName = binding.tietLastNameSignup.text.toString()
            binding.tilLastNameSignup.isErrorEnabled = false
        }
        return true
    }

    private fun validateEmail(): Boolean {
        if (binding.tietEmailSignup.text.toString().trim().isEmpty()) {
            binding.tilEmailSignup.error = "Required Field!"
            binding.tietEmailSignup.requestFocus()
            return false
        } else if (!Validation().isValidEmail(binding.tietEmailSignup.text.toString())) {
            binding.tilEmailSignup.error = "Invalid Email Address!"
            binding.tietEmailSignup.requestFocus()
            return false
        } else {
            email = binding.tietEmailSignup.text.toString()
            binding.tilEmailSignup.isErrorEnabled = false
        }
        return true
    }

    private fun validateReferralCode(): Boolean {
        if (binding.tietReferralCodeSignup.text.toString().trim().isEmpty()) {
            binding.tilReferralCodeSignup.error = "Required Field!"
            binding.tietReferralCodeSignup.requestFocus()
            return false
        } else {
            referralCode = binding.tietReferralCodeSignup.text.toString()
            binding.tilReferralCodeSignup.isErrorEnabled = false
        }
        return true
    }

    private fun validateAddress(): Boolean {
        if (binding.tietAddressSignup.text.toString().trim().isEmpty()) {
            binding.tilAddressSignup.error = "Required Field!"
            binding.tietAddressSignup.requestFocus()
            return false
        } else {
            address = binding.tietAddressSignup.text.toString()
            binding.tilAddressSignup.isErrorEnabled = false
        }
        return true
    }

    private fun validatePincode(): Boolean {
        if (binding.tietPincodeSignup.text.toString().trim().isEmpty()) {
            binding.tilPincodeSignup.error = "Required Field!"
            binding.tietPincodeSignup.requestFocus()
            return false
        } else if (!Validation().isValidPincode(binding.tietPincodeSignup.text.toString())) {
            binding.tilPincodeSignup.error = "Invalid Pincode!"
            binding.tietPincodeSignup.requestFocus()
            return false
        } else {
            pincode = binding.tietPincodeSignup.text.toString()
            binding.tilPincodeSignup.isErrorEnabled = false
        }
        return true
    }


    private fun imagePicker() {
        //For more feature, please follow https://github.com/Dhaval2404/ImagePicker
        ImagePicker.with(this)
            // Crop Square image
            .cropSquare()
            .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
            }
            .setDismissListener {
                Log.d("ImagePicker", "Dialog Dismiss")
            }
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!

                    mProfileUri = fileUri
                    imgProfile?.setImageURI(fileUri)
                    imageName = fileUri.pathSegments.last()
                    val iStream: InputStream? = contentResolver.openInputStream(fileUri)
                    val inputData: ByteArray? = CommonUtils().getBytes(iStream!!)
                    // upload image into PoketMoney Server
                    NetworkUtils().uploadImage(inputData!!, imageName, this)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    @SuppressLint("SetTextI18n")
    override fun onBackPressed() {
        if (btnClicked == 3 || btnClicked == 2) {
            when (btnClicked) {
                3 -> {
                    address = ""
                    pincode = ""
                    binding.acivLocationSignup.setImageResource(R.drawable.ic_location)
                    binding.acivDashed2Signup.setImageResource(R.drawable.ic_dashed_white)
                    binding.rlLocationSignup.visibility = View.GONE
                    binding.rlMultiUserSignup.visibility = View.VISIBLE
                    binding.btnNextSignup.text = "Next"
                    btnClicked = 2
                }
                2 -> {
                    email = ""
                    referralCode = ""
                    binding.acivMultipleUserSignup.setImageResource(R.drawable.ic_multiple_user)
                    binding.acivDashed1Signup.setImageResource(R.drawable.ic_dashed_white)
                    binding.rlMultiUserSignup.visibility = View.GONE
                    binding.rlSingleUserSignup.visibility = View.VISIBLE
                    btnClicked = 1
                }
            }
        } else {
            if (backPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed()
                finish()
            } else {
                Snackbar.make(
                    binding.rlBodySignup,
                    "Press once again to exit!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            backPressed = System.currentTimeMillis()
        }
    }


    private fun getEditTextValue() {
        firstName = binding.tietFirstNameSignup.text.toString()
        lastName = binding.tietLastNameSignup.text.toString()
        email = binding.tietEmailSignup.text.toString()
        referralCode = binding.tietReferralCodeSignup.text.toString()
        address = binding.tietAddressSignup.text.toString()
        pincode = binding.tietPincodeSignup.text.toString()
    }


    override fun onSuccess(value: Offer18SignupResponse) {
        val userCreds = User(
            phoneNumber = mobileNo,
            firstName = firstName,
            lastName = lastName,
            emailId = email,
            userImage = imageName,
            pinCode = pincode,
            fullAddress = address,
            referralCode = referralCode,
            affiliateId = value.affiliateId,
            apiAccess = value.apiAccess,
            apiKey = value.apiKey
        )
        // send data to PoketMoney Server
        signupViewModel.signupUser(userCreds)
    }

    override fun onError(throwable: Throwable) {
        CommonUtils().hideLoading(progressDialog)
        Snackbar.make(
            binding.rlBodySignup,
            "Email Id is already registered!",
            Snackbar.LENGTH_LONG
        ).show()
    }

}
