package org.app.dzung.data

import org.app.dzung.common.call

class Repository {

    suspend fun getPopularAssets() = call {
        DataSource.assets().take(10)
    }

    suspend fun getAllAssets() = call {
        DataSource.assets()
    }

    suspend fun getRecentTransactions() = call {
        DataSource.transactions().take(10)
    }

    suspend fun getHistoryTransactions() = call {
        DataSource.transactions()
    }
}