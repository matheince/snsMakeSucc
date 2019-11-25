2019년 11월 25일 작업내용
 android:inputType="textPassword" 입력값이 ***** 처리됨
 파이버베이스에 사용설정및 LoginActivity 에 다음 함수및 activity_login.xml 에 아이디 지정만으로 생성됨
 fun createAndLoginEmail(){
         auth?.createUserWithEmailAndPassword(email_EditText.text.toString(),password_EditText.text.toString())?.addOnCompleteListener {
             task ->
             if(task.isSuccessful){
                 Toast.makeText(this,"아이디 생성 성공",Toast.LENGTH_LONG).show()
             }
         }

     }