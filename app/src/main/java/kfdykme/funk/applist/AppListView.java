package kfdykme.funk.applist;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.support.v7.widget.*;
import java.util.*;
import android.content.pm.*;
import android.widget.ProgressBar;

import kfdykme.funk.R;
import xyz.kfdykme.view.HoriGroup;

public class AppListView extends Fragment implements AppListContract.View
{
	RecyclerView rv;

	AppListAdapter adapter;

	AppListIndexAdapter indexAdapter ;

	RecyclerView rvIndex;

	ProgressBar progressBar;

	AppListContract.Presenter presenter;

	HoriGroup horiGroup;

	public static AppListView newInstance() {

       return new AppListView();
    }

    public static AppListView INSTANCE = null;


    @Override
	public void setPresenter(AppListContract.Presenter presenter)
	{
		this.presenter = presenter;
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater
                .inflate(R.layout.frag_applist,null);

        rv = v.findViewById(R.id.rv);

        rvIndex =   v.findViewById(R.id.rvIndex);


        rvIndex.setLayoutManager(new LinearLayoutManager(getContext()));

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = v.findViewById(R.id.progressBar);

	    return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onReflash()
    {

    }

    @Override
	public void onLoad(List<Object> objects)
	{
		progressBar.setVisibility(View.GONE);
		indexAdapter= new AppListIndexAdapter(getContext());
		indexAdapter.setOnClickListener(new AppListIndexAdapter.OnItemClickListener(){

				@Override
				public void onClick(int pos)
				{
					presenter.onIndexClick(pos);
				}

			});
		rvIndex.setAdapter(indexAdapter);

		adapter = new AppListAdapter(getContext(),objects);
		rv.setAdapter(adapter);

		adapter.setOnItemClickListener(new AppListAdapter.OnItemClickListener(){

				@Override
				public void onLongClick(View v,int pos)
				{
					presenter.onAppLongClick(v,(ResolveInfo)adapter.data.get(pos));
				}

				@Override
				public void onClick(int pos)
				{
					presenter.onAppClick((ResolveInfo)adapter.data.get(pos));
				}
			});
	}

	@Override
	public void scrollTo(int pos)
	{
		rv.smoothScrollToPosition(adapter.getSort().get(pos));
	}



}
