一个滑动悬浮置顶的View，通过自定义ScrollView来实现一个精美的固定悬浮效果

效果图：

![效果图](http://upload-images.jianshu.io/upload_images/4614633-8d8b14ee44e4444e.gif?imageMogr2/auto-orient/strip)


这个特效其实没有那么复杂！

思路：
- 自定义ListView对头布局进行处理
- 自定义 RecycleView 貌似很复杂的样子
- 自定义Behavior 把问题复杂化了
- 自定义listView + PopuWindows
- 自定义ViewGroup ，（需要重新onLayout登方法）
- 自定义Scrollview，对View进行处理

思来考去，其实我们写View的时候以少量的代码打造轮子才是精髓。于是对scrollView进行处理。明显的简单方面！！！

![ 未完全置顶的时候](http://upload-images.jianshu.io/upload_images/4614633-c29625e0fae14a43.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![ ](http://upload-images.jianshu.io/upload_images/4614633-9dc05704af091d66.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

通过广告栏的高度H和view中Y方向锁滑动的距离Y进行比较，从而对view进行处理

![完全置顶的时候 ](http://upload-images.jianshu.io/upload_images/4614633-9e57de0417e4f20f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![ 初始静态值](http://upload-images.jianshu.io/upload_images/4614633-90ccba3123e3514f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

关键代码：

重写onTouchEvent获取滑动的距离
```
/**
 * 重写onTouchEvent， 当用户的手在HoveringScrollview上面的时候，
 */
public boolean onTouchEvent(MotionEvent ev) {
		//直接将HoveringScrollview滑动的Y方向距离回调给onScroll方法中
		if (onScrollListener != null) {
			onScrollListener.onMyScroll(lastScrollY = this.getScrollY());
		}
		switch (ev.getAction()) {
			//当用户抬起手的时候，  HoveringScrollview可能还在滑动，
			// 所以当用户抬起手我们隔6毫秒给handler发送消息，
			// 在handler处理 HoveringScrollview滑动的距离
		case MotionEvent.ACTION_UP:
			handler.sendMessageDelayed(handler.obtainMessage(), 20);
			break;
		}
		return super.onTouchEvent(ev);
};
````

需要在Handler里面进行

```
 /**
* 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
*/
private Handler handler = new Handler() {

	public void handleMessage(android.os.Message msg) {
		int scrollY = HoveringScrollview.this.getScrollY();

		// 此时的距离和记录下的距离不相等，在隔6毫秒给handler发送消息?
		if (lastScrollY != scrollY) {
			lastScrollY = scrollY;
			handler.sendMessageDelayed(handler.obtainMessage(), 6);
		}
		if (onScrollListener != null) {
			onScrollListener.onMyScroll(scrollY);
		}
	};
};

```

重要的在监听回调里面做一些操作：

```

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
```

当广告栏的高亮 大于手指所华东的高度的时候，所指定固定悬浮的View在他本身父布局的View中，否则指定一个顶部的父容器添加在里面即可。

![效果图](http://upload-images.jianshu.io/upload_images/4614633-8d8b14ee44e4444e.gif?imageMogr2/auto-orient/strip)

#### 关于更多

[2017上半年技术文章集合—184篇文章分类汇总](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100000944&idx=1&sn=fce596a5fb1579efcebd6348787733d4&chksm=6b47682e5c30e138ee265562ecf0c4a7dad58c854ddedac42418616366f82de7a55e02034a99#rd)

[高级UI特效仿直播点赞效果—一个优美炫酷的点赞动画](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100000969&idx=1&sn=626d821d16346764fdce33e65f372031&chksm=6b4768575c30e14163ae8fb9f0406db0b3295ce47c4bc27b1df7a3abee1fa0bb71ef27b4e959#rd)

[一个实现录音和播放的小案例](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100000959&idx=1&sn=a5acb0f44fbadeaa9351df067438922c&chksm=6b4768215c30e1371a3c750f2b826f38b3a263c937272ae208717f73f92ed3e8fd8b6a674686#rd)

[NDK项目实战—高仿360手机助手之卸载监听](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100000934&idx=1&sn=b3f56d248793524288dfeb44c2131e61&chksm=6b4768385c30e12e37efcd796df9337b12f21653c3fcdf2f6d5233dbd9073cafe9bfeece215a#rd)

[玩转自定义柱形图—教你玩一把牛逼的  ](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100001094&idx=1&sn=64cb3e19dbaa7743e8154717a4740375&chksm=6b4769d85c30e0cef170e2fea241e067554e2d3646a60318b02aae37088b4fc9c582bf059fbe#rd)

[玩转雷达效果—一个炫酷的仿雷达扩散效果竟如此简单](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100001086&idx=1&sn=ab873271ce74dd2c3d18ca475740d36d&chksm=6b4769a05c30e0b632b6e71ea01e4ac72ec85cbf47826973019bb0813a58488e1a221955a1a9#rd)

[高级UI特效之仿3D翻转切换效果](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100001081&idx=1&sn=f9cd5f013d2bfe4fe374da9c9e95ff8b&chksm=6b4769a75c30e0b198019b313f9f7ea21336ffa75dc2a137d60a32be39a996500f1383928412#rd)

[手把手教你撸一个上下拉刷新控件](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100001076&idx=1&sn=4e58e76ab18c3f3dc954471ec22fedc1&chksm=6b4769aa5c30e0bc3c9f555cac3c409b71b660eaa28d56f06980591217ad30a875017dd30fe1#rd)


>
> 代码并没有很多，需要的同学可以下载github Demo体验和学习。
>
> 项目Github链接地址
>
>https://github.com/androidstarjack/MyCorverUpLoadApp
>
>下载慢？CSDN下载链接：
>
>http://download.csdn.net/detail/androidstarjack/9892366
>


#### 相信自己，没有做不到的，只有想不到的
 如果你觉得此文对您有所帮助，欢迎入群 QQ交流群 ：232203809
微信公众号：终端研发部

![技术+职场](http://upload-images.jianshu.io/upload_images/4614633-8af7afbca8ae9de9.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

