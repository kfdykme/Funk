package kfdykme.funk.top

import android.content.Intent
import kfdykme.funk.BasePresenter
import kfdykme.funk.BaseView
import xyz.kfdykme.view.HoriGroup

interface TopContract {
    interface View : BaseView<Presenter> {

        var horiGroup: HoriGroup?
        fun onLoad()
        fun onReflash()
    }

    interface Presenter : BasePresenter {

        fun onAdd()
        fun onDelete()
        fun onMove()
    }

}
