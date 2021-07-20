import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:is_app_installed/is_app_installed.dart';

void main() {
  const MethodChannel channel = MethodChannel('is_app_installed');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await IsAppInstalled.platformVersion, '42');
  });
}
