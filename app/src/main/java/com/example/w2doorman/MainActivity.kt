package com.example.w2doorman

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.w2doorman.util.ErrorLogger
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    val contentUrl = "content://com.example.w2fragmentsroom.provider.PersonProvider/persons"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        getCurrentPersons()

    }

    private fun getCurrentPersons() {

        val uri = Uri.parse(contentUrl)
        var cursor: Cursor?
        val stringBuilder = StringBuilder()

        try {
            ErrorLogger.logError(Throwable("Attempting"))
            cursor = contentResolver
                .query(uri, null, null, null, null)

            cursor?.let { myValues ->
                if (myValues.count == 0) {
                    my_textview.text = "No Person Entered"
                } else {
                    myValues.moveToFirst()
                    stringBuilder.append(
                        "${myValues.getString(myValues.getColumnIndex("person_name"))} ${myValues.getString(
                            myValues.getColumnIndex("person_relation")
                        )}\n"
                    )

                    while (myValues.moveToNext()) {
                        stringBuilder.append(
                            "${myValues.getString(myValues.getColumnIndex("person_name"))} ${myValues.getString(
                                myValues.getColumnIndex("person_relation")
                            )}\n"
                        )
                    }
                    my_textview.text = stringBuilder.toString()

                }

                myValues.close()
            } ?: {
                my_textview.text = "Empty List"
                ErrorLogger.logError(Throwable("Resolver was null."))
            }()
        } catch (throwable: Throwable) {
            ErrorLogger.logError(throwable)
        }

    }
}
