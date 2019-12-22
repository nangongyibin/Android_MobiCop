# 手机卫士


## 1.1	 项目功能介绍

手机卫士这个项目，是一个融合了很多Android知识点的综合应用，它采用MVC框架结构思想，用到的核心技术包括activity,service,broadcast,content provider, 数据库,自定义title，自定义控件，自定义toast、widget等。

### 1.1.1	主界面

手机卫士项目有很多界面，当用户通过点击应用图标打开应用时，通常第一个呈现给我们的是一个启动界面，我们这里称为Splash界面（该界面的相关内容在后面讲解）。启动界面之后的第二个界面通常才是主界面。手机卫士的主界面是一个类似九宫格的界面，它的每一个格子代表的一项功能模块，具体如图1-1所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)

### 1.1.2	进程管理

进行管理功能，主要用于显示当前手机运行的进程信息，并且该功能可以单个或批量杀死进程，并设置是否显示系统进程以及锁屏自动清理进程，进程管理的部分截图如图1-2所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/2.png?raw=true)


![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/3.png?raw=true)

### 1.1.3	手机杀毒

手机杀毒功能，主要是通过扫描用户手机，通过和本地病毒库的对比，根据程序的包名和程序的数字签名识别病毒,提示用户并查杀，让恶意软件，吸费木马无处可藏，手机杀毒的效果如图1-3所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/4.png?raw=true)

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/5.png?raw=true)

### 1.1.4	常用工具

手机卫士的常用工具，包含了自动IP拨号、手机号码归属地查询、更改归属地位置，常用号码查询等功能，具体效果如图1-4所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/6.png?raw=true)

### 1.1.5	 设置中心

设置中心，主要用于设置某些功能模块，比如自动更新的设置、骚扰拦截的设置、归属地显示的设置、归属地风格设置等。设置中心的界面效果如图1-10所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/7.png?raw=true)

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/8.png?raw=true)

## 1.2	SplashActivity界面版本更新

应用开启时需告知用户是否需要版本更新，通常做法为应用程序本地版本号和服务器端版本号比对，如本地版本号小于服务端版本号，则以弹出对话框的形式提示用户更新。

### 1.2.1	SplashActivity界面展示

SplashActivity即闪屏界面，用于显示应用logo和项目版本号，具体如图1-11所示。
![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/9.png?raw=true)

从图1-11中可以看出，Splash界面展示了当前应用的名称，该版本名称可从应用AndroidManifest.xml清单配置文件中获取，android:versionName="1.0"属性指向的就是应用的版本名称。通过PackageManager相关api可获取此版本信息，其实现过程如下所示：

            //1.PackageManager包的管理者对象
        PackageManager pm = getPackageManager();
        //2.获取应用的配置信息,在此处传递0获取的是基本信息(包名,版本名称,版本号)
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";

在上述代码中，第6~7行通过调用getgetPackageInfo方法获取应用程序信息，其中，参数context.getPackageName()代表的是应用程序的包名，参数0代表获取应用程序的基本信息。基本信息包含应用版本名称，应用版本号等。
第9~11行代码，用于捕获未获取到packageInfo对象时抛出的异常。

### 1.2.2	版本更新流程分析

当进入应用程序时，应用程序通常都会向服务器发送请求，检测当前的版本信息是否是最新的。如果服务器有新的版本，则客户端会发送请求，服务器会返回一个新版本的apk下载地址，用户可以根据地址下载并安装应用程序，从而实现版本的更新。接下来，通过一张图来描述应用程序版本更新的具体流程，如图1-12所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/10.png?raw=true)

从服务端获取新版apk信息的json文件，解析此json文件，如果发现服务端版本号大于本地版本号，则需要从服务器提供的链接地址下载apk进行更新，并安装。图1-12展示了版本更新及其安装的整个过程。

## 1.3	版本号对比

### 1.3.1	获取本地版本号

本地版本号需从应用AndroidManifest.xml清单配置文件中获取，android:versionCode="1"属性指向的就是应用的版本信息。通过PackageManager相关api可获取此版本信息。

            //1.PackageManager包的管理者对象
        PackageManager pm = getPackageManager();
        //2.获取应用的配置信息,在此处传递0获取的是基本信息(包名,版本名称,版本号)
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;

第6行，getgetPackageInfo方法，通过包名获取应用程序的信息，需为其传递参数分别为，应用包名context.getPackageName()，获取标识0,0代表获取应用程序的基本信息，基本信息包含，应用版本名称，应用版本号等。
第9行，指根据包名未获取packageInfo对象抛出异常。

### 1.3.2	获取服务器端版本号

通过第三方网络请求框架okhttp中提供的newCall请求网络，获取服务器端版本号，与本地版本号比对，如果服务器端大于本地则，则弹出对话框通知用户有新版本可供更新。

            //1.获取本地VersionCode
        localVersionCode = getpackageVersionCode();
        //2.发送网路请求,获取服务器的versioncode,okhttp加jar包,okhttp和okio这2个jar包
        //通过分析,服务器必须提供versioncode,downloadUrl,新版本的描述,新版本的版本名称json
        //2.1创建一个客户端,此客户端可以给服务区发请求
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        //2.2请求对象创建
        String url = "http://47.105.71.243:8080/resource/update.json";
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        //2.3通过客户端把请求发出去
        Call call = okHttpClient.newCall(request);
        //2.4处理请求的结果
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                enterHome();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求有结果,成功
                //子线程中触发onResponse方法,不能够操作UI
                //获取服务器中的json里面的数据
                ResponseBody body = response.body();
                String json = body.string();
                //手动解析gson概率高
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    desc = jsonObject.getString("desc");
                    downloadUrl = jsonObject.getString("downloadUrl");
                    remoteVersionCode = jsonObject.getInt("versionCode");
                    //服务器和本地的versioncode进行比对
                    if (remoteVersionCode > localVersionCode) {
                        //服务器有新版本,可供下载
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showDialog();
                            }
                        });
                    } else {
                        //服务器没有更新的版本,可以直接进入应用程序的后一个界面
                        try {
                            Thread.sleep(3000);
                            enterHome();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    enterHome();
                }
            }
        });

第29~32行，解析服务器返回json中versionCode，downloadUrl，desc等字段。
服务器端版本号和本地版本号比对，如果本地版本号小于服务器端版本号，则需要通知用户更新，否则进入应用程序主界面。

                        //服务器和本地的versioncode进行比对
                    if (remoteVersionCode > localVersionCode) {
                        //服务器有新版本,可供下载
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showDialog();
                            }
                        });
                    } else {
                        //服务器没有更新的版本,可以直接进入应用程序的后一个界面
                        try {
                            Thread.sleep(3000);
                            enterHome();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

### 1.3.3	更新对话框

以下方法为，检测到更新版本，弹出对话框让用户选择更新与否。

        private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("是否下载新版本?");
        builder.setMessage(desc);//设置对话框除标题外的描述内容
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //下载apk,下载路径downloadUrl,考虑在何处存储files cache() sd卡
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //跳转到应用程序的后一个界面
                enterHome();
            }
        });
        //如果用户点击回退按钮,隐藏了对话框,则可以被如下方法监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

第25行，为对手机返回按钮的事件监听，一旦点击返回按钮则对话框消失。
第6行，为“立即升级”按钮的事件监听，一旦点击此按钮则开始下载apk。
第15行，为“稍后再说”按钮的事件监听，一旦点击此按钮则取消对话框，调用enterHome()方法，跳转至应用程序主界面。

### 1.3.4	下载应用

检测是否有更新版本时，同时获取了新应用的下载地址，如需对原有应用进行更新，则需向下载地址发送请求获取最新apk，并最终进行安装。

        private void downloadApk() {
        //下载apk,并且指定放置下载文件的路径,sd
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //创建一个进度条对话框,用于显示下载进度
            progressDialog = new ProgressDialog(this);
            //默认情况下对话框进度条圆形转圈的
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            //手机的sd卡可用
            //sd卡存储文件的路径
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mobicop.apk";
            //如何根据downloadUrl进行下载okhttp下载
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .get()
                    .url(downloadUrl)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //请求失败
                    enterHome();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    File file = null;
                    InputStream inputStream = null;
                    FileOutputStream fos = null;
                    try {
                        //请求成功,从服务器的响应对象中获取apk,流(服务器,输入流(提供数据) 本地输出流(写入文件))
                        ResponseBody body = response.body();
                        //告知progressDialog总进度,不变
                        progressDialog.setMax((int) body.contentLength());
                        //inputStream就是服务器吧需要下载的apk以流的形式提供给客户端
                        inputStream = body.byteStream();
                        file = new File(path);
                        fos = new FileOutputStream(file);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        int temp = 0;//用于记录目前下载的到的位置
                        while ((len = inputStream.read(bytes)) != -1) {
                            //将度过的数据写入文件中
                            fos.write(bytes, 0, len);
                            //告知progressDialog在在下载过程中的当前进度
                            temp += len;
                            //将当前下载位置,设置给progressDialog
                            progressDialog.setProgress(temp);
                            //没下载一段数据,睡眠一段时间
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                enterHome();
                            }
                        }
                        //一旦循环结束,则sd中就有一个从服务器下载下来的apk
                        //下载完成后,则隐藏对话框
                        progressDialog.dismiss();
                        //安装apk,这个要装的apk在哪里
                        installApk(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                        enterHome();
                    } finally {
                        StreamUtils.close(inputStream, fos);
                    }
                }
            });
        }
    }

## 1.4	Apk的安装

### 1.4.1	开启安装apk

如应用下载成功，即调用了RequestCallBack实现类对象中的onSuccess方法，如下载apk成功则需要提示用户进行安装，安装只需调用系统安装界面的activity即可，通过查看源码可得知通过隐式意图开启安装activity，所以需要在清单文件中配置系统安装activity过滤器。

             <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:scheme="content" />
            <data android:scheme="file" />
            <data android:mimeType="application/vnd.android.package-archive" />
        </intent-filter>

匹配action、category按照scheme要求传递内容为文件，并指定类型。

            //通过隐式意图去开启activity
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
    /*intent.setData(Uri.fromFile(file));
    intent.setType("application/vnd.android.package-archive");*/
        //这一句话中,第一个参数是要安装的apk的路径,第二个参数是apk
        // 所对应的类型。可以在tomcat的安装目录下的conf目录下的web.xml中找到
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, REQUEST_INSTALL_APK_CODE);

### 1.4.2	对安装activity关闭后进行监听

如用户点击安装，并下载无误，则安装装成功，如果在安装界面用户点击取消，则结束安装activity，一旦结束安装activity，则会调用前一个activity的onActivityResult方法，在此方法中需直接指向应用主界面。

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //在系统的安装界面结束后,则会调用此方法,在此方法中需要开启后续的界面,确保用户能够使用低版本的apk
        if (requestCode == REQUEST_INSTALL_APK_CODE) {
            enterHome();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

### 1.4.3	apk签名文件生成

apk如需覆盖安装，例如：手机卫士由1.0版本升级到2.0版本。需保证apk包名以及签名一致，如果两者缺少一个都会导致覆盖安装失败。按照如下方式生成签名文件。
![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/11.png?raw=true)

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/12.png?raw=true)

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/13.png?raw=true)

## 1.5	属性动画实现旋转效果

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)

logo图片在屏幕中以y轴作为对称轴，在Z轴方向做旋转，如上图效果，需用到属性动画实现此效果。

        private void initAnimation() {
        //1.指定动画类型(沿着y轴的旋转动画,属性动画)
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivLogo, "rotationY", 0f, 360f);
        //2.旋转次数,一致旋转
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        //3.旋转方式
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        //4.约定旋转一圈的时长
        objectAnimator.setDuration(2000);
        //5.开启旋转
        objectAnimator.start();
    }

第2行，创建属性动画对象，三个参数分别代表，执行属性动画的控件，执行操作，动画执行过程中所处的角度值。
第10行，设置旋转模式，常用参数为，REVERSE ，动画执行完成，逆向执行动画，RESTART ，每次都初始化执行动画。

## 1.6	TextView滚动及焦点处理

实现文字在水平方向滚动，无限轮训，如下图所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)

### 1.6.1	Xml实现文字水平滚动

可通过在TextView的xml中设置以下相关属性，达到水平滚动的效果。下表是常用属性的相关说明。

| 属性名称 | 属性说明 | 
| :------:| :------: | 
| focusableInTouchMode | 触摸获取焦点，true获取焦点，false不可以获取焦点。 | 
| focusable | 是否获取焦，true获取焦点，false不可以获取焦点。 |
| Ellipsize | None点的形式省略后面的文字，start 点的形式隐藏前面的文字，middle点的形式隐藏中间的文字，end 隐藏后面的文字，marquee 水平滚动文字。 | 

在此处给TextView设置focusableInTouchMode，focusable，Ellipsize等相关属性，最终得到水平滚动效果。

                <ngyb.mobicop.view.FocuseTextView
                android:id="@+id/tv_des"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="5dp"
                android:ellipsize="marquee"
                android:marqueeRepeatLimit="marquee_forever"
                android:singleLine="true"
                android:text="南宫燚滨手机安全卫士,保护手机安全,防止病毒的危害https://www.cnblogs.com/nangongyibin/"
                android:textColor="#f00" />

### 1.6.2	Java代码实现文本水平滚动

除了可以在布局文件中指定TextView相关属性，让其实现水平滚动效果，还可以编写一个类FocusTextView继承至TextView，构造方法中指定属性值，并重写isFocused方法，让其返回true，用于默认可获取焦点。

    public class FocuseTextView extends android.support.v7.widget.AppCompatTextView {
    public FocuseTextView(Context context) {
        //转掉2个参数的构造方法
        this(context, null);
    }

    public FocuseTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FocuseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 为了让TextView在布局文件中一定能够获取焦点
     * @return
     */
    @Override
    public boolean isFocused() {
        return true;
    }
	}


### 1.6.3	TextView滚动容错处理

如果TextView中文字在滚动过程中，TextView的焦点被别的控件抢去，例如：界面弹出一个对话框，或者页面还有一个抢夺焦点的EditText，则会导致TextView的滚动停止。针对上诉情况我们可以做如下修复。在TextView的子类中重写以下两个方法，以保证TextView继续滚动。

        @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }

第2行，onFocusChanged方法，如果EditText抢夺焦点则会调用此方法，此时焦点不在TextView上则focused返回false，则不会触发父类的onFocusChanged方法，以保证TextView继续滚动。
第9行，弹出对话框，则当前activity窗口无焦点，hasWindowFocus返回false，则不触发父类的onWindowFocusChanged，以保证TextView可以水平滚动。

## 1.7	主界面宫格实现

### 1.7.1	宫格GridView控件布局文件中使用

主界面采用宫格效果展示，如下图所示，需要用到GridView控件，numColumns属性用于指定宫格的列数，目前指定为两列，verticalSpacing和horizontalSpacing分别指定的是宫格内部方格的间距，目前指定大小分别为5dp。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)

        <GridView
        android:id="@+id/gv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="5dp"
        android:horizontalSpacing="5dp"
        android:numColumns="2"
        android:verticalSpacing="5dp" />

### 1.7.2	宫格GridView填充数据

gridView宫格填充数据的方式和ListView类似，只需给其设置相应的数据适配器即可，并且实现数据适配其中未实现的方法。

        class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                //不能复用,只能生成
                convertView = View.inflate(HomeActivity.this, R.layout.item_gird_home, null);
            }
            ImageView ivImage = convertView.findViewById(R.id.iv_image);
            TextView tvTitle = convertView.findViewById(R.id.tv_title);
            TextView tvDes = convertView.findViewById(R.id.tv_des);
            ivImage.setBackgroundResource(drawables[position]);
            tvTitle.setText(title[position]);
            tvDes.setText(des[position]);
            return convertView;
        }
    }

