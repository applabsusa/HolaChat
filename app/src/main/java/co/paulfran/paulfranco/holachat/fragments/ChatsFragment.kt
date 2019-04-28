package co.paulfran.paulfranco.holachat.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import co.paulfran.paulfranco.holachat.R
import co.paulfran.paulfranco.holachat.adapters.ChatsAdapter
import co.paulfran.paulfranco.holachat.listeners.ChatClickListener
import com.google.api.Distribution
import kotlinx.android.synthetic.main.fragment_chats.*


class ChatsFragment : Fragment(), ChatClickListener {

    private var chatsAdapter = ChatsAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatsAdapter.setOnItemClickListener(this)

        chatsRV.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            adapter = chatsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            var chatList = arrayListOf("chat 1", "chat 2", "chat 3", "chat 1", "chat 2", "chat 3", "chat 1", "chat 2", "chat 3")
            chatsAdapter.updateChats(chatList)

        }
    }

    override fun onChatClicked(name: String?, otheruserId: String?, chatImageUrl: String?, chatName: String?) {
        Toast.makeText(context, "$name clicked", Toast.LENGTH_SHORT).show()
    }

}
