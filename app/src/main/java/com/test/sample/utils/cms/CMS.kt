package com.farmdrop.customer.utils.cms

import android.content.Context
import com.farmdrop.customer.BuildConfig
import io.prismic.Api
import io.prismic.Document
import io.prismic.Fragment
import io.prismic.SimpleLinkResolver
import io.prismic.android.Prismic
import java.util.LinkedList

class CMS(context: Context) {

    companion object {
        private const val ERROR_DOCUMENT_NOT_FOUND = "Document not found."
        private const val ERROR_UNKNOWN = "Unknown error."
        private const val ROOT_URL = "/"
    }

    private val mPrismic: Prismic = Prismic(context, BuildConfig.PRISMIC_API_ENDPOINT, BuildConfig.PRISMIC_API_TOKEN)

    private var mDocumentsRequests: LinkedList<Pair<String, Listener>>? = null

    fun init() {
        mPrismic.registerListener(object : Prismic.Listener<Api>() {
            override fun onSuccess(result: Api?) {
                mDocumentsRequests?.let { documentsRequests ->
                    documentsRequests.forEach { internalLoadDocument(it.first, it.second) }
                    documentsRequests.clear()
                    mDocumentsRequests = null
                }
            }
        })
        mPrismic.init()
    }

    private fun isInitialized() = mPrismic.api != null

    fun loadDocument(documentId: String, listener: Listener) {
        if (isInitialized()) {
            internalLoadDocument(documentId, listener)
        } else {
            if (mDocumentsRequests == null) {
                mDocumentsRequests = LinkedList()
            }
            mDocumentsRequests?.add(Pair(documentId, listener))
        }
    }

    private fun internalLoadDocument(documentId: String, listener: Listener) {
        mPrismic.getDocument(documentId, object : Prismic.Listener<Document>() {
            override fun onSuccess(result: Document?) {
                if (result != null) {
                    listener.onDocumentLoaded(result)
                } else {
                    listener.onDocumentError(documentId, ERROR_DOCUMENT_NOT_FOUND)
                }
            }

            override fun onError(error: Api.Error?) {
                super.onError(error)
                listener.onDocumentError(documentId, error?.toString() ?: ERROR_UNKNOWN)
            }
        })
    }

    fun getDocumentAsHtml(document: Document): String {
        val linkResolver = object : SimpleLinkResolver() {
            override fun resolve(link: Fragment.DocumentLink?): String {
                return ROOT_URL
            }
        }

        return document.asHtml(linkResolver)
    }

    interface Listener {
        fun onDocumentLoaded(document: Document)
        fun onDocumentError(documentId: String, error: String)
    }
}