### 1.7.3	宫格条目点击选择器处理

点中宫格条目后需要和为点中有明显的颜色区分，如下图，可采用状态选择器实现。一个状态对应一张图片项目中有选中状态和未选中状态,相应的图片也需提供两张。根节点为根节点为selector,子节点为item,状态和相应图片匹配。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)

    <?xml version="1.0" encoding="utf-8"?>
	<selector xmlns:android="http://schemas.android.com/apk/res/android">
	<item android:state_pressed="true" android:drawable="@mipmap/antispam_report_button_press"/>
    <item android:drawable="@mipmap/antispam_report_button"/>
	</selector>

### 1.7.4	宫格条目点击事件处理

点中宫格中某个条目后，需跳转到相应的activity界面中，则需要对条目的点击事件进行监听。监听做法如下：

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(HomeActivity.this, CommonToolActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(HomeActivity.this, ProcessManagerActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(HomeActivity.this, AnitVirusActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        break;
                }
            }
        });


## 1.8	绘制圆型按钮背景

设置按钮背景为xml绘制，需用到shape标签对圆型图片进行绘制，绘制需指定绘制类型、半径大小、圆宽高大小、指定图片颜色。以下是绘制过程中用到的属性说明。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/1.png?raw=true)


| 属性 | 属性说明 | 
| :------:| :------: | 
| shape | 设置图片形状，rectangle圆角矩形，oval椭圆，line线，ring环形 | 
| corners | 圆角弧度 |
| solid | 图片色值 | 

    <?xml version="1.0" encoding="utf-8"?>
	<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@mipmap/btn_setting_pressed" android:state_pressed="true" />
    <item android:drawable="@mipmap/btn_setting_normal" />
	</selector>

## 1.9.	设置界面

### 1.9.1	设置界面顶部文字样式抽取

项目中反复用到同样式的文本内容，将控件在布局文件中编写的样式文件统一抽取到values文件夹styles.xml中，此种做法便于其余的布局文件对此样式的复用，减少代码量。实现方式如下：

        <TextView
        style="@style/TitleStyle"
        android:text="设置中心" />

        <style name="TitleStyle">
        <item name="android:textSize">18sp</item>
        <item name="android:gravity">center</item>
        <item name="android:background">#00a2ff</item>
        <item name="android:textColor">#fff</item>
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">45dp</item>
    	</style>

### 1.9.2	自定义组合控件

组合控件，即将settingview整块布局中的所有的控件,转换成view对象,放置到另一块布局内部。

    public class SettingItemView extends RelativeLayout {
	    public static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
	    //提供一个变量,用于记录按钮是开启还是关闭
	    private boolean openState;
	    private ImageView ivSettingToggle;
	
	    public SettingItemView(Context context) {
	        this(context, null);
	    }
	
	    public SettingItemView(Context context, AttributeSet attrs) {
	        this(context, attrs, -1);
	    }
	
	    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	        //AttributeSet就是在布局文件中定义的属性
	        //系统的属性已经有有系统代码处理好了
	        //自定义的属性,必须要由我们自己处理器属性值
	        initView(attrs);
	    }

	    private void initView(AttributeSet attrs) {
	        //1.xml------>view
	        View view = View.inflate(getContext(), R.layout.iv_setting_item_view, null);
	        //2.将view添加在当前的SettingItemView中,即相对布局中
	        addView(view);
	        //3.通过attrs获取Mobicop:title="string"和Mobilesafe:isToggle="boolean"属性值
	        //获取字符串类型属性值 即获取title的属性值
	        String title = attrs.getAttributeValue(NAMESPACE, "title");
	        //获取boolean类型属性值,即获取isToggle的属性值
	        boolean isToggle = attrs.getAttributeBooleanValue(NAMESPACE, "isToggle", true);
	        //4.获取TextView和ImageView2个控件,然后根据自定义属性的内容,修改相应的值
	        TextView tvSettingTitle = view.findViewById(R.id.tv_setting_title);
	        ivSettingToggle = view.findViewById(R.id.iv_setting_toggle);
	        tvSettingTitle.setText(title);
	        if (isToggle) {
	            ivSettingToggle.setVisibility(View.VISIBLE);
	        } else {
	            ivSettingToggle.setVisibility(View.GONE);
	        }
	    }
	
	    //状态取反,图片切换
	    public void reverseState() {
	        //1.反转状态
	        openState = !openState;
	        //2.根据取反后的状态,显示图片(true开启,图片绿色,false关闭,图片红色)
	        if (openState) {
	            ivSettingToggle.setBackgroundResource(R.mipmap.on);
	        } else {
	            ivSettingToggle.setBackgroundResource(R.mipmap.off);
	        }
	    }
	
	    //实时获取状态的方法
	    public boolean getOpenState() {
	        return openState;
	    }
	
	    //根据你设置的状态,修改图片的颜色
	    public void setOpenState(boolean isToggle) {
	        openState = isToggle;
	        if (openState) {
	            ivSettingToggle.setBackgroundResource(R.mipmap.on);
	        } else {
	            ivSettingToggle.setBackgroundResource(R.mipmap.off);
	        }
	    }
	}

第15行，将settingview转换成view对象，并将此view对象添加在继承至相对布局的SettingView中。

### 1.9.3	自定义属性

#### 1.9.3.1	申明命名空间

使用自定义属性首先需指定名空间，参照android对名空间的申明，申明自定义属性的名空间，xmlns:mobilesafe="http://schemas.android.com/apk/res-auto"

    `<?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:mobicop="http://schemas.android.com/apk/res-auto"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:orientation="vertical">`

#### 1.9.3.2	在xml中声明属性

设置界面开启关闭按钮，以及条目对应背景效果，需使用自定义的属性做指明，则需要对属性进行名称，类型，所属控件的申明，申明放置在res/values/attrs.xml中。

    
    <?xml version="1.0" encoding="utf-8"?>
	<resources>
	    <declare-styleable name="SettingItemView">
	        <attr name="title" format="string"/>
	        <attr name="isToggle" format="boolean"/>
	    </declare-styleable>
	</resources>

以上为使用ngyb定义的string类型的title属性，以及枚举类型的setBackground类型属性。

        <ngyb.mobicop.view.SettingItemView
        android:id="@+id/siv_update_auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:background="@drawable/selector_first_bg"
        mobicop:isToggle="true"
        mobicop:title="设置自动更新" />

#### 1.9.3.3	xml中自定义属性获取

自定义控件类中，通过相关api获取在布局文件中使用的自定义属性，需获取的属性包含标ngyb:title属性值，以及ngyb:setBackground="first"指向的int类型值，获取方式如下：

        private void initView(AttributeSet attrs) {
        //1.xml------>view
        View view = View.inflate(getContext(), R.layout.iv_setting_item_view, null);
        //2.将view添加在当前的SettingItemView中,即相对布局中
        addView(view);
        //3.通过attrs获取Mobicop:title="string"和Mobilesafe:isToggle="boolean"属性值
        //获取字符串类型属性值 即获取title的属性值
        String title = attrs.getAttributeValue(NAMESPACE, "title");
        //获取boolean类型属性值,即获取isToggle的属性值
        boolean isToggle = attrs.getAttributeBooleanValue(NAMESPACE, "isToggle", true);
        //4.获取TextView和ImageView2个控件,然后根据自定义属性的内容,修改相应的值
        TextView tvSettingTitle = view.findViewById(R.id.tv_setting_title);
        ivSettingToggle = view.findViewById(R.id.iv_setting_toggle);
        tvSettingTitle.setText(title);
        if (isToggle) {
            ivSettingToggle.setVisibility(View.VISIBLE);
        } else {
            ivSettingToggle.setVisibility(View.GONE);
        }
    }

### 1.9.4	设置界面屏蔽检测更新

在本地的sp中存储是否需要检测更新的标志，进入应用的时候将此标志指向的更新状态在界面中回显，并在点击设置条目的时候对是否需要更新状态进行切换，如下图所示。

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/21.png?raw=true)

            boolean openState = SharePreUtils.getBooleanValue(this, Constant.UPDATE_AUTO, true);
        //状态匹配图片,显示在屏幕上
        sivUpdateAuto.setOpenState(openState);
        //判断号码归属地的服务是否正在运行,如果正在运行则让按钮显示成绿色,否则红色
        String serviceName = AddressService.class.getName();
        //状态回显
        boolean serviceRunning = ServiceUtils.isServiceRunning(this, serviceName);
        sivAddress.setOpenState(serviceRunning);
    }

    @OnClick({R.id.siv_update_auto, R.id.siv_address, R.id.siv_address_style, R.id.adLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_update_auto:
                //1.调用settingItemView的reverseState即可
                //reverseState中首先取反状态,其次切换图片
                sivUpdateAuto.reverseState();
                //2.记录取反后的状态
                SharePreUtils.saveBooleanValue(SettingActivity.this,Constant.UPDATE_AUTO,sivUpdateAuto.getOpenState());
                break;
            case R.id.siv_address:
                //状态取反,图片切换
                sivAddress.reverseState();
                //服务开启和关闭状态,和取反后的图片保持一致,绿  开服务  红 关服务
                boolean openState = sivAddress.getOpenState();
                if (openState){
                    //true 服务器需要开启
                    startService(new Intent(SettingActivity.this,AddressService.class));
                }else{
                    //false 服务器需要开启
                    stopService(new Intent(SettingActivity.this,AddressService.class));
                }
                break;
            case R.id.siv_address_style:
                showDialog();
                break;
            case R.id.adLayout:
                break;
        }
    }

## 1.10	号码归属地数据库操作

号码归属地的效果图：

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/22.png?raw=true)

接下来我们就来实现号码归属地查询功能，在中国还是比较有规律的，比如手机号码第一位都是1，第二位34578，第三位往后0-9数字，总共11位，我们就可以根据号码的前三位我们可以确定号码的运营商，比如131联通、133电信、134移动等等，然后从第三位开始再往后数4位，确定号码的归属地，也是就说我们可以通过一个号码的前7位查询出一个号码的运营商和归属地，其实最开始是这个号码归属地是网友收集的，他将这些号段收集起来，创建了一个数据库，我们就可以根据号码查询出对应的归属地了。

### 1.10.1	需求

实现号码归属地数据库的拷贝查询和完善。

### 1.10.2	查询号码归属地数据库拷贝操作

因为数据库是不需要编译的，而res目录下的资源都是需要去编译的，所以一般数据库不存放在res目录下，一般存放在assets目录下，在assets目录下的资源一般都不会去编译，也就说不会在R文件中生成，会直接释放到内存中。

        private void copyDB() {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //1.需要将assets目录下的address.db拷贝到哪去,拷贝到files文件夹中
            File file = new File(getFilesDir(), "address.db");
            //2.读取文件的输入流
            inputStream = getAssets().open("address.db");
            //3.创建输出流
            fileOutputStream = new FileOutputStream(file);
            //4.从inputStream读取内容,直到读完为止
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null && fileOutputStream != null) {
                try {
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

### 1.10.3	查询数据库

拷贝数据库完成了，接下来实现查询数据库操作。

       public static String queryPhoneAddresss(Context ctx, String phone) {
        //1.获取address.db在文件夹中的路径
        String path = ctx.getFilesDir().getAbsolutePath() + File.separator + "address.db";
        //2.
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        //3.准备触发sql语句
        //3.1判断输入的phone是不是一个电话号码
        //手机号码第一位一定是1
        //手机号码第二位是3,5,7,8
        //手机号码的后9位是数字就行判断每一个字符是不是0-9之间的整数,一共匹配9次
        String rex = "^1[3,5,7,8]\\d{9}";
        boolean matches = phone.matches(rex);
        if (matches) {
            //3.2截取电话号码前7位,用于查询
            String subPhone = phone.substring(0, 7);
            //3.3查询
            String sql = "select cardtype from info where mobileprefix = ?;";
            Cursor cursor = db.rawQuery(sql, new String[]{subPhone});
            if (cursor.moveToNext()) {
                address = cursor.getString(0);
            } else {
                address = "未知号码";
            }
            cursor.close();
        } else {
            switch (phone.length()) {
                case 3:
                    address = "报警号码";
                    break;
                case 5:
                    address = "运营商服务";
                    break;
                case 7:
                    address = "本地座机";
                    break;
                case 8:
                    address = "本地座机";
                    break;
                case 11:
                    //3+8
                    String subArea = phone.substring(0, 3);
                    String sql = "select city from info where area = ?;";
                    Cursor cursor = db.rawQuery(sql, new String[]{subArea});
                    if (cursor.moveToNext()) {
                        address = cursor.getString(0);
                    } else {
                        address = "未知号码";
                    }
                    cursor.close();
                    break;
                case 12:
                    //4+8 区号四位电话号码8位
                    String subAreal = phone.substring(0, 4);
                    String sqll = "select city from info where area = ?;";
                    Cursor cursor1 = db.rawQuery(sqll, new String[]{subAreal});
                    if (cursor1.moveToNext()) {
                        address = cursor1.getString(0);
                    } else {
                        address = "未知号码";
                    }
                    cursor1.close();
                    break;
                default:
                    address = "未知号码";
                    break;
            }
        }
        return address;
    }

### 1.10.4.	数据库完善工作

发现输入6位数，程序崩溃，原因是角标越界问题，接下来我们就需要对传递过来的电话号码进行判断，判断一下传递过来的是否是电话号码，因为电话号码是多种数字的组合，所以这里我们选择使用正则表示去判断。

       public static String queryPhoneAddresss(Context ctx, String phone) {
        //1.获取address.db在文件夹中的路径
        String path = ctx.getFilesDir().getAbsolutePath() + File.separator + "address.db";
        //2.
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        //3.准备触发sql语句
        //3.1判断输入的phone是不是一个电话号码
        //手机号码第一位一定是1
        //手机号码第二位是3,5,7,8
        //手机号码的后9位是数字就行判断每一个字符是不是0-9之间的整数,一共匹配9次
        String rex = "^1[3,5,7,8]\\d{9}";
        boolean matches = phone.matches(rex);
        if (matches) {
            //3.2截取电话号码前7位,用于查询
            String subPhone = phone.substring(0, 7);
            //3.3查询
            String sql = "select cardtype from info where mobileprefix = ?;";
            Cursor cursor = db.rawQuery(sql, new String[]{subPhone});
            if (cursor.moveToNext()) {
                address = cursor.getString(0);
            } else {
                address = "未知号码";
            }
            cursor.close();
        } else {
            switch (phone.length()) {
                case 3:
                    address = "报警号码";
                    break;
                case 5:
                    address = "运营商服务";
                    break;
                case 7:
                    address = "本地座机";
                    break;
                case 8:
                    address = "本地座机";
                    break;
                case 11:
                    //3+8
                    String subArea = phone.substring(0, 3);
                    String sql = "select city from info where area = ?;";
                    Cursor cursor = db.rawQuery(sql, new String[]{subArea});
                    if (cursor.moveToNext()) {
                        address = cursor.getString(0);
                    } else {
                        address = "未知号码";
                    }
                    cursor.close();
                    break;
                case 12:
                    //4+8 区号四位电话号码8位
                    String subAreal = phone.substring(0, 4);
                    String sqll = "select city from info where area = ?;";
                    Cursor cursor1 = db.rawQuery(sqll, new String[]{subAreal});
                    if (cursor1.moveToNext()) {
                        address = cursor1.getString(0);
                    } else {
                        address = "未知号码";
                    }
                    cursor1.close();
                    break;
                default:
                    address = "未知号码";
                    break;
            }
        }
        return address;
    }

第8行，使用正则表达式判断手机号码是不是正确，手机号码的第一位肯定是阿拉伯数字1开头的，所以正则的第一部分可以写成”^1”；手机号码的第二位肯定是阿拉伯数字34578其中的一个，所以正则的第二部分可以写成”[34578]”；手机号码的第3-11位肯定是连续的9个非负整数，所以正则的第三部分可以写成”\\d{9}$”，因为’\’需要转义所以写成”\\”；所以最终的正则表达式可以写成”^1[34578]\\d{9}$”。

## 1.11.	归属地显示特效

归属地特效效果图：


![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/21.png?raw=true)


![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/8.png?raw=true)


![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/24.png?raw=true)

### 1.11.1.	需求

1．实现来电显示归属地的特效效果

2．实现归属地显示的抖动效果

3．实现归属地显示的振动效果

### 1.11.2.	实现显示

点击查询按钮才能进行号码归属地的查询，用户体验不好，一般会这么做，当用户输入完号码后，直接显示出号码归属地来。所以我们需要对输入框中文本发生改变做相应的事件监听。

            etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //一旦文本发生变化,则需要就最新的文本进行查询
                String phone = etPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    String address = AddressDao.queryPhoneAddresss(QueryAddressActivity.this, phone);
                    tvResult.setText(address);
                }
            }
        });

