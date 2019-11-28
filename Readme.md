2019년 11월 28-1일 작업내용

  1.activity_add_photo.xml 에 다음과 같은 코드를 입력
       <androidx.appcompat.widget.Toolbar
              android:layout_width="match_parent"
              android:layout_height="55dp"
              android:id="@+id/my_Toolbar">
      
              <ImageView
                  android:layout_width="match_parent"
                  android:layout_height="match_parent"
                  android:src="@drawable/logo">
      
              </ImageView>
      
          </androidx.appcompat.widget.Toolbar>
      
          <LinearLayout
              android:orientation="vertical"
              android:id="@+id/toolbar_division"
              android:layout_width="match_parent"
              android:layout_height="1dp"
              android:layout_below="@+id/my_Toolbar"
              android:background="@color/colorDivision">
      
          </LinearLayout>
      
          <ImageView
              android:layout_margin="8dp"
              android:layout_below="@+id/toolbar_division"
              android:layout_width="100dp"
              android:layout_height="100dp"
              android:id="@+id/addPhoto_Image">
      
          </ImageView>
      
          <com.google.android.material.textfield.TextInputLayout
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:layout_below="@+id/toolbar_division"
              android:layout_toRightOf="@+id/addPhoto_Image"
              android:id="@+id/editText">
      
              <EditText
                  android:layout_width="100dp"
                  android:layout_height="100dp"
                  android:id="@+id/addPhotoEdit_explain"
                  android:hint="@string/hint_image_content"
                  android:gravity="top">
              </EditText>
          </com.google.android.material.textfield.TextInputLayout>
      
      
          <Button
              android:text ="@string/upload_image"
              android:layout_below="@+id/editText"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:id="@+id/addphoto_btn_upload"
              android:layout_margin="8dp"
              android:layout_toRightOf="@+id/addPhoto_Image"
              android:theme="@style/ButtonStyle"></Button>
     *** Layout 은 Relative_Layout 으로 변경
     
  2. Add_photo_Activity 추가
      먼저 사진을 불러올수 있는 권한을 MainActivity 에 넣어줌
      override fun onCreate(savedInstanceState: Bundle?) {
              super.onCreate(savedInstanceState)
              setContentView(R.layout.activity_main)
      ===>    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
      그리고 Manifest 파일에 6번째 줄에
         <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
      넣어줌
             
  3 사진올리기
     Add_Photo_Activity 에 다음 코드를 넣어주고
     class Add_Photo_Activity : AppCompatActivity() {
         val PICK_IMAGE_FROM_ALBUM = 0
         override fun onCreate(savedInstanceState: Bundle?) {
             super.onCreate(savedInstanceState)
             setContentView(R.layout.activity_add__photo_)
             var photoPickerIntent = Intent(Intent.ACTION_PICK)
             photoPickerIntent.type ="image/*"
             startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
     
         }
     }
     입력후 MainActivity 에 이벤트를 넣어줌
     R.id.action_add_photo -> {
                 -->    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                 -->        startActivity(Intent(this,Add_Photo_Activity::class.java))
                     
                     return true
                 }
  4 담기는 사진을 관리
     Add_Photo_Activity.kt 이동
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
             super.onActivityResult(requestCode, resultCode, data)
             if (requestCode==PICK_IMAGE_FROM_ALBUM){
                 addPhoto_Image.setImageURI(data?.data)
                 if(resultCode==Activity.RESULT_OK){
                     addPhoto_Image.setImageURI(data?.data)  //사진 선택시 다른 사진 선택
     
                 }
                 else{
                   finish()  // 뒤로가기 버튼 클릭시 activity_add_photo.xml 을 닫아줌
                 }
             }    
         }
         
     - 코드입력후 
     override fun onCreate(savedInstanceState: Bundle?) {
             super.onCreate(savedInstanceState)
             setContentView(R.layout.activity_add__photo_)
             var photoPickerIntent = Intent(Intent.ACTION_PICK)
             photoPickerIntent.type ="image/*"
             startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
       --->  addPhoto_Image.setOnClickListener {
       --->      var photoPickerIntent = Intent(Intent.ACTION_PICK)
       --->      photoPickerIntent.type ="image/*"
       --->      startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
       --->  }
       입력한다.
  5 firebase에 사진 업로드하기
     - 파이어베이스의 Storage 선택후 보안==> 완료후  (인증된 사람만이 업로드하기 위한 코드 자동작성후)
        Add_Photo_Activity.kt에 다음 코드 작성후 Android Studio 메뉴바 -> Tools 중에 firebase 실행후 Assistant 중
        Storage 선택 1단계(connect) 2단계(Add Cloud Storage to your app)선택 진행
          -build.gradel(Module:app) 에 dependencis 작업
                   implementation 'com.google.firebase:firebase-auth:16.0.5'
                   implementation 'com.google.firebase:firebase-storage:16.0.5'
                   버전을 같게 맞춤 
     
     Add_Photo_Activity.kt 이동              
        class Add_Photo_Activity : AddCompaActivity(){ 안에 
           var storage :FirebaseStorage? = null  <--변수 선언해 주고
        onCreate()함수내에
           storage = FirebaseStorage.getInstance() <<-- 넣어줌   
     
         
     전역변수 : photoURI 선언 // phto data가 저장될 변수
        var photoURI :Uri? = null
         override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode==PICK_IMAGE_FROM_ALBUM){
                    addPhoto_Image.setImageURI(data?.data)
                    if(resultCode==Activity.RESULT_OK){
                        
                 --->>  photoURI = data?.data
                        addPhoto_Image.setImageURI(data?.data)
                        
           위의 photoURI를 contentUpload() 함수에서 사용
           
     fun contentUpload(){
                  val timeStamp = SimpleDateFormat("yyyymmdd_HHMMSS").format(Date())
                  val ImageFileName = "JPEG_"+timeStamp+"_png"
                  val storageRef = storage?.reference?.child("images")?.child(ImageFileName)
                  
                  //photoURI에 빨간중이 가면 !! 를 넣어줌
                  storageRef?.putFile(photoURI!!)?.addOnSuccessListener {
                              Toast.makeText(this,getString(R.string.upload_success),Toast.LENGTH_LONG).show()
                          
              }  
     
     - "사진올리기" 버튼에 event 추가 onCreate()함수에 다음 코드추가
            addphoto_btn_upload.setOnClickListener { 
                    contentUpload()
            }
        
                
     *** www.pixabay.com 에 무료 사진이 많음
     