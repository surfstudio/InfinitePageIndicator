InfinitePageIndicator
=====================

The project shows how to make infinite ViewPager with the page indicator.
It is based on 2 other projects:

https://github.com/antonyt/InfiniteViewPager <br>
https://github.com/JakeWharton/Android-ViewPagerIndicator

For now only CirclePageIndicator is supported. How to adapt any other indicator for infinite scrolling? The easiest way to do it:<br>
1) Replace in the source code of the indicator all references to the method adapter.getCount() with adapter.getRealCount().<br>
2) Remove of modify onTouchEvent.<br>
3) Modify or disable page scrolling animation using setSnap(true).
