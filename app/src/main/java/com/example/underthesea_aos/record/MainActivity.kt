package com.example.underthesea_aos.record

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.databinding.ActivityMainBinding
import com.example.underthesea_aos.plan.GetPlanRes
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_record.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/*
    기록 작성 화면
 */
class MainActivity : AppCompatActivity() {
    lateinit var spinner: Spinner
    lateinit var result: TextView
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private var imageFragment: View? = null
    var pickImageFromAlbum = 0
    var fbStorage: FirebaseStorage? = null
    var uriPhoto: Uri? = null
    var satisfaction = 1
    lateinit var plan: String
    var strDate = ""
    var planTitles =  ArrayList<String>()
    var planIds =  ArrayList<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        //back 버튼 클릭 시
        back.setOnClickListener{
            val intent1 = Intent(this, MainActivity2::class.java)
            intent1.putExtra("date",strDate)
            startActivity(intent1)
        }

        //캘린더로부터 날짜 받아올 인텐트
        if(intent.hasExtra("date")) {  //date라는 키값을 가진 intent가 정보를 가지고 있다면 실행
            // date라는 id의 textview의 문구를 date라는 키값을 가진 intent의 정보로 변경
            strDate = intent.getStringExtra("date").toString()
            Log.d("date", intent.getStringExtra("date").toString())
            date.text = strDate
        }

        GetPlans(strDate)
        spinner = findViewById(R.id.spinner)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planTitles);
        spinner.adapter = adapter

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        val intent4 = Intent(this, com.example.underthesea_aos.plan.UpdateActivity::class.java)
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                plan = spinner.selectedItem.toString()
                intent4.putExtra("plan_id", planIds[position])
                startActivity(intent4)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btn_UploadPicture.setOnClickListener{
            onCreateView()
        }

        //글자수 제한
        with(binding){
            txt_content.addTextChangedListener(object : TextWatcher {
                var maxText = ""
                override fun beforeTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    maxText = pos.toString()
                }
                override fun onTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    if(txt_content.lineCount > 3){
                        Toast.makeText(this@MainActivity,
                            "최대 3줄까지 입력 가능합니다.",
                            Toast.LENGTH_SHORT).show()

                        txt_content.setText(maxText)
                        txt_content.setSelection(txt_content.length())
                        count.setText("${txt_content.length()} / 30")
                    } else if(txt_content.length() > 30){
                        Toast.makeText(this@MainActivity, "최대 30자까지 입력 가능합니다.",
                            Toast.LENGTH_SHORT).show()

                        txt_content.setText(maxText)
                        txt_content.setSelection(txt_content.length())
                        count.setText("${txt_content.length()} / 30")
                    } else {
                        count.setText("${txt_content.length()} / 30")
                    }
                }
                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }

        //만족 버튼 클릭
        smile.setOnClickListener{
            satisfaction = 1
        }

        //불만족 버튼 클릭
        sad.setOnClickListener{
            Log.d("Response: ", txt_content.context.toString())
            satisfaction = 2
        }

        //cancel 버튼 클릭 -> 기록 view page로 이동
        btn_cancel.setOnClickListener{
            val intent2 = Intent(this, MainActivity2::class.java)
            intent2.putExtra("date",strDate)
            startActivity(intent2)
        }
        //view 버튼 클릭 -> 기록 view page로 이동
//        view.setOnClickListener{
//            val intent2 = Intent(this, MainActivity3::class.java)
//            intent2.putExtra("date",strDate)
//            startActivity(intent2)
//        }

        //save 버튼 클릭
        val recordInfo = RecordInfo()
        btn_save.setOnClickListener{
            PostRecords(recordInfo)
            Toast.makeText(this, "저장이 완료되었습니다", Toast.LENGTH_SHORT).show()
            val intent3 = Intent(this, com.example.underthesea_aos.calendar_record.MainActivity::class.java)
            intent3.putExtra("date",strDate)
            startActivity(intent3)
        }
    }

    //날짜에 해당하는 계획 목록 불러올 API
    fun GetPlans(date: String){
        val call = RetrofitBuilder().retrofit().getPlansResponse(date)
        //비동기 방식의 통신
        call.enqueue(object : Callback<BaseResponse<GetPlanRes>> {
            //통신 성공
            override fun onResponse(call: Call<BaseResponse<GetPlanRes>>, response: Response<BaseResponse<GetPlanRes>>) {
                //응답 성공
                if(response.isSuccessful()){
                    Log.d("Response: ", response.body()!!.result.toString())
                    if(response!!.body()?.result?.plans != null){
                        for(i in 0..response.body()!!.result!!.plans!!.size-1) {
                            planTitles.add(response.body()!!.result!!.plans!![i].title.toString());
                            planIds.add(response.body()!!.result!!.plans!![i].planId!!.toLong())
                        }
                    }
                }
                //응답 실패
                else{
                    Log.d("Response: ", "failure")
                }
            }
            //통신 실패
            override fun onFailure(call: Call<BaseResponse<GetPlanRes>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }

    //기록 작성 후 서버와 통신할 API
    fun PostRecords(record: RecordInfo){
        record.date = strDate
        record.content = txt_content.text.toString()
        record.img_url = uriPhoto.toString()
        record.satisfaction = satisfaction
        //record.plan_id = plan

        val call = RetrofitBuilder().retrofit().postRecordsResponse(record)
        //비동기 방식의 통신
        call.enqueue(object : Callback<BaseResponse<Long>> {
            //통신 성공
            override fun onResponse(call: Call<BaseResponse<Long>>, response: Response<BaseResponse<Long>>) {
                //응답 성공
                if(response.isSuccessful()){
                    Log.d("Response: ", response.body().toString())
                }
                //응답 실패
                else{
                    Log.d("Response: ", "failure")
                }
            }
            //통신 실패
            override fun onFailure(call: Call<BaseResponse<Long>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }

    //파이어 베이스 초기화하고 upload 클릭 시 동작할 리스너 구성
    fun onCreateView(){
        //imageFragment = inflater.inflate(R.layout.activity_record, container, false)

        //firebase storage 초기화
        fbStorage = FirebaseStorage.getInstance()

        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, pickImageFromAlbum)

        //return imageFragment
    }

    //앨범에서 사진 선택 시 동작
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                //path for selected image
                uriPhoto = data?.data
                user_image.setImageURI(uriPhoto)

                if (ContextCompat.checkSelfPermission(
                        imageFragment!!.context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    funImageUpload(imageFragment!!)
                }
            } else {

            }
        }
    }

    //사진 선택 후 해당 사진 imageView에 도출 하고 파이어베이스에 업로드
    private fun funImageUpload(view: View) {
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "IMAGE_" + timeStamp + "_.png"
        var storageRef = fbStorage?.reference?.child("images")?.child(imgFileName)

        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
            Toast.makeText(
                view.context, "Image Uploaded",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}