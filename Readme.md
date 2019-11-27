2019년 11월 27-2일 작업내용

  1. res dir에 menu dir 생성 후 
     <?xml version="1.0" encoding="utf-8"?>
     <menu xmlns:android="http://schemas.android.com/apk/res/android">
         <item
             android:id="@+id/action_home"
             android:enabled="true"
             android:icon="@drawable/ic_home"
             android:title="@string/home">
         </item>
     
     
         <item
             android:id="@+id/action_search"
             android:enabled="true"
             android:icon="@drawable/ic_search"
             android:title="@string/search">
         </item>
         <item
             android:id="@+id/action_add_photo"
             android:enabled="true"
             android:icon="@drawable/ic_add_a_photo"
             android:title="@string/gallery">
         </item>
         <item
             android:id="@+id/action_favorite_alarm"
             android:enabled="true"
             android:icon="@drawable/ic_favorite_border"
             android:title="@string/favorite">
         </item>
         <item
             android:id="@+id/action_account"
             android:enabled="true"
             android:icon="@drawable/ic_account"
             android:title="@string/account">
         </item>
     </menu>
     
     코딩합니다.
     
  2. 그후 MainActivity.kt 이동 Implement Members 로 BottomNavigationView.OnNavigationItemSelectedListener 작성함
     class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
         override fun onNavigationItemSelected(p0: MenuItem): Boolean {
             TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         }
  3. activity_main.xml 에 다음과 같은 코드 생성후    
     <com.google.android.material.bottomnavigation.BottomNavigationView
             android:id="@+id/bottom_navigation"            
             android:layout_width="match_parent"
             android:layout_height="wrap_content"
             android:layout_alignParentBottom="true"
             app:menu="@menu/bottom_navigation_main">
     
     </com.google.android.material.bottomnavigation.BottomNavigationView>
     
 4. MainActivity.kt 이동후
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
       -->  bottom_navigation 입력하면 
            import kotlinx.android.synthetic.main.activity_main.* 이 생긴다.
            bottom_navigation.setOnNavigationItemSelectedListener(this) 여기서 this 의미하는 것은 43번째 줄의    
               class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener 것을 가리키고 곧 44,45번째
               줄을 실행함.
               
 5  
 class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
     override fun onNavigationItemSelected(p0: MenuItem): Boolean {
         when(p0.itemId){
             R.id.action_home -> {
                 println("1")
                 return true
             }
             R.id.action_search -> {
                 println("2")
                 return true
             }
             R.id.action_add_photo -> {
                 println("3")
                 return true
             }
             R.id.action_favorite_alarm -> {
                 println("4")
                 return true
             }
             R.id.action_account -> {
                 println("5")
                 return true
             }
 
         }
         return false
 
     }
     실행창에 1,2,3,4,5 가 정상적으로 찍힘
     
     
 6 자동 로그인 LoginActivity.kt 에 다음 함수 넣는다
    override fun onResume() {
            super.onResume()
            moveMainPage(auth?.currentUser)
        }
     
 7 Fragment 넣기
   Activity 작성하듯
      New -> Kotlin Files -> AlertFragment 작성
                             DetailviewFragment 작성
                             GridFragment 작성
                             UserFragment 작성
      Layout 에서 위 Fragment 에 쓸 xml 작성 (res -> layout -> resource file)
                             fragment_alarm.xml
                             fragment_detail.xml
                             fragment_grid.xml
                             fragment_user.xml
                             
                             
 8 각 Fragment에 다음 코드를 넣는다 (참고 : Android Studio 3.5.2는 app 수준의 fragment 는 제공하지 않음)
     
     class DetailviewFragment :Fragment(){
         override fun onCreateView(
             inflater: LayoutInflater,
             container: ViewGroup?,
             savedInstanceState: Bundle?
         ): View? {
             return LayoutInflater.from(inflater.context).inflate(R.layout.fragment_detail,container,false)
     
         }
     
     }   
     
     class AlertFragment :Fragment(){
         override fun onCreateView(
             inflater: LayoutInflater,
             container: ViewGroup?,
             savedInstanceState: Bundle?
         ): View? {
             return LayoutInflater.from(inflater.context).inflate(R.layout.fragment_alarm,container,false)
         }
     }
                   
                                               
     class GridFragment :Fragment(){
         override fun onCreateView(
             inflater: LayoutInflater,
             container: ViewGroup?,
             savedInstanceState: Bundle?
         ): View? {
             return LayoutInflater.from(inflater.context).inflate(R.layout.fragment_grid,container,false)
     
         }
     
     }    
     
     class UserFragment :Fragment(){
         override fun onCreateView(
             inflater: LayoutInflater,
             container: ViewGroup?,
             savedInstanceState: Bundle?
         ): View? {
             return LayoutInflater.from(inflater.context).inflate(R.layout.fragment_user,container,false)
     
         }
     }

    위 코드를 모두 작성후 MainActivity 에 
    when(p0.itemId){
                R.id.action_home -> {
      각 리소스는     var detailviewFragment = DetailviewFragment() 을 추가
                    return true
                }
                ....
                            
9 transaction 처리를 위해 각 리소스에 var detailviewFragment = DetailviewFragment() 다음에 다음 코드 작성
    supportFragmentManager.beginTransaction().replace(R.id.main_content,detailviewFragment).commit() 여기 commit()굉장히 중요함.
    넣고 activity_main.xml 에 화면을 넣어줄 layout 작성 toolbar 문 뒤에
        <FrameLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:id="@+id/main_content">
        </FrameLayout>
    
10 화면이 전환되었는지 확인 위해 각 fragment.xml 파일에 다음 코드 작성
   android:background="@color/colorPrimary" 색을 바꿔가며 넣어줌
    
   
***** 실행이 안되었당 정말 챙피하게도 activity_main.xml 의 FrameLayout 에 
<FrameLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_parent"  <- 이렇게 되서 
        android:id="@+id/main_content">

    </FrameLayout>   
    android:layout_height="match_parent" 로 바꿔주고 정상적으로 작동됨
    
         
 ** google login Error
    --> D/FirebaseApp: Notified 0 auth state listeners. 로그창 메세지
    