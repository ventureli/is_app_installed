# is_app_installed

A new flutter plugin project.

## Getting Started


### iOS 
  Fix your info.plist like this
  
  ```
  <key>LSApplicationQueriesSchemes</key>
   <array>
      <!-- 微信 URL Scheme 白名单-->
      <string>wechat</string>
      <string>weixin</string>
   </array>
   
  ```
### example

```
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:is_app_installed/is_app_installed.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  bool _installed = false;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await IsAppInstalled.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    bool cannInstall = await IsAppInstalled.isAppInstalled("com.tencent.mm");
    this._installed = cannInstall;
    print(cannInstall);

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(children: [
            Text('Running on: $_platformVersion\n $_installed '),
            SizedBox(
              height: 10,
            ),
            GestureDetector(
              onTap: () async {
                await IsAppInstalled.jumpTo("http://www.baidu.com");
              },
              child: Container(
                color: Colors.red,
                height: 40,
                child: Center(
                  child: Text("跳转"),
                ),
              ),
            )
          ]),
        ),
      ),
    );
  }
}


```
