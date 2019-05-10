package pl.sjmprofil.grocerylist.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.add_item_dialog_fragment.*
import pl.sjmprofil.grocerylist.R

class AddItemDialogFragment: DialogFragment() {

    var onAddButtonClick: ((String, String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return inflater.inflate(R.layout.add_item_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       button_cancel_bio_dialog_fragment.setOnClickListener {
            dismiss()
        }

        button_add_bio_dialog_fragment.setOnClickListener {
            onAddButtonClick?.invoke(
                tv_add_header_dialog_fragment.text.toString(),
                tv_add_description_dialog_fragment.text.toString())
            tv_add_header_dialog_fragment.text.clear()
            tv_add_description_dialog_fragment.text.clear()
        }
    }
}
