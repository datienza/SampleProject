package com.farmdrop.customer.utils.constants

object CMSConstants {

    // To find the document id on Prismic, open a document. Look at the URL. It should look like:
    // https://farmdrop-web.prismic.io/documents~b=working&c=published&l=en-gb/Wm76MSQAAFFK6YYp*WorCPioAAHdXLDz1/
    // The id is the first bit after the penultimate /. In this case, it's Wm76MSQAAFFK6YYp.

    const val DOCUMENT_ID_LEAVE_SAFE = "Wm76MSQAAFFK6YYp"
    const val DOCUMENT_ID_ORDER_CONFIRMATION = "Wp8GrCYAAOp-vClb"
    const val DOCUMENT_ID_CREDIT = "Wgwl3CkAAOKPMJbB"
    const val DOCUMENT_ID_NEWSLETTER = "WrTftSIAAN_4-Upc"
    const val DOCUMENT_ID_ETHICAL_REMINDERS = "Wo16ESoAAAL_ODum"
    const val DOCUMENT_ID_FAQS = "WaaiaCsAAE0wiNbz"
    const val DOCUMENT_ID_HOW_IT_WORKS = "WlyEiCcAACBh3nbH"
    const val DOCUMENT_ID_HOW_WE_SOURCE = "WlYaDi0AABQnjTXk"
    const val DOCUMENT_ID_GDPR_NAME = "Wt879TEAAHVDaklo"
    const val DOCUMENT_ID_GDPR_CONTACT = "Wt88QjEAAHVDakrG"
    const val DOCUMENT_ID_GDPR_ADDRESS = "Wt9RKCoAADd5_H29"
    const val DOCUMENT_ID_POSTCODE_CHECKER = "WwUuViIAAHO1uQ0Y"
    const val DOCUMENT_ID_ON_BOARDING = "Ws3wsh8AAP1J1f2-"

    const val NEW_ORDER_SUBHEADING_KEY = "headers.new_order_subheading"
    const val NEW_CREDIT_KEY = "credit_component.standard_amount_in_pounds"
    const val SINGLE_TEXT_KEY = "single_text.text"
    const val ETHICAL_REMINDERS_ELEMENTS_KEY = "ethical_reminders.elements"
    const val FAQS_CATEGORIES_KEY = "faqs_page.categories"
    const val CATEGORY_KEY = "category"

    const val TITLE_KEY = "title"
    const val IMAGE_KEY = "image"

    private const val POSTCODE_CHECKER_KEY = "postcode_checker."

    const val POSTCODE_TITLE_KEY = POSTCODE_CHECKER_KEY + "postcode_title"
    const val POSTCODE_COPY_KEY = POSTCODE_CHECKER_KEY + "postcode_copy"
    const val POSTCODE_INPUT_PLACEHOLDER_TEXT_KEY = POSTCODE_CHECKER_KEY + "postcode_input_placeholder_text"
    const val POSTCODE_CTA_BUTTON_KEY = POSTCODE_CHECKER_KEY + "postcode_cta_button"
    const val POSTCODE_VALIDATION_MESSAGE_KEY = POSTCODE_CHECKER_KEY + "postcode_validation_message"
    const val POSTCODE_MODAL_COPY_KEY = POSTCODE_CHECKER_KEY + "postcode_modal_copy"
    const val POSTCODE_SUPPORTED_TITLE_KEY = POSTCODE_CHECKER_KEY + "postcode_supported_title"
    const val POSTCODE_SUPPORTED_COPY_KEY = POSTCODE_CHECKER_KEY + "postcode_supported_copy"

    const val EMAIL_TITLE_KEY = POSTCODE_CHECKER_KEY + "email_title"
    const val EMAIL_COPY_KEY = POSTCODE_CHECKER_KEY + "email_copy"
    const val EMAIL_COPY_CTA_KEY = POSTCODE_CHECKER_KEY + "email_copy_cta"
    const val EMAIL_INPUT_PLACEHOLDER_TEXT_KEY = POSTCODE_CHECKER_KEY + "email_input_placeholder_text"
    const val EMAIL_CTA_BUTTON_KEY = POSTCODE_CHECKER_KEY + "email_cta_button"
    const val EMAIL_VALIDATION_MESSAGE_KEY = POSTCODE_CHECKER_KEY + "email_validation_message"

    const val THANKS_SIGNING_UP_TITLE_KEY = "thanks_for_signing_up_title"
    const val THANKS_SIGNING_UP_COPY_KEY = "thanks_for_signing_up_copy"

    const val ON_BOARDING_PAGE_KEY = "on_boarding.page"
    const val PAGE_TITLE_KEY = "page_title"
    const val PAGE_BODY_KEY = "page_body"
}
