package com.shiro.rickandmorty.helpers

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.*
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import com.shiro.rickandmorty.R
import com.shiro.rickandmorty.data.source.remote.api.ApiError
import com.shiro.rickandmorty.databinding.DialogSimpleLayoutBinding
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.UnknownHostException

object Utils {
    inline fun <reified T : Any> Any.cast(): T {
        return this as T
    }

    fun getError(exception: Exception): ApiError {
        return when (exception) {
            is IOException -> ApiError.Network()
            is UnknownHostException -> ApiError.Network()
            is HttpException -> {
                when (exception.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ApiError.NotFound()
                    HttpURLConnection.HTTP_BAD_REQUEST -> ApiError.BadRequest()
                    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> ApiError.Timeout()
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> ApiError.Server()
                    HttpURLConnection.HTTP_FORBIDDEN -> ApiError.Unauthorized()
                    HttpURLConnection.HTTP_UNAVAILABLE -> ApiError.Unavailable()
                    else -> ApiError.Unknown()
                }
            }
            else -> ApiError.Unknown()
        }
    }

    fun showDoubleDialog(
        context: Context,
        bundle: Bundle,
        clickListener: DialogClickListeners? = null
    ): Dialog {
        val dialogBinding: DialogSimpleLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_simple_layout,
                null,
                false
            )

        if (bundle.containsKey(Constants.DIALOG_KEY_TITLE)) {
            dialogBinding.textTitle.text = bundle.getString(Constants.DIALOG_KEY_TITLE)
        }
        if (bundle.containsKey(Constants.DIALOG_KEY_DESCRIPTION)) {
            dialogBinding.textDialog.text =
                fromHtml(bundle.getString(Constants.DIALOG_KEY_DESCRIPTION))
        }
        if (bundle.containsKey(Constants.DIALOG_KEY_ACCEPT)) {
            dialogBinding.buttonAccept.visibility = View.VISIBLE
            dialogBinding.buttonAccept.text = bundle.getString(Constants.DIALOG_KEY_ACCEPT)
        } else {
            dialogBinding.buttonAccept.visibility = View.GONE
        }
        if (bundle.containsKey(Constants.DIALOG_KEY_CANCEL)) {
            dialogBinding.buttonCancel.visibility = View.VISIBLE
            dialogBinding.buttonCancel.text = bundle.getString(Constants.DIALOG_KEY_CANCEL)
        } else {
            dialogBinding.buttonCancel.visibility = View.GONE
        }

        val dialog = Dialog(context)

        if (bundle.containsKey(Constants.DIALOG_KEY_NON_CANCELABLE)) {
            dialog.setCancelable(false)
        } else {
            dialog.setCancelable(true)
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogBinding.buttonAccept.setOnClickListener {
            if (bundle.getBoolean(Constants.DIALOG_KEY_DISMISS_CLICK, true)) {
                dialog.dismiss()
            }
            clickListener?.positive()
        }
        dialogBinding.buttonCancel.setOnClickListener {
            if (bundle.getBoolean(Constants.DIALOG_KEY_DISMISS_CLICK, true)) {
                dialog.dismiss()
            }
            clickListener?.negative()
        }
        dialogBinding.buttonClose.setOnClickListener {
            if (bundle.getBoolean(Constants.DIALOG_KEY_DISMISS_CLICK, true)) {
                dialog.dismiss()
            }
            clickListener?.close()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }

    fun fromHtml(html: String?): Spanned? {
        return when {
            html == null -> SpannableString("")
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            }
            else -> Html.fromHtml(html)
        }
    }

    interface DialogClickListeners {
        fun positive() {}
        fun negative() {}
        fun close() {}
    }
}