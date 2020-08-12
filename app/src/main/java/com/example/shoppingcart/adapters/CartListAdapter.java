package com.example.shoppingcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.databinding.CartRowBinding;
import com.example.shoppingcart.models.CartItem;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartVH> {

    private CartInterface cartInterface;
    public CartListAdapter(CartInterface cartInterface) {
        super(CartItem.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartRowBinding cartRowBinding = CartRowBinding.inflate(layoutInflater, parent, false);
        return new CartVH(cartRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        holder.cartRowBinding.setCartItem(getItem(position));
        holder.cartRowBinding.executePendingBindings();
    }

    class CartVH extends RecyclerView.ViewHolder {

        CartRowBinding cartRowBinding;
        public CartVH(@NonNull CartRowBinding cartRowBinding) {
            super(cartRowBinding.getRoot());
            this.cartRowBinding = cartRowBinding;

            cartRowBinding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                }
            });

            cartRowBinding.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int quantity = position + 1;
                    if (quantity == getItem(getAdapterPosition()).getQuantity()) {
                        return;
                    }
                    cartInterface.changeQuantity(getItem(getAdapterPosition()), quantity);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public interface CartInterface {
        void deleteItem(CartItem cartItem);
        void changeQuantity(CartItem cartItem, int quantity);
    }
}
