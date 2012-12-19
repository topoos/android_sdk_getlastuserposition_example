android_sdk_getlastuserposition_example
=======================================

Example: how to use topoos Android SDK for get the last user registered position and displays it on a map. Read more in http://docs.topoos.com

## Building

### Eclipse
 * This project has to be build with Google APIs API Level 8 or higher!

### topoos CLIENT ID for Access
 * You must write your own app APPTOKEN_USER or other valid Access Token for get it working (Replace XXXXXXXXXXX)
  
```java
public class MainActivity extends MapActivity {

  private static final String APPTOKEN_USER = "XXXXXXXXXXXXXXXXXXX";
```

### Libs
 * You must include topoos SDK in your project libs folder
 * [`topoos Android SDK`][URI_TOPOOS_ANDROID_SDK]

### Google Maps API key (if you don't get your own API Key, the map will be display as grey background)

 * [`Get a Google Maps Api key`][URI_GOOGLEMAPSKEY] (You must get a Google Maps Api key before use this project)
 * [`Publishing an app with Google Maps key`][URI_GOOGLEMAPSPUBLISH]
You must get a Google Maps Api key before use this code code: 
 *     https://developers.google.com/maps/documentation/android/mapkey
 * About publishing app with Google Map
 *		http://developer.android.com/intl/es/tools/publishing/app-signing.html

## Related documentation

 * [`Register your own topoos app`][URI_TOPOOS_REGISTER_APP]
 * [`topoos Android SDK`][URI_TOPOOS_ANDROID_SDK]
 * [`topoos API operation reference: Add Position`][URI_TOPOOS_APIREF_ADDPOS]
 * [`topoos API operation reference: GetLast position`][URI_TOPOOS_APIREF_GETPOS] (Using method 2)

[URI_GOOGLEMAPSKEY]: https://developers.google.com/maps/documentation/android/mapkey
[URI_GOOGLEMAPSPUBLISH]: http://developer.android.com/intl/es/tools/publishing/app-signing.html
[URI_TOPOOS_REGISTER_APP]: http://docs.topoos.com/api-guides/registering-your-app/
[URI_TOPOOS_ANDROID_SDK]: http://docs.topoos.com/tools/sdks/android/
[URI_TOPOOS_APIREF_ADDPOS]: http://docs.topoos.com/reference/positions/add/
[URI_TOPOOS_APIREF_GETPOS]: http://docs.topoos.com/reference/positions/get/
