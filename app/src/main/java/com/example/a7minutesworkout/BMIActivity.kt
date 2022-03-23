package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"    // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"    // US Unit View
    }
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private val binding by lazy{ ActivityBmiBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바 설정
        setSupportActionBar(binding.toolbarBmiActivity)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }

        }

        binding.btnCalculateUnits.setOnClickListener {
            /*
            if(validateMetricUnits()){
                val heightValue : Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue : Float = binding.etMetricUnitWeight.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue*heightValue) * 100

                displayBMIResult(bmi)

            }else{
                Toast.makeText(this@BMIActivity, "유효한 값을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
            */
            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE
        binding.tilUsMetricUnitWeight.visibility = View.GONE
        binding.tilMetricUsUnitHeightFeet.visibility = View.GONE
        binding.tilMetricUsUnitHeightInch.visibility = View.GONE

        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilMetricUnitHeight.visibility = View.INVISIBLE
        binding.tilUsMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUsUnitHeightFeet.visibility = View.VISIBLE
        binding.tilMetricUsUnitHeightInch.visibility = View.VISIBLE

        binding.etUsMetricUnitWeight.text!!.clear()
        binding.etUsMetricUnitHeightFeet.text!!.clear()
        binding.etUsMetricUnitHeightInch.text!!.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi:Float){

        val bmiLabel: String
        val bmiDescription: String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "매우 극심한 저체중입니다"
            bmiDescription = "당신의 건강을 위해 살을 찌우셔야 합니다!"
        } else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "매우 저체중입니다"
            bmiDescription = "당신의 건강을 위해 살을 찌우셔야 합니다!"
        } else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "체중 미달입니다"
            bmiDescription = "당신의 건강을 위해 살을 찌우셔야 합니다!"
        } else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "정상입니다"
            bmiDescription = "축하합니다! 정상 체중이 되셨군요!"
        } else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "과체중입니다"
            bmiDescription = "당신의 건강을 위해 운동을 하셔야 합니다! 7minutes workout을 이용해보세요!"
        } else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "중도 비만입니다"
            bmiDescription = "당신의 건강을 위해 운동을 하셔야 합니다! 7minutes workout을 이용해보세요!"
        } else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0){
            bmiLabel = "고도 비만입니다"
            bmiDescription = "당신의 건강이 위험할지도 모릅니다, 당장 운동을 시작하세요!"
        } else {
            bmiLabel = "심각한 고도 비만입니다"
            bmiDescription = "당신의 건강이 위험할지도 모릅니다, 당장 운동을 시작하세요"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.llDisplayBMIResult.visibility = View.VISIBLE
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }

    // 입력창이 비었는지 확인(Metric)
    private fun validateMetricUnits():Boolean{
        var isValid = true  // 초기화

        if(binding.etMetricUnitWeight.text.toString().isEmpty()){
            isValid = false
        }else if(binding.etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }


    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightValue : Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue : Float = binding.etMetricUnitWeight.text.toString().toFloat() / 100

                val bmi = weightValue / (heightValue*heightValue) * 100

                displayBMIResult(bmi)

            }else{
                Toast.makeText(this@BMIActivity,
                    "유효한 값을 입력해 주세요.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            if(validateUsUnits()){
                val usUnitHeightValueFeet: String =
                    binding.etUsMetricUnitHeightFeet.text.toString()
                val usUnitHeightValueInch: String =
                    binding.etUsMetricUnitHeightInch.text.toString()
                val usUnitWeightValue: Float = binding.etUsMetricUnitWeight
                    .text.toString().toFloat()

                // 인치 계산
                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi)

            }else{
                Toast.makeText(this@BMIActivity,
                    "유효한 값을 입력해 주세요.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // 입력창이 비었는지 확인(US)
    private fun validateUsUnits():Boolean{
        var isValid = true  // 초기화

        when {
            binding.etUsMetricUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsMetricUnitHeightFeet.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsMetricUnitHeightInch.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }

}