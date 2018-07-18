# ImagePhoto
全屏可以触摸操作图片轮播<br>
project下
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
app下
```java
	dependencies {
	        implementation 'com.github.Ayaseling:ImagePhoto:v1.0'
	}
 ```

使用时传入图片地址集合  List<String>  list<br>
position为多宫格图片显示时点击的位置
```java
 ImageDialog dialog = new ImageDialog(context,R.style.mydialogstyle,list,position);
```
