2019년 11월 25-2일 작업내용

 facebook ID 연동

 facebook for developers 사이트 이동해서
 앱만들기 ID 생성후 facebook 로그인 설정으로 이동 다음 안드로이드 os 선택후
 첫번째 build.gradle 파일 열어서
   buildscript {
       ext.kotlin_version = '1.3.60'
       repositories {
           google()
           jcenter()
      ==>     mavenCentral() 입력후

  두번째 gradle 파일 열어서 dependencies 에 다음 문장을 추가ㅓ
     implementation 'com.facebook.android:facebook-android-sdk:[4,5)'
  
  Hash key 생성
    -구글에서 How to create facebook Hash key 입력하면 stackoverflow.com 사이트로 이동하고 다음 코드를 복사 LoginActivity.kt 에 onCreat() 함수 다음에 넣어 줌
    
    printHashKey(this)  // 이함수는  googleSignInClient = GoogleSignIn.getClient(this,gso) 다음 줄에 입력 
    
    public static void printHashKey(Context pContext) {
            try {
                PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String hashKey = new String(Base64.encode(md.digest(), 0));
                    Log.i("Howls", "printHashKey() Hash Key: " + hashKey);
                }
            } catch (NoSuchAlgorithmException e) {
                Log.e("Howls", "printHashKey()", e);
            } catch (Exception e) {
                Log.e(TAG, "printHashKey()", e);   // TAG -> "Howls" 로 바꿔 줌
            }
        }
        
        위의 코드를 입력하면 kortlin으로 변환 물어보면 yes 빨간 줄 나오면 import해줌
        
        위의 코드 입력후 실행하면 logcat 으로 이동 value 에 Howls 입력하면 다음과 같은 Hash key 값을 얻을 수 있고 facebook site 4번째 항목인 Hash 값에 넣어 줌
        dgYsqQ5+OQZOIO4bLlE6aSgQ1dU=