### 1.11.3.	抖动

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/23.png?raw=true)

打开apiDemo（apidemo所在位置如上图所示）查看需要增加的效果，apidemo里android中各种控件的实现效果，举例演示，查找增加的效果，views - animation - shake，和我们的界面进行比较，点击login发现输入框开始左右抖动，接下就来给输入框增加相应的效果。

                //抖动
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etPhone.startAnimation(shake);

## 1.12.	来电显示号码归属地

来电显示归属地的效果图：

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/24.png?raw=true)

### 1.12.1.	需求

实现来电显示界面显示号码归属地。

### 1.12.2.	监听电话状态

有电话打进来的时候在电话显示界面显示来电号码的归属地。比如，上图就是个紧急电话。

#### 1.12.2.1.	服务监听来电电话状态

我们可以使用telePhoneManager来实现监听电话的状态，我们要实现的效果是，不管什么时候有电话过来，都能显示号码的归属地(时时刻刻监听电话的状态)，可以写到服务里。

            //监听电话状态(响铃,空闲,摘机(在打电话))
        //1.TelephonyManager
        mTM = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //2.对电话状态进行监听
        myPhoneStateListener = new MyPhoneStateListener();
        mTM.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

第4行，参数1是电话的状态的监听事件，参数2是要去监听电话的什么状态。
电话状态的监听事件。

        class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            //电话号码状态发生改变,则会调用如下方法
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲
                    curstomerToast.hiddenToast();
                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃
                    //查询归属地,放在吐司上面显示
                    String address = AddressDao.queryPhoneAddresss(AddressService.this, incomingNumber);
                    curstomerToast.showToast(address);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://摘机
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

#### 1.12.2.2.	服务的开启和关闭

归属地设置在设置中也有,所以也要到设置中设置开启关闭,参考骚扰设置。可以参考设置中心中骚扰拦截条目的操作。

##  1.13．自定义Toast

###  1.13.1.需求

1．查看系统源码，了解系统Toast的操作

2．根据系统源码自定义Toast样式

3．隐藏显示Toast

###  1.13.2.了解系统Toast的实现

当我们点击进入Toast.makeText方法（第6-19行是Toast.makeText的系统源码）之后可以发现第inflate.inflate（第9-12行代码）的实现原理和View.inflate（第1-5行代码是View.inflate的系统源码）的实现原理是一模一样的，在后面仿照系统自定Toast会用的到，2.3再做具体使用。

        public static View inflate(Context context, @LayoutRes int resource, ViewGroup root) {
        LayoutInflater factory = LayoutInflater.from(context);
        return factory.inflate(resource, root);
    }

        public static Toast makeText(@NonNull Context context, @Nullable Looper looper,
            @NonNull CharSequence text, @Duration int duration) {
        Toast result = new Toast(context, looper);

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(com.android.internal.R.layout.transient_notification, null);
        TextView tv = (TextView)v.findViewById(com.android.internal.R.id.message);
        tv.setText(text);

        result.mNextView = v;
        result.mDuration = duration;

        return result;
    }

2．点击show方法进入源码查看，第7行有个TN的类对象，Toast的显示就是通过TN类中的handlerShow（第20行代码）方法进行显示的。通过查看第27和29行代码发现Toast是通过WindowManager的addView方法将自身的View添加到屏幕进行显示的，参数mParams是设置View的属性。

     /**
     * Show the view for the specified duration.
     */
    public void show() {
        if (mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }

        INotificationManager service = getService();
        String pkg = mContext.getOpPackageName();
        TN tn = mTN;
        tn.mNextView = mNextView;

        try {
            service.enqueueToast(pkg, tn, mDuration);
        } catch (RemoteException e) {
            // Empty
        }
    }

        private static class TN extends ITransientNotification.Stub {
        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        private static final int SHOW = 0;
        private static final int HIDE = 1;
        private static final int CANCEL = 2;
        final Handler mHandler;

        int mGravity;
        int mX, mY;
        float mHorizontalMargin;
        float mVerticalMargin;


        View mView;
        View mNextView;
        int mDuration;

        WindowManager mWM;

        String mPackageName;

        static final long SHORT_DURATION_TIMEOUT = 4000;
        static final long LONG_DURATION_TIMEOUT = 7000;

        TN(String packageName, @Nullable Looper looper) {
            // XXX This should be changed to use a Dialog, with a Theme.Toast
            // defined that sets up the layout params appropriately.
            final WindowManager.LayoutParams params = mParams;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.windowAnimations = com.android.internal.R.style.Animation_Toast;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.setTitle("Toast");
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

            mPackageName = packageName;

            if (looper == null) {
                // Use Looper.myLooper() if looper is not specified.
                looper = Looper.myLooper();
                if (looper == null) {
                    throw new RuntimeException(
                            "Can't toast on a thread that has not called Looper.prepare()");
                }
            }
            mHandler = new Handler(looper, null) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case SHOW: {
                            IBinder token = (IBinder) msg.obj;
                            handleShow(token);
                            break;
                        }
                        case HIDE: {
                            handleHide();
                            // Don't do this in handleHide() because it is also invoked by
                            // handleShow()
                            mNextView = null;
                            break;
                        }
                        case CANCEL: {
                            handleHide();
                            // Don't do this in handleHide() because it is also invoked by
                            // handleShow()
                            mNextView = null;
                            try {
                                getService().cancelToast(mPackageName, TN.this);
                            } catch (RemoteException e) {
                            }
                            break;
                        }
                    }
                }
            };
        }

        /**
         * schedule handleShow into the right thread
         */
        @Override
        public void show(IBinder windowToken) {
            if (localLOGV) Log.v(TAG, "SHOW: " + this);
            mHandler.obtainMessage(SHOW, windowToken).sendToTarget();
        }

        /**
         * schedule handleHide into the right thread
         */
        @Override
        public void hide() {
            if (localLOGV) Log.v(TAG, "HIDE: " + this);
            mHandler.obtainMessage(HIDE).sendToTarget();
        }

        public void cancel() {
            if (localLOGV) Log.v(TAG, "CANCEL: " + this);
            mHandler.obtainMessage(CANCEL).sendToTarget();
        }

        public void handleShow(IBinder windowToken) {
            if (localLOGV) Log.v(TAG, "HANDLE SHOW: " + this + " mView=" + mView
                    + " mNextView=" + mNextView);
            // If a cancel/hide is pending - no need to show - at this point
            // the window token is already invalid and no need to do any work.
            if (mHandler.hasMessages(CANCEL) || mHandler.hasMessages(HIDE)) {
                return;
            }
            if (mView != mNextView) {
                // remove the old view if necessary
                handleHide();
                mView = mNextView;
                Context context = mView.getContext().getApplicationContext();
                String packageName = mView.getContext().getOpPackageName();
                if (context == null) {
                    context = mView.getContext();
                }
                mWM = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
                // We can resolve the Gravity here by using the Locale for getting
                // the layout direction
                final Configuration config = mView.getContext().getResources().getConfiguration();
                final int gravity = Gravity.getAbsoluteGravity(mGravity, config.getLayoutDirection());
                mParams.gravity = gravity;
                if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                    mParams.horizontalWeight = 1.0f;
                }
                if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                    mParams.verticalWeight = 1.0f;
                }
                mParams.x = mX;
                mParams.y = mY;
                mParams.verticalMargin = mVerticalMargin;
                mParams.horizontalMargin = mHorizontalMargin;
                mParams.packageName = packageName;
                mParams.hideTimeoutMilliseconds = mDuration ==
                    Toast.LENGTH_LONG ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
                mParams.token = windowToken;
                if (mView.getParent() != null) {
                    if (localLOGV) Log.v(TAG, "REMOVE! " + mView + " in " + this);
                    mWM.removeView(mView);
                }
                if (localLOGV) Log.v(TAG, "ADD! " + mView + " in " + this);
                // Since the notification manager service cancels the token right
                // after it notifies us to cancel the toast there is an inherent
                // race and we may attempt to add a window after the token has been
                // invalidated. Let us hedge against that.
                try {
                    mWM.addView(mView, mParams);
                    trySendAccessibilityEvent();
                } catch (WindowManager.BadTokenException e) {
                    /* ignore */
                }
            }
        }

        private void trySendAccessibilityEvent() {
            AccessibilityManager accessibilityManager =
                    AccessibilityManager.getInstance(mView.getContext());
            if (!accessibilityManager.isEnabled()) {
                return;
            }
            // treat toasts as notifications since they are used to
            // announce a transient piece of information to the user
            AccessibilityEvent event = AccessibilityEvent.obtain(
                    AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED);
            event.setClassName(getClass().getName());
            event.setPackageName(mView.getContext().getPackageName());
            mView.dispatchPopulateAccessibilityEvent(event);
            accessibilityManager.sendAccessibilityEvent(event);
        }

        public void handleHide() {
            if (localLOGV) Log.v(TAG, "HANDLE HIDE: " + this + " mView=" + mView);
            if (mView != null) {
                // note: checking parent() just to make sure the view has
                // been added...  i have seen cases where we get here when
                // the view isn't yet added, so let's try not to crash.
                if (mView.getParent() != null) {
                    if (localLOGV) Log.v(TAG, "REMOVE! " + mView + " in " + this);
                    mWM.removeViewImmediate(mView);
                }


                // Now that we've removed the view it's safe for the server to release
                // the resources.
                try {
                    getService().finishToken(mPackageName, this);
                } catch (RemoteException e) {
                }

                mView = null;
            }
        }
    }

###  1.13.3.定义样式

根据2.2对系统源码的解释，现在我们可以自定义Toast样式了。

        //显示吐司
    public void showToast(String address) {
        //2.给吐司定义一套布局文件
        //从sp中获取存储的吐司背景图片
        int drawableId = SharePreUtils.getIntValue(mCtx, Constant.TOAST_STYLE_COLOR, R.drawable.shape_alpha_style);
        viewToast = View.inflate(mCtx, R.layout.layout_toast, null);
        //将吐司的背景图片显示出来
        viewToast.setBackgroundResource(drawableId);
        tvPhoneAddress = viewToast.findViewById(R.id.tv_phone_address);
        tvPhoneAddress.setText(address);
        //3.将view对象添加在窗体上WindowManager
        mWM.addView(viewToast, params);
        //注册一个触摸的时间监听
        viewToast.setOnTouchListener(new View.OnTouchListener() {
            private int startX;
            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //一次触发
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //多次触发
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();
                        //计算手指的移动距离
                        int disX = moveX - startX;
                        int disY = moveY - startY;
                        //手指的偏移量就是吐司这个view的偏移量
                        params.x += disX;
                        params.y += disY;
                        //要将修改后的(x,y)作用在吐司上
                        //更新某一个view在屏幕中的位置
                        mWM.updateViewLayout(viewToast, params);
                        //上一次移动的终点,作为下一次移动的起点
                        startX = moveX;
                        startY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起一次触发
                        break;
                }
                return false;
            }
        });
    }

第8行，LayoutParams代表的就是设置view的属性，其实表示的就是控件在代码中如何设置属性，跟在布局文件中设置一样的效果，LayoutParams设置规则遵循的是view要添加到的父控件的规则，view要添加到那个父控件，view属性规则必须服合父控件的属性规则才会有效。

第17行，因为toast是天生没有触摸事件，所以需要设置TYPE_PRIORITY_PHONE优先于电话的类型。

第18行，这个方法是将params属性设置给view，并将view添加到windowmanager。

###  1.13.4. 隐藏Toast

运行测试挂断电话发现归属地还在显示(桌面，各种应用打开测试)，所以当挂断电话的时候要移除Toast。

        //隐藏吐司
    public void hiddenToast() {
        //窗体对象不为空,添加的吐司view对象也不为空
        if (mWM != null && viewToast != null) {
            //吐司确实挂载在窗体上
            if (viewToast.getParent() != null) {
                //吐司的移除
                mWM.removeView(viewToast);
            }
        }
    }

##  1.14．外拨电话显示号码归属地

显示号码归属地效果图：

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/24.png?raw=true)

###  1.14.1需求

实现监听外拨点电话操作。

###  1.14.2.注册广播接收者

注册广播有两种配置方式，清单文件注册和代码注册：

①	在清单文件中注册，安装到系统，一直有效，程序员无法控制

②	在代码中注册，程序运行生效，杀死进程，失效，可以通过代码反注册。

如下是代码注册广播接受者，监听播出电话广播。

    `        //去点查询,发出系统广播,广播接受者
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(myReceiver, intentFilter);`

在广播接受者的onReceive方法中查询号码归属地并显示出来，通过监听外拨电话的广播接受者实现。

        class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //去电号码查询逻辑
            //1.获取去电号码
            String outGoingCall = getResultData();
            //2.去电号码归属地
            String address = AddressDao.queryPhoneAddresss(AddressService.this, outGoingCall);
            //3.以吐司的形式显示出来
            curstomerToast.showToast(address);
        }
    }

需要的权限：

        <!--去电广播监听权限-->
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />

##  1.15．设置Toast的坐标

设置和设置完之后Toast的效果图：

![](https://github.com/nangongyibin/Android_MobiCop/blob/master/picture/24.png?raw=true)

###  1.15.1.需求

实现修改Toast位置的操作。

###  1.15.2.设置Toast的位置

                            //手指的偏移量就是吐司这个view的偏移量
                        params.x += disX;
                        params.y += disY;

第3行，x不是表示坐标，而是表示距离边框的距离，根据gravity来设置的，如果left表示左边框的距离，如果是right则表示距离右边框的距离。
第4行，y不是表示坐标，而是表示距离边框的距离，根据gravity来设置的，如果top表示距离上边框的距离，如果是bottom则表示距离底边框的距离。

##  1.16．随着手指移动而移动操作

###  1.16.1.需求

通过Toast的触摸监听事件，实现Toast随着手指移动而移动的操作。

###  1.16.2.实现思路

1．按住控件，记录开始的x和y的坐标startX和startY手指的位置。

2．移动到新的位置，记录新的位置x和y的坐标newX和newY手指的位置。

3．计算移动的偏移量dX=newX-startX，dY=newY-startY。

4．将控件移动相应的偏移量，重新绘制控件。

5．更新开始的坐标startX=newX，startY=newY。


###  1.16.3 随着手指移动而移动操作

控件的触摸监听事件，只要执行触摸，就自动暂时获取焦点然后拿到对应手指当前的坐标，然后将坐标设置给当前的Toast控件。

            //注册一个触摸的时间监听
        viewToast.setOnTouchListener(new View.OnTouchListener() {
            private int startX;
            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //一次触发
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //多次触发
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();
                        //计算手指的移动距离
                        int disX = moveX - startX;
                        int disY = moveY - startY;
                        //手指的偏移量就是吐司这个view的偏移量
                        params.x += disX;
                        params.y += disY;
                        //要将修改后的(x,y)作用在吐司上
                        //更新某一个view在屏幕中的位置
                        mWM.updateViewLayout(viewToast, params);
                        //上一次移动的终点,作为下一次移动的起点
                        startX = moveX;
                        startY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        //抬起一次触发
                        break;
                }
                return false;
            }
        });

