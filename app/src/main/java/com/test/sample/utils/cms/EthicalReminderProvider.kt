package com.farmdrop.customer.utils.cms

import com.farmdrop.customer.utils.constants.CMSConstants
import io.prismic.Document
import io.prismic.Fragment
import java.util.Random

class EthicalReminderProvider(private val mCMS: CMS, private val mRandom: Random) {

    var mEthicalReminder: ArrayList<EthicalReminder>? = null

    fun loadEthicalInfo(listener: Listener) {
        mCMS.loadDocument(CMSConstants.DOCUMENT_ID_ETHICAL_REMINDERS, object : CMS.Listener {
            override fun onDocumentLoaded(document: Document) {
                val fragmentGroup = document.getGroup(CMSConstants.ETHICAL_REMINDERS_ELEMENTS_KEY)
                if (fragmentGroup != null) {
                    parseEthicalInfo(fragmentGroup)
                    listener.onLoaded()
                } else {
                    listener.onError()
                }
            }

            override fun onDocumentError(documentId: String, error: String) {
                listener.onError()
            }
        })
    }

    private fun parseEthicalInfo(fragmentGroup: Fragment.Group) {
        mEthicalReminder = ArrayList()
        fragmentGroup.docs.forEach {
            mEthicalReminder?.add(EthicalReminder((it.fragments[CMSConstants.TITLE_KEY] as Fragment.StructuredText).title.text,
                    (it.fragments[CMSConstants.IMAGE_KEY] as Fragment.ImageLink).url))
        }
    }

    fun getRandomEthicalInfo(): EthicalReminder? {
        mEthicalReminder?.let {
            return it[mRandom.nextInt(it.size)]
        }
        return null
    }

    interface Listener {
        fun onLoaded()
        fun onError()
    }
}