# QiaoWeather 用河南话来说就是瞧天气
这个软件使用MVP架构+Dagger2+Retrofit2+Rxjava2+Okhttp3来完成。后面会一一介绍各个部分。


这个项目使用MVP模式

在安卓当中如果在Main界面做太多耗时操作，会造成软件卡顿，所以有必要将数据处理工作和UI界面分离开，这就是使用MVP模式的原因

在MVP模式里通常包含4个要素：

(1)View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity);

(2)View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合，方便进行单元测试;

(3)Model:负责存储、检索、操纵数据(有时也实现一个Model interface用来降低耦合);

(4)Presenter:作为View与Model交互的中间纽带，处理与用户交互的负责逻辑。


使用Dagger2进行依赖注入

在使用MVP框架以后需要解决一个问题就是Presenter和一些处理模块（比如网络连接模块，图片下载模块，数据库模块）如何注入，使用Dagger2 以后View层不用关心Presenter如果实现，包括它的构造方法，只需要将Presenter注入即可，还有就是在Presenter当中如果要使用一些模块，也可以通过注入的方式实现，这样他们之间的依赖性会变小，各自互不干扰，有利于软件修改和维护，


使用RxJava2来解决异步请求

 Android 中可以用Rxjava用来处理所有异步操作，无论是网络、Android 自身的事件、数据库，还是 UI，使用它来编写代码，对这些数据源的变化做出响应，可以实现在newThread ，IO线程和主线程之间灵活切换
 
 每调用一次observeOn() 线程便会切换一次, 因此如果我们有类似的需求时, 便可知道如何处理了.


在RxJava2中, 已经内置了很多线程选项供我们选择, 例如有

Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作

Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作

Schedulers.newThread() 代表一个常规的新线程

AndroidSchedulers.mainThread() 代表Android的主线程



在Rxjava2中会有一个Disposable 对象，要记得及时清理。

 RxJava中已经内置了一个容器CompositeDisposable, 每当我们得到一个Disposable时就调用CompositeDisposable.add()将它添加到容器中, 在退出的时候, 调用CompositeDisposable.clear() 即可切断所有的水管



Retrofit2+OkHttp3来作为网络请求框架


使用retrofit可以进行GET，POST，PUT，DELETE等请求方式。添加输出网络请求和结果的 Log，当出现错误进行重新连接，同时可以对网络请求进行缓存。


使用Realm做数据库

Realm数据库有速度快，和跨平台的优点，安卓和iOS都可以使用

Realm支持Rxjava，不过目前只支持Rxjava1，对Rxjava2的支持正在开发当中


使用RxPermissions用于权限管理(适配android6.0）

RxPermission目前支持Rxjava2


使用Leakcanary来检查内存溢出，如果有内存溢出的情况，Leakcanary会有通知，通过分析Leakcanary给出的提升可以找出问题所在


使用Blockcanary来检查UI界面卡顿，Blockcanary可以精确到某一个类的某个方法，某一行代码，很厉害


使用Stetho来进行调试

在安卓当中查看sql数据库里面的数据，和SharedPreference里面的数据比较麻烦，用Stetho会很方便的解决这个问题。

Stetho还支持网络请求的查看，和抓包


推荐一个Log查看工具

  compile 'com.orhanobut:logger:1.15'
  
可以显示出在哪个类，哪个方法，哪一行。查看Log很方便


使用Butterknife来进行view注入

使用Butterknife用完以后记得及时清理

 if (unbinder != null && unbinder != Unbinder.EMPTY)
 {
            unbinder.unbind();

        }


在Recyclerview使用Multitype来加载不同类型的数据和不同类型的界面

    compile 'me.drakeet.multitype:multitype:2.4.1'
    
在Recyclerview的item里面可以有不同的数据，和不同的界面，包括item也可以是一个图表或者是一个Recylerview


使用Glide来进行图片加载功能

Glide加载完图片以后记得清理

  if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                Glide.clear(imageView);
            }
        }

        if (config.getTargets() != null && config.getTargets().length > 0) {//取消在执行的任务并且释放资源
            for (Target target : config.getTargets())
                Glide.clear(target);
        }


        if (config.isClearDiskCache()) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearDiskCache();
                        }
                    });

        }

        if (config.isClearMemory()) {//清除内存缓存
            Glide.get(ctx).clearMemory();
        }


使用AutoLayout框架,实现控件自适应

AutoLayout官方只默认提供了三个ViewGroup,AutoRelativeLayout,AutoLinearLayout,AutoFrameLayout实现了这些操作，在这个项目里面又依葫芦画瓢，添加了几个AutoAppBarLayout，AutoCardView等

对于屏幕适配，如果要自己做，可以参考下面这个网页

http://www.jianshu.com/p/ec5a1a30694b
