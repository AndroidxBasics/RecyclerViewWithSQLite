package com.example.lubuntupc.choreapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.lubuntupc.choreapp.R
import com.example.lubuntupc.choreapp.data.ChoreListAdapter
import com.example.lubuntupc.choreapp.data.ChoresDatabaseHandler
import com.example.lubuntupc.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null

    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    //dodatkowo do pomocy
    private var choreListItems: ArrayList<Chore>? = null

    private var layoutManager: RecyclerView.LayoutManager? = null

    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDatabaseHandler(this)

        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreListItems!!, this)

        //setup list = recyclerView
        recyclerViewId.adapter = adapter
        recyclerViewId.layoutManager = layoutManager

        //load chores
        choreList = dbHandler!!.readChores()
        choreList!!.reverse()


        for (c in choreList!!.iterator()) {
            val chore = Chore()
            chore.choreName = c.choreName
            chore.assignedBy = c.assignedBy
            chore.assignedTo = c.assignedTo
            chore.id = c.id
            chore.showHumanDate(c.timeAssigned!!)

            choreListItems!!.add(chore)
         //   Log.d("List", c.choreName)
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.add_menu_button) {
            Log.d("Item Clicked", "Item Clicked")

            createPopupDialog()
        }
        return super.onOptionsItemSelected(item)
    }


    fun createPopupDialog() {
        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.popEnterChore
        var assignedBy = view.popEnterAssignedBy
        var assignedTo = view.popEnterAssignTo
        var saveButton = view.popSaveChore

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveButton.setOnClickListener {
            var name = choreName.text.toString().trim()
            var aBy = assignedBy.text.toString().trim()
            var aTo = assignedTo.text.toString().trim()

            if (!TextUtils.isEmpty(name)
                    && !TextUtils.isEmpty(aBy)
                    && !TextUtils.isEmpty(aTo)) {

                var chore = Chore()
                chore.choreName = name
                chore.assignedBy = aBy
                chore.assignedTo = aTo

                dbHandler!!.createChore(chore)

                dialog!!.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()

            } else {

            }
        }

    }

}
