/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wing.android.mvvm.basicsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wing.android.mvvm.basicsample.data.ProfileLiveDataViewModel
import com.wing.android.R
import com.wing.android.databinding.ViewmodelProfileBinding

/**
 * This activity uses a [androidx.lifecycle.ViewModel] to hold the data and respond to user
 * actions. Also, the layout uses [androidx.databinding.BindingAdapter]s instead of expressions
 * which are much more powerful.
 *
 * @see com.example.android.databinding.basicsample.util.BindingAdapters
 */
class ViewModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain ViewModel from ViewModelProviders
//        val viewModel by viewModels<ProfileLiveDataViewModel>()

        // An alternative ViewModel using Observable fields and @Bindable properties can be used:
         val viewModel = ViewModelProvider(this)[ProfileLiveDataViewModel::class.java]

        // Obtain binding
        val binding: ViewmodelProfileBinding =
                DataBindingUtil.setContentView(this, R.layout.viewmodel_profile)

        // Bind layout with ViewModel
        binding.viewmodel = viewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this
    }
}
