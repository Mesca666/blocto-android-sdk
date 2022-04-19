package com.portto.sdk.core.method

import android.net.Uri
import com.portto.sdk.core.Blockchain
import com.portto.sdk.wallet.BloctoSDKError
import com.portto.sdk.wallet.Const
import org.json.JSONObject

class SignAndSendTransactionMethod(
    val fromAddress: String,
    val message: String,
    val isInvokeWrapped: Boolean,
    val publicKeySignaturePairs: Map<String, String>? = null,
    val appendTx: Map<String, String>? = null,
    blockchain: Blockchain,
    onSuccess: (String) -> Unit,
    onError: (BloctoSDKError) -> Unit
) : Method<String>(blockchain, onSuccess, onError) {

    override val name: String
        get() = "sign_and_send_transaction"

    override fun encodeToUri(authority: String, appId: String, requestId: String): Uri.Builder {
        return super.encodeToUri(authority, appId, requestId)
            .appendQueryParameter(Const.KEY_FROM, fromAddress)
            .appendQueryParameter(Const.KEY_MESSAGE, message)
            .appendQueryParameter(Const.KEY_IS_INVOKE_WRAPPED, isInvokeWrapped.toString())
            .apply {
                publicKeySignaturePairs?.let {
                    appendQueryParameter(
                        Const.KEY_PUBLIC_KEY_SIGNATURE_PAIRS,
                        JSONObject(it).toString()
                    )
                }
                appendTx?.let {
                    appendQueryParameter(
                        Const.KEY_APPEND_TX,
                        JSONObject(it).toString()
                    )
                }
            }
    }
}
