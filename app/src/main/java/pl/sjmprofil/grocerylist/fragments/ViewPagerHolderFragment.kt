package pl.sjmprofil.grocerylist.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view_pager_holder.*

import pl.sjmprofil.grocerylist.R
import pl.sjmprofil.grocerylist.adapters.ViewPagerAdapter

class ViewPagerHolderFragment : Fragment() {

    private val remoteFragment = PrimaryFragment()
    private val localFragment = LocalFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager_holder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager(viewPager)

        // viewPager
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val viewPagerAdapter = ViewPagerAdapter(fragmentManager!!)
        viewPagerAdapter.addFragment(remoteFragment, "Nasza Lista")
        viewPagerAdapter.addFragment(localFragment, "Twoja Lista")
        viewPager.adapter = viewPagerAdapter
    }
}
