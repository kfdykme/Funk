package kfdykme.funk.top.bean

import android.app.Activity
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Rect
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import kfdykme.funk.R
import kfdykme.funk.top.TopModel
import xyz.kfdykme.view.TopViewHolder
import xyz.kfdykme.view.ViewSizeChangeActivity

/**
 * Created by wimkf on 2018/3/12.
 */
class AppViewHolder:TopViewHolder{

    var resolveInfo:ResolveInfo

    var tv:TextView





    constructor(view: View,info:AppInfo):super(view){

        var text = info.resolveInfo!!.loadLabel(view.context.packageManager).toString()


        resolveInfo = info.resolveInfo!!
        tv = view.findViewById(R.id.tv)
        tv.text =text
        tv.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                view.context.startActivity(
                            view.context.packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
                    )
            }
        })

        tv.setOnLongClickListener (object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                mOnLongClickListener?.onLongClick(v)
                var popup = PopupMenu(tv!!.context,tv)
                popup.inflate(R.menu.top_app_long_click_menu)
                popup.setOnMenuItemClickListener(object :PopupMenu.OnMenuItemClickListener{
                    override fun onMenuItemClick(item: MenuItem?): Boolean {

                        when(item?.itemId){
                            R.id.edit ->{
                                var intent = Intent(view.context,ViewSizeChangeActivity::class.java)
                                toIntent(intent)
                                (view.context as Activity).startActivityForResult(intent,ViewSizeChangeActivity.VIEW_SIZE_CHANGE_REQUEST)
                            }
                            R.id.delete->{
                                TopModel.INSTANCE.deleteById(id)

                            }
                        }
                        return false
                    }
                })
                popup.show()
                return true
            }
        })
        tv.setOnTouchListener(mOnTouchListener)

        var w :Int
        var h :Int
        if(info.width == 0) w= text.length *3 else w = info.width
        if(info.height == 0) h=4 else h = info.height

        realX = info.x
        realY = info.y
        width =w
        height = h

    }

    fun toAppInfo(info :AppInfo){
        info.id = id
        info.x = x
        info.y = y
        info.width = width
        info.height = height
    }


}