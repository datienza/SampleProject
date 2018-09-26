package com.farmdrop.customer.contracts.account

import com.farmdrop.customer.data.model.order.OrderContent
import uk.co.farmdrop.library.ui.base.BaseListContract

interface OrdersFragmentContract {
    interface View : BaseListContract.BaseListView<OrderContent>
}