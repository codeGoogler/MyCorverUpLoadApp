package corver.yyh.com.mycorveruploadapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import corver.yyh.com.mycorveruploadapp.adapter.Mydapter;
import corver.yyh.com.mycorveruploadapp.impl.ProvicerData;
import corver.yyh.com.mycorveruploadapp.util.GetToast;
import corver.yyh.com.mycorveruploadapp.util.LogUtils;
import corver.yyh.com.mycorveruploadapp.view.HoveringScrollview;


 /**
 * 类功能描述：</br>
 * 上滑停靠顶端悬浮框,下滑恢复原有位置
 * 博客地址：http://blog.csdn.net/androidstarjack
 * 公众号：终端研发部
 * @author yuyahao
 * @version 1.0 </p> 修改时间：2017/10/12 </br> 修改备注：</br>
 */
public class HoveringScrollSampleActivity extends Activity implements
		HoveringScrollview.OnCustomserScrollListener {

	private HoveringScrollview hoveringScrollview;
	private int searchLayoutTop;

	LinearLayout hoveringLayout;
	LinearLayout search01, search02;
	RelativeLayout rlayout;
	private ListView lv_tontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_samples);
		initViews();
	}

	private void initViews() {
		hoveringLayout = (LinearLayout) findViewById(R.id.hoveringLayout);
		hoveringScrollview = (HoveringScrollview) findViewById(R.id.hoveringScrollview);
		search01 = (LinearLayout) findViewById(R.id.search01);
		search02 = (LinearLayout) findViewById(R.id.search02);
		lv_tontent = (ListView) findViewById(R.id.lv_tontent);
		rlayout = (RelativeLayout) findViewById(R.id.rlayout);
		hoveringScrollview.setOnScrollListener(this);
		Mydapter mydapter = new Mydapter(this, ProvicerData.getMyListData());
		lv_tontent.setAdapter(mydapter);
		setListViewHeightBasedOnChildren(lv_tontent);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			searchLayoutTop = rlayout.getBottom();// 获取searchLayout的顶部位置
			LogUtils.e("yuyahao","searchLayoutTop: " +searchLayoutTop);
		}
	}

	@Override
	public void onMyScroll(int scrollY) {
		LogUtils.e("yuyahao","scrollY: " +scrollY);
		// TODO Auto-generated method stub
		if (scrollY >= searchLayoutTop) {
			if (hoveringLayout.getParent() != search01) {
				search02.removeView(hoveringLayout);
				search01.addView(hoveringLayout);
			}
		} else {
			if (hoveringLayout.getParent() != search02) {
				search01.removeView(hoveringLayout);
				search02.addView(hoveringLayout);
			}
		}
	}

	public void clickListenerMe(View view) {
		if (view.getId() == R.id.btnQiaBuy) {
			GetToast.useString(this, "抢购成功");
		}
	}
	/**
	 * 动态设置ListView的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if(listView == null) return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
