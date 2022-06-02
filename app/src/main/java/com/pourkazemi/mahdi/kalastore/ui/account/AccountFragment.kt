package com.pourkazemi.mahdi.kalastore.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.databinding.FragmentAccountBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private val binding: FragmentAccountBinding by viewBinding(
        FragmentAccountBinding::bind
    )
    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            logoutButton.setOnClickListener {
                accountViewModel.deleteCustomerFromDataBase(
                    Customer(
                        idDatabase.text.toString().toInt(),
                        emailDatabase.text.toString(),
                        firstNameDatabase.text.toString(),
                        lastNameDatabase.text.toString(),
                        userNameDatabase.text.toString()
                    )
                )
            }

            register.setOnClickListener {
/*                if (nameEdit.text.isNullOrBlank() &&
                    lastNameEdit.text.isNullOrBlank() &&
                    emailEdit.text.isNullOrBlank() &&
                    userNameEdit.text.isNullOrBlank() &&
                    (passwordEdit.text == rePasswordEdit.text && passwordEdit.text.isNullOrBlank())
                ) {*/
                accountViewModel.createCustomer(
                    Customer(
                        0,
                        emailEdit.text.toString(),
                        nameEdit.text.toString(), lastNameEdit.text.toString(),
                        passwordEdit.text.toString()
                    )
                )

            }
        }
        accountViewModel.createdUser.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    Timber.tag("mahdiTest").d("loading")
                }
                is ResultWrapper.Success -> {
                    accountViewModel.insertCustomer(it.value)
                    Timber.tag("mahdiTest").d("add to data base")
                }
                is ResultWrapper.Error -> {
                    Timber.tag("mahdiTest").d("create user error")
                }
            }
        }
        accountViewModel.dataBaseCustomer.collectIt(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.apply {
                    idDatabase.text = it[0].id.toString()
                    firstNameDatabase.text = it[0].first_name
                    lastNameDatabase.text = it[0].last_name
                    userNameDatabase.text = it[0].username
                    emailDatabase.text = it[0].email
                    availableAccount.visibility = View.VISIBLE
                    registerLayout.visibility = View.GONE
                }

            } else {
                binding.availableAccount.visibility = View.GONE
                binding.registerLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun <T> StateFlow<T>.collectIt(
        lifecycleOwner: LifecycleOwner,
        function: (T) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    function.invoke(it)
                }
            }
        }
    }
}