第6行，这个方法表示触摸控件的调用，参数v是当前的控件，参数event是触摸执行的事件。

第46行，返回true表示事件被消费了，返回false表示事件被拦截了。

##  1.17．自定义Dialog

###  1.17.1.需求

1．创建Dialog并设置Dialog的位置

2．自定义Dialog的样式

3．设置Dialog显示内容


###  1.17.2.自定义Dialog样式

因为Dialog的样式系统中没有提供，所以需要我们自己定义Dialog。

####  1.17.2.1.创建自定义的Dialog

    public class CustomerDialog extends Dialog {
	    @BindView(R.id.lv_dialog_style)
	    ListView lvDialogStyle;
	    private String[] title = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};
	    private int[] drawables = new int[]{R.drawable.shape_alpha_style, R.drawable.shape_orange_style, R.drawable.shape_blue_style, R.drawable.shape_gray_style, R.drawable.shape_green_style};
	
	    /**
	     * 记录目前选中条目的索引值
	     */
	    private int currentPosition = 0;
	    private MyAdapter myAdapter;
	
	    public CustomerDialog(@NonNull Context context) {
	        super(context, R.style.CustomerDialogStyle);
	        //1.获取窗体对象
	        Window window = getWindow();
	        //2.获取对话框的配置参数
	        WindowManager.LayoutParams params = window.getAttributes();
	        //3.让对话框水平居中,并且在屏幕的最下方
	        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
	        //4.变化后的params重新告知对话框,安装此要求进行展示
	        window.setAttributes(params);
	    }
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //给对话框设置布局文件,写法和给activity设置布局文件一致
	        setContentView(R.layout.layout_customer_dialog);
	        ButterKnife.bind(this);
	        //给ListView设置数据适配器
	        myAdapter = new MyAdapter();
	        lvDialogStyle.setAdapter(myAdapter);
	        //选中条目的回显
	        //1.读取在sp中记录的图片的唯一性资源id
	        int drawableId = SharePreUtils.getIntValue(getContext(), Constant.TOAST_STYLE_COLOR, drawables[0]);
	        //2.判断drawableId在drawables数据中的索引位置
	        for (int i = 0; i < drawables.length; i++) {
	            if (drawables[i] == drawableId) {
	                //找到了之前选中条目的索引
	                currentPosition = i;
	                break;
	            }
	        }
	        lvDialogStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //记录点中了第几个条目
	                currentPosition = position;
	                //刷新数据适配器,用于更新界面效果
	                myAdapter.notifyDataSetChanged();
	                //记录选中条目的图片(int resId)sp
	                SharePreUtils.saveIntValue(getContext(), Constant.TOAST_STYLE_COLOR, drawables[position]);
	                dismiss();
	            }
	        });
	
	    }
	
	    class MyAdapter extends BaseAdapter {
	
	        @Override
	        public int getCount() {
	            return title.length;
	        }
	
	        @Override
	        public Object getItem(int position) {
	            return null;
	        }
	
	        @Override
	        public long getItemId(int position) {
	            return 0;
	        }
	
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            if (convertView == null) {
	                convertView = View.inflate(getContext(), R.layout.item_dialog_style, null);
	            }
	            ImageView ivStyleColor = convertView.findViewById(R.id.iv_style_color);
	            TextView tvStyle = convertView.findViewById(R.id.tv_style);
	            ImageView ivSelected = convertView.findViewById(R.id.iv_selected);
	            ivStyleColor.setBackgroundResource(drawables[position]);
	            tvStyle.setText(title[position]);
	            //用于判断哪个索引位置右侧的勾需要显示
	            if (position == currentPosition) {
	                //展示第0个条目的view对象,则需要显示右侧的勾
	                ivSelected.setVisibility(View.VISIBLE);
	            } else {
	                ivSelected.setVisibility(View.GONE);
	            }
	            return convertView;
	        }
	    }
	}

####  1.17.2.2.更改Dialog的位置

Dialog位置未调整前。

![](https://github.com/nangongyibin/Android_MobiCop/raw/master/picture/8.png?raw=true)

测试发现Dialog的位置不对（如上图所示），通过获取Window设置Dialog的位置，修改dialog对话框的位置。


            //1.获取窗体对象
        Window window = getWindow();
        //2.获取对话框的配置参数
        WindowManager.LayoutParams params = window.getAttributes();
        //3.让对话框水平居中,并且在屏幕的最下方
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        //4.变化后的params重新告知对话框,安装此要求进行展示
        window.setAttributes(params);

设置完上面的这一段代码之后效果图如下所示：

![](https://github.com/nangongyibin/Android_MobiCop/raw/master/picture/8.png?raw=true)

####  1.17.2.3.更改Dialog的样式

发现自定义的Dialog多出了标题等内容，实际显示效果是没有的，所以需要自定义Dialog的样式。设置dialog的样式，通过styles.xml中的android:Theme.Light进入系统的Themes.xml文件中,将Themes.xml的Theme.Dialog.NoFrame的样式属性拷贝到我们自己的styles.xml样式文件中并修改。

1．设置Dialog需要的样式。

        <style name="CustomerDialogStyle" parent="@android:style/Theme.Dialog">
        <item name="android:windowNoTitle">true</item><!--去掉对话框标题-->
        <item name="android:background">@android:color/white</item><!--对话框背景颜色为白色-->
        <item name="android:windowAnimationStyle">@style/CustomerDialogAnimation
        </item><!--给对话框添加动画效果-->
    </style>

2．使用我们自定义的样式。

            super(context, R.style.CustomerDialogStyle);

####  1.17.2.4.设置Dialog显示动画

设置dialog显示动画，通过Themes.xml文件中Theme.Dialog样式windowAnimationStyle去sdk中找相应的样式。

1．查找路径：sdk\platforms\android-16\data\res\values\styles.xml。

2．打开搜索 Animation.Dialog，将Animation.Dialog中的属性复制到我们的样式中。

        <style name="CustomerDialogAnimation">
	        <!--对话框窗体进入的动画效果-->
	        <item name="android:windowEnterAnimation">@anim/dialog_enter</item>
	        <!--对话框窗体退出的动画效果-->
	        <item name="android:windowExitAnimation">@anim/dialog_exit</item>
    	</style>

3．根据样式中的缺少的文件去拷贝系统的文件。

  文件路径:

sdk\platforms\android-16\data\res\anim	

sdk\platforms\android-16\data\res\interpolator将相应的文件拷贝到我们工程的

res的interpolator文件夹中 -> interpolator。

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/25.png)

####  1.17.2.5.设置Dialog显示的内容


在AddressDialog的onCreate()方法中初始化ListView，通过ListView展示数据，因为自定义的Dialog可能会在其他地方使用，所以将Adapter和点击事件都设置在使用自定义Dialog的类中，通过自定义Dialog中定义的方法将Adapter和点击事件传递给自定义的Dialog进行使用。

在自定义的Dialog中创建获取Adapter和点击事件的方法。

            //给ListView设置数据适配器
        myAdapter = new MyAdapter();
        lvDialogStyle.setAdapter(myAdapter);

            lvDialogStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录点中了第几个条目
                currentPosition = position;
                //刷新数据适配器,用于更新界面效果
                myAdapter.notifyDataSetChanged();
                //记录选中条目的图片(int resId)sp
                SharePreUtils.saveIntValue(getContext(), Constant.TOAST_STYLE_COLOR, drawables[position]);
                dismiss();
            }
        });

第6行，因为条目显示是在Settingactivity中做的，点击的是条目，所以谁调用dialog谁就把点击事件传递进来。

在使用Dialog的SettingActivity中通过相应的方法将Adapter和点击事件传递给自定义Dialog。

        private String[] title = new String[]{"半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿"};
    private int[] drawables = new int[]{R.drawable.shape_alpha_style, R.drawable.shape_orange_style, R.drawable.shape_blue_style, R.drawable.shape_gray_style, R.drawable.shape_green_style};

            lvDialogStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录点中了第几个条目
                currentPosition = position;
                //刷新数据适配器,用于更新界面效果
                myAdapter.notifyDataSetChanged();
                //记录选中条目的图片(int resId)sp
                SharePreUtils.saveIntValue(getContext(), Constant.TOAST_STYLE_COLOR, drawables[position]);
                dismiss();
            }
        });


##  1.18．获取进程信息

进程信息效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

###  1.18.1.需求

获取系统中所有进程的信息。

###  1.18.2.获取进程信息

通过ActivityManager和PackageManager获取进程的信息用集合保存起来待后面通过列表进行展示。

        /**
     * @param ctx
     * @return 获取进程的信息 包名 图片 名臣个大小 是否为系统
     */
    public static List<ProcessBean> getAllProcessInfo(Context ctx) {
        ArrayList<ProcessBean> processList = new ArrayList<>();
        //1.获取ActivityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = ctx.getPackageManager();
        //2.获取手机正在运行的进程集合
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        boolean isSys;
        //3.循环遍历进程集合
        Log.e(TAG, "getAllProcessInfo: " + runningAppProcesses.size());
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            //包名
            ActivityManager.RunningAppProcessInfo processInfo = runningAppProcesses.get(i);
            String processName = processInfo.processName;
            //在此获取pid作用是为了能够让ActivityManager获取每一个进程memoryInfo
            int pid = processInfo.pid;
            Drawable drawable;
            String name;
            int usedMemory = 0;
            try {
                ApplicationInfo applicationInfo = pm.getApplicationInfo(processName, 0);
                //应用程序图片和名称
                drawable = applicationInfo.loadIcon(pm);
                name = applicationInfo.loadLabel(pm).toString();
                //是否为系统级别应用
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                    //满足此条件,则认为applicationInfo指向的应用,是系统级别应用
                    isSys = true;
                } else {
                    isSys = false;
                }
                //根据int数组中的应用唯一性皮带获取多个应用的内存信息对象
                Debug.MemoryInfo[] processMemoryInfo = am.getProcessMemoryInfo(new int[]{pid});
                //因为智传娣一个pid所以得到的数组中索引为0是有值的即是pid指向的应用的memoryinfo
                Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
                //获取应用占用的内存大小
                usedMemory = memoryInfo.getTotalPss() * 1024;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "getAllProcessInfo: " + e.getLocalizedMessage().toString());
                //原来的异常时根据packagename找不到applicationInfo才出现的异常
                e.printStackTrace();
                //如果系统不给提供则由api提供必要信息,构建Javabean对象
                name = "未命名进程";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    drawable = ctx.getResources().getDrawable(R.mipmap.ic_launcher);
                } else {
                    drawable = ctx.getResources().getDrawable(R.mipmap.ic_launcher);
                }
                isSys = true;
            }
            ProcessBean processBean = new ProcessBean(drawable, name, processName, usedMemory, isSys, false);
            processList.add(processBean);
        }
        //打印获取的进程数有多少个
        Log.e(TAG, "getAllProcessInfo: " + processList.size());
        return processList;
    }

第14-17行，计算占用的内存，参数int[]是存放进程的pid，数组是你存放几个进程的pid，最终会获取几个进程的内存信息。

第42-45行，表示找不到applicationinfo，表示进程是系统进程，给c进程设置默认数据。

##  1.19．进程管理顶部实现

进程管理顶部的效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

###  1.19.1.需求

1．获取正在运行的进程数和总进程数

2．获取可用内存和总内存	

###  1.19.2. 进程管理界面搭建

参考软件管理界面拷贝软件管理界面修改id，增加清理进程的按钮。

        <RelativeLayout
	        android:layout_width="match_parent"
	        android:layout_height="45dp">
	
	        <TextView
	            style="@style/TitleStyle"
	            android:text="进程管理" />
	
	        <ImageView
	            android:id="@+id/iv_clean"
	            android:layout_width="wrap_content"
	            android:layout_height="wrap_content"
	            android:layout_alignParentRight="true"
	            android:layout_centerVertical="true"
	            android:layout_marginRight="10dp"
	            android:background="@mipmap/clean_normal" />
	    </RelativeLayout>

###  1.19.3.获取正在运行的进程数

        //获取正在运行的进程的数量
    public static int getRunningProcess(Context ctx) {
        //1.ActivityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        //2.获取手机正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        return runningAppProcesses.size();
    }

###  1.19.4.获取总进程数据

因为一个软件可以占用多个进程，所以占用多个进程的软件要额外的将多出来的进程统计出来。也有一个进程是多个程序共用的，我们在这里采用HashSet进行去除重复的进程了。

        /**
     * @param ctx 上下文环境
     * @return 手机中可以有的进程的总数
     * 1.获取手机上所有安装应用
     * 2.获取所有应用的activity ,receiver,provider,service
     * 3.将第二个不走中所有组件的procName都记录在hastset(排重)中
     */
    public static int getAllProcess(Context ctx) {
        //1.获取PackageManager对象
        PackageManager pm = ctx.getPackageManager();
        //2.获取安装在手机上的所有的应用
        Log.e(TAG, "getAllProcess:getInstalledPackages前 ");
        List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES | PackageManager.GET_PROVIDERS | PackageManager.GET_SERVICES | PackageManager.GET_RECEIVERS);
        Log.e(TAG, "getAllProcess:getInstalledPackages后 " + installedPackages.size());
        HashSet<String> hashSet = new HashSet<>();
        //3.循环遍历安装在手机上的每一个应用 获取activi,service,provider,receiver的processname
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            //application
            String processName = packageInfo.applicationInfo.processName;
            hashSet.add(processName);
            //activity
            ActivityInfo[] activities = packageInfo.activities;
            if (activities != null && activities.length > 0) {
                for (int j = 0; j < activities.length; j++) {
                    ActivityInfo activity = activities[j];
                    hashSet.add(activity.processName);
                }
            }
            //service
            ServiceInfo[] services = packageInfo.services;
            if (services != null && services.length > 0) {
                for (int j = 0; j < services.length; j++) {
                    ServiceInfo service = services[j];
                    hashSet.add(service.processName);
                }
            }
            //provider
            ProviderInfo[] providers = packageInfo.providers;
            if (providers != null && providers.length > 0) {
                for (int j = 0; j < providers.length; j++) {
                    ProviderInfo provider = providers[j];
                    hashSet.add(provider.processName);
                }
            }
            //receiver
            ActivityInfo[] receivers = packageInfo.receivers;
            if (receivers != null && receivers.length > 0) {
                for (int j = 0; j < receivers.length; j++) {
                    ActivityInfo receiver = receivers[j];
                    hashSet.add(receiver.processName);
                }
            }
        }
        Log.e(TAG, "getAllProcess: " + hashSet.size());
        return hashSet.size();
    }

###  1.19.5.获取可用内存

        /**
     * 获取可用的内存大小
     *
     * @param ctx
     * @return
     */
    public static long getAvailMemory(Context ctx) {
        //1.获取ActivityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        //2.通过ActivityManager获取内存信息对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        //3.通过api获取已用内存大小G M K,BYTE
        return memoryInfo.availMem;
    }

###  1.19.6.获取总内存

        /**
     * 获取可用的内存大小
     *
     * @param ctx
     * @return
     */
    public static long getAllMemory(Context ctx) {
        //1.获取ActivityManager
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        //2.通过ActivityManager获取内存信息对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        //3.通过api获取已用内存大小G M K,BYTE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return memoryInfo.totalMem;
        } else {
            //对16以下版本兼容代码逻辑,写在此处
            return getLowTotalMem();
        }
    }

