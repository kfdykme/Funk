package kfdykme.funk

import android.content.Context
import android.content.Intent
import android.view.View

interface BaseView<P : BasePresenter> {
    fun setPresenter(presenter: P)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}
