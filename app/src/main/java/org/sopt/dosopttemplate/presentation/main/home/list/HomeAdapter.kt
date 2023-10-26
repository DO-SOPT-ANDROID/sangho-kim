package org.sopt.dosopttemplate.presentation.main.home.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ItemBirthInfoBinding
import org.sopt.dosopttemplate.databinding.ItemFriendInfoBinding
import org.sopt.dosopttemplate.databinding.ItemMyInfoBinding

class HomeAdapter(
    context: Context,
    private val itemClick: (UserInfo.MyInfo) -> (Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater by lazy { LayoutInflater.from(context) }

    private var itemList = mutableListOf<UserInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MY_INFO -> {
                MyInfoViewHolder(ItemMyInfoBinding.inflate(inflater, parent, false), itemClick)
            }

            VIEW_TYPE_FRIEND_INFO -> {
                FriendInfoViewHolder(ItemFriendInfoBinding.inflate(inflater, parent, false))
            }

            VIEW_TYPE_BIRTHDAY_INFO -> {
                BirthInfoViewHolder(ItemBirthInfoBinding.inflate(inflater, parent, false))
            }

            else -> throw ClassCastException(
                parent.context.getString(R.string.view_type_error_msg, viewType)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyInfoViewHolder -> holder.onBind(itemList[position] as UserInfo.MyInfo)
            is FriendInfoViewHolder -> holder.onBind(itemList[position] as UserInfo.FriendInfo)
            is BirthInfoViewHolder -> holder.onBind(itemList[position] as UserInfo.FriendInfo)
        }
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is UserInfo.MyInfo -> VIEW_TYPE_MY_INFO
            is UserInfo.FriendInfo -> if ((itemList[position] as UserInfo.FriendInfo).isBirthday) VIEW_TYPE_BIRTHDAY_INFO else VIEW_TYPE_FRIEND_INFO
        }
    }

    fun addList(list: MutableList<UserInfo>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun changeMyInfo(userInfo: UserInfo) {
        itemList.removeAt(0)
        itemList.add(0, userInfo)
        notifyDataSetChanged()
    }

    companion object {

        private const val VIEW_TYPE_MY_INFO = 0
        private const val VIEW_TYPE_FRIEND_INFO = 1
        private const val VIEW_TYPE_BIRTHDAY_INFO = 2
    }
}