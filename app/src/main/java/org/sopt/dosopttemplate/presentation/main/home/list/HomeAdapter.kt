package org.sopt.dosopttemplate.presentation.main.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ItemBirthInfoBinding
import org.sopt.dosopttemplate.databinding.ItemFriendInfoBinding
import org.sopt.dosopttemplate.databinding.ItemMyInfoBinding
import org.sopt.dosopttemplate.util.ItemDiffCallback

class HomeAdapter(
    private val itemClick: (UserInfo.MyInfo) -> (Unit),
) : ListAdapter<UserInfo, RecyclerView.ViewHolder>(diffUtil) {

    private var itemList = mutableListOf<UserInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MY_INFO -> {
                MyInfoViewHolder(
                    ItemMyInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ), itemClick
                )
            }

            VIEW_TYPE_FRIEND_INFO -> {
                FriendInfoViewHolder(
                    ItemFriendInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }

            VIEW_TYPE_BIRTHDAY_INFO -> {
                BirthInfoViewHolder(
                    ItemBirthInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
            }

            else -> throw ClassCastException(
                parent.context.getString(
                    R.string.view_type_error_msg,
                    viewType,
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyInfoViewHolder -> holder.onBind(itemList[position] as UserInfo.MyInfo)
            is FriendInfoViewHolder -> holder.onBind(itemList[position] as UserInfo.FriendInfo)
            is BirthInfoViewHolder -> holder.onBind(itemList[position] as UserInfo.BirthdayInfo)
        }
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.bottomMargin = if (position == itemList.size) 24 else 0
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is UserInfo.MyInfo -> VIEW_TYPE_MY_INFO
            is UserInfo.FriendInfo -> VIEW_TYPE_FRIEND_INFO
            is UserInfo.BirthdayInfo -> VIEW_TYPE_BIRTHDAY_INFO
        }
    }

    companion object {
        private val diffUtil = ItemDiffCallback<UserInfo>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new },
        )

        private const val VIEW_TYPE_MY_INFO = 0
        private const val VIEW_TYPE_FRIEND_INFO = 1
        private const val VIEW_TYPE_BIRTHDAY_INFO = 2
    }
}