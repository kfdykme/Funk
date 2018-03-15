package kfdykme.funk.top

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import kfdykme.funk.R
import kfdykme.funk.top.bean.AppViewHolder

import xyz.kfdykme.view.HoriGroup
import xyz.kfdykme.view.TopGroup
import xyz.kfdykme.view.TopViewHolder

/**
 * Created by wimkf on 2018/3/10.
 */
class TopView : Fragment(),TopContract.View {




    var mPresenter:TopContract.Presenter? = null


    override var horiGroup: HoriGroup? = null

    var topGroup: TopGroup? = null

    var hasLoaded = false

    override fun setPresenter(p: TopContract.Presenter) {
        mPresenter = p
    }


     override fun onLoad() {
         if(hasLoaded) return
        TopModel.INSTANCE.loadFromLocal()


         TopModel.INSTANCE.onDeleteListeners =object :TopModel.OnDeleteListener{
             override fun onDelete() {
                 onReflash()
             }
         }
         TopModel.INSTANCE.onAddListener =object :TopModel.onAddAppListener{
             override fun onAdd() {
                 onReflash()
             }
         }

         var apps:MutableList<TopViewHolder> = mutableListOf()

         Log.i("TopView","on Load")
        for(info in TopModel.INSTANCE.topInfo.appinfos!!){

            var view = LayoutInflater.from(context)
                    .inflate(R.layout.item_top_app,topGroup,false)

            var app = AppViewHolder(view,info)


            app.loadFromRealXY()

            app.onSaveRealListener  = object :TopViewHolder.OnSaveRealListener{
                override fun onSave() {

                    app.toAppInfo(info)
                    TopModel.INSTANCE.save()
                }
            }

            apps.add(app)

        }
        topGroup!!.setVHS(apps)
        hasLoaded =true
    }

    override fun onResume() {
        super.onResume()
        mPresenter!!.start()
    }

    override fun onReflash() {
        topGroup!!.removeAllViews()
        hasLoaded = false
        onLoad()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view:View = inflater!!.inflate(R.layout.frag_top,container,false)

        topGroup = view.findViewById(R.id.topGroup)

        return view
    }

    override fun onDetach() {
        super.onDetach()
        TopModel.INSTANCE.save()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i("TopView","on result")
        topGroup?.loadFromIntent(data!!)
        var app = TopModel.INSTANCE.findAppInfoById(TopViewHolder(data!!).id)

        (topGroup?.findViewHolderById(TopViewHolder(data!!).id)as AppViewHolder).toAppInfo(app!!)
        TopModel.INSTANCE.save()
    }

    companion object {
        var INSTANCE:TopView? = null

        fun newInstance():TopView{
            return TopView()
        }
    }

}