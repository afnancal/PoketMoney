
# Android MVVM Architecture: Poket Money App

[![](https://jitpack.io/v/nekocode/Badge.svg)](https://jitpack.io/#nekocode/Badge) 
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=26)
[![Kotlin](https://camo.githubusercontent.com/628c2b47266595b7cef46260bbf46057a40147b8a3176b413dcd9b31605b72db/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6c616e67756167652d4b6f746c696e2d6f72616e67652e737667)](https://camo.githubusercontent.com/628c2b47266595b7cef46260bbf46057a40147b8a3176b413dcd9b31605b72db/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6c616e67756167652d4b6f746c696e2d6f72616e67652e737667)
[![](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://img.shields.io/badge/Android%20Arsenal-Retrofit-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/401)


This repository contains a detailed Poket Money app that implements MVVM architecture using Retrofit, Glide, android-gif-drawable, ViewPager2, RoundedImageView and Image Picker Library.





#### The app has following packages:
**data:** It contains all the data accessing and manipulating components.\
**di:** Dependency providing classes using Retrofit.\
**ui:** View classes along with their corresponding ViewModel.\
**utils:** Utility classes.

***Classes have been designed in such a way that it could be inherited and maximize the code reuse.***




#### #Converters:
In the past, Retrofit relied on the GSON library to serialize and deserialize JSON data. Retrofit 2 now supports many different parsers for processing network response data, including Moshi, a library build by Square for efficient JSON parsing.
| CONVERTER | LIBRARY |
| --------- | ------- |
| Gson | com.squareup.retrofit2:converter-gson:2.0.2 |
| Jackson | com.squareup.retrofit2:converter-jackson:2.0.2 |
| Moshi | com.squareup.retrofit2:converter-moshi:2.0.2 |

## Overview

Poket Money is an online income app that requires no investment and is one of the top work from home options in India. Join the Poket Money App if you are looking for a part-time or full-time career Online. Over 400k people have installed Poket Money App and filled their pockets with Money.
## Screenshots

![App Screenshot](https://github.com/afnancal/PoketMoney/blob/master/screenshots/1app.png)

![App Screenshot](https://github.com/afnancal/PoketMoney/blob/master/screenshots/2app.png)

![App Screenshot](https://github.com/afnancal/PoketMoney/blob/master/screenshots/3app.png)




## Library reference resources:

 * Retrofit2:  https://square.github.io/retrofit/
 * Gson: https://github.com/google/gson
 * ViewPager2: https://developer.android.com/jetpack/androidx/releases/viewpager2
 * RoundedImageView:  https://github.com/vinc3m1/RoundedImageView
 * android-gif-drawable:  https://github.com/koral--/android-gif-drawable
 * Image Picker Library:  https://github.com/Dhaval2404/ImagePicker
 * Glide:  https://github.com/bumptech/glide


## License

       Copyright (C) 2022 Mohammad Afnan Haseeb
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and limitations under the License.

