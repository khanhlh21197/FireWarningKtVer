package com.khanhlh.firewarningkt.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 页面描述：用户
 *
 * Created by ditclear on 2017/9/26.
 */

@Entity(tableName = "users")
class User
constructor(
    email: String = "",
    password: String = "",
    rePassword: String = "",
    name: String = "",
    phoneNumber: String = "",
    address: String = ""
) : Serializable {

    @PrimaryKey
    @ColumnInfo(name = "userid")
    var id: Int = 0

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    var email: String? = null

    @SerializedName("password")
    @Expose
    @ColumnInfo(name = "password")
    var password: String? = null

    var rePassword: String? = null

    @SerializedName("phoneNumber")
    @Expose
    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    var name: String? = null

    @SerializedName("address")
    @Expose
    @ColumnInfo(name = "address")
    var address: String? = null
}
