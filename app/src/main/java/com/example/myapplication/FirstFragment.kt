package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.api.UserAPIService
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService = UserAPIService.create()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitBtn.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            val userId = binding.idInput.text
            val user = userAPIService.getUser(userId.toString())

            // trigger when userId input is empty
            if(userId.toString().isEmpty()){
                showErrorMsg("Enter id")
                clear()
                return@setOnClickListener
            }

            // trigger when userId input number is larger than 10
            if(Integer.parseInt(userId.toString()) > 10){
                showErrorMsg("Id must be less than or equal 10")
                clear()
                return@setOnClickListener
            }
            user.enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    clearErrorMsg()
                    val body = response.body()
                    body?.let {
                        binding.idOutput.text = it.id.toString()
                        binding.nameOutput.text = it.name
                        binding.emailOutput.text = it.email
                    }
                    hide_keyboard_from(view.context, view)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("FirstFragment", t.message!!)
                }
            })
        }

    }
    fun hide_keyboard_from(context: Context, view: View) {
        val inputMethodManager: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clear(){
        binding.idOutput.setText("")
        binding.nameOutput.setText("")
        binding.emailOutput.setText("")
    }

    fun showErrorMsg(msg: String){
        binding.tvErrorMsg.text = msg
    }

    fun clearErrorMsg(){
        binding.tvErrorMsg.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}