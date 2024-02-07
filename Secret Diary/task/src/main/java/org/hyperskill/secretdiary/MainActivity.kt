package org.hyperskill.secretdiary
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaInstant
import org.hyperskill.secretdiary.databinding.ActivityMainBinding
import java.time.*
import java.time.format.DateTimeFormatter

const val PREF_DIARY = "PREF_DIARY"
const val KEY_DIARY_TEXT = "KEY_DIARY_TEXT"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences(PREF_DIARY, Context.MODE_PRIVATE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.tvDiary.text = sharedPreferences.getString(KEY_DIARY_TEXT, "")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener {
            if (binding.etNewWriting.text.isBlank())
                Toast.makeText(
                    applicationContext,
                    "Empty or blank input cannot be saved",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                val currentDateTime =
                    Clock.System.now().toJavaInstant().atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                if (binding.tvDiary.text.isNotEmpty()) binding.tvDiary.text =
                    ("${currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}\n${binding.etNewWriting.text}\n\n") + binding.tvDiary.text
                else binding.tvDiary.text =
                    ("${currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}\n${binding.etNewWriting.text}")
            }
            binding.etNewWriting.setText("")
        }
        binding.btnUndo.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Remove last note")
                .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton("Yes") { _, _ ->
                    binding.tvDiary.text = removeUntilDoubleNewLine(binding.tvDiary.text)
                }.setNegativeButton("No", null).show()
        }
        binding.tvDiary.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val editor = sharedPreferences.edit()
                editor.putString(KEY_DIARY_TEXT, binding.tvDiary.text.toString()).apply()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
        fun removeUntilDoubleNewLine(s: CharSequence): CharSequence {
            val index = s.indexOf("\n\n")
            return if (index != -1) {
                s.substring(index + 2)
            } else {
                ""
            }
        }
    }
