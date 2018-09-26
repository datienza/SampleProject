package com.farmdrop.customer.utils

import com.farmdrop.customer.data.model.delivery.DeliverySlot
import uk.co.farmdrop.library.utils.Constants.INVALID_ID

class DeliverySlotsEditor(private val mDateAndTimeFormatter: DateAndTimeFormatter) {

    fun addClosed(deliverySlots: List<DeliverySlot>): List<DeliverySlot> {
        val daysSet = HashSet<String>()
        val hoursMap = HashMap<String, Float>()

        deliverySlots.forEach { deliverySlot ->
            deliverySlot.day = mDateAndTimeFormatter.extractDateFromIso8601(deliverySlot.openAt)
            deliverySlot.hours = getHoursFromDeliverySlot(deliverySlot)

            deliverySlot.day?.let {
                if (!daysSet.contains(it)) {
                    daysSet.add(it)
                }
            }

            deliverySlot.hours?.let {
                if (!hoursMap.contains(it)) {
                    hoursMap[it] = deliverySlot.duration
                }
            }
        }

        val fullDeliverySlots = ArrayList<DeliverySlot>()

        daysSet.forEach { day ->
            hoursMap.forEach { hours ->
                var deliverySlotMatchingDayAndHours = deliverySlots.find { it.day == day && it.hours == hours.key }
                if (deliverySlotMatchingDayAndHours == null) {
                    val openAt = mDateAndTimeFormatter.createIso8601FromDateAndTime(day, mDateAndTimeFormatter.extractFirstTimeFromConcat(hours.key))
                    val closeAt = mDateAndTimeFormatter.createIso8601FromDateAndTime(day, mDateAndTimeFormatter.extractSecondTimeFromConcat(hours.key))
                    deliverySlotMatchingDayAndHours = DeliverySlot(INVALID_ID, openAt, closeAt, null, 0, hours.value, false, DeliverySlot.AVAILABILITY_REASON_CLOSED, day, hours.key)
                }
                fullDeliverySlots.add(deliverySlotMatchingDayAndHours)
            }
        }
        return fullDeliverySlots
    }

    private fun getHoursFromDeliverySlot(deliverySlot: DeliverySlot): String {
        val openingHour = mDateAndTimeFormatter.extractTimeFromIso8601(deliverySlot.openAt)
        val closingHour = mDateAndTimeFormatter.extractTimeFromIso8601(deliverySlot.closeAt)
        return mDateAndTimeFormatter.concatTimes(openingHour, closingHour)
    }
}