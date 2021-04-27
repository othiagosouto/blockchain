package dev.thiagosouto.blockchain.features.charts

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dev.thiagosouto.blockchain.features.charts.databinding.FeaturesChartsActivityChartsBinding
import org.koin.android.ext.android.inject

class ChartsActivity : FragmentActivity() {
    private val chartsProperties: ChartsProperties by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FeaturesChartsActivityChartsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.pager.adapter = ChartsAdapter(
            chartsProperties.ids,
            supportFragmentManager,
            lifecycle
        )

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = chartsProperties.titles[position]
        }.attach()
    }
}

internal class ChartsAdapter(
    private val ids: List<String>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = ids.count()

    override fun createFragment(position: Int): Fragment {
        return ChartFragment.newInstance(ids[position])
    }
}
