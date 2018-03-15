package kfdykme.funk.top

import android.content.Context

/**
 * Created by wimkf on 2018/3/10.
 */
class TopPresenter :TopContract.Presenter  {

    var view:TopContract.View? = null
    constructor(view:TopContract.View) {
        this.view = view
        view.setPresenter(this)
    }

    interface OnStartListener{
        fun onStart()
    }

    var onStartListener:OnStartListener? = null

    override fun start() {
        view!!.onLoad()
        onStartListener?.onStart()
    }

    override fun onAdd() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDelete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMove() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}