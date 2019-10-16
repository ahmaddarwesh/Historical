package com.superdar.historical

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brouding.simpledialog.SimpleDialog
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        callUs.setOnClickListener {
            SimpleDialog.Builder(this)
                .setTitle("إتصل بنا")
                .setContent("هل تريد حقاً الإتصال بنا عبر الهاتف؟")
                .setBtnConfirmText("نعم", true)
                .onConfirm { dialog, which ->
                    dialog.hide()
                    val intent = Intent(Intent.ACTION_DIAL);
                    intent.data = Uri.parse("tel:0096171085998")
                    startActivity(intent)
                }
                .setBtnCancelText("إلغاء")
                .onCancel { dialog, which ->
                    dialog.hide()
                }
                .show()
        }
        EmailUs.setOnClickListener {
            SimpleDialog.Builder(this)
                .setTitle("إرسال بريد إلكتروني")
                .setContent("هل تريد حقاً مراسلتنا عبر البريد إلكتروني؟")
                .setBtnConfirmText("نعم", true)
                .onConfirm { dialog, which ->
                    dialog.hide()
                    val email = Intent(Intent.ACTION_SEND)
                    email.putExtra(Intent.EXTRA_EMAIL, arrayOf("Dar.workspace@gmail.com"))
                    email.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
                    email.putExtra(Intent.EXTRA_TEXT, "")
                    email.type = "message/rfc822"
                    startActivity(Intent.createChooser(email, "Choose an Email client :"))
                }
                .setBtnCancelText("إلغاء")
                .onCancel { dialog, which ->
                    dialog.hide()
                }
                .show()
        }
        starUs.setOnClickListener {
            SimpleDialog.Builder(this)
                .setTitle("ادعمنا بخمس نجوم")
                .setContent("هل تريد تقييم البرنامج في متجر التطبيقات؟")
                .setBtnConfirmText("نعم", true)
                .onConfirm { dialog, which ->
                    dialog.hide()
                    val i = Intent(Intent.ACTION_VIEW);
                    i.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                    startActivity(i)
                }
                .setBtnCancelText("إلغاء")
                .onCancel { dialog, which ->
                    dialog.hide()
                }
                .show()
        }

    }
}
