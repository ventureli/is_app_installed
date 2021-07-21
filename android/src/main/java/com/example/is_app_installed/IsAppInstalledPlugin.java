package com.example.is_app_installed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** IsAppInstalledPlugin */
public class IsAppInstalledPlugin implements FlutterPlugin, ActivityAware, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  public static Context context;
  public static Activity  activity;




  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    context = flutterPluginBinding.getApplicationContext();

    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "is_app_installed");
    channel.setMethodCallHandler(this);
  }


  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }else if(call.method.equals("isAppInstalled"))
    {
        Boolean installed = this.checkAppInstalled(context,call.arguments.toString());
        result.success(installed);
    }else if(call.method.equals("jumToUrl"))
    {
      try {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(call.arguments.toString()));
        activity.startActivity(intent);
        result.success(true);
      }catch (Exception e){
        result.success(false);
      }

    }else{
      result.notImplemented();
    }
  }

  private boolean checkAppInstalled(Context context, String pkgName) {
    if (pkgName == null || pkgName.isEmpty()) {
      return false;
    }
    final PackageManager packageManager = context.getPackageManager();
    List<PackageInfo> info = packageManager.getInstalledPackages(0);
    if (info == null || info.isEmpty())
      return false;
    for (int i = 0; i < info.size(); i++) {
      if (pkgName.equals(info.get(i).packageName)) {
        return true;
      }
    }
    return false;
  }
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull  ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull  ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }
}
