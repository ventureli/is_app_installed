#import "IsAppInstalledPlugin.h"

@implementation IsAppInstalledPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"is_app_installed"
            binaryMessenger:[registrar messenger]];
  IsAppInstalledPlugin* instance = [[IsAppInstalledPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([@"getPlatformVersion" isEqualToString:call.method]) {
        result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
    }else if([@"isAppInstalled" isEqualToString:call.method])
    {
        NSString *url = [call.arguments description];
        BOOL canOpen = [[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:url]];
        result(@(canOpen));

    }else if([@"jumToUrl" isEqualToString:call.method])
    {
        NSString *url = [call.arguments description];
        BOOL res = [[UIApplication sharedApplication] openURL:[NSURL URLWithString:url]];
        result(@(res));
    }else {
        result(FlutterMethodNotImplemented);
    }
}

@end
