package kfdykme.funk

import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout

import kfdykme.funk.applist.AppListContract
import kfdykme.funk.applist.AppListPresenter
import kfdykme.funk.applist.AppListView
import kfdykme.funk.top.TopContract
import kfdykme.funk.top.TopModel
import kfdykme.funk.top.TopPresenter
import kfdykme.funk.top.TopView
import kfdykme.funk.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_main.*
import xyz.kfdykme.view.ViewSizeChangeActivity

class MainActivity : AppCompatActivity() {
    private var appListPresenter: AppListContract.Presenter? = null
    private var topPresenter:TopContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

    override fun onResume() {
        super.onResume()
        addTop()
    }

    override fun onStop() {

        appListPresenter?.onStop()
        super.onStop()
    }

    fun addTop (){

        TopView.INSTANCE = supportFragmentManager.findFragmentById(R.id.fragTop) as TopView?

        if(TopView.INSTANCE ==null){
            TopView.INSTANCE  = TopView.newInstance()

            ActivityUtils.addFragmentToActivity(
                    supportFragmentManager, (TopView.INSTANCE  as TopView),R.id.fragTop
            )
            TopView.INSTANCE !!.horiGroup = horiGroup
        }

        TopModel.INSTANCE.context = this

        topPresenter = TopPresenter(TopView.INSTANCE !!)

        (topPresenter as TopPresenter)!!.onStartListener = object :TopPresenter.OnStartListener{
            override fun onStart() {

                addAppList()
            }
        }
    }

    fun addAppList(){

        AppListView.INSTANCE = supportFragmentManager.findFragmentById(R.id.fragAppList) as AppListView?
        if(AppListView.INSTANCE== null){
            AppListView.INSTANCE = AppListView.newInstance()

            ActivityUtils.addFragmentToActivity(
                    supportFragmentManager, (AppListView.INSTANCE as AppListView),R.id.fragAppList
            )
        }
        appListPresenter = AppListPresenter(AppListView.INSTANCE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == ViewSizeChangeActivity.VIEW_SIZE_CHANGE_RESULT)
            TopView.INSTANCE ?.onActivityResult(requestCode,resultCode,data)
    }


}
