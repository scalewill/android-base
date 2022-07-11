package com.scalewill.android.profile

import com.scalewill.android.graphql.query.GetUserQuery
import com.scalewill.android.profile.entities.Profile

object ProfileMapper {
    fun mapProfile(me: GetUserQuery.Me?) = me?.fragments?.userGqlFragment.run {
        Profile(
            firstName = this?.firstName ?: "",
            lastName = this?.lastName ?: ""
        )
    }
}
