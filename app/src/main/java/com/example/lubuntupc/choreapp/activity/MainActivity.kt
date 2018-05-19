package com.example.lubuntupc.choreapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.lubuntupc.choreapp.R
import com.example.lubuntupc.choreapp.R.string.assignedBy
import com.example.lubuntupc.choreapp.data.ChoresDatabaseHandler
import com.example.lubuntupc.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.RelativeLayout
import com.example.lubuntupc.choreapp.data.ChoreListAdapter
import kotlinx.android.synthetic.main.activity_chore_list.*


class MainActivity : AppCompatActivity() {


    var dbHandler: ChoresDatabaseHandler? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        checkDB()

        saveCHore.setOnClickListener{

            if (!TextUtils.isEmpty(enterChoreId.text.toString())
                    && !TextUtils.isEmpty(assignedById.text.toString())
                    && !TextUtils.isEmpty(assignToId.text.toString())) {


                //save to database
                var chore = Chore()
                chore.choreName = enterChoreId.text.toString()
                chore.assignedTo = assignToId.text.toString()
                chore.assignedBy = assignedById.text.toString()

                saveToDB(chore)
                Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ChoreListActivity::class.java))
            } else {
                Toast.makeText(this,"Please enter a chore", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun checkDB() {
        if (dbHandler!!.getChoresCount() > 0) {
            startActivity(Intent(this, ChoreListActivity::class.java))
        }
    }

    fun saveToDB(chore: Chore){
        dbHandler!!.createChore(chore)
    }

}















//stare

/*
var chore = Chore()
chore.choreName = "Clean Room"
chore.assignedTo = "James"
chore.assignedBy = "Carlos"

// dbHandler!!.createChore(chore)

//read from database
var chores: Chore = dbHandler!!.readAChore(1)
Log.d("Item:", chores.choreName)
*/