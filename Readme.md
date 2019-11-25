2019년 11월 25-1일 작업내용
 implementation 'com.google.android.gms:play-services-auth:17.0.0' 을 통한 Google 인증 시스템에 라이브러리 등록

 LoginActivity.kt 안에 onCreate() 함수내에 다음 코드를 넣어줌
    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

 정말 어처구니 없게 var accout 선언후 firebaseAuthWithGoogle(account!!) 써야 하는데
               override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                    super.onActivityResult(requestCode, resultCode, data)
                    if(requestCode==GOOGLE_LOGIN_CODE) {
                        var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                        if(result.isSuccess){
                            var account = result.signInAccount
                            //firebaseAuthWithGoogle(account!! )
                            firebaseAuthWithGoogle(account!!)

                        }
                    }
                }
            }

결국 다음과 같이 성공함.