totalMem字段在16版本以上才有，为了兼容低版本，要根据手机系统的SDK版本使用不同的方式获取总内存信息。

            //3.通过api获取已用内存大小G M K,BYTE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return memoryInfo.totalMem;
        } else {
            //对16以下版本兼容代码逻辑,写在此处
            return getLowTotalMem();
        }

通过查询proc文件中meminfo获取系统总内存。

        /**
     * @return 在低版本手机上获取手机总内存大小
     */
    public static long getLowTotalMem() {
        try {
            File file = new File("proc/meminfo");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String strLineOne = bufferedReader.readLine();
            String totalMem = strLineOne.replace("MemTotal:", "").replace("kB", "");
            return Long.parseLong(totalMem) * 1024;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

##  1.20.	进程数据的展示

进程数据展示的效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

###  1.20.1.	需求

使用StickyListHeaders第三方框架进行数据的展示。

###  1.20.2.	第三方框架的使用

####  1.20.2.1.	下载StickyListHeaders 

下载路径：https://github.com/emilsjolander/StickyListHeaders

####  1.20.2.2.	使用StickyListHeaders 

1.在布局文件中使用第三方框架。

                <se.emilsjolander.stickylistheaders.StickyListHeadersListView
                android:id="@+id/lv_process"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

2.创建适配器展示进程数据信息。

       class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            //返回头的view对象
            TitleViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_process_text, null);
                holder = new TitleViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (TitleViewHolder) convertView.getTag();
            }
            if (!procesInfoList.get(position).isSys()) {
                //根据索引区分用户进程和系统进程,用户进程公用同一个头,系统进程公用同一个头
                holder.tvProcessTitle.setText("用户进程(" + customerList.size() + ")");
            } else {
                holder.tvProcessTitle.setText("系统进程(" + systemList.size() + ")");
            }
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            //此方法返回的值有几种情况,头就有几个
            if (!procesInfoList.get(position).isSys()) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public int getCount() {
            if (showSys) {
                //显示系统进程
                return procesInfoList.size();
            } else {
                //不显示系统进程,只显示用户进程
                return customerList.size();
            }
        }

        @Override
        public ProcessBean getItem(int position) {
            if (showSys) {
                return procesInfoList.get(position);
            } else {
                return customerList.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_process, null);
                viewHolder = new ViewHolder();
                viewHolder.ivIcon = convertView.findViewById(R.id.iv_icon);
                viewHolder.tvName = convertView.findViewById(R.id.tv_name);
                viewHolder.tvSize = convertView.findViewById(R.id.tv_size);
                viewHolder.cbBox = convertView.findViewById(R.id.cb_box);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ProcessBean processBean = getItem(position);
            viewHolder.ivIcon.setBackgroundDrawable(processBean.getDrawable());
            viewHolder.tvName.setText(processBean.getName());
            //用用大小进行格式化后,在作为字符串显示
            String strSize = android.text.format.Formatter.formatFileSize(getApplicationContext(), processBean.getSize());
            viewHolder.tvSize.setText(strSize);
            //获取目前应用程序的包名和进程列表集合中每一个对象的包名做比对,如果一致,则右侧的CheckBox隐藏,否则显示
            String packageName = getPackageName();
            if (packageName.equals(processBean.getPackageName())) {
                viewHolder.cbBox.setVisibility(View.GONE);
            } else {
                viewHolder.cbBox.setVisibility(View.VISIBLE);
            }
            //根据每一个条目对象中的ischeck的状态,决定checkBox选中还是未选中
            viewHolder.cbBox.setChecked(processBean.isCheck());
            return convertView;
        }
    }

##  1.21.	功能点的实现

进程管理功能点的效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

###  1.21.1.	需求

实现单选、反选、清理功能。

###  1.21.2.	单选的实现

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

1.在bean类中设置checkbox是否选中的属性

    !processBean.isCheck()

2.在adapter的getview方法中，根据标示设置checkbox选中不选中，以及根据包名判断是否显示checkbox。

                if (packageName.equals(processBean.getPackageName())) {
                viewHolder.cbBox.setVisibility(View.GONE);
            } else {
                viewHolder.cbBox.setVisibility(View.VISIBLE);
            }

3.设置ListView的监听事件，根据条目选中状态更新界面效果。当前点击的条目已经选中时，标示置为false表示取消选中；当前点击的条目没有选中时，标示置为true表示选中。在listview中实时改变的操作，一般不会去复用缓存的，因为checkbox状态是要实时改变的，所以checkbox状态是不能跟着去复用缓存的。一般会把操作状态的标示存放bean类，根据bean类来进行改变checkbox的状态。

            lvProcess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (showSys) {
                    //点击完成后,需要将选中条目指向对象的isCheck进行取反操作
                    ProcessBean processBean = procesInfoList.get(position);
                    //手机卫士应用没有勾选和未勾选状态变化,所以不需要进行页面更新
                    if (!processBean.getPackageName().equals(getPackageName())) {
                        processBean.setCheck(!processBean.isCheck());
                        //状态的取反目的是让页面尽心更新,所以在状态取反后必须调用notifyDataSetChanged()
                        myAdapter.notifyDataSetChanged();
                    }
                } else {
                    //点击完成后,需要将选中条目指向对象的isCheck进行取反操作
                    ProcessBean processBean = customerList.get(position);
                    //手机卫士应用没有勾选和未勾选状态变化,所以不需要进行页面更新
                    if (!processBean.getPackageName().equals(getPackageName())) {
                        processBean.setCheck(!processBean.isCheck());
                        //状态的取反目的是让页面尽心更新,所以在状态取反后必须调用notifyDataSetChanged()
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

###  1.21.3.	全选的实现

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

将所有应用进程的标示（除了我们自己的手机卫士进程）都改成true，然后刷新界面。

        private void selectAll() {
        if (showSys) {
            for (int i = 0; i < procesInfoList.size(); i++) {
                ProcessBean processBean = procesInfoList.get(i);
                if (processBean.getPackageName().equals(getPackageName())) {
                    continue;//跳出本次循环,继续下一次循环
                }
                processBean.setCheck(true);
            }
            myAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < customerList.size(); i++) {
                ProcessBean processBean = customerList.get(i);
                if (processBean.getPackageName().equals(getPackageName())) {
                    continue;//跳出本次循环,继续下一次循环
                }
                processBean.setCheck(true);
            }
            myAdapter.notifyDataSetChanged();
        }
    }

###  1.21.4.	反选的实现

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

反选的意思是当前列表中的所有条目如果是选中状态的，变成非选中状态，如果是非选中的，全部变成选中状态。

        private void selectReverse() {
        if (showSys) {
            for (int i = 0; i < procesInfoList.size(); i++) {
                ProcessBean processBean = procesInfoList.get(i);
                if (processBean.getPackageName().equals(getPackageName())) {
                    continue;//跳出本次循环,继续下一次循环
                }
                processBean.setCheck(!processBean.isCheck());
            }
            myAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < customerList.size(); i++) {
                ProcessBean processBean = customerList.get(i);
                if (processBean.getPackageName().equals(getPackageName())) {
                    continue;//跳出本次循环,继续下一次循环
                }
                processBean.setCheck(!processBean.isCheck());
            }
            myAdapter.notifyDataSetChanged();
        }
    }

###  1.21.5.	清理的实现

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/2.png)

将选中的进程清理掉。

    private void killProcess() {
        ArrayList<ProcessBean> tempProcessInfoList = new ArrayList<>();
        if (showSys) {
            tempProcessInfoList.addAll(procesInfoList);
        } else {
            tempProcessInfoList.addAll(customerList);
        }
        int count = 0;
        int releaseMemory = 0;
        //1.从ListView中移除条目,本质上海市操作集合中的对象删除
        for (int i = 0; i < tempProcessInfoList.size(); i++) {
            ProcessBean processBean = tempProcessInfoList.get(i);
            if (processBean.getPackageName().equals(getPackageName())) {
                continue;
            }
            if (processBean.isCheck()) {
                //被选中条目,从集合中移除
                procesInfoList.remove(processBean);
                //如果此对象在用户集合汇总,则需要从用户集合中移除对象
                if (customerList.contains(processBean)) {
                    customerList.remove(processBean);
                }
                //如果此对象在系统集合中,则需要从系统集合中移除对象
                if (systemList.contains(processBean)) {
                    systemList.remove(processBean);
                }
                //在移除条目的同时,还需要杀死进程
                ProcessInfoProvider.killProcess(getApplicationContext(), processBean.getPackageName());
                //每杀死一个进程,则累加1
                count++;
                //将杀死每一个进程释放的空间进行累加
                releaseMemory += processBean.getSize();//byte
            }
        }
        String strReleaseMemory = android.text.format.Formatter.formatFileSize(getApplicationContext(), releaseMemory);
        Toast.makeText(this, "杀死" + count + "个进程,释放" + strReleaseMemory + "空间", Toast.LENGTH_SHORT).show();
        myAdapter.notifyDataSetChanged();
        //正在运行进程总数变化
        runningProcess = runningProcess - count;
        pivProcess.setMiddleText("正在运行进程数" + runningProcess + "个");
        //正在运行进程总数占比变化
        long progressMem = runningProcess * 100 / allProcess;
        pivProcess.setProgressBar((int) progressMem);
    }

##  1.22.	抽屉菜单的实现

抽屉菜单效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/26.png)

###  1.22.1.	需求

实现SlidingDrawer的使用。

###  1.22.2.	抽屉菜单的布局

                <SlidingDrawer
                android:id="@+id/sd"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:content="@+id/content"
                android:handle="@+id/handle">
                <!--相对布局作为把手-->
                <RelativeLayout
                    android:id="@id/handle"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:background="@mipmap/drawer_bg"
                    android:gravity="center">

                    <ImageView
                        android:id="@+id/iv_arrow1"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="10dp"
                        android:src="@mipmap/drawer_arrow_up" />

                    <ImageView
                        android:id="@+id/iv_arrow2"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_below="@id/iv_arrow1"
                        android:src="@mipmap/drawer_arrow_up" />
                </RelativeLayout>
                <!--线性布局作为抽屉的内容-->
                <LinearLayout
                    android:id="@id/content"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:background="#fff"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="进程管理设置" />

                    <ngyb.mobicop.view.SettingItemView
                        android:id="@+id/siv_show_sys"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:background="@drawable/selector_first_bg"
                        mobicop:isToggle="true"
                        mobicop:title="显示系统进程" />

                    <ngyb.mobicop.view.SettingItemView
                        android:id="@+id/siv_lock_clean"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:background="@drawable/selector_last_bg"
                        mobicop:isToggle="true"
                        mobicop:title="锁屏自动清理" />
                </LinearLayout>
            </SlidingDrawer>

###  1.22.3.	设置开关动画

1.初始化控件。

        @BindView(R.id.sd)
    SlidingDrawer sd;

2.创建渐变动画，并在oncreate/initview中调用，表示进入界面就执行动画。当上拉抽屉菜单关闭状态时，通过动画的显示让用户知道这个菜单可以上拉。

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/27.png)

        private void openAnimation() {
        //1.将之前运行的动画停止
        ivArrow1.clearAnimation();
        ivArrow2.clearAnimation();
        //2.将箭头方向变成向下
        ivArrow1.setImageResource(R.mipmap.drawer_arrow_down);
        ivArrow2.setImageResource(R.mipmap.drawer_arrow_down);
        //3.触发朝下箭头动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(200);
        ivArrow1.startAnimation(alphaAnimation);
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(1.0f, 0.3f);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        alphaAnimation1.setDuration(200);
        ivArrow2.startAnimation(alphaAnimation1);
    }

3.当用户打开了上拉菜单，这时候就需要关闭动画了。

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/26.png)

        private void closeAnimation() {
        //1.将之前运行的动画停止
        ivArrow1.clearAnimation();
        ivArrow2.clearAnimation();
        ivArrow1.setImageResource(R.mipmap.drawer_arrow_up);
        ivArrow2.setImageResource(R.mipmap.drawer_arrow_up);
        //1.透明度(0.3f->1.0f,0f->0.3f->1.0f)
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(200);
        ivArrow1.startAnimation(alphaAnimation);
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(1.0f, 0.3f);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        alphaAnimation1.setDuration(200);
        ivArrow2.startAnimation(alphaAnimation1);
    }

##  1.23.	隐藏显示系统进程

隐藏和显示系统进程开关位置：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/26.png)

###  1.23.1.	需求

隐藏和显示系统进程。

###  1.23.2.	设置标示

设置标示，用来判断系统进程的显示和隐藏。

        private boolean showSys;

###  1.23.3.	修改getCount()方法返回值

当需要隐藏系统进程时，getCount的返回值就是应用进程的数量；当需要系统进程时，getCount的返回值就是系统进程的数量+应用进程的数量。

                if (showSys) {
                //显示系统进程
                return procesInfoList.size();
            } else {
                //不显示系统进程,只显示用户进程
                return customerList.size();
            }

###  1.23.4.	条目点击设置标示

点击是否显示系统进程的开关，如果当前开关是关闭的状态则变成打开状态，如果当前开关是打开的状态则变成要关闭状态。获取到最新的开关状态需要保存在本地，在显示进程列表的时候需要用到。

            sivLockClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.状态取反,图片切换
                sivLockClean.reverseState();
                //2.根据取反后的状态决定开启服务还是关闭服务
                boolean openState = sivLockClean.getOpenState();
                if (openState) {
                    //点击后需要开启服务
                    startService(new Intent(getApplicationContext(), LockCleanService.class));
                } else {
                    //点击后需要关闭服务
                    stopService(new Intent(getApplicationContext(), LockCleanService.class));
                }
            }
        });

###  1.23.5.	回显设置

当用户再进入进程管理界面打开抽屉菜单时，需要将保存在本地的是否显示系统进程的标示取出来，作为显示开关状态依据。

            //在开启此界面的时候,需要从sp中获取是否显示系统进程的状态
        showSys = SharePreUtils.getBooleanValue(getApplicationContext(), Constant.SHOW_SYS_PROCESS, true);
        //showSys状态决定图片的红色和绿色
        sivShowSys.setOpenState(showSys);

##  1.24.	锁屏清理进程

锁屏清理进程功能效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/26.png)

###  1.24.1.	需求

锁屏实现清理进程操作。

###  1.24.2.	注册锁屏广播接收者

创建锁屏监听的Service，在Service中代码注册广播接收者。

                    if (openState) {
                    //点击后需要开启服务
                    startService(new Intent(getApplicationContext(), LockCleanService.class));

###  1.24.3.	清理进程

当锁屏操作被锁屏的广播接收到时，在接收者内部进行清理进程。

        class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //杀死所有进程,不要自杀
            ProcessInfoProvider.killAllProcess(context);
        }
    }

        /**
     * @param ctx 上下文环境
     *            杀死手机中的进程
     */
    public static void killAllProcess(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo processInfo = runningAppProcesses.get(i);
            if (processInfo.processName.equals(ctx.getPackageName())) {
                continue;
            }
            am.killBackgroundProcesses(processInfo.processName);
        }
    }

###  1.24.4.	注销广播接收者

当服务销毁时，需要注销锁屏广播接收者。

        @Override
    public void onDestroy() {
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
        super.onDestroy();
    }

##  1.25.	PopuWindow实现

PopuWindow的效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/28.png)

###  1.25.1.	需求

点击软件列表显示PopuWindow。

###  1.25.2.	PopuWindow动画

1.给popuwindow设置动画。

            //给冒泡窗体设置动画效果
        popupWindow.setAnimationStyle(R.style.PoputWindowAnimationStyle);

2.在styles.xml文件中设置动画样式。

        <style name="PoputWindowAnimationStyle">
	        <!--对话框窗体进入的动画效果-->
	        <item name="android:windowEnterAnimation">@anim/popup_enter</item>
	        <!--对话框窗体退出的动画效果-->
	        <item name="android:windowExitAnimation">@anim/popup_exit</item>
	    </style>

