import 'dart:async';

import 'package:flutter/services.dart';

class IsAppInstalled {
  static const MethodChannel _channel = const MethodChannel('is_app_installed');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> isAppInstalled(String packageNameOrScheme) async {
    final bool installed = await _channel.invokeMethod('isAppInstalled', packageNameOrScheme);
    return installed;
  }

  static Future<bool> jumpTo(String schemeUrl) async {
    final bool installed = await _channel.invokeMethod('jumToUrl', schemeUrl);
    return installed;
  }
}
