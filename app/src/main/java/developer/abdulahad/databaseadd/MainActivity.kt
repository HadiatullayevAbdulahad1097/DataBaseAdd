package developer.abdulahad.databaseadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import developer.abdulahad.databaseadd.DB.MyDbHelper
import developer.abdulahad.databaseadd.Models.User
import developer.abdulahad.databaseadd.adapter.RvAction
import developer.abdulahad.databaseadd.adapter.RvAdapter
import developer.abdulahad.databaseadd.databinding.ActivityMainBinding
import developer.abdulahad.databaseadd.databinding.ItemDialogBinding

class MainActivity : AppCompatActivity(), RvAction {
    lateinit var binding: ActivityMainBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var list : ArrayList<User>
    lateinit var rvAdapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)

        list = myDbHelper.getUsers() as ArrayList<User>

        rvAdapter = RvAdapter(list,this)
        binding.rv.adapter = rvAdapter

        binding.btnAdd.setOnClickListener{
            var alertDialog = AlertDialog.Builder(this).create()

            val itemDialog = ItemDialogBinding.inflate(layoutInflater)

            itemDialog.btnSave.setOnClickListener {
                val user = User(
                    itemDialog.edtName.text.toString(),
                    itemDialog.edtNumber.text.toString()
                )
                list.add(user)
                rvAdapter.notifyDataSetChanged()
                myDbHelper.addUser(user)
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
                alertDialog.cancel()
            }
            alertDialog.setView(itemDialog.root)
            alertDialog.show()
        }
    }

    override fun showPopupMenu(view: View, user: User) {
        var popupMenu = PopupMenu(this,view)
        popupMenu.inflate(R.menu.my_popup_menu)

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete -> {
                    myDbHelper.delete(user)
                    var index = list.indexOf(user)
                    list.remove(user)
                    rvAdapter.notifyItemRemoved(index)
                    Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
                }
                R.id.edit ->{
                    var alertDialog = AlertDialog.Builder(this).create()

                    val itemDialog = ItemDialogBinding.inflate(layoutInflater)

                    itemDialog.edtName.setText(user.name)
                    itemDialog.edtNumber.setText(user.number)
                    var index = list.indexOf(user)

                    itemDialog.btnSave.setOnClickListener {
                        user.name = itemDialog.edtName.text.toString()
                        user.number = itemDialog.edtNumber.text.toString()
                        list[index] = user
                        rvAdapter.notifyItemChanged(index)
                        myDbHelper.upDateUser(user)
                        Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()

                        alertDialog.cancel()
                    }
                        alertDialog.setView(itemDialog.root)

                    alertDialog.show()
                }
            }
            true
        }

        popupMenu.show()
    }
}