3.修改动画样式。


    <set xmlns:android="http://schemas.android.com/apk/res/android"
        android:shareInterpolator="false">
	    <translate android:fromYDelta="0" android:toYDelta="100%" android:duration="300" android:interpolator="@anim/accelerate_quint"/>
	    <alpha android:fromAlpha="1.0" android:toAlpha="0.0" android:duration="300"
	        android:interpolator="@anim/accelerate_cubic"/>
	</set>

###  1.25.3.	展示PopuWindow

1.设置listview条目点击事件

            lvProcess.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //1.长按某个条目后,将选中条目的对象获取出来
                if (showSys) {
                    bean = procesInfoList.get(position);
                } else {
                    bean = customerList.get(position);
                }
                //2.弹出冒泡窗体
                showPopupWindow(view);
                return false;
            }
        });

2.在点击事件中将自定义View的布局效果通过PopuWindow进行展示。

        private void showPopupWindow(View view) {
        View viewPopup = View.inflate(this, R.layout.layout_popup_window, null);
        TextView tvUninstall = viewPopup.findViewById(R.id.tv_uninstall);
        TextView tvOpen = viewPopup.findViewById(R.id.tv_open);
        TextView tvShare = viewPopup.findViewById(R.id.tv_share);
        TextView tvInfo = viewPopup.findViewById(R.id.tv_info);
        tvUninstall.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        tvInfo.setOnClickListener(this);
        //1.构建冒泡窗体对象
        popupWindow = new PopupWindow(viewPopup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //冒泡窗体如果想在点击会退按钮的时候隐藏,则必须添加如下方法
        //让冒泡窗体可以获取焦点
        popupWindow.setFocusable(true);
        //让冒泡窗体有背景\
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //给冒泡窗体设置动画效果
        popupWindow.setAnimationStyle(R.style.PoputWindowAnimationStyle);
        //2.找个位置让冒泡窗体显示
        popupWindow.showAsDropDown(view, -60, 60);
    }

第20-21行，contentView是设置填充的veiw对象，参数2是view对象宽度，参数3是view对象的高度。

第23行，这个方法的意思是PopuWindow显示挂载在哪个View之下，这里是挂载在ListView的条目底下，参数1就是PopuWindow的挂载View，参数2是确定x轴坐标，参数3是确定y轴坐标。 

###  1.25.4.	隐藏PopuWindow

当点击其他区域或者选择某个选项之后就需要隐藏PopuWindow。

                    //隐藏冒泡窗体
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

##  1.26.	PopuWindow菜单功能实现

###  1.26.1.	需求

实现卸载、打开、分享、详情的功能。

###  1.26.2.	功能实现

####  1.26.2.1.	卸载程序

卸载程序效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/29.png)

通过意图的方式调用系统卸载器进行程序的卸载。

                    Intent intent = new Intent("android.intent.action.DELETE");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package:" + bean.getPackageName()));
                startActivity(intent);
                //隐藏冒泡窗体
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

####  1.26.2.2.	打开程序

打开程序效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/9.png)

通过PackageManager获取对应程序的意图启动器，若得到的意图不为空则启动意图打开程序，否则提示无法启动。

                    //根据应用程序报名开启应用
                //1.包管理者对象PackageManager
                PackageManager packageManager = getPackageManager();
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(bean.getPackageName());
                if (launchIntentForPackage != null) {
                    startActivity(launchIntentForPackage);
                }
                //隐藏冒泡窗体
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

####  1.26.2.3.	分享程序

分享程序效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/30.png)

                    Intent intent1 = new Intent("android.intent.action.SEND");
                intent1.addCategory("android.intent.category.DEFAULT");
                intent1.putExtra(Intent.EXTRA_TEXT, "分享一个应用" + bean.getName());
                startActivity(intent1);
                //隐藏冒泡窗体
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

####  1.26.2.4 程序详情

程序详情效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/31.png)

通过意图跳转到程序详情界面。

    			Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent2.setData(Uri.fromParts("package" , bean.getPackageName(),null));
                startActivity(intent2);

##  1.27.	短信的备份和还原

短信备份和还原的功能位置图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/32.png)


###  1.27.1.	需求

1．获取短信

2．备份短信

3．还原短信

###  1.27.2.	获取短信

从本地数据库中读取需要备份的短信数据。

        public static void backUp(final Context ctx, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                FileWriter fileWriter = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    try {
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sms.json";
                        //内容提供者 对外提供自身数据库的访问入口
                        //内容解析者 通过内容提供者提供的访问入口,访问数据库中的数据
                        //内容观察者 观察数据库数据的变化,一旦发生变化,则对外发出通知
                        ContentResolver contentResolver = ctx.getContentResolver();
                        Uri uri = Uri.parse("content://sms/");
                        String[] projection = {"address", "date", "read", "type", "body"};
                        cursor = contentResolver.query(uri, projection, null, null, null);
                        //告知进度条,总进度为多少
                        //因为现实总进度的控件,频繁改动,所以位置具体的控件类型是什么,在此处就将显示最大进度的操作,封装在一个未实现的方法中
                        if (callBack != null) {
                            callBack.setMax(cursor.getCount());
                        }
                        ArrayList<SmsInfo> smsInfoArrayList = new ArrayList<>();
                        int count = 0;
                        //游标可以向下移动,获取每一条数据中的字段的值
                        while (cursor.moveToNext()) {
                            String address = cursor.getString(0);
                            int date = cursor.getInt(1);
                            int read = cursor.getInt(2);
                            int type = cursor.getInt(3);
                            String body = cursor.getString(4);
                            count++;
                            //此段代码用到的控件未知,所以封装到未实现方法中去
                            if (callBack != null) {
                                callBack.setProgress(count);
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            SmsInfo smsInfo = new SmsInfo(address, date, read, type, body);
                            Log.e(TAG, "run: "+smsInfo.toString() );
                            smsInfoArrayList.add(smsInfo);
                        }
                        //需要将数据写入到json文件中,smsInfoArrayList集合中的数据---------->json文件中
                        Gson gson = new Gson();
                        String json = gson.toJson(smsInfoArrayList);
                        Log.e(TAG, "run: "+json );
                        File file = new File(path);
                        fileWriter = new FileWriter(file);
                        fileWriter.write(json);
                        fileWriter.flush();
                        //如果代码能够执行到这里,则说明备份成功
                        ((Activity) ctx).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //在此处调用备份成功的方法
                                if (callBack != null) {
                                    callBack.onSuccessed();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        ((Activity) ctx).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //在此处调用备份失败的方法
                                if (callBack != null) {
                                    callBack.onFail();
                                }
                            }
                        });
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

###  1.27.3.	备份短信

备份短信效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/33.png)

将短信保存到本地文件中。

                            //需要将数据写入到json文件中,smsInfoArrayList集合中的数据---------->json文件中
                        Gson gson = new Gson();
                        String json = gson.toJson(smsInfoArrayList);
                        Log.e(TAG, "run: "+json );
                        File file = new File(path);
                        fileWriter = new FileWriter(file);
                        fileWriter.write(json);
                        fileWriter.flush();

###  1.27.4.	回调监听

当短信比较多的时候，备份的时间可能会比较长，这时候需要知道备份的进度如下图所示。

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/33.png)

1.创建接口。

        public interface CallBack {
        //2.分析要提供几个未实现的方法,有几段未知逻辑,有几段未知逻辑就有几个方法
        //1.设置总进度方法
        public void setMax(int maxProgress);

        //2.设置当前进度方法
        public void setProgress(int currentProgress);

        //3.备份成功,如何告知用户
        public void onSuccessed();

        //4.备份失败,如何告知用户
        public void onFail();
    }

2.设置接口。

                                if (callBack != null) {
                                callBack.setProgress(count);
                            }

3.使用接口。

            SmsUtils.backUp(this, new SmsUtils.CallBack() {
            @Override
            public void setMax(int maxProgress) {
                //对话框设置总进度
                progressDialog.setMax(maxProgress);
                //用进度条设置总进度
            }

            @Override
            public void setProgress(int currentProgress) {
                progressDialog.setProgress(currentProgress);
            }

            @Override
            public void onSuccessed() {
                Toasty.success(CommonToolActivity.this, "备份成功", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFail() {
                Toasty.error(CommonToolActivity.this, "备份失败", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

###  1.27.5	还原短信

还原短信的过程：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/34.png)


![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/35.png)


当用户选择了还原短信，就从本地文件中取出备份的短信重新写到数据中。

        /**
     * 备份还原
     */
    private void smsRestore() {
        Log.e(TAG, "smsRestore: 短信还原");
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sms.json")));
            String readLine = br.readLine();
            Gson gson = new Gson();
            List<SmsInfo> list = gson.fromJson(readLine, new TypeToken<List<SmsInfo>>() {
            }.getType());
            for (SmsInfo smsInfo : list) {
                ContentResolver resolver = getContentResolver();
                Uri uri = Uri.parse("content://sms/");
                ContentValues values = new ContentValues();
                values.put("address", smsInfo.getAddress());
                values.put("date", smsInfo.getDate());
                values.put("type", smsInfo.getType());
                values.put("body", smsInfo.getBody());
                resolver.insert(uri, values);
            }
            Toasty.success(this, "短信还原成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "smsRestore: " + e.getLocalizedMessage().toString());
            Toasty.error(this, "短信还原失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

##  1.28.	程序锁界面实现

###  1.28.1.	需求

实现程序锁界面数据展示操作。

###  1.28.2.	界面搭建

程序锁界面效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/36.png)

    <?xml version="1.0" encoding="utf-8"?>
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:orientation="vertical">
	
	    <RelativeLayout
	        android:layout_width="match_parent"
	        android:layout_height="45dp"
	        android:background="#00a2ff"
	        android:gravity="center">
	
	        <Button
	            android:id="@+id/btn_unlock"
	            android:layout_width="wrap_content"
	            android:layout_height="25dp"
	            android:background="@drawable/shape_unlock_blue_bg"
	            android:text="未加锁"
	            android:textColor="#fff"
	            android:textSize="12sp" />
	
	        <Button
	            android:id="@+id/btn_lock"
	            android:layout_width="wrap_content"
	            android:layout_height="25dp"
	            android:layout_toRightOf="@id/btn_unlock"
	            android:background="@drawable/shape_lock_white_bg"
	            android:text="已加锁"
	            android:textColor="#00f"
	            android:textSize="12sp" />
	    </RelativeLayout>
	
	    <LinearLayout
	        android:id="@+id/ll_unlock"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:orientation="vertical">
	
	        <TextView
	            android:id="@+id/tv_unlock"
	            android:layout_width="match_parent"
	            android:layout_height="wrap_content"
	            android:background="#666"
	            android:padding="5dp"
	            android:text="未加锁"
	            android:textColor="#fff" />
	
	        <ListView
	            android:id="@+id/lv_unlock"
	            android:layout_width="match_parent"
	            android:layout_height="wrap_content"></ListView>
	    </LinearLayout>
	
	    <LinearLayout
	        android:id="@+id/ll_lock"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:orientation="vertical"
	        android:visibility="gone">
	
	        <TextView
	            android:id="@+id/tv_lock"
	            android:layout_width="match_parent"
	            android:layout_height="wrap_content"
	            android:background="#666"
	            android:padding="5dp"
	            android:text="已加锁"
	            android:textColor="#fff" />
	
	        <ListView
	            android:id="@+id/lv_lock"
	            android:layout_width="match_parent"
	            android:layout_height="wrap_content"></ListView>
	    </LinearLayout>
	</LinearLayout>

###  1.28.3.	程序加锁和解锁

因为要加锁的程序有很多，所以我们需要将要加锁的程序保存到数据库中。当解锁某个程序就再从数据库中移除这个程序的信息。

                    if (isLock) {
                    rightToLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            lockActivity.changeList(isLock, appInfo);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    //在已加锁的列表中,点击右侧锁,触发业务就是  已加锁 ----->未加锁
                    viewAnimation.startAnimation(rightToLeftAnimation);
                } else {
                    //监听动画的执行完成
                    leftToRightAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //动画刚刚开始的时候,会触发的方法
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            lockActivity.changeList(isLock, appInfo);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            //动画重复的时候会触发的方法
                        }
                    });
                    //在未加锁的列表中,点击右侧锁,触发业务就是  未加锁 ----->已加锁
                    viewAnimation.startAnimation(leftToRightAnimation);
                }

###  1.28.4.	获取所有软件信息

因为获取未加锁和已加锁程序的操作是比较费时的，所以需要在子线程中进行查询。

            new Thread() {
            @Override
            public void run() {
                appLockDao = new AppLockDao(LockActivity.this);
                lockPackageNameList = appLockDao.queryAll();
                //安装在手机上面应用信息的集合
                AppInfoProvider appInfoProvider = new AppInfoProvider();
                ArrayList<AppInfo> appInfoList = appInfoProvider.getAppInfoList(LockActivity.this);
                //区分已加锁和未加锁应用程序
                for (int i = 0; i < appInfoList.size(); i++) {
                    AppInfo appInfo = appInfoList.get(i);
                    //判断appinfo指向应用程序的包名是否包含在lockPackageNameList集合中
                    if (lockPackageNameList.contains(appInfo.getPackageName())) {
                        //appInfo是加锁应用
                        lockList.add(appInfo);
                    } else {
                        //appInfo是未加锁应用
                        unlockList.add(appInfo);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //回到主线程中拿上诉集合填充2个ListView数据适配器
                        //false代表的就是创建的未加锁数据适配器对象
                        unLockAdapter = new LockAdapter(LockActivity.this, unlockList, false);
                        lvUnlock.setAdapter(unLockAdapter);
                        //true代表的就是创建的已加锁数据适配器对象
                        lockAdapter = new LockAdapter(LockActivity.this, lockList, true);
                        lvLock.setAdapter(lockAdapter);
                    }
                });
                super.run();
            }
        }.start();

###  1.28.5.	展示数据

将获取的软件信息用列表进行显示。

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/36.png)

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/37.png)

        @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(lockActivity, R.layout.item_lock, null);
            viewHolder = new ViewHolder();
            viewHolder.ivIocn = convertView.findViewById(R.id.iv_icon);
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.ivlock = convertView.findViewById(R.id.iv_lock);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final AppInfo appInfo = (AppInfo) getItem(position);
        viewHolder.ivIocn.setBackgroundDrawable(appInfo.getDrawable());
        viewHolder.tvName.setTextColor(Color.BLACK);
        viewHolder.tvName.setText(appInfo.getName());
        if (isLock) {
            //目前展示的是已加锁的一个条目,右侧的锁是打开的
            viewHolder.ivlock.setImageResource(R.drawable.selector_iv_unlock_bg);
        } else {
            //目前展示的是未加锁的一个条目,右侧的锁是关闭的
            viewHolder.ivlock.setImageResource(R.drawable.selector_iv_lock_bg);
        }
        final View viewAnimation = convertView;
        viewHolder.ivlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLock) {
                    rightToLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            lockActivity.changeList(isLock, appInfo);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    //在已加锁的列表中,点击右侧锁,触发业务就是  已加锁 ----->未加锁
                    viewAnimation.startAnimation(rightToLeftAnimation);
                } else {
                    //监听动画的执行完成
                    leftToRightAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //动画刚刚开始的时候,会触发的方法
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            lockActivity.changeList(isLock, appInfo);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            //动画重复的时候会触发的方法
                        }
                    });
                    //在未加锁的列表中,点击右侧锁,触发业务就是  未加锁 ----->已加锁
                    viewAnimation.startAnimation(leftToRightAnimation);
                }
            }
        });
        return convertView;
    }

###  1.28.6.	加锁和未加锁的界面切换

