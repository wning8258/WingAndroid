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

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wing.android.R
import com.wing.android.databinding.ActivityMvvmBasicSampleBinding

/**
 * Shows a menu.
 */
class  MvvmBasicSampleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // DataBindingUtil.
        val binding: ActivityMvvmBasicSampleBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_mvvm_basic_sample)

        // The returned binding has references to all the Views with an ID.
        binding.observableFieldsActivityButton.setOnClickListener {
            startActivity(Intent(this, ObservableFieldActivity::class.java))
        }
        binding.viewmodelActivityButton.setOnClickListener {
            startActivity(Intent(this, ViewModelActivity::class.java))
        }
    }
}
