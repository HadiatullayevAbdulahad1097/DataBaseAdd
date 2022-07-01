package developer.abdulahad.databaseadd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import developer.abdulahad.databaseadd.Models.User
import developer.abdulahad.databaseadd.R
import developer.abdulahad.databaseadd.databinding.ItemRvBinding
import kotlin.contracts.contract

class RvAdapter(var list: List<User>,var rvAction: RvAction) : RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var view: ItemRvBinding) : RecyclerView.ViewHolder(view.root) {
        fun onBind(user: User) {
            view.itemName.text = user.name
            view.itemNumber.text = user.number

            view.imagePopup.setOnClickListener {
                 rvAction.showPopupMenu(it,user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
interface RvAction{
    fun showPopupMenu(view:View, user: User)
}