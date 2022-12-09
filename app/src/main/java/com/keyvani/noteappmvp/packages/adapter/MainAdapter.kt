package com.keyvani.noteappmvp.packages.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keyvani.noteappmvp.R
import com.keyvani.noteappmvp.databinding.ItemNotesBinding
import com.keyvani.noteappmvp.packages.data.model.NoteEntity
import com.keyvani.noteappmvp.packages.utils.DELETE
import com.keyvani.noteappmvp.packages.utils.EDIT
import javax.inject.Inject

class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    //Binding
    private lateinit var binding: ItemNotesBinding
    private lateinit var context: Context
    private var noteList = emptyList<NoteEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(noteList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = noteList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: NoteEntity) {
            binding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.desc
                //Priority
                when (item.priority) {
                    "High" -> priorityColor.setBackgroundColor(ContextCompat.getColor(context, R.color.red))

                    "Medium" -> priorityColor.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))

                    "Low" -> priorityColor.setBackgroundColor(ContextCompat.getColor(context, R.color.aqua))

                }
                //Category
                when (item.category) {
                    "Work" -> ivCategory.setImageResource(R.drawable.work)

                    "Home" -> ivCategory.setImageResource(R.drawable.home)

                    "Education" -> ivCategory.setImageResource(R.drawable.education)

                    "Health" -> ivCategory.setImageResource(R.drawable.healthcare)
                }
                //Menu
                ivMenu.setOnClickListener {
                    val popupMenu = PopupMenu(context, it)
                    popupMenu.menuInflater.inflate(R.menu.menu_item, popupMenu.menu)
                    popupMenu.show()
                    //Click
                    popupMenu.setOnMenuItemClickListener { items ->
                        when (items.itemId) {
                            R.id.itemEdit -> {
                                onItemClickListener?.let {
                                    it(item, EDIT)
                                }

                            }
                            R.id.itemDelete -> {
                                onItemClickListener?.let {
                                    it(item, DELETE)
                                }

                            }
                        }
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }

    }

    private var onItemClickListener: ((NoteEntity, String) -> Unit)? = null

    fun setonItemClickListener(listener: (NoteEntity, String) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<NoteEntity>) {
        val moviesDiffUtil = NotesDiffUtils(noteList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        noteList = data
        diffUtils.dispatchUpdatesTo(this)

    }

    class NotesDiffUtils(
        private val oldItem: List<NoteEntity>, private val newItem: List<NoteEntity>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }


    }


}