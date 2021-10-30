package uz.xsoft.noteapp.ui.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexandrius.accordionswipelayout.library.SwipeLayout
import uz.xsoft.noteapp.R
import uz.xsoft.noteapp.data.entity.NoteEntity

class NoteAdapter : ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder>(DiffItem) {
    private var listener: ((NoteEntity, Int) -> Unit)? = null
    fun setListener(block: (NoteEntity, Int) -> Unit) {
        listener = block
    }

    object DiffItem : DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
            return oldItem == newItem
            /*oldItem.isPinned == newItem.isPinned ||
                        oldItem.title == newItem.title ||
                        oldItem.message == newItem.message ||
                        oldItem.time == newItem.time*/

        }
    }

    @SuppressLint("ResourceType")
    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = itemView.findViewById(R.id.textTitle)
        private val description: TextView = itemView.findViewById(R.id.textDescription)
        private val pinImage: ImageView = itemView.findViewById(R.id.pinButton)

        init {
            val swipeLayout = itemView.findViewById(R.id.swipe_layout) as SwipeLayout
            swipeLayout.setOnSwipeItemClickListener { left, index ->
                if (left) {
                    when (index) {
                        0 -> {
                            getItem(adapterPosition).isPinned = !getItem(adapterPosition).isPinned
                        }
                    }
                } else {
                    when (index) {
                        0 -> {
                            getItem(adapterPosition).isRemoved = 1
                        }
                    }
                }
                listener?.invoke(getItem(adapterPosition), adapterPosition)
            }
            swipeLayout.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("infoData", getItem(adapterPosition))
                itemView.findNavController()
                    .navigate(R.id.action_mainScreen_to_addNoteScreen, bundle)
            }
            /*itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("infoData", getItem(adapterPosition))
                itemView.findNavController()
                    .navigate(R.id.action_mainScreen_to_addNoteScreen, bundle)
            }*/
        }

        fun bind() {
            val data = getItem(adapterPosition)
            title.text = data.title
            description.text = Html.fromHtml(data.message)
            if (data.isPinned) {
                pinImage.visibility = View.VISIBLE
            } else {
                pinImage.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind()
    }
}