package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentFilterMainBinding


class FilterMainFragment : Fragment() {

    private var _binging: FragmentFilterMainBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binging = FragmentFilterMainBinding.inflate(inflater, container, false)
        return binding.root
    }


}
