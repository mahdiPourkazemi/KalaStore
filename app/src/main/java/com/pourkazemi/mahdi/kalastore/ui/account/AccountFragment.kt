package com.pourkazemi.mahdi.kalastore.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isEmpty
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.databinding.FragmentAccountBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment(R.layout.fragment_account) {

    private val binding: FragmentAccountBinding by viewBinding(
        FragmentAccountBinding::bind
    )
    private val accountViewModel: AccountViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpClick()
        logOutClick()
        initDataBaseCustomer()

    }

    private fun initDataBaseCustomer() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                accountViewModel.dataBaseCustomer.collect {
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
                        Snackbar.make(
                            requireView(),
                            resources.getString(R.string.signUpFail),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }

    private fun signUpClick() {
        binding.apply {
            register.setOnClickListener {
                if (emailEdit.text.toString().isNotEmpty() &&
                    nameEdit.text.toString().isNotEmpty() &&
                    lastNameEdit.text.toString().isNotEmpty() &&
                    userNameEdit.text.toString().isNotEmpty() &&
                    passwordEdit.text.toString().isNotEmpty()
                ) {
                    accountViewModel.createCustomer(
                        Customer(
                            0,
                            emailEdit.text.toString(),
                            nameEdit.text.toString(),
                            lastNameEdit.text.toString(),
                            userNameEdit.text.toString(),
                            passwordEdit.text.toString()
                        )
                    )
                } else {
                    MaterialAlertDialogBuilder(requireActivity())
                        .setMessage(resources.getString(R.string.empetyField))
                        .setNegativeButton(resources.getString(R.string.decline)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    private fun logOutClick() {
        binding.apply {
            logoutButton.setOnClickListener {
                accountViewModel.deleteCustomerFromDataBase()
            }
        }
    }

    private fun <T> StateFlow<T>.collectIt(
        function: (T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    function.invoke(it)
                }
            }
        }
    }
}