package com.app.currencyconverter.apputils

interface AppConstants {

    companion object {
        const val IS_LOGIN: String = "IS_LOGIN"
        const val MOBILE: String = "MOBILE"
        const val IMAGE_LIST: String = "IMAGE_LIST"
        const val IMAGE_POSITION: String = "IMAGE_POSITION"
        const val SETTINGS: String = "SETTINGS"
        const val NAME: String = "NAME"
        const val AMOUNT: String = "AMOUNT"
        const val orderId: String = "orderId"
        const val paymentId: String = "paymentId"
        const val totalPaidAmount: String = "totalPaidAmount"
    }

    interface APIEndPoints {
        companion object {
            const val BASE_URL = "https://openexchangerates.org/api/"
            const val latest: String = "latest"
        }
    }

    interface RequestParameters {
        companion object {
            const val MOBILE = "mobile"
            const val NAME = "name"
            const val BIRTH_DATE = "birth_date"
            const val CID = "cid"
            const val START = "start"
            const val END = "end"
            const val USER_MOBILE = "user_mobile"
            const val PID = "pid"
            const val EMAIL = "email"
            const val CONTACT_NO = "contact_no"
            const val MESSAGE = "message"
            const val customer_name = "customer_name"
            const val customer_email = "customer_email"
            const val area = "area"
            const val sub_area = "sub_area"
            const val address = "address"
            const val zipcode = "zipcode"
            const val city = "city"
            const val state = "state"
            const val mobile_no = "mobile_no"
            const val alternate_contact_no = "alternate_contact_no"
            const val product_id = "product_id"
            const val product_name = "product_name"
            const val packing_id = "packing_id"
            const val packing_weight = "packing_weight"
            const val packing_weight_type = "packing_weight_type"
            const val packing_quantity = "packing_quantity"
            const val packing_price = "packing_price"
            const val gift_pack = "gift_pack"
            const val notes = "notes"
            const val order_amount = "order_amount"
            const val shipping_charge = "shipping_charge"
            const val platform = "platform"
            const val order_no = "order_no"
            const val razorpay_orderid = "razorpay_orderid"
            const val razorpay_paymentid = "razorpay_paymentid"
        }
    }
}