点击按钮改变按钮的样式和切换界面。

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/38.png)

        @OnClick({R.id.btn_unlock, R.id.btn_lock, R.id.tv_unlock, R.id.ll_unlock, R.id.tv_lock, R.id.ll_lock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_unlock:
                //由未加锁点中变成已加锁点中,触发此处的额地点时间
                //未加锁的按钮图片变成白色
                btnUnlock.setBackgroundResource(R.drawable.shape_unlock_blue_bg);
                //已加锁的按钮图片变为蓝色
                btnLock.setBackgroundResource(R.drawable.shape_lock_white_bg);
                //未加锁文字变成蓝色
                btnUnlock.setTextColor(Color.WHITE);
                //已加锁文字颜色变成白色
                btnLock.setTextColor(Color.BLUE);
                llLock.setVisibility(View.GONE);
                llUnlock.setVisibility(View.VISIBLE);
                //更新已加锁和未加锁文本内容
                if (unlockList != null) {
                    tvUnlock.setText("未加锁(" + unlockList.size() + ")");
                }
                if (lockList != null) {
                    tvLock.setText("已加锁(" + lockList.size() + ")");
                }
                break;
            case R.id.btn_lock:
                //由未加锁点中变成已加锁点中,触发此处的额点击事件
                //未加锁的按钮图片变成白色
                btnUnlock.setBackgroundResource(R.drawable.shape_unlock_white_bg);
                //已加锁的按钮图片变为蓝色
                btnLock.setBackgroundResource(R.drawable.shape_lock_blue_bg);
                //未加锁文字变成蓝色
                btnUnlock.setTextColor(Color.BLUE);
                //已加锁文字颜色变成白色
                btnLock.setTextColor(Color.WHITE);
                llLock.setVisibility(View.VISIBLE);
                llUnlock.setVisibility(View.GONE);
                //更新已加锁和未加锁文本内容
                if (unlockList != null) {
                    tvUnlock.setText("未加锁(" + unlockList.size() + ")");
                }
                if (lockList != null) {
                    tvLock.setText("已加锁(" + lockList.size() + ")");
                }
                break;
            case R.id.tv_unlock:
                break;
            case R.id.ll_unlock:
                break;
            case R.id.tv_lock:
                break;
            case R.id.ll_lock:
                break;
        }
    }

###  1.28.7.	加锁解锁动画实现

####  1.28.7.1.	创建动画

当程序被加锁时，程序条目向右边平移；当程序解锁时，程序条目向左边平移。


            //要执行动画的view,由开始位置,移动到离自身宽度(+)为100%那个坐标位置
        leftToRightAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        leftToRightAnimation.setDuration(1000);
        rightToLeftAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        rightToLeftAnimation.setDuration(1000);

####  1.28.7.2.	使用动画

当加锁的时候使用向右滑动的动画，当解锁的时候使用向左的动画。

            if (isLock){
            //已加锁----->未加锁
            //1.已加锁集合移除对象
            lockList.remove(appInfo);
            //2.未加锁集合添加对象
            unlockList.add(0, appInfo);
            //3.刷新2个数据适配器
            unLockAdapter.notifyDataSetChanged();
            lockAdapter.notifyDataSetChanged();
            //4.数据库中需要删除一条数据
            appLockDao.delete(appInfo.getPackageName());
        }else{
            //动画结束的时候会触发的方法
            //1.未加锁中移除选中的这个条目的对象
            unlockList.remove(appInfo);
            //2.已加锁集合中添加这个对象
            lockList.add(0, appInfo);
            //3.通知数据适配器更新
            unLockAdapter.notifyDataSetChanged();
            lockAdapter.notifyDataSetChanged();
            //4.数据库数据变化,数据库中添加一条数据
            appLockDao.insert(appInfo.getPackageName());
        }

##  1.29.	电子狗服务

###  1.29.1.	需求

监听用户打开应用程序，并弹出解锁界面。

###  1.29.2.	监听用户打开程序

计算器为例加锁效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/41.png)


![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/42.png)

因为要时时刻刻监听用户打开应用程序，所以在服务中进行操作。

        private void startRun() {
        //循环的特点
        //1.可以受服务开启和关闭的控制,控制循环执行还是停止
        //2.死循环是一个耗时操作,所以需要放到子线程中
        new Thread() {
            @Override
            public void run() {
                //1.从数据库中读取已加锁应用程序的包名集合
                appLockDao = new AppLockDao(DogService.this);
                packageNameList = appLockDao.queryAll();
                while (isRunning) {
                    Log.e(TAG, "run: 看门狗服务正在运行中");
                    //在此处一直执行子线程中的代码非常消耗cpu,所以让子线程有一段休眠时间
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String packageName = getTopApp(DogService.this);
                    //2.在循环中判断开启的应用程序是否已加锁的应用,即是否包含包名包含在packageNameList集合中
                    //2.1获取开启用用程序包名
                    //获取开启的任务栈是那个
	//                    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	//                    List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
	//                    //获取正在运行的任务栈,刚刚代开的那个应用的任务栈的信息对象
	//                    ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
	//                    ComponentName componentName = runningTaskInfo.topActivity;
	//                    //获取刚刚开启的应用程序的包名
	//                    String packageName = componentName.getPackageName();
                    //优先判断此应用程序是否已经输入过密码,正常解锁了
                    if (unLockPackageNameList.contains(packageName)) {
                        continue;
                    }
                    //2.2判断步骤1的包名是否在packageNameList集合中
                    if (packageNameList.contains(packageName)) {
                        //开启的应用程序是加锁的应用程序,需要弹出拦截界面
                        Intent intent = new Intent(getApplicationContext(), InterceptetActivity.class);
                        //在开启拦截界面的视乎,需要告知拦截的是那个应用
                        intent.putExtra("packagename", packageName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                super.run();
            }
        }.start();
    }

###  1.29.3.	返回键bug处理

当在解锁界面点击返回键表示想退出时，发现又进入输入密码界面了，对于这个bug的处理，可以在watchdogActivity中重写onkeydown方法，在其中对返回键进行处理，屏蔽返回键并finish()界面。

        @Override
    public void onBackPressed() {
        //一旦点击了回退按钮,则需要让应用程序回到手机桌面(应用)
         /*<intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.HOME" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.MONKEY"/>
        </intent-filter>*/
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
        super.onBackPressed();
    }

###  1.29.4.	解锁bug

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/42.png)

加锁界面不退出，点击home键，再次打开其他加锁程序，发现显示的图标和名称不对（如上图所示第一次打开的是计算器应用，第二次打开日历应用还是计算器应用的界面），原因是点击home最小化，再次打开其他程序的时候发现之前的界面已经创建过了，所以直接把界面移动到最前面。解决办法是将操作放到onstart方法中，或者在界面不可见的时候把界面finish掉。

        @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

###  1.29.5.	解锁操作

解锁之后又进入输入密码界面，所以解锁之后我们要告诉看门狗，这个应用临时不能加锁，如果解锁之后把包名移出，这样之后就再也不会加锁了，用户体验不好，我们应该在解锁之后，通知服务把应用设置成临时不加锁的程序，那这个涉及到数据传递了，在服务中是不能使用startActivityforresult的，我们可以通过发广播的形式来告诉服务。

1.当用户输入密码之后解锁成功之后，发送自定义广播告诉加密狗这个程序在没销毁之前都不用再输入密码了。

        @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        //1.获取输入框中输入的密码,如果符合要求这需要结束当前拦截界面activity
        String psd = etPsd.getText().toString();
        if (psd.equals("123")) {
            //如果解锁成功,则需要告知看门狗跳过此应用的检测过程
            //发送广播,告知DogService,要跳过解锁应用程序的检测过程
            //action就是用于给广播接受者匹配,是谁发送的广播
            Intent intent = new Intent("Intent.action.UNLOCK");
            intent.putExtra("packagename", packagename);
            sendBroadcast(intent);
            finish();
        }
    }


1．服务中代码注册广播接受者。

            //z注册一个广播接受者和,用于接收InterceptActivity中发出的广播
        myReceiver = new DogReceiver(DogService.this);
        IntentFilter intentFilter = new IntentFilter("Intent.action.UNLOCK");
        //解锁
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //锁屏
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myReceiver, intentFilter);


2．广播接受者中接受传递的数据，解锁的广播接受者。

    public class DogReceiver extends BroadcastReceiver {
	    private final DogService dogService;
	
	    public DogReceiver(DogService dogService) {
	        this.dogService = dogService;
	    }
	
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        dogService.receiver(intent);
	    }
	}

