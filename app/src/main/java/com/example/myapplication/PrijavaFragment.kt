package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class PrijavaFragment : Fragment(R.layout.fragment_prijava) {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    /*if(eposta.getText().toString().isEmpty()) {
        Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
    }else {
        if (emailId.getText().toString().trim().matches(emailPattern)) {
            Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
        }
    }*/
}