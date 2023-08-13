package com.test.mini_board_projects

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import com.test.mini_board_projects.Auth.AddUserinfoFragment
import com.test.mini_board_projects.Auth.JoinFragment
import com.test.mini_board_projects.Auth.LoginFragment
import com.test.mini_board_projects.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var newFragment: Fragment? = null
    var oldFragment: Fragment? = null

    companion object {
        val LOGIN_FRAGMENT = "LoginFragment"
        val JOIN_FRAGMENT = "JoinFragment"
        val ADD_USER_INFO_FRAGMENT = "AddUserInfoFragment"
        val BOARD_MAIN_FRAGMENT = "BoardMainFragment"
        val POST_WRITE_FRAGMENT = "PostWriteFragment"
        val POST_READ_FRAGMENT = "PostReadFragment"
        val POST_MODIFY_FRAGMENT = "PostModifyFragment"
    }

    //게시판의 종류
    val boardTypeList = arrayOf(
        "자유게시판", "유머게시판", "질문게시판", "스포츠게시판"
    )

    // 확인할 권한 목록
    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
        Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        requestPermissions(permissionList, 0)
        //앱이 실행됨과 동시에 LoginFragment로 이동한다.
        replaceFragment(LOGIN_FRAGMENT,true,null)

    }

    fun splashScreenCustomizing(splashScreen: SplashScreen){
        // SplashScreen이 사라질 때 동작하는 리스너를 설정한다.
        splashScreen.setOnExitAnimationListener{
            // 가로 비율 애니메이션
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 2f, 1f, 0f)
            // 세로 비율 애니메이션
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 2f, 1f, 0f)
            // 투명도
            val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 1f, 0.5f, 0f)

            // 애니메이션 관리 객체를 생성한다.
            // 첫 번째 : 애니메이션을 적용할 뷰
            // 나머지는 적용한 애니메이션 종류
            val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(it.iconView, scaleX, scaleY, alpha)
            // 애니메이션 적용을 위한 에이징
            objectAnimator.interpolator = AnticipateInterpolator()
            // 애니메이션 동작 시간
            objectAnimator.duration = 1000
            // 애니메이션이 끝났을 때 동작할 리스너
            objectAnimator.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)

                    // SplashScreen을 제거한다.
                    it.remove()
                }
            })

            // 애니메이션 가동
            objectAnimator.start()
        }
    }
    fun replaceFragment(name: String, addToBackStack: Boolean, bundle: Bundle?) {

        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()


        // 새로운 Fragment를 담을 변수
        newFragment = when (name) {
            LOGIN_FRAGMENT -> LoginFragment()
            JOIN_FRAGMENT -> JoinFragment()
            ADD_USER_INFO_FRAGMENT -> AddUserinfoFragment()
            /* BOARD_MAIN_FRAGMENT -> BoardMainFragment()
            POST_WRITE_FRAGMENT -> PostWriteFragment()
            POST_READ_FRAGMENT -> PostReadFragment()
            POST_MODIFY_FRAGMENT -> PostModifyFragment() */
            else -> Fragment()
        }

        newFragment?.arguments = bundle


        // Fragment를 교채한다.
        fragmentTransaction.replace(R.id.mainContainer, newFragment!!)

        if (addToBackStack == true) {
            // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
            fragmentTransaction.addToBackStack(name)
        }

        // 교체 명령이 동작하도록 한다.
        fragmentTransaction.commit()
    }
    fun removeFragment(name: String) {
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun showSoftInput(view:View){
        view.requestFocus()

        val inputMethodManger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        thread {
            SystemClock.sleep(200)
            inputMethodManger.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

data class UserClass(var userIdx:Long,            // 사용자 인덱스번호
                     var userId:String,           // 사용자 아이디
                     var userPw:String,           // 비밀번호
                     var userNickname: String,    // 닉네임
                     var userAge:Long,            // 나이
                     var hobby1:Boolean,          // 이하는 취미들
                     var hobby2:Boolean,
                     var hobby3:Boolean,
                     var hobby4:Boolean,
                     var hobby5:Boolean,
                     var hobby6:Boolean)

// 게시글 정보를 담을 클래스
data class PostDataClass(var postIdx:Long,              // 게시글 인덱스 번호
                         var postType:Long,             // 게시판 종류
                         var postSubject:String,        // 제목
                         var postText:String,           // 내용
                         var postWriteDate:String,      // 작성일
                         var postImage:String,          // 첨부이미지 파일 이름
                         var postWriterIdx:Long)        // 작성자 인덱스 번호


