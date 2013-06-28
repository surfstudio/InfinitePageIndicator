package ru.surfstudio.infinitepageindicator.sample;

import ru.surfstudio.infinitepageindicator.InfiniteCirclePageIndicator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;

public class InfinitePagerActivity extends Activity {
    
    private InfiniteViewPager  mViewPager;
    private InfinitePagerAdapter mPagerAdapter;
    private InfiniteCirclePageIndicator mPageIndicator;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_infinite_pager_layout);
        initView();
    }
    
    private void initView() {
    	mViewPager = (InfiniteViewPager) findViewById(R.id.sample_infinite_pager);
    	mViewPager.setOffscreenPageLimit(3);
    	mPagerAdapter = new InfinitePagerAdapter(new NumbersAdapter(this));
    	// Show one page on the screen at a time
    	mPagerAdapter.setOneItemMode();
		mViewPager.setAdapter(mPagerAdapter);
		
    	mPageIndicator = (InfiniteCirclePageIndicator) findViewById(R.id.sample_infinite_page_indicator);
    	// Because animation between circles now looks ugly, we should disable it
    	mPageIndicator.setSnap(true);
    	mPageIndicator.setViewPager(mViewPager);
    }
    
    private class NumbersAdapter extends PagerAdapter {
    	
    	private LayoutInflater mInflater;
    	private int mColorRed;
    	private int mColorGreen;
    	private int mColorBlue;
    	
    	public NumbersAdapter(Context context) {
    		mInflater = LayoutInflater.from(context);
    		mColorRed = context.getResources().getColor(android.R.color.holo_red_dark);
    		mColorGreen = context.getResources().getColor(android.R.color.holo_green_dark);
    		mColorBlue = context.getResources().getColor(android.R.color.holo_blue_dark);
    	}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = getView(position);
			container.addView(view);
			return view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		private View getView(int position) {
			View pageView = mInflater.inflate(R.layout.infinite_pager_item, null);
			TextView pageText = (TextView) pageView.findViewById(R.id.infinite_pager_item_text_view);
			pageText.setText(String.valueOf(position + 1));
			switch(position) {
			case 0:
				pageText.setBackgroundColor(mColorRed);
				break;
			case 1:
				pageText.setBackgroundColor(mColorGreen);
				break;
			case 2:
				pageText.setBackgroundColor(mColorBlue);
				break;
			}
			return pageView;
		}
    	
    }
    
}