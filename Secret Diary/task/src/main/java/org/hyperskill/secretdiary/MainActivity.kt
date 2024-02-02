package org.hyperskill.secretdiary
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import org.hyperskill.secretdiary.databinding.ActivityMainBinding
import java.time.*
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener {
            if (binding.etNewWriting.text.isBlank())
                Toast.makeText(applicationContext, "Empty or blank input cannot be saved", Toast.LENGTH_SHORT).show()
            else {
                val currentDateTime = Clock.System.now().toJavaInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                if (binding.tvDiary.text.isNotEmpty()) binding.tvDiary.text = ("${currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}\n${binding.etNewWriting.text}\n\n") + binding.tvDiary.text
                else binding.tvDiary.text = ("${currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}\n${binding.etNewWriting.text}")
            }
            binding.etNewWriting.setText("")
        }

        /*
            Tests for android can not guarantee the correctness of solutions that make use of
            mutation on "static" variables to keep state. You should avoid using those.
            Consider "static" as being anything on kotlin that is transpiled to java
            into a static variable. That includes global variables and variables inside
            singletons declared with keyword object, including companion object.
            This limitation is related to the use of JUnit on tests. JUnit re-instantiate all
            instance variable for each test method, but it does not re-instantiate static variables.
            The use of static variable to hold state can lead to state from one test to spill over
            to another test and cause unexpected results.
            Using mutation on static variables to keep state
            is considered a bad practice anyway and no measure
            attempting to give support to that pattern will be made.
         */
    }
}