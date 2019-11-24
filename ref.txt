2019년 11월 24일 작업내용
dependencies 세팅에 다음 줄을 추가해야
 implementation 'com.android.support:appcompat-v7:27.0.2'
 implementation 'com.android.support:design:27.0.2'

이 TextInputLayout을 사용할 수 있음
<com.google.android.material.textfield.TextInputLayout>

다음의 오류는 폰에서 앱을 지웠을 때 설정>일반>앱 및 알림> 중지된 앱을 강제 종료후 해결
$ adb shell am start -n "com.e.howlssns/com.e.howlssns.LoginActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

앱이름은 처음 실행될 Activity에서 Application 안에 설정
 android:label="mathienceInst"

Android Keyboard 자동시 화면 밀림 현상은 AndroidManifest.xml 로 이동 <Actitity 안에 android:windowSoftInputMode = "adjustNothing" 설정으로 해결 