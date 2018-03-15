package kfdykme.funk.util

/**
 * Created by wimkf on 2018/3/10.
 */

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

class ActivityUtils{

    companion object {
        fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, @IdRes fragmentId:Int ){
            var transation = fragmentManager.beginTransaction()
            transation.add(fragmentId,fragment)
            transation.commit()
        }
        fun replaceFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, @IdRes fragmentId:Int ){
            var transation = fragmentManager.beginTransaction()
            transation.replace(fragmentId,fragment)
            transation.commit()
        }
    }
}