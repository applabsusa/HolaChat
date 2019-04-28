package co.paulfran.paulfranco.holachat.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.paulfran.paulfranco.holachat.R
import co.paulfran.paulfranco.holachat.listeners.ChatClickListener
import co.paulfran.paulfranco.holachat.util.populateImage

class ChatsAdapter(val chats: ArrayList<String>) : RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder>(){

    private var clickListener : ChatClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ChatsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
    )

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        holder.bind(chats[position], clickListener)
    }

    fun setOnItemClickListener(listener: ChatClickListener) {
        clickListener = listener
        notifyDataSetChanged()
    }

    fun updateChats(updatedChats: ArrayList<String>) {
        chats.clear()
        chats.addAll(updatedChats)
        notifyDataSetChanged()

    }

    class ChatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var chatIV = view.findViewById<ImageView>(R.id.chatIV)
        private var chatName = view.findViewById<TextView>(R.id.chatTV)

        fun bind(chatId: String, listener: ChatClickListener?) {
            populateImage(chatIV.context, "", chatIV, R.drawable.default_user)
            chatName.text = chatId
        }
    }
}