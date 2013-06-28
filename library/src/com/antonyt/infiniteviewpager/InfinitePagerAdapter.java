package com.antonyt.infiniteviewpager;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging
 * wrap-around.
 * 
 */
public class InfinitePagerAdapter extends PagerAdapter {

	private static final String TAG = "InfinitePagerAdapter";
	private static final boolean DEBUG = true;
	private static final float PAGE_WIDTH_DOUBLE_ITEMS = 0.5f;
	private static final float PAGE_WIDTH_SINGLE_ITEM = 1.0f;

	private PagerAdapter adapter;
	private boolean infinitePagesEnabled = true;
	private float pageWidth = PAGE_WIDTH_DOUBLE_ITEMS;

	public InfinitePagerAdapter(PagerAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public int getCount() {
		// warning: scrolling to very high values (1,000,000+) results in
		// strange drawing behaviour
		if (infinitePagesEnabled) {
			return Integer.MAX_VALUE;
		} else { 
			return adapter.getCount();
		}
	}
	
	@Override 
	public float getPageWidth(int position) { 
		return pageWidth;
	}
	
	public void setOneItemMode() { 
		pageWidth = PAGE_WIDTH_SINGLE_ITEM;
	}
	
	public void setTwoItemsMode() { 
		pageWidth = PAGE_WIDTH_DOUBLE_ITEMS;
	}

	/**
	 * @return the {@link #getCount()} result of the wrapped adapter
	 */
	public int getRealCount() {
		if (infinitePagesEnabled)
			return adapter.getCount();
		else {
			int count = adapter.getCount();
			if (count == 1 || count == 2)
				return 1;
			else
				return count;
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		int virtualPosition = getVirtualPosition(position);
		debug("instantiateItem: real position: " + position);
		debug("instantiateItem: virtual position: " + virtualPosition);

		// only expose virtual position to the inner adapter
		return adapter.instantiateItem(container, virtualPosition);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		int virtualPosition = getVirtualPosition(position);
		debug("destroyItem: real position: " + position);
		debug("destroyItem: virtual position: " + virtualPosition);

		// only expose virtual position to the inner adapter
		adapter.destroyItem(container, virtualPosition, object);
	}

	/*
	 * Delegate rest of methods directly to the inner adapter.
	 */

	@Override
	public void finishUpdate(ViewGroup container) {
		adapter.finishUpdate(container);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return adapter.isViewFromObject(view, object);
	}

	@Override
	public void restoreState(Parcelable bundle, ClassLoader classLoader) {
		adapter.restoreState(bundle, classLoader);
	}

	@Override
	public Parcelable saveState() {
		return adapter.saveState();
	}

	@Override
	public void startUpdate(ViewGroup container) {
		adapter.startUpdate(container);
	}
	
	public int getVirtualPosition(int position) {
		return infinitePagesEnabled ? position % getRealCount() : position;
	}
	
	public void enableInfinitePages(boolean enable) {
		infinitePagesEnabled = enable;
	}

	/*
	 * End delegation
	 */

	private void debug(String message) {
		if (DEBUG) {
			Log.d(TAG, message);
		}
	}
}
