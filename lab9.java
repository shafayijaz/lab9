
package id.my.developer.bmicalculator

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {
        val weightButton = findViewById(R.id.imageView) as ImageView
        val bmiButton = findViewById(R.id.imageView2) as ImageView

        val startFadeoutAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.scale_out)

        bmiButton.startAnimation(startFadeoutAnimation)
        weightButton.startAnimation(startFadeoutAnimation)

        bmiButton.setOnClickListener(){
            val intent = Intent(this, BMIActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.scale_out,R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }

        weightButton.setOnClickListener(){
            val intent = Intent(this, BeratActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.scale_out,R.anim.fade_out)
            startActivity(intent, options.toBundle())
        }
    }
}
package id.my.developer.bmicalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import id.my.developer.bmicalculator.bmiactivity.Presenter
import id.my.developer.bmicalculator.models.BMI

class BMIActivity : AppCompatActivity() {
    val presenter = Presenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        init()
    }

    fun init(){
        val beratField = findViewById(R.id.berat_field) as EditText
        val tinggiField = findViewById(R.id.tinggi_field) as EditText

        val calculateButton = findViewById(R.id.hitung_button) as Button
        calculateButton.setOnClickListener() {
            val bmi = BMI(value = presenter.calculateBMI(beratField.text.toString().toDouble(), tinggiField.text.toString().toDouble()))

            showResultDialog(bmi)
        }
    }

    fun showResultDialog(bmi: BMI){
        val view = layoutInflater.inflate(R.layout.bmi_result_view, null)

        val dialog = AlertDialog.Builder(this).setView(view).show()

        val bmiFieldDialog = view.findViewById<TextView>(R.id.bmi_field)
        val kategoriFieldDialog = view.findViewById<TextView>(R.id.kategori_field)
        val selesaiButton = view.findViewById<Button>(R.id.selesai_button)
        selesaiButton.setOnClickListener{dialog.dismiss()}

        bmiFieldDialog.setText(bmi.toString())
        bmiFieldDialog.setTextColor(ContextCompat.getColor(this, bmi.color))
        kategoriFieldDialog.setText(bmi.kategori)
    }
}
package id.my.developer.bmicalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import id.my.developer.bmicalculator.beratactivity.Presenter
import id.my.developer.bmicalculator.utils.DecimalFormatter

class BeratActivity : AppCompatActivity() {
    val presenter=Presenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berat)

        init()
    }
    fun init(){
        val bmiField = findViewById(R.id.bmi_field) as TextView
        val tinggiField = findViewById(R.id.tinggi_field) as TextView
        findViewById(R.id.calculate_button).setOnClickListener {
            val berat = presenter.calculateBerat(bmiField.text.toString().toDouble(), tinggiField.text.toString().toDouble())

            showResultDialog(berat)
        }
    }
    fun showResultDialog(berat: Double){
        val view = layoutInflater.inflate(R.layout.berat_result_view, null)

        val dialog = AlertDialog.Builder(this).setView(view).show()

        val hasilFieldDialog = view.findViewById<TextView>(R.id.hasil_field)
        val selesaiButton = view.findViewById<Button>(R.id.done_button)
        selesaiButton.setOnClickListener { dialog.dismiss() }

        hasilFieldDialog.setText(DecimalFormatter.format(berat))
    }
}
