package kfdykme.funk.applist;
import android.content.Context;
import android.content.pm.*;
import java.util.*;

import kfdykme.funk.BasePresenter;
import kfdykme.funk.BaseView;

public interface AppListContract
{
	interface View extends BaseView<Presenter> {
		void onLoad(List<Object> resolveInfos);
		void onReflash();
		void scrollTo(int pos);
		Context getContext();
	}
	
	interface Presenter extends BasePresenter {
		void onAppClick(ResolveInfo info);
		void onAppLongClick(android.view.View view, ResolveInfo info);
		void onIndexClick(int pos);
		void reflash();
		void onAddAppToTop(ResolveInfo info);
		void onStop();
	}
	
}
