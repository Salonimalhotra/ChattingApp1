package com.example.chattingapp

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context:Context,val messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE=1
    val ITEM_SENT=2


    override fun getItemViewType(position: Int): Int {
        var currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECIEVE
        }
    }

    class RecieveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val recieveMessage=itemView.findViewById<TextView>(R.id.txt_recieve_message)


    }

    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val sentMessage=itemView.findViewById<TextView>(R.id.txt_sent_message)

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
    if(p1==1){
        //inflate recieve
        val view:View=LayoutInflater.from(context).inflate(R.layout.recieve,p0,false)
        return RecieveViewHolder(view)
    }
        else{
            //inflate sent
            val view:View=LayoutInflater.from(context).inflate(R.layout.sent,p0,false)
            return SentViewHolder(view)
    }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val currentMessage=messageList[p1]
        if(p0.javaClass==SentViewHolder::class.java){
         //do the stuff for sent viewHolder

             val viewHolder=p0 as SentViewHolder
             p0.sentMessage.text=currentMessage.message
     }

        else{
         //do the stuff for recieve viewHolder
         val viewHolder=p0 as RecieveViewHolder
         p0.recieveMessage.text=currentMessage.message
     }

    }

    override fun getItemCount(): Int {
  return messageList.size
    }
}