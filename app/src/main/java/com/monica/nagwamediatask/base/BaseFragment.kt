package com.monica.nagwamediatask.base

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(val vmClass: Class<VM>) : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM

    private var dialog: Dialog? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        injectDagger()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(vmClass)
        viewModel.internalState.observe(viewLifecycleOwner, Observer {
                renderView(it)
        })
        initView()
        setListeners()
        startRequest()
    }

    abstract fun injectDagger()

    abstract fun getLayout(): Int

    abstract fun renderView(viewState: BaseViewState?)

    abstract fun initView()

    abstract fun setListeners()

    abstract fun startRequest()


}