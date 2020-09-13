package luyao.wanandroid.address

import android.content.Context

class AddressSelectorKt : AddressSelector() {
    companion object {
        // Just call the superclass implementation for now
        fun build(context: Context): Builder =
                AddressSelector.build(context)
    }

}