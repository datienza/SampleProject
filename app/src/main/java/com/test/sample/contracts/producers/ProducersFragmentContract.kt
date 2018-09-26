package com.farmdrop.customer.contracts.producers

import com.farmdrop.customer.data.model.ProducerData
import uk.co.farmdrop.library.ui.base.BaseListContract

/**
 * Define contract methods between ProducersListFragment & ProducersFragmentPresenter
 */
interface ProducersFragmentContract {

    interface View : BaseListContract.BaseListView<ProducerData>
}
