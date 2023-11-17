package org.sopt.dosopttemplate.presentation.auth

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.presentation.main.MainActivity
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.extension.getParcelable
import org.sopt.dosopttemplate.util.extension.setOnSingleClickListener
import org.sopt.dosopttemplate.util.extension.snackBar
import org.sopt.dosopttemplate.util.extension.toast

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var signUpActivityLauncher: ActivityResultLauncher<Intent>

    private var backPressedTime: Long = 0

    override fun onStart() {
        super.onStart()
        if (viewModel.checkAutoLogin()) startMainActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSignUpActivityLauncher()
        initSignUpBtnListener()
        initLoginBtnListener()
        initOnBackPressedListener()
        observeSignedUserLoginState()
        observeServerUserLoginState()
    }

    private fun initSignUpActivityLauncher() {
        signUpActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.setSignedUser(
                    result.data?.getParcelable(EXTRA_USER, User::class.java)
                )
            }
        }
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            Intent(this, SignUpActivity::class.java).apply {
                signUpActivityLauncher.launch(this)
            }
        }
    }

    private fun initLoginBtnListener() {
        binding.btnLogin.setOnSingleClickListener {
            viewModel.setEditedUser(with(binding) {
                User(
                    id = etLoginId.text.toString().trim(),
                    pw = etLoginPw.text.toString().trim(),
                )
            })
            if (viewModel.isUserSigned()) {
                viewModel.checkSignedUserAvailable()
            } else {
                viewModel.checkServerUserAvailable()
            }
        }
    }

    private fun observeSignedUserLoginState() {
        viewModel.checkSignedUserState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is AuthState.IdError -> snackBar(binding.root) { getString(R.string.login_id_error) }

                is AuthState.PwError -> snackBar(binding.root) { getString(R.string.login_pw_error) }

                is AuthState.EmptyError -> snackBar(binding.root) { getString(R.string.login_empty_error) }

                is AuthState.Success -> {
                    toast(getString(R.string.login_success))
                    if (binding.cbAutoLogin.isChecked) viewModel.setAutoLogin(viewModel.getSignedUser())
                    startMainActivity()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun observeServerUserLoginState() {
        viewModel.checkServerUserState.observe(this) { state ->
            when (state) {
                is ServerState.Success -> {
                    toast(getString(R.string.login_success_from_server, state.data.uuid))
                    if (binding.cbAutoLogin.isChecked) viewModel.setAutoLogin(state.data)
                    startMainActivity()
                }

                is ServerState.Failure -> toast(getString(R.string.login_failure))

                is ServerState.ServerError -> toast(getString(R.string.server_error))

                is ServerState.Empty -> return@observe
            }
        }
    }

    private fun startMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
        finish()
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= BACK_INTERVAL) {
                    backPressedTime = System.currentTimeMillis()
                    toast(getString(R.string.back_btn_pressed))
                } else {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    companion object {
        const val EXTRA_USER = "USER"
        const val BACK_INTERVAL = 2000
    }
}