package kfdykme.funk.applist;

import android.content.*;
import android.content.pm.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.*;

import kfdykme.funk.R;
import android.net.*;
import kfdykme.funk.top.TopModel;
import kfdykme.funk.top.bean.AppInfo;


public class AppListPresenter implements AppListContract.Presenter
{


	
	AppListContract.View view;

	Thread loadThread;

	public AppListPresenter(AppListContract.View view){
		this.view = view;
		view.setPresenter(this);
	}

	@Override
	public void start()
	{

        final AppCompatActivity activity = (AppCompatActivity) view.getContext();

        if(loadThread == null)
        loadThread = new Thread(new Runnable() {

            @Override
			public void run() {


                if(!loadThread.isInterrupted())
				AppListModel.INSTANCE.load(view.getContext());

                if(!loadThread.isInterrupted())
                    activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						view.onLoad(AppListModel.INSTANCE.combine());

					}
				});

			}
		});

        loadThread.start();

	}

	@Override
	public void reflash()
	{

	}


    @Override
    public void onStop() {
        if(loadThread != null
                && !loadThread.isInterrupted())
            loadThread.interrupt();
    }

    @Override
    public void onAppLongClick(final View v, final ResolveInfo info)
    {
        PopupMenu popup = new PopupMenu(view.getContext(),v);
        popup.inflate(R.menu.applist_long_click_menu);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem p1)
            {

                final String SETTINGS_ACTION = "android.settings.APPLICATION_DETAILS_SETTINGS";

                switch(p1.getItemId()){
                    case R.id.detail:
                        Intent intent = new Intent()
                                .setAction(SETTINGS_ACTION)
                                .setData(Uri.fromParts("package",info.activityInfo.packageName,null));

                        view.getContext().startActivity(intent);

                        break;
                    case R.id.appAdd:
                        onAddAppToTop(info);

                        break;

                    case R.id.uninstall:
                        view.getContext()
                                .startActivity(
                                        new Intent(Intent.ACTION_DELETE
                                                , Uri.fromParts("package",info.activityInfo.packageName,null)));
                        break;
                }
                return false;
            }


        });
        popup.show();
    }


    @Override
    public void onAddAppToTop(ResolveInfo info) {
	    String packageName = info.activityInfo.packageName;
        TopModel.Companion.getINSTANCE().addInfo(new AppInfo(packageName));



    }

    @Override
    public void onAppClick(ResolveInfo info)
    {
        Intent i = view.getContext().getPackageManager().getLaunchIntentForPackage(info.activityInfo.packageName);

        view.getContext().startActivity(i);
    }

    @Override
    public void onIndexClick(int pos)
    {
        view.scrollTo(pos);
    }



}
