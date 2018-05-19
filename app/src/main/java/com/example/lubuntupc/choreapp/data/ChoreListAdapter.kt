package com.example.lubuntupc.choreapp.data

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.lubuntupc.choreapp.R
import com.example.lubuntupc.choreapp.activity.ChoreListActivity
import com.example.lubuntupc.choreapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

/**
 * Created by lubuntupc on 21.11.17.
 */
class ChoreListAdapter(private var list: ArrayList<Chore>, private var context: Context): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view, context, list)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(list[position])
    }

    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Chore>): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mContext = context
        var mList = list

        var choreName = itemView.findViewById<TextView>(R.id.listChoreName)
        var assignedBy = itemView.findViewById<TextView>(R.id.listAssignedBy)
        var assignedTo = itemView.findViewById<TextView>(R.id.listAssignedTo)
        var assignedDate = itemView.findViewById<TextView>(R.id.listDate)
        var deleteButton = itemView.findViewById<Button>(R.id.listDeleteButton)
        var editButton = itemView.findViewById<Button>(R.id.listEditButton)

        fun bindView(chore: Chore) {
            choreName.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedDate.text = chore.showHumanDate(System.currentTimeMillis())
            assignedTo.text = chore.assignedTo

            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)

        }



        override fun onClick(view: View?) {

            var mPosition: Int = adapterPosition
            var chore = mList[mPosition]

            when(view!!.id) {
                deleteButton.id -> {
                    deleteChore(chore.id!!)
                    mList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editButton.id -> {
                    editChore(chore)

                }

            }

        }

        fun deleteChore(id: Int) {
            var db: ChoresDatabaseHandler = ChoresDatabaseHandler(mContext)

            db.deleteChore(id)
        }

        fun editChore(chore: Chore) {

            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?
            var dbHandler: ChoresDatabaseHandler = ChoresDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            var choreName = view.popEnterChore
            var assignedBy = view.popEnterAssignedBy
            var assignedTo = view.popEnterAssignTo
            var saveButton = view.popSaveChore

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            saveButton.setOnClickListener {
                var name = choreName.text.toString().trim()
                var aBy = assignedBy.text.toString().trim()
                var aTo = assignedTo.text.toString().trim()

                if (!TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(aBy)
                        && !TextUtils.isEmpty(aTo)) {

                    //  var chore = Chore()
                    chore.choreName = name
                    chore.assignedBy = aBy
                    chore.assignedTo = aTo

                    dbHandler!!.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)

                    dialog!!.dismiss()

//                    startActivity(Intent(this, ChoreListActivity::class.java))
//                    finish()

                } else {

                }
            }


        }
    }




}