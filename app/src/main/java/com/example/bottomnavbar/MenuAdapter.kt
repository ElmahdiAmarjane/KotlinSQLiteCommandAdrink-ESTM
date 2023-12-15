package com.example.bottomnavbar

// CommandAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.DatabaseHelper.MenuModel
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView


class MenuAdapter(private val products: List<MenuModel>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewTitle: TextView = itemView.findViewById(R.id.productTitle)
        val viewPrice: TextView = itemView.findViewById(R.id.productPrice)
        val circleImageView:CircleImageView = itemView.findViewById(R.id.circleimageviewmenu)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentProduct= products[position]
        holder.viewTitle.text = currentProduct.title
        holder.viewPrice.text = currentProduct.price.toString()+" DH"
        // Assuming `imagesrc` is the image name (e.g., "coffee")
        val imageName = "p"+currentProduct.imagesrc
        val context = holder.itemView.context
        // Get the resource ID directly
        val imageResource = context.resources.getIdentifier(imageName, "drawable", context.packageName)

        // Check if the resource was found
        if (imageResource != 0) {
            holder.circleImageView.setImageResource(imageResource)
        } else {
            // Handle the case where the resource was not found
            holder.circleImageView.setImageResource(R.drawable.xmark) // Use a default image or handle accordingly
        }


    }

    override fun getItemCount(): Int {
        return products.size
    }
}
