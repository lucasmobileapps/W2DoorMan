package com.example.w2doorman

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.w2doorman.adapater.PersonAdapter
import com.example.w2doorman.model.Persons
import com.example.w2doorman.util.ErrorLogger
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), PersonAdapter.PersonAdapterDelegate {

    var personList: MutableList <Persons> = mutableListOf()
    val contentUrl = "content://com.example.w2fragmentsroom.provider.PersonProvider/persons"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()

    }

    fun setUpRecyclerView(){
        allPersons_recyclerview.adapter = PersonAdapter(this)
        allPersons_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)


        val itemDecorator = DividerItemDecoration(this, LinearLayout.VERTICAL)
        allPersons_recyclerview.addItemDecoration(itemDecorator)
    }

    override fun onResume() {
        super.onResume()
        getCurrentPersons()
        (allPersons_recyclerview.adapter as PersonAdapter).submitList(personList)
        //Log.d("LOG_THIS","${personList.get(0).id} : ${personList.get(0).person} ${personList.get(0).relation} ")
    }

    private fun getCurrentPersons() {

        val uri = Uri.parse(contentUrl)
        var cursor: Cursor?
        val stringBuilder = StringBuilder()

        personList.clear()
        (allPersons_recyclerview.adapter as PersonAdapter).notifyDataSetChanged()

        try {
            ErrorLogger.logError(Throwable("Attempting"))
            cursor = contentResolver
                .query(uri, null, null, null, null)

            cursor?.let { myValues ->
                if (myValues.count == 0) {
                    my_textview.text = "No Person Entered"
                } else {
                    var nameDB = myValues.getColumnIndex("person_name")
                    var idDB = myValues.getColumnIndex("id")
                    var relationDB = myValues.getColumnIndex("person_relation")


                    myValues.moveToFirst()
                    stringBuilder.append(
                        "${myValues.getInt(idDB)} : ${myValues.getString(nameDB)} ${myValues.getString(relationDB)}\n"
                    )
                    personList.add(Persons(myValues.getInt(idDB), myValues.getString(nameDB),myValues.getString(relationDB)))
                    while (myValues.moveToNext()) {
                        stringBuilder.append(
                            "${myValues.getInt(idDB)} : ${myValues.getString(nameDB)} ${myValues.getString(relationDB)}\n"
                        )
                        personList.add(Persons(myValues.getInt(idDB), myValues.getString(nameDB),myValues.getString(relationDB)))
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
