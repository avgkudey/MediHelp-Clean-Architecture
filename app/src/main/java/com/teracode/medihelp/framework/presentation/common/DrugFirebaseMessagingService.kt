package com.teracode.medihelp.framework.presentation.common

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class DrugFirebaseMessagingService : FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)

    }


    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
//        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
    }
}