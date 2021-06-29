package com.example.lottonum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val addBt: Button by lazy {
        findViewById(R.id.add_bt)
    }

    private val resetBt: Button by lazy {
        findViewById(R.id.reset_bt)
    }

    private val runBt by lazy {
        findViewById<Button>(R.id.run_bt)
    }

    private val numberPicker by lazy {
        findViewById<NumberPicker>(R.id.num_picker)
    }

    private val numberTVList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.num_tv1),
            findViewById(R.id.num_tv2),
            findViewById(R.id.num_tv3),
            findViewById(R.id.num_tv4),
            findViewById(R.id.num_tv5),
            findViewById(R.id.num_tv6)
        )
    }

    private var isRun = false

    private val picNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunBt()
        initAddBt()
        initResetBt()

    }

    private fun initRunBt(){
        runBt.setOnClickListener {
            val list = getRandomNumber()

            list.forEachIndexed { index, num ->
                val textView = numberTVList[index]
                textView.text = num.toString()
                textView.isVisible = true
                setTvBackground(textView,num)
            }

            isRun = true
        }
    }

    private fun initAddBt(){
        addBt.setOnClickListener {

            if (isRun){
                Toast.makeText(this,"초기화 후에 시도해 주세요",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (picNumberSet.size >= 6){
                Toast.makeText(this,"번호는 6개까지 선택할 수 있습니다.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (picNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"이미 선택된 번호 입니다.",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val textView = numberTVList[picNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            setTvBackground(textView,numberPicker.value)
            picNumberSet.add(numberPicker.value)
        }
    }

    private fun initResetBt(){
        resetBt.setOnClickListener {
            picNumberSet.clear()
            numberTVList.forEach{
                it.isVisible = false
            }

            isRun = false
        }
    }

    private fun getRandomNumber(): List<Int>{
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45){
                    if (picNumberSet.contains(i)){
                        continue
                    }
                    this.add(i)
                }
            }

        numberList.shuffle()

        return picNumberSet.toList() + numberList.subList(0,6 - picNumberSet.size).sorted()
    }

    private fun setTvBackground(textView: TextView, value: Int){
        when (value){
            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_red)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_yellow)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.circle_green)
        }
    }
}