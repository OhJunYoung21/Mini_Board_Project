package com.test.mini_board_projects.Auth

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.test.mini_board_projects.MainActivity
import com.test.mini_board_projects.R
import com.test.mini_board_projects.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    lateinit var loginBinding:FragmentLoginBinding

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        loginBinding = FragmentLoginBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        val view = loginBinding.root

        loginBinding.run{

            toolbarLogin.run{

                title = "로그인"

                setTitleTextColor(Color.WHITE)

            }

            buttonLoginJoin.run{

                setOnClickListener {

                    mainActivity.replaceFragment(MainActivity.JOIN_FRAGMENT,true,null)

                }

            }

            buttonLoginSubmit.run{

                setOnClickListener {

                    loginSubmit()

                }

            }

            textInputEditTextLoginUserPw.run{
                setOnEditorActionListener { textView, i, keyEvent ->
                    loginSubmit()
                    true
                }
            }


        }


        return view
    }

    fun loginSubmit(){

        loginBinding.run{

            val loginUserId = textInputEditTextLoginUserId.text.toString()
            val loginUserPw = textInputEditTextLoginUserPw.text.toString()

            if(loginUserId.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("로그인 오류")
                builder.setMessage("아이디를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginUserId)
                }
                builder.show()
                return
            }

            if(loginUserPw.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비밀번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginUserPw)
                }
                builder.show()
                return
            }

            mainActivity.replaceFragment(MainActivity.BOARD_MAIN_FRAGMENT, false, null)
        }

    }


}