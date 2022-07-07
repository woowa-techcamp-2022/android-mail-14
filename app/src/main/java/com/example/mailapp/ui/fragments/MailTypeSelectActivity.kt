package com.example.mailapp.ui.fragments

import androidx.lifecycle.LiveData
import com.example.mailapp.model.MailModel

interface MailTypeSelectActivity {
    val mailType: LiveData<MailModel.MailType>
}