3．解锁操作。

                        if (packageNameList.contains(packageName)) {
                        //开启的应用程序是加锁的应用程序,需要弹出拦截界面
                        Intent intent = new Intent(getApplicationContext(), InterceptetActivity.class);
                        //在开启拦截界面的视乎,需要告知拦截的是那个应用
                        intent.putExtra("packagename", packageName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

###  1.29.6.	看门狗解锁bug

点击home键最小化手机卫士，打开加锁应用解锁，发现看到的是手机卫士，退出手机卫士才看到解锁的应用。解决办法是让看门狗activity在单独的任务栈中。

            <activity
	            android:name=".activity.InterceptetActivity"
	            android:launchMode="singleInstance"
	            android:screenOrientation="portrait" />

###  1.29.7.	最近列表bug的解决

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/43.png)

打开加锁应用，显示出看门狗界面解锁。然后再长按home键，选择手机卫士应用，发现看门狗界面显示出来了。解决办法是将应用从最近列表中删除如上图所示。

            <activity
	            android:name=".activity.InterceptetActivity"
	            android:launchMode="singleInstance"
	            android:excludeFromRecents="true"
	            android:screenOrientation="portrait" />

##  1.30.	已加锁数据库更新

###  1.30.1.	内容观察者观察数据库变化

发现当我们在对一个程序加锁时，集合没有及时更新新加入的程序，所以加锁的程序打开时不会弹出看门狗界面。

1．添加数据，通知内存观察者，进行数据更新操作。

            contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://ngyb.mobicop.change");
        DogContentObserver myConentObserver = new DogContentObserver(null, DogService.this);
        contentResolver.registerContentObserver(uri, true, myConentObserver);
 

2．更新内存数据，当数据库发生变化的时候，重新获取所有的数据库数据存放到内存中。

        @Override
    public void onChange(boolean selfChange) {
        //当监听的数据库中的数据发生变化的时候,调用的方法
        //重新获取数据库中的最新的数据,用最新的数据判断开启的应用程序是否为加锁应用,是否要弹出拦截界面
        dogService.change();
        super.onChange(selfChange);
    }

##  1.31.	病毒的简介


###  1.31.1.	需求

介绍什么是病毒。

###  1.31.2.	病毒简介

病毒其实本质就是计算机程序，他具备一定的恶意性，病毒可以分为三类：

1.恶搞，纯粹就是为了炫耀自己的技术，损人不利己的病毒。

2.灰鸽子，盗取别人账号密码，比如盗取qq，游戏账号等等。

3.肉鸡，把你的电脑当成一个傀儡机，通过肉鸡程序就可以控制你的电脑。

##  1.32.	杀毒界面搭建

###  1.32.1.	需求

杀毒界面搭建。

###  1.32.2.	ArcProgress的使用

1．在布局文件中使用ArcProgress	，下面是布局中使用到的属性的含义。

| arc_progress | 当前进度 | 
| :------:| :------: | 
| arc_angle	  | 进度条显示的角度 |
| arc_stroke_width	 | 设置边框的宽度 |
| arc_max	 | 设置最大进度 |
| custom:arc_unfinished_color="#ff0000"	 | 设置未完成的进度颜色 |
| custom:arc_finished_color="#00ff00"	 | 设置已完成进度颜色 |
| custom:arc_text_size="100sp"	 | 设置百分比字体大小 |
| custom:arc_text_color="@android:color/white"	 | 设置百分比字体颜色 |
| custom:arc_suffix_text="Hello world"	 | 设置%显示文本 |
| custom:arc_suffix_text_size="20sp" 	 | 设置%显示字体大小 |
| custom:arc_suffix_text_padding="15dp"	 | 设置%文本距离百分比字体的距离 |
| custom:arc_bottom_text="扫描中"	 | 底部的文本 |
| custom:arc_bottom_text_size="15sp"	 | 底部文本的字体大小 |


                <!--此处需要添加自定义控件的布局 circleProgress-->
            <com.github.lzyzsd.circleprogress.ArcProgress
                android:id="@+id/arc_progress"
                android:layout_width="135dp"
                android:layout_height="135dp"
                android:layout_centerHorizontal="true"
                android:background="#00a2ff"
                custom:arc_bottom_text="扫描"
                custom:arc_stroke_width="8dp"
                custom:arc_text_color="#fff" />

2．在代码中对ArcProgress进行初始化了以及刷新进度。

                PackageManager pm = getPackageManager();
            List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
            //获取应用的签名文件 --->签名文件字符串--->md5码(32位16进制字符串
            //扫描应用总数获取
            maxSize = installedPackages.size();
            for (int i = 0; i < installedPackages.size(); i++) {
                if (isStop) {
                    break;
                }
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取每一个安装在手机上的应用的信息
                PackageInfo packageInfo = installedPackages.get(i);
                String packageName = packageInfo.packageName;
                Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
                String name = packageInfo.applicationInfo.loadLabel(pm).toString();
                //获取每一个用用信息中存储的签名文件
                Signature[] signatures = packageInfo.signatures;
                //获取数组位置0的对象,就是目前扫描到的应用的签名文件对象
                Signature signature = signatures[0];
                //签名文件转换成字符串
                String strSignature = signature.toCharsString();
                //字符串转换成md5  32位16进制字符串
                MD5Utils md5Utils = new MD5Utils();
                String md5 = md5Utils.encode(strSignature);
                VirusDao virusDao = new VirusDao();
                boolean isVirus = virusDao.isVirus(AnitVirusActivity.this, md5);
                VirusInfo info = new VirusInfo(packageName, name, drawable, isVirus);
                //宣布一个当前进度,宣布的进度需要体现在页面上
                publishProgress(info);
            }

##  1.33.	扫描病毒实现

###  1.33.1.	需求

实现扫描病毒操作。

###  1.33.2.	拷贝数据库

我们在扫描病毒的过程其实是从病毒数据库中查询验证的过程，因为需要使用到储存病毒特征的数据库，所以这里需要在应用启动的时候先将数据库拷贝到本地。

        private void copyVirusDB() {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //1.需要将assets目录下的address.db拷贝到哪去,拷贝到files文件夹中
            File file = new File(getFilesDir(), "antivirus.db");
            //2.读取文件的输入流
            inputStream = getAssets().open("antivirus.db");
            //3.创建输出流
            fileOutputStream = new FileOutputStream(file);
            //4.从inputStream读取内容,直到读完为止
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null && fileOutputStream != null) {
                try {
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

###  1.33.3. 查询数据库

1.通过对软件的特征码（可以理解为身份标示）进行MD5加密，然后查询验证是否是病毒。

                    //获取每一个用用信息中存储的签名文件
                Signature[] signatures = packageInfo.signatures;
                //获取数组位置0的对象,就是目前扫描到的应用的签名文件对象
                Signature signature = signatures[0];
                //签名文件转换成字符串
                String strSignature = signature.toCharsString();
                //字符串转换成md5  32位16进制字符串
                MD5Utils md5Utils = new MD5Utils();
                String md5 = md5Utils.encode(strSignature);
                VirusDao virusDao = new VirusDao();
                boolean isVirus = virusDao.isVirus(AnitVirusActivity.this, md5);

2.将应用的特征码通过MD5加密之后传入数据库中匹配是否存在，存在的话就说明这个程序是病毒，不存在的话就不是病毒。

        public boolean isVirus(Context ctx, String md5) {
	        //1.获取病毒数据库在手机上的路径
	        String path = ctx.getFilesDir().getAbsolutePath() + File.separator + "antivirus.db";
	        //2.开启数据库
	        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
	        //3.拿MD5在db指向的数据库表中进行查询
	        String sql = "select count(*) from datable where md5 = ?;";
	        Cursor cursor = db.rawQuery(sql, new String[]{md5});
	        int count = 0;
	        if (cursor.moveToNext()) {
	            count = cursor.getInt(0);
	        }
	        cursor.close();
	        db.close();
	        return count > 0;
	    }

##  1.34.	扫描完成效果实现

###  1.34.1.	需求

实现扫描完成效果。


![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/44.png)

2.遍历循环检查所有通过MD5加密后的程序的特征码，在扫描过程中设置扫描的进度和正在扫描的程序包名。

                if (isStop) {
                return;
            }
            currentPosition++;
            //此方法会在publishProgress方法调时调用
            //每循环一次,调用一次publishProgress每调一次publishProgress方法,则会触发onProgressUpdate方法
            //values即opublishProgress(info)方法中传递过来的参数
            VirusInfo bean = values[0];
            if (bean.isVirus) {
                //病毒
                //计算机病毒一共几个
                virusCount++;
                //要将病毒的应用,展示在ListView顶部()
                //每扫描一个应用,则需要将扫描完的应用添加在扫描应用的集合中
                appInfoList.add(0, bean);
            } else {
                //安全
                appInfoList.add(bean);
            }
            //获取bean中扫描完的应用的包名
            String packageName = bean.packageName;
            tvPackagename.setText(packageName);
            //数据适配器更新
            myAdapter.notifyDataSetChanged();
            //如果listview列表显示不全,则向上滚动至最后一个条目出现
            lvAppVirus.smoothScrollToPosition(appInfoList.size() - 1);
            //更新进度条(当前进度,总进度)
            arcProgress.setProgress(currentPosition * 100 / maxSize);

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/44.png)

3.对某个程序进行扫描之后，如果扫描发现当前程序是病毒，则需要将AntivirusInfo中isAntivirus的标示置为true并保存在集合的第一个位置。最后通过适配器实时刷新当前的列表。

        class MyAsyncTask extends AsyncTask<String, VirusInfo, Integer> {
        private int maxSize = 0;
        private int currentPosition = 0;
        private int virusCount = 0;

        @Override
        protected void onPreExecute() {
            //先于doInBackground执行的,代码运行在主线程中
            //重新处理控件的显示和隐藏
            rlScan.setVisibility(View.VISIBLE);
            llImage.setVisibility(View.GONE);
            rlScanAgain.setVisibility(View.GONE);
            myAdapter = new ScanAdapter(AnitVirusActivity.this, appInfoList);
            lvAppVirus.setAdapter(myAdapter);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            //在子线程中所作的操作
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PackageManager pm = getPackageManager();
            List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
            //获取应用的签名文件 --->签名文件字符串--->md5码(32位16进制字符串
            //扫描应用总数获取
            maxSize = installedPackages.size();
            for (int i = 0; i < installedPackages.size(); i++) {
                if (isStop) {
                    break;
                }
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取每一个安装在手机上的应用的信息
                PackageInfo packageInfo = installedPackages.get(i);
                String packageName = packageInfo.packageName;
                Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
                String name = packageInfo.applicationInfo.loadLabel(pm).toString();
                //获取每一个用用信息中存储的签名文件
                Signature[] signatures = packageInfo.signatures;
                //获取数组位置0的对象,就是目前扫描到的应用的签名文件对象
                Signature signature = signatures[0];
                //签名文件转换成字符串
                String strSignature = signature.toCharsString();
                //字符串转换成md5  32位16进制字符串
                MD5Utils md5Utils = new MD5Utils();
                String md5 = md5Utils.encode(strSignature);
                VirusDao virusDao = new VirusDao();
                boolean isVirus = virusDao.isVirus(AnitVirusActivity.this, md5);
                VirusInfo info = new VirusInfo(packageName, name, drawable, isVirus);
                //宣布一个当前进度,宣布的进度需要体现在页面上
                publishProgress(info);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(VirusInfo... values) {
            if (isStop) {
                return;
            }
            currentPosition++;
            //此方法会在publishProgress方法调时调用
            //每循环一次,调用一次publishProgress每调一次publishProgress方法,则会触发onProgressUpdate方法
            //values即opublishProgress(info)方法中传递过来的参数
            VirusInfo bean = values[0];
            if (bean.isVirus) {
                //病毒
                //计算机病毒一共几个
                virusCount++;
                //要将病毒的应用,展示在ListView顶部()
                //每扫描一个应用,则需要将扫描完的应用添加在扫描应用的集合中
                appInfoList.add(0, bean);
            } else {
                //安全
                appInfoList.add(bean);
            }
            //获取bean中扫描完的应用的包名
            String packageName = bean.packageName;
            tvPackagename.setText(packageName);
            //数据适配器更新
            myAdapter.notifyDataSetChanged();
            //如果listview列表显示不全,则向上滚动至最后一个条目出现
            lvAppVirus.smoothScrollToPosition(appInfoList.size() - 1);
            //更新进度条(当前进度,总进度)
            arcProgress.setProgress(currentPosition * 100 / maxSize);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (isStop) {
                return;
            }
            //在扫描完成后,考虑图片的获取分割
            //1.取图片,带进度条和应用程序报名的图片,在rlScan这个相对布局上展示
            //设置rlScan显示的图片可用,可以进行切割等操作
            rlScan.setDrawingCacheEnabled(true);
            //图片质量
            rlScan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            //截取整张图片,位图
            Bitmap drawingCache = rlScan.getDrawingCache();
            //2.截取drawingCache左侧图片和drawingCache右侧图片
            Bitmap leftBitmap = getLeftBitmap(drawingCache);
            Bitmap rightBitmap = getRightBitmap(drawingCache);
            //3将上述的2张图片设置给左右图片的控件
            ivLeft.setImageBitmap(leftBitmap);
            ivRight.setImageBitmap(rightBitmap);
            //容器显示和隐藏
            //因为扫描结束,所以进度条容器需要隐藏
            rlScan.setVisibility(View.GONE);
            //因为扫描结束,需要执行开门动画,左右2个图片所在的容器需要显示
            llImage.setVisibility(View.VISIBLE);
            //因为扫描结束,需要显示扫描的最终结果,并且提供一个重新扫描按钮,则重新扫描容器需要显示
            rlScanAgain.setVisibility(View.VISIBLE);
            if (virusCount > 0) {
                tvSafe.setText("您的手机很不安全");
            } else {
                tvSafe.setText("您的手机很安全");
            }
            //4.指定动画,让图片进行移动
            openAnimation();
            super.onPostExecute(integer);
        }
    }

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/44.png)


4.当扫描结束之后需要对界面顶部和列表进行显示效果更新（动画效果在4.3,4.4中进行实现），如果有病毒则会在顶部显示有病毒，在列表的最顶部位置会依次显示是病毒的程序。

                if (virusCount > 0) {
                tvSafe.setText("您的手机很不安全");
            } else {
                tvSafe.setText("您的手机很安全");
            }

###  1.34.2.	扫描完成的动画

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/5.png)

当扫描完成之后，需要实现如上图的一个动画。将在扫描过程完成时的图片平分成两半用属性动画分别向两边平移并且伴随透明度的变化，同时扫描结果的界面也要从透明到不透明进行显示。

        private void openAnimation() {
        //左侧图片的平移
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(ivLeft, "translationX", 0, -width);
        //左侧图片透明度变化
        ObjectAnimator obj2 = ObjectAnimator.ofFloat(ivLeft, "alpha", 1.0f, 0.0f);
        //右侧图片平移(0的含义是,图片控件在开始位置,而width则是从开始位置需要进行平移的距离(分+,-))
        ObjectAnimator obj3 = ObjectAnimator.ofFloat(ivRight, "translationX", 0, width);
        //右侧图片透明度变化
        ObjectAnimator obj4 = ObjectAnimator.ofFloat(ivRight, "alpha", 1.0f, 0.0f);
        //重新扫描容器透明度变化
        ObjectAnimator obj5 = ObjectAnimator.ofFloat(rlScanAgain, "alpha", 0.0f, 1.0f);
        //让5个动画同时执行
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(obj1, obj2, obj3, obj4, obj5);
        animatorSet.setDuration(2000);
        //设置控件不可用,则点击事件就无法触发
        btnScanAgain.setEnabled(false);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束以后,设置控件可用,则点击事件可以触发
                btnScanAgain.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

第42-48行，动画1表示的意思是将iv_antivirus_left从“left=0”移动到“left=-iv_antivirus_left.getWidth()”的位置；动画2表示的意思是将iv_antivirus_right从“right=0”移动到“right=iv_antivirus_right.getWidth()”的位置；动画3和动画4表示的意思是将iv_antivirus_left和iv_antivirus_right从透明变成不透明的动画；动画5是将扫描完成后的rel_antivirus_result界面从不透明变成透明的动画。

###  1.34.3.	重新扫描的动画

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/5.png)

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/45.png)

        private void closeAnimation() {
        //左侧图片的平移
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(ivLeft, "translationX", -width, 0);
        //左侧图片透明度变化
        ObjectAnimator obj2 = ObjectAnimator.ofFloat(ivLeft, "alpha", 0.0f, 1.0f);
        //右侧图片平移(0的含义是,图片控件在开始位置,而width则是从开始位置需要进行平移的距离(分+,-))
        ObjectAnimator obj3 = ObjectAnimator.ofFloat(ivRight, "translationX", width, 0);
        //右侧图片透明度变化
        ObjectAnimator obj4 = ObjectAnimator.ofFloat(ivRight, "alpha", 0.0f, 1.0f);
        //重新扫描容器透明度变化
        ObjectAnimator obj5 = ObjectAnimator.ofFloat(rlScanAgain, "alpha", 1.0f, 0.0f);
        //让5个动画同时执行
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(obj1, obj2, obj3, obj4, obj5);
        animatorSet.setDuration(2000);
        //设置控件不可用,则点击事件就无法触发
        btnScanAgain.setEnabled(false);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束以后,设置控件可用,则点击事件可以触发
                btnScanAgain.setEnabled(true);
                //重新扫描
                startAsyncTask();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

第10-16行，动画1表示的意思是将iv_antivirus_left从“left=-iv_antivirus_left.getWidth()”移动到“left=0”的位置；动画2表示的意思是将iv_antivirus_right从“right=iv_antivirus_right.getWidth()”移动到“right=0”的位置；动画3和动画4表示的意思是将iv_antivirus_left和iv_antivirus_right这两张图片从不透明变成到透明的动画；动画5是将扫描完成后的rel_antivirus_result界面从透明变成不透明的动画。

##  1.35.	快捷图标创建

###  1.35.1.	需求

在手机桌面创建应用程序快捷图标。

###  1.35.2.	实现

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/46.png)

在应用启动的时候创建快捷图标，其实就是给桌面发送一个广播事件。

        private void create() {
	        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	        //快捷方式的名称
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "手机卫士快捷方式");
	        //不允许重复创建
	        shortcut.putExtra("duplicate", true);
	        //指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
	        //注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
	        ComponentName comp = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
	        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
	        //传递参数：点击快捷方式跳转到指定Activity中，通过拿到指定参数值进行下一步操作
	        shortcutIntent.putExtra("快捷方式参数", "快捷方式参数值");
	        shortcutIntent.setComponent(comp);
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
	        //快捷方式的图标
	        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.mipmap.icon);
	        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
	        this.sendBroadcast(shortcut);
	    }

##  1.36.	混淆	

关于proguard-project.txt文件中配置详解，可以参考下面这个帖子：http://blog.csdn.net/hudashi/article/details/9016805

###  1.36.1.	需求

对整个应用程序进行混淆操作

###  1.36.2.	配置

修改工程配置文件如下。


![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/47.png)

###  1.36.3.	添加混淆文件相关代码

在工程proguard-rules.pro文件中添加如下代码

##  1.37	创建前台进程

前台进程状态栏效果图：

![](https://raw.githubusercontent.com/nangongyibin/Android_MobiCop/master/picture/48.png)

###  1.37.1.	需求

实现前台进程的创建。

###  1.37.2.	功能实现

我们知道前台进程的优先级是最大的，为了防止手机卫士的进程被杀死，所以通过创建Notification的方式将手机卫士设置为前台进程。


关于进程优先级的解释：

| 空进程	 |  这是Android系统优先杀死的，因为此时该进程已经没有任何用途 | 
| 后台进程	|  包含不可见的Activity，即跳转到其他activity后，由于资源不足，系统会将原来的activity杀死（即跳转的来源）| 
| 服务进程	|  即Service，当系统资源不足时，系统可能会杀掉正在执行任务的Service。因此在Service执行比较耗时的操作，并不能保证一定能执行完毕 | 
| 可见进程	|  当前屏幕上可以看到的activity，例如显示一个对话框的activity，那么对话框变成了前台进程，而调用他的activity是可见进程，但并不是前台的
前台进程	当前处于最前端的activity，也就是Android最后考虑杀死的对象。一般来说，前台进程Android系统是不会杀死的，只有当前4个都杀掉资源依旧不够才可能会发生 | 


    public class ProtectService extends Service {
	    @Nullable
	    @Override
	    public IBinder onBind(Intent intent) {
	        return null;
	    }
	
	    @Override
	    public void onCreate() {
	        //通知栏
	        Notification notification = new Notification();
	        //通知栏没有下拉时候显示的图标
	        notification.icon = R.mipmap.icon;
	        //将布局文件转换成view对象,然后将view对象赋值给notification
	        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
	        //将布局文件转换成的远程view对象赋值给notification
	        notification.contentView = remoteViews;
	        //一旦点击了notification需要做什么事情开启应用程序
	        Intent intent = new Intent(this, SplashActivity.class);
	        intent.setAction("android.intent.action.MAIN");
	        intent.addCategory("android.intent.category.LAUNCHER");
	        ComponentName componentName = new ComponentName(this, SplashActivity.class);
	        intent.setComponent(componentName);
	        notification.contentIntent = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	        //让notification在后台运行
	        startForeground(1000, notification);
	        super.onCreate();
	    }
	
	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	    }
	}

第17行，给要跳转activity设置一个任务栈，在服务中跳转到一个activity的是会用，因为服务是不在任务栈里的。

##  1.38.	异常捕获

###  1.38.1.	需求

实现异常捕获。

###  1.38.2.	实现

程序写完之后，剩下的我们就需要去维护这个项目，那为了方便我们的维护，一般我们都需要去收集一下异常，比如程序崩溃了，我们呢可以把异常收集起来，方便我们去查找。

1．创建Application。

    public class MyApplication extends Application {


2．清单文件配置。

        <application
	        android:name=".global.MyApplication"
	        android:allowBackup="true"
	        android:hardwareAccelerated="false"
	        android:icon="@mipmap/icon"
	        android:label="@string/app_name"
	        android:roundIcon="@mipmap/icon"
	        android:supportsRtl="true"
	        android:theme="@style/AppThemeNo">

3.获取程序的异常。


        @Override
    public void onCreate() {
        super.onCreate();
        AdManager.getInstance(this).init("187d05c27f6bb3ed", "187d05c27f6bb3ed", true);
        //初始化操作
        //捕获全局的异常
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                //为捕获的异常,打印异常的具体日志
                e.printStackTrace();
                try {
                    //将异常信息存储文件中,然后将文件发送给服务器工程师
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "error7219.log";
                    File file = new File(path);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    Log.e(TAG, "uncaughtException: " + e.getLocalizedMessage().toString());
                    PrintWriter printWriter = new PrintWriter(file);
                    //将e中的异常日志通过printWriter写入file文件中
                    e.printStackTrace(printWriter);
                    printWriter.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                //让应用程序自杀
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }



