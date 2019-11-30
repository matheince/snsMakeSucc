2019년 11월 29-1일 작업내용

  1.res > layout > New 해서 item_detail.xml을 만들고 Layout 작업
  
  2. DetailviewFragment.kt 의 fragment_detail.xml 에 RecycleView를 갖게 작업
     
     <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
         android:orientation="vertical" android:layout_width="match_parent"
         android:layout_height="match_parent">
     
         <view class="androidx.appcompat.app.AlertController$RecycleListView"
             android:id="@+id/detailviewfragment_recyleview"
     
             android:layout_width="match_parent"
             android:layout_height="match_parent">
     
         </view>
     
     </LinearLayout>
     다음  detailviewfragment_recyleview을 DetailviewFragment.kt 에서 쓰도록 함
            ): View? {
                  var view = LayoutInflater.from(inflater.context).inflate(R.layout.fragment_detail,container,false)
                  view.detailviewfragment_recyleview
                  return view
          
              }
              inner class  DetailRecyclerview:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
                  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                      var view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail,parent,false)
                      return  CustomviewHolder(view)  <<---  CustomviewHolder위에서 alt+Enter 키를 누르면 Create class 메뉴 선택후
                      네번째 메뉴중 CustomviewHolder 선택 
          
              inner class CustomviewHolder(view: View?) : RecyclerView.ViewHolder() {
              
                      } 위 코드가 자동 생성
                      
                         
     *** www.pixabay.com 에 무료 사진이 많음
     
     
     import androidx.recyclerview.widget.LinearLayoutManager
     import androidx.recyclerview.widget.RecyclerView
     
     
     import kotlinx.android.synthetic.main.fragment_detail.view.*
     
     private var View.layoutManager: LinearLayoutManager
         get() {}
         set() {}
     private var View.adapter: DetailviewFragment.DetailRecyclerviewAdapter
         get() {}
         set() {}
     
     class DetailviewFragment :Fragment() {
         override fun onCreateView(
             inflater: LayoutInflater,
             container: ViewGroup?,
             savedInstanceState: Bundle?
         ): View? {
             var view = LayoutInflater.from(inflater.context).inflate(R.layout.fragment_detail, container, false)
             
             view.detailviewfragment_recyleview.adapter = DetailRecyclerviewAdapter()
             view.detailviewfragment_recyleview.layoutManager = LinearLayoutManager(activity)
             
             return view
             
         }
     
         inner class DetailRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     
             override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                 //var imageview = LayoutInflater.from(parent.context).inflate(R.layout.item_detail,parent,false)
                 var imageview = ImageView(parent.context)
                 return CustomviewHolder(imageview)
             }
     
             inner class CustomviewHolder(imageView: ImageView?) : RecyclerView.ViewHolder(imageView) {
     
             }
     
             override fun getItemCount(): Int {
                 return 3
     
             }
     
             override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
     
             }
         }
     }
     
    3.Adapter 생성
      CustomViewHolder(view) <<== 빨간줄이 나오면 alt+Enter 키를 누르고 create class 선택하면 다음줄이 자동 완성
              }
      
              inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder() {
      
              }
              
              
              
              class CartActivity : BaseActivity() {
                  internal var cartItemsList: MutableList<ItemObject> = ArrayList()
              
                  override fun onCreate(savedInstanceState: Bundle?) {
                      super.onCreate(savedInstanceState)
                      setContentView(R.layout.cart_master)
              
                      cartItemsList = DatabaseAdapter.cartItems
                      cart_RecyclerView.setHasFixedSize(true)
                      cart_RecyclerView.layoutManager = LinearLayoutManager(this)
                      rvAdapter = ItemRecyclerViewAdapter(cartItemsList, this){item->
                          supportActionBar?.setTitle("hi")
                      }
                      cart_RecyclerView.adapter = rvAdapter
                  }
              }