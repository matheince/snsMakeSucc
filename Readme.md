2019년 11월 28-2일 작업내용

  1.DDO 제작
    Package 만들기
      루트에 "Model" 패키지 만들기
      그안에 New 메뉴로 kotlin FileClass 만들기
      ContentDTO 안에
          package com.e.howlssns.model
          
          import java.sql.Timestamp
          import java.util.HashMap
          
          data class ContentDTO(
                      var explain:String? = null,
                      var imageUrl : String? = null,
                      var uid : String? = null,
                      var userId : String? =null,
                      var timestamp: Long?=null,
                      var favoriteCount : Int = 0,
                      var favorites : Map<String,Boolean> = HashMap()
                      ) {
              data class Comment(
                  var uid: String? = null,
                  var userID: String? = null,
                  var comment: String? = null,
                  var timestamp: Long? = null
              )
          } 
      위의 코드입력
   2 데이터베이스에 사진정보를 입력
     Add_Photo_Activity 로 이동
        storageRef 안에 다음 코드 추가
           storageRef?.putFile(photoURI!!)?.addOnSuccessListener { taskSnapshot ->
                       Toast.makeText(this,getString(R.string.upload_success),Toast.LENGTH_LONG).show()
                       var url = taskSnapshot.downloadUrl 
           위코드 넣을 때 주의사항 ==>> addOnSuccessListener 리스너 넣을 때 ctrl+Space 바로 'taskSnapshot ->' 넣고,
                        var url = taskSnapshot.downloadUrl 의 downloadUrl 이 빨간색 나올 때 alt+Enter 키로 import 해야 됨
                          
   3 Firebase에 저장 Add_Photo_Activity.kt 에 다음 코드 작성및 Firebase의 Firestore 지정
   
     var uri = taskSnapshot.downloadUrl
                 var contentDTO = ContentDTO()
                 //val db = FirebaseFirestore.getInstance()
                 //이미지 주소
                 contentDTO.imageUrl = uri!!.toString()
                 // 유저의 UID
                 contentDTO.uid = auth?.currentUser?.uid
                 // 게시물 설명
                 contentDTO.explain = addPhotoEdit_explain.text.toString()
                 // 유저 ID
                 contentDTO.userId = auth?.currentUser?.email
                 // 게시물 업로드 시간
                 contentDTO.timestamp = System.currentTimeMillis()
                 // 저장시 firestore 설정 요망
                 // firebase 창 띄워 Firestore 지정 --> 자동 저장됨
                // val db = FirebaseFirestore.getInstance()
                // 다음 문장을 쓰기위해 onCreate() 위에  변수 지정
                // var firestore : FirebaseFirestore? = null 지정
                 firestore?.collection("images")?.document()?.set(contentDTO)
                 
     
                 setResult(Activity.RESULT_OK)
                 finish()
      
     ** class Add_Photo_Activity : AppCompatActivity() 안에 다음 두줄 선언함.
        var auth : FirebaseAuth? =null
        var firestore : FirebaseFirestore? = null
      
      그 다음으로 Firebase 권한을 지정해야 함.
      Firebase 의 홈페이지에서 database로 이동
      database 만들기로 이동 --> 규칙 탭 이동 
          service cloud.firestore {
            match /databases/{database}/documents {
              match /{document=**} {
   -->편집     allow read, write: if request.auth.uid != null;
              }
            }
          }
          
       '게시'버튼으로 게시함.
                 
     *** www.pixabay.com 에 무료 사진